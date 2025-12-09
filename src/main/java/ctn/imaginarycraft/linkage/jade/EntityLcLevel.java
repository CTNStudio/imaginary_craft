package ctn.imaginarycraft.linkage.jade;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

// 实体等级显示
public enum EntityLcLevel implements IEntityComponentProvider {
  INSTANCE;

  @Override
  public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
    @Nullable LcLevel level = LcLevelUtil.getLevel(entityAccessor.getEntity());
    if (level == null) {
      return;
    }
    iTooltip.add(1, Component.literal(level.getName().toUpperCase()).withColor(level.getColourValue()));
  }

  @Override
  public ResourceLocation getUid() {
    return ModPlugin.ENTITY_LC_LEVEL;
  }
}
