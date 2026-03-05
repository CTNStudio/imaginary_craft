package ctn.imaginarycraft.common.world.item.ego.weapon;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.api.world.item.*;
import ctn.imaginarycraft.core.capability.item.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * EGO武器
 */
public abstract class EgoWeaponItem extends Item implements IEgoWeaponItem, IItemUsageReq, IItemLcDamageType {
  private final @Nullable LcDamageType lcDamageType;
  private final Set<LcDamageType> canCauseLcDamageTypes;

  public EgoWeaponItem(Properties itemProperties, Builder<?> builder) {
    super(IEgoWeaponItem.add(itemProperties, builder));
    this.lcDamageType = builder.lcDamageType;
    this.canCauseLcDamageTypes = builder.canCauseLcDamageTypes;
  }

  /// 是否可以挖掘方块
  @Override
  public boolean canAttackBlock(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos blockPosition, Player playerEntity) {
    return !playerEntity.isCreative();
  }

  @Override
  public @Nullable LcDamageType getLcDamageType(ItemStack stack) {
    return lcDamageType;
  }

  @Override
  public @NotNull Set<LcDamageType> getCanCauseLcDamageTypes(ItemStack stack) {
    return canCauseLcDamageTypes;
  }
}
