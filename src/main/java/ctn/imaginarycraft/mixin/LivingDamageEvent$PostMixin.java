package ctn.imaginarycraft.mixin;

import ctn.imaginarycraft.mixed.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.common.damagesource.*;
import net.neoforged.neoforge.event.entity.living.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(LivingDamageEvent.Post.class)
public abstract class LivingDamageEvent$PostMixin implements ILivingDamageEvent$Post {
  @Unique
  private DamageContainer projectMoon$damageContainer;

  @Inject(method = "<init>", at = @At("RETURN"))
  private void imaginarycraft$projectMoon$Post(LivingEntity entity, DamageContainer container, CallbackInfo ci) {
    projectMoon$damageContainer = container;
  }

  @Override
  public DamageContainer getImaginaryCraft$DamageContainer() {
    return projectMoon$damageContainer;
  }
}
