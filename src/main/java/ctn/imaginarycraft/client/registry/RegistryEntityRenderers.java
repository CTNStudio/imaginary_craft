package ctn.imaginarycraft.client.registry;

import ctn.imaginarycraft.common.entity.abnormalities.AbnormalitiesEntity;
import ctn.imaginarycraft.common.entity.abnormalities.ordeals.violet.GrantUsLove;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.entiey.AbnormalitiesEntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class RegistryEntityRenderers {
  @SubscribeEvent
  public static void registry(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(AbnormalitiesEntityTypes.GRANT_US_LOVE.get(), GrantUsLove.GrantUsLoveRenderer::new);

  }
}
