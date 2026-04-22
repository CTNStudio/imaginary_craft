package ctn.imaginarycraft.api.world.entity;

import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;

public interface IPatch<T extends EntityPatch<?>> {
	T getPatch();
}
