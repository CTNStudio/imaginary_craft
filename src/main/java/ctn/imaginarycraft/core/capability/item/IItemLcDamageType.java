package ctn.imaginarycraft.core.capability.item;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.mixed.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

import java.util.*;

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
  LcDamageType getLcDamageType(ItemStack stack);

  /**
   * 获取可以造成的伤害类型 一般用于描述
   */
  @NotNull
  default Set<LcDamageType> getCanCauseLcDamageTypes(final ItemStack stack) {
    return Set.of();
  }
}
