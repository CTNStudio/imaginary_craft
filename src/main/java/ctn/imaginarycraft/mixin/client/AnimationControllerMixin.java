package ctn.imaginarycraft.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.RawAnimation;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.mixed.client.IAnimationController;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnimationController.class)
public abstract class AnimationControllerMixin implements IAnimationController {
  @Shadow
  protected abstract void linkModifiers();

  @Shadow
  @Final
  protected AnimationController.AnimationStateHandler stateHandler;

  @Shadow
  protected abstract void setAnimation(RawAnimation rawAnimation, float startAnimFrom);

  @Unique
  @Override
  public void imaginarycraft$linkModifiers() {
    linkModifiers();
  }

  @Definition(id = "CONTINUE", field = "Lcom/zigythebird/playeranimcore/enums/PlayState;CONTINUE:Lcom/zigythebird/playeranimcore/enums/PlayState;")
  @Expression("CONTINUE")
  @ModifyReturnValue(method = "handleAnimation", at = @At(value = "RETURN", ordinal = 0))
  private PlayState handleAnimation(PlayState original, @Local(argsOnly = true) AnimationData state) {
    return this.stateHandler.handle(imaginarycraft$get(), state, (animation, startTick) -> {
      this.setAnimation(animation, (float) startTick);
      return original;
    });
  }

  @Unique
  private @NotNull AnimationController imaginarycraft$get() {
    return (AnimationController) (Object) this;
  }
}
