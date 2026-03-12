package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.NoMixinException;
import net.minecraft.sounds.SoundEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.List;
import java.util.function.Function;

public interface IWeaponCapability {
  static IWeaponCapability of(WeaponCapability builder) {
    return (IWeaponCapability) builder;
  }

  default HitParticleType imaginaryCraft$getHitParticle(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default SoundEvent imaginaryCraft$getSmashingSound(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default SoundEvent imaginaryCraft$getHitSound(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getAutoAttackMotionFunction(PlayerPatch<?> playerpatch) {
    throw new NoMixinException();
  }

  default List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> imaginaryCraft$getMountAttackMotion(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getMountAttackMotionFunction(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default Collider imaginaryCraft$getWeaponCollider(LivingEntityPatch<?> livingEntityPatch) {
    throw new NoMixinException();
  }
}
