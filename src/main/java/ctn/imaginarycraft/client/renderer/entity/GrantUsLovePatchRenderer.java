package ctn.imaginarycraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.model.entity.EmptyEntityModel;
import ctn.imaginarycraft.client.model.entity.GrantUsLoveMesh;
import ctn.imaginarycraft.client.model.entity.ModGeoEntityModel;
import ctn.imaginarycraft.client.renderer.renderlayer.GlowmaskModelRenderPatchedLayer;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.GrantUsLove;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.GrantUsLovePatch;
import ctn.imaginarycraft.init.ModMeshes;
import ctn.imaginarycraft.init.world.entity.OrdealsEntityTypes;
import ctn.imaginarycraft.util.ModUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GrantUsLovePatchRenderer extends ModPatchedLivingEntityRenderer<GrantUsLove, GrantUsLovePatch, EmptyEntityModel<GrantUsLove>, EmptyLivingEntityRenderer<GrantUsLove>, GrantUsLoveMesh> {
  private static final ResourceLocation GLOWMASK_TEXTURE = ModGeoEntityModel.getTexturePath("grant_us_love_glowmask");
  private final float[] glowmaskValue = new float[1];

  public GrantUsLovePatchRenderer(EntityRendererProvider.Context context) {
    super(context, OrdealsEntityTypes.GRANT_US_LOVE.get(), ModMeshes.GRANT_US_LOVE);
    addCustomLayer((mesh) -> new GlowmaskModelRenderPatchedLayer<>(mesh, GLOWMASK_TEXTURE, this.glowmaskValue));
  }

  @Override
  public void render(GrantUsLove entity, GrantUsLovePatch entitypatch, EmptyLivingEntityRenderer<GrantUsLove> renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
    super.render(entity, entitypatch, renderer, buffer, poseStack, packedLight, partialTicks);
    this.glowmaskValue[0] = ModUtils.calculateSineCycle(0.9f, 2f, 4.0f);
  }
}
