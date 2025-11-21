package ctn.singularity.lib.api.lobotomycorporation.virtue;

import com.mojang.serialization.Codec;
import ctn.ctnapi.client.util.ColorUtil;
import ctn.singularity.lib.api.ColourText;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.LibAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

/**
 * 心核四德 简称 四德
 */
public enum Virtue implements ColourText, StringRepresentable {
  /**
   * 勇气 - 影响最大生命值
   */
  FORTITUDE(0, "fortitude", LibAttachments.FORTITUDE, "#ff0000"),
  /**
   * 谨慎 - 影响最大精神
   */
  PRUDENCE(1, "prudence", LibAttachments.PRUDENCE, "#ffffff"),
  /**
   * 自律 - 影响挖掘速度 TODO 补充
   */
  TEMPERANCE(2, "temperance", LibAttachments.TEMPERANCE, "#8a2be2"),
  /**
   * 正义 - 影响移动速度和攻击速度
   */
  JUSTICE(3, "justice", LibAttachments.JUSTICE, "#00ffff"),
  ;

  public static final Codec<Virtue> CODEC = StringRepresentable
    .fromEnum(Virtue::values);
  private static final IntFunction<Virtue> BY_ID = ByIdMap
    .continuous(type -> type.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
  public static final StreamCodec<ByteBuf, Virtue> STREAM_CODEC = ByteBufCodecs
    .idMapper(BY_ID, type -> type.id);

  private final int id;
  private final String name;
  @Nullable
  private final Holder<AttachmentType<?>> attachmentTypeHolder;
  @Nullable
  private final String colour;
  private final int colourValue;

  Virtue(int id, String name, @Nullable Holder<AttachmentType<?>> attachmentTypeHolder, @Nullable String colour) {
    this.id = id;
    this.name = name;
    this.attachmentTypeHolder = attachmentTypeHolder;
    this.colour = colour;
    this.colourValue = colour == null ? 0 : ColorUtil.rgbColor(colour);
  }

  @Override
  public @NotNull String getSerializedName() {
    return LibMain.LIB_ID + "." + getName();
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  @Nullable
  public Holder<AttachmentType<?>> getAttachmentTypeHolder() {
    return attachmentTypeHolder;
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
  public String getColourName() {
    return name;
  }
}
