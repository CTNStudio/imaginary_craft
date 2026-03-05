package ctn.imaginarycraft.core.capability.block;

import ctn.imaginarycraft.api.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface IBlockLcLevel {
  /**
   * 返回null则不参与等级系统处理
   */
  @Nullable
  LcLevelType getLcLevel(Level level, BlockPos pos);
}
