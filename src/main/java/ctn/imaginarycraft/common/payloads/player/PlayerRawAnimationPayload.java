package ctn.imaginarycraft.common.payloads.player;

import com.zigythebird.playeranimcore.animation.Animation;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimRawAnimation;
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
 * @param startAnimFrom     动画开始播放的时间
 * @param rawAnimation      动画链
 * @param withFade          动画淡入淡出 如果为空则无淡入淡出
 */
public record PlayerRawAnimationPayload(
  UUIDFilterUtil playPlayerUUID,
  Optional<UUIDFilterUtil> receivePlayerUUID,
  ResourceLocation controller,
  PlayerAnimRawAnimation rawAnimation,
  float startAnimFrom,
  Optional<PlayerAnimStandardFadePlayerAnim> withFade
) implements CustomPacketPayload {
  public static final CustomPacketPayload.Type<PlayerRawAnimationPayload> TYPE = new CustomPacketPayload.Type<>(ImaginaryCraft.modRl("player_raw_animation_payload"));

  public static final StreamCodec<ByteBuf, PlayerRawAnimationPayload> STREAM_CODEC = StreamCodec.composite(
    UUIDFilterUtil.STREAM_CODEC, PlayerRawAnimationPayload::playPlayerUUID,
    ByteBufCodecs.optional(UUIDFilterUtil.STREAM_CODEC), PlayerRawAnimationPayload::receivePlayerUUID,
    ResourceLocation.STREAM_CODEC, PlayerRawAnimationPayload::controller,
    PlayerAnimRawAnimation.STREAM_CODEC, PlayerRawAnimationPayload::rawAnimation,
    ByteBufCodecs.FLOAT, PlayerRawAnimationPayload::startAnimFrom,
    ByteBufCodecs.optional(PlayerAnimStandardFadePlayerAnim.STREAM_CODEC), PlayerRawAnimationPayload::withFade,
    PlayerRawAnimationPayload::new);

  public PlayerRawAnimationPayload(Player player, @NotNull ResourceLocation controller,
                                   @Nullable ResourceLocation animationId) {
    this(player, controller, animationId, 0);
  }

  public PlayerRawAnimationPayload(Player player, @NotNull ResourceLocation controller,
                                   @Nullable ResourceLocation animationId,
                                   float startAnimFrom) {
    this(player, controller, animationId, startAnimFrom, null);
  }

  public PlayerRawAnimationPayload(Player player, @NotNull ResourceLocation controller,
                                   ResourceLocation animationId, float startAnimFrom,
                                   @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(player, controller, PlayerAnimRawAnimation.begin().then(animationId, Animation.LoopType.DEFAULT), startAnimFrom, withFade);
  }

  public PlayerRawAnimationPayload(Player player, @NotNull ResourceLocation controller,
                                   PlayerAnimRawAnimation rawAnimation, float startAnimFrom,
                                   @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(player, null, controller, rawAnimation, startAnimFrom, withFade);
  }

  public PlayerRawAnimationPayload(Player player, @Nullable UUIDFilterUtil receivePlayerUUID,
                                   @NotNull ResourceLocation controller, PlayerAnimRawAnimation rawAnimation,
                                   float startAnimFrom, @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(UUIDFilterUtil.create().addWhite(player), Optional.ofNullable(receivePlayerUUID), controller, rawAnimation, startAnimFrom, Optional.ofNullable(withFade));
  }

  public PlayerRawAnimationPayload(UUIDFilterUtil playPlayerUUID, @Nullable UUIDFilterUtil receivePlayerUUID,
                                   @NotNull ResourceLocation controller, PlayerAnimRawAnimation rawAnimation,
                                   float startAnimFrom, @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(playPlayerUUID, Optional.ofNullable(receivePlayerUUID), controller, rawAnimation, startAnimFrom, Optional.ofNullable(withFade));
  }

  public static void toServer(final PlayerRawAnimationPayload data, final IPayloadContext context) {
    PlayerStopAnimationPayload.getPlayers(data.receivePlayerUUID, context, ServerPlayer.class).forEach(p ->
      PayloadUtil.sendToClient(p, data));
  }

  public static void toClient(final PlayerRawAnimationPayload data, final IPayloadContext context) {
    PlayerStopAnimationPayload.getPlayers(data.playPlayerUUID, context, AbstractClientPlayer.class).forEach(p ->
      PlayerAnimUtil.playRawAnimationClient(p, data.controller, data.rawAnimation, data.startAnimFrom, data.withFade.orElse(null)));
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
