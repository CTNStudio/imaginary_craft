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

@SuppressWarnings({"LoggingSimilarMessage", "UnusedReturnValue"})
public final class PlayerAnimUtil {
  //region 控制器id
  /**
   * 头部旋转
   */
  public static final ResourceLocation HEAD_ROTATION = ImaginaryCraft.modRl("head_rotation");
  /**
   * 待机和行走
   */
  public static final ResourceLocation STANDBY_OR_WALK = ImaginaryCraft.modRl("standby_or_walk");
  /**
   * 常态
   */
  public static final ResourceLocation NORMAL_STATE = ImaginaryCraft.modRl("normal_state");
  //endregion

  //region 停止动画
  /**
   * 停止动画
   */
  public static void stop(Player player, ResourceLocation controllerId) {
    if (player instanceof ServerPlayer serverPlayer) {
      stopServer(serverPlayer, controllerId);
      return;
    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
      stopClient(clientPlayer, controllerId);
      return;
    }
    throw new UnsupportedOperationException("Not implemented");
  }

  public static void stopServer(ServerPlayer serverPlayer, ResourceLocation controllerId) {
    PayloadUtil.sendToClient(serverPlayer, new PlayerStopAnimationPayload(controllerId));
  }

  public static void stopClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId) {
    PlayerAnimationController controller = getPlayerAnimationController(clientPlayer, controllerId);
    if (controller == null) {
      ImaginaryCraft.LOGGER.warn("PlayerAnimationController not found: {}", controllerId);
      return;
    }
    controller.stop();
  }
  //endregion

  //region 播放动画
  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId) {
    playAnimation(player, controllerId, animationId, -1);
  }

  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   float playTime) {
    playAnimation(player, controllerId, animationId, 0, playTime);
  }

  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   float startAnimFrom, float playTime) {
    playAnimation(player, controllerId, animationId, startAnimFrom, playTime, null);
  }

  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   float playTime,
                                   @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    playAnimation(player, controllerId, animationId, 0, playTime, playerAnimStandardFade);
  }

  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    playAnimation(player, controllerId, animationId, 0, -1, playerAnimStandardFade);
  }

  /**
   * 播放动画
   */
  public static void playAnimation(Player player, ResourceLocation controllerId, ResourceLocation animationId,
                                   float startAnimFrom, float playTime,
                                   @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    if (player instanceof ServerPlayer serverPlayer) {
      playAnimationServer(serverPlayer, controllerId, animationId, startAnimFrom, playTime, playerAnimStandardFade);
      return;
    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
      playAnimationClient(clientPlayer, controllerId, animationId, startAnimFrom, playTime, playerAnimStandardFade);
      return;
    }
    throw new UnsupportedOperationException("Not implemented");
  }


  public static void playAnimationServer(ServerPlayer serverPlayer, ResourceLocation controller,
                                         ResourceLocation animationId, float startAnimFrom, float playTime,
                                         @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    PayloadUtil.sendToClient(serverPlayer, new PlayerAnimationPayload(controller, animationId, startAnimFrom, playTime, withFade));
  }

  public static void playAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                         ResourceLocation animationId, float startAnimFrom, float playTime,
                                         @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    playAnimationClient(clientPlayer, controllerId, animationId, startAnimFrom, playTime, playerAnimStandardFade != null ? playerAnimStandardFade.toModifier() : null);
  }

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
   * 播放动画
   */
  public static void playRawAnimation(Player player, ResourceLocation controllerId,
                                      PlayerAnimRawAnimation rawAnimation, float startAnimFrom,
                                      @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    if (player instanceof ServerPlayer serverPlayer) {
      playRawAnimationServer(serverPlayer, controllerId, rawAnimation, startAnimFrom, playerAnimStandardFade);
      return;
    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
      playRawAnimationClient(clientPlayer, controllerId, rawAnimation, startAnimFrom, playerAnimStandardFade);
      return;
    }
    throw new UnsupportedOperationException("Not implemented");
  }

  public static void playRawAnimationServer(ServerPlayer serverPlayer, ResourceLocation controllerId,
                                            PlayerAnimRawAnimation rawAnimation, float startAnimFrom,
                                            @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    PayloadUtil.sendToClient(serverPlayer, new PlayerRawAnimationPayload(controllerId, rawAnimation, startAnimFrom, withFade));
  }

  public static void playRawAnimationClient(AbstractClientPlayer clientPlayer, ResourceLocation controllerId,
                                            PlayerAnimRawAnimation rawAnimation, float startAnimFrom,
                                            @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    playRawAnimationClient(clientPlayer, controllerId, rawAnimation, startAnimFrom, playerAnimStandardFade != null ? playerAnimStandardFade.toModifier() : null);
  }

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
    if (abstractFadeModifier != null) {
      controller.replaceAnimationWithFade(abstractFadeModifier, animation, true);
      return;
    }
    controller.triggerAnimation(animation, startAnimFrom);
  }

  //endregion

  //region 其他工具
  @Nullable
  public static PlayerAnimationController getPlayerAnimationController(@NotNull AbstractClientPlayer player, @NotNull ResourceLocation id) {
    return (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(player, id);
  }

  /**
   * 是否可播放动画
   */
  public static boolean isExecutableAnimation(AnimationController controller, ResourceLocation animationId) {
    return isExecutableAnimation(controller, PlayerAnimResources.getAnimation(animationId));
  }

  /**
   * 是否可播放动画
   */
  public static boolean isExecutableAnimation(AnimationController controller, Animation animation) {
    return animation != null && !isSameAnimation(controller.getCurrentAnimationInstance(), animation);
  }

  /**
   * 是否是同一动画
   */
  public static boolean isSameAnimation(Animation animation, Animation animation1) {
    return animation != null && animation1 != null && animation.uuid().equals(animation1.uuid());
  }

  public static AbstractFadeModifier getDefaultAbstractFadeModifier() {
    return AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE);
  }

  public static PlayerAnimStandardFadePlayerAnim getDefaultStandardFade() {
    return new PlayerAnimStandardFadePlayerAnim(3, EasingType.EASE_IN_OUT_SINE, null, FadeType.FADE_IN);
  }
  //endregion
}
