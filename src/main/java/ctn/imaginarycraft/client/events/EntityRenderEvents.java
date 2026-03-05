package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.client.eventexecute.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.player.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class EntityRenderEvents {
  @SubscribeEvent
  public static void onRenderPlayerEventPre(RenderPlayerEvent.Pre event) {
    Player entity = event.getEntity();
    Minecraft instance = Minecraft.getInstance();
    EntityRenderDispatcher entityRenderDispatcher = instance.getEntityRenderDispatcher();
    PlayerRenderer renderer = (PlayerRenderer) (EntityRenderer<?>) entityRenderDispatcher.getRenderer(entity);
    EntityRenderEventExecute.hiddenParts(entity, renderer.getModel());
  }
}
