package ctn.imaginarycraft.init.animmodels;

import ctn.imaginarycraft.client.model.animmodels.animations.grantuslove.GrantUsLoveTentacleAttackAnimation;
import ctn.imaginarycraft.init.world.ModColliders;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.damagesource.StunType;

/**
 * 动画
 */
public final class ModAnimations {
  //region grant_us_love 请给我们爱
  public static AnimationManager.AnimationAccessor<StaticAnimation> GRANT_US_LOVE_IDLE;
  public static AnimationManager.AnimationAccessor<StaticAnimation> GRANT_US_LOVE_DEATH;
  public static AnimationManager.AnimationAccessor<AttackAnimation> GRANT_US_LOVE_STAB_FRONT;
  public static AnimationManager.AnimationAccessor<AttackAnimation> GRANT_US_LOVE_SLASH;
  public static AnimationManager.AnimationAccessor<AttackAnimation> GRANT_US_LOVE_SWING_L1;
  public static AnimationManager.AnimationAccessor<AttackAnimation> GRANT_US_LOVE_SWING_L2;
  public static AnimationManager.AnimationAccessor<AttackAnimation> GRANT_US_LOVE_SWING_L3;
  public static AnimationManager.AnimationAccessor<AttackAnimation> GRANT_US_LOVE_SWING_R1;
  public static AnimationManager.AnimationAccessor<AttackAnimation> GRANT_US_LOVE_SWING_R2;
  public static AnimationManager.AnimationAccessor<AttackAnimation> GRANT_US_LOVE_SWING_R3;
  //endregion

  public static void build(AnimationManager.AnimationBuilder builder) {
    //region grant_us_love 请给我们爱
    GRANT_US_LOVE_IDLE = builder.nextAccessor("entity/grant_us_love/idle", (accessor) -> new StaticAnimation(true, accessor, ModArmatures.GRANT_US_LOVE));
    GRANT_US_LOVE_DEATH = builder.nextAccessor("entity/grant_us_love/death", (accessor) -> new StaticAnimation(false, accessor, ModArmatures.GRANT_US_LOVE));

    GRANT_US_LOVE_STAB_FRONT = builder.nextAccessor("entity/grant_us_love/stab_front", (accessor) ->
      new GrantUsLoveTentacleAttackAnimation(0.08F, 0.5F, 0.55F, 0.5F, 1.5f, ModColliders.TENTACLE, ModArmatures.GRANT_US_LOVE.get().tentacle1_0_R, accessor, ModArmatures.GRANT_US_LOVE)
        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, GrantUsLoveTentacleAttackAnimation.COMBO_ATTACK_DIRECTION_MODIFIER));

    GRANT_US_LOVE_SLASH = builder.nextAccessor("entity/grant_us_love/slash", (accessor) ->
      new GrantUsLoveTentacleAttackAnimation(0.08F, accessor, ModArmatures.GRANT_US_LOVE,
        new AttackAnimation.Phase(0.0F, 30 / 11f, 0, 0.5F, 30 / 29f, Float.MAX_VALUE, InteractionHand.MAIN_HAND,
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_1_R, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_2_R, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_3_R, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_1_R, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_2_R, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_3_R, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_1_R, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_2_R, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_3_R, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_1_L, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_2_L, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle1_3_L, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_1_L, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_2_L, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle2_3_L, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_1_L, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_2_L, ModColliders.TENTACLE),
          AttackAnimation.JointColliderPair.of(ModArmatures.GRANT_US_LOVE.get().tentacle3_3_L, ModColliders.TENTACLE)))
        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN));

    GRANT_US_LOVE_SWING_L1 = builder.nextAccessor("entity/grant_us_love/swing_l1", (accessor) ->
      new GrantUsLoveTentacleAttackAnimation(0.08F, 0.5F, 0.55F, 0.5F, 1.5f, ModColliders.TENTACLE, ModArmatures.GRANT_US_LOVE.get().tentacle1_2_L, accessor, ModArmatures.GRANT_US_LOVE)
        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, GrantUsLoveTentacleAttackAnimation.COMBO_ATTACK_DIRECTION_MODIFIER));

    GRANT_US_LOVE_SWING_L2 = builder.nextAccessor("entity/grant_us_love/swing_l2", (accessor) ->
      new GrantUsLoveTentacleAttackAnimation(0.08F, 0.5F, 0.55F, 0.5F, 1.5f, ModColliders.TENTACLE, ModArmatures.GRANT_US_LOVE.get().tentacle2_2_L, accessor, ModArmatures.GRANT_US_LOVE)
        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, GrantUsLoveTentacleAttackAnimation.COMBO_ATTACK_DIRECTION_MODIFIER));

    GRANT_US_LOVE_SWING_L3 = builder.nextAccessor("entity/grant_us_love/swing_l3", (accessor) ->
      new GrantUsLoveTentacleAttackAnimation(0.08F, 0.5F, 0.55F, 0.5F, 1.5f, ModColliders.TENTACLE, ModArmatures.GRANT_US_LOVE.get().tentacle3_2_L, accessor, ModArmatures.GRANT_US_LOVE)
        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, GrantUsLoveTentacleAttackAnimation.COMBO_ATTACK_DIRECTION_MODIFIER));

    GRANT_US_LOVE_SWING_R1 = builder.nextAccessor("entity/grant_us_love/swing_r1", (accessor) ->
      new GrantUsLoveTentacleAttackAnimation(0.08F, 0.5F, 0.55F, 0.5F, 1.5f, ModColliders.TENTACLE, ModArmatures.GRANT_US_LOVE.get().tentacle1_2_R, accessor, ModArmatures.GRANT_US_LOVE)
        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, GrantUsLoveTentacleAttackAnimation.COMBO_ATTACK_DIRECTION_MODIFIER));

    GRANT_US_LOVE_SWING_R2 = builder.nextAccessor("entity/grant_us_love/swing_r2", (accessor) ->
      new GrantUsLoveTentacleAttackAnimation(0.08F, 0.5F, 0.55F, 0.5F, 1.5f, ModColliders.TENTACLE, ModArmatures.GRANT_US_LOVE.get().tentacle2_2_R, accessor, ModArmatures.GRANT_US_LOVE)
        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, GrantUsLoveTentacleAttackAnimation.COMBO_ATTACK_DIRECTION_MODIFIER));

    GRANT_US_LOVE_SWING_R3 = builder.nextAccessor("entity/grant_us_love/swing_r3", (accessor) ->
      new GrantUsLoveTentacleAttackAnimation(0.08F, 0.5F, 0.55F, 0.5F, 1.5f, ModColliders.TENTACLE, ModArmatures.GRANT_US_LOVE.get().tentacle3_2_R, accessor, ModArmatures.GRANT_US_LOVE)
        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, GrantUsLoveTentacleAttackAnimation.COMBO_ATTACK_DIRECTION_MODIFIER));
    //endregion
  }
}
