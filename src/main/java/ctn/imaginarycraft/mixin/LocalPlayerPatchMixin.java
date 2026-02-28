package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;

@Mixin(LocalPlayerPatch.class)
public abstract class LocalPlayerPatchMixin {

  @WrapOperation(method = "canPlayAttackAnimation", at = @At(value = "INVOKE", target = "Lyesman/epicfight/client/events/engine/RenderEngine;hitResultEquals(Lnet/minecraft/world/phys/HitResult;Lnet/minecraft/world/phys/HitResult$Type;)Z", ordinal = 0))
  private boolean imaginarycraft$canPlayAttackAnimation(
    HitResult hitResult,
    HitResult.Type hitType,
    Operation<Boolean> original,
    @Local(name = "hitResult") HitResult localHitResult
  ) {
    return original.call(localHitResult, hitType);
  }
}
