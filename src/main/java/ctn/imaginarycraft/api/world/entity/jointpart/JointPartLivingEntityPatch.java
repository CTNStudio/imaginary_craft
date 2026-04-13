package ctn.imaginarycraft.api.world.entity.jointpart;

import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class JointPartLivingEntityPatch<T extends JointPartLivingEntity<?>, TP extends LivingEntityPatch<?> & IMultiJointPartEntityPatch<?>> extends LivingEntityPatch<T> implements IJointPartEntityPatch<TP> {
	public JointPartLivingEntityPatch(T entity) {
		super(entity);
	}
}
