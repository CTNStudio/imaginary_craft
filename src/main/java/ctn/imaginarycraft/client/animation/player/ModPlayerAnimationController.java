package ctn.imaginarycraft.client.animation.player;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.bones.AdvancedPlayerAnimBone;
import com.zigythebird.playeranimcore.bones.PivotBone;
import com.zigythebird.playeranimcore.bones.PlayerAnimBone;
import com.zigythebird.playeranimcore.enums.PlayState;
import net.minecraft.client.player.AbstractClientPlayer;
import team.unnamed.mocha.MochaEngine;

import java.util.Collections;
import java.util.Map;
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

  public Map<String, AdvancedPlayerAnimBone> getBones() {
    return Collections.unmodifiableMap(bones);
  }

  public Map<String, PlayerAnimBone> getActiveBones() {
    return Collections.unmodifiableMap(activeBones);
  }

  public Map<String, PivotBone> getPivotBone() {
    return Collections.unmodifiableMap(pivotBones);
  }
}
