package ctn.imaginarycraft.init.animmodels;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;

/**
 * 动画
 */
public final class ModAnimations {

  //region grant_us_love 请给我们爱
  public static AnimationManager.AnimationAccessor<StaticAnimation> GRANT_US_LOVE_IDLE;
  public static AnimationManager.AnimationAccessor<StaticAnimation> GRANT_US_LOVE_BEHIND;
  public static AnimationManager.AnimationAccessor<StaticAnimation> GRANT_US_LOVE_FRONT;
  //endregion

  public static void build(AnimationManager.AnimationBuilder builder) {
    GRANT_US_LOVE_IDLE = builder.nextAccessor("entity/grant_us_love/idle", (accessor) -> new StaticAnimation(false, accessor, ModArmatures.GRANT_US_LOVE));
    GRANT_US_LOVE_BEHIND = builder.nextAccessor("entity/grant_us_love/behind", (accessor) -> new StaticAnimation(false, accessor, ModArmatures.GRANT_US_LOVE));
    GRANT_US_LOVE_FRONT = builder.nextAccessor("entity/grant_us_love/front", (accessor) -> new StaticAnimation(false, accessor, ModArmatures.GRANT_US_LOVE));
  }
}
