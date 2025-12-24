package ctn.imaginarycraft.common.payloads.player;

import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimStandardFadePlayerAnim;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import ctn.imaginarycraft.util.UUIDFilterUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * 玩家动画数据包
 *
 * @param playPlayerUUID    进行操作的玩家
 * @param receivePlayerUUID 接收者 输入为空则发送给所有玩家
 * @param controller        动画控制器id
 * @param animationId       动画id
 * @param startAnimFrom     动画开始播放的时间
 * @param playTime          播放时间 如果为-1则使用动画本身的时间
 * @param withFade          动画淡入淡出 如果为空则无淡入淡出
 */
public record PlayerAnimationPayload(
  UUIDFilterUtil playPlayerUUID,
  Optional<UUIDFilterUtil> receivePlayerUUID,
  ResourceLocation controller,
  ResourceLocation animationId,
  float startAnimFrom, float playTime,
  Optional<PlayerAnimStandardFadePlayerAnim> withFade
) implements CustomPacketPayload {
  public static final CustomPacketPayload.Type<PlayerAnimationPayload> TYPE = new CustomPacketPayload.Type<>(ImaginaryCraft.modRl("player_animation_payload"));
  public static final StreamCodec<ByteBuf, PlayerAnimationPayload> STREAM_CODEC = NeoForgeStreamCodecs.composite(
    UUIDFilterUtil.STREAM_CODEC, PlayerAnimationPayload::playPlayerUUID,
    ByteBufCodecs.optional(UUIDFilterUtil.STREAM_CODEC), PlayerAnimationPayload::receivePlayerUUID,
    ResourceLocation.STREAM_CODEC, PlayerAnimationPayload::controller,
    ResourceLocation.STREAM_CODEC, PlayerAnimationPayload::animationId,
    ByteBufCodecs.FLOAT, PlayerAnimationPayload::playTime,
    ByteBufCodecs.FLOAT, PlayerAnimationPayload::startAnimFrom,
    ByteBufCodecs.optional(PlayerAnimStandardFadePlayerAnim.STREAM_CODEC), PlayerAnimationPayload::withFade,
    PlayerAnimationPayload::new);


  public PlayerAnimationPayload(Player player, @NotNull ResourceLocation controller,
                                ResourceLocation animationId) {
    this(player, controller, animationId, -1);
  }

  public PlayerAnimationPayload(Player player, @NotNull ResourceLocation controller,
                                ResourceLocation animationId, float length) {
    this(player, controller, animationId, length, 0, null);
  }

  public PlayerAnimationPayload(Player player, @NotNull ResourceLocation controller,
                                ResourceLocation animationId, float startAnimFrom, float playTime,
                                @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(UUIDFilterUtil.create().addWhite(player), Optional.empty(), controller, animationId, startAnimFrom, playTime, Optional.ofNullable(withFade));
  }

  public PlayerAnimationPayload(UUIDFilterUtil playPlayerUUID, @Nullable UUIDFilterUtil receivePlayerUUID,
                                @NotNull ResourceLocation controller, ResourceLocation animationId,
                                float startAnimFrom, float playTime,
                                @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(playPlayerUUID, Optional.ofNullable(receivePlayerUUID), controller, animationId, startAnimFrom, playTime, Optional.ofNullable(withFade));
  }

  public static void toServer(final PlayerAnimationPayload data, final IPayloadContext context) {
    PlayerStopAnimationPayload.getPlayers(data.receivePlayerUUID, context, ServerPlayer.class).forEach(p ->
      PayloadUtil.sendToClient(p, data));
  }

  public static void toClient(final PlayerAnimationPayload data, final IPayloadContext context) {
    PlayerStopAnimationPayload.getPlayers(data.playPlayerUUID, context, AbstractClientPlayer.class).forEach(p ->
      PlayerAnimUtil.playAnimationClient(p, data.controller, data.animationId, data.startAnimFrom, data.playTime, data.withFade.orElse(null)));
  }

  @Override
  public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

}
