package ctn.imaginarycraft.common.world.item.ego.weapon;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.world.item.IEgoItem;
import ctn.imaginarycraft.api.world.item.IEgoWeaponItem;
import ctn.imaginarycraft.core.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.core.capability.item.IItemUsageReq;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * EGO武器
 */
public abstract class EgoWeaponItem extends Item implements IEgoWeaponItem, IItemUsageReq, IItemLcDamageType {
  private final @Nullable LcDamageType lcDamageType;
  private final Set<LcDamageType> canCauseLcDamageTypes;

  public EgoWeaponItem(Properties itemProperties, Builder<?> builder) {
    super(IEgoItem.add(itemProperties, builder));
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
