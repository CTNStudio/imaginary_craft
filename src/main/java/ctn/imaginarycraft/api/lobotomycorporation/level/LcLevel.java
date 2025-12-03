package ctn.imaginarycraft.api.lobotomycorporation.level;

import ctn.ctnapi.client.util.ColorUtil;
import ctn.imaginarycraft.api.ColourText;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * 脑叶等级
 */
public enum LcLevel implements ColourText, StringRepresentable {
  /**
   * 此伤害无视等级减免
   */
  VOID(-1, "void", "#000000"),
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

  public static LcLevel byLevel(int level) {
    return Arrays.stream(values()).filter(value -> value.levelValue == level).findFirst().orElse(ZAYIN);
  }

  /**
   * @return 返回 {@link -1} 代表无视等级抗性
   */
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
