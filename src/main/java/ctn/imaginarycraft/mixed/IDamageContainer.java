package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.*;
import net.neoforged.neoforge.common.damagesource.*;

public interface IDamageContainer {
  static IDamageContainer of(DamageContainer damageContainer) {
    return (IDamageContainer) damageContainer;
  }

  default LcImmuneType getImaginaryCraft$LcImmuneType() {
    throw new NoMixinException();
  }

  default void getImaginaryCraft$LcImmuneType(LcImmuneType lcImmuneType) {
    throw new NoMixinException();
  }

  default DamageContainer getImaginaryCraft$This() {
    throw new NoMixinException();
  }
}
