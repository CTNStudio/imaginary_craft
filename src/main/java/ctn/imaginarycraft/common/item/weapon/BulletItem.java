package ctn.imaginarycraft.common.item.weapon;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileItem;

public abstract class BulletItem extends Item implements ProjectileItem {
  public BulletItem(Properties properties) {
    super(properties);
  }
}
