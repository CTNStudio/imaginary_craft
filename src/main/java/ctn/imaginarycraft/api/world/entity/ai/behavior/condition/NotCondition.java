package ctn.imaginarycraft.api.world.entity.ai.behavior.condition;

public class NotCondition extends AbstractConditionLeaf {
  Condition child;

  public NotCondition(Condition child) {
    this.child = child;
  }

  @Override
  public boolean check() {
    return !child.check();
  }
}
