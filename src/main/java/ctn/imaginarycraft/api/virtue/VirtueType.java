package ctn.imaginarycraft.api.virtue;

import com.mojang.serialization.*;
import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.client.util.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.init.*;
import io.netty.buffer.*;
import net.minecraft.core.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import net.neoforged.neoforge.attachment.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

/**
 * 心核四德 简称 四德
 */
public enum VirtueType implements ColourText, StringRepresentable {
  /**
   * 勇气 - 影响最大生命值
   */
  FORTITUDE(0, "fortitude", ModAttachments.FORTITUDE, ImaginaryCraft.modRlText("tooltip.fortitude"), "#ff0000"),
  /**
   * 谨慎 - 影响最大精神
   */
  PRUDENCE(1, "prudence", ModAttachments.PRUDENCE, ImaginaryCraft.modRlText("tooltip.prudence"), "#ffffff"),
  /**
   * 自律 - 影响挖掘速度 TODO 补充
   */
  TEMPERANCE(2, "temperance", ModAttachments.TEMPERANCE, ImaginaryCraft.modRlText("tooltip.temperance"), "#8a2be2"),
  /**
   * 正义 - 影响移动速度和攻击速度
   */
  JUSTICE(3, "justice", ModAttachments.JUSTICE, ImaginaryCraft.modRlText("tooltip.justice"), "#00ffff"),
  /**
   * 综合
   */
  COMPOSITE(4, "composite", null, ImaginaryCraft.modRlText("tooltip.composite_rating"), null);

  public static final Codec<VirtueType> CODEC = StringRepresentable
    .fromEnum(VirtueType::values);
  private static final IntFunction<VirtueType> BY_ID = ByIdMap
    .continuous(type -> type.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
  public static final StreamCodec<ByteBuf, VirtueType> STREAM_CODEC = ByteBufCodecs
    .idMapper(BY_ID, type -> type.id);

  private final int id;
  private final String name;
  private final String tooltipName;
  @Nullable
  private final Holder<AttachmentType<?>> attachmentTypeHolder;
  @Nullable
  private final String colour;
  private final int colourValue;

  VirtueType(int id, String name, @Nullable Holder<AttachmentType<?>> attachmentTypeHolder, String tooltipName, @Nullable String colour) {
    this.id = id;
    this.name = name;
    this.attachmentTypeHolder = attachmentTypeHolder;
    this.tooltipName = tooltipName;
    this.colour = colour;
    this.colourValue = colour == null ? 0 : ColorUtil.rgbColor(colour);
  }

  @Override
  public @NotNull String getSerializedName() {
    return ImaginaryCraft.ID + "." + getName();
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  @Nullable
  public <T extends AbstractVirtue> Holder<AttachmentType<T>> getAttachmentTypeHolder() {
    return (Holder<AttachmentType<T>>) (Object) attachmentTypeHolder;
  }

  @Override
  public int getColourValue() {
    return colourValue;
  }

  @Override
  @Nullable
  public String getColourText() {
    return colour;
  }

  @Override
  @Nullable
  public String getColourName() {
    return name;
  }

  public String getTooltipName() {
    return tooltipName;
  }
}
