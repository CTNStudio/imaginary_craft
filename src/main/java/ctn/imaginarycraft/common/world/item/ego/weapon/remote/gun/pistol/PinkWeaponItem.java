package ctn.imaginarycraft.common.world.item.ego.weapon.remote.gun.pistol;

import ctn.imaginarycraft.common.world.item.ego.weapon.remote.*;
import ctn.imaginarycraft.common.world.item.ego.weapon.remote.gun.*;
import software.bernie.geckolib.model.*;

public class PinkWeaponItem extends GunEgoWeaponItem {

  public PinkWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<RemoteEgoWeaponGeoItem> geoModel, GeoModel<RemoteEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public PinkWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }
}
