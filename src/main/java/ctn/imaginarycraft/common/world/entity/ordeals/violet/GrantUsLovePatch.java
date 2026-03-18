package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.init.world.ModFactions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;

public class GrantUsLovePatch extends MobPatch<GrantUsLove> {
  public GrantUsLovePatch(GrantUsLove entity) {
    super(entity, ModFactions.ORDEALS_VIOLET);
  }

  @Override
  public void updateMotion(boolean considerInaction) {

  }

  @Override
  public AssetAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
    return null;
  }
}
