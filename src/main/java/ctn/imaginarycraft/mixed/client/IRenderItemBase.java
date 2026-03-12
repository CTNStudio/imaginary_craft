package ctn.imaginarycraft.mixed.client;

import ctn.imaginarycraft.api.NoMixinException;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public interface IRenderItemBase {
  static IRenderItemBase of(RenderItemBase renderItemBase) {
    return (IRenderItemBase) renderItemBase;
  }

  default TrailInfo imaginarycraft$getTrailInfoProvider(LivingEntityPatch<?> livingEntityPatch) {
    throw new NoMixinException();
  }
}
