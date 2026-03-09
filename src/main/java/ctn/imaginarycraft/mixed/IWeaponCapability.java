package ctn.imaginarycraft.mixed;

import com.mojang.datafixers.util.*;
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
  static IWeaponCapability of(WeaponCapability.Builder builder) {
    return (IWeaponCapability) builder;
  }

  HitParticleType getImaginarycraft$hitParticle(LivingEntityPatch<?> entitypatch);

  SoundEvent getImaginarycraft$smashingSound(LivingEntityPatch<?> entitypatch);

  SoundEvent getImaginarycraft$hitSound(LivingEntityPatch<?> entitypatch);

  List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> getImaginarycraft$autoAttackMotion1(PlayerPatch<?> playerpatch);

  List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getImaginarycraft$mountAttackMotion(LivingEntityPatch<?> entitypatch);

  List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> getImaginarycraft$mountAttackMotion1(LivingEntityPatch<?> entitypatch);

  Collider getImaginarycraft$weaponCollider(LivingEntityPatch<?> livingEntityPatch);

  interface IBuilder {
    static IBuilder of(WeaponCapability.Builder builder) {
      return (IBuilder) builder;
    }

    IBuilder imaginarycraft$hitParticle(HitParticleType defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, HitParticleType>> predicates);

    Function<LivingEntityPatch<?>, HitParticleType> getImaginarycraft$hitParticleProvider();

    IBuilder imaginarycraft$swingSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates);

    Function<LivingEntityPatch<?>, SoundEvent> getImaginarycraft$swingSoundProvider();

    IBuilder imaginarycraft$hitSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates);

    Function<LivingEntityPatch<?>, SoundEvent> getImaginarycraft$hitSoundProvider();

    IBuilder imaginarycraft$newStryleCombo(Style style, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> defaultPredicates, List<Pair<Predicate<LivingEntityPatch<?>>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> predicates);

    Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> getImaginarycraft$autoAttackMotionProviderMap();

    IBuilder imaginarycraft$innateSkill(Style style, Function<ItemStack, Skill> defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Function<ItemStack, Skill>>> predicates);

    Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> getImaginarycraft$innateSkillProviderByStyle();

    IBuilder imaginarycraft$livingMotionModifier(Style style, LivingMotion livingmotion, AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, List<Pair<Predicate<LivingEntityPatch<?>>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> pairs);

    Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> getImaginarycraft$livingMotionProviderModifiers();

    IBuilder imaginarycraft$collider(Collider defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Collider>> predicates);

    Function<LivingEntityPatch<?>, Collider> getImaginarycraft$colliderProvider();
  }
}
