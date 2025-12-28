package ctn.imaginarycraft.client.registry;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.ModGuiLayers;
import ctn.imaginarycraft.client.gui.layers.LcDamageScreenFilterLayer;
import ctn.imaginarycraft.client.gui.layers.LeftBarLayer;
import ctn.imaginarycraft.client.gui.layers.ScreenFilterLayer;
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
    event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, ModGuiLayers.LC_DAMAGE_SCREEN_FILTER, LcDamageScreenFilterLayer.INSTANCE);
    event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, ModGuiLayers.LEFT_BAR, LeftBarLayer.INSTANCE);
    event.wrapLayer(VanillaGuiLayers.ARMOR_LEVEL, (layer) -> (guiGraphics, deltaTracker) -> {
      PoseStack pose = guiGraphics.pose();
      pose.pushPose();
      pose.translate(0, -(LeftBarLayer.INSTANCE.getHeight() + 1), 0);
      layer.render(guiGraphics, deltaTracker);
      pose.popPose();
    });
    event.registerAbove(VanillaGuiLayers.SAVING_INDICATOR, ModGuiLayers.SCREEN_FILTER, ScreenFilterLayer.INSTANCE);
  }
}
