package ctn.imaginarycraft.init;

import com.mojang.serialization.Codec;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueFortitude;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueJustice;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtuePrudence;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueTemperance;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.util.AttachmentRegisterUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModAttachments extends AttachmentRegisterUtil {
  public static final DeferredRegister<AttachmentType<?>> REGISTRY = ImaginaryCraft.modRegister(NeoForgeRegistries.ATTACHMENT_TYPES);

  /**
   * 理智值
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> RATIONALITY = register("rationality",
    (player) -> 0f, builder -> builder
      .serialize(Codec.FLOAT)
      .sync(ByteBufCodecs.FLOAT));
  /**
   * 理智值暂停恢复tick
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> RATIONALITY_PAUSE_RECOVERY_TICK = register("rationality_pause_recovery_tick",
    (player) -> 0, builder -> builder
      .serialize(Codec.INT)
      .sync(ByteBufCodecs.INT));

  /// 四徳
  /**
   * 勇气
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtueFortitude>> FORTITUDE = register("fortitude",
    VirtueFortitude::new, builder -> builder
      .serialize(new VirtueFortitude.Serialize())
      .sync(new VirtueFortitude.Sync())
      .copyOnDeath());
  /**
   * 谨慎
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtuePrudence>> PRUDENCE = register("prudence",
    VirtuePrudence::new, builder -> builder
      .serialize(new VirtuePrudence.Serialize())
      .sync(new VirtuePrudence.Sync())
      .copyOnDeath());
  /**
   * 自律
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtueTemperance>> TEMPERANCE = register("temperance",
    VirtueTemperance::new, builder -> builder
      .serialize(new VirtueTemperance.Serialize())
      .sync(new VirtueTemperance.Sync())
      .copyOnDeath());
  /**
   * 正义
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtueJustice>> JUSTICE = register("justice",
    VirtueJustice::new, builder -> builder
      .serialize(new VirtueJustice.Serialize())
      .sync(new VirtueJustice.Sync())
      .copyOnDeath());
}
