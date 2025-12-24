package ctn.imaginarycraft.client.util;

import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.SpeedModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import com.zigythebird.playeranimcore.enums.FadeType;
import ctn.imaginarycraft.api.client.IAnimationController;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimRawAnimation;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimStandardFadePlayerAnim;
import ctn.imaginarycraft.common.payloads.player.PlayerAnimationPayload;
import ctn.imaginarycraft.common.payloads.player.PlayerRawAnimationPayload;
import ctn.imaginarycraft.common.payloads.player.PlayerStopAnimationPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * 玩家动画工具类，提供播放、停止动画以及获取动画控制器等方法
 * 支持在客户端和服务端之间同步动画状态
 */
@SuppressWarnings({"LoggingSimilarMessage", "UnusedReturnValue"})
public final class PlayerAnimUtil {
  //region 控制器id
  /**
   * 头部旋转控制器ID
   */
  public static final ResourceLocation HEAD_ROTATION = ImaginaryCraft.modRl("head_rotation");
  /**
   * 待机和行走控制器ID
   */
  public static final ResourceLocation STANDBY_OR_WALK = ImaginaryCraft.modRl("standby_or_walk");
  /**
   * 常态控制器ID
   */
  public static final ResourceLocation NORMAL_STATE = ImaginaryCraft.modRl("normal_state");
  //endregion

