package ctn.imaginarycraft.util;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.core.capability.item.*;
import ctn.imaginarycraft.init.*;
import ctn.imaginarycraft.mixed.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

import java.util.*;

public final class LcDamageUtil {
  /**
   * 获取伤害物品
   */
  @Nullable
  public static ItemStack getDamageItemStack(@NotNull DamageSource damageSource) {
    return damageSource.getWeaponItem();
  }

  @Nullable
  public static LcDamageType getLcDamageType(ItemStack itemStack) {
    IItemLcDamageType colorDamageTypeItem = itemStack.getCapability(ModCapabilitys.LC_DAMAGE_TYPE_ITEM);
    if (colorDamageTypeItem != null) {
      return colorDamageTypeItem.getLcDamageType(itemStack);
    }

    if (itemStack.getItem() instanceof IItemLcDamageType iItemLcDamageType) {
      return iItemLcDamageType.getLcDamageType(itemStack);
    }

    return LcDamageType.PHYSICS;
  }

  @NotNull
  public static Set<LcDamageType> getCanCauseLcDamageTypes(ItemStack itemStack) {
    if (itemStack.has(ModDataComponents.LC_DAMAGE_TYPE)) {
      LcDamageType.Component component = itemStack.get(ModDataComponents.LC_DAMAGE_TYPE);
      return component == null ? Set.of() : component.canCauseLcDamageTypes();
    }
    if (itemStack.getItem() instanceof IItemLcDamageType iItemLcDamageType) {
      return iItemLcDamageType.getCanCauseLcDamageTypes(itemStack);
    }
    return Set.of();
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
