package ctn.imaginarycraft.common.item.weapon;

/**
 * 远程武器
 */
public abstract class RemoteWeaponItem extends WeaponItem {
  private final boolean isConsumingBullets;

  public RemoteWeaponItem(Properties properties, Builder builder, boolean isConsumingBullets) {
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
