package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.client.eventexecute.EntityRenderEventExecute;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

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
