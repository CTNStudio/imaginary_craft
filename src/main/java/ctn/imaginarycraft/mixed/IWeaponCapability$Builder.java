package ctn.imaginarycraft.mixed;

import com.mojang.datafixers.util.Pair;
import ctn.imaginarycraft.api.NoMixinException;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IWeaponCapability$Builder {
  static IWeaponCapability$Builder of(WeaponCapability.Builder builder) {
    return (IWeaponCapability$Builder) builder;
  }

  default IWeaponCapability$Builder imaginarycraft$hitParticle(HitParticleType defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, HitParticleType>> predicates) {
    throw new NoMixinException();
  }

  default Function<LivingEntityPatch<?>, HitParticleType> imaginaryCraft$getHitParticleProvider() {
    throw new NoMixinException();
  }

  default IWeaponCapability$Builder imaginarycraft$swingSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates) {
    throw new NoMixinException();
  }

  default Function<LivingEntityPatch<?>, SoundEvent> imaginaryCraft$getSwingSoundProvider() {
    throw new NoMixinException();
  }

  default IWeaponCapability$Builder imaginarycraft$hitSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates) {
    throw new NoMixinException();
  }

  default Function<LivingEntityPatch<?>, SoundEvent> imaginaryCraft$getHitSoundProvider() {
    throw new NoMixinException();
  }

  default IWeaponCapability$Builder imaginarycraft$newStryleCombo(Style style, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> defaultPredicates, List<Pair<Predicate<LivingEntityPatch<?>>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> predicates) {
    throw new NoMixinException();
  }

  default Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> imaginaryCraft$getAutoAttackMotionProviderMap() {
    throw new NoMixinException();
  }

  default IWeaponCapability$Builder imaginarycraft$innateSkill(Style style, Function<ItemStack, Skill> defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Function<ItemStack, Skill>>> predicates) {
    throw new NoMixinException();
  }

  default Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> imaginaryCraft$getInnateSkillProviderByStyle() {
    throw new NoMixinException();
  }

  default IWeaponCapability$Builder imaginarycraft$livingMotionModifier(Style style, LivingMotion livingmotion, AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, List<Pair<Predicate<LivingEntityPatch<?>>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> pairs) {
    throw new NoMixinException();
  }

  default Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginaryCraft$getLivingMotionProviderModifiers() {
    throw new NoMixinException();
  }

  default IWeaponCapability$Builder imaginarycraft$collider(Collider defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Collider>> predicates) {
    throw new NoMixinException();
  }

  default Function<LivingEntityPatch<?>, Collider> imaginaryCraft$getColliderProvider() {
    throw new NoMixinException();
  }
}
