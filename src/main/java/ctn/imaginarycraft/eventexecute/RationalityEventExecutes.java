package ctn.imaginarycraft.eventexecute;

import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.util.RationalityUtil;
import net.minecraft.server.level.ServerPlayer;

public final class RationalityEventExecutes {
  /**
   * 自然恢复理智值
   */
  public static void refreshRationalityValue(ServerPlayer player) {
    if (!ModConfig.SERVER.enableNaturalRationalityRationality.get()) {
      return;
    }

    int recoveryTick = RationalityUtil.getPauseRecoveryTick(player);
    boolean isRecovery = recoveryTick > 0;

    if (isRecovery) {
      RationalityUtil.setRecoveryTick(player, recoveryTick - 1);
      return;
    }

    float value = RationalityUtil.getValue(player);
    if (value < 0 || value >= RationalityUtil.getMaxValue(player)) {
      return;
    }

    float recoveryAmount = RationalityUtil.getRationalityRecoveryAmount(player);
    if (recoveryAmount > 0) {
      RationalityUtil.modifyValue(player, recoveryAmount, true);
    }

    float naturalRecoveryRate = RationalityUtil.getNaturalRecoveryRate(player);
    if (naturalRecoveryRate > 0) {
      RationalityUtil.setRecoveryTick(player, (int) (20 * naturalRecoveryRate));
    }
  }
}
