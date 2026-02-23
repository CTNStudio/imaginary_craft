package ctn.imaginarycraft.eventexecute;

import ctn.imaginarycraft.api.LcLevelType;
import ctn.imaginarycraft.core.capability.entity.IEntityAbnormalities;
import ctn.imaginarycraft.util.LcLevelUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

// TODO 精神伤害之类的不能被原版防御处理
public final class LcDamageEventExecutes {
  public static final double VULNERABILITY_DECELERATE_THRESHOLD = 1.2;


  /**
   * 等级判断
   *
   * @param entity        目标
   * @param attackerLevel 攻击者等级
   * @param damage        伤害
   * @return 新伤害
   */
  public static float levelJudgment(final LivingEntity entity, @Nullable final LcLevelType attackerLevel, final float damage) {
    if (attackerLevel == null) {
      return damage;
    }

    // 盔甲判断
    if (entity instanceof IEntityAbnormalities) {
      return ontologyLevelCalculate(entity, attackerLevel, damage);
    }
    // 盔甲等级
    int armorItemStackLaval = 0;
    // 护甲数量
    int number = 0;
    int voidNumber = 0;
    // 盔甲
    for (final ItemStack armorItemStack : entity.getArmorAndBodyArmorSlots()) {
      if (armorItemStack == null || armorItemStack.isEmpty()) {
        continue;
      }

      // 盔甲等级
      @Nullable LcLevelType level = LcLevelUtil.getLevel(armorItemStack);
      if (level == null) {
        voidNumber++;
      } else {
        armorItemStackLaval += level.getLevelValue();
      }

      number++;
    }

    if (number == 0) {
      return ontologyLevelCalculate(entity, attackerLevel, damage);
    }

    if (voidNumber == number) {
      armorItemStackLaval = -1;
    }

    // 获取平均等级
    if (armorItemStackLaval != -1) {
      armorItemStackLaval /= number;
    }
    return damage * LcLevelUtil.getDamageMultiple(LcLevelType.byLevel(armorItemStackLaval), attackerLevel);
  }

  /**
   * 本体计算
   *
   * @param entity        目标
   * @param attackerLevel 攻击者等级
   * @param damage        伤害
   * @return 新伤害
   */
  private static float ontologyLevelCalculate(final LivingEntity entity, @Nullable final LcLevelType attackerLevel, final float damage) {
    return damage * LcLevelUtil.getDamageMultiple(LcLevelUtil.getLevel(entity), attackerLevel);
  }
}
