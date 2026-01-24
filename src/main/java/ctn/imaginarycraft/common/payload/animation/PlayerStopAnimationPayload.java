package ctn.imaginarycraft.common.payload.animation;

import ctn.imaginarycraft.client.util.PlayerAnimationUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.network.codec.CompositeStreamCodecBuilder;
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

import java.util.Objects;
import java.util.Optional;

/**
 * 停止播放动画
 *
 */
public final class PlayerStopAnimationPayload extends IPlayerAnimationPayload {
  public static final Type<PlayerStopAnimationPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_stop_animation_payload"));

  public static final StreamCodec<ByteBuf, PlayerStopAnimationPayload> STREAM_CODEC = CompositeStreamCodecBuilder.<ByteBuf, PlayerStopAnimationPayload>builder()
    .withComponent(UUIDFilterUtil.STREAM_CODEC, PlayerStopAnimationPayload::getPlayPlayerUUID)
    .withComponent(ByteBufCodecs.optional(UUIDFilterUtil.STREAM_CODEC), (payload) -> Optional.ofNullable(payload.getReceivePlayerUUID()))
    .withComponent(ResourceLocation.STREAM_CODEC, PlayerStopAnimationPayload::controllerId)
    .withComponent(ByteBufCodecs.BOOL, PlayerStopAnimationPayload::isStopTriggeredAnimation)
    .decoderFactory(
      decoder -> new PlayerStopAnimationPayload(
        (UUIDFilterUtil) decoder.next(),
        ((Optional<UUIDFilterUtil>) decoder.next()).orElse(null),
        (ResourceLocation) decoder.next(),
        (Boolean) decoder.next()
      )).build();
  private final boolean isStopTriggeredAnimation;

  /**
   * @param playPlayerUUID           进行操作的玩家
   * @param receivePlayerUUID        接收者 输入为空则发送给所有玩家
   * @param controllerId             控制器
   * @param isStopTriggeredAnimation 是否是完全移除动画
   */
  public PlayerStopAnimationPayload(
    UUIDFilterUtil playPlayerUUID,
    UUIDFilterUtil receivePlayerUUID,
    ResourceLocation controllerId,
    boolean isStopTriggeredAnimation
  ) {
    super(playPlayerUUID, receivePlayerUUID, controllerId);
    this.isStopTriggeredAnimation = isStopTriggeredAnimation;
  }

  public PlayerStopAnimationPayload(Player player, ResourceLocation controller, boolean isStopTriggeredAnimation) {
    this(UUIDFilterUtil.create().addWhite(player), null, controller, isStopTriggeredAnimation);
  }

  public PlayerStopAnimationPayload(UUIDFilterUtil playerUUID, ResourceLocation controller, boolean isStopTriggeredAnimation) {
    this(playerUUID, null, controller, isStopTriggeredAnimation);
  }

  @Override
  public void toServer(ServerPlayer serverPlayer) {
    PayloadUtil.sendToClient(serverPlayer, this);
  }

  @Override
  public void toClient(AbstractClientPlayer clientPlayer) {
    PlayerAnimationUtil.stopClient(clientPlayer, getControllerId(), isStopTriggeredAnimation);
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public ResourceLocation controllerId() {
    return getControllerId();
  }

  public boolean isStopTriggeredAnimation() {
    return isStopTriggeredAnimation;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPlayPlayerUUID(), getReceivePlayerUUID(), getControllerId(), isStopTriggeredAnimation);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (PlayerStopAnimationPayload) obj;
    return Objects.equals(this.getPlayPlayerUUID(), that.getPlayPlayerUUID()) &&
      Objects.equals(this.getReceivePlayerUUID(), that.getReceivePlayerUUID()) &&
      Objects.equals(this.getControllerId(), that.getControllerId()) &&
      this.isStopTriggeredAnimation == that.isStopTriggeredAnimation;
  }

  @Override
  public String toString() {
    return "PlayerStopAnimationPayload[" +
      "playPlayerUUID=" + getPlayPlayerUUID() + ", " +
      "receivePlayerUUID=" + getReceivePlayerUUID() + ", " +
      "controllerId=" + getControllerId() + ", " +
      "isStopTriggeredAnimation=" + isStopTriggeredAnimation + ']';
  }
}
