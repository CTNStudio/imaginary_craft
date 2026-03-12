package ctn.imaginarycraft.mixin.world;

import ctn.imaginarycraft.mixed.IDamageContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageContainer.class)
public abstract class DamageContainerMixin implements IDamageContainer {

  @Shadow
  public abstract void setPostAttackInvulnerabilityTicks(int ticks);

  @Shadow
  public abstract void setNewDamage(final float damage);

  @Shadow
  @Final
  private float originalDamage;

  @Inject(method = "<init>", at = @At("RETURN"))
  private void imaginaryCraft$DamageContainer(DamageSource source, float originalDamage, CallbackInfo ci) {
  }

  @Unique
  public DamageContainer imaginaryCraft$this() {
    return (DamageContainer) (Object) this;
  }
}
