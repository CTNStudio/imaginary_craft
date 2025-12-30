package ctn.imaginarycraft.common.payloads.entity.player;

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
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

/**
 * 玩家动画数据包
 *
 * @param playPlayerUUID    进行操作的玩家
 * @param receivePlayerUUID 接收者 输入为空则发送给所有玩家
 * @param controllerId        动画控制器id
 * @param startAnimFrom     动画开始播放的时间
 * @param rawAnimation      动画链
 * @param withFade          动画淡入淡出 如果为空则无淡入淡出
 * @param playSpeed         播放速度
 */
public record PlayerRawAnimationPayload(
  UUIDFilterUtil playPlayerUUID,
  Optional<UUIDFilterUtil> receivePlayerUUID,
  ResourceLocation controllerId,
  PlayerAnimRawAnimation rawAnimation,
  float startAnimFrom,
  float playSpeed,
  Optional<PlayerAnimStandardFadePlayerAnim> withFade
) implements CustomPacketPayload {
  public static final CustomPacketPayload.Type<PlayerRawAnimationPayload> TYPE = new CustomPacketPayload.Type<>(ImaginaryCraft.modRl("player_raw_animation_payload"));

  public static final StreamCodec<ByteBuf, PlayerRawAnimationPayload> STREAM_CODEC = NeoForgeStreamCodecs.composite(
    UUIDFilterUtil.STREAM_CODEC, PlayerRawAnimationPayload::playPlayerUUID,
    ByteBufCodecs.optional(UUIDFilterUtil.STREAM_CODEC), PlayerRawAnimationPayload::receivePlayerUUID,
      ResourceLocation.STREAM_CODEC, PlayerRawAnimationPayload::controllerId,
    PlayerAnimRawAnimation.STREAM_CODEC, PlayerRawAnimationPayload::rawAnimation,
    ByteBufCodecs.FLOAT, PlayerRawAnimationPayload::startAnimFrom,
      ByteBufCodecs.FLOAT, PlayerRawAnimationPayload::playSpeed,
    ByteBufCodecs.optional(PlayerAnimStandardFadePlayerAnim.STREAM_CODEC), PlayerRawAnimationPayload::withFade,
    PlayerRawAnimationPayload::new);

  public static void toServer(final PlayerRawAnimationPayload data, final IPayloadContext context) {
    PlayerStopAnimationPayload.getPlayers(data.receivePlayerUUID, context, ServerPlayer.class).forEach(p ->
      PayloadUtil.sendToClient(p, data));
  }

  public static void toClient(final PlayerRawAnimationPayload data, final IPayloadContext context) {
    PlayerStopAnimationPayload.getPlayers(data.playPlayerUUID, context, AbstractClientPlayer.class).forEach(p ->
        PlayerAnimUtil.playRawAnimationClient(p, data.controllerId, data.rawAnimation, data.startAnimFrom, data.playSpeed, data.withFade.orElse(null)));
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public static class Builder {
    private UUIDFilterUtil playPlayerUUID = null;
    private UUIDFilterUtil receivePlayerUUID = UUIDFilterUtil.create();
    private PlayerAnimRawAnimation rawAnimation = PlayerAnimRawAnimation.begin();
    private ResourceLocation controllerId = null;
    private float startAnimFrom = 0;
    private float playSpeed = 1;
    private PlayerAnimStandardFadePlayerAnim withFade = null;

    public static Builder create(Player player, ResourceLocation controller, PlayerAnimRawAnimation rawAnimation) {
      return new Builder()
          .playPlayerUUID(player)
          .rawAnimation(rawAnimation)
          .controllerId(controller);
    }

    public Builder controllerId(ResourceLocation controller) {
      this.controllerId = controller;
      return this;
    }

    public Builder playPlayerUUID(UUIDFilterUtil playPlayerUUID) {
      this.playPlayerUUID = playPlayerUUID;
      return this;
    }

    public Builder playPlayerUUID(Player player) {
      this.playPlayerUUID = UUIDFilterUtil.create().addWhite(player);
      return this;
    }

    public Builder receivePlayerUUID(UUIDFilterUtil receivePlayerUUID) {
      this.receivePlayerUUID = receivePlayerUUID;
      return this;
    }

    public Builder receivePlayerUUID(Player player) {
      this.receivePlayerUUID = UUIDFilterUtil.create().addWhite(player);
      return this;
    }

    public Builder rawAnimation(PlayerAnimRawAnimation rawAnimation) {
      this.rawAnimation = rawAnimation;
      return this;
    }

    public Builder startAnimFrom(float startAnimFrom) {
      this.startAnimFrom = startAnimFrom;
      return this;
    }

    public Builder playSpeed(float playSpeed) {
      this.playSpeed = playSpeed;
      return this;
    }

    public Builder withFade(PlayerAnimStandardFadePlayerAnim withFade) {
      this.withFade = withFade;
      return this;
    }

    public PlayerRawAnimationPayload build() {
      assert controllerId != null : "controller cannot be null";
      assert playPlayerUUID != null : "playPlayerUUID cannot be null";
      return new PlayerRawAnimationPayload(
          playPlayerUUID,
          Optional.ofNullable(receivePlayerUUID),
          controllerId,
          rawAnimation,
          startAnimFrom,
          playSpeed,
          Optional.ofNullable(withFade));
    }
  }
}
