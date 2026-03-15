package ctn.imaginarycraft.core.registry.client;

import ctn.imaginarycraft.client.renderer.entity.GrantUsLoveRenderer;
import ctn.imaginarycraft.client.renderer.entity.MagicBulletRenderer;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.entity.AbnormalitiesEntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class EntityRenderersRegistry {
  @SubscribeEvent
  public static void registry(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(AbnormalitiesEntityTypes.GRANT_US_LOVE.get(), GrantUsLoveRenderer::new);
    event.registerEntityRenderer(AbnormalitiesEntityTypes.MAGIC_BULLET_ENTITY.get(), MagicBulletRenderer::new);
  }
}
