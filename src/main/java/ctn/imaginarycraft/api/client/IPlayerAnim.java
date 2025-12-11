package ctn.imaginarycraft.api.client;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.CheckForNull;

public interface IPlayerAnim {
  /**
   * 停止动画
   */
  static void stopAnimation(Level level, Player player, String animName) {
    if (level instanceof ServerLevel serverLevel) {
//			PlayerAnimAPI.stopPlayerAnim(serverLevel, player, getAnimationID(animName));
      return;
    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
//			PlayerAnimAPIClient.stopPlayerAnim(clientPlayer, getAnimationID(animName));
    }
  }

  /**
   * 播放动画
   */
  static void playAnimation(Level level, Player player, String animName) {
//		if (level instanceof ServerLevel serverLevel) {
//			PlayerAnimAPI.playPlayerAnim(serverLevel, player, getAnimationID(animName));
//			return;
//		}
//		if (player instanceof AbstractClientPlayer clientPlayer) {
//			PlayerAnimAPIClient.playPlayerAnim(clientPlayer, getAnimationID(animName));
//		}
  }

//  static @NotNull ResourceLocation getAnimationID(String animName) {
////		return ImaginaryCraft.modRl(animName);
//  }

  /**
   * 强制打断
   */
  void forcedInterruption(Level level, Player player);

  /**
   * 进入攻击状态
   */
  default void enterAttackState(Level level, Player player) {
//		enterAttackState(level, player, null);
  }

  /**
   * 进入攻击状态并播放动画
   */
  default void enterAttackState(Level level, Player player, @CheckForNull String attackAnimName) {
//		CompoundTag nbt = player.getPersistentData();
//    // TODO
////		nbt.putBoolean(PLAYER_USE_ITEM, true);
////		nbt.putBoolean(PLAYER_ATTACK, true);
//		if (attackAnimName == null) {
//			return;
//		}
//    PlayerRawAnimationBuilder;
//    PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(
//      player, RegistrarPlayAnimations.STANDBY);
//    controller.triggerAnimation(animationID);
//		if (level instanceof ServerLevel serverLevel) {
//			PlayerAnimAPI.playPlayerAnim(serverLevel, player, getAnimationID(attackAnimName), PlayerParts.allExceptHeadRot(), null, 2000);
//		}
  }
}
