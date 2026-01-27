package ctn.imaginarycraft.client.animation.player.controller;

import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import com.zigythebird.playeranimcore.bones.AdvancedPlayerAnimBone;
import com.zigythebird.playeranimcore.bones.PivotBone;
import com.zigythebird.playeranimcore.bones.PlayerAnimBone;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import team.unnamed.mocha.MochaEngine;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class ModPlayerAnimationController extends PlayerAnimationController {

  public ModPlayerAnimationController(AbstractClientPlayer player, AnimationStateHandler animationHandler) {
    super(player, animationHandler);
  }

  public ModPlayerAnimationController(AbstractClientPlayer player, AnimationStateHandler animationHandler, Function<AnimationController, MochaEngine<AnimationController>> molangRuntime) {
    super(player, animationHandler, molangRuntime);
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
