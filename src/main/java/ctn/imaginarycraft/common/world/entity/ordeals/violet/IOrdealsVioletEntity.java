package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.ai.CampHurtByTargetGoal;
import ctn.imaginarycraft.common.world.entity.ordeals.IOrdealsEntity;
import ctn.imaginarycraft.init.tag.ModEntityTags;
import net.minecraft.world.entity.Entity;

public interface IOrdealsVioletEntity extends IOrdealsEntity {
	@Override
	default boolean isCamp(Entity entity) {
		return IOrdealsEntity.super.isCamp(entity) || entity.getType().is(ModEntityTags.ORDEALS_VIOLET);
	}

	@Override
	default void registerGoals() {
		IOrdealsEntity.super.registerGoals();
		getTargetSelector().addGoal(2, new CampHurtByTargetGoal(getMob(), IOrdealsVioletEntity.class));
	}
}
