package ctn.imaginarycraft.api.client.renderer.entity;

import ctn.imaginarycraft.api.world.entity.jointpart.IJointPartEntity;
import ctn.imaginarycraft.api.world.entity.jointpart.IJointPartEntityPatch;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

public abstract class JointLivingEntityPartRenderer<
	E extends LivingEntity & IJointPartEntity<?>,
	T extends LivingEntityPatch<E> & IJointPartEntityPatch<?>,
	M extends EntityModel<E>,
	A extends LivingEntityRenderer<E, M>,
	AM extends SkinnedMesh>
	extends ModPatchedLivingEntityRenderer<E, T, M, A, AM> {

	public JointLivingEntityPartRenderer(EntityRendererProvider.Context context, EntityType<?> entityType, AssetAccessor<AM> mesh) {
		super(context, entityType, mesh);
	}

	@Override
	protected void prepareModel(AM mesh, E entity, T entitypatch, A renderer) {
		mesh.getAllParts().forEach((part) -> part.setHidden(true));
		updatePartVisibility(mesh, entity);
	}

	protected void updatePartVisibility(AM mesh, E entity) {
		entity.getJointsNames().stream()
			.map(mesh::getPart)
			.filter(Objects::nonNull)
			.forEach(modelPart -> modelPart.setHidden(false));
	}
}
