package ctn.singularity.lib.api.lobotomycorporation.util;

import ctn.singularity.lib.core.LibEventHooks;
import ctn.singularity.lib.init.LibAttachments;
import ctn.singularity.lib.init.LibAttributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Map;

/**
 * 理智值相关
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
    modifyRationalityValue(player, -event.getNewDamage(), true);
    event.setNewDamage(0);
  }

  /**
   * 修改理智值
   */
  public static void modifyRationalityValue(Player player, float value, final boolean isEvent) {
    setRationalityValue(player, getRationalityValue(player) + value,isEvent);
  }

  /**
   * 限制理智
   */
  private static void restrictRationality(Player entity, final boolean isEvent) {
    float maxRationalityValue = getMaxRationalityValue(entity);
    setRationalityValue(entity, Math.clamp(getRationalityValue(entity), maxRationalityValue, -maxRationalityValue), isEvent);
  }

  /**
   * 获取理智值
   */
  public static float getRationalityValue(Player player) {
    return player.getData(LibAttachments.RATIONALITY);
  }

  /**
   * 设置理智值
   */
  public static void setRationalityValue(final Player player, final float value, final boolean isEvent) {
    float oldData = getRationalityValue(player);
    float newValue = value;
    if (isEvent){
      Map.Entry<Boolean, Float> pre = LibEventHooks.sourceRationalityPre(player, oldData, value);
      newValue = pre.getValue();
      if (pre.getKey()) {
        return;
      }
    }

    if (newValue == 0) {
      player.setData(LibAttachments.RATIONALITY, newValue);
    }

    if (isEvent) {
      LibEventHooks.sourceRationalityPost(player, oldData, newValue);
    }
  }

  /**
   * 获取最大理智值
   */
  public static float getMaxRationalityValue(Player entity) {
    return (float) entity.getAttributeValue(LibAttributes.MAX_RATIONALITY);
  }

  /**
   * 获取暂停恢复理智tick
   */
  public static int getRationalityPauseRecoveryTick(Player player) {
    return player.getData(LibAttachments.RATIONALITY_PAUSE_RECOVERY_TICK);
  }

  /**
   * 设置恢复暂停理智tick
   */
  public static void setRationalityRecoveryTick(Player player, int value) {
    player.setData(LibAttachments.RATIONALITY_PAUSE_RECOVERY_TICK, value);
  }
}
