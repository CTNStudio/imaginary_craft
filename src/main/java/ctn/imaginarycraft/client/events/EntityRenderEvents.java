package ctn.imaginarycraft.client.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import ctn.imaginarycraft.api.event.client.PatchedEntityRendererEvent;
import ctn.imaginarycraft.client.eventexecute.EntityRenderEventExecute;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.FruitOfUnderstandingBullet;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.entity.ProjectileEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

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

  @SubscribeEvent
  public static void renderLivingEventPost(RenderLivingEvent.Post<?, ?> event) {
    LivingEntity entity = event.getEntity();
    PoseStack poseStack = event.getPoseStack();
    MultiBufferSource multiBufferSource = event.getMultiBufferSource();
    int packedLight = event.getPackedLight();
    ShieldRenderer.renderShieldIfPresent(entity, poseStack, multiBufferSource, packedLight);
  }

  @SubscribeEvent
  public static void renderLivingEventPost(PatchedEntityRendererEvent.Post<?, ?, ?, ?> event) {
    LivingEntity entity = event.getLivingEntity();
    PoseStack poseStack = event.getPoseStack();
    MultiBufferSource multiBufferSource = event.getBuffer();
    int packedLight = event.getPackedLight();
    ShieldRenderer.renderShieldIfPresent(entity, poseStack, multiBufferSource, packedLight);
  }

  @SubscribeEvent
  public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(
      ProjectileEntityTypes.FRUIT_OF_UNDERSTANDING_BULLET.get(),
      context -> new GeoEntityRenderer<>(context, FruitOfUnderstandingBullet.getModel()) {
        @Override
        public RenderType getRenderType(FruitOfUnderstandingBullet animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
          return RenderType.entityTranslucentEmissive(texture);
        }

        @Override
        public void render(FruitOfUnderstandingBullet entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
          poseStack.pushPose();

          Vec3 movement = entity.getDeltaMovement();
          if (movement.length() > 0.0001) {
            float yaw = (float)(Math.atan2(movement.z, movement.x) * 180.0 / Math.PI);
            float pitch = (float)(Math.atan2(movement.y, movement.horizontalDistance()) * 180.0 / Math.PI);

            poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
          } else {
            poseStack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
          }

          super.render(entity, entityYaw, partialTick, poseStack, bufferSource, 15728880);
          poseStack.popPose();
        }
      }
    );
  }

}
