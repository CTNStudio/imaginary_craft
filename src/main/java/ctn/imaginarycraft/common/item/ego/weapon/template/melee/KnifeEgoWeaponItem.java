package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class KnifeEgoWeaponItem extends GeoMeleeEgoWeaponItem {

  public KnifeEgoWeaponItem(Properties properties, Builder builder, GeoModel<GeoMeleeEgoWeaponItem> model, GeoModel<GeoMeleeEgoWeaponItem> guiModel) {
    super(properties, builder, model, guiModel);
  }

  public KnifeEgoWeaponItem(Properties properties, Builder builder, String modPath) {
    super(properties, builder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }
}
