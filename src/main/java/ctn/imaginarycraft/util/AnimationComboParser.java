package ctn.imaginarycraft.util;

import ctn.imaginarycraft.api.data.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import org.apache.logging.log4j.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.world.capabilities.entitypatch.*;
import yesman.epicfight.world.capabilities.provider.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * 动画连招解析器
 * <p>负责解析武器配置中的动画和连招数据</p>
 */
public class AnimationComboParser {

  private static final Logger LOGGER = ImaginaryCraft.LOGGER;

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
  public static <T extends StaticAnimation> AnimationManager.AnimationAccessor<? extends T> getAnimationAccessor(
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
      LOGGER.warn("Cannot find animation '{}' in {}", animationId, resourceLocation);
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
  @SuppressWarnings("unchecked")
  public static Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>> parseComboAnimation(
    ResourceLocation resourceLocation,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    Tag styleTag
  ) {
    if (!(styleTag instanceof CompoundTag styleCompoundTag)) {
      var defaultAnim = AnimationComboParser.<AttackAnimation>getAnimationAccessor(
        resourceLocation,
        extraEntryProvider, styleTag.getAsString());
      return entitypatch -> defaultAnim;
    }
    return ConditionalProviderFactory.getProvider(
      AnimationComboParser.<AttackAnimation>getAnimationAccessor(resourceLocation, extraEntryProvider, styleCompoundTag.getString(ModWeaponTypeReloadListener.DEFAULT_TAG)),
      ConditionalEntryParser.parseConditionalEntries(styleCompoundTag,
        valueString -> (AnimationManager.AnimationAccessor<? extends AttackAnimation>) AnimationComboParser.getAnimationAccessor(resourceLocation, extraEntryProvider, valueString),
        (accessor, string) -> accessor != null));
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
  public static List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> parseComboAnimations(
    ResourceLocation resourceLocation,
    @SuppressWarnings("removal") ExtraEntryProvider extraEntryProvider,
    ListTag styleListTag
  ) {
    return styleListTag.stream()
      .map(styleTag -> parseComboAnimation(resourceLocation, extraEntryProvider, styleTag))
      .collect(Collectors.toList());
  }
}
