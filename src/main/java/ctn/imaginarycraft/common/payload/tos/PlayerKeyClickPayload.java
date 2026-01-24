package ctn.imaginarycraft.common.payload.tos;

import ctn.imaginarycraft.common.payload.api.ToServerPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PlayerKeyClickUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record PlayerKeyClickPayload(String keyName, PlayerKeyClickUtil.ClickState clickState) implements ToServerPayload {
  public static final Type<PlayerKeyClickPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_key_click_payload"));

  public static final StreamCodec<ByteBuf, PlayerKeyClickPayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.STRING_UTF8, PlayerKeyClickPayload::keyName,
    PlayerKeyClickUtil.ClickState.STREAM_CODEC, PlayerKeyClickPayload::clickState,
    PlayerKeyClickPayload::new);

  @Override
  public void work(ServerPlayer player) {
    PlayerKeyClickUtil.of().setClickState(player, keyName, clickState);
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
