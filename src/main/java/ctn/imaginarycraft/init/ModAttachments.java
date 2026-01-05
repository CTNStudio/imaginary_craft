package ctn.imaginarycraft.init;

import com.mojang.serialization.Codec;
import ctn.imaginarycraft.api.PlayerTimingRun;
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
   * 玩家延迟运行
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<PlayerTimingRun>> PLAYER_TIMING_RUN = registerPlayer("player_timing_run",
    PlayerTimingRun::new);

  /**
   * 枪械蓄力值（主手）
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GUN_CHARGE_UP_TICK_MAIN_HAND = registerPlayer("gun_charge_up_tick_main_hand",
    (player) -> 0, builder -> builder
      .sync(ByteBufCodecs.INT));
  /**
   * 枪械蓄力值（副手）
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GUN_CHARGE_UP_TICK_OFF_HAND = registerPlayer("gun_charge_up_tick_off_hand",
    (player) -> 0, builder -> builder
      .sync(ByteBufCodecs.INT));
  /**
   * 枪械是否可以攻击（主手）
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> IS_GUN_ATTACK_MAIN_HAND = registerPlayer("is_gun_left_key_attack_main_hand",
    (player) -> true, builder -> builder
      .sync(ByteBufCodecs.BOOL));
  /**
   * 枪械是否可以攻击（副手）
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> IS_GUN_ATTACK_OFF_HAND = registerPlayer("is_gun_left_key_attack_off_hand",
    (player) -> true, builder -> builder
      .sync(ByteBufCodecs.BOOL));

  // TODO 待实装
  /**
   * 魔弹使用数量
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> MAGIC_BULLET_USAGE_QUANTITY = registerPlayer("magic_bullet_usage_quantity",
    (player) -> 0, builder -> builder
      .sync(ByteBufCodecs.INT)
      .serialize(Codec.INT)
      .copyOnDeath());

  /**
   * 理智值
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> RATIONALITY = registerPlayer("rationality",
    (player) -> 0f, builder -> builder
      .serialize(Codec.FLOAT)
      .sync(ByteBufCodecs.FLOAT));

  /**
   * 理智值暂停恢复tick
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> RATIONALITY_PAUSE_RECOVERY_TICK = registerPlayer("rationality_pause_recovery_tick",
    (player) -> 0, builder -> builder
      .serialize(Codec.INT)
      .sync(ByteBufCodecs.INT));

  /// 四徳
  /**
   * 勇气
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtueFortitude>> FORTITUDE = registerPlayer("fortitude",
    VirtueFortitude::new, builder -> builder
      .serialize(new VirtueFortitude.Serialize())
      .sync(new VirtueFortitude.Sync())
      .copyOnDeath());
  /**
   * 谨慎
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtuePrudence>> PRUDENCE = registerPlayer("prudence",
    VirtuePrudence::new, builder -> builder
      .serialize(new VirtuePrudence.Serialize())
      .sync(new VirtuePrudence.Sync())
      .copyOnDeath());
  /**
   * 自律
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtueTemperance>> TEMPERANCE = registerPlayer("temperance",
    VirtueTemperance::new, builder -> builder
      .serialize(new VirtueTemperance.Serialize())
      .sync(new VirtueTemperance.Sync())
      .copyOnDeath());
  /**
   * 正义
   */
  public static final DeferredHolder<AttachmentType<?>, AttachmentType<VirtueJustice>> JUSTICE = registerPlayer("justice",
    VirtueJustice::new, builder -> builder
      .serialize(new VirtueJustice.Serialize())
      .sync(new VirtueJustice.Sync())
      .copyOnDeath());
}
