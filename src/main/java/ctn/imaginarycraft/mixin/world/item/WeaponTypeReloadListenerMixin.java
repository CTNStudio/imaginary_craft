package ctn.imaginarycraft.mixin.world.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.util.Pair;
import ctn.imaginarycraft.api.data.AnimationComboParser;
import ctn.imaginarycraft.api.data.ConditionalEntryParser;
import ctn.imaginarycraft.api.data.ModWeaponTypeReloadListener;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.mixed.IWeaponCapability$Builder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.registry.EpicFightRegistries;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponTypeReloadListener;
import yesman.epicfight.world.capabilities.provider.ExtraEntryProvider;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(WeaponTypeReloadListener.class)
public abstract class WeaponTypeReloadListenerMixin {
  //region ==================== collider - 碰撞箱 ====================

  /**
   * 应用碰撞箱到构建器，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 0, target = "Lyesman/epicfight/gameasset/ColliderPreset;deserializeSimpleCollider(Lnet/minecraft/nbt/CompoundTag;)Lyesman/epicfight/api/collider/Collider;"))
  private static Collider imaginarycraft$deserializeWeaponCapabilityBuilder$applyColliderToBuilder(
    CompoundTag compTag,
    Operation<Collider> original,
    @Local(name = "tag") CompoundTag tag,
    @Local(name = "builder") WeaponCapability.Builder builder
  ) {
    Collider defaultCollider = original.call(compTag);
    if (!tag.contains("collider_cases")) {
      return defaultCollider;
    }

    IWeaponCapability$Builder.of(builder).imaginarycraft$collider(defaultCollider, ConditionalEntryParser.parseFromTag(
      tag.getList("collider_cases", Tag.TAG_COMPOUND),
      args -> ColliderPreset.deserializeSimpleCollider((CompoundTag) args),
      (a, f) -> a != null));

    return defaultCollider;
  }
  //endregion

  //region ==================== hit_particle - 命中粒子效果 ====================

  /**
   * 应用命中粒子的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;hitParticle(Lyesman/epicfight/particle/HitParticleType;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$hitParticle$applyPredicates(
    ResourceLocation rl,
    CompoundTag tag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "particleType") ParticleType<?> particleType
  ) {
    if (!tag.contains("hit_particle_cases")) {
      return;
    }

    //noinspection unchecked
    IWeaponCapability$Builder.of(builder).imaginarycraft$hitParticle((HitParticleType) particleType, (List<Pair<Predicate<LivingEntityPatch<?>>, HitParticleType>>) (List<?>) ConditionalEntryParser.parseFromRegistryString(
      tag.getList("hit_particle_cases", Tag.TAG_COMPOUND), BuiltInRegistries.PARTICLE_TYPE, (particleType1, string) -> {
        if (particleType1 == null) {
          ImaginaryCraft.LOGGER.warn("Cannot find particle type '{}' in {}", string, rl);
          return false;
        }

        if (!(particleType1 instanceof HitParticleType)) {
          ImaginaryCraft.LOGGER.warn("'{}' is not a hit particle type in {}", string, rl);
          return false;
        }
        return true;
      })
    );
  }
  //endregion

  //region ==================== swing_sound - 挥舞声音 ====================

  /**
   * 应用挥舞声音的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;swingSound(Lnet/minecraft/sounds/SoundEvent;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$swingSound$applyPredicates(
    ResourceLocation rl,
    CompoundTag tag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "sound") SoundEvent sound
  ) {
    if (!tag.contains("swing_sound_cases")) {
      return;
    }

    IWeaponCapability$Builder.of(builder).imaginarycraft$swingSound(sound, ConditionalEntryParser.parseFromRegistryString(
      tag.getList("swing_sound_cases", Tag.TAG_COMPOUND), BuiltInRegistries.SOUND_EVENT, (soundEvent, string) -> {
        if (soundEvent != null) {
          return true;
        }

        ImaginaryCraft.LOGGER.warn("Cannot find swing sound '{}' in {}", string, rl);
        return false;
      })
    );
  }
  //endregion

  //region ==================== hit_sound - 命中声音 ====================

  /**
   * 应用命中声音的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;hitSound(Lnet/minecraft/sounds/SoundEvent;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$hitSound$applyPredicates(
    ResourceLocation rl,
    CompoundTag tag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "sound") SoundEvent sound
  ) {
    if (!tag.contains("hit_sound_cases")) {
      return;
    }

    IWeaponCapability$Builder.of(builder).imaginarycraft$hitSound(sound, ConditionalEntryParser.parseFromRegistryString(
      tag.getList("hit_sound_cases", Tag.TAG_COMPOUND), BuiltInRegistries.SOUND_EVENT, (soundEvent, string) -> {
        if (soundEvent != null) {
          return true;
        }

        ImaginaryCraft.LOGGER.warn("Cannot find hit sound '{}' in {}", string, rl);
        return false;
      })
    );
  }
  //endregion

  //region ==================== combos - 连招 ====================

  /**
   * 包裹连招配置标签的解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 0,
      target = "Lnet/minecraft/nbt/CompoundTag;getList(Ljava/lang/String;I)Lnet/minecraft/nbt/ListTag;"))
  private static ListTag imaginarycraft$combos$wrapConfigTag(
    CompoundTag compoundTag,
    String key,
    int tagTypeInt,
    Operation<ListTag> original,
    @Local(name = "rl") ResourceLocation resourceLocation,
    @Local(name = "extraEntryProvider") @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    @Local(name = "key") String configKey,
    @Share("defaultCombos") LocalRef<List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>> defaultComboListShare,
    @Share("casesCombos") LocalRef<List<Pair<Predicate<LivingEntityPatch<?>>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>>> casesComboListShare
  ) {
    Tag styleTags = compoundTag.get(configKey);

    if (styleTags instanceof ListTag styleListTag) {
      List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> parsedCombos =
        AnimationComboParser.parseComboAnimations(resourceLocation, extraEntryProvider, styleListTag);
      if (parsedCombos != null) {
        defaultComboListShare.set(parsedCombos);
      }
      return original.call(compoundTag, key, tagTypeInt);
    }

    if (!(styleTags instanceof CompoundTag styleCompoundTag)) {
      ImaginaryCraft.LOGGER.warn("Cannot find combos config in {}", resourceLocation);
      return original.call(compoundTag, key, tagTypeInt);
    }

    Tag defaultStyleTag = styleCompoundTag.get(ModWeaponTypeReloadListener.DEFAULT);
    if (defaultStyleTag == null) {
      ImaginaryCraft.LOGGER.warn("Cannot find default combos config in {}", resourceLocation);
      return original.call(compoundTag, key, tagTypeInt);
    }

    if (!(defaultStyleTag instanceof ListTag defaultStyleListTag)) {
      return original.call(compoundTag, key, tagTypeInt);
    }

    List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> parsedDefaultCombos =
      AnimationComboParser.parseComboAnimations(resourceLocation, extraEntryProvider, defaultStyleListTag);
    if (parsedDefaultCombos != null) {
      defaultComboListShare.set(parsedDefaultCombos);
    }

    casesComboListShare.set(ConditionalEntryParser.parseCasesFromTag(
      styleCompoundTag,
      tag -> AnimationComboParser.parseComboAnimations(resourceLocation, extraEntryProvider, (ListTag) tag),
      (functions, tag) -> functions != null && !functions.isEmpty()));

    return defaultStyleListTag;
  }

  /**
   * 包裹连招动画 ID 的解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 0,
      target = "Lnet/minecraft/nbt/ListTag;getString(I)Ljava/lang/String;"))
  private static String imaginarycraft$combos$wrapAnimationId(
    ListTag listTag,
    int index,
    Operation<String> original,
    @Local(name = "rl") ResourceLocation resourceLocation
  ) {
    Tag tag = listTag.get(index);
    if (!(tag instanceof CompoundTag tagCompound)) {
      return original.call(listTag, index);
    }

    if (tagCompound.contains(ModWeaponTypeReloadListener.DEFAULT)) {
      return tagCompound.getString(ModWeaponTypeReloadListener.DEFAULT);
    }

    ImaginaryCraft.LOGGER.warn("Cannot find default animation in {}", resourceLocation);
    return "";

  }

  /**
   * 应用连招的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;newStyleCombo(Lyesman/epicfight/world/capabilities/item/Style;[Lyesman/epicfight/api/animation/AnimationManager$AnimationAccessor;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$combos$applyPredicates(
    ResourceLocation resourceLocation,
    CompoundTag configTag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "style") Style style,
    @Share("defaultCombos") LocalRef<List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>> defaultComboListShare,
    @Share("casesCombos") LocalRef<List<Pair<Predicate<LivingEntityPatch<?>>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>>> casesComboListShare
  ) {
    var defaultPredicates = defaultComboListShare.get();
    var predicates = casesComboListShare.get();
    if (defaultPredicates == null || predicates == null) {
      return;
    }

    IWeaponCapability$Builder.of(builder).imaginarycraft$newStryleCombo(style, defaultPredicates, predicates);
  }
  //endregion

  //region ==================== innate_skills - 先天技能 ====================

  /**
   * 包裹先天技能的默认值解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 9,
      target = "Lnet/minecraft/nbt/CompoundTag;getString(Ljava/lang/String;)Ljava/lang/String;"))
  private static String imaginarycraft$innateSkills$wrapConfigTag(
    CompoundTag compoundTag,
    String key,
    Operation<String> original
  ) {
    if (compoundTag.contains(ModWeaponTypeReloadListener.DEFAULT)) {
      return compoundTag.getString(ModWeaponTypeReloadListener.DEFAULT);
    }
    return original.call(compoundTag, key);
  }

  /**
   * 应用先天技能的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;innateSkill(Lyesman/epicfight/world/capabilities/item/Style;Ljava/util/function/Function;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$innateSkills$applyPredicates(
    ResourceLocation resourceLocation,
    CompoundTag configTag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "innateSkillsTag") CompoundTag innateSkillsTag,
    @Local(name = "style") Style style,
    @Local(name = "skill") Skill skill
  ) {
    Tag casesTag = innateSkillsTag.get("cases");
    if (!(casesTag instanceof CompoundTag casesCompoundTag)) {
      return;
    }

    IWeaponCapability$Builder.of(builder).imaginarycraft$innateSkill(style, itemstack -> skill, ConditionalEntryParser.parseCases(casesCompoundTag,
      (a) -> {
        Skill skill1 = EpicFightRegistries.SKILL.get(ResourceLocation.parse(a));
        return itemstack -> skill1;
      },
      (a, b) -> a != null));
  }
  //endregion

  // region ==================== livingmotion_modifier - 运动动画 ====================

  /**
   * 包裹运动动画配置键的解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 3,
      target = "Lnet/minecraft/nbt/CompoundTag;getAllKeys()Ljava/util/Set;"))
  private static Set<String> imaginarycraft$livingmotionModifier$wrapConfigKeys(
    CompoundTag instance,
    Operation<Set<String>> original
  ) {
    var call = original.call(instance);
    if (call.contains(ModWeaponTypeReloadListener.DEFAULT)) {
      return instance.getCompound(ModWeaponTypeReloadListener.DEFAULT).getAllKeys();
    }

    return call;
  }

  /**
   * 包裹运动动画 ID 的解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 10,
      target = "Lnet/minecraft/nbt/CompoundTag;getString(Ljava/lang/String;)Ljava/lang/String;"))
  private static String imaginarycraft$livingmotionModifier$wrapAnimationId(
    CompoundTag compoundTag,
    String key,
    Operation<String> original
  ) {
    var tag1 = compoundTag.get(ModWeaponTypeReloadListener.DEFAULT);
    if (!(tag1 instanceof CompoundTag compTag)) {
      return original.call(compoundTag, key);
    }

    Tag tag = compTag.get(key);
    if (tag instanceof CompoundTag tagCompound) {
      return tagCompound.getString(ModWeaponTypeReloadListener.DEFAULT);
    }
    return tag.getAsString();
  }

  /**
   * 应用运动动画的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;livingMotionModifier(Lyesman/epicfight/world/capabilities/item/Style;Lyesman/epicfight/api/animation/LivingMotion;Lyesman/epicfight/api/animation/AnimationManager$AnimationAccessor;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$livingmotionModifier$applyPredicates(
    ResourceLocation resourceLocation,
    CompoundTag configTag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "styleAnimationTag") CompoundTag styleAnimationTag,
    @Local(name = "sLivingmotion") String sLivingmotion,
    @Local(name = "livingmotion") LivingMotion livingmotion,
    @Local(name = "animation") AnimationManager.AnimationAccessor<? extends AttackAnimation> animation,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "style") Style style
  ) {
    Tag motionTag = styleAnimationTag.get(sLivingmotion);
    if (!(motionTag instanceof CompoundTag motionCompoundTag)) {
      return;
    }

    IWeaponCapability$Builder.of(builder).imaginarycraft$livingMotionModifier(style, livingmotion, animation, ConditionalEntryParser.parseCases(
      motionCompoundTag, (animId) -> AnimationComboParser.getAnimationAccessor(resourceLocation, extraEntryProvider, animId),
      (a, b) -> false));
  }
  //endregion
}
