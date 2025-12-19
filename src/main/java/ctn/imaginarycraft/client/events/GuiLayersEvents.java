package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class GuiLayersEvents {
  @SubscribeEvent
  public static void renderGuiLayerEventPre(RenderGuiLayerEvent.Pre event) {
    final ResourceLocation name = event.getName();
    // 关闭原版的血条
    if (ModConfig.CLIENT.enableNewHealthBar.get() && name.equals(VanillaGuiLayers.PLAYER_HEALTH)) {
      event.setCanceled(true);
    }
  }

  @SubscribeEvent
  public static void renderGuiLayerEventPost(RenderGuiLayerEvent.Post event) {

  }
}
