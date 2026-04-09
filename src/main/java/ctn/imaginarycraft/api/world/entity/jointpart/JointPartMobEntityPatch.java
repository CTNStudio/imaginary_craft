package ctn.imaginarycraft.api.world.entity.jointpart;

import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class JointPartMobEntityPatch<T extends JointPartMobEntity<?>> extends LivingEntityPatch<T> implements IJointPartLivingEntityPatch<T> {
	public JointPartMobEntityPatch(T entity) {
		super(entity);
	}
}
