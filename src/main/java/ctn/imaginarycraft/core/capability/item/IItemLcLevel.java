package ctn.imaginarycraft.core.capability.item;

import ctn.imaginarycraft.api.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface IItemLcLevel {
  /**
   * 返回null则不参与等级系统处理
   */
  @Nullable
  LcLevelType getLcLevel(ItemStack stack);
}
