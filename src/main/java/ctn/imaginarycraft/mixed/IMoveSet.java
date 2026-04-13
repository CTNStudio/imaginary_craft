package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.NoMixinException;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.ex_cap.modules.core.data.MoveSet;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface IMoveSet {
	static IMoveSet of(MoveSet obj) {
		return (IMoveSet) obj;
	}

	default Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getMountAttackAnimationsProvider() {
		throw new NoMixinException();
	}

	default Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getImaginarycraft$comboAttackAnimationsProvider() {
		throw new NoMixinException();
	}

	default Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> imaginaryCraft$getLivingMotionModifiersProvider() {
		throw new NoMixinException();
	}
}
