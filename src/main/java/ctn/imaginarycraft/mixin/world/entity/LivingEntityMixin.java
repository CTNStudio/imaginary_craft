package ctn.imaginarycraft.mixin.world.entity;

import ctn.imaginarycraft.mixed.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.extensions.*;
import org.spongepowered.asm.mixin.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension, ILivingEntity {

  public LivingEntityMixin(EntityType<?> entityType, Level level) {
    super(entityType, level);
  }
}
