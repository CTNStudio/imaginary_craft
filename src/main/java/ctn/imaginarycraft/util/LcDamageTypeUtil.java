package ctn.imaginarycraft.util;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.core.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.init.ModCapabilitys;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public final class LcDamageTypeUtil {
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
}
