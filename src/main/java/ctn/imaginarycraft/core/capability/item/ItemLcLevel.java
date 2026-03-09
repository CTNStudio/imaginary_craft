package ctn.imaginarycraft.core.capability.item;

import ctn.imaginarycraft.api.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

public enum ItemLcLevel implements IItemLcLevel {
  NULL() {
    @Override
    public @Nullable LcLevel getLcLevel(final ItemStack stack) {
      return null;
    }
  },
  ZAYIN() {
    @Override
    public @NotNull LcLevel getLcLevel(final ItemStack stack) {
      return LcLevel.ZAYIN;
    }
  },
  TETH() {
    @Override
    public @NotNull LcLevel getLcLevel(final ItemStack stack) {
      return LcLevel.TETH;
    }
  },
  HE() {
    @Override
    public @NotNull LcLevel getLcLevel(final ItemStack stack) {
      return LcLevel.HE;
    }
  },
  WAW() {
    @Override
    public @NotNull LcLevel getLcLevel(final ItemStack stack) {
      return LcLevel.WAW;
    }
  },
  ALEPH() {
    @Override
    public @NotNull LcLevel getLcLevel(final ItemStack stack) {
      return LcLevel.ALEPH;
    }
  }
}
