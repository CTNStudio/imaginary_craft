package ctn.imaginarycraft.api.world.entity.ai.behavior.composite;


import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;
import ctn.imaginarycraft.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

public class WeightNode extends CompositeNode {
  private final List<Integer> weights;
  private BTNode currentChild;

  public WeightNode() {
    this.children = new ArrayList<>();
    this.weights = new ArrayList<>();
  }

  public WeightNode addChild(int weight, BTNode child) {
    this.children.add(child);
    this.weights.add(weight);
    return this;
  }

  @Override
  public void start() {
    super.start();
    this.currentChild = EntityUtil.getRandomByWeightInt(children, weights);
  }

  @Override
  public BTStatus execute() {
    return this.currentChild.execute();
  }

  @Override
  protected void cleanup() {
    super.cleanup();
    if (this.currentChild != null) {
      this.currentChild.stop();
    }

    this.currentChild = null;
  }
}
