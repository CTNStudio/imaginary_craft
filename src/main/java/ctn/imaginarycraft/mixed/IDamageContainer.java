package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.*;
import net.neoforged.neoforge.common.damagesource.*;
import org.spongepowered.asm.mixin.*;

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
