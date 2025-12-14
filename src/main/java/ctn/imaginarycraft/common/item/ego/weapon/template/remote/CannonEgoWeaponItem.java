package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import software.bernie.geckolib.animation.AnimatableManager;

public class CannonEgoWeaponItem extends GeoRemoteEgoWeaponItem {
  public CannonEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
  }

  public CannonEgoWeaponItem(Builder builder) {
    super(builder);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }
}
