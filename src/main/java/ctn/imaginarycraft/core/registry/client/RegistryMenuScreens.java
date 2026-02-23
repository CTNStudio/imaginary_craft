package ctn.imaginarycraft.core.registry.client;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistryMenuScreens {
  @SubscribeEvent
  public static void registry(RegisterMenuScreensEvent event) {
  }
}
