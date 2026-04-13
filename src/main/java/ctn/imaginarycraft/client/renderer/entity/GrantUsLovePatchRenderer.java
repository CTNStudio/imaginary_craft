package ctn.imaginarycraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.api.client.renderer.entity.EmptyLivingEntityRenderer;
import ctn.imaginarycraft.api.client.renderer.entity.EmptyMobRenderer;
import ctn.imaginarycraft.api.client.renderer.entity.JointLivingEntityPartRenderer;
import ctn.imaginarycraft.api.client.renderer.entity.MultiJointMobPartRenderer;
import ctn.imaginarycraft.client.animmodels.mesh.GrantUsLoveMesh;
import ctn.imaginarycraft.client.model.entity.EmptyEntityModel;
import ctn.imaginarycraft.client.model.entity.ModGeoEntityModel;
import ctn.imaginarycraft.client.renderer.renderlayer.GlowmaskModelRenderPatchedLayer;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.GrantUsLove;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.GrantUsLovePatch;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.GrantUsLoveTentacle;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.GrantUsLoveTentaclePatch;
import ctn.imaginarycraft.init.epicfight.animmodels.ModMeshes;
import ctn.imaginarycraft.init.world.entity.OrdealsEntityTypes;
import ctn.imaginarycraft.util.ModUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

// TODO 死亡之后光芒变暗
public class GrantUsLovePatchRenderer extends MultiJointMobPartRenderer<GrantUsLove, GrantUsLovePatch, EmptyEntityModel<GrantUsLove>, EmptyMobRenderer<GrantUsLove>, GrantUsLoveMesh> {
	private static final ResourceLocation GLOWMASK_TEXTURE = ModGeoEntityModel.getTexturePath("grant_us_love_glowmask");
	private final float[] glowmaskValue = {1F};

	public GrantUsLovePatchRenderer(EntityRendererProvider.Context context) {
		super(context, OrdealsEntityTypes.GRANT_US_LOVE.get(), ModMeshes.GRANT_US_LOVE);
		addCustomLayer((mesh) -> new GlowmaskModelRenderPatchedLayer<>(mesh, GLOWMASK_TEXTURE, this.glowmaskValue));
	}

	@Override
	public void render(GrantUsLove entity, GrantUsLovePatch entitypatch, EmptyMobRenderer<GrantUsLove> renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
		this.glowmaskValue[0] = ModUtils.calculateSineCycle(0.9f, 2f, 4.0f);
		super.render(entity, entitypatch, renderer, buffer, poseStack, packedLight, partialTicks);
	}

	public static class GrantUsLovePatchTentacleRenderer extends JointLivingEntityPartRenderer<GrantUsLoveTentacle, GrantUsLoveTentaclePatch, EmptyEntityModel<GrantUsLoveTentacle>, EmptyLivingEntityRenderer<GrantUsLoveTentacle>, GrantUsLoveMesh> {
		private final float[] glowmaskValue = {1F};

		public GrantUsLovePatchTentacleRenderer(EntityRendererProvider.Context context) {
			super(context, OrdealsEntityTypes.GRANT_US_LOVE_TENTACLE.get(), ModMeshes.GRANT_US_LOVE);
			addCustomLayer((mesh) -> new GlowmaskModelRenderPatchedLayer<>(mesh, GLOWMASK_TEXTURE, this.glowmaskValue));
		}

		@Override
		public void render(GrantUsLoveTentacle entity, GrantUsLoveTentaclePatch entitypatch, EmptyLivingEntityRenderer<GrantUsLoveTentacle> renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
			this.glowmaskValue[0] = ModUtils.calculateSineCycle(0.9f, 2f, 4.0f);
			super.render(entity, entitypatch, renderer, buffer, poseStack, packedLight, partialTicks);
		}
	}
}
