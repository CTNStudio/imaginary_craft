package ctn.imaginarycraft.api.capability.item;

import net.minecraft.world.item.ItemStack;

public interface IItemInvincibleTick {
  /**
   * 用于判断是否更改攻击造成的无敌游戏刻
   */
  int getInvincibleTick(ItemStack stack);
}
