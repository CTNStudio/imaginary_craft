package ctn.imaginarycraft.common.world.entity.ordeals.crimson;

import ctn.imaginarycraft.api.world.entity.ai.CampHurtByTargetGoal;
import ctn.imaginarycraft.common.world.entity.ordeals.IOrdealsEntity;
import ctn.imaginarycraft.init.tag.ModEntityTags;
import net.minecraft.world.entity.Entity;

public interface IOrdealsCrimsonEntity extends IOrdealsEntity {
  @Override
  default boolean isCamp(Entity entity) {
    return IOrdealsEntity.super.isCamp(entity) || getMob().getType().is(ModEntityTags.ORDEALS_CRIMSON);
  }

  @Override
  default void registerGoals() {
    IOrdealsEntity.super.registerGoals();
    getTargetSelector().addGoal(1, new CampHurtByTargetGoal(getMob(), IOrdealsCrimsonEntity.class));
  }
}
