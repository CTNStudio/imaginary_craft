package ctn.imaginarycraft.common.world.item.ego.weapon.remote.cannon;

import ctn.imaginarycraft.common.world.item.ego.weapon.remote.*;
import ctn.imaginarycraft.common.world.item.ego.weapon.remote.gun.*;
import software.bernie.geckolib.model.*;

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
}
