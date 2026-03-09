package ctn.imaginarycraft.core.capability.entity;

import ctn.imaginarycraft.api.*;
import org.jetbrains.annotations.*;

public enum EntityLcLevel implements IEntityLcLevel {
  NULL() {
    @Override
    public @Nullable LcLevel getLcLevel() {
      return null;
    }
  },
  ZAYIN() {
    @Override
    public @NotNull LcLevel getLcLevel() {
      return LcLevel.ZAYIN;
    }
  },
  TETH() {
    @Override
    public @NotNull LcLevel getLcLevel() {
      return LcLevel.TETH;
    }
  },
  HE() {
    @Override
    public @NotNull LcLevel getLcLevel() {
      return LcLevel.HE;
    }
  },
  WAW() {
    @Override
    public @NotNull LcLevel getLcLevel() {
      return LcLevel.WAW;
    }
  },
  ALEPH() {
    @Override
    public @NotNull LcLevel getLcLevel() {
      return LcLevel.ALEPH;
    }
  }
}
