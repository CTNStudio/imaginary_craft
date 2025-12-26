package ctn.imaginarycraft.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.bones.PlayerAnimBone;
import ctn.imaginarycraft.api.client.IAnimationController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(AnimationController.class)
public abstract class AnimationControllerMixin implements IAnimationController {
  @Shadow
  protected abstract void linkModifiers();

  @Unique
  @Override
  public void imaginarycraft$linkModifiers() {
    linkModifiers();
  }

  @Definition(id = "pivotBones", field = "Lcom/zigythebird/playeranimcore/animation/AnimationController;pivotBones:Ljava/util/Map;")
  @Definition(id = "get", method = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;")
  @Definition(id = "bone", local = @Local(type = PlayerAnimBone.class, name = "bone"))
  @Definition(id = "parentsMap", local = @Local(type = Map.class, name = "parentsMap"))
  @Definition(id = "getName", method = "Lcom/zigythebird/playeranimcore/bones/PlayerAnimBone;getName()Ljava/lang/String;")
  @Expression("this.pivotBones.get(parentsMap.get(bone.getName()))")
  @ModifyExpressionValue(method = "applyCustomPivotPoints", at = @At("MIXINEXTRAS:EXPRESSION"))
  private Object imaginarycraft$applyCustomPivotPoints(Object original, @Local(name = "bone") PlayerAnimBone playerAnimBone) {
    if (original == null) {
      throw new NullPointerException("The bone was not found name: " + playerAnimBone.getName());
    }
    return original;
  }
}
