package ctn.imaginarycraft.mixed;

import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.*;

public interface ILivingEntity {
  static ILivingEntity of(LivingEntity livingEntity) {
    return (ILivingEntity) livingEntity;
  }

  int getImaginarycraft$AttackStrengthTicker();

  @Unique
  void setImaginarycraft$AttackStrengthTicker(int attackStrengthTicker);
}
