package ctn.imaginarycraft.client.registry;

import ctn.imaginarycraft.client.ModGuiLayers;
import ctn.imaginarycraft.client.gui.layers.NewHealthBarLayer;
import ctn.imaginarycraft.client.gui.layers.RationalityBarLayer;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistryGuiLayer {
  @SubscribeEvent
  public static void register(RegisterGuiLayersEvent event) {
    event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, ModGuiLayers.RATIONALITY_BAR, RationalityBarLayer.INSTANCE);
    event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, ModGuiLayers.NEW_HEALTH_BAR, NewHealthBarLayer.INSTANCE);
//    event.wrapLayer(VanillaGuiLayers.PLAYER_HEALTH, (o) -> {
//      return o;
//    });
//    event.wrapLayer(VanillaGuiLayers.ARMOR_LEVEL, (o) -> {
//      return o;
//    });
  }
}
