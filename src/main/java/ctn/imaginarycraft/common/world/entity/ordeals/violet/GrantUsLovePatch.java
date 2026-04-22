package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.ai.ModMeleeAttackGoal;
import ctn.imaginarycraft.init.epicfight.ModFactions;
import ctn.imaginarycraft.init.epicfight.animations.GrantUsLoveAnimations;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

public class GrantUsLovePatch extends MobPatch<GrantUsLove> {
	public GrantUsLovePatch(GrantUsLove entity) {
		super(entity, ModFactions.ORDEALS_VIOLET);
	}

	@Override
	protected void initAI() {
		super.initAI();

		this.original.goalSelector.addGoal(0, new AnimatedAttackGoal<>(this, CombatBehaviors.<GrantUsLovePatch>builder()
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(80.0F).canBeInterrupted(true).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.SLASH)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.STAB_L1)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.STAB_L2)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.STAB_L3)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.STAB_R1)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.STAB_R2)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.STAB_R3)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.SWING_L1)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.SWING_L2)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.SWING_L3)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.SWING_R1)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.SWING_R2)))
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(50.0F).canBeInterrupted(false).looping(false)
				.nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder().animationBehavior(GrantUsLoveAnimations.SWING_R3)))
			.build(this)));
		this.original.goalSelector.addGoal(1, new ModMeleeAttackGoal(this.original, 1, false));
	}

	@Override
	public void initAnimator(Animator animator) {
		super.initAnimator(animator);
		// 待机
		animator.addLivingAnimation(LivingMotions.IDLE, GrantUsLoveAnimations.IDLE);
		// 死亡
		animator.addLivingAnimation(LivingMotions.DEATH, GrantUsLoveAnimations.DEATH);
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
