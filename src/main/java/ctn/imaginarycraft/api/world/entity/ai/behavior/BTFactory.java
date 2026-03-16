package ctn.imaginarycraft.api.world.entity.ai.behavior;

import ctn.imaginarycraft.api.world.entity.ai.behavior.composite.ParallelNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.composite.SelectorNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.composite.SequenceNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.condition.Condition;
import ctn.imaginarycraft.api.world.entity.ai.behavior.decoration.ConditionNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.decoration.InverterNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.decoration.RepeaterNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.leaf.GoalWrapper;
import ctn.imaginarycraft.api.world.entity.ai.behavior.leaf.RandomWaitAction;
import ctn.imaginarycraft.api.world.entity.ai.behavior.leaf.WaitAction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * 行为树工厂
 */
public class BTFactory {
  public static SequenceNode sequence() {
    return new SequenceNode();
  }

  public static SelectorNode selector() {
    return new SelectorNode();
  }

  public static ParallelNode parallel(ParallelNode.Policy successPolicy,
                                      ParallelNode.Policy failurePolicy) {
    return new ParallelNode(successPolicy, failurePolicy);
  }

  public static InverterNode inverter(BTNode child) {
    return new InverterNode(child);
  }

  public static RepeaterNode repeater(int count, BTNode child) {
    return new RepeaterNode(count, child);
  }

  public static RepeaterNode infinite(BTNode child) {
    return new RepeaterNode(-1, child);
  }

  public static ConditionNode condition(Condition condition, BTNode child) {
    return new ConditionNode(condition, child);
  }

  public static BTNode wait(int ticks) {
    return new WaitAction(ticks);
  }

  public static BTNode waitRandom(int minInclusive, int maxInclusive, RandomSource random) {
    return new RandomWaitAction(minInclusive, maxInclusive, random);
  }

  public static BTNode waitRandom(int minInclusive, int maxInclusive) {
    return waitRandom(minInclusive, maxInclusive, RandomSource.create());
  }

  public static BTNode success(Runnable runnable) {
    return new BTNode() {
      @Override
      public BTStatus execute() {
        runnable.run();
        return BTStatus.SUCCESS;
      }
    };
  }

  public static BTNode waitForever() {
    return new BTNode() {
      @Override
      public BTStatus execute() {
        return BTStatus.RUNNING;
      }
    };
  }

  public static ParallelNode withTimer(int duration, BTNode node) {
    return parallel(ParallelNode.Policy.REQUIRE_ONE, ParallelNode.Policy.REQUIRE_ONE)
      .addChild(wait(duration))
      .addChild(node);
  }

  public static ParallelNode withTimer(int duration) {
    return parallel(ParallelNode.Policy.REQUIRE_ONE, ParallelNode.Policy.REQUIRE_ONE)
      .addChild(wait(duration));
  }

  public static GoalWrapper goal(Goal goal) {
    return new GoalWrapper(goal);
  }
}
