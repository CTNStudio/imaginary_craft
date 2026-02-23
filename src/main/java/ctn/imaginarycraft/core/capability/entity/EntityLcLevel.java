package ctn.imaginarycraft.core.capability.entity;

import ctn.imaginarycraft.api.LcLevelType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum EntityLcLevel implements IEntityLcLevel {
  NULL() {
    @Override
    public @Nullable LcLevelType getLcLevel() {
      return null;
    }
  },
  ZAYIN() {
    @Override
    public @NotNull LcLevelType getLcLevel() {
      return LcLevelType.ZAYIN;
    }
  },
  TETH() {
    @Override
    public @NotNull LcLevelType getLcLevel() {
      return LcLevelType.TETH;
    }
  },
  HE() {
    @Override
    public @NotNull LcLevelType getLcLevel() {
      return LcLevelType.HE;
    }
  },
  WAW() {
    @Override
    public @NotNull LcLevelType getLcLevel() {
      return LcLevelType.WAW;
    }
  },
  ALEPH() {
    @Override
    public @NotNull LcLevelType getLcLevel() {
      return LcLevelType.ALEPH;
    }
  }
}
