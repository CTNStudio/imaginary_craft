package ctn.imaginarycraft.api.client;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.minecraft.client.player.AbstractClientPlayer;
import team.unnamed.mocha.MochaEngine;

import java.util.function.Function;

public class ModPlayerAnimationController extends PlayerAnimationController {
  private final TickAnimationStateHandler tickAnimationStateHandler;

  public ModPlayerAnimationController(AbstractClientPlayer player, TickAnimationStateHandler tickAnimationStateHandler, AnimationStateHandler animationHandler) {
    super(player, animationHandler);
    this.tickAnimationStateHandler = tickAnimationStateHandler;
  }

  public ModPlayerAnimationController(AbstractClientPlayer player, TickAnimationStateHandler tickAnimationStateHandler, AnimationStateHandler animationHandler, Function<AnimationController, MochaEngine<AnimationController>> molangRuntime) {
    super(player, animationHandler, molangRuntime);
    this.tickAnimationStateHandler = tickAnimationStateHandler;
  }

  @Override
  public void tick(AnimationData state) {
    this.tickAnimationStateHandler.handle(this, state, (animation, startTick) -> {
      this.setAnimation(animation, startTick);
      return PlayState.CONTINUE;
    });
    super.tick(state);
  }

  @FunctionalInterface
  public interface TickAnimationStateHandler {
    void handle(AnimationController controller, AnimationData state, AnimationSetter animationSetter);
  }
}
