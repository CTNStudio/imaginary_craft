package ctn.imaginarycraft.api.world.entity.ai.behavior.leaf;

import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

/**
 * 包装现有的Goal作为行为，大概率有问题，极不推荐使用
 */
public class GoalWrapper extends BTNode {
  private final Goal goal;

  public GoalWrapper(Goal goal) {
    this.goal = goal;
  }

  @Override
  public BTStatus execute() {
//        if (!goal.canUse()) {
//            return BTStatus.FAILURE;
//        }

    if (goal.canContinueToUse()) {
      goal.tick();
      return BTStatus.RUNNING;
    }

    return BTStatus.SUCCESS;
  }

  @Override
  public boolean canUse() {
    return super.canUse();
  }

  @Override
  public boolean isReady() {
    return super.isReady() && goal.canUse();
  }

  @Override
  public boolean canContinueToUse() {
    return super.canContinueToUse();
  }

  @Override
  public void start() {
    goal.start();
    super.start();
  }

  @Override
  public void stop() {
    goal.stop();
    super.stop();
  }

  @Override
  public void setFlags(EnumSet<Flag> flagSet) {
    goal.setFlags(flagSet);
  }
}
