package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class PistolEgoWeaponItem extends GunEgoWeaponItem {
  public PistolEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoRemoteEgoWeaponItem> geoModel, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public PistolEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }
}
