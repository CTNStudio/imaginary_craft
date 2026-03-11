package ctn.imaginarycraft.mixed;

import ctn.imaginarycraft.api.*;
import net.neoforged.neoforge.common.damagesource.*;
import net.neoforged.neoforge.event.entity.living.*;

public interface ILivingDamageEvent$Post {
  static ILivingDamageEvent$Post of(LivingDamageEvent.Post o) {
    return (ILivingDamageEvent$Post) o;
  }

  default DamageContainer getImaginaryCraft$DamageContainer() {
    throw new NoMixinException();
  }
}
