package ctn.imaginarycraft.common.payload.tos;

import ctn.imaginarycraft.api.IPlayerItemAttackClick;
import ctn.imaginarycraft.common.payload.api.ToServerPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import ctn.imaginarycraft.util.PlayerKeyClickUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record PlayerKeyClickPayload(
  String keyName,
  PlayerKeyClickUtil.ClickState clickState
) implements ToServerPayload {
  public static final Type<PlayerKeyClickPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_key_click_payload"));

  public static final StreamCodec<ByteBuf, PlayerKeyClickPayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.STRING_UTF8, PlayerKeyClickPayload::keyName,
    PlayerKeyClickUtil.ClickState.STREAM_CODEC, PlayerKeyClickPayload::clickState,
    PlayerKeyClickPayload::new);

  public static void send(String keyName, PlayerKeyClickUtil.ClickState clickState) {
    PayloadUtil.sendToServer(new PlayerKeyClickPayload(keyName, clickState));
  }

  @Override
  public void work(ServerPlayer player) {
    PlayerKeyClickUtil.setClickState(player, keyName, clickState);
    if (player.getMainHandItem().getItem() instanceof IPlayerItemAttackClick click) {
      PlayerKeyClickUtil.itemClick(player, clickState, click, true);
    } else if (player.getOffhandItem().getItem() instanceof IPlayerItemAttackClick click) {
      PlayerKeyClickUtil.itemClick(player, clickState, click, false);
    }
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
