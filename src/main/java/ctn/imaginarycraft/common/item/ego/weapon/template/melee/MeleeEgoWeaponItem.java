package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;

public abstract class MeleeEgoWeaponItem extends EgoWeaponItem implements IMeleeEgoWeaponItem {

  public MeleeEgoWeaponItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder) {
    super(itemProperties, egoWeaponBuilder);
  }
}
