package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;

import javax.annotation.*;

public interface IDamageSource {
  static IDamageSource of(DamageSource source) {
    return (IDamageSource) source;
  }

  void setImaginaryCraft$WeaponItem(ItemStack itemStack);

  @Nullable
  LcDamageType getImaginaryCraft$LcDamageType();

  void setImaginaryCraft$LcDamageType(LcDamageType type);

  @Nullable
  LcLevelType getImaginaryCraft$LcDamageLevel();

  void setImaginaryCraft$DamageLevel(@Nullable LcLevelType pmLevel);

  @Unique
  boolean isImaginaryCraft$LcLevelNull();

  @Unique
  void setImaginaryCraft$LcLevelNull(boolean lcLevelNull);

  @Unique
  boolean isImaginaryCraft$LcDamageTypeNull();

  @Unique
  void setImaginaryCraft$LcDamageTypeNull(boolean lcDamageTypeNull);
}
