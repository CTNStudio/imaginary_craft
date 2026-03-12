package ctn.imaginarycraft.mixin.world;

import ctn.imaginarycraft.init.tag.ModDamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
