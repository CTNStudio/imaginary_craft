package ctn.imaginarycraft.api.world.entity.jointpart;

import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class JointPartLivingEntityPatch<T extends JointPartLivingEntity<?>> extends LivingEntityPatch<T> implements IJointPartLivingEntityPatch<T> {
	public JointPartLivingEntityPatch(T entity) {
		super(entity);
	}
}
