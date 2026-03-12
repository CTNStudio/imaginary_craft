package ctn.imaginarycraft.eventexecute;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.mixed.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.core.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.damagesource.*;

import javax.annotation.*;

// TODO 精神伤害之类的不能被原版防御处理
public final class LcDamageEventExecutes {
  public static final double VULNERABILITY_DECELERATE_THRESHOLD = 1.2;

  /**
   * 根据目标护甲等级与攻击者等级计算伤害减免倍率
   */
  public static void levelReduction(LivingEntity entity, DamageContainer damageContainer, DamageSource damageSource) {
    LcLevel attackerLevel = damageSource.imaginaryCraft$getLcDamageLevel();
    if (attackerLevel == null) {
      return;
    }

    int armorLcLaval = 0; // 盔甲等级总和
    int armorNumber = 0; // 护甲数量
    int voidLcLevelArmorNumber = 0; // 无等级护甲数量

    // 遍历所有护甲槽位，统计护甲等级和数量
    for (final ItemStack armorItemStack : entity.getArmorAndBodyArmorSlots()) {
      if (armorItemStack == null || armorItemStack.isEmpty()) {
        continue;
      }
      // 获取盔甲等级
      @Nullable LcLevel level = LcLevelUtil.getLevel(armorItemStack);
      if (level == null) {
        voidLcLevelArmorNumber++;
      } else {
        armorLcLaval += level.getLevelValue();
      }
      armorNumber++;
    }

    if (armorNumber == 0) {
      damageContainer.setNewDamage(ontologyLevelCalculate(entity, attackerLevel, damageContainer.getOriginalDamage()));
      return;
    }

    // 所有护甲都无等级时设置特殊标记值
    if (voidLcLevelArmorNumber == armorNumber) {
      armorLcLaval = -1;
    }

    // 计算平均护甲等级
    if (armorLcLaval != -1) {
      armorLcLaval /= armorNumber;
    }

    damageContainer.setNewDamage(damageContainer.getOriginalDamage() * LcLevelUtil.getDamageMultiple(LcLevel.byLevel(armorLcLaval), attackerLevel));
  }

  /**
   * 计算易伤属性对最终伤害的加成效果
   */
  public static void vulnerableReduction(LivingEntity entity, DamageContainer damageContainer, DamageSource damageSource) {
    LcDamageType lcDamageType = damageSource.imaginaryCraft$getLcDamageType();
    float newDamage = damageContainer.getNewDamage();

    // 检查是否存在自定义伤害类型
    if (lcDamageType != null) {
      // 获取实体的易伤属性值并应用到伤害计算
      Holder<Attribute> vulnerable = lcDamageType.getVulnerable();
      AttributeInstance attributeInstance = entity.getAttribute(vulnerable);
      if (attributeInstance != null) {
        newDamage *= (float) attributeInstance.getValue();
      } else {
        newDamage *= (float) vulnerable.value().getDefaultValue();
      }
    }

    damageContainer.setNewDamage(newDamage);
  }

  /**
   * 本体计算
   */
  private static float ontologyLevelCalculate(final LivingEntity entity, @Nullable final LcLevel attackerLevel, final float damage) {
    return damage * LcLevelUtil.getDamageMultiple(LcLevelUtil.getLevel(entity), attackerLevel);
  }

  /**
   * 灵魂伤害计算
   */
  public static float theSoulDamage(float damage, LivingEntity attackedEntity, @org.jetbrains.annotations.Nullable Entity sourceEntity, DamageSource damageSource) {
    damage /= 100;
    float maxHealth = 0;
    @org.jetbrains.annotations.Nullable LcLevel attackedLevel = LcLevelUtil.getLevel(attackedEntity);
    @org.jetbrains.annotations.Nullable LcLevel attackerLevel = IDamageSource.of(damageSource).imaginaryCraft$getLcDamageLevel();
    if (sourceEntity instanceof LivingEntity living) {
      maxHealth = (float) living.getAttributeValue(Attributes.MAX_HEALTH);
    }

    // 如果未获取到最大生命值，则使用默认值20
    if (maxHealth == 0) {
      maxHealth = 20;
    }
    // TODO 重新调整灵魂伤害算法
    // 根据伤害等级差异计算最终伤害
    return damage * (maxHealth / 5) * LcLevelUtil.getDamageMultiple(attackedLevel, attackerLevel);
  }
}
