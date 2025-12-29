package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.client.renderer.item.MagicBulletMagicCircleRenderer;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class PlayerRenderEvents {

  @SubscribeEvent
  public static void playerRender(final RenderPlayerEvent.Post event) {
    MultiBufferSource multiBufferSource = event.getMultiBufferSource();
    float partialTick = event.getPartialTick();
    AbstractClientPlayer entity = (AbstractClientPlayer) event.getEntity();
    MagicBulletMagicCircleRenderer.magicBulletMagicCircle(entity, partialTick, multiBufferSource);
  }
}
