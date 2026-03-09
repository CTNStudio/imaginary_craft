package ctn.imaginarycraft.core.capability.block;

import ctn.imaginarycraft.api.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

public enum BlockLcLevel implements IBlockLcLevel {
  NULL() {
    @Override
    public @Nullable LcLevel getLcLevel(final Level level, final BlockPos pos) {
      return null;
    }
  },
  ZAYIN() {
    @Override
    public @NotNull LcLevel getLcLevel(final Level level, final BlockPos pos) {
      return LcLevel.ZAYIN;
    }
  },
  TETH() {
    @Override
    public @NotNull LcLevel getLcLevel(final Level level, final BlockPos pos) {
      return LcLevel.TETH;
    }
  },
  HE() {
    @Override
    public @NotNull LcLevel getLcLevel(final Level level, final BlockPos pos) {
      return LcLevel.HE;
    }
  },
  WAW() {
    @Override
    public @NotNull LcLevel getLcLevel(final Level level, final BlockPos pos) {
      return LcLevel.WAW;
    }
  },
  ALEPH() {
    @Override
    public @NotNull LcLevel getLcLevel(final Level level, final BlockPos pos) {
      return LcLevel.ALEPH;
    }
  }
}
