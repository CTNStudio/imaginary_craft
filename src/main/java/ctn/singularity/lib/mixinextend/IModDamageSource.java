package ctn.singularity.lib.mixinextend;

import ctn.singularity.lib.api.lobotomycorporation.LcDamage;
import ctn.singularity.lib.api.lobotomycorporation.LcLevel;
import net.minecraft.world.damagesource.DamageSource;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

public interface IModDamageSource {
  static IModDamageSource of(DamageSource source) {
    return (IModDamageSource) source;
  }

  @Nullable
  LcDamage getLcDamage();

  void setLcDamage(LcDamage type);

  @Nullable
  LcLevel getLcDamageLevel();

  void setDamageLevel(LcLevel pmLevel);

  int getInvincibleTick();

  void setInvincibleTick(int tick);
}
