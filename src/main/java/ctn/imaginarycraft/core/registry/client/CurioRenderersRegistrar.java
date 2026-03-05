package ctn.imaginarycraft.core.registry.client;

import ctn.imaginarycraft.client.renderer.curios.*;
import ctn.imaginarycraft.common.world.item.ego.curio.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.init.world.item.ego.*;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.registries.*;
import top.theillusivec4.curios.api.client.*;

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
