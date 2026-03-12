package ctn.imaginarycraft.mixin.world.item;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import ctn.imaginarycraft.api.data.ConditionalProviderFactory;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.mixed.IWeaponCapability$Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(WeaponCapability.Builder.class)
public abstract class WeaponCapability$BuilderMixin extends CapabilityItem.Builder<WeaponCapability.Builder> implements IWeaponCapability$Builder {
  @Shadow
  boolean canBePlacedOffhand;
  @Unique
  private Function<LivingEntityPatch<?>, HitParticleType> imaginarycraft$hitParticleProvider;
  @Unique
  private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$swingSoundProvider;
  @Unique
  private Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$hitSoundProvider;
  @Unique
  private Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> imaginarycraft$autoAttackMotionProviderMap;
  @Unique
  private Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> imaginarycraft$innateSkillProviderByStyle;
  @Unique
  private Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginarycraft$livingMotionProviderModifiers;
  @Unique
  private Function<LivingEntityPatch<?>, Collider> imaginarycraft$colliderProvider;

  @Override
  public IWeaponCapability$Builder imaginarycraft$hitParticle(HitParticleType defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, HitParticleType>> predicates) {
    imaginarycraft$hitParticleProvider = ConditionalProviderFactory.getProvider(defaultValue, predicates);
    return this;
  }

  @Override
  public IWeaponCapability$Builder imaginarycraft$swingSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates) {
    imaginarycraft$swingSoundProvider = ConditionalProviderFactory.getProvider(defaultValue, predicates);
    return this;
  }

  @Override
  public IWeaponCapability$Builder imaginarycraft$hitSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates) {
    imaginarycraft$hitSoundProvider = ConditionalProviderFactory.getProvider(defaultValue, predicates);
    return this;
  }

  @Override
  public IWeaponCapability$Builder imaginarycraft$newStryleCombo(
    Style style,
    List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> defaultPredicates,
    @Nullable List<Pair<Predicate<LivingEntityPatch<?>>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> predicates
  ) {
    if (imaginarycraft$autoAttackMotionProviderMap == null) {
      imaginarycraft$autoAttackMotionProviderMap = new HashMap<>();
    }
    imaginarycraft$autoAttackMotionProviderMap.put(style, ConditionalProviderFactory.getProvider(defaultPredicates, predicates));
    return this;
  }

  @Override
  public IWeaponCapability$Builder imaginarycraft$innateSkill(Style style, Function<ItemStack, Skill> defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Function<ItemStack, Skill>>> predicates) {
    if (imaginarycraft$innateSkillProviderByStyle == null) {
      imaginarycraft$innateSkillProviderByStyle = new HashMap<>();
    }
    imaginarycraft$innateSkillProviderByStyle.put(style, ConditionalProviderFactory.getProvider(defaultValue, predicates));
    return this;
  }

  @Override
  public IWeaponCapability$Builder imaginarycraft$livingMotionModifier(
    Style style,
    LivingMotion livingMotion,
    AnimationManager.AnimationAccessor<? extends StaticAnimation> defaultValue,
    List<Pair<Predicate<LivingEntityPatch<?>>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> predicates
  ) {
    if (AnimationManager.checkNull(defaultValue)) {
      ImaginaryCraft.LOGGER.warn("Unable to put an empty animation to weapon capability builder: {}, {}", livingMotion, defaultValue);
      return this;
    }

    if (imaginarycraft$livingMotionProviderModifiers == null) {
      imaginarycraft$livingMotionProviderModifiers = Maps.newHashMap();
    }

    if (!imaginarycraft$livingMotionProviderModifiers.containsKey(style)) {
      imaginarycraft$livingMotionProviderModifiers.put(style, new HashMap<>());
    }

    imaginarycraft$livingMotionProviderModifiers.get(style).put(livingMotion, ConditionalProviderFactory.getProvider(defaultValue, predicates));

    return this;
  }

  @Override
  public IWeaponCapability$Builder imaginarycraft$collider(Collider defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Collider>> predicates) {
    imaginarycraft$colliderProvider = ConditionalProviderFactory.getProvider(defaultValue, predicates);
    return this;
  }

  @Override
  public Function<LivingEntityPatch<?>, HitParticleType> imaginaryCraft$getHitParticleProvider() {
    return imaginarycraft$hitParticleProvider;
  }

  @Override
  public Function<LivingEntityPatch<?>, SoundEvent> imaginaryCraft$getSwingSoundProvider() {
    return imaginarycraft$swingSoundProvider;
  }

  @Override
  public Function<LivingEntityPatch<?>, SoundEvent> imaginaryCraft$getHitSoundProvider() {
    return imaginarycraft$hitSoundProvider;
  }

  @Override
  public Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> imaginaryCraft$getAutoAttackMotionProviderMap() {
    return imaginarycraft$autoAttackMotionProviderMap;
  }

  @Override
  public Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> imaginaryCraft$getInnateSkillProviderByStyle() {
    return imaginarycraft$innateSkillProviderByStyle;
  }

  @Override
  public Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginaryCraft$getLivingMotionProviderModifiers() {
    return imaginarycraft$livingMotionProviderModifiers;
  }

  @Override
  public Function<LivingEntityPatch<?>, Collider> imaginaryCraft$getColliderProvider() {
    return imaginarycraft$colliderProvider;
  }
}
