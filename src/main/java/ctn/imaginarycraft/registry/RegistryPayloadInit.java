package ctn.imaginarycraft.registry;

import ctn.imaginarycraft.common.payloads.player.PlayerAnimationPayload;
import ctn.imaginarycraft.common.payloads.player.PlayerLeftEmptyClickPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class RegistryPayloadInit {
  @SubscribeEvent
  public static void register(final RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar("1.0");
    // 接收来自服务端和客户端的数据
    registrar.playBidirectional(PlayerAnimationPayload.TYPE, PlayerAnimationPayload.STREAM_CODEC, new DirectionalPayloadHandler<>(PlayerAnimationPayload::toClient, PlayerAnimationPayload::toServer));

    // 接收来自服务端的数据

    // 接收来自客户端的数据
    registrar.playToServer(PlayerLeftEmptyClickPayload.TYPE, PlayerLeftEmptyClickPayload.STREAM_CODEC, PlayerLeftEmptyClickPayload::toServer);
    ImaginaryCraft.LOGGER.info("Registering payloads finish");
  }
}
