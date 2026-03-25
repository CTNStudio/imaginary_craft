package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.init.animmodels.ModAnimations;
import ctn.imaginarycraft.init.world.entity.ModFactions;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;

public class GrantUsLovePatch extends MobPatch<GrantUsLove> {
  public GrantUsLovePatch(GrantUsLove entity) {
    super(entity, ModFactions.ORDEALS_VIOLET);
  }

  @Override
  public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
    return ModAnimations.GRANT_US_LOVE_IDLE;
  }

  @Override
  public <A extends Animator> A getAnimator() {
    return super.getAnimator();
  }

  @Override
  public void updateMotion(boolean b) {
    super.commonMobUpdateMotion(b);
  }

  @Override
  protected void initAI() {
    super.initAI();
  }

  @Override
  public void initAnimator(Animator animator) {
    super.initAnimator(animator);
    // 待机
    animator.addLivingAnimation(LivingMotions.IDLE, ModAnimations.GRANT_US_LOVE_IDLE);
    // 死亡
    animator.addLivingAnimation(LivingMotions.DEATH, ModAnimations.GRANT_US_LOVE_DEATH);

//    animator.addLivingAnimation(LivingMotions.WALK,  Animations.EMPTY_ANIMATION);
//    animator.addLivingAnimation(LivingMotions.RUN,  Animations.EMPTY_ANIMATION);
//    animator.addLivingAnimation(LivingMotions.FALL,  Animations.EMPTY_ANIMATION);
//    animator.addLivingAnimation(LivingMotions.SIT,  Animations.EMPTY_ANIMATION);
//    animator.addLivingAnimation(LivingMotions.JUMP,  Animations.EMPTY_ANIMATION);
//    animator.addLivingAnimation(LivingMotions.SLEEP,  Animations.EMPTY_ANIMATION);
//    animator.addLivingAnimation(LivingMotions.AIM,  Animations.EMPTY_ANIMATION);
//    animator.addLivingAnimation(LivingMotions.SHOT,  Animations.EMPTY_ANIMATION);
//    animator.addLivingAnimation(LivingMotions.DRINK,  Animations.EMPTY_ANIMATION);
//    animator.addLivingAnimation(LivingMotions.EAT,  Animations.EMPTY_ANIMATION);
  }
}
