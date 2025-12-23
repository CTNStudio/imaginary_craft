package ctn.imaginarycraft.common.payloads.player;

import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimStandardFadePlayerAnim;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * 玩家动画数据包
 *
 * @param controller 动画控制器id
 * @param length     动画长度 如果为-1则无加速
 * @param animation  动画id 如果为空则停止动画
 * @param withFade   动画淡入淡出 如果为空则无淡入淡出
 */
public record PlayerAnimationPayload(ResourceLocation controller,
                                     Optional<ResourceLocation> animation,
                                     float length,
                                     Optional<PlayerAnimStandardFadePlayerAnim> withFade) implements CustomPacketPayload {
  public static final CustomPacketPayload.Type<PlayerAnimationPayload> TYPE = new CustomPacketPayload.Type<>(ImaginaryCraft.modRl("player_animation_payload"));

  public static final StreamCodec<ByteBuf, PlayerAnimationPayload> STREAM_CODEC = StreamCodec.composite(
    ResourceLocation.STREAM_CODEC, PlayerAnimationPayload::controller,
    ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), PlayerAnimationPayload::animation,
    ByteBufCodecs.FLOAT, PlayerAnimationPayload::length,
    ByteBufCodecs.optional(PlayerAnimStandardFadePlayerAnim.STREAM_CODEC), PlayerAnimationPayload::withFade,
    PlayerAnimationPayload::new);

  /**
   * 玩家动画数据包
   *
   * @param controller 动画控制器id
   * @param length     动画长度
   * @param animation  动画id 如果为空则停止动画
   * @param withFade   动画淡入淡出 如果为空则无淡入淡出
   */
  public PlayerAnimationPayload(@NotNull ResourceLocation controller, @Nullable ResourceLocation animation, float length, @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(controller, Optional.ofNullable(animation), length, Optional.ofNullable(withFade));
  }


  /**
   * 玩家动画数据包(无过度动画)
   *
   * @param controller 动画控制器id
   * @param length     动画长度 如果为-1则无加速
   * @param animation  动画id 如果为空则停止动画
   */
  public PlayerAnimationPayload(@NotNull ResourceLocation controller, @Nullable ResourceLocation animation, float length) {
    this(controller, animation, length, null);
  }

  /**
   * 玩家动画数据包(无过度动画，无加速)
   *
   * @param controller 动画控制器id
   * @param animation  动画id 如果为空则停止动画
   */
  public PlayerAnimationPayload(@NotNull ResourceLocation controller, @Nullable ResourceLocation animation) {
    this(controller, animation, -1);
  }

  /**
   * 暂停动画
   * @param controller 动画控制器id
   */
  public PlayerAnimationPayload(@NotNull ResourceLocation controller) {
    this(controller, null);
  }

  public static void toServer(final PlayerAnimationPayload data, final IPayloadContext context) {
    MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
    if (minecraftServer == null) {
      return;
    }
    Player senderPlayer = context.player();
    ServerLevel level = minecraftServer.getLevel(senderPlayer.level().dimension());
    if (level == null) {
      return;
    }
    UUID senderUUID = senderPlayer.getUUID();
    for (ServerPlayer player : level.players()) {
      if (player.getUUID().equals(senderUUID)) {
        continue;
      }
      PayloadUtil.sendToClient(player, data);
    }
  }

  public static void toClient(final PlayerAnimationPayload data, final IPayloadContext context) {
    Optional<ResourceLocation> animation = data.animation;
    if (animation.isEmpty()) {
      PlayerAnimUtil.stop(context.player(), data.controller);
      return;
    }
    PlayerAnimUtil.play(context.player(), data.controller, animation.get());
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
