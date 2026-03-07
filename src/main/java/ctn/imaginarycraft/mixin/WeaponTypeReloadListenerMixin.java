package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.*;
import com.mojang.datafixers.util.*;
import ctn.imaginarycraft.api.data.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.mixed.*;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.particle.*;
import yesman.epicfight.registry.*;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.*;
import yesman.epicfight.world.capabilities.item.*;
import yesman.epicfight.world.capabilities.provider.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@Mixin(WeaponTypeReloadListener.class)
public abstract class WeaponTypeReloadListenerMixin {

  @Unique
  private static final String CONFIG_KEY_HIT_PARTICLE = "hit_particle";
  @Unique
  private static final String CONFIG_KEY_SWING_SOUND = "swing_sound";
  @Unique
  private static final String CONFIG_KEY_HIT_SOUND = "hit_sound";
  @Unique
  private static final String CONFIG_KEY_COMBOS = "combos";
  @Unique
  private static final String DEFAULT_TAG = "default";

  /**
   * 获取动画访问器
   *
   * @param resourceLocation   资源位置
   * @param extraEntryProvider 额外条目提供者
   * @param animationId        动画 ID
   * @return 动画访问器，找不到时返回 null
   */
  @Nullable
  @Unique
  private static <T extends StaticAnimation> AnimationManager.AnimationAccessor<? extends T> imaginarycraft$getAnimationAccessor(
    ResourceLocation resourceLocation,
    @SuppressWarnings("removal") @Nullable ExtraEntryProvider extraEntryProvider,
    String animationId
  ) {
    AnimationManager.AnimationAccessor<? extends T> animation;
    if (extraEntryProvider == null) {
      animation = AnimationManager.byKey(animationId);
    } else {
      //noinspection removal
      animation = extraEntryProvider.getExtraOrBuiltInAnimation(animationId);
    }

    if (animation == null) {
      ImaginaryCraft.LOGGER.warn("Cannot find animation '{}' in {}", animationId, resourceLocation);
    }

    return animation;
  }

  /**
   * 解析单个连招动画（支持条件化配置）
   *
   * @param resourceLocation   资源位置
   * @param extraEntryProvider 额外条目提供者
   * @param styleTag           样式标签
   * @return 条件化动画提供者函数
   */
  @Unique
  private static Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>> imaginarycraft$parseComboAnimation(
    ResourceLocation resourceLocation,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    Tag styleTag
  ) {
    if (styleTag instanceof CompoundTag styleCompoundTag) {
      return ModWeaponTypeReloadListener.getProvider(
        imaginarycraft$getAnimationAccessor(resourceLocation, extraEntryProvider, styleCompoundTag.getString(DEFAULT_TAG)),
        ModWeaponTypeReloadListener.parseConditionalEntries(styleCompoundTag,
          valueString -> WeaponTypeReloadListenerMixin.<AttackAnimation>imaginarycraft$getAnimationAccessor(resourceLocation, extraEntryProvider, valueString),
          (accessor, string) -> accessor != null));
    }

    return ModWeaponTypeReloadListener.getProvider(
      imaginarycraft$getAnimationAccessor(resourceLocation, extraEntryProvider, styleTag.getAsString()),
      new ArrayList<>());
  }

  /**
   * 解析连招动画列表
   *
   * @param resourceLocation   资源位置
   * @param extraEntryProvider 额外条目提供者
   * @param styleListTag       样式列表标签
   * @return 动画提供者函数列表
   */
  @Unique
  private static List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginarycraft$parseComboAnimations(
    ResourceLocation resourceLocation,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    ListTag styleListTag
  ) {
    return styleListTag.stream()
      .map(styleTag -> imaginarycraft$parseComboAnimation(resourceLocation, extraEntryProvider, styleTag))
      .collect(Collectors.toList());
  }

  //region hit_particle - 命中粒子效果

