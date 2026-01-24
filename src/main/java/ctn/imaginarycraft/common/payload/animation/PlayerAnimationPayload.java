package ctn.imaginarycraft.common.payload.animation;

import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimStandardFadePlayerAnim;
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
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * 玩家动画数据包
 *
 */
public final class PlayerAnimationPayload extends IPlayerAnimationPayload {
  public static final Type<PlayerAnimationPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_animation_payload"));
  public static final StreamCodec<ByteBuf, PlayerAnimationPayload> STREAM_CODEC = CompositeStreamCodecBuilder.<ByteBuf, PlayerAnimationPayload>builder()
    .withComponent(UUIDFilterUtil.STREAM_CODEC, PlayerAnimationPayload::getPlayPlayerUUID)
    .withComponent(ByteBufCodecs.optional(UUIDFilterUtil.STREAM_CODEC), (payload) -> Optional.ofNullable(payload.getReceivePlayerUUID()))
    .withComponent(ResourceLocation.STREAM_CODEC, PlayerAnimationPayload::controllerId)
    .withComponent(ResourceLocation.STREAM_CODEC, PlayerAnimationPayload::animationId)
    .withComponent(ByteBufCodecs.FLOAT, PlayerAnimationPayload::startAnimFrom)
    .withComponent(ByteBufCodecs.FLOAT, PlayerAnimationPayload::playSpeed)
    .withComponent(ByteBufCodecs.optional(PlayerAnimStandardFadePlayerAnim.STREAM_CODEC), PlayerAnimationPayload::getWithFade)
    .withComponent(ByteBufCodecs.BOOL, PlayerAnimationPayload::reverse)
    .decoderFactory(
      decoder -> new PlayerAnimationPayload(
        (UUIDFilterUtil) decoder.next(),
        ((Optional<UUIDFilterUtil>) decoder.next()).orElse(null),
        (ResourceLocation) decoder.next(),
        (ResourceLocation) decoder.next(),
        (Float) decoder.next(),
        (Float) decoder.next(),
        ((Optional<PlayerAnimStandardFadePlayerAnim>) decoder.next()).orElse(null),
        (Boolean) decoder.next()
      )).build();
  private final ResourceLocation animationId;
  private final float startAnimFrom;
  private final float playSpeed;
  private final @Nullable PlayerAnimStandardFadePlayerAnim withFade;
  private final boolean reverse;

  /**
   * @param playPlayerUUID    进行操作的玩家
   * @param receivePlayerUUID 接收者 输入为空则发送给所有玩家
   * @param controllerId      动画控制器id
   * @param animationId       动画id
   * @param startAnimFrom     动画开始播放的时间
   * @param playSpeed         播放速度 如果为-1则使用动画本身的时间
   * @param withFade          动画淡入淡出 如果为空则无淡入淡出
   */
  public PlayerAnimationPayload(
    UUIDFilterUtil playPlayerUUID,
    UUIDFilterUtil receivePlayerUUID,
    ResourceLocation controllerId,
    ResourceLocation animationId,
    float startAnimFrom,
    float playSpeed,
    @Nullable PlayerAnimStandardFadePlayerAnim withFade,
    boolean reverse
  ) {
    super(playPlayerUUID, receivePlayerUUID, controllerId);
    this.animationId = animationId;
    this.startAnimFrom = startAnimFrom;
    this.playSpeed = playSpeed;
    this.withFade = withFade;
    this.reverse = reverse;
  }

  @Override
  public void toServer(ServerPlayer serverPlayer) {
    PayloadUtil.sendToClient(serverPlayer, this);
  }

  @Override
  public void toClient(AbstractClientPlayer clientPlayer) {
    PlayerAnimationUtil.playAnimationClient(clientPlayer,
      getControllerId(),
      animationId,
      startAnimFrom,
      playSpeed,
      withFade,
      reverse);
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public ResourceLocation controllerId() {
    return getControllerId();
  }

  public ResourceLocation animationId() {
    return animationId;
  }

  public float startAnimFrom() {
    return startAnimFrom;
  }

  public float playSpeed() {
    return playSpeed;
  }

  public Optional<PlayerAnimStandardFadePlayerAnim> getWithFade() {
    return Optional.ofNullable(withFade);
  }

  public boolean reverse() {
    return reverse;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPlayPlayerUUID(), getReceivePlayerUUID(), getControllerId(), animationId, startAnimFrom, playSpeed, withFade, reverse);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (PlayerAnimationPayload) obj;
    return Objects.equals(this.getPlayPlayerUUID(), that.getPlayPlayerUUID()) &&
      Objects.equals(this.getReceivePlayerUUID(), that.getReceivePlayerUUID()) &&
      Objects.equals(this.getControllerId(), that.getControllerId()) &&
      Objects.equals(this.animationId, that.animationId) &&
      Float.floatToIntBits(this.startAnimFrom) == Float.floatToIntBits(that.startAnimFrom) &&
      Float.floatToIntBits(this.playSpeed) == Float.floatToIntBits(that.playSpeed) &&
      Objects.equals(this.withFade, that.withFade) &&
      this.reverse == that.reverse;
  }

  @Override
  public String toString() {
    return "PlayerAnimationPayload[" +
      "playPlayerUUID=" + getPlayPlayerUUID() + ", " +
      "receivePlayerUUID=" + getReceivePlayerUUID() + ", " +
      "controllerId=" + getControllerId() + ", " +
      "animationId=" + animationId + ", " +
      "startAnimFrom=" + startAnimFrom + ", " +
      "playSpeed=" + playSpeed + ", " +
      "withFade=" + withFade + ", " +
      "reverse=" + reverse + ']';
  }


  public static class Builder {
    private UUIDFilterUtil playPlayerUUID = null;
    private UUIDFilterUtil receivePlayerUUID = null;
    private ResourceLocation controllerId = null;
    private ResourceLocation animationId = null;
    private float startAnimFrom = 0;
    private float playSpeed = 1;
    private PlayerAnimStandardFadePlayerAnim withFade = null;
    private boolean reverse = false;

    public static Builder create(Player player, ResourceLocation controller, ResourceLocation animationId) {
      return new Builder()
        .playPlayerUUID(player)
        .animationId(animationId)
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

    public Builder animationId(ResourceLocation animationId) {
      this.animationId = animationId;
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

    public Builder reverse() {
      this.reverse = true;
      return this;
    }

    public Builder reverse(boolean reverse) {
      this.reverse = reverse;
      return this;
    }

    public PlayerAnimationPayload build() {
      assert controllerId != null : "controllerId cannot be null";
      assert playPlayerUUID != null : "playPlayerUUID cannot be null";
      assert animationId != null : "animationId cannot be null";
      return new PlayerAnimationPayload(
        playPlayerUUID,
        receivePlayerUUID,
        controllerId,
        animationId,
        startAnimFrom,
        playSpeed,
        withFade,
        reverse);
    }
  }
}
