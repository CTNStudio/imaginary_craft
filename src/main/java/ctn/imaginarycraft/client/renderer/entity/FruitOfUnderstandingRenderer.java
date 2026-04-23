package ctn.imaginarycraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import ctn.imaginarycraft.client.model.entity.ModGeoEntityModel;
import ctn.imaginarycraft.client.renderer.renderlayer.AutoGlowingRenderLayer;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.FruitOfUnderstanding;
import ctn.imaginarycraft.util.ModUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FruitOfUnderstandingRenderer extends GeoEntityRenderer<FruitOfUnderstanding> {
	protected final Float[] glowmaskValue = {1f};

	public FruitOfUnderstandingRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ModGeoEntityModel<>("fruit_of_understanding"));
		addRenderLayer(new AutoGlowingRenderLayer<>(this, glowmaskValue));
	}

	@Override
	public void defaultRender(PoseStack poseStack, FruitOfUnderstanding animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
		super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
		this.glowmaskValue[0] = ModUtils.calculateSineCycle(0.8f, 1.2f, 1f);
	}

	public static class FruitBulletRenderer extends GeoEntityRenderer<FruitOfUnderstanding.FruitBullet> {
		protected final Float[] glowmaskValue = {1f};

		public FruitBulletRenderer(EntityRendererProvider.Context renderManager) {
			super(renderManager, new ModGeoEntityModel<>("fruit_bullet"));
			addRenderLayer(new AutoGlowingRenderLayer<>(this, glowmaskValue));
		}

		@Override
		public RenderType getRenderType(FruitOfUnderstanding.FruitBullet animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
			return RenderType.entityTranslucentEmissive(texture);
		}

		@Override
		public void defaultRender(PoseStack poseStack, FruitOfUnderstanding.FruitBullet animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
			super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
		}

		@Override
		public void preRender(PoseStack poseStack, FruitOfUnderstanding.FruitBullet animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
			Vec3 movement = animatable.getDeltaMovement();
			if (movement.length() > 0.0001) {
				float yaw = (float) (Math.atan2(movement.z, movement.x) * 180.0 / Math.PI);
				float pitch = (float) (Math.atan2(movement.y, movement.horizontalDistance()) * 180.0 / Math.PI);

				poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
				poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
			} else {
				poseStack.mulPose(Axis.YP.rotationDegrees(-animatable.getYRot()));
				poseStack.mulPose(Axis.XP.rotationDegrees(animatable.getXRot()));
			}
			super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
		}
	}
}
