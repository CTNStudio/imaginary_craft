package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import software.bernie.geckolib.animation.AnimatableManager;

public class KnifeEgoWeaponItem extends GeoMeleeEgoWeaponItem {
  public KnifeEgoWeaponItem(Builder builder) {
    super(builder);
  }

  public KnifeEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }
}
