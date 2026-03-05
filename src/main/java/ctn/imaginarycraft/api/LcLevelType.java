package ctn.imaginarycraft.api;

import com.mojang.serialization.*;
import ctn.imaginarycraft.client.util.*;
import ctn.imaginarycraft.core.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * 脑叶等级
 */
public enum LcLevelType implements ColourText, StringRepresentable {
  ZAYIN(0, "zayin", "#00ff00"),
  TETH(1, "teth", "#1e90ff"),
  HE(2, "he", "#ffff00"),
  WAW(3, "waw", "#8a2be2"),
  ALEPH(4, "aleph", "#ff0000");

  private final String name;
  private final int levelValue;
  private final String colour;
  private final int colourValue;

  public static final Codec<LcLevelType> CODEC = StringRepresentable
    .fromEnum(LcLevelType::values).validate(DataResult::success);
  public static final StreamCodec<ByteBuf, LcLevelType> STREAM_CODEC = ByteBufCodecs
    .idMapper(ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP), Enum::ordinal);

  LcLevelType(int levelValue, String name, String colour) {
    this.name = name;
    this.levelValue = levelValue;
    this.colour = colour;
    this.colourValue = ColorUtil.rgbColor(colour);
  }

  public String getName() {
    return name;
  }

  public static @NotNull LcLevelType byLevel(int level) {
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
