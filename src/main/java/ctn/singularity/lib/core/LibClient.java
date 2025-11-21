package ctn.singularity.lib.core;

import ctn.singularity.lib.ModMainkt;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = LibMain.LIB_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = LibMain.LIB_ID, value = Dist.CLIENT)
public final class LibClient {
  public LibClient(ModContainer container) {
    ModMainkt.modClient();
  }

  @SubscribeEvent
  static void onClientSetup(FMLClientSetupEvent event) {
    LibMain.LOGGER.error("Client {}", LibMain.LIB_NAME);
  }
}
