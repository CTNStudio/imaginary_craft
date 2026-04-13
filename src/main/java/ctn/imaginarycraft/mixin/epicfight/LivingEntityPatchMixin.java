package ctn.imaginarycraft.mixin.epicfight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ctn.imaginarycraft.mixed.IWeaponCapability;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.function.BiFunction;

@Mixin(LivingEntityPatch.class)
public abstract class LivingEntityPatchMixin {
  @WrapOperation(method = "getWeaponHitParticle", at = @At(value = "INVOKE",
    target = "Lyesman/epicfight/world/capabilities/item/CapabilityItem;getHitParticle()Lyesman/epicfight/particle/HitParticleType;"))
  public HitParticleType imaginarycraft$getWeaponHitParticle(CapabilityItem instance, Operation<HitParticleType> original) {
    return imaginarycraft$get(instance, original, IWeaponCapability::imaginaryCraft$getHitParticle);
  }

  @Unique
  private <T> T imaginarycraft$get(CapabilityItem instance, Operation<T> original, BiFunction<IWeaponCapability, LivingEntityPatch<?>, T> function) {
    if (!(instance instanceof IWeaponCapability iWeaponCapability)) {
      return original.call(instance);
    }

    T t = function.apply(iWeaponCapability, imaginarycraft$getThis());
    if (t != null) {
      return t;
    }
    return original.call(instance);
  }

  @Unique
  private LivingEntityPatch<?> imaginarycraft$getThis() {
    return (LivingEntityPatch<?>) (Object) this;
  }

  @WrapOperation(method = "getSwingSound", at = @At(value = "INVOKE",
    target = "Lyesman/epicfight/world/capabilities/item/CapabilityItem;getSmashingSound()Lnet/minecraft/sounds/SoundEvent;"))
  public SoundEvent imaginarycraft$getSwingSound(CapabilityItem instance, Operation<SoundEvent> original) {
    return imaginarycraft$get(instance, original, IWeaponCapability::imaginaryCraft$getSmashingSound);
  }

  @WrapOperation(method = "getWeaponHitSound", at = @At(value = "INVOKE",
    target = "Lyesman/epicfight/world/capabilities/item/CapabilityItem;getHitSound()Lnet/minecraft/sounds/SoundEvent;"))
  public SoundEvent imaginarycraft$getHitSound(CapabilityItem instance, Operation<SoundEvent> original) {
    return imaginarycraft$get(instance, original, IWeaponCapability::imaginaryCraft$getHitSound);
  }

  @WrapOperation(method = "getColliderMatching", at = @At(value = "INVOKE",
    target = "Lyesman/epicfight/world/capabilities/item/CapabilityItem;getWeaponCollider()Lyesman/epicfight/api/collider/Collider;"))
  private Collider imaginarycraft$getColliderMatching(
    CapabilityItem instance,
    Operation<Collider> original
  ) {
    return imaginarycraft$get(instance, original, IWeaponCapability::imaginaryCraft$getWeaponCollider);
  }
}
