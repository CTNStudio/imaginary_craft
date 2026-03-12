package ctn.imaginarycraft.mixin.world.entity;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

import javax.annotation.*;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessorMixin {
  @Accessor("attackStrengthTicker")
  int getAttackStrengthTicker();

  @Accessor("attackStrengthTicker")
  void setAttackStrengthTicker(int attackStrengthTicker);

  @Invoker("onEffectUpdated")
  void onEffectUpdated(MobEffectInstance effectInstance, boolean forced, @Nullable Entity entity);
}
