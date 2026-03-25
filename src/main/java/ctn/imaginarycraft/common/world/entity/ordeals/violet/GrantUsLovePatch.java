package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import java.util.EnumSet;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import ctn.imaginarycraft.api.world.entity.ai.behavior.BTFactory;
import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.BTRoot;
import ctn.imaginarycraft.api.world.entity.ai.behavior.composite.ParallelNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.condition.ConditionBT;
import ctn.imaginarycraft.api.world.entity.ai.behavior.condition.DistanceLowerThanCondition;
import ctn.imaginarycraft.api.world.entity.ai.behavior.condition.TargetExistCondition;
import ctn.imaginarycraft.init.animmodels.ModAnimations;
import ctn.imaginarycraft.init.world.entity.ModFactions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.MobCombatBehaviors;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.IronGolemPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.TargetChasingGoal;

import java.util.EnumSet;

public class GrantUsLovePatch extends MobPatch<GrantUsLove> {
  public static final CombatBehaviors.Builder<GrantUsLovePatch> TENTACLE_GOAL = CombatBehaviors.<GrantUsLovePatch>builder()
    .newBehaviorSeries(
      CombatBehaviors.BehaviorSeries.<GrantUsLovePatch>builder()
        .weight(10.0F)
        .canBeInterrupted(true)
        .looping(false)
        .nextBehavior(CombatBehaviors.Behavior.<GrantUsLovePatch>builder()
          .animationBehavior(ModAnimations.GRANT_US_LOVE_SLASH)
          .withinEyeHeight()
          .withinDistance(0.0D, 5.0D)));

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
    this.original.goalSelector.addGoal(1, new MeleeAttackGoal(this.original, 1, false));
  }

  @Override
  public void initAnimator(Animator animator) {
    super.initAnimator(animator);
    // 待机
    animator.addLivingAnimation(LivingMotions.IDLE, ModAnimations.GRANT_US_LOVE_IDLE);
    // 死亡
    animator.addLivingAnimation(LivingMotions.DEATH, ModAnimations.GRANT_US_LOVE_DEATH);
  }

  public static class MeleeAttackGoal extends Goal {
    private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
    protected final Mob mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private final int attackInterval = 20;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastCanUseCheck;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;

    public MeleeAttackGoal(Mob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
      this.mob = mob;
      this.speedModifier = speedModifier;
      this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
      long i = this.mob.level().getGameTime();
      if (i - this.lastCanUseCheck < 20L) {
        return false;
      }

      this.lastCanUseCheck = i;
      LivingEntity livingentity = this.mob.getTarget();
      if (livingentity == null) {
        return false;
      }

      if (!livingentity.isAlive()) {
        return false;
      }

      if (canPenalize) {
        if (--this.ticksUntilNextPathRecalculation <= 0) {
          this.path = this.mob.getNavigation().createPath(livingentity, 0);
          this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
          return this.path != null;
        } else {
          return true;
        }
      }

      this.path = this.mob.getNavigation().createPath(livingentity, 0);
      return this.path != null ? true : this.mob.isWithinMeleeAttackRange(livingentity);
    }

    @Override
    public boolean canContinueToUse() {
      LivingEntity livingentity = this.mob.getTarget();
      if (livingentity == null) {
        return false;
      }

      if (!livingentity.isAlive()) {
        return false;
      }

      if (!this.followingTargetEvenIfNotSeen) {
        return !this.mob.getNavigation().isDone();
      }

      return !this.mob.isWithinRestriction(livingentity.blockPosition())
        ? false
        : !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
    }

    @Override
    public void start() {
      this.mob.getNavigation().moveTo(this.path, this.speedModifier);
      this.mob.setAggressive(true);
      this.ticksUntilNextPathRecalculation = 0;
      this.ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
      LivingEntity livingentity = this.mob.getTarget();
      if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
        this.mob.setTarget(null);
      }

      this.mob.setAggressive(false);
      this.mob.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
      return true;
    }

    @Override
    public void tick() {
      LivingEntity livingentity = this.mob.getTarget();
      if (livingentity == null) {
        return;
      }

      this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
      this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
      if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity))
        && this.ticksUntilNextPathRecalculation <= 0
        && (
        this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0
          || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0
          || this.mob.getRandom().nextFloat() < 0.05F
      )) {
        this.pathedTargetX = livingentity.getX();
        this.pathedTargetY = livingentity.getY();
        this.pathedTargetZ = livingentity.getZ();
        this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
        double d0 = this.mob.distanceToSqr(livingentity);
        if (this.canPenalize) {
          this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
          if (this.mob.getNavigation().getPath() != null) {
            net.minecraft.world.level.pathfinder.Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
            if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
              failedPathFindingPenalty = 0;
            else
              failedPathFindingPenalty += 10;
          } else {
            failedPathFindingPenalty += 10;
          }
        }
        if (d0 > 1024.0) {
          this.ticksUntilNextPathRecalculation += 10;
        } else if (d0 > 256.0) {
          this.ticksUntilNextPathRecalculation += 5;
        }

        if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
          this.ticksUntilNextPathRecalculation += 15;
        }

        this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
      }

      this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
      this.checkAndPerformAttack(livingentity);
    }

    protected void checkAndPerformAttack(LivingEntity target) {
      if (!this.canPerformAttack(target)) {
        return;
      }

      this.resetAttackCooldown();
      this.mob.swing(InteractionHand.MAIN_HAND);
      this.mob.doHurtTarget(target);
    }

    protected void resetAttackCooldown() {
      this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    protected boolean isTimeToAttack() {
      return this.ticksUntilNextAttack <= 0;
    }

    protected boolean canPerformAttack(LivingEntity entity) {
      return this.isTimeToAttack() && this.mob.isWithinMeleeAttackRange(entity) && this.mob.getSensing().hasLineOfSight(entity);
    }

    protected int getTicksUntilNextAttack() {
      return this.ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
      return this.adjustedTickDelay(20);
    }
  }
}
