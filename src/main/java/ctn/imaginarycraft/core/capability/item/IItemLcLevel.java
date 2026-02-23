package ctn.imaginarycraft.core.capability.item;

import ctn.imaginarycraft.api.LcLevelType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface IItemLcLevel {
  /**
   * 返回null则不参与等级系统处理
   */
  @Nullable
  LcLevelType getLcLevel(ItemStack stack);
}
