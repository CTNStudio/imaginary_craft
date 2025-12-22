package ctn.imaginarycraft.client.util;

import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class PlayerAnimUtil {
  /**
   * 头部旋转
   */
  public static final ResourceLocation HEAD_ROTATION = ImaginaryCraft.modRl("head_rotation");
  /**
   * 待机
   */
  public static final ResourceLocation STANDBY = ImaginaryCraft.modRl("standby");
  /**
   * 行走
   */
  public static final ResourceLocation WALK = ImaginaryCraft.modRl("walk");
  /**
   * 攻击待机
   */
  public static final ResourceLocation ATTACK_STANDBY = ImaginaryCraft.modRl("attack_standby");
  /**
   * 攻击
   */
  public static final ResourceLocation ATTACK = ImaginaryCraft.modRl("attack");

  @Nullable
  public static PlayerAnimationController getPlayerAnimationController(@NotNull AbstractClientPlayer player, @NotNull ResourceLocation id) {
    return (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(player, id);
  }

  public static void controller(@NotNull Player player, @NotNull ResourceLocation id, Consumer<PlayerAnimationController> consumer) {
    if (!(player instanceof AbstractClientPlayer clientPlayer)) {
      return;
    }
    PlayerAnimationController controller = getPlayerAnimationController(clientPlayer, id);
    if (controller == null) {
      return;
    }
    consumer.accept(controller);
  }

  /**
   * 停止动画
   */
  public static void stop(Player player, ResourceLocation animationID) {
  }

  /**
   * 播放动画
   */
  public static void play(Player player, ResourceLocation controllerID, ResourceLocation animationID) {
    if (!(player instanceof AbstractClientPlayer clientPlayer)) {
      // TODO 处理同步
      return;
    }
    PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(clientPlayer, controllerID);
    if (controller == null) {
      ImaginaryCraft.LOGGER.warn("PlayerAnimationController not found: {}", controllerID);
      return;
    }
    controller.triggerAnimation(animationID);
  }


  public static boolean playItemAnimation(ItemStack toItemStack, PlayerAnimationController standbyController, ResourceLocation playAnimationId, Item... items) {
    for (Item item : items) {
      if (!toItemStack.is(item)) {
        continue;
      }
      return playStandbyAnimation(standbyController, playAnimationId);
    }
    return false;
  }

  public static boolean playStandbyAnimation(PlayerAnimationController controller, ResourceLocation playAnimationId) {
    return !isExecutableAnimation(controller, playAnimationId) &&
      controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(1, EasingType.LINEAR), playAnimationId, true);
  }

  public static boolean isExecutableAnimation(AnimationController controller, ResourceLocation animationId) {
    return isExecutableAnimation(controller, PlayerAnimResources.getAnimation(animationId));
  }

  public static boolean isExecutableAnimation(AnimationController controller, Animation animation) {
    return animation != null &&
      !isSameAnimation(controller.getCurrentAnimationInstance(), animation);
  }

  public static boolean isSameAnimation(Animation animation, Animation animation1) {
    return animation != null &&
      animation1 != null &&
      animation.uuid().equals(animation1.uuid());
  }

  @SafeVarargs
  public static boolean is(ItemStack item, Supplier<? extends Item>... items) {
    return Arrays.stream(items).anyMatch(i -> i.get() == item.getItem());
  }

  /**
   * 动画集合
   */
  @SuppressWarnings("UnusedReturnValue")
  public record AnimCollection(ResourceLocation standby, ResourceLocation move,
                               Supplier<? extends Item>... items) {
    @SafeVarargs
    public AnimCollection {
    }

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
