package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import ctn.imaginarycraft.mixed.client.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import yesman.epicfight.api.client.animation.property.*;
import yesman.epicfight.client.particle.*;
import yesman.epicfight.client.renderer.patched.item.*;
import yesman.epicfight.world.capabilities.entitypatch.*;

@Mixin(AnimationTrailParticle.class)
public abstract class AnimationTrailParticleMixin {
  @Mixin(AnimationTrailParticle.Provider.class)
  public abstract static class Provider {
    @Unique
    private static TrailInfo imaginarycraft$getTrailInfo(RenderItemBase instance, LivingEntityPatch<?> entitypatch) {
      return IRenderItemBase.of(instance).imaginarycraft$getTrailInfoProvider(entitypatch);
    }

    @WrapOperation(method = "createParticle(Lnet/minecraft/core/particles/SimpleParticleType;Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At(value = "INVOKE",
      target = "Lyesman/epicfight/client/renderer/patched/item/RenderItemBase;trailInfo()Lyesman/epicfight/api/client/animation/property/TrailInfo;"))
    private TrailInfo imaginarycraft$begin$trailInfo(
      RenderItemBase instance,
      Operation<TrailInfo> original,
      @Local(name = "entitypatch") LivingEntityPatch<?> entitypatch
    ) {
      TrailInfo imaginarycraft$trailInfoProvider = imaginarycraft$getTrailInfo(instance, entitypatch);
      if (imaginarycraft$trailInfoProvider != null) return imaginarycraft$trailInfoProvider;
      return original.call(instance);
    }
  }
}
