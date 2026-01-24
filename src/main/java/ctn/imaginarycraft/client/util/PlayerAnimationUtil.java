package ctn.imaginarycraft.client.util;

import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.MirrorModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.SpeedModifier;
import com.zigythebird.playeranimcore.api.firstPerson.FirstPersonConfiguration;
import com.zigythebird.playeranimcore.easing.EasingType;
import com.zigythebird.playeranimcore.enums.FadeType;
import ctn.imaginarycraft.mixed.client.IAnimationController;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimRawAnimation;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimStandardFadePlayerAnim;
import ctn.imaginarycraft.common.payload.animation.PlayerAnimationPayload;
import ctn.imaginarycraft.common.payload.animation.PlayerRawAnimationPayload;
import ctn.imaginarycraft.common.payload.animation.PlayerStopAnimationPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO 制作可以同时淡入淡出的控制器

/**
 * 玩家动画工具类，提供播放、停止动画以及获取动画控制器等方法
 * 支持在客户端和服务端之间同步动画状态
 */
@SuppressWarnings({"LoggingSimilarMessage", "UnusedReturnValue"})
public final class PlayerAnimationUtil {
  public static final FirstPersonConfiguration DEFAULT_FIRST_PERSON_CONFIG = new FirstPersonConfiguration(true, true, true, true);

  //region 控制器id
  /**
   * 待机和行走控制器ID
   */
  public static final ResourceLocation STANDBY_OR_WALK = ImaginaryCraft.modRl("standby_or_walk");
  /**
   * 常态控制器ID
   */
  public static final ResourceLocation NORMAL_STATE = ImaginaryCraft.modRl("normal_state");
  /**
   * 武器控制器ID
   */
  public static final ResourceLocation WEAPON_STATE = ImaginaryCraft.modRl("weapon_state");
  /**
   * 左手
   */
  public static final ResourceLocation LEFT_HAND = ImaginaryCraft.modRl("left_hand");
  /**
   * 右手
   */
  public static final ResourceLocation RIGHT_HAND = ImaginaryCraft.modRl("right_hand");
  /**
   * 头部旋转控制器ID
   */
  public static final ResourceLocation HEAD_ROTATION = ImaginaryCraft.modRl("head_rotation");
  //endregion

  public static final PlayerAnimStandardFadePlayerAnim DEFAULT_FADE_IN = new PlayerAnimStandardFadePlayerAnim(3, EasingType.EASE_IN_OUT_SINE, null, FadeType.FADE_IN);

  public static final PlayerAnimStandardFadePlayerAnim DEFAULT_FADE_OUT = new PlayerAnimStandardFadePlayerAnim(3, EasingType.EASE_IN_OUT_SINE, null, FadeType.FADE_OUT);

  //region 停止动画

  /**
   * 停止指定控制器的动画
   *
   * @param player       玩家对象
   * @param controllerId 控制器ID
   */
  public static void stop(Player player, ResourceLocation controllerId) {
    stop(player, controllerId, false);
  }

  /**
   * 停止指定控制器的动画
   *
   * @param player                   玩家对象
   * @param controllerId             控制器ID
   * @param isStopTriggeredAnimation 是否停止触发的动画
   */
  public static void stop(Player player, ResourceLocation controllerId, boolean isStopTriggeredAnimation) {
    if (player instanceof ServerPlayer serverPlayer) {
      PayloadUtil.sendToClient(serverPlayer, new PlayerStopAnimationPayload(serverPlayer, controllerId, isStopTriggeredAnimation));
      return;
    }

    if (player instanceof AbstractClientPlayer clientPlayer) {
      PayloadUtil.sendToServer(new PlayerStopAnimationPayload(clientPlayer, controllerId, isStopTriggeredAnimation));
      return;
    }

    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * 在客户端停止指定控制器的动画
   *
   * @param clientPlayer             客户端玩家对象
   * @param controllerId             控制器ID
   * @param isStopTriggeredAnimation 是否停止触发的动画
   */
  public static void stopClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId, boolean isStopTriggeredAnimation) {
    PlayerAnimationController controller = getPlayerAnimationController(clientPlayer, controllerId);
    if (controller == null) {
      ImaginaryCraft.LOGGER.warn("PlayerAnimationController not found: {}", controllerId);
      return;
    }

    if (isStopTriggeredAnimation) {
      controller.stopTriggeredAnimation();
    } else {
      controller.stop();
    }
  }
  //endregion

