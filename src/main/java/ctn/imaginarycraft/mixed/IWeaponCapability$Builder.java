package ctn.imaginarycraft.mixed;

import com.mojang.datafixers.util.Pair;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IWeaponCapability$Builder {
	static IWeaponCapability$Builder of(WeaponCapability.Builder obj) {
		return (IWeaponCapability$Builder) obj;
  }

	IWeaponCapability$Builder imaginarycraft$hitParticle(HitParticleType defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, HitParticleType>> predicates);

	Function<LivingEntityPatch<?>, HitParticleType> imaginaryCraft$getHitParticleProvider();

	IWeaponCapability$Builder imaginarycraft$swingSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates);

	Function<LivingEntityPatch<?>, SoundEvent> imaginaryCraft$getSwingSoundProvider();

	IWeaponCapability$Builder imaginarycraft$hitSound(SoundEvent defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, SoundEvent>> predicates);

	Function<LivingEntityPatch<?>, SoundEvent> imaginaryCraft$getHitSoundProvider();

	IWeaponCapability$Builder imaginarycraft$newStryleCombo(Style style, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>> defaultPredicates, List<Pair<Predicate<LivingEntityPatch<?>>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> predicates);

	Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> imaginaryCraft$getAutoAttackMotionProviderMap();

	IWeaponCapability$Builder imaginarycraft$innateSkill(Style style, Function<ItemStack, Skill> defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Function<ItemStack, Skill>>> predicates);

	Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> imaginaryCraft$getInnateSkillProviderByStyle();

	IWeaponCapability$Builder imaginarycraft$livingMotionModifier(Style style, LivingMotion livingmotion, AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, List<Pair<Predicate<LivingEntityPatch<?>>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> pairs);

	Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginaryCraft$getLivingMotionProviderModifiers();

	IWeaponCapability$Builder imaginarycraft$collider(Collider defaultValue, List<Pair<Predicate<LivingEntityPatch<?>>, Collider>> predicates);

	Function<LivingEntityPatch<?>, Collider> imaginaryCraft$getColliderProvider();

	void imaginarycraft$setHitParticleProvider(Function<LivingEntityPatch<?>, HitParticleType> imaginarycraft$hitParticleProvider);

	void imaginarycraft$setSwingSoundProvider(Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$swingSoundProvider);

	void imaginarycraft$setHitSoundProvider(Function<LivingEntityPatch<?>, SoundEvent> imaginarycraft$hitSoundProvider);

	void imaginarycraft$setAutoAttackMotionProviderMap(Map<Style, Function<LivingEntityPatch<?>, List<Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends AttackAnimation>>>>> imaginarycraft$autoAttackMotionProviderMap);

	void imaginarycraft$setInnateSkillProviderByStyle(Map<Style, Function<LivingEntityPatch<?>, Function<ItemStack, Skill>>> imaginarycraft$innateSkillProviderByStyle);

	void imaginarycraft$setLivingMotionProviderModifiers(Map<Style, Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginarycraft$livingMotionProviderModifiers);

	void imaginarycraft$setColliderProvider(Function<LivingEntityPatch<?>, Collider> imaginarycraft$colliderProvider);
}
