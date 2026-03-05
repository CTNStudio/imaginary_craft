package ctn.imaginarycraft.common.payload.api;

import net.minecraft.client.player.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.network.handling.*;

public interface ToClientPayload extends ToServerAndClientPayload {
  @Override
  default void work(IPayloadContext context) {
    if (context.player().isLocalPlayer()) {
      work(context.player());
    }
  }

  @Override
  default void toClient(AbstractClientPlayer player) {
    work(player);
  }

  @Override
  default void toServer(ServerPlayer serverPlayer) {
  }

  void work(Player player);
}
