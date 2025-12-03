package ctn.imaginarycraft.api.lobotomycorporation.level.util;

import ctn.imaginarycraft.api.lobotomycorporation.level.LcLevel;
import ctn.imaginarycraft.capability.ILcLevel;
import ctn.imaginarycraft.init.ModCapabilitys;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class LcLevelUtil {
  @NotNull
  public static LcLevel getLevel(@NotNull Entity entity) {
    ILcLevel capability = entity.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ENTITY);
    return capability != null ? capability.getLcLevel() : LcLevel.ZAYIN;
  }

  @NotNull
  public static LcLevel getLevel(@NotNull ItemStack item) {
    ILcLevel capability = item.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ITEM);
    return capability != null ? capability.getLcLevel() : LcLevel.ZAYIN;
  }
}
