package ctn.imaginarycraft.mixin.world.entity;

import ctn.imaginarycraft.mixed.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import org.spongepowered.asm.mixin.*;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayer {

  protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
    super(entityType, level);
  }
}
