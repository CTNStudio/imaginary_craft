package ctn.imaginarycraft.client.events;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.culling.Frustum;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class GameRenderEvents {

  @SubscribeEvent
  public static void levelRender(final RenderLevelStageEvent event) {
    RenderLevelStageEvent.Stage stage = event.getStage();
    Minecraft minecraft = Minecraft.getInstance();
    ClientLevel level = minecraft.level;
    Frustum frustum = event.getFrustum();
    PoseStack pose = event.getPoseStack();
    Camera camera = event.getCamera();
    DeltaTracker partialTick = event.getPartialTick();
  }
}
