package ctn.imaginarycraft.client.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.bones.AdvancedPlayerAnimBone;
import com.zigythebird.playeranimcore.math.Vec3f;
import ctn.imaginarycraft.client.ModRenderTypes;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.client.util.RendererUtil;
import ctn.imaginarycraft.common.item.ego.weapon.remote.MagicBulletWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class PlayerRenderEvents {
  public static final RenderType RENDER_TYPE = ModRenderTypes.magicBullet(ImaginaryCraft.modRl("textures/particle/magic_bullet/magic_circle16x.png"));
  public static final RenderType RENDER_TYPE1 = ModRenderTypes.magicBullet(ImaginaryCraft.modRl("textures/particle/magic_bullet/magic_circle32x.png"));
  public static final RenderType RENDER_TYPE2 = ModRenderTypes.magicBullet(ImaginaryCraft.modRl("textures/particle/magic_bullet/magic_circle128x.png"));

  @SubscribeEvent
  public static void playerRender(final RenderPlayerEvent.Post event) {
    MultiBufferSource multiBufferSource = event.getMultiBufferSource();

    AbstractClientPlayer entity = (AbstractClientPlayer) event.getEntity();
    PlayerAnimationController controller = PlayerAnimUtil.getPlayerAnimationController(entity, PlayerAnimUtil.NORMAL_STATE);
    if (controller == null) {
      return;
    }

//    if (!controller.isActive()) {
//      return;
//    }

//    Animation animation = controller.getCurrentAnimationInstance();
//    if (animation == null) {
//      return;
//    }

    if (!(entity.getWeaponItem().getItem() instanceof MagicBulletWeaponItem)) {
      return;
    }

    String boneName = "right_item";
    AdvancedPlayerAnimBone bone = controller.getBone(boneName);

    if (bone == null) {
      return;
    }

    Minecraft minecraft = Minecraft.getInstance();

    float partialTick = event.getPartialTick();
    PoseStack bonePoseStack = controller.getBoneWorldPositionPoseStack(boneName, partialTick, minecraft.gameRenderer.getMainCamera().getPosition());
    if (bonePoseStack == null) {
      return;
    }

    PoseStack poseStack = event.getPoseStack();
    VertexConsumer buffer = multiBufferSource.getBuffer(RENDER_TYPE);

    int lightColor = LightTexture.FULL_BRIGHT;
    bonePoseStack.pushPose();
    Vec3f positionVector = bone.getPositionVector();
    float z = positionVector.z() / 16;
    float y = positionVector.y() / 16;
    float x = positionVector.x() / 16;
    bonePoseStack.translate(x, y, z);
    bonePoseStack.translate(0, 0, -1.3125f);
//    RenderUtil.translateMatrixToBone(bonePoseStack, bone);
    PoseStack.Pose last = bonePoseStack.last();
    RendererUtil.renderRotatedQuad(buffer, last, 0, 0, 0, 0.7f, lightColor, 1, 1, 1, 1);

    bonePoseStack.popPose();
  }
}
