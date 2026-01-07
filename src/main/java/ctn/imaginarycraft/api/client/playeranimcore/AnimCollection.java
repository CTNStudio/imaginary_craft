package ctn.imaginarycraft.api.client.playeranimcore;

import com.zigythebird.playeranim.animation.PlayerAnimResources;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import com.zigythebird.playeranimcore.enums.State;
import net.minecraft.resources.ResourceLocation;

/**
 * 动画集合
 */
public record AnimCollection(ResourceLocation standby, ResourceLocation move) {

  public void executeAnim(PlayerAnimationController controller, AnimationData state,
                          AnimationController.AnimationSetter animationSetter) {
    if (state.isMoving()) {
      if (is(controller, move)) {
        movingAnim(controller);
      }
    } else {
      if (is(controller, standby)) {
        standbyAnim(controller);
      }
    }
  }

  private boolean is(PlayerAnimationController controller, ResourceLocation animationId) {
    if (controller.getAnimationState() == State.STOPPED || controller.getAnimationState() == State.PAUSED) {
      return true;
    }
    Animation currentAnimationInstance = controller.getCurrentAnimationInstance();
    if (currentAnimationInstance == null) {
      return true;
    }
    Animation animation = PlayerAnimResources.getAnimation(animationId);
    if (animation == null) {
      return true;
    }
    return !currentAnimationInstance.get().equals(animation.get());
  }

  public void movingAnim(PlayerAnimationController controller) {
    controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), move, true);
  }

  public void standbyAnim(PlayerAnimationController controller) {
    controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), standby, true);
  }
}
