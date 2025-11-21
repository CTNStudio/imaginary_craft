package ctn.singularity.lib.mixinextend;

import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public interface IModLivingDamageEvent$Post {
  static IModLivingDamageEvent$Post of(LivingDamageEvent o) {
    return (IModLivingDamageEvent$Post) o;
  }

  DamageContainer getDamageContainer();
}
