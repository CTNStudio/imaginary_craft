package ctn.imaginarycraft.mixin.epicfight;

import ctn.imaginarycraft.mixed.IMoveSet;
import ctn.imaginarycraft.mixed.IMoveSet$MoveSetBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.ex_cap.modules.core.data.MoveSet;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Mixin(MoveSet.class)
public abstract class MoveSetMixin implements IMoveSet {
	/**
	 * 对应 mountAttackAnimations
	 */
	@Unique
	private Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginarycraft$mountAttackAnimationsProvider;
	/**
	 * 对应 comboAttackAnimations
	 */
	@Unique
	private Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginarycraft$comboAttackAnimationsProvider;
	/**
	 * 对应 livingMotionModifiers
	 */
	@Unique
	private Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> imaginarycraft$livingMotionModifiersProvider;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void imaginarycraft$init(MoveSet.MoveSetBuilder builder, CallbackInfo ci) {
		IMoveSet$MoveSetBuilder iBuilder = IMoveSet$MoveSetBuilder.of(builder);
		imaginarycraft$mountAttackAnimationsProvider = iBuilder.imaginaryCraft$getMountAttackAnimationsProvider();
		imaginarycraft$comboAttackAnimationsProvider = iBuilder.imaginaryCraft$getComboAttackAnimationsProvider();
		imaginarycraft$livingMotionModifiersProvider = iBuilder.imaginaryCraft$getLivingMotionModifiersProvider();
	}

	@Override
	public Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getMountAttackAnimationsProvider() {
		return imaginarycraft$mountAttackAnimationsProvider;
	}

	@Override
	public Function<LivingEntityPatch<?>, List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> imaginaryCraft$getImaginarycraft$comboAttackAnimationsProvider() {
		return imaginarycraft$comboAttackAnimationsProvider;
	}

	@Override
	public Map<LivingMotion, Function<LivingEntityPatch<?>, AnimationManager.AnimationAccessor<? extends StaticAnimation>>> imaginaryCraft$getLivingMotionModifiersProvider() {
		return imaginarycraft$livingMotionModifiersProvider;
	}
}
