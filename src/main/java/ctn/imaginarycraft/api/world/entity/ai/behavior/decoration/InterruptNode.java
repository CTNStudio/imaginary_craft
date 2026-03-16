package ctn.imaginarycraft.api.world.entity.ai.behavior.decoration;

import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;

/**
 *
 */
public class InterruptNode extends DecorationNode {
  protected InterruptNode(BTNode child) {
    super(child);
  }

  @Override
  public BTStatus execute() {
    return null;
  }
}
