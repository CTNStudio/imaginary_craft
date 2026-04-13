package ctn.imaginarycraft.client.renderer.renderlayer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.ModelRenderLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class GlowmaskModelRenderPatchedLayer<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>, R extends RenderLayer<E, M>, AM extends SkinnedMesh> extends ModelRenderLayer<E, T, M, R, AM> {
  protected final RenderType renderType;
  private final float[] glowmaskValue;

  public GlowmaskModelRenderPatchedLayer(AssetAccessor<AM> mesh, ResourceLocation texture, float[] glowmaskValue) {
    super(mesh);
    this.renderType = RenderType.eyes(texture);
    this.glowmaskValue = glowmaskValue;
  }

	@Override
	public void renderLayer(E entityliving, T entitypatch, RenderLayer<E, M> vanillaLayer, PoseStack poseStack, MultiBufferSource buffer, int packedLight, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
		this.renderLayer(entitypatch, entityliving, this.castLayer(vanillaLayer), poseStack, buffer, packedLight, poses, bob, yRot, xRot, partialTicks);
	}

	@Override
  protected void renderLayer(T entitypatch, E entityliving, @Nullable R vanillaLayer, PoseStack poseStack, MultiBufferSource buffer, int packedLight, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
    float glowmaskValue = getGlowmaskValue();
    RenderSystem.setShaderColor(glowmaskValue, glowmaskValue, glowmaskValue, 1);
    MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
    this.mesh.get().draw(poseStack, buffer, this.renderType, LightTexture.FULL_BRIGHT, 1.0F, 1.0F, 1.0F, 1.0F, OverlayTexture.NO_OVERLAY, entitypatch.getArmature(), poses);
    bufferSource.endBatch(this.renderType);
    RenderSystem.setShaderColor(1, 1, 1, 1);
  }

  public float getGlowmaskValue() {
    return glowmaskValue[0];
  }
}
