package ctn.singularity.lib.core;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = LibMain.LIB_ID)
public final class LibPayloadInit {
  @SubscribeEvent
  public static void register(final RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar("1.0");
    /// 接收来自服务端和客户端的数据

    /// 接收来自服务端的数据

    /// 接收来自客户端的数据
  }
}
