package ctn.imaginarycraft.api.world.entity.efpart;

import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class EFPartLivingEntityPatch<T extends EFPartLivingEntity<?>> extends LivingEntityPatch<T> implements IEFPartLivingEntityPatch<T> {
  public EFPartLivingEntityPatch(T entity) {
    super(entity);
  }
}
