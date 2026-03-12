package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.*;
import net.neoforged.neoforge.common.damagesource.*;

public interface IDamageContainer {
  static IDamageContainer of(DamageContainer damageContainer) {
    return (IDamageContainer) damageContainer;
  }

  default LcImmuneType imaginaryCraft$getLcImmuneType() {
    throw new NoMixinException();
  }

  default void imaginaryCraft$setLcImmuneType(LcImmuneType lcImmuneType) {
    throw new NoMixinException();
  }
}
