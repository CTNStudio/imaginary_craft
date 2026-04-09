package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.jointpart.JointPartLivingEntityPatch;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.damagesource.StunType;

public class GrantUsLoveTentaclePatch extends JointPartLivingEntityPatch<GrantUsLoveTentacle> {
  public GrantUsLoveTentaclePatch(GrantUsLoveTentacle entity) {
    super(entity);
  }

  @Override
  public void updateMotion(boolean considerInaction) {

  }

  @Override
  public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
    return null;
  }

  @Override
  public Faction getFaction() {
    return null;
  }
}
