package ctn.imaginarycraft.mixed;

import net.neoforged.neoforge.common.damagesource.*;
import net.neoforged.neoforge.event.entity.living.*;

public interface ILivingDamageEvent$Post {
  static ILivingDamageEvent$Post of(LivingDamageEvent o) {
    return (ILivingDamageEvent$Post) o;
  }

  DamageContainer getImaginaryCraft$DamageContainer();
}
