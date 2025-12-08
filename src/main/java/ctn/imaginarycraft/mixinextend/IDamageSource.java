package ctn.imaginarycraft.mixinextend;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import net.minecraft.world.damagesource.DamageSource;

import javax.annotation.Nullable;

public interface IDamageSource {
  static IDamageSource of(DamageSource source) {
    return (IDamageSource) source;
  }

  @Nullable
  LcDamageType getLcDamageType();

  void setLcDamageType(LcDamageType type);

  LcLevel getLcDamageLevel();

  void setDamageLevel(LcLevel pmLevel);

  int getInvincibleTick();

  void setInvincibleTick(int tick);
}
