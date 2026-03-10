package ctn.imaginarycraft.core.registry.client;

import ctn.imaginarycraft.client.renderer.entity.*;
import ctn.imaginarycraft.common.world.entity.abnormalities.ordeals.violet.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.init.world.entity.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class EntityRenderersRegistry {
  @SubscribeEvent
  public static void registry(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(AbnormalitiesEntityTypes.GRANT_US_LOVE.get(), GrantUsLove.GrantUsLoveRenderer::new);
    event.registerEntityRenderer(
      AbnormalitiesEntityTypes.MAGIC_BULLET_ENTITY.get(),
      MagicBulletRenderer::new
    );

  }
}
