package ctn.imaginarycraft.common.world.entity.ordeals.green;

import ctn.imaginarycraft.api.world.entity.ai.CampHurtByTargetGoal;
import ctn.imaginarycraft.common.world.entity.ordeals.IOrdealsEntity;
import ctn.imaginarycraft.init.tag.ModEntityTags;
import net.minecraft.world.entity.Entity;

public interface IOrdealsGreenEntity extends IOrdealsEntity {
  @Override
  default boolean isCamp(Entity entity) {
    return IOrdealsEntity.super.isCamp(entity) || getMob().getType().is(ModEntityTags.ORDEALS_GREEN);
  }

  @Override
  default void registerGoals() {
    IOrdealsEntity.super.registerGoals();
    getTargetSelector().addGoal(1, new CampHurtByTargetGoal(getMob(), IOrdealsGreenEntity.class));
  }
}
