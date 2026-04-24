package ctn.imaginarycraft.common.world.entity.ordeals.violet;

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
import yesman.epicfight.world.entity.ai.goal.TargetChasingGoal;

public class GrantUsLovePatch extends MobPatch<GrantUsLove> {
	public GrantUsLovePatch(GrantUsLove entity) {
		super(entity, ModFactions.ORDEALS_VIOLET);
	}

	@Override
	protected void initAI() {
		super.initAI();
		this.original.goalSelector.addGoal(1, new TargetChasingGoal(this, this.original, 1.0D, false));

		this.original.goalSelector.addGoal(5, new AnimatedAttackGoal<>(this, CombatBehaviors.<GrantUsLovePatch>builder()
			.newBehaviorSeries(CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder().weight(8000.0F).canBeInterrupted(true).looping(false)
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
	}

	@Override
	public void initAnimator(Animator animator) {
		super.initAnimator(animator);

		animator.addLivingAnimation(LivingMotions.IDLE, GrantUsLoveAnimations.IDLE); // 待机
		animator.addLivingAnimation(LivingMotions.DEATH, GrantUsLoveAnimations.DEATH); // 死亡
	}

	@Override
	@Nullable
	public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
		return null;
	}

	@Override
	public void updateMotion(boolean considerInaction) {
		if (this.original.getHealth() <= 0.0F) {
			currentLivingMotion = LivingMotions.DEATH;
		} else if (this.state.inaction() && considerInaction) {
			currentLivingMotion = LivingMotions.INACTION;
		} else {
			currentLivingMotion = LivingMotions.IDLE;
		}
		this.currentCompositeMotion = this.currentLivingMotion;
	}

	@Override
	public void postTick() {
		super.postTick();
	}
}
