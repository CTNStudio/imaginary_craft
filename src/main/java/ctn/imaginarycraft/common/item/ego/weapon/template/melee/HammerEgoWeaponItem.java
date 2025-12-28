package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class HammerEgoWeaponItem extends GeoMeleeEgoWeaponItem {

  public HammerEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoMeleeEgoWeaponItem> geoModel, GeoModel<GeoMeleeEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public HammerEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }
}
