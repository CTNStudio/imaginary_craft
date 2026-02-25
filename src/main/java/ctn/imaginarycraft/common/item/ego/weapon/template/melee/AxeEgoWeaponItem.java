package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import ctn.imaginarycraft.core.capability.item.IEgoItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class AxeEgoWeaponItem extends AxeItem implements IMeleeEgoWeaponItem {

  public AxeEgoWeaponItem(Tier tier, Properties itemProperties, Builder egoWeaponBuilder) {
    super(tier, IEgoItem.add(itemProperties, egoWeaponBuilder));
  }
}

