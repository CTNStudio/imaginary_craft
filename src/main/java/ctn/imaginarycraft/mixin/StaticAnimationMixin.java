package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import ctn.imaginarycraft.mixed.client.IRenderItemBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(StaticAnimation.class)
public abstract class StaticAnimationMixin {
  @WrapOperation(method = "lambda$begin$6", at = @At(value = "INVOKE",
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

  @Unique
  private static TrailInfo imaginarycraft$getTrailInfo(RenderItemBase instance, LivingEntityPatch<?> entitypatch) {
    return IRenderItemBase.of(instance).imaginarycraft$getTrailInfoProvider(entitypatch);
  }
}
