package ctn.imaginarycraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import ctn.imaginarycraft.common.world.entity.projectile.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

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
