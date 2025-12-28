package ctn.imaginarycraft.util;

import ctn.imaginarycraft.init.ModAttachments;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public final class ChargeUpUtil {
  public static int getValue(Player player) {
    return player.getData(ModAttachments.CHARGE_UP);
  }

  public static void setValue(Player player, int value) {
    player.setData(ModAttachments.CHARGE_UP, value);
  }

  public static void reset(Player player) {
    setValue(player, 0);
  }

  public static float getPercentage(Player player) {
    return Mth.clamp(getValue(player) / player.getCurrentItemAttackStrengthDelay(), 0, 1);
  }

  public static void setPercentage(Player player, float value) {
    setValue(player, (int) Mth.clamp(value * player.getCurrentItemAttackStrengthDelay(), 0, 1));
  }
}
