package ctn.imaginarycraft.api.world.entity.ai.behavior.leaf;

import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;
import net.minecraft.world.entity.Mob;

/**
 * 看向目标
 */
public class LookAtTargetAction extends BTNode {
  final Mob mob;

  public LookAtTargetAction(Mob mob) {
    this.mob = mob;
  }

  @Override
  public BTStatus execute() {
    if (mob.getTarget() == null) {
      return BTStatus.FAILURE;
    }
    mob.lookAt(mob.getTarget(), 90, 85);
    mob.getLookControl().setLookAt(mob.getTarget());

    return BTStatus.RUNNING;
  }
}
