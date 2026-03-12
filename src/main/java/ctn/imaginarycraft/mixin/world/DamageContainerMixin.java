package ctn.imaginarycraft.mixin.world;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.mixed.*;
import net.minecraft.world.damagesource.*;
import net.neoforged.neoforge.common.damagesource.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(DamageContainer.class)
public abstract class DamageContainerMixin implements IDamageContainer {
  @Unique
  private LcImmuneType imaginaryCraft$lcImmuneType = null;

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
  @Override
  public LcImmuneType imaginaryCraft$getLcImmuneType() {
    return imaginaryCraft$lcImmuneType;
  }

  @Unique
  @Override
  public void imaginaryCraft$setLcImmuneType(final LcImmuneType lcImmuneType) {
    this.imaginaryCraft$lcImmuneType = lcImmuneType;
  }

  @Unique
  public DamageContainer imaginaryCraft$this() {
    return (DamageContainer) (Object) this;
  }
}
