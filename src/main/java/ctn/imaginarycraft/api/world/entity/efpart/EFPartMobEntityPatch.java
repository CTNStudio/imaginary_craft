package ctn.imaginarycraft.api.world.entity.efpart;

import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class EFPartMobEntityPatch<T extends EFPartMobEntity<?>> extends LivingEntityPatch<T> implements IEFPartLivingEntityPatch<T> {
  public EFPartMobEntityPatch(T entity) {
    super(entity);
  }
}
