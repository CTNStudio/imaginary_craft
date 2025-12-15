package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import software.bernie.geckolib.animation.AnimatableManager;

// TODO 需要具备原版斧头的一切功能
public class AxeEgoWeaponItem extends GeoMeleeEgoWeaponItem {
  public AxeEgoWeaponItem(Builder builder) {
    super(builder);
  }

  public AxeEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }
}

