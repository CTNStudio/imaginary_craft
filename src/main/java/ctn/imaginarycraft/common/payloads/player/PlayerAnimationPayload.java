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
 * @param rawAnimation  动画id
 * @param startAnimFrom 动画开始播放的时间
 * @param playTime      播放时间 如果为-1则使用动画本身的时间
 * @param withFade   动画淡入淡出 如果为空则无淡入淡出
 */
public record PlayerAnimationPayload(ResourceLocation controller, ResourceLocation rawAnimation,
                                     float startAnimFrom, float playTime,
                                     Optional<PlayerAnimStandardFadePlayerAnim> withFade) implements CustomPacketPayload {
  public static final CustomPacketPayload.Type<PlayerAnimationPayload> TYPE = new CustomPacketPayload.Type<>(ImaginaryCraft.modRl("player_animation_payload"));
  public static final StreamCodec<ByteBuf, PlayerAnimationPayload> STREAM_CODEC = StreamCodec.composite(
    ResourceLocation.STREAM_CODEC, PlayerAnimationPayload::controller,
    ResourceLocation.STREAM_CODEC, PlayerAnimationPayload::rawAnimation,
    ByteBufCodecs.FLOAT, PlayerAnimationPayload::playTime,
    ByteBufCodecs.FLOAT, PlayerAnimationPayload::startAnimFrom,
    ByteBufCodecs.optional(PlayerAnimStandardFadePlayerAnim.STREAM_CODEC), PlayerAnimationPayload::withFade,
    PlayerAnimationPayload::new);


  /**
   * 玩家动画数据包(无过度动画，无加速)
   *
   * @param controller 动画控制器id
   * @param animationId  动画id 如果为空则停止动画
   */
  public PlayerAnimationPayload(@NotNull ResourceLocation controller, ResourceLocation animationId) {
    this(controller, animationId, -1);
  }

  /**
   * 玩家动画数据包(无过度动画)
   *
   * @param controller 动画控制器id
   * @param length     播放时间 如果为-1则使用动画本身的时间
   * @param animation  动画id 如果为空则停止动画
   */
  public PlayerAnimationPayload(@NotNull ResourceLocation controller, ResourceLocation animation,
                                float length) {
    this(controller, animation, length, 0, Optional.empty());
  }

  /**
   * 玩家动画数据包
   *
   * @param controller 动画控制器id
   * @param playTime     播放时间 如果为-1则使用动画本身的时间
   * @param animationId  动画id 如果为空则停止动画
   * @param withFade   动画淡入淡出 如果为空则无淡入淡出
   */
  public PlayerAnimationPayload(@NotNull ResourceLocation controller, ResourceLocation animationId,
                                float startAnimFrom, float playTime, @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(controller, animationId, startAnimFrom, playTime, Optional.ofNullable(withFade));
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
    PlayerAnimUtil.playAnimation(context.player(), data.controller, data.rawAnimation);
  }

  @Override
  public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

}
