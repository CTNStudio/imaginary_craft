package ctn.imaginarycraft.api;

import ctn.imaginarycraft.api.lobotomycorporation.LcImmuneType;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Unique;

public interface IDamageContainer {
  static IDamageContainer of(DamageContainer damageContainer) {
    return (IDamageContainer) damageContainer;
  }

  @Unique
  LcImmuneType getImaginaryCraft$LcImmuneType();

  @Unique
  void getImaginaryCraft$LcImmuneType(LcImmuneType lcImmuneType);

  DamageContainer getImaginaryCraft$This();
}
