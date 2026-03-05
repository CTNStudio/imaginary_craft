package ctn.imaginarycraft.linkage.jade;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import org.jetbrains.annotations.*;
import snownee.jade.api.*;
import snownee.jade.api.config.*;

public enum BlockLcLevel implements IBlockComponentProvider {
  INSTANCE;

  @Override
  public ResourceLocation getUid() {
    return ModJadePlugin.BLOCK_LC_LEVEL;
  }

  @Override
  public void appendTooltip(final ITooltip tooltip, final BlockAccessor accessor, final IPluginConfig config) {
    @Nullable LcLevelType level = LcLevelUtil.getLevel(accessor.getLevel(), accessor.getPosition());
    if (level == null) {
      return;
    }
    tooltip.add(1, Component.literal(level.getName().toUpperCase()).withColor(level.getColourValue()));
  }
}
