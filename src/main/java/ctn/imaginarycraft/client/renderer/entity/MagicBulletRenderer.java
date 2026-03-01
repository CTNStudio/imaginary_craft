package ctn.imaginarycraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.common.world.entity.projectile.MagicBulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MagicBulletRenderer extends EntityRenderer<MagicBulletEntity> {
  public MagicBulletRenderer(EntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public ResourceLocation getTextureLocation(MagicBulletEntity entity) {
    // 应该临时使用烈焰弹的贴图模型
    return ResourceLocation.fromNamespaceAndPath("imaginarycraft", "textures/particle/solemn_lament/butterfly_white.png");
  }

  @Override
  public void render(MagicBulletEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
    // 可自定义模型渲染，默认空实现（可用小球或自定义模型）
    super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
  }
}
