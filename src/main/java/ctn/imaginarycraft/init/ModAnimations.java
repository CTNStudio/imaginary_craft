package ctn.imaginarycraft.init;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;

public final class ModAnimations {
  public static AnimationManager.AnimationAccessor<StaticAnimation> GRANT_US_LOVE_IDLE;

  public static void build(AnimationManager.AnimationBuilder builder) {
    GRANT_US_LOVE_IDLE = builder.nextAccessor("entity/grant_us_love/idle", (accessor) -> new StaticAnimation(true, accessor, Armatures.BIPED));
  }
}
