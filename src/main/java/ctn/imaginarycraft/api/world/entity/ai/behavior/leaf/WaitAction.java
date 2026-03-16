package ctn.imaginarycraft.api.world.entity.ai.behavior.leaf;

import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;

/**
 * 等待节点，到达指定时间返回{@link BTStatus#SUCCESS SUCCESS}
 */
public class WaitAction extends BTNode {
  protected int waitTicks;
  protected int currentTicks = 0;

  public WaitAction(int waitTicks) {
    this.waitTicks = waitTicks;
  }

  @Override
  public BTStatus execute() {
    if (++currentTicks >= waitTicks) {
      return BTStatus.SUCCESS;
    }
    return BTStatus.RUNNING;
  }

  @Override
  protected void cleanup() {
    currentTicks = 0;
  }

  public int getWaitTicks() {
    return waitTicks;
  }

  public int getCurrentTicks() {
    return currentTicks;
  }
}
