package ctn.imaginarycraft.common.item;

import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * 理智值控制工具
 */
public class CreativeRationalityToolItem extends Item {
  public CreativeRationalityToolItem(Properties properties) {
    super(properties.component(ModDataComponents.MODE_BOOLEAN, false));
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
    ItemStack itemStack = player.getItemInHand(usedHand);
    if (player.isCreative()) {
      if (player.isShiftKeyDown()) {
        itemStack.set(ModDataComponents.MODE_BOOLEAN, Boolean.FALSE.equals(itemStack.get(ModDataComponents.MODE_BOOLEAN)));
        return InteractionResultHolder.success(itemStack);
      }
      if (!level.isClientSide()) {
        if (Boolean.TRUE.equals(itemStack.get(ModDataComponents.MODE_BOOLEAN))) {
          RationalityUtil.modifyValue(player, -1, false);
        } else {
          RationalityUtil.modifyValue(player, 1, false);
        }
        return InteractionResultHolder.success(itemStack);
      }
    }
    return InteractionResultHolder.fail(itemStack);
  }

  @Override
  public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
    super.inventoryTick(stack, level, entity, slotId, isSelected);
    if (entity instanceof Player player && !level.isClientSide() && isSelected) {
      getRationality("当前的理智值为：" + RationalityUtil.getValue(player));
    }
  }

  private void getRationality(String player) {
    Minecraft.getInstance().gui.setOverlayMessage(Component.literal(player), false);
  }
}
