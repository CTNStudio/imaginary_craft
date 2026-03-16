package ctn.imaginarycraft.api.world.entity.ai.behavior.leaf;

import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;
import ctn.imaginarycraft.util.EntityUtil;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

/**
 * 看向目标
 */
public class RandomLookAction extends BTNode {

  final Mob mob;
  Vec3 pos;

  public RandomLookAction(Mob mob) {
    this.mob = mob;
  }

  @Override
  public void start() {
    super.start();
    pos = mob.getEyePosition().add(EntityUtil.sphere(1, mob.getRandom().nextFloat() * 6.28f, (float) Math.PI * 0.5f));
  }

  @Override
  public BTStatus execute() {
    mob.lookAt(EntityAnchorArgument.Anchor.EYES, pos);
    mob.getLookControl().setLookAt(pos);

    return BTStatus.RUNNING;
  }

}
