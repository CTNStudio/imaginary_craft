package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.*;
import net.minecraft.world.entity.*;

public interface ILivingEntity {
  static ILivingEntity of(LivingEntity livingEntity) {
    return livingEntity;
  }

  default int getImaginarycraft$AttackStrengthTicker() {
    throw new NoMixinException();
  }

  default void setImaginarycraft$AttackStrengthTicker(int attackStrengthTicker) {
    throw new NoMixinException();
  }
}
