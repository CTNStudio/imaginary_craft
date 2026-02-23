package ctn.imaginarycraft.api.lobotomycorporation.util;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.mixed.IDamageSource;
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
    return damageSource.getWeaponItem();
  }

  /**
   * 灵魂伤害计算
   *
   * @return 计算后的灵魂伤害值
   */
  public static float theSoulDamage(float damage, LivingEntity attackedEntity, @Nullable Entity sourceEntity, DamageSource damageSource) {
    damage /= 100;
    float maxHealth = 0;
    @Nullable LcLevelType attackedLevel = LcLevelUtil.getLevel(attackedEntity);
    @Nullable LcLevelType attackerLevel = IDamageSource.of(damageSource).getImaginaryCraft$LcDamageLevel();
    if (sourceEntity instanceof LivingEntity living) {
      maxHealth = (float) living.getAttributeValue(Attributes.MAX_HEALTH);
    }

    // 如果未获取到最大生命值，则使用默认值20
    if (maxHealth == 0) {
      maxHealth = 20;
    }
    // TODO 重新调整算法
    // 根据伤害等级差异计算最终伤害
    return damage * (maxHealth / 5) * LcLevelUtil.getDamageMultiple(attackedLevel, attackerLevel);
  }
}
