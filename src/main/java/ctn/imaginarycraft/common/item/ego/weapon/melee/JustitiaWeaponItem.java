package ctn.imaginarycraft.common.item.ego.weapon.melee;

import ctn.imaginarycraft.common.item.ego.weapon.template.melee.GeoMeleeEgoWeaponItem;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class JustitiaWeaponItem extends GeoMeleeEgoWeaponItem {

  public JustitiaWeaponItem(Properties properties, Builder builder, GeoModel<GeoMeleeEgoWeaponItem> model, GeoModel<GeoMeleeEgoWeaponItem> guiModel) {
    super(properties, builder, model, guiModel);
  }

  public JustitiaWeaponItem(Properties properties, Builder builder, String modPath) {
    super(properties, builder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }
}
