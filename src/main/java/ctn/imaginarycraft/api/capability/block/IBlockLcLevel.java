package ctn.imaginarycraft.api.capability.block;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface IBlockLcLevel {
  /**
   * 返回null则不参与等级系统处理
   */
  @Nullable
  LcLevel getLcLevel(Level level, BlockPos pos);
}
