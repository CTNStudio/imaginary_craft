package ctn.imaginarycraft.core.registry.client;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.ModGuiLayers;
import ctn.imaginarycraft.client.gui.hudlayers.GunChargeUpHudLayer;
import ctn.imaginarycraft.client.gui.hudlayers.LeftBarLayer;
import ctn.imaginarycraft.client.gui.hudlayers.screenfilter.LcDamageScreenFilterLayer;
import ctn.imaginarycraft.client.gui.hudlayers.screenfilter.RationalityScreenFilterLayer;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class GuiLayerRegistry {
  @SubscribeEvent
  public static void register(RegisterGuiLayersEvent event) {
    event.registerAbove(VanillaGuiLayers.CROSSHAIR, ModGuiLayers.GUN_CHARGE_UP_HUD_LAYER_CROSSHAIR, GunChargeUpHudLayer.INSTANCE_CROSSHAIR);
    event.registerAbove(VanillaGuiLayers.HOTBAR, ModGuiLayers.GUN_CHARGE_UP_HUD_LAYER_HOTBAR, GunChargeUpHudLayer.INSTANCE_HOTBAR);
    event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, ModGuiLayers.LC_DAMAGE_SCREEN_FILTER, LcDamageScreenFilterLayer.INSTANCE);
    event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, ModGuiLayers.LEFT_BAR, LeftBarLayer.INSTANCE);
    event.wrapLayer(VanillaGuiLayers.ARMOR_LEVEL, (layer) -> (guiGraphics, deltaTracker) -> {
      PoseStack pose = guiGraphics.pose();
      pose.pushPose();
      pose.translate(0, -(LeftBarLayer.INSTANCE.getHeight() + 1), 0);
      layer.render(guiGraphics, deltaTracker);
      pose.popPose();
    });
    event.registerAbove(VanillaGuiLayers.SAVING_INDICATOR, ModGuiLayers.SCREEN_FILTER, RationalityScreenFilterLayer.INSTANCE);
  }
}
