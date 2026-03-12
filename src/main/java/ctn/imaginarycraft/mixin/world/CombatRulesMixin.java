package ctn.imaginarycraft.mixin.world;

import ctn.imaginarycraft.init.tag.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(CombatRules.class)
public abstract class CombatRulesMixin {
  @Inject(method = "getDamageAfterAbsorb", at = @At("HEAD"), cancellable = true)
  private static void imaginarycraft$getDamageAfterAbsorb(LivingEntity entity, float damage, DamageSource damageSource, float armorValue, float armorToughness, CallbackInfoReturnable<Float> cir) {
    if (damageSource.is(ModDamageTypeTags.EROSION) ||
      damageSource.is(ModDamageTypeTags.SPIRIT) ||
      damageSource.is(ModDamageTypeTags.THE_SOUL)) {
      cir.setReturnValue(damage);
    }
  }
}
