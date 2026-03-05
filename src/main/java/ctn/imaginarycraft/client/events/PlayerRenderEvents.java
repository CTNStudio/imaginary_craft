package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.client.renderer.item.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;

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
