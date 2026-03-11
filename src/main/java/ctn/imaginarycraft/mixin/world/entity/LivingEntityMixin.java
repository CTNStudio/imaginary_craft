package ctn.imaginarycraft.mixin.world.entity;

import ctn.imaginarycraft.mixed.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.extensions.*;
import org.spongepowered.asm.mixin.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension, ILivingEntity {
  @Shadow
  protected int attackStrengthTicker;

  public LivingEntityMixin(EntityType<?> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public int getImaginarycraft$AttackStrengthTicker() {
    return attackStrengthTicker;
  }

  @Override
  public void setImaginarycraft$AttackStrengthTicker(int attackStrengthTicker) {
    this.attackStrengthTicker = attackStrengthTicker;
  }
}
