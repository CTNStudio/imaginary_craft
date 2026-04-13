package ctn.imaginarycraft.api.world.entity.jointpart;

import net.minecraft.world.entity.Mob;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public abstract class MultiJointPartMobPatch<T extends LivingEntityPatch<?> & IJointPartEntityPatch<?>, O extends Mob & IMultiJointPartEntity<?>> extends MobPatch<O> implements IMultiJointPartEntityPatch<T> {
	public MultiJointPartMobPatch(O entity, Faction faction) {
		super(entity, faction);
	}
}
