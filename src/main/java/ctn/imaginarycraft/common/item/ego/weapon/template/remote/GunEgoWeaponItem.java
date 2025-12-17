package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import software.bernie.geckolib.model.GeoModel;

public abstract class GunEgoWeaponItem extends GeoRemoteEgoWeaponItem {
  public GunEgoWeaponItem(Properties properties, Builder builder, GeoModel<GeoRemoteEgoWeaponItem> model, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(properties, builder, model, guiModel);
  }

  public GunEgoWeaponItem(Properties properties, Builder builder, String modPath) {
    super(properties, builder, modPath);
  }
}
