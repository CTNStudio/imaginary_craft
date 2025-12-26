package ctn.imaginarycraft.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IItemPlayerLeftClick {

  default void leftClickEmpty(Player player, ItemStack stack) {

  }
}
