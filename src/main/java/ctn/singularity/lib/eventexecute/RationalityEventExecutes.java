package ctn.singularity.lib.eventexecute;

import ctn.singularity.lib.api.lobotomycorporation.util.RationalityUtil;
import ctn.singularity.lib.core.LibConfig;
import ctn.singularity.lib.init.LibAttributes;
import net.minecraft.server.level.ServerPlayer;

public final class RationalityEventExecutes {
  /**
   * 自然恢复理智值
   */
  public static void refreshRationalityValue(ServerPlayer player) {
    if (!LibConfig.SERVER.enableNaturalRationalityRationality.get()) {
      return;
    }

    int recoveryTick = RationalityUtil.getRationalityPauseRecoveryTick(player);
    boolean isRecovery = recoveryTick > 0;

    if (isRecovery) {
      RationalityUtil.setRationalityRecoveryTick(player, recoveryTick - 1);
      return;
    }

    float recoveryAmount = (float) player.getAttributeValue(LibAttributes.RATIONALITY_RECOVERY_AMOUNT);
    if (recoveryAmount > 0) {
      RationalityUtil.modifyRationalityValue(player, recoveryAmount, true);
    }

    int value = (int) (20 * player.getAttributeValue(LibAttributes.RATIONALITY_NATURAL_RECOVERY_RATE));
    RationalityUtil.setRationalityRecoveryTick(player, value);
  }
}
