package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeaponItem extends EgoWeaponItem {

  public RemoteEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
  }

  public RemoteEgoWeaponItem(Builder builder) {
    this(builder.buildProperties(), builder);
  }
}
