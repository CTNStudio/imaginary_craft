package ctn.imaginarycraft.common.world.item.ego.weapon.melee.spear;

import ctn.imaginarycraft.api.world.item.IMeleeEgoWeaponItem;
import ctn.imaginarycraft.common.world.item.ego.weapon.melee.MeleeEgoWeaponGeoItem;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class SpearEgoWeaponItem extends MeleeEgoWeaponGeoItem {

  public SpearEgoWeaponItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, GeoModel<MeleeEgoWeaponGeoItem> geoModel, GeoModel<MeleeEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public SpearEgoWeaponItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }
}
