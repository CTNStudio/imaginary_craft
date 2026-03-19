package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.init.ModAnimations;
import ctn.imaginarycraft.init.world.ModFactions;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;

public class GrantUsLovePatch extends MobPatch<GrantUsLove> {
  public GrantUsLovePatch(GrantUsLove entity) {
    super(entity, ModFactions.ORDEALS_VIOLET);
  }

  @Override
  public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
    return ModAnimations.GRANT_US_LOVE_DEFAULT;
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
//
//    this.original.goalSelector.addGoal(
//      1,
//      new AnimatedAttackGoal<>(this, new CombatBehaviors.Builder<>().build(this))
//    );
//    this.original.goalSelector.addGoal(2, new TargetChasingGoal(this, this.getOriginal(), 1.2f, true));
//    this.original.goalSelector.addGoal(3, new RandomStrollGoal(original, 1.0f));
//
//    this.original.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(original, Player.class, true));
  }

  @Override
  public void initAnimator(Animator animator) {
    super.initAnimator(animator);
    // 待机
    animator.addLivingAnimation(LivingMotions.IDLE, ModAnimations.GRANT_US_LOVE_DEFAULT);
    // 死亡
    animator.addLivingAnimation(LivingMotions.DEATH, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.WALK, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.RUN, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.FALL, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.SIT, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.JUMP, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.SLEEP, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.AIM, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.SHOT, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.DRINK, ModAnimations.GRANT_US_LOVE_DEFAULT);
    animator.addLivingAnimation(LivingMotions.EAT, ModAnimations.GRANT_US_LOVE_DEFAULT);
  }
}
