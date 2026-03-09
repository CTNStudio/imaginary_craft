package ctn.imaginarycraft.linkage.jade;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import org.jetbrains.annotations.*;
import snownee.jade.api.*;
import snownee.jade.api.config.*;

public enum EntityLcLevel implements IEntityComponentProvider {
  INSTANCE;

  @Override
  public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
    Entity entity = entityAccessor.getEntity();
    @Nullable LcLevel level = LcLevelUtil.getLevel(entity);
    if (entity instanceof ItemEntity itemEntity) {
      level = LcLevelUtil.getLevel(itemEntity.getItem());
    }
    if (level == null) {
      return;
    }
    iTooltip.add(1, Component.literal(level.getName().toUpperCase()).withColor(level.getColourValue()));
  }

  @Override
  public ResourceLocation getUid() {
    return ModJadePlugin.ENTITY_LC_LEVEL;
  }
}
