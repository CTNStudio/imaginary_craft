package ctn.singularity.lib.init;

import com.mojang.serialization.Codec;
import ctn.singularity.lib.api.lobotomycorporation.virtue.FortitudeAttachment;
import ctn.singularity.lib.api.lobotomycorporation.virtue.JusticeAttachment;
import ctn.singularity.lib.api.lobotomycorporation.virtue.PrudenceAttachment;
import ctn.singularity.lib.api.lobotomycorporation.virtue.TemperanceAttachment;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.util.AttachmentRegisterUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class LibAttachments extends AttachmentRegisterUtil {
  public static final DeferredRegister<AttachmentType<?>> REGISTRY = LibMain.modRegister(NeoForgeRegistries.ATTACHMENT_TYPES);

  /**
   * 理智值
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> RATIONALITY = register("rationality",
    (player) -> 0f, builder -> builder
      .serialize(Codec.FLOAT)
      .sync(ByteBufCodecs.FLOAT)
      .copyOnDeath());
  /**
   * 理智值暂停恢复tick
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> RATIONALITY_PAUSE_RECOVERY_TICK = register("rationality_pause_recovery_tick",
    (player) -> 0, builder -> builder
      .serialize(Codec.INT)
      .sync(ByteBufCodecs.INT)
      .copyOnDeath());

  /// 四徳
  /**
   * 勇气
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<FortitudeAttachment>> FORTITUDE = register("fortitude",
    FortitudeAttachment::new, builder -> builder
      .serialize(new FortitudeAttachment.Serialize())
      .sync(new FortitudeAttachment.Sync())
      .copyOnDeath());
  /**
   * 谨慎
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<PrudenceAttachment>> PRUDENCE = register("prudence",
    PrudenceAttachment::new, builder -> builder
      .serialize(new PrudenceAttachment.Serialize())
      .sync(new PrudenceAttachment.Sync())
      .copyOnDeath());
  /**
   * 自律
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<TemperanceAttachment>> TEMPERANCE = register("temperance",
    TemperanceAttachment::new, builder -> builder
      .serialize(new TemperanceAttachment.Serialize())
      .sync(new TemperanceAttachment.Sync())
      .copyOnDeath());
  /**
   * 正义
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<JusticeAttachment>> JUSTICE = register("justice",
    JusticeAttachment::new, builder -> builder
      .serialize(new JusticeAttachment.Serialize())
      .sync(new JusticeAttachment.Sync())
      .copyOnDeath());
}
