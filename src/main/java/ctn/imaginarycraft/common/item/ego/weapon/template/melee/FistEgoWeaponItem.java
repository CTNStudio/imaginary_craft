package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import software.bernie.geckolib.animation.AnimatableManager;

public class FistEgoWeaponItem extends GeoMeleeEgoWeaponItem {
  public FistEgoWeaponItem(Builder builder) {
    super(builder);
  }

  public FistEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }
}
