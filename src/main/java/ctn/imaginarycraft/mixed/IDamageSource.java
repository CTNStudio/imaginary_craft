package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.item.*;

import javax.annotation.*;

public interface IDamageSource {
  static IDamageSource of(DamageSource source) {
    return source;
  }

  default void setImaginaryCraft$WeaponItem(ItemStack itemStack) {
    throw new NoMixinException();
  }

  @Nullable
  default LcDamageType getImaginaryCraft$LcDamageType() {
    throw new NoMixinException();
  }

  default void setImaginaryCraft$LcDamageType(LcDamageType type) {
    throw new NoMixinException();
  }

  @Nullable
  default LcLevel getImaginaryCraft$LcDamageLevel() {
    throw new NoMixinException();
  }

  default void setImaginaryCraft$DamageLevel(@Nullable LcLevel pmLevel) {
    throw new NoMixinException();
  }

  default boolean isImaginaryCraft$LcLevelNull() {
    throw new NoMixinException();
  }

  default void setImaginaryCraft$LcLevelNull(boolean lcLevelNull) {
    throw new NoMixinException();
  }

  default boolean isImaginaryCraft$LcDamageTypeNull() {
    throw new NoMixinException();
  }

  default void setImaginaryCraft$LcDamageTypeNull(boolean lcDamageTypeNull) {
    throw new NoMixinException();
  }
}
