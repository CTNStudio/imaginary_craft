package ctn.imaginarycraft.api.capability.item;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum ItemLcLevel implements IItemLcLevel {
  NULL() {
    @Override
    public @Nullable LcLevelType getLcLevel(final ItemStack stack) {
      return null;
    }
  },
  ZAYIN() {
    @Override
    public @NotNull LcLevelType getLcLevel(final ItemStack stack) {
      return LcLevelType.ZAYIN;
    }
  },
  TETH() {
    @Override
    public @NotNull LcLevelType getLcLevel(final ItemStack stack) {
      return LcLevelType.TETH;
    }
  },
  HE() {
    @Override
    public @NotNull LcLevelType getLcLevel(final ItemStack stack) {
      return LcLevelType.HE;
    }
  },
  WAW() {
    @Override
    public @NotNull LcLevelType getLcLevel(final ItemStack stack) {
      return LcLevelType.WAW;
    }
  },
  ALEPH() {
    @Override
    public @NotNull LcLevelType getLcLevel(final ItemStack stack) {
      return LcLevelType.ALEPH;
    }
  }
}
