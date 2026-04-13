package ctn.imaginarycraft.api.client.renderer.entity;

import ctn.imaginarycraft.api.world.entity.jointpart.IMultiJointPartEntity;
import ctn.imaginarycraft.api.world.entity.jointpart.IMultiJointPartEntityPatch;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

public abstract class MultiJointMobPartRenderer<
	E extends Mob & IMultiJointPartEntity<?>,
	T extends LivingEntityPatch<E> & IMultiJointPartEntityPatch<?>,
	M extends EntityModel<E>,
	A extends MobRenderer<E, M>,
	AM extends SkinnedMesh>
	extends ModPatchedLivingEntityRenderer<E, T, M, A, AM> {

	public MultiJointMobPartRenderer(EntityRendererProvider.Context context, EntityType<?> entityType, AssetAccessor<AM> mesh) {
		super(context, entityType, mesh);
	}

	@Override
	protected void prepareModel(AM mesh, E entity, T entitypatch, A renderer) {
		super.prepareModel(mesh, entity, entitypatch, renderer);
		updatePartVisibility(mesh, entity);
	}

	protected void updatePartVisibility(AM mesh, E entity) {
		// 收集并隐藏所有子部件的关节
		entity.getJointPartManager().getAllJointNames().stream()
			.map(mesh::getPart)
			.filter(Objects::nonNull)
			.forEach(modelPart -> modelPart.setHidden(true));
	}
}
