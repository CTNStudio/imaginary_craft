package ctn.singularity.lib.capability.entity;

import ctn.singularity.lib.api.lobotomycorporation.LcDamage;
import net.minecraft.world.entity.Entity;

public interface ILcDamageTypeEntity {
  LcDamage getDamageType(Entity entity);
}
