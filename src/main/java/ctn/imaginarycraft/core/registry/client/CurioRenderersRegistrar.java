package ctn.imaginarycraft.core.registry.client;

import ctn.imaginarycraft.client.renderer.curios.BasicCuriosRenderer;
import ctn.imaginarycraft.common.item.ego.curio.EgoCurioItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoCurioItems;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class CurioRenderersRegistrar {
  /**
   * 注册饰品渲染
   */
  @SubscribeEvent
  public static void onClientSetup(FMLClientSetupEvent event) {
    for (DeferredHolder<Item, ? extends Item> itemDeferredHolder : EgoCurioItems.REGISTRY.getEntries()) {
      if (itemDeferredHolder.get() instanceof EgoCurioItem egoCurioItem) {
        register(egoCurioItem);
      }
    }
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
