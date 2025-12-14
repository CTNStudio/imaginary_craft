package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import software.bernie.geckolib.animation.AnimatableManager;

public class PistolEgoWeaponItem extends GunEgoWeaponItem {
  public PistolEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
  }

  public PistolEgoWeaponItem(Builder builder) {
    super(builder);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }
}
