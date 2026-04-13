package ctn.imaginarycraft.mixed;

import net.minecraft.world.entity.LivingEntity;

public interface ILivingEntity {
	static ILivingEntity of(LivingEntity obj) {
		return obj;
  }
}
