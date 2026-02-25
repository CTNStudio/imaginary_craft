package ctn.imaginarycraft.common.item.ego.weapon.template;

import ctn.imaginarycraft.core.capability.item.IEgoItem;
import ctn.imaginarycraft.core.capability.item.IItemUsageReq;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * EGO武器
 */
public abstract class EgoWeaponItem extends Item implements IEgoItem, IItemUsageReq {

  public EgoWeaponItem(Properties itemProperties, Builder<?> builder) {
    super(IEgoItem.add(itemProperties, builder));
  }

  /// 是否可以挖掘方块
  @Override
  public boolean canAttackBlock(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos blockPosition, Player playerEntity) {
    return !playerEntity.isCreative();
  }
}
