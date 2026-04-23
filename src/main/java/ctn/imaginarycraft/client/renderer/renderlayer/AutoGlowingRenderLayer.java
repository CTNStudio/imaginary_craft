package ctn.imaginarycraft.client.renderer.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ctn.imaginarycraft.client.ModRenderTypes;
import ctn.imaginarycraft.util.ModUtils;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class AutoGlowingRenderLayer<T extends GeoAnimatable> extends AutoGlowingGeoLayer<T> {
	protected RenderType renderType;
	protected final Float[] glowmaskValue;

	public AutoGlowingRenderLayer(GeoRenderer<T> renderer, Float[] glowmaskValue) {
		super(renderer);
		this.glowmaskValue = glowmaskValue;
	}

	@Override
	protected RenderType getRenderType(T animatable, @Nullable MultiBufferSource bufferSource) {
		if (renderType == null) {
			renderType = ModRenderTypes.glowmask(glowmaskValue, getTextureResource(animatable));
		}
		return renderType;
	}

	@Override
	public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		renderType = getRenderType(animatable, bufferSource);
		var vertexConsumer1 = bufferSource.getBuffer(renderType);
		getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, renderType,
			vertexConsumer1, partialTick, LightTexture.FULL_BRIGHT, packedOverlay,
			getRenderer().getRenderColor(animatable, partialTick, packedLight).argbInt());
	}

	@Override
	protected ResourceLocation getTextureResource(T animatable) {
		return ModUtils.getTextureLight(super.getTextureResource(animatable));
	}
}
