package ctn.imaginarycraft.events.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ctn.imaginarycraft.client.renderer.effect.shield.ShieldSphereMesh;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.ModAbsorptionShieldRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public class ShieldRenderer {
  private static final ResourceLocation SHIELD_TEXTURE =
    ResourceLocation.fromNamespaceAndPath(ImaginaryCraft.ID, "textures/entity/shield.png");
  private static final ShieldSphereMesh SPHERE_MESH = new ShieldSphereMesh();

  @SubscribeEvent
  public static void onRenderLivingPost(RenderLivingEvent.Post<?, ?> event) {
    LivingEntity entity = event.getEntity();
    renderShieldIfPresent(entity, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
  }

  private static void renderShieldIfPresent(LivingEntity entity, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
    for (var entry : ModAbsorptionShieldRegistry.getAll()) {
      if (entity.hasEffect(entry.effect())) {
        System.out.println("render shield");
        renderShield(entity, poseStack, buffer, packedLight,entry);
        break;
      }
    }
  }

  private static void renderShield(LivingEntity entity, PoseStack poseStack, MultiBufferSource buffer, int packedLight, ModAbsorptionShieldRegistry.ShieldEntry entry) {
    float radius = Math.max(entity.getBbWidth(), entity.getBbHeight()) * 1.2f;
    int color = entry.effect().value().getColor();
    float r = ((color >> 16) & 0xFF) / 255f;
    float g = ((color >> 8) & 0xFF) / 255f;
    float b = (color & 0xFF) / 255f;
    float a = 0.6f;

    poseStack.pushPose();
    poseStack.translate(0, entity.getBbHeight() * 0.5, 0);
    poseStack.scale(radius, radius, radius);

    RenderType renderType = RenderType.entityTranslucentEmissive(SHIELD_TEXTURE);
    VertexConsumer consumer = buffer.getBuffer(renderType);
    SPHERE_MESH.render(consumer, poseStack.last(), r, g, b, a, packedLight);

    poseStack.popPose();
  }
}