  //region 播放动画

  /**
   * 播放指定动画
   *
   * @param player  玩家对象
   * @param builder 动画参数构建器
   */
  public static void playAnimation(Player player, PlayerAnimationPayload.Builder builder) {
    if (player instanceof ServerPlayer serverPlayer) {
      PayloadUtil.sendToClient(serverPlayer, builder.playPlayerUUID(serverPlayer).build());
      return;
    }

    if (player instanceof AbstractClientPlayer clientPlayer) {
      PayloadUtil.sendToServer(builder.playPlayerUUID(clientPlayer).build());
      return;
    }

    throw new UnsupportedOperationException("Not implemented");
  }

  public static void playAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                         ResourceLocation animationId,
                                         @Nullable AbstractFadeModifier abstractFadeModifier) {
    playAnimationClient(clientPlayer, controllerId, animationId, 0, 1, abstractFadeModifier, false);
  }

  /**
   * 在客户端播放动画
   *
   * @param clientPlayer           客户端玩家对象
   * @param controllerId           控制器ID
   * @param animationId            动画ID
   * @param startAnimFrom          动画开始位置
   * @param playSpeed              播放速度
   * @param playerAnimStandardFade 播放动画的标准淡入淡出效果
   */
  public static void playAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                         ResourceLocation animationId, float startAnimFrom, float playSpeed,
                                         @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade, boolean reverse) {
    playAnimationClient(clientPlayer, controllerId, animationId, startAnimFrom, playSpeed, playerAnimStandardFade != null ? playerAnimStandardFade.toModifier() : null, reverse);
  }

  /**
   * 在客户端播放动画
   *
   * @param clientPlayer         客户端玩家对象
   * @param controllerId         控制器ID
   * @param animationId          动画ID
   * @param startAnimFrom        动画开始位置
   * @param playSpeed            播放速度
   * @param abstractFadeModifier 抽象淡入淡出修饰符
   */
  public static void playAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                         ResourceLocation animationId, float startAnimFrom, float playSpeed,
                                         @Nullable AbstractFadeModifier abstractFadeModifier, boolean reverse) {
    PlayerAnimationController controller = getPlayerAnimationController(clientPlayer, controllerId);
    if (controller == null) {
      ImaginaryCraft.LOGGER.warn("PlayerAnimationController not found: {}", controllerId);
      return;
    }

    if (!PlayerAnimResources.hasAnimation(animationId)) {
      return;
    }

    controller.removeModifierIf(AbstractFadeModifier.class::isInstance);
    controller.removeModifierIf(SpeedModifier.class::isInstance);
    controller.removeModifierIf(MirrorModifier.class::isInstance);
    if (reverse) {
      controller.addModifierLast(new MirrorModifier());
    }
    controller.addModifierLast(new SpeedModifier(playSpeed));
    IAnimationController.of(controller).imaginarycraft$linkModifiers();
    if (abstractFadeModifier == null) {
      controller.triggerAnimation(animationId, startAnimFrom);
    } else {
      controller.replaceAnimationWithFade(abstractFadeModifier, animationId, true);
    }
  }

  /**
   * 播放原始动画
   *
   * @param player  玩家对象
   * @param builder 播放原始动画的构建器
   */
  public static void playRawAnimation(Player player, PlayerRawAnimationPayload.Builder builder) {
    if (player instanceof ServerPlayer serverPlayer) {
      PayloadUtil.sendToClient(serverPlayer, builder.playPlayerUUID(serverPlayer).build());
      return;
    }

    if (player instanceof AbstractClientPlayer clientPlayer) {
      PayloadUtil.sendToServer(builder.playPlayerUUID(clientPlayer).build());
      return;
    }

    throw new UnsupportedOperationException("Not implemented");
  }

  public static void playRawAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                            PlayerAnimRawAnimation rawAnimation,
                                            @Nullable AbstractFadeModifier abstractFadeModifier) {
    playRawAnimationClient(clientPlayer, controllerId, rawAnimation, 0, 1, abstractFadeModifier, false);
  }

  /**
   * 在客户端播放原始动画
   *
   * @param clientPlayer           客户端玩家对象
   * @param controllerId           控制器ID
   * @param rawAnimation           原始动画对象
   * @param startAnimFrom          动画开始位置
   * @param playSpeed              播放速度
   * @param playerAnimStandardFade 播放动画的标准淡入淡出效果
   */
  public static void playRawAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                            PlayerAnimRawAnimation rawAnimation, float startAnimFrom, float playSpeed,
                                            @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade, boolean reverse) {
    playRawAnimationClient(clientPlayer, controllerId, rawAnimation, startAnimFrom, playSpeed, playerAnimStandardFade != null ? playerAnimStandardFade.toModifier() : null, reverse);
  }

  /**
   * 在客户端播放原始动画
   *
   * @param clientPlayer         客户端玩家对象
   * @param controllerId         控制器ID
   * @param rawAnimation         原始动画对象
   * @param startAnimFrom        动画开始位置
   * @param playSpeed            播放速度
   * @param abstractFadeModifier 抽象淡入淡出修饰符
   */
  public static void playRawAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                            PlayerAnimRawAnimation rawAnimation, float startAnimFrom, float playSpeed,
                                            @Nullable AbstractFadeModifier abstractFadeModifier, boolean reverse) {
    playRawAnimationClient(clientPlayer, controllerId, rawAnimation.toRawAnimation(), startAnimFrom, playSpeed, abstractFadeModifier, reverse);
  }

  /**
   * 在客户端播放原始动画
   *
   * @param clientPlayer         玩家对象
   * @param controllerId         控制器ID
   * @param rawAnimation         原始动画对象
   * @param startAnimFrom        动画开始位置
   * @param playSpeed            播放速度
   * @param abstractFadeModifier 抽象淡入淡出修饰符
   */
  public static void playRawAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                            RawAnimation rawAnimation, float startAnimFrom, float playSpeed,
                                            @Nullable AbstractFadeModifier abstractFadeModifier, boolean reverse) {
    PlayerAnimationController controller = getPlayerAnimationController(clientPlayer, controllerId);
    if (controller == null) {
      ImaginaryCraft.LOGGER.warn("PlayerAnimationController not found: {}", controllerId);
      return;
    }

    controller.removeModifierIf(AbstractFadeModifier.class::isInstance);
    controller.removeModifierIf(SpeedModifier.class::isInstance);
    controller.removeModifierIf(MirrorModifier.class::isInstance);
    if (reverse) {
      controller.addModifierLast(new MirrorModifier());
    }
    controller.addModifierLast(new SpeedModifier(playSpeed));
    IAnimationController.of(controller).imaginarycraft$linkModifiers();
    if (abstractFadeModifier == null) {
      controller.triggerAnimation(rawAnimation, startAnimFrom);
    } else {
      controller.replaceAnimationWithFade(abstractFadeModifier, rawAnimation, true);
    }
  }

  //endregion

  //region 其他工具

  /**
   * 获取玩家动画控制器
   *
   * @param player 客户端玩家对象
   * @param id     控制器ID
   * @return 玩家动画控制器
   */
  @Nullable
  public static PlayerAnimationController getPlayerAnimationController(@NotNull AbstractClientPlayer player, @NotNull ResourceLocation id) {
    return (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(player, id);
  }

  /**
   * 判断是否可以播放动画
   *
   * @param controller  动画控制器
   * @param animationId 动画ID
   * @return 如果可以播放动画则返回true，否则返回false
   */
  public static boolean isExecutableAnimation(AnimationController controller, ResourceLocation animationId) {
    return isExecutableAnimation(controller, PlayerAnimResources.getAnimation(animationId));
  }

  /**
   * 判断是否可以播放动画
   *
   * @param controller 动画控制器
   * @param animation  动画对象
   * @return 如果可以播放动画则返回true，否则返回false
   */
  public static boolean isExecutableAnimation(AnimationController controller, Animation animation) {
    return animation != null && !isSameAnimation(controller.getCurrentAnimationInstance(), animation);
  }

  /**
   * 判断是否是同一动画
   *
   * @param animation  第一个动画对象
   * @param animation1 第二个动画对象
   * @return 如果是同一动画则返回true，否则返回false
   */
  public static boolean isSameAnimation(Animation animation, Animation animation1) {
    return animation != null && animation1 != null && animation.uuid().equals(animation1.uuid());
  }

  /**
   * 判断是否是同一动画
   *
   * @param animation   第一个动画对象
   * @param animationId 第二个动画对象
   * @return 如果是同一动画则返回true，否则返回false
   */
  public static boolean isSameAnimation(Animation animation, ResourceLocation animationId) {
    return isSameAnimation(animation, PlayerAnimResources.getAnimation(animationId));
  }
  //endregion
}
