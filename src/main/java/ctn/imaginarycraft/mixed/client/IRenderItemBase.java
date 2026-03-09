package ctn.imaginarycraft.mixed.client;

import yesman.epicfight.api.client.animation.property.*;
import yesman.epicfight.client.renderer.patched.item.*;
import yesman.epicfight.world.capabilities.entitypatch.*;

public interface IRenderItemBase {
  static IRenderItemBase of(RenderItemBase renderItemBase) {
    return (IRenderItemBase) renderItemBase;
  }

  TrailInfo getImaginarycraft$trailInfoProvider(LivingEntityPatch<?> livingEntityPatch);
}
