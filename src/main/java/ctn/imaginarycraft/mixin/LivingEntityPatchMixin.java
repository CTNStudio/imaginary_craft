package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import ctn.imaginarycraft.mixed.*;
import net.minecraft.sounds.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import yesman.epicfight.particle.*;
import yesman.epicfight.world.capabilities.entitypatch.*;
import yesman.epicfight.world.capabilities.item.*;

import java.util.function.*;

@Mixin(LivingEntityPatch.class)
public abstract class LivingEntityPatchMixin {
  @WrapOperation(method = "getWeaponHitParticle", at = @At(value = "INVOKE",
    target = "Lyesman/epicfight/world/capabilities/item/CapabilityItem;getHitParticle()Lyesman/epicfight/particle/HitParticleType;"))
  public HitParticleType imaginarycraft$getWeaponHitParticle(CapabilityItem instance, Operation<HitParticleType> original) {
    return imaginarycraft$get(instance, original, IWeaponCapability::getImaginarycraft$hitParticle);
  }

  @WrapOperation(method = "getSwingSound", at = @At(value = "INVOKE",
    target = "Lyesman/epicfight/world/capabilities/item/CapabilityItem;getSmashingSound()Lnet/minecraft/sounds/SoundEvent;"))
  public SoundEvent imaginarycraft$getSwingSound(CapabilityItem instance, Operation<SoundEvent> original) {
    return imaginarycraft$get(instance, original, IWeaponCapability::getImaginarycraft$swingSound);
  }

  @WrapOperation(method = "getWeaponHitSound", at = @At(value = "INVOKE",
    target = "Lyesman/epicfight/world/capabilities/item/CapabilityItem;getHitSound()Lnet/minecraft/sounds/SoundEvent;"))
  public SoundEvent imaginarycraft$getHitSound(CapabilityItem instance, Operation<SoundEvent> original) {
    return imaginarycraft$get(instance, original, IWeaponCapability::getImaginarycraft$hitSound);
  }

  @Unique
  private <T> T imaginarycraft$get(CapabilityItem instance, Operation<T> original, BiFunction<IWeaponCapability, LivingEntityPatch<?>, T> function) {
    if (!(instance instanceof IWeaponCapability iWeaponCapability)) {
      return original.call(instance);
    }

    T t = function.apply(iWeaponCapability, imaginarycraft$getLivingEntitypatch());
    if (t != null) {
      return t;
    }
    return original.call(instance);
  }

  @Unique
  private LivingEntityPatch<?> imaginarycraft$getLivingEntitypatch() {
    return (LivingEntityPatch<?>) (Object) this;
  }
}
