package ctn.singularity.lib.api.lobotomycorporation;

import ctn.ctnapi.client.util.ColorUtil;
import ctn.singularity.lib.api.ColourText;
import ctn.singularity.lib.capability.ILcLevel;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.LibCapabilitys;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 脑叶等级
 */
public enum LcLevel implements ColourText, StringRepresentable {
  ZAYIN (1, "zayin", "#00ff00"),
  TETH(2, "teth", "#1e90ff"),
  HE(3, "he", "#ffff00"),
  WAW(4, "waw", "#8a2be2"),
  ALEPH(5, "aleph", "#ff0000");

  private final String name;
  private final int level;
  private final String colour;
  private final int colourValue;

  LcLevel(int level, String name, String colour) {
    this.name = name;
    this.level = level;
    this.colour = colour;
    this.colourValue = ColorUtil.rgbColor(colour);
  }

  @NotNull
  public static LcLevel getEntityLevel(@NotNull Entity entity) {
    ILcLevel capability = entity.getCapability(LibCapabilitys.LcLevel.LC_LEVEL_ENTITY);

    if (capability == null) {
      return LcLevel.ZAYIN;
    }

    LcLevel lcLevel = capability.getItemLevel();
    if (lcLevel == null) {
      lcLevel = LcLevel.ZAYIN;
    }
    return lcLevel;
  }

  /**
   * 返回实体或物品之间的等级差值
   */
  public static int leveDifferenceValue(@NotNull LcLevel pmLevel, @NotNull LcLevel pmLevel2) {
    return pmLevel.getLevel() - pmLevel2.getLevel();
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }

  @Override
  public int getColourValue() {
    return colourValue;
  }

  @Override
  public String getColourText() {
    return colour;
  }

  @Override
  public String getColourName() {
    return name;
  }

  @Contract(pure = true)
  @Override
  public @NotNull String getSerializedName() {
    return LibMain.LIB_ID + "." + getName();
  }
}
