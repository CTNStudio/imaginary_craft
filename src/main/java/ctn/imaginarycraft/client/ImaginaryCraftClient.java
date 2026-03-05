package ctn.imaginarycraft.client;

import ctn.imaginarycraft.core.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.client.gui.*;

@Mod(value = ImaginaryCraft.ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class ImaginaryCraftClient {

  public ImaginaryCraftClient(ModContainer container) {
    container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
  }

  @SubscribeEvent
  static void onClientSetup(FMLClientSetupEvent event) {
    ImaginaryCraft.LOGGER.info("Client {}", ImaginaryCraft.NAME);
  }
}
