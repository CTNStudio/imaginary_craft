package ctn.imaginarycraft.common.world.item.ego.weapon.melee.axe;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.api.world.item.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AxeEgoWeaponItem extends AxeItem implements IMeleeEgoWeaponItem {
  private final @Nullable LcDamageType lcDamageType;
  private final Set<LcDamageType> canCauseLcDamageTypes;

  public AxeEgoWeaponItem(Tier tier, Properties itemProperties, Builder builder) {
    super(tier, IEgoWeaponItem.add(itemProperties, builder));
    this.lcDamageType = builder.lcDamageType;
    this.canCauseLcDamageTypes = builder.canCauseLcDamageTypes;
  }

  @Override
  public @Nullable LcDamageType getLcDamageType(ItemStack stack) {
    return lcDamageType;
  }

  @Override
  public @NotNull Set<LcDamageType> getCanCauseLcDamageTypes(ItemStack stack) {
    return canCauseLcDamageTypes;
  }
}

