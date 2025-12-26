package ctn.imaginarycraft.client.events;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.ModRenderTypes;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class GameRenderEvents {
  public static final RenderType RENDER_TYPE = ModRenderTypes.magicBullet(ImaginaryCraft.modRl("textures/particle/magic_bullet/magic_circle16x.png"));
  public static final RenderType RENDER_TYPE1 = ModRenderTypes.magicBullet(ImaginaryCraft.modRl("textures/particle/magic_bullet/magic_circle32x.png"));
  public static final RenderType RENDER_TYPE2 = ModRenderTypes.magicBullet(ImaginaryCraft.modRl("textures/particle/magic_bullet/magic_circle128x.png"));

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

  @SubscribeEvent
  public static void playerRender(final RenderPlayerEvent.Post event) {
//    MultiBufferSource multiBufferSource = event.getMultiBufferSource();
//
//    AbstractClientPlayer entity = (AbstractClientPlayer) event.getEntity();
//    PlayerAnimationController controller = PlayerAnimUtil.getPlayerAnimationController(entity, PlayerAnimUtil.NORMAL_STATE);
//    if (controller == null) {
//      return;
//    }
//
////    if (!controller.isActive()) {
////      return;
////    }
//
////    Animation animation = controller.getCurrentAnimationInstance();
////    if (animation == null) {
////      return;
////    }
//
//    if (!(entity.getWeaponItem().getItem() instanceof MagicBulletWeaponItem)) {
//      return;
//    }
//
//    String boneName = "right_item";
//    AdvancedPlayerAnimBone bone = controller.getBone(boneName);
//
//    if (bone == null) {
//      return;
//    }
//
//    Minecraft minecraft = Minecraft.getInstance();
//    VertexConsumer buffer = multiBufferSource.getBuffer(RENDER_TYPE);
//
//    PoseStack poseStack = event.getPoseStack();
//    float partialTick = event.getPartialTick();
//    PoseStack bonePoseStack = controller.getBoneWorldPositionPoseStack(boneName, partialTick, minecraft.gameRenderer.getMainCamera().getPosition());
//
//    int lightColor = LightTexture.FULL_BRIGHT;
//    bonePoseStack.pushPose();
//    Vec3f positionVector = bone.getPositionVector();
//    float z = positionVector.z() / 16;
//    float y = positionVector.y() / 16;
//    float x = positionVector.x() / 16;
//    bonePoseStack.translate(x, y, z);
//    bonePoseStack.translate(0, 0, -1.3125f);
////    RenderUtil.translateMatrixToBone(bonePoseStack, bone);
//    PoseStack.Pose last = bonePoseStack.last();
//    RendererUtil.renderRotatedQuad(buffer, last, 0, 0, 0, 0.7f, lightColor, 1, 1, 1, 1);
//
//    bonePoseStack.popPose();
  }
}
