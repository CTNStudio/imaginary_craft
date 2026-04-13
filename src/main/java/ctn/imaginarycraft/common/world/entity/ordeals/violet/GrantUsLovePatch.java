package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.ai.ModMeleeAttackGoal;
import ctn.imaginarycraft.api.world.entity.jointpart.MultiJointPartMobPatch;
import ctn.imaginarycraft.init.epicfight.ModFactions;
import ctn.imaginarycraft.init.epicfight.animmodels.ModAnimations;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

public class GrantUsLovePatch extends MultiJointPartMobPatch<GrantUsLoveTentaclePatch, GrantUsLove> {
	//  public static final CombatBehaviors.Builder<GrantUsLovePatch> TENTACLE_GOAL =
	public GrantUsLovePatch(GrantUsLove entity) {
		super(entity, ModFactions.ORDEALS_VIOLET);
	}

	@Override
	protected void initAI() {
		super.initAI();

		this.original.goalSelector.addGoal(0, new AnimatedAttackGoal<>(this, CombatBehaviors.<GrantUsLovePatch>builder()
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(true).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_SLASH)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_STAB_L1)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_STAB_L2)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_STAB_L3)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_STAB_R1)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_STAB_R2)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_STAB_R3)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_SWING_L1)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_SWING_L2)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_SWING_L3)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_SWING_R1)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_SWING_R2)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(ModAnimations.GRANT_US_LOVE_SWING_R3)))
			.build(this)));
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

	@Override
	@Nullable
	public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
		return null;
	}

	@Override
	public void updateMotion(boolean b) {
//    super.commonMobUpdateMotion(b);
	}
}
