package ctn.imaginarycraft.eventexecute;

import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.config.ModConfig;
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

    if (RationalityUtil.getValue(player) >= RationalityUtil.getMaxValue(player)) {
      return;
    }

    float recoveryAmount = RationalityUtil.getRationalityRecoveryAmount(player);
    if (recoveryAmount > 0) {
      RationalityUtil.modifyValue(player, recoveryAmount, true);
    }

    int value = (int) (20 * RationalityUtil.getNaturalRecoveryRate(player));
    RationalityUtil.setRecoveryTick(player, value);
  }
}
