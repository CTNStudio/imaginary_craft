package ctn.imaginarycraft.mixin.epicfight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import ctn.imaginarycraft.common.world.entity.ordeals.IOrdealsEntity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(AttackAnimation.class)
public abstract class AttackAnimationMixin extends ActionAnimation {

	public AttackAnimationMixin(float transitionTime, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
		super(transitionTime, accessor, armature);
	}

	// 自定义过滤实体
	@WrapOperation(method = "hurtCollidingEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isAlive()Z"))
	private boolean hurtCollidingEntities(
		LivingEntity trueEntity,
		Operation<Boolean> original,
		@Local(name = "entitypatch") LivingEntityPatch<?> entitypatch
	) {
		boolean call = original.call(trueEntity);
		if (!(entitypatch.getOriginal() instanceof IOrdealsEntity iOrdealsEntity)) {
			return call;
		}

		return call && iOrdealsEntity.canTarget(trueEntity);
	}
}
