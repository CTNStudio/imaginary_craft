package ctn.imaginarycraft.common.item.ego.weapon.melee;

import ctn.imaginarycraft.common.item.ego.weapon.template.melee.GeoMeleeEgoWeaponItem;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class LifeForADaredevilWeaponItem extends GeoMeleeEgoWeaponItem {

  public LifeForADaredevilWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoMeleeEgoWeaponItem> geoModel, GeoModel<GeoMeleeEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public LifeForADaredevilWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }
}
