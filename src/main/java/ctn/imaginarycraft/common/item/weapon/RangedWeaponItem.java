package ctn.imaginarycraft.common.item.weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * 远程武器
 */
public abstract class RangedWeaponItem extends WeaponItem {
  private final boolean isConsumingBullets;

  public RangedWeaponItem(Properties properties, Builder builder, boolean isConsumingBullets) {
    super(properties, builder);
    this.isConsumingBullets = isConsumingBullets;
  }

  /**
   * 是否消耗子弹
   */
  public boolean isConsumingBullets() {
    return isConsumingBullets;
  }

  /**
   * 是否消耗子弹
   */
  public boolean isConsumingBullets(LivingEntity entity, ItemStack stack) {
    return isConsumingBullets;
  }
}
