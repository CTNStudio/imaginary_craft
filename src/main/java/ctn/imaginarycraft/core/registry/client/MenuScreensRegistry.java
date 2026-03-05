package ctn.imaginarycraft.core.registry.client;

import ctn.imaginarycraft.core.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class MenuScreensRegistry {
  @SubscribeEvent
  public static void registry(RegisterMenuScreensEvent event) {
  }
}
