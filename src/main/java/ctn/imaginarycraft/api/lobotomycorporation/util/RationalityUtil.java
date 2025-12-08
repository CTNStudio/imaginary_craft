package ctn.imaginarycraft.api.lobotomycorporation.util;

import ctn.imaginarycraft.core.ModEventHooks;
import ctn.imaginarycraft.init.ModAttachments;
import ctn.imaginarycraft.init.ModAttributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Map;

/**
 * 理智工具
 */
public final class RationalityUtil {
  /**
   * 遭受精神伤害
   * <p>
   * 如果受伤者有理智，则仅减少理智
   */
  public static void handleSpirit(LivingDamageEvent.Pre event, Player player) {
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    modifyValue(player, -event.getNewDamage(), true);
    event.setNewDamage(0);
  }

  /**
   * 修改理智值
   */
  public static void modifyValue(Player player, float value, final boolean isEvent) {
    setValue(player, getValue(player) + value, isEvent);
  }

  /**
   * 限制理智
   */
  public static void restrictValue(Player entity, final boolean isEvent) {
    restrictValue(entity, getValue(entity), isEvent);
  }

  /**
   * 限制理智
   */
  public static void restrictValue(Player entity, float value, final boolean isEvent) {
    float maxRationalityValue = getMaxValue(entity);
    setValue(entity, Math.clamp(value, -maxRationalityValue, maxRationalityValue), isEvent);
  }

  /**
   * 获取理智值
   */
  public static float getValue(Player player) {
    return player.getData(ModAttachments.RATIONALITY);
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
  public static float getMaxValue(Player entity) {
    return (float) entity.getAttributeValue(ModAttributes.MAX_RATIONALITY);
  }

  /**
   * 设置基本最大理智值
   */
  public static void setBaseMaxValue(Player entity, float value) {
    entity.getAttributes().getInstance(ModAttributes.MAX_RATIONALITY).setBaseValue(value);
  }

  /**
   * 获取理智值自然恢复效率
   */
  public static float getNaturalRecoveryRate(Player entity) {
    return (float) entity.getAttributeValue(ModAttributes.RATIONALITY_NATURAL_RECOVERY_WAIT_TIME);
  }

  /**
   * 设置基本理智值自然恢复效率
   */
  public static void setBaseNaturalRecoveryRate(Player entity, float value) {
    entity.getAttributes().getInstance(ModAttributes.RATIONALITY_NATURAL_RECOVERY_WAIT_TIME).setBaseValue(value);
  }

  /**
   * 获取理智值自然恢复量
   */
  public static float getRationalityRecoveryAmount(Player entity) {
    restrictValue(entity, true);
    return (float) entity.getAttributeValue(ModAttributes.RATIONALITY_RECOVERY_AMOUNT);
  }

  /**
   * 设置基本理智值自然恢复量
   */
  public static void setBaseRationalityRecoveryAmount(Player entity, float value) {
    entity.getAttributes().getInstance(ModAttributes.RATIONALITY_RECOVERY_AMOUNT).setBaseValue(value);
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
