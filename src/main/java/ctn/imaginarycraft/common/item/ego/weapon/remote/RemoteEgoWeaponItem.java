package ctn.imaginarycraft.common.item.ego.weapon.remote;

import ctn.imaginarycraft.common.item.ego.weapon.EgoWeaponItem;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeaponItem extends EgoWeaponItem {
  private final boolean isConsumingBullets;

  public RemoteEgoWeaponItem(Properties properties, Builder builder, boolean isConsumingBullets) {
    super(properties, builder);
    this.isConsumingBullets = isConsumingBullets;
  }

  /**
   * 是否消耗子弹
   */
  public boolean isConsumingBullets() {
    return isConsumingBullets;
  }
}