  public static final AbstractFadeModifier DEFAULT_ABSTRACT_FADE_MODIFIER = AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE);
  public static final PlayerAnimStandardFadePlayerAnim DEFAULT_STANDARD_FADE = new PlayerAnimStandardFadePlayerAnim(3, EasingType.EASE_IN_OUT_SINE, null, FadeType.FADE_IN);

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
   * @param player       玩家对象
   * @param controllerId 控制器ID
   * @param animationId  动画ID
   */
  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId) {
    playAnimation(player, controllerId, animationId, -1);
  }

  /**
   * 播放指定动画
   *
   * @param player       玩家对象
   * @param controllerId 控制器ID
   * @param animationId  动画ID
   * @param playTime     播放时间
   */
  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   float playTime) {
    playAnimation(player, controllerId, animationId, 0, playTime);
  }

  /**
   * 播放指定动画
   *
   * @param player        玩家对象
   * @param controllerId  控制器ID
   * @param animationId   动画ID
   * @param startAnimFrom 动画开始位置
   * @param playTime      播放时间
   */
  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   float startAnimFrom, float playTime) {
    playAnimation(player, controllerId, animationId, startAnimFrom, playTime, null);
  }

  /**
   * 播放指定动画
   *
   * @param player                 玩家对象
   * @param controllerId           控制器ID
   * @param animationId            动画ID
   * @param playTime               播放时间
   * @param playerAnimStandardFade 播放动画的标准淡入淡出效果
   */
  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   float playTime,
                                   @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    playAnimation(player, controllerId, animationId, 0, playTime, playerAnimStandardFade);
  }

  /**
   * 播放指定动画
   *
   * @param player                 玩家对象
   * @param controllerId           控制器ID
   * @param animationId            动画ID
   * @param playerAnimStandardFade 播放动画的标准淡入淡出效果
   */
  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    playAnimation(player, controllerId, animationId, 0, -1, playerAnimStandardFade);
  }

  /**
   * 播放指定动画
   *
   * @param player                 玩家对象
   * @param controllerId           控制器ID
   * @param animationId            动画ID
   * @param startAnimFrom          动画开始位置
   * @param playTime               播放时间
   * @param playerAnimStandardFade 播放动画的标准淡入淡出效果
   */
  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   float startAnimFrom, float playTime,
                                   @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    if (player instanceof ServerPlayer serverPlayer) {
      PayloadUtil.sendToClient(serverPlayer, new PlayerAnimationPayload(serverPlayer, controllerId, animationId, startAnimFrom, playTime, playerAnimStandardFade));
      return;
    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
      PayloadUtil.sendToServer(new PlayerAnimationPayload(clientPlayer, controllerId, animationId, startAnimFrom, playTime, playerAnimStandardFade));
      return;
    }
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * 在客户端播放动画
   *
   * @param clientPlayer           客户端玩家对象
   * @param controllerId           控制器ID
   * @param animationId            动画ID
   * @param startAnimFrom          动画开始位置
   * @param playTime               播放时间
   * @param playerAnimStandardFade 播放动画的标准淡入淡出效果
   */
  public static void playAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                         ResourceLocation animationId, float startAnimFrom, float playTime,
                                         @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    playAnimationClient(clientPlayer, controllerId, animationId, startAnimFrom, playTime, playerAnimStandardFade != null ? playerAnimStandardFade.toModifier() : null);
  }

  /**
   * 在客户端播放动画
   *
   * @param clientPlayer         客户端玩家对象
   * @param controllerId         控制器ID
   * @param animationId          动画ID
   * @param startAnimFrom        动画开始位置
   * @param playTime             播放时间
   * @param abstractFadeModifier 抽象淡入淡出修饰符
   */
  public static void playAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                         ResourceLocation animationId, float startAnimFrom, float playTime,
                                         @Nullable AbstractFadeModifier abstractFadeModifier) {
    PlayerAnimationController controller = getPlayerAnimationController(clientPlayer, controllerId);
    if (controller == null) {
      ImaginaryCraft.LOGGER.warn("PlayerAnimationController not found: {}", controllerId);
      return;
    }
    if (!PlayerAnimResources.hasAnimation(animationId)) {
      return;
    }
    Animation animation = PlayerAnimResources.getAnimation(animationId);
    Optional<SpeedModifier> speedModifier = controller.getModifiers().stream()
      .filter(SpeedModifier.class::isInstance)
      .map(SpeedModifier.class::cast).findFirst();
    if (playTime > 0) {
      float speed = animation.length() / playTime;
      speedModifier.ifPresentOrElse(modifiers -> modifiers.speed = speed, () -> controller.addModifierLast(new SpeedModifier(speed)));
    } else {
      speedModifier.ifPresent(modifiers -> modifiers.speed = 1);
    }
    IAnimationController.of(controller).imaginarycraft$linkModifiers();
    if (abstractFadeModifier != null) {
      controller.replaceAnimationWithFade(abstractFadeModifier, animation, true);
      return;
    }
    controller.triggerAnimation(animation, startAnimFrom);
  }

  /**
   * 播放原始动画
   *
   * @param player       玩家对象
   * @param controllerId 控制器ID
   * @param rawAnimation 原始动画对象
   */
  public static void playRawAnimation(Player player, ResourceLocation controllerId,
                                      PlayerAnimRawAnimation rawAnimation) {
    playRawAnimation(player, controllerId, rawAnimation, -1);
  }


  /**
   * 播放原始动画
   *
   * @param player        玩家对象
   * @param controllerId  控制器ID
   * @param rawAnimation  原始动画对象
   * @param startAnimFrom 动画开始位置
   */
  public static void playRawAnimation(Player player, ResourceLocation controllerId,
                                      PlayerAnimRawAnimation rawAnimation, float startAnimFrom) {
    playRawAnimation(player, controllerId, rawAnimation, startAnimFrom, null);
  }

  /**
   * 播放原始动画
   *
   * @param player                 玩家对象
   * @param controllerId           控制器ID
   * @param rawAnimation           原始动画对象
   * @param playerAnimStandardFade 播放动画的标准淡入淡出效果
   */
  public static void playRawAnimation(Player player, ResourceLocation controllerId,
                                      PlayerAnimRawAnimation rawAnimation,
                                      @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    playRawAnimation(player, controllerId, rawAnimation, 0, playerAnimStandardFade);
  }

  /**
   * 播放原始动画
   *
   * @param player                 玩家对象
   * @param controllerId           控制器ID
   * @param rawAnimation           原始动画对象
   * @param startAnimFrom          动画开始位置
   * @param playerAnimStandardFade 播放动画的标准淡入淡出效果
   */
  public static void playRawAnimation(Player player, ResourceLocation controllerId,
                                      PlayerAnimRawAnimation rawAnimation, float startAnimFrom,
                                      @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    if (player instanceof ServerPlayer serverPlayer) {
      PayloadUtil.sendToClient(serverPlayer, new PlayerRawAnimationPayload(serverPlayer, controllerId, rawAnimation, startAnimFrom, playerAnimStandardFade));
      return;
    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
      PayloadUtil.sendToServer(new PlayerRawAnimationPayload(clientPlayer, controllerId, rawAnimation, startAnimFrom, playerAnimStandardFade));
      return;
    }
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * 在客户端播放原始动画
   *
   * @param clientPlayer           客户端玩家对象
   * @param controllerId           控制器ID
   * @param rawAnimation           原始动画对象
   * @param startAnimFrom          动画开始位置
   * @param playerAnimStandardFade 播放动画的标准淡入淡出效果
   */
  public static void playRawAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                            PlayerAnimRawAnimation rawAnimation, float startAnimFrom,
                                            @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    playRawAnimationClient(clientPlayer, controllerId, rawAnimation, startAnimFrom, playerAnimStandardFade != null ? playerAnimStandardFade.toModifier() : null);
  }

  /**
   * 在客户端播放原始动画
   *
   * @param clientPlayer         客户端玩家对象
   * @param controllerId         控制器ID
   * @param rawAnimation         原始动画对象
   * @param startAnimFrom        动画开始位置
   * @param abstractFadeModifier 抽象淡入淡出修饰符
   */
  public static void playRawAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                            PlayerAnimRawAnimation rawAnimation, float startAnimFrom,
                                            @Nullable AbstractFadeModifier abstractFadeModifier) {
    PlayerAnimationController controller = getPlayerAnimationController(clientPlayer, controllerId);
    if (controller == null) {
      ImaginaryCraft.LOGGER.warn("PlayerAnimationController not found: {}", controllerId);
      return;
    }
    IAnimationController.of(controller).imaginarycraft$linkModifiers();
    RawAnimation animation = rawAnimation.toRawAnimation();
    if (abstractFadeModifier == null) {
      controller.triggerAnimation(animation, startAnimFrom);
    } else {
      controller.replaceAnimationWithFade(abstractFadeModifier, animation, true);
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
   * 获取默认的抽象淡入淡出修饰符
   *
   * @return 默认的抽象淡入淡出修饰符
   */
  public static AbstractFadeModifier getDefaultAbstractFadeModifier() {
    return DEFAULT_ABSTRACT_FADE_MODIFIER;
  }

  /**
   * 获取默认的标准淡入淡出效果
   *
   * @return 默认的标准淡入淡出效果
   */
  public static PlayerAnimStandardFadePlayerAnim getDefaultStandardFade() {
    return DEFAULT_STANDARD_FADE;
  }
  //endregion
}
