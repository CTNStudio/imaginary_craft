package ctn.imaginarycraft.common.world.item.ego.weapon.remote.gun.rifle;

import ctn.imaginarycraft.common.world.item.ego.weapon.remote.RemoteEgoWeaponGeoItem;
import ctn.imaginarycraft.common.world.item.ego.weapon.remote.gun.GunEgoWeaponItem;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class RifleEgoWeaponItem extends GunEgoWeaponItem {

  public RifleEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<RemoteEgoWeaponGeoItem> geoModel, GeoModel<RemoteEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public RifleEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }
}
