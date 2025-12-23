package ctn.imaginarycraft.client.util;

import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.animation.layered.modifier.SpeedModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import com.zigythebird.playeranimcore.enums.FadeType;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimStandardFadePlayerAnim;
import ctn.imaginarycraft.common.payloads.player.PlayerAnimationPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings({"LoggingSimilarMessage", "UnusedReturnValue"})
public final class PlayerAnimUtil {
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

  public static void stop(Player player) {
    stop(player, NORMAL_STATE);
  }

  public static void play(Player player, ResourceLocation animationId) {
    play(player, animationId, -1);
  }

  public static void play(Player player, ResourceLocation animationId, float length) {
    play(player, NORMAL_STATE, animationId, length, null);
  }

  public static void play(Player player, ResourceLocation controllerId, ResourceLocation animationId) {
    play(player, controllerId, animationId, -1);
  }

  public static void play(Player player, ResourceLocation controllerId, ResourceLocation animationId, float length) {
    play(player, controllerId, animationId, length, null);
  }

  /**
   * 停止动画
   */
  public static void stop(Player player, ResourceLocation controllerId) {
    if (player instanceof ServerPlayer serverPlayer) {
      PayloadUtil.sendToClient(serverPlayer, new PlayerAnimationPayload(controllerId));
      return;
    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
      clientStop(controllerId, clientPlayer);
      return;
    }
    throw new UnsupportedOperationException("Not implemented");
  }

  /**
   * 播放动画
   */
  public static void play(Player player, ResourceLocation controllerId, ResourceLocation animationId, float length, @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    if (player instanceof ServerPlayer serverPlayer) {
      PayloadUtil.sendToClient(serverPlayer, new PlayerAnimationPayload(controllerId, animationId, length, playerAnimStandardFade));
      return;
    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
      clientPlay(controllerId, animationId, clientPlayer, length, playerAnimStandardFade);
      return;
    }
    throw new UnsupportedOperationException("Not implemented");
  }

  public static void clientStop(ResourceLocation controllerId, AbstractClientPlayer clientPlayer) {
    PlayerAnimationController controller = getPlayerAnimationController(clientPlayer, controllerId);
    if (controller == null) {
      ImaginaryCraft.LOGGER.warn("PlayerAnimationController not found: {}", controllerId);
      return;
    }
    controller.stop();
  }

  public static void clientPlay(ResourceLocation controllerId, ResourceLocation animationId,
                                AbstractClientPlayer clientPlayer, float length, @Nullable PlayerAnimStandardFadePlayerAnim playerAnimStandardFade) {
    clientPlay(controllerId, animationId, clientPlayer, length, playerAnimStandardFade != null ? playerAnimStandardFade.toModifier() : null);
  }

  private static void clientPlay(ResourceLocation controllerId, ResourceLocation animationId, AbstractClientPlayer clientPlayer, float length, @Nullable AbstractFadeModifier abstractFadeModifier) {
    PlayerAnimationController controller = getPlayerAnimationController(clientPlayer, controllerId);
    if (controller == null) {
      ImaginaryCraft.LOGGER.warn("PlayerAnimationController not found: {}", controllerId);
      return;
    }
    if (!PlayerAnimResources.hasAnimation(animationId)) {
      return;
    }
    Animation animation = PlayerAnimResources.getAnimation(animationId);
    if (length > 0) {
      float speed = animation.length() / length;
      controller.getModifiers().stream()
        .filter(SpeedModifier.class::isInstance)
        .map(SpeedModifier.class::cast).findFirst()
        .ifPresentOrElse(modifiers -> modifiers.speed = speed, () -> controller.addModifierLast(new SpeedModifier(speed)));
    }
    if (abstractFadeModifier != null) {
      controller.replaceAnimationWithFade(abstractFadeModifier, animation, true);
      return;
    }
    controller.triggerAnimation(animation);
  }

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

  /**
   * 动画集合
   */
  @SuppressWarnings("unchecked")
  public record AnimCollection(ResourceLocation standby, ResourceLocation move,
                               Supplier<? extends Item>... items) {
    public boolean executeAnim(ItemStack toItemStack, PlayerAnimationController controller,
                               AnimationData state, AnimationController.AnimationSetter animationSetter) {
      for (Supplier<? extends Item> item : items) {
        if (!toItemStack.is(item.get())) {
          continue;
        }
        if (state.isMoving()) {
          movingAnim(controller);
        } else {
          standbyAnim(controller);
        }
        return true;
      }
      return false;
    }

    public void movingAnim(PlayerAnimationController controller) {
      if (PlayerAnimUtil.isExecutableAnimation(controller, move)) {
        controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), move, true);
      }
    }

    public void standbyAnim(PlayerAnimationController controller) {
      if (PlayerAnimUtil.isExecutableAnimation(controller, standby)) {
        controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), standby, true);
      }
    }
  }
}
