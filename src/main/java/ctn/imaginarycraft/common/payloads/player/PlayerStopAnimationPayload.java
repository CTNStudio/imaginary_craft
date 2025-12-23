package ctn.imaginarycraft.common.payloads.player;

import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PlayerStopAnimationPayload(
  ResourceLocation controller) implements CustomPacketPayload {
  public static final Type<PlayerStopAnimationPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_stop_animation_payload"));

  public static final StreamCodec<ByteBuf, PlayerStopAnimationPayload> STREAM_CODEC = StreamCodec.composite(
    ResourceLocation.STREAM_CODEC, PlayerStopAnimationPayload::controller,
    PlayerStopAnimationPayload::new);

  public static void toServer(final PlayerStopAnimationPayload data, final IPayloadContext context) {
  }

  public static void toClient(final PlayerStopAnimationPayload data, final IPayloadContext context) {
    PlayerAnimUtil.stopClient((AbstractClientPlayer) context.player(), data.controller);
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
