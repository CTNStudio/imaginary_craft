package ctn.imaginarycraft.api.client;

import com.zigythebird.playeranimcore.animation.AnimationController;
import org.spongepowered.asm.mixin.Unique;

public interface IAnimationController {
  static IAnimationController of(AnimationController animationController) {
    return (IAnimationController) animationController;
  }

  @Unique
  void imaginarycraft$linkModifiers();
}
