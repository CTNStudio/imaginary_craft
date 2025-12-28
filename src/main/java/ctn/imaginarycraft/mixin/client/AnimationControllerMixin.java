package ctn.imaginarycraft.mixin.client;

import com.zigythebird.playeranimcore.animation.AnimationController;
import ctn.imaginarycraft.api.client.IAnimationController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AnimationController.class)
public abstract class AnimationControllerMixin implements IAnimationController {
  @Shadow
  protected abstract void linkModifiers();

  @Unique
  @Override
  public void imaginarycraft$linkModifiers() {
    linkModifiers();
  }
}