  /**
   * 包裹命中粒子的默认值解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 2,
      target = "Lnet/minecraft/nbt/CompoundTag;getString(Ljava/lang/String;)Ljava/lang/String;"))
  private static String imaginarycraft$wrapHitParticleDefaultValue(
    CompoundTag compoundTag,
    String key,
    Operation<String> original,
    @Local(name = "tag", ordinal = 0, argsOnly = true) CompoundTag configTag
  ) {
    String defaultValue = ModWeaponTypeReloadListener.getConfigDefaultValue(configTag, CONFIG_KEY_HIT_PARTICLE);
    if (defaultValue == null) {
      return original.call(compoundTag, key);
    }
    return defaultValue;
  }

  /**
   * 应用命中粒子的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;hitParticle(Lyesman/epicfight/particle/HitParticleType;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$applyHitParticlePredicates(
    ResourceLocation resourceLocation,
    CompoundTag configTag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "particleType") ParticleType<?> particleType
  ) {
    Tag hitParticleTag = configTag.get(CONFIG_KEY_HIT_PARTICLE);
    if (hitParticleTag instanceof CompoundTag hitParticleCompoundTag) {
      //noinspection unchecked
      IWeaponCapability.IBuilder.of(builder).imaginarycraft$hitParticle(
        (HitParticleType) particleType,
        (List<Pair<Predicate<LivingEntityPatch<?>>, HitParticleType>>) (List<?>) ModWeaponTypeReloadListener.parseConditionalEntriesFromString(
          hitParticleCompoundTag, BuiltInRegistries.PARTICLE_TYPE, (particleType1, string) -> {
            if (particleType1 == null) {
              ImaginaryCraft.LOGGER.warn("Cannot find particle type '{}' in {}", string, resourceLocation);
              return false;
            }
            if (!(particleType1 instanceof HitParticleType)) {
              ImaginaryCraft.LOGGER.warn("'{}' is not a hit particle type in {}", string, resourceLocation);
              return false;
            }
            return true;
          }
        )
      );
    }
  }
  //endregion

  //region swing_sound - 挥舞声音

  /**
   * 包裹挥舞声音的默认值解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 5,
      target = "Lnet/minecraft/nbt/CompoundTag;getString(Ljava/lang/String;)Ljava/lang/String;"))
  private static String imaginarycraft$wrapSwingSoundDefaultValue(
    CompoundTag compoundTag,
    String key,
    Operation<String> original,
    @Local(name = "tag", ordinal = 0, argsOnly = true) CompoundTag configTag
  ) {
    String defaultValue = ModWeaponTypeReloadListener.getConfigDefaultValue(configTag, CONFIG_KEY_SWING_SOUND);
    if (defaultValue == null) {
      return original.call(compoundTag, key);
    }
    return defaultValue;
  }

  /**
   * 应用挥舞声音的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;swingSound(Lnet/minecraft/sounds/SoundEvent;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$applySwingSoundPredicates(
    ResourceLocation resourceLocation,
    CompoundTag configTag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "sound") SoundEvent sound,
    @Share("swingSoundPredicates") LocalRef<List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>>> predicatesShare
  ) {
    Tag swingSoundTag = configTag.get(CONFIG_KEY_SWING_SOUND);
    if (swingSoundTag instanceof CompoundTag swingSoundCompoundTag) {
      IWeaponCapability.IBuilder.of(builder).imaginarycraft$swingSound(sound, ModWeaponTypeReloadListener.parseConditionalEntriesFromString(
        swingSoundCompoundTag, BuiltInRegistries.SOUND_EVENT, (soundEvent, string) -> {
          if (soundEvent == null) {
            ImaginaryCraft.LOGGER.warn("Cannot find swing sound '{}' in {}", string, resourceLocation);
            return false;
          }
          return true;
        })
      );
    }
  }
  //endregion

  //region hit_sound - 命中声音

  /**
   * 包裹命中声音的默认值解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 7,
      target = "Lnet/minecraft/nbt/CompoundTag;getString(Ljava/lang/String;)Ljava/lang/String;"))
  private static String imaginarycraft$wrapHitSoundDefaultValue(
    CompoundTag compoundTag,
    String key,
    Operation<String> original,
    @Local(name = "tag", ordinal = 0, argsOnly = true) CompoundTag configTag
  ) {
    String defaultValue = ModWeaponTypeReloadListener.getConfigDefaultValue(configTag, CONFIG_KEY_HIT_SOUND);
    if (defaultValue == null) {
      return original.call(compoundTag, key);
    }
    return defaultValue;
  }

  /**
   * 应用命中声音的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;hitSound(Lnet/minecraft/sounds/SoundEvent;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$applyHitSoundPredicates(
    ResourceLocation resourceLocation,
    CompoundTag configTag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "sound") SoundEvent sound
  ) {
    Tag hitSoundTag = configTag.get(CONFIG_KEY_HIT_SOUND);
    if (hitSoundTag instanceof CompoundTag hitSoundCompoundTag) {
      IWeaponCapability.IBuilder.of(builder).imaginarycraft$hitSound(sound, ModWeaponTypeReloadListener.parseConditionalEntriesFromString(
        hitSoundCompoundTag, BuiltInRegistries.SOUND_EVENT, (soundEvent, string) -> {
          if (soundEvent == null) {
            ImaginaryCraft.LOGGER.warn("Cannot find hit sound '{}' in {}", string, resourceLocation);
            return false;
          }
          return true;
        })
      );
    }
  }
  //endregion

  //region combos - 连招配置

  /**
   * 包裹连招配置标签的解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 0,
      target = "Lnet/minecraft/nbt/CompoundTag;getList(Ljava/lang/String;I)Lnet/minecraft/nbt/ListTag;"))
  private static ListTag imaginarycraft$wrapCombosTag(
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
    defaultComboListShare.set(new ArrayList<>());
    casesComboListShare.set(new ArrayList<>());

    if (styleTags instanceof ListTag styleListTag) {
      List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> parsedCombos =
        imaginarycraft$parseComboAnimations(resourceLocation, extraEntryProvider, styleListTag);
      if (parsedCombos != null) {
        defaultComboListShare.get().addAll(parsedCombos);
      }
      return original.call(compoundTag, key, tagTypeInt);
    }

    if (!(styleTags instanceof CompoundTag styleCompoundTag)) {
      ImaginaryCraft.LOGGER.warn("Cannot find combos config in {}", resourceLocation);
      return original.call(compoundTag, key, tagTypeInt);
    }

    Tag defaultStyleTag = styleCompoundTag.get(DEFAULT_TAG);
    if (defaultStyleTag == null) {
      ImaginaryCraft.LOGGER.warn("Cannot find default combos config in {}", resourceLocation);
      return original.call(compoundTag, key, tagTypeInt);
    }

    if (defaultStyleTag instanceof ListTag defaultStyleListTag) {
      List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> parsedDefaultCombos =
        imaginarycraft$parseComboAnimations(resourceLocation, extraEntryProvider, defaultStyleListTag);
      if (parsedDefaultCombos != null) {
        defaultComboListShare.get().addAll(parsedDefaultCombos);
      }

      casesComboListShare.get().addAll(ModWeaponTypeReloadListener.parseConditionalEntriesFromTag(
        styleCompoundTag,
        tag -> imaginarycraft$parseComboAnimations(resourceLocation, extraEntryProvider, (ListTag) tag),
        (functions, tag) -> functions != null && !functions.isEmpty()));

      return defaultStyleListTag;
    }

    return original.call(compoundTag, key, tagTypeInt);
  }

  /**
   * 包裹连招动画 ID 的解析，支持条件化配置
   */
  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 0,
      target = "Lnet/minecraft/nbt/ListTag;getString(I)Ljava/lang/String;"))
  private static String imaginarycraft$wrapComboAnimationId(
    ListTag listTag,
    int index,
    Operation<String> original,
    @Local(name = "rl") ResourceLocation resourceLocation
  ) {
    Tag tag = listTag.get(index);
    if (tag instanceof CompoundTag tagCompound) {
      String defaultValue = ModWeaponTypeReloadListener.getConfigDefaultValue(tagCompound);
      if (defaultValue != null) {
        return defaultValue;
      }
      ImaginaryCraft.LOGGER.warn("Cannot find default animation in {}", resourceLocation);
      return "";
    }
    return original.call(listTag, index);
  }

  /**
   * 应用连招的条件谓词到构建器
   */
  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;newStyleCombo(Lyesman/epicfight/world/capabilities/item/Style;[Lyesman/epicfight/api/animation/AnimationManager$AnimationAccessor;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$applyComboPredicates(
    ResourceLocation resourceLocation,
    CompoundTag configTag,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    CallbackInfoReturnable<WeaponCapability.Builder> callbackInfo,
    @Local(name = "builder") WeaponCapability.Builder builder,
    @Local(name = "style") Style style,
    @Share("defaultCombos") LocalRef<List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>> defaultComboListShare,
    @Share("casesCombos") LocalRef<List<Pair<Predicate<LivingEntityPatch<?>>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>>> casesComboListShare
  ) {
    IWeaponCapability.IBuilder.of(builder).imaginarycraft$newStryleCombo(style, defaultComboListShare.get(), casesComboListShare.get());
  }
  //endregion

  //region innate_skills - 先天技能

  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 9,
      target = "Lnet/minecraft/nbt/CompoundTag;getString(Ljava/lang/String;)Ljava/lang/String;"))
  private static String imaginarycraft$wrapInnateSkillsTag(
    CompoundTag compoundTag,
    String key,
    Operation<String> original
  ) {
    if (compoundTag.contains("default")) {
      return compoundTag.getString("default");
    }
    return original.call(compoundTag, key);
  }

  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;innateSkill(Lyesman/epicfight/world/capabilities/item/Style;Ljava/util/function/Function;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$applyInnateSkillsPredicates(
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
    if (casesTag instanceof CompoundTag casesCompoundTag) {
      IWeaponCapability.IBuilder.of(builder).imaginarycraft$innateSkill(style,
        itemstack -> skill,
        ModWeaponTypeReloadListener.parseConditionalEntries(casesCompoundTag,
          (a) -> {
            Skill skill1 = EpicFightRegistries.SKILL.get(ResourceLocation.parse(a));
            return itemstack -> skill1;
          },
          (a, b) -> a != null));
    }
  }
  //endregion

  //region livingmotion_modifier - 运动动画

  @WrapOperation(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE", ordinal = 10,
      target = "Lnet/minecraft/nbt/CompoundTag;getString(Ljava/lang/String;)Ljava/lang/String;"))
  private static String imaginarycraft$wrapLivingmotionModifierTag(
    CompoundTag compoundTag,
    String key,
    Operation<String> original
  ) {
    Tag tag = compoundTag.get(key);
    if (tag instanceof CompoundTag tagCompound) {
      return tagCompound.getString("default");
    }
    return original.call(compoundTag, key);
  }

  @Inject(
    method = "deserializeWeaponCapabilityBuilder(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;",
    at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;livingMotionModifier(Lyesman/epicfight/world/capabilities/item/Style;Lyesman/epicfight/api/animation/LivingMotion;Lyesman/epicfight/api/animation/AnimationManager$AnimationAccessor;)Lyesman/epicfight/world/capabilities/item/WeaponCapability$Builder;"))
  private static void imaginarycraft$applyLivingmotionModifierPredicates(
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
    if (motionTag instanceof CompoundTag motionCompoundTag) {
      IWeaponCapability.IBuilder.of(builder).imaginarycraft$livingMotionModifier(style, livingmotion, animation, ModWeaponTypeReloadListener.parseConditionalEntries(
        motionCompoundTag, (animId) -> imaginarycraft$getAnimationAccessor(resourceLocation, extraEntryProvider, animId),
        (a, b) -> false));
    }
  }
  //endregion
}
