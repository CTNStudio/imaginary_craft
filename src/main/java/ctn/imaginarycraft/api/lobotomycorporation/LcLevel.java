package ctn.imaginarycraft.api.lobotomycorporation;

import ctn.ctnapi.client.util.ColorUtil;
import ctn.imaginarycraft.api.ColourText;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

// TODO 对于药水需要做特殊处理

/**
 * 脑叶等级
 */
public enum LcLevel implements ColourText, StringRepresentable {
  ZAYIN(1, "zayin", "#00ff00"),
  TETH(2, "teth", "#1e90ff"),
  HE(3, "he", "#ffff00"),
  WAW(4, "waw", "#8a2be2"),
  ALEPH(5, "aleph", "#ff0000");

  private final String name;
  private final int levelValue;
  private final String colour;
  private final int colourValue;

  LcLevel(int levelValue, String name, String colour) {
    this.name = name;
    this.levelValue = levelValue;
    this.colour = colour;
    this.colourValue = ColorUtil.rgbColor(colour);
  }

  public String getName() {
    return name;
  }

  public static @NotNull LcLevel byLevel(int level) {
    final int finalLevel = Math.clamp(level, ZAYIN.getLevelValue(), ALEPH.getLevelValue());
    return Arrays.stream(values()).filter(value -> value.levelValue == finalLevel).findFirst().orElse(ZAYIN);
  }

  public int getLevelValue() {
    return levelValue;
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
    return ImaginaryCraft.modRlText(getName());
  }
}
