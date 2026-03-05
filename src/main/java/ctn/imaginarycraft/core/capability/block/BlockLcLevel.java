package ctn.imaginarycraft.core.capability.block;

import ctn.imaginarycraft.api.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

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
