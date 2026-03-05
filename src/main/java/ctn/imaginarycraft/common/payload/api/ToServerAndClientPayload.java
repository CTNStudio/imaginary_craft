package ctn.imaginarycraft.common.payload.api;

import net.minecraft.client.player.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.network.handling.*;

public interface ToServerAndClientPayload extends CustomPacketPayload {
  default void handle(IPayloadContext context) {
    context.enqueueWork(() -> work(context)).exceptionally(e -> null);
  }

  default void work(IPayloadContext context) {
    Player player = context.player();
    if (player instanceof AbstractClientPlayer abstractClientPlayer) {
      toClient(abstractClientPlayer);
    } else if (player instanceof ServerPlayer serverPlayer) {
      toServer(serverPlayer);
    }
  }

  void toServer(ServerPlayer serverPlayer);

  void toClient(AbstractClientPlayer clientPlayer);
}
