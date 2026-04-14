package ctn.imaginarycraft.api.client.renderer.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Function;

public abstract class ModPatchedLivingEntityRenderer<
	E extends LivingEntity,
	T extends LivingEntityPatch<E>,
	M extends EntityModel<E>,
	A extends LivingEntityRenderer<E, M>,
	AM extends SkinnedMesh>
	extends PatchedLivingEntityRenderer<E, T, M, A, AM> {
  private final AssetAccessor<AM> mesh;

  public ModPatchedLivingEntityRenderer(EntityRendererProvider.Context context, EntityType<?> entityType, AssetAccessor<AM> mesh) {
    super(context, entityType);
    this.mesh = mesh;
  }

	public void addCustomLayer(Function<AssetAccessor<AM>, PatchedLayer<E, T, M, ? extends RenderLayer<E, M>>> patchedLayer) {
    this.customLayers.add(patchedLayer.apply(this.getDefaultMesh()));
  }

  @Override
  public AssetAccessor<AM> getDefaultMesh() {
    return mesh;
  }
}
