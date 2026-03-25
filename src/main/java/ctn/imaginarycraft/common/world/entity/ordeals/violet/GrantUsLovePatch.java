package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.ai.ModMeleeAttackGoal;
import ctn.imaginarycraft.init.animmodels.ModAnimations;
import ctn.imaginarycraft.init.world.entity.ModFactions;
import ctn.imaginarycraft.init.world.entity.OrdealsEntityTypes;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.registry.entries.EpicFightAttributes;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

public class GrantUsLovePatch extends MobPatch<GrantUsLove> {
  public static final CombatBehaviors.Builder<GrantUsLovePatch> TENTACLE_GOAL = CombatBehaviors.<GrantUsLovePatch>builder()
    .newBehaviorSeries(
      CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder()
        .weight(10.0F)
        .canBeInterrupted(true)
        .looping(false)
        .nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder()
          .animationBehavior(ModAnimations.GRANT_US_LOVE_SLASH)
//          .health(5, HealthPoint.Comparator.LESS_RATIO)
//          .withinEyeHeight()
          .withinDistance(0.0D, 5.0D)));

  public static void initAttributes(EntityAttributeModificationEvent event) {
    event.add(OrdealsEntityTypes.GRANT_US_LOVE.get(), EpicFightAttributes.IMPACT, 8.0D);
    event.add(OrdealsEntityTypes.GRANT_US_LOVE.get(), EpicFightAttributes.MAX_STRIKES, Double.MAX_VALUE);
  }

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
    this.original.goalSelector.addGoal(0, new AnimatedAttackGoal<>(this, TENTACLE_GOAL.build(this)));
    this.original.goalSelector.addGoal(1, new ModMeleeAttackGoal(this.original, 1, false));
  }

  @Override
  public void initAnimator(Animator animator) {
    super.initAnimator(animator);
    // 待机
    animator.addLivingAnimation(LivingMotions.IDLE, ModAnimations.GRANT_US_LOVE_IDLE);
    // 死亡
    animator.addLivingAnimation(LivingMotions.DEATH, ModAnimations.GRANT_US_LOVE_DEATH);
  }
}
