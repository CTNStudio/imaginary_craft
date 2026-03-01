package ctn.imaginarycraft.common.world.item.ego.weapon.melee;

import ctn.imaginarycraft.api.world.item.IMeleeEgoWeaponItem;
import ctn.imaginarycraft.common.world.item.ego.weapon.EgoWeaponItem;

public abstract class MeleeEgoWeaponItem extends EgoWeaponItem implements IMeleeEgoWeaponItem {

  public MeleeEgoWeaponItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder) {
    super(itemProperties, egoWeaponBuilder);
  }
}
