package ctn.imaginarycraft.client.particle.trail;

import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.*;
import yesman.epicfight.api.client.animation.property.*;
import yesman.epicfight.client.particle.*;
import yesman.epicfight.world.capabilities.entitypatch.*;

public class RedEyesTachiItemTrail extends AnimationTrailParticle {
  public RedEyesTachiItemTrail(ClientLevel level, LivingEntityPatch<?> owner, Joint joint, AssetAccessor<? extends StaticAnimation> animation, TrailInfo trailInfo) {
    super(level, owner, joint, animation, trailInfo);
  }

  @Override
  public ParticleRenderType getRenderType() {
    return super.getRenderType();
  }
}
