package ctn.imaginarycraft.api.lobotomycorporation.util;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import ctn.imaginarycraft.mixinextend.IDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public final class LcDamageUtil {
  /**
   * 获取伤害物品
   */
  @Nullable
  public static ItemStack getDamageItemStack(@NotNull DamageSource damageSource) {
    ItemStack itemStack = damageSource.getWeaponItem();
    if (itemStack == null && damageSource.getEntity() != null) {
      itemStack = damageSource.getEntity().getWeaponItem();
    }
    return itemStack;
  }

  /**
   * 获取伤害比例
   *
   * @param attackedLevel 被攻击的等级
   * @param attackerLevel 攻击者的等级
   */
  public static float getDamageMultiple(@NotNull LcLevel attackedLevel, @NotNull LcLevel attackerLevel) {
    if (attackedLevel == LcLevel.VOID || attackerLevel == LcLevel.VOID) {
      return 1.0f;
    }
    int attackedLevelValue = attackedLevel.getLevelValue();
    int attackerLevelValue = attackerLevel.getLevelValue();
    int i = attackedLevelValue - attackerLevelValue;
    return switch (i) {
      case 4 -> 0.4F;
      case 3 -> 0.6F;
      case 2 -> 0.7F;
      case 1 -> 0.8F;
      case 0, -1 -> 1.0F;
      case -2 -> 1.2F;
      case -3 -> 1.5F;
      case -4 -> 2.0F;
      default -> throw new IllegalArgumentException("Invalid levelValue difference: " + i);
    };
  }

  /**
   * 灵魂伤害计算
   *
   * @return 计算后的灵魂伤害值
   */
  public static float theSoulDamage(float damage, LivingEntity attackedEntity, @Nullable Entity sourceEntity, DamageSource damageSource) {
    damage /= 100;
    float maxHealth = 0;
    LcLevel attackedLevel = LcLevelUtil.getLevel(attackedEntity);
    LcLevel attackerLevel = IDamageSource.of(damageSource).getLcDamageLevel();
    if (sourceEntity instanceof LivingEntity living) {
      maxHealth = (float) living.getAttributeValue(Attributes.MAX_HEALTH);
    }

    // 如果未获取到最大生命值，则使用默认值20
    if (maxHealth == 0) {
      maxHealth = 20;
    }

    // 根据伤害等级差异计算最终伤害
    return damage * (maxHealth / 5) * getDamageMultiple(attackedLevel, attackerLevel);
  }
}
