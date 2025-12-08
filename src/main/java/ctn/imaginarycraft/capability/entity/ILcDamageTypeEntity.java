package ctn.imaginarycraft.capability.entity;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import net.minecraft.world.entity.Entity;

public interface ILcDamageTypeEntity {
  LcDamageType getDamageType(Entity entity);
}
