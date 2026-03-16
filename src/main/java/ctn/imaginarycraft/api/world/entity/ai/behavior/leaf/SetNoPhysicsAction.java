package ctn.imaginarycraft.api.world.entity.ai.behavior.leaf;

import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;
import net.minecraft.world.entity.Mob;

public class SetNoPhysicsAction extends BTNode {
  final Mob mob;
  final boolean isNoPhysics;

  public SetNoPhysicsAction(Mob mob, boolean isNoPhysics) {
    this.mob = mob;
    this.isNoPhysics = isNoPhysics;
  }

  @Override
  public BTStatus execute() {
    this.mob.noPhysics = this.isNoPhysics;
    return BTStatus.SUCCESS;
  }
}
