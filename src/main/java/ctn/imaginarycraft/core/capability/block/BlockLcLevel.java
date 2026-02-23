package ctn.imaginarycraft.core.capability.block;

import ctn.imaginarycraft.api.LcLevelType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum BlockLcLevel implements IBlockLcLevel {
  NULL() {
    @Override
    public @Nullable LcLevelType getLcLevel(final Level level, final BlockPos pos) {
      return null;
    }
  },
  ZAYIN() {
    @Override
    public @NotNull LcLevelType getLcLevel(final Level level, final BlockPos pos) {
      return LcLevelType.ZAYIN;
    }
  },
  TETH() {
    @Override
    public @NotNull LcLevelType getLcLevel(final Level level, final BlockPos pos) {
      return LcLevelType.TETH;
    }
  },
  HE() {
    @Override
    public @NotNull LcLevelType getLcLevel(final Level level, final BlockPos pos) {
      return LcLevelType.HE;
    }
  },
  WAW() {
    @Override
    public @NotNull LcLevelType getLcLevel(final Level level, final BlockPos pos) {
      return LcLevelType.WAW;
    }
  },
  ALEPH() {
    @Override
    public @NotNull LcLevelType getLcLevel(final Level level, final BlockPos pos) {
      return LcLevelType.ALEPH;
    }
  }
}
