package ctn.imaginarycraft.mixin;

import ctn.imaginarycraft.api.ILivingDamageEvent$Post;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingDamageEvent.Post.class)
public abstract class LivingDamageEvent$PostMixin implements ILivingDamageEvent$Post {
  @Unique
  private DamageContainer projectMoon$damageContainer;

  @Inject(method = "<init>", at = @At("RETURN"))
  private void projectMoon$Post(LivingEntity entity, DamageContainer container, CallbackInfo ci) {
    projectMoon$damageContainer = container;
  }

  @Unique
  @Override
  public DamageContainer getImaginaryCraft$DamageContainer() {
    return projectMoon$damageContainer;
  }
}
