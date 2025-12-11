package ctn.imaginarycraft.client.registry;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistrarClientTooltipComponentFactories {
  @SubscribeEvent
  public static void registrar(RegisterClientTooltipComponentFactoriesEvent event) {
  }
}
