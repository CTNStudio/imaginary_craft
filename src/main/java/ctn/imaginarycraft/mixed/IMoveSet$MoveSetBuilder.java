package ctn.imaginarycraft.mixed;

import com.mojang.datafixers.util.Pair;
import ctn.imaginarycraft.api.NoMixinException;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.ex_cap.modules.core.data.MoveSet;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IMoveSet$MoveSetBuilder {
	static IMoveSet$MoveSetBuilder of(MoveSet.MoveSetBuilder obj) {
		return (IMoveSet$MoveSetBuilder) obj;
	}

	@Nullable
	default Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getMountAttackAnimationsProvider() {
		throw new NoMixinException();
	}

	@Nullable
	default Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getComboAttackAnimationsProvider() {
		throw new NoMixinException();
	}

	@Nullable
	default Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> imaginaryCraft$getLivingMotionModifiersProvider() {
		throw new NoMixinException();
	}

	IMoveSet$MoveSetBuilder imaginarycraft$addComboAttacks(Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>... attackAnimations);

	IMoveSet$MoveSetBuilder imaginarycraft$addMountAttacks(Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>... predicates);

	IMoveSet$MoveSetBuilder imaginarycraft$addMountAttacks(AnimationManager.AnimationAccessor<? extends AttackAnimation>[] defaultValue, Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>... predicates);

	IMoveSet$MoveSetBuilder imaginarycraft$addComboAttacks(AnimationManager.AnimationAccessor<? extends AttackAnimation>[] defaultValue, Pair<Predicate<LivingEntityPatch<?>>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>>... attackAnimations);

	IMoveSet$MoveSetBuilder imaginarycraft$addLivingMotionModifier(LivingMotion livingMotion, AnimationManager.AnimationAccessor<? extends StaticAnimation> defaultValue, Pair<Predicate<LivingEntityPatch<?>>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>... predicates);
}
