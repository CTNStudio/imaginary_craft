package ctn.imaginarycraft.mixed;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Unique;

public interface ILivingEntity {
  static ILivingEntity of(LivingEntity livingEntity) {
    return (ILivingEntity) livingEntity;
  }

  int getImaginarycraft$AttackStrengthTicker();

  @Unique
  void setImaginarycraft$AttackStrengthTicker(int attackStrengthTicker);
}
