package ctn.imaginarycraft.api.client.playeranimcore;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import com.zigythebird.playeranimcore.enums.PlayState;
import ctn.imaginarycraft.client.animation.player.controller.GenericAnimationController;
import ctn.imaginarycraft.client.util.PlayerAnimationUtil;
import net.minecraft.resources.ResourceLocation;

/**
 * 动画集合
 */
public record AnimCollection(ResourceLocation standby, ResourceLocation move) {

  public PlayState executeAnim(GenericAnimationController.AnimationContext context) {
    return executeAnim(context.controller, context.data, context.setter);
  }

  public PlayState executeAnim(PlayerAnimationController controller, AnimationData state,
                          AnimationController.AnimationSetter animationSetter) {
    if (state.isMoving()) {
      if (PlayerAnimationUtil.isPlaying(controller, move)) {
        return movingAnim(controller);
      }
    } else {
      if (PlayerAnimationUtil.isPlaying(controller, standby)) {
        return standbyAnim(controller);
      }
    }
    return PlayState.STOP;
  }

  public PlayState movingAnim(PlayerAnimationController controller) {
    controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), move, true);
    return PlayState.CONTINUE;
  }

  public PlayState standbyAnim(PlayerAnimationController controller) {
    controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), standby, true);
    return PlayState.CONTINUE;
  }
}
