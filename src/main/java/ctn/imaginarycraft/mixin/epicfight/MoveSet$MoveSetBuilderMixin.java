package ctn.imaginarycraft.mixin.epicfight;

import com.mojang.datafixers.util.Pair;
import ctn.imaginarycraft.api.data.ConditionalProviderFactory;
import ctn.imaginarycraft.mixed.IMoveSet$MoveSetBuilder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.ex_cap.modules.core.data.MoveSet;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(MoveSet.MoveSetBuilder.class)
public abstract class MoveSet$MoveSetBuilderMixin implements IMoveSet$MoveSetBuilder {
	@Shadow
	@Final
	protected List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> mountAttackAnimations;
	@Shadow
	@Final
	protected List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> comboAttackAnimations;

	@Shadow
	public abstract MoveSet.MoveSetBuilder addMountAttacks(AnimationManager.AnimationAccessor<? extends AttackAnimation>... attackAnimations);

	@Shadow
	public abstract MoveSet.MoveSetBuilder addComboAttacks(AnimationManager.AnimationAccessor<? extends AttackAnimation>... attackAnimations);

	@Shadow
	@Final
	protected Map<LivingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation>> livingMotionModifiers;

	@Shadow
	public abstract MoveSet.MoveSetBuilder addLivingMotionModifier(LivingMotion livingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation> animation);

	@Unique
	@Nullable
	private List<Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>> imaginarycraft$mountAttackAnimationsProvider;
	@Unique
	@Nullable
	private List<Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>> imaginarycraft$comboAttackAnimationsProvider;
	@Unique
	@Nullable
	private Map<LivingMotion, List<Pair<Predicate<LivingEntityPatch<?>>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> imaginarycraft$livingMotionModifiersProvider;

	@Override
	@Nullable
	public Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getMountAttackAnimationsProvider() {
		return ConditionalProviderFactory.getProvider(mountAttackAnimations, imaginarycraft$mountAttackAnimationsProvider);
	}

	@Override
	@Nullable
	public Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getComboAttackAnimationsProvider() {
		return ConditionalProviderFactory.getProvider(comboAttackAnimations, imaginarycraft$comboAttackAnimationsProvider);
	}

	@Override
	@Nullable
	public Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> imaginaryCraft$getLivingMotionModifiersProvider() {
		Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> map = new HashMap<>();
		for (var v : livingMotionModifiers.entrySet()) {
			LivingMotion key = v.getKey();
			map.put(key, ConditionalProviderFactory.getProvider(v.getValue(), imaginarycraft$livingMotionModifiersProvider == null ? null : imaginarycraft$livingMotionModifiersProvider.get(key)));
		}
		return map;
	}

	@Override
	public IMoveSet$MoveSetBuilder imaginarycraft$addComboAttacks(Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>... attackAnimations) {
		if (imaginarycraft$comboAttackAnimationsProvider == null) {
			imaginarycraft$comboAttackAnimationsProvider = new ArrayList<>();
		}
		imaginarycraft$comboAttackAnimationsProvider.addAll(Arrays.asList(attackAnimations));
		return this;
	}

	@Override
	public IMoveSet$MoveSetBuilder imaginarycraft$addMountAttacks(Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>... predicates) {
		if (imaginarycraft$mountAttackAnimationsProvider == null) {
			imaginarycraft$mountAttackAnimationsProvider = new ArrayList<>();
		}
		imaginarycraft$mountAttackAnimationsProvider.addAll(Arrays.asList(predicates));
		return this;
	}

	@Override
	public IMoveSet$MoveSetBuilder imaginarycraft$addMountAttacks(AnimationManager.AnimationAccessor<? extends AttackAnimation>[] defaultValue, Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>... predicates) {
		addMountAttacks(defaultValue);
		imaginarycraft$addMountAttacks(predicates);
		return this;
	}

	@Override
	public IMoveSet$MoveSetBuilder imaginarycraft$addComboAttacks(AnimationManager.AnimationAccessor<? extends AttackAnimation>[] defaultValue, Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>... attackAnimations) {
		addComboAttacks(defaultValue);
		imaginarycraft$addComboAttacks(attackAnimations);
		return this;
	}

	@Override
	public IMoveSet$MoveSetBuilder imaginarycraft$addLivingMotionModifier(LivingMotion livingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation> defaultValue, Pair<Predicate<LivingEntityPatch<?>>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>... predicates) {
		if (imaginarycraft$livingMotionModifiersProvider == null) {
			imaginarycraft$livingMotionModifiersProvider = new HashMap<>();
		}
		addLivingMotionModifier(livingMotion, defaultValue);
		imaginarycraft$livingMotionModifiersProvider.put(livingMotion, Arrays.asList(predicates));
		return this;
	}
}
