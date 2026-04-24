package ctn.imaginarycraft.init.epicfight.animations;

import ctn.imaginarycraft.client.animmodels.animations.grantuslove.GrantUsLoveTentacleAttackAnimation;
import ctn.imaginarycraft.init.epicfight.ModArmatures;
import ctn.imaginarycraft.init.world.ModColliders;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.main.EpicFightSharedConstants;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

public final class GrantUsLoveAnimations {
	public static AnimationManager.AnimationAccessor<LongHitAnimation> DEATH;
	public static AnimationManager.AnimationAccessor<ActionAnimation> EXTEND;
	public static AnimationManager.AnimationAccessor<StaticAnimation> IDLE;
	public static AnimationManager.AnimationAccessor<ActionAnimation> NO_TENTACLES;
	public static AnimationManager.AnimationAccessor<ActionAnimation> ULTIMATE_SKILL;
	public static AnimationManager.AnimationAccessor<ActionAnimation> SHRINK;
	public static AnimationManager.AnimationAccessor<AttackAnimation> SLASH;
	public static AnimationManager.AnimationAccessor<AttackAnimation> STAB_L1;
	public static AnimationManager.AnimationAccessor<AttackAnimation> STAB_L2;
	public static AnimationManager.AnimationAccessor<AttackAnimation> STAB_L3;
	public static AnimationManager.AnimationAccessor<AttackAnimation> STAB_R1;
	public static AnimationManager.AnimationAccessor<AttackAnimation> STAB_R2;
	public static AnimationManager.AnimationAccessor<AttackAnimation> STAB_R3;
	public static AnimationManager.AnimationAccessor<AttackAnimation> SWING_L1;
	public static AnimationManager.AnimationAccessor<AttackAnimation> SWING_L2;
	public static AnimationManager.AnimationAccessor<AttackAnimation> SWING_L3;
	public static AnimationManager.AnimationAccessor<AttackAnimation> SWING_R1;
	public static AnimationManager.AnimationAccessor<AttackAnimation> SWING_R2;
	public static AnimationManager.AnimationAccessor<AttackAnimation> SWING_R3;

	static void build(AnimationManager.AnimationBuilder builder) {
		IDLE = builder.nextAccessor("entity/grant_us_love/idle", (accessor) -> new StaticAnimation(
			true, accessor, ModArmatures.GRANT_US_LOVE));
		DEATH = builder.nextAccessor("entity/grant_us_love/death", (accessor) -> new LongHitAnimation(
			0.15f, accessor, ModArmatures.GRANT_US_LOVE));
		EXTEND = builder.nextAccessor("entity/grant_us_love/extend", (accessor) -> new ActionAnimation(
			EpicFightSharedConstants.GENERAL_ANIMATION_TRANSITION_TIME, accessor, ModArmatures.GRANT_US_LOVE));
		SHRINK = builder.nextAccessor("entity/grant_us_love/shrink", (accessor) -> new ActionAnimation(
			EpicFightSharedConstants.GENERAL_ANIMATION_TRANSITION_TIME, accessor, ModArmatures.GRANT_US_LOVE));
		NO_TENTACLES = builder.nextAccessor("entity/grant_us_love/no_tentacles", (accessor) -> new ActionAnimation(
			EpicFightSharedConstants.GENERAL_ANIMATION_TRANSITION_TIME, accessor, ModArmatures.GRANT_US_LOVE));

		ULTIMATE_SKILL = builder.nextAccessor("entity/grant_us_love/ultimate_skill", (accessor) -> new ActionAnimation(
			0.3f, 3f, SHRINK.registryName().getPath(), ModArmatures.GRANT_US_LOVE)
			.addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS,
				AnimationEvent.SimpleEvent.create((AnimationEvent.E0) (a, b, c) -> {
					a.playAnimationInstantly(NO_TENTACLES);
				}, AnimationEvent.Side.BOTH)));

		SLASH = builder.nextAccessor("entity/grant_us_love/slash", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.1f, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0, 0, 0.76f, 0.1f, 4.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().root, ModColliders.GRANT_US_LOVE_TENTACLE_SLASH))
			.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
			.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(32))
			.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN))
			.addEvents(AnimationEvent.InTimeEvent.create(0.76f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT)
				.params(new Vec3f(0, -0.2F, 0), ModArmatures.GRANT_US_LOVE.get().root, 4D, 0.3F)));

		//region stab 戳刺
		STAB_L1 = builder.nextAccessor("entity/grant_us_love/stab_l1", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.05F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.333f, 0.333f, 0.7f, 4.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_2_L, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		STAB_L2 = builder.nextAccessor("entity/grant_us_love/stab_l2", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.05F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.333f, 0.333f, 0.7f, 4.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_2_L, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		STAB_L3 = builder.nextAccessor("entity/grant_us_love/stab_l3", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.05F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.333f, 0.333f, 0.7f, 4.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_2_L, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		STAB_R1 = builder.nextAccessor("entity/grant_us_love/stab_r1", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.05F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.333f, 0.333f, 0.7f, 4.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_2_R, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		STAB_R2 = builder.nextAccessor("entity/grant_us_love/stab_r2", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.05F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.333f, 0.333f, 0.7f, 2.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_2_R, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		STAB_R3 = builder.nextAccessor("entity/grant_us_love/stab_r3", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			00.05F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.333f, 0.333f, 0.7f, 2.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_2_R, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		//endregion

		//region swing 鞭打
		SWING_L1 = builder.nextAccessor("entity/grant_us_love/swing_l1", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.2F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.467f, 0.467f, 0.7f, 4.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_0_L, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_1_L, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_2_L, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		SWING_L2 = builder.nextAccessor("entity/grant_us_love/swing_l2", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.2F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.467f, 0.467f, 0.7f, 4.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_0_L, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_1_L, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_2_L, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		SWING_L3 = builder.nextAccessor("entity/grant_us_love/swing_l3", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.2F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.467f, 0.467f, 0.7f, 4.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_0_L, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_1_L, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_2_L, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		SWING_R1 = builder.nextAccessor("entity/grant_us_love/swing_r1", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.2F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.467f, 0.467f, 0.7f, 4.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_0_R, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_1_R, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_2_R, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		SWING_R2 = builder.nextAccessor("entity/grant_us_love/swing_r2", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.2F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.467f, 0.467f, 0.7f, 2.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_0_R, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_1_R, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_2_R, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));

		SWING_R3 = builder.nextAccessor("entity/grant_us_love/swing_r3", (accessor) -> new GrantUsLoveTentacleAttackAnimation(
			0.2F, accessor, ModArmatures.GRANT_US_LOVE,
			new AttackAnimation.Phase(0.0f, 0.467f, 0.467f, 0.7f, 2.2f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_0_R, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_1_R, ModColliders.TENTACLE),
				AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_2_R, ModColliders.TENTACLE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8))));
		//endregion
	}
}
