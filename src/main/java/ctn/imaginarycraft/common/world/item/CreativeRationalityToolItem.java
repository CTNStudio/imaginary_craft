package ctn.imaginarycraft.common.world.item;

import ctn.imaginarycraft.init.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

/**
 * 理智值控制工具
 */
public class CreativeRationalityToolItem extends Item {
  public CreativeRationalityToolItem(Properties properties) {
    super(properties.component(ModDataComponents.MODE_BOOLEAN, false));
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player playerEntity, @NotNull InteractionHand handUsed) {
    ItemStack itemStackInHand = playerEntity.getItemInHand(handUsed);
    if (!playerEntity.isCreative()) {
      return InteractionResultHolder.fail(itemStackInHand);
    }

    if (playerEntity.isShiftKeyDown()) {
      itemStackInHand.set(ModDataComponents.MODE_BOOLEAN, Boolean.FALSE.equals(itemStackInHand.get(ModDataComponents.MODE_BOOLEAN)));
      return InteractionResultHolder.success(itemStackInHand);
    }

    if (world.isClientSide()) {
      return InteractionResultHolder.fail(itemStackInHand);
    }

    RationalityUtil.modifyValue(playerEntity, Boolean.TRUE == itemStackInHand.get(ModDataComponents.MODE_BOOLEAN) ? -1 : 1, false);
    return InteractionResultHolder.success(itemStackInHand);
  }

  @Override
  public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level world, @NotNull Entity entity, int slotIndex, boolean isCurrentlySelected) {
    super.inventoryTick(itemStack, world, entity, slotIndex, isCurrentlySelected);
    if (entity instanceof Player player && !world.isClientSide() && isCurrentlySelected) {
      displayRationalityValue("当前的理智值为：" + RationalityUtil.getValue(player));
    }
  }

  private void displayRationalityValue(String message) {
    Minecraft.getInstance().gui.setOverlayMessage(Component.literal(message), false);
  }
}
