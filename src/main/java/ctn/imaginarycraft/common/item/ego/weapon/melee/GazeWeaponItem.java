package ctn.imaginarycraft.common.item.ego.weapon.melee;

import ctn.imaginarycraft.common.item.ego.weapon.template.melee.MeleeEgoWeaponGeoItem;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class GazeWeaponItem extends MeleeEgoWeaponGeoItem {
  public GazeWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<MeleeEgoWeaponGeoItem> geoModel, GeoModel<MeleeEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public GazeWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }
}
