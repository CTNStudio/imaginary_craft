package ctn.imaginarycraft.common.world.item.ego.weapon.remote.gun.rifle;

import ctn.imaginarycraft.common.world.item.ego.weapon.remote.*;
import ctn.imaginarycraft.common.world.item.ego.weapon.remote.gun.*;
import software.bernie.geckolib.model.*;

public class RifleEgoWeaponItem extends GunEgoWeaponItem {

  public RifleEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<RemoteEgoWeaponGeoItem> geoModel, GeoModel<RemoteEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public RifleEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }
}
