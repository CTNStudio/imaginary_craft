package ctn.imaginarycraft.linkage.jade;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum BlockLcLevel implements IBlockComponentProvider {
  INSTANCE;

  @Override
  public ResourceLocation getUid() {
    return ModPlugin.BLOCK_LC_LEVEL;
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
