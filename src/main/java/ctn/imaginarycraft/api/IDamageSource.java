package ctn.imaginarycraft.api;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

public interface IDamageSource {
  static IDamageSource of(DamageSource source) {
    return (IDamageSource) source;
  }

  @Nullable
  LcDamageType getImaginaryCraft$LcDamageType();

  void setImaginaryCraft$LcDamageType(LcDamageType type);

  @Nullable
  LcLevelType getImaginaryCraft$LcDamageLevel();

  void setImaginaryCraft$DamageLevel(@Nullable LcLevelType pmLevel);

  int getImaginaryCraft$InvincibleTick();

  void setImaginaryCraft$InvincibleTick(int tick);

  @Unique
  boolean isImaginaryCraft$LcLevelNull();

  @Unique
  void setImaginaryCraft$LcLevelNull(boolean lcLevelNull);

  @Unique
  boolean isImaginaryCraft$LcDamageTypeNull();

  @Unique
  void setImaginaryCraft$LcDamageTypeNull(boolean lcDamageTypeNull);
}
