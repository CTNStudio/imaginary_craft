package ctn.imaginarycraft.common.payload.tos;

import ctn.imaginarycraft.common.payload.api.ToServerPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record PlayerKeyClickPayload() implements ToServerPayload {
  @Override
  public void work(ServerPlayer player) {

  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return null;
  }
}
