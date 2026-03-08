package ctn.imaginarycraft.mixin.world.item;

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

/**
 * 武器类型重载监听器 Mixin - 扩展武器能力配置的解析逻辑
 * <p>支持以下 JSON 配置格式：</p>
 *
 * <h2>1. hit_particle - 命中粒子效果配置</h2>
 * <h3>完整格式（支持条件化）：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "hit_particle": {
 *     "default": "epicfight:blood",
 *     "cases": [
 *       {
 *         "value": "minecraft:glow_squid_ink",
 *         "conditions": [
 *           {
 *             "predicate": "epicfight:entity_type",
 *             "entity_type": "minecraft:skeleton"
 *           }
 *         ]
 *       }
 *     ]
 *   }
 * }}</code></pre>
 * <h3>简单格式：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "hit_particle": "epicfight:blood"
 * }}</code></pre>
 *
 * <h2>2. swing_sound - 挥舞声音配置</h2>
 * <h3>完整格式（支持条件化）：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "swing_sound": {
 *     "default": "epicfight:blade_swing",
 *     "cases": [
 *       {
 *         "value": "minecraft:entity.warden.attack.swing",
 *         "conditions": [
 *           {
 *             "predicate": "epicfight:has_skill",
 *             "skill": "imaginarycraft:heavy_weapon"
 *           }
 *         ]
 *       }
 *     ]
 *   }
 * }}</code></pre>
 * <h3>简单格式：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "swing_sound": "epicfight:blade_swing"
 * }}</code></pre>
 *
 * <h2>3. hit_sound - 命中声音配置</h2>
 * <h3>完整格式（支持条件化）：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "hit_sound": {
 *     "default": "epicfight:blade_hit",
 *     "cases": [
 *       {
 *         "value": "minecraft:entity.generic.burn",
 *         "conditions": [
 *           {
 *             "predicate": "epicfight:is_on_fire"
 *           }
 *         ]
 *       }
 *     ]
 *   }
 * }}</code></pre>
 * <h3>简单格式：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "hit_sound": "epicfight:blade_hit"
 * }}</code></pre>
 *
 * <h2>4. combos - 连招配置</h2>
 * <h3>简单数组格式：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "combos": [
 *     "imaginarycraft:slash_1",
 *     "imaginarycraft:slash_2",
 *     "imaginarycraft:stab"
 *   ]
 * }}</code></pre>
 *
 * <h3>带样式分类的格式：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "combos": {
 *     "one_hand": [
 *       "imaginarycraft:slash_1",
 *       "imaginarycraft:slash_2",
 *       "imaginarycraft:stab"
 *     ]
 *   }
 * }}</code></pre>
 *
 * <h3>单个动画的条件化配置：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "combos": {
 *     "one_hand": [
 *       {
 *         "default": "imaginarycraft:slash_1",
 *         "cases": [
 *           {
 *             "value": "imaginarycraft:pierce",
 *             "conditions": [
 *               {
 *                 "predicate": "epicfight:is_sneaking"
 *               }
 *             ]
 *           }
 *         ]
 *       },
 *       "imaginarycraft:slash_2",
 *       "imaginarycraft:stab"
 *     ]
 *   }
 * }}</code></pre>
 *
 * <h3>完整的条件化配置（嵌套）：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "combos": {
 *     "one_hand": {
 *       "default": [
 *         "imaginarycraft:slash_1",
 *         "imaginarycraft:slash_2",
 *         "imaginarycraft:stab"
 *       ],
 *       "cases": [
 *         {
 *           "value": [
 *             {
 *               "default": "imaginarycraft:slash_1",
 *               "cases": [
 *                 {
 *                   "value": "imaginarycraft:pierce",
 *                   "conditions": [
 *                     {
 *                       "predicate": "epicfight:is_sneaking"
 *                     }
 *                   ]
 *                 }
 *               ]
 *             },
 *             "imaginarycraft:slash_2",
 *             "imaginarycraft:stab"
 *           ],
 *           "conditions": [
 *             {
 *               "predicate": "epicfight:has_skill",
 *               "skill": "imaginarycraft:mastery"
 *             }
 *           ]
 *         }
 *       ]
 *     }
 *   }
 * }}</code></pre>
 *
 * <h2>5. innate_skills - 先天技能配置</h2>
 * <h3>完整格式（支持条件化和样式分类）：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "innate_skills": {
 *     "one_hand": {
 *       "default": "epicfight:battle_mastery",
 *       "cases": [
 *         {
 *           "value": "imaginarycraft:weapon_specialist",
 *           "conditions": [
 *             {
 *               "predicate": "epicfight:has_item",
 *               "item": "imaginarycraft:ancient_weapon"
 *             }
 *           ]
 *         }
 *       ]
 *     }
 *   }
 * }}</code></pre>
 * <h3>简单格式：</h3>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "innate_skills": {
 *     "one_hand": "epicfight:battle_mastery"
 *   }
 * }}</code></pre>
 *
 * <h2>6. livingmotion_modifier - 运动动画修饰配置</h2>
 * <pre><code>{@snippet lang = "json":
 * {
 *   "livingmotion_modifier": {
 *     "one_hand": {
 *       "idle": {
 *         "default": "imaginarycraft:idle_combat",
 *         "cases": [
 *           {
 *             "value": "imaginarycraft:idle_berserk",
 *             "conditions": [
 *               {
 *                 "predicate": "epicfight:low_health",
 *                 "threshold": 0.3
 *               }
 *             ]
 *           }
 *         ]
 *       },
 *       "walk": "imaginarycraft:walk_combat"
 *     }
 *   }
 * }}</code></pre>
 *
 * <h2>通用规则说明：</h2>
 * <ul>
 *   <li><strong>default</strong>: 默认值，当没有满足任何条件时使用</li>
 *   <li><strong>cases</strong>: 条件分支数组，按顺序匹配</li>
 *   <li><strong>value</strong>: 条件满足时使用的值</li>
 *   <li><strong>conditions</strong>: 条件列表，包含 predicate 和参数</li>
 *   <li><strong>predicate</strong>: 谓词 ID，用于判断条件是否满足</li>
 *   <li>第一个匹配成功的 case 会被使用，如果没有匹配的 case 则使用 default</li>
 * </ul>
 */
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

  //region collider - 碰撞箱
  //
  //endregion

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

  //region combos - 连招

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
