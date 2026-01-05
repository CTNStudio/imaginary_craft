package ctn.imaginarycraft.common.payloads.entity.player;

import ctn.imaginarycraft.client.util.PlayerAnimationUtil;
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
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

/**
 * 停止播放动画
 *
 * @param playPlayerUUID           进行操作的玩家
 * @param receivePlayerUUID        接收者 输入为空则发送给所有玩家
 * @param controller               控制器
 * @param isStopTriggeredAnimation 是否是完全移除动画
 */
public record PlayerStopAnimationPayload(
  UUIDFilterUtil playPlayerUUID,
  Optional<@Nullable UUIDFilterUtil> receivePlayerUUID,
  ResourceLocation controller,
  boolean isStopTriggeredAnimation
) implements CustomPacketPayload {
  public static final Type<PlayerStopAnimationPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_stop_animation_payload"));

  public static final StreamCodec<ByteBuf, PlayerStopAnimationPayload> STREAM_CODEC = StreamCodec.composite(
    UUIDFilterUtil.STREAM_CODEC, PlayerStopAnimationPayload::playPlayerUUID,
    ByteBufCodecs.optional(UUIDFilterUtil.STREAM_CODEC), PlayerStopAnimationPayload::receivePlayerUUID,
    ResourceLocation.STREAM_CODEC, PlayerStopAnimationPayload::controller,
    ByteBufCodecs.BOOL, PlayerStopAnimationPayload::isStopTriggeredAnimation,
    PlayerStopAnimationPayload::new);

  public PlayerStopAnimationPayload(Player player, ResourceLocation controller, boolean isStopTriggeredAnimation) {
    this(UUIDFilterUtil.create().addWhite(player), controller, isStopTriggeredAnimation);
  }

  public PlayerStopAnimationPayload(UUIDFilterUtil playerUUID, ResourceLocation controller, boolean isStopTriggeredAnimation) {
    this(playerUUID, Optional.empty(), controller, isStopTriggeredAnimation);
  }

  public PlayerStopAnimationPayload(UUIDFilterUtil playPlayerUUID, @Nullable UUIDFilterUtil receivePlayerUUID, ResourceLocation controller, boolean isStopTriggeredAnimation) {
    this(playPlayerUUID, Optional.ofNullable(receivePlayerUUID), controller, isStopTriggeredAnimation);
  }

  public static void toServer(final PlayerStopAnimationPayload data, final IPayloadContext context) {
    getPlayers(data.receivePlayerUUID, context, ServerPlayer.class).forEach(serverPlayer ->
      PayloadUtil.sendToClient(serverPlayer, data));
  }

  public static void toClient(final PlayerStopAnimationPayload data, final IPayloadContext context) {
    getPlayers(data.playPlayerUUID, context, AbstractClientPlayer.class).forEach(clientPlayer ->
      PlayerAnimationUtil.stopClient(clientPlayer, data.controller, data.isStopTriggeredAnimation));
  }

  public static <T extends Player> Collection<T> getPlayers(Optional<@Nullable UUIDFilterUtil> uuidFilterUtil, IPayloadContext context, Class<T> playerClass) {
    Collection<T> players = context.player().level().players().stream().map(playerClass::cast).toList();
    return uuidFilterUtil.isEmpty() ? players : uuidFilterUtil.get().filter(players);
  }

  public static <T extends Player> Collection<T> getPlayers(UUIDFilterUtil uuidFilterUtil, IPayloadContext context, Class<T> playerClass) {
    return uuidFilterUtil.filter(context.player().level().players().stream().map(playerClass::cast).toList());
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
