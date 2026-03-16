package ctn.imaginarycraft.api.world.entity.ai.behavior.composite;

import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeNode extends BTNode {
  protected List<BTNode> children = new ArrayList<>();

  @Override
  public BTStatus execute() {
    return null;
  }

  public List<BTNode> getChildren() {
    return children;
  }
}
