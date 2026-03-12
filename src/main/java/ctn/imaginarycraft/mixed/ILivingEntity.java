package ctn.imaginarycraft.mixed;

import net.minecraft.world.entity.*;

public interface ILivingEntity {
  static ILivingEntity of(LivingEntity livingEntity) {
    return livingEntity;
  }
}
