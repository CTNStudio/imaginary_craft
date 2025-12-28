package ctn.imaginarycraft.api.client.playeranimcore;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * 动画集合
 */
@SuppressWarnings("unchecked")
public record AnimCollection(ResourceLocation standby, ResourceLocation move,
                             Supplier<? extends Item>... items) {
  public boolean executeAnim(ItemStack toItemStack, PlayerAnimationController controller,
                             AnimationData state, AnimationController.AnimationSetter animationSetter) {
    for (Supplier<? extends Item> item : items) {
      if (!toItemStack.is(item.get())) {
        continue;
      }
      if (state.isMoving()) {
        movingAnim(controller);
      } else {
        standbyAnim(controller);
      }
      return true;
    }
    return false;
  }

  public void movingAnim(PlayerAnimationController controller) {
    if (PlayerAnimUtil.isExecutableAnimation(controller, move)) {
      controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), move, true);
    }
  }

  public void standbyAnim(PlayerAnimationController controller) {
    if (PlayerAnimUtil.isExecutableAnimation(controller, standby)) {
      controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_OUT_SINE), standby, true);
    }
  }
}
