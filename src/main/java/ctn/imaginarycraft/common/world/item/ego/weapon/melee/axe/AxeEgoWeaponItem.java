package ctn.imaginarycraft.common.world.item.ego.weapon.melee.axe;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.world.item.IEgoItem;
import ctn.imaginarycraft.api.world.item.IMeleeEgoWeaponItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class AxeEgoWeaponItem extends AxeItem implements IMeleeEgoWeaponItem {
  private final @Nullable LcDamageType lcDamageType;
  private final Set<LcDamageType> canCauseLcDamageTypes;

  public AxeEgoWeaponItem(Tier tier, Properties itemProperties, Builder builder) {
    super(tier, IEgoItem.add(itemProperties, builder));
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

