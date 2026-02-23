package ctn.imaginarycraft.core.capability.item;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.mixed.IDamageSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * 该类主要针对物品的近战攻击
 * <p>
 * 如果你可以直接返回{@link DamageSource}请使用{@link IDamageSource#setImaginaryCraft$LcDamageType(LcDamageType)}
 */
public interface IItemLcDamageType {
  /**
   * 获取物品当前的伤害类型
   */
  @Nullable
  LcDamageType getLcDamageColorDamageType(ItemStack stack);

  /**
   * 获取可以造成的伤害类型 一般用于描述
   */
  @Nullable
  Set<LcDamageType> getCanCauseLcDamageTypes(final ItemStack stack);

  /**
   * 获取伤害类型描述
   */
  @Nullable
  default Component getLcDamageTypeToTooltip(final ItemStack stack) {
    return null;
  }
}
