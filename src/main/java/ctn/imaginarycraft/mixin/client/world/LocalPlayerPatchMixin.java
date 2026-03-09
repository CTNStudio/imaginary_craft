package ctn.imaginarycraft.mixin.client.world;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import yesman.epicfight.client.world.capabilites.entitypatch.player.*;

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
