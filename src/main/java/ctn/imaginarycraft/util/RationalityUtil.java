package ctn.imaginarycraft.util;

import ctn.imaginarycraft.core.ModEventHooks;
import ctn.imaginarycraft.init.ModAttachments;
import ctn.imaginarycraft.init.world.ModAttributes;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

/**
 * 理智工具
 */
public final class RationalityUtil {
  /**
   * 修改理智值
   */
  public static void modifyValue(Player player, float value, final boolean isEvent) {
    setValue(player, getValue(player) + value, isEvent);
  }

  /**
   * 限制理智
   */
  public static void restrictValue(Player player, final boolean isEvent) {
    restrictValue(player, getValue(player), isEvent);
  }

  /**
   * 限制理智
   */
  public static void restrictValue(Player player, float value, final boolean isEvent) {
    float maxRationalityValue = getMaxValue(player);
    setValue(player, Math.clamp(value, -maxRationalityValue, maxRationalityValue), isEvent);
  }

  /**
   * 获取理智值
   */
  public static float getValue(Player player) {
    float maxRationalityValue = getMaxValue(player);
    return Math.clamp(player.getData(ModAttachments.RATIONALITY), -maxRationalityValue, maxRationalityValue);
  }

  /**
   * 设置理智值
   */
  public static void setValue(final Player player, final float value, final boolean isEvent) {
    float oldValue = getValue(player);
    float newValue = value;
    if (isEvent) {
      Map.Entry<Boolean, Float> pre = ModEventHooks.sourceRationalityPre(player, oldValue, value);
      newValue = pre.getValue();
      if (pre.getKey()) {
        return;
      }
    }

    float maxRationalityValue = getMaxValue(player);

    if (oldValue != newValue) {
      player.setData(ModAttachments.RATIONALITY, Math.clamp(newValue, -maxRationalityValue, maxRationalityValue));
    }

    if (isEvent) {
      ModEventHooks.sourceRationalityPost(player, oldValue, newValue);
    }
  }

  /**
   * 获取最大理智值
   */
  public static float getMaxValue(Player player) {
    return (float) player.getAttributeValue(ModAttributes.MAX_RATIONALITY);
  }

  /**
   * 设置基本最大理智值
   */
  public static void setBaseMaxValue(Player player, float value) {
    player.getAttributes().getInstance(ModAttributes.MAX_RATIONALITY).setBaseValue(value);
  }

  /**
   * 获取理智值自然恢复效率
   */
  public static float getNaturalRecoveryRate(Player player) {
    return (float) player.getAttributeValue(ModAttributes.RATIONALITY_NATURAL_RECOVERY_WAIT_TIME);
  }

  /**
   * 设置基本理智值自然恢复效率
   */
  public static void setBaseNaturalRecoveryRate(Player player, float value) {
    player.getAttributes().getInstance(ModAttributes.RATIONALITY_NATURAL_RECOVERY_WAIT_TIME).setBaseValue(value);
  }

  /**
   * 获取理智值自然恢复量
   */
  public static float getRationalityRecoveryAmount(Player player) {
    restrictValue(player, true);
    return (float) player.getAttributeValue(ModAttributes.RATIONALITY_RECOVERY_AMOUNT);
  }

  /**
   * 设置基本理智值自然恢复量
   */
  public static void setBaseRationalityRecoveryAmount(Player player, float value) {
    player.getAttributes().getInstance(ModAttributes.RATIONALITY_RECOVERY_AMOUNT).setBaseValue(value);
  }

  /**
   * 获取暂停恢复理智tick
   */
  public static int getPauseRecoveryTick(Player player) {
    return player.getData(ModAttachments.RATIONALITY_PAUSE_RECOVERY_TICK);
  }

  /**
   * 设置恢复暂停理智tick
   */
  public static void setRecoveryTick(Player player, int value) {
    player.setData(ModAttachments.RATIONALITY_PAUSE_RECOVERY_TICK, value);
  }
}
