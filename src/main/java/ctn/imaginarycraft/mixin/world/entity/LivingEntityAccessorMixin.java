package ctn.imaginarycraft.mixin.world.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessorMixin {
  @Accessor("attackStrengthTicker")
  int imaginarycraft$getAttackStrengthTicker();

  @Accessor("attackStrengthTicker")
  void imaginarycraft$setAttackStrengthTicker(int attackStrengthTicker);

  @Invoker("onEffectUpdated")
  void imaginarycraft$onEffectUpdated(MobEffectInstance effectInstance, boolean forced, @Nullable Entity entity);
}
