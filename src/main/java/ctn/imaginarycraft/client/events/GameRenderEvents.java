package ctn.imaginarycraft.client.events;

import com.mojang.blaze3d.vertex.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.culling.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;

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
