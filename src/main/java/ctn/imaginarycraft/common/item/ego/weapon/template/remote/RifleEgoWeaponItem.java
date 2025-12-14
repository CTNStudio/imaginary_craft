package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import software.bernie.geckolib.animation.AnimatableManager;

public class RifleEgoWeaponItem extends GunEgoWeaponItem {
  public RifleEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
  }

  public RifleEgoWeaponItem(Builder builder) {
    super(builder);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }
}
