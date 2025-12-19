package ctn.imaginarycraft.client.util;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class PlayerAnimUtil {
  /**
   * 待机
   */
  public static final ResourceLocation STANDBY = ImaginaryCraft.modRl("standby");
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

  public static void play(@NotNull Player player, @NotNull ResourceLocation id, Consumer<PlayerAnimationController> consumer) {
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
//      controller.replaceAnimationWithFade();
//      PlayerRawAnimationBuilder

  }
}
