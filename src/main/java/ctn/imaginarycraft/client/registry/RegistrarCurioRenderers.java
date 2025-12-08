package ctn.imaginarycraft.client.registry;

import ctn.imaginarycraft.client.renderer.curios.BasicCuriosRenderer;
import ctn.imaginarycraft.common.item.ego.EgoCurioItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ModEgoCurios;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistrarCurioRenderers {
  /**
   * 注册饰品渲染
   */
  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent event) {
    ModEgoCurios.REGISTRY.getEntries().stream()
      .map(DeferredHolder::get)
      .filter(EgoCurioItem.class::isInstance)
      .map(EgoCurioItem.class::cast)
      .forEach(RegistrarCurioRenderers::register);
  }

  private static void register(EgoCurioItem item) {
    if (item.getModel() == null) {
      return;
    }
    CuriosRendererRegistry.register(item, () -> {
      BasicCuriosRenderer curiosRenderer = item.getCuriosRenderer();
      return curiosRenderer == null ? new BasicCuriosRenderer(item) : curiosRenderer;
    });
  }
}
