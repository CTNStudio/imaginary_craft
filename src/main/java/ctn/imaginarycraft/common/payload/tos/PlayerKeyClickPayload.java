package ctn.imaginarycraft.common.payload.tos;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.common.payload.api.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.util.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;

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
