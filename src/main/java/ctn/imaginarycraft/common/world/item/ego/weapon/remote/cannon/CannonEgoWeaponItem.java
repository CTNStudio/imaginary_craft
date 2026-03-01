package ctn.imaginarycraft.common.world.item.ego.weapon.remote.cannon;

import ctn.imaginarycraft.common.world.item.ego.weapon.remote.RemoteEgoWeaponGeoItem;
import ctn.imaginarycraft.common.world.item.ego.weapon.remote.gun.GunEgoWeaponItem;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

/**
 * 加农炮
 */
public class CannonEgoWeaponItem extends GunEgoWeaponItem {

  public CannonEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<RemoteEgoWeaponGeoItem> geoModel, GeoModel<RemoteEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public CannonEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }

}
