package ctn.imaginarycraft.mixinextend;

import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.level.LcLevel;
import net.minecraft.world.damagesource.DamageSource;

import javax.annotation.Nullable;

public interface IDamageSource {
  static IDamageSource of(DamageSource source) {
    return (IDamageSource) source;
  }

  @Nullable
  LcDamageType getLcDamageType();

  void setLcDamageType(LcDamageType type);

  @Nullable
  LcLevel getLcDamageLevel();

  void setDamageLevel(LcLevel pmLevel);

  int getInvincibleTick();

  void setInvincibleTick(int tick);
}
