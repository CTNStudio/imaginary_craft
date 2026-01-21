package ctn.imaginarycraft.mixin;

import ctn.imaginarycraft.mixed.ILivingEntity;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension, ILivingEntity {
  @Shadow
  protected int attackStrengthTicker;

  public LivingEntityMixin(EntityType<?> entityType, Level level) {
    super(entityType, level);
  }

  @Unique
  @Override
  public int getImaginarycraft$AttackStrengthTicker() {
    return attackStrengthTicker;
  }

  @Unique
  @Override
  public void setImaginarycraft$AttackStrengthTicker(int attackStrengthTicker) {
    this.attackStrengthTicker = attackStrengthTicker;
  }
}
