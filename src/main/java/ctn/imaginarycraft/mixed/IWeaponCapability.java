package ctn.imaginarycraft.mixed;

import com.mojang.datafixers.util.*;
import ctn.imaginarycraft.api.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.*;
import yesman.epicfight.particle.*;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.*;
import yesman.epicfight.world.capabilities.entitypatch.player.*;
import yesman.epicfight.world.capabilities.item.*;

import java.util.*;
import java.util.function.*;

public interface IWeaponCapability {
  static IWeaponCapability of(WeaponCapability builder) {
    return (IWeaponCapability) builder;
  }

  default HitParticleType getImaginarycraft$hitParticle(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default SoundEvent getImaginarycraft$smashingSound(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default SoundEvent getImaginarycraft$hitSound(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> getImaginarycraft$autoAttackMotion1(PlayerPatch<?> playerpatch) {
    throw new NoMixinException();
  }

  default List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getImaginarycraft$mountAttackMotion(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> getImaginarycraft$mountAttackMotion1(LivingEntityPatch<?> entitypatch) {
    throw new NoMixinException();
  }

  default Collider getImaginarycraft$weaponCollider(LivingEntityPatch<?> livingEntityPatch) {
    throw new NoMixinException();
  }

  interface IBuilder {
    static IBuilder of(WeaponCapability.Builder builder) {
      return (IBuilder) builder;
    }

    default IBuilder imaginarycraft$hitParticle(HitParticleType defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, HitParticleType>> predicates) {
      throw new NoMixinException();
    }

    default Function<LivingEntityPatch<?>, HitParticleType> getImaginarycraft$hitParticleProvider() {
      throw new NoMixinException();
    }

    default IBuilder imaginarycraft$swingSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates) {
      throw new NoMixinException();
    }

    default Function<LivingEntityPatch<?>, SoundEvent> getImaginarycraft$swingSoundProvider() {
      throw new NoMixinException();
    }

    default IBuilder imaginarycraft$hitSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates) {
      throw new NoMixinException();
    }

    default Function<LivingEntityPatch<?>, SoundEvent> getImaginarycraft$hitSoundProvider() {
      throw new NoMixinException();
    }

    default IBuilder imaginarycraft$newStryleCombo(Style style, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> defaultPredicates, List<Pair<Predicate<LivingEntityPatch<?>>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> predicates) {
      throw new NoMixinException();
    }

    default Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> getImaginarycraft$autoAttackMotionProviderMap() {
      throw new NoMixinException();
    }

    default IBuilder imaginarycraft$innateSkill(Style style, Function<ItemStack, Skill> defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Function<ItemStack, Skill>>> predicates) {
      throw new NoMixinException();
    }

    default Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> getImaginarycraft$innateSkillProviderByStyle() {
      throw new NoMixinException();
    }

    default IBuilder imaginarycraft$livingMotionModifier(Style style, LivingMotion livingmotion, AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, List<Pair<Predicate<LivingEntityPatch<?>>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> pairs) {
      throw new NoMixinException();
    }

    default Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> getImaginarycraft$livingMotionProviderModifiers() {
      throw new NoMixinException();
    }

    default IBuilder imaginarycraft$collider(Collider defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Collider>> predicates) {
      throw new NoMixinException();
    }

    default Function<LivingEntityPatch<?>, Collider> getImaginarycraft$colliderProvider() {
      throw new NoMixinException();
    }
  }
}
