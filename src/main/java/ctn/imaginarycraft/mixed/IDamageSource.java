package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.LcLevel;
import ctn.imaginarycraft.api.NoMixinException;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface IDamageSource {
  static IDamageSource of(DamageSource source) {
    return source;
  }

  default void imaginaryCraft$setWeaponItem(ItemStack itemStack) {
    throw new NoMixinException();
  }

  @Nullable
  default LcDamageType imaginaryCraft$getLcDamageType() {
    throw new NoMixinException();
  }

  default void imaginaryCraft$setLcDamageType(LcDamageType type) {
    throw new NoMixinException();
  }

  @Nullable
  default LcLevel imaginaryCraft$getLcDamageLevel() {
    throw new NoMixinException();
  }

  default void imaginaryCraft$setDamageLevel(@Nullable LcLevel pmLevel) {
    throw new NoMixinException();
  }

  default boolean imaginaryCraft$isLcLevelNull() {
    throw new NoMixinException();
  }

  default void imaginaryCraft$setLcLevelNull(boolean lcLevelNull) {
    throw new NoMixinException();
  }

  default boolean imaginaryCraft$isLcDamageTypeNull() {
    throw new NoMixinException();
  }

  default void imaginaryCraft$setLcDamageTypeNull(boolean lcDamageTypeNull) {
    throw new NoMixinException();
  }
}
