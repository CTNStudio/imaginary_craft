package ctn.imaginarycraft.mixin;

import ctn.imaginarycraft.api.IDamageContainer;
import ctn.imaginarycraft.api.IDamageSource;
import ctn.imaginarycraft.api.capability.item.IItemRandomDamage;
import ctn.imaginarycraft.api.lobotomycorporation.LcImmuneType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcDamageUtil;
import ctn.imaginarycraft.init.ModCapabilitys;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
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
    setPostAttackInvulnerabilityTicks(IDamageSource.of(source).getImaginaryCraft$InvincibleTick());

    IItemRandomDamage capability = null;
    ItemStack stack = LcDamageUtil.getDamageItemStack(source);
    if (stack != null) {
      capability = stack.getCapability(ModCapabilitys.RANDOM_DAMAGE_ITEM);
    }

    if (capability == null) {
      return;
    }

    setNewDamage(capability.countBidirectionalFluctuationDamageValue(this.originalDamage, stack, RandomSource.create()));
  }

  @Unique
  @Override
  public LcImmuneType getImaginaryCraft$LcImmuneType() {
    return imaginaryCraft$lcImmuneType;
  }

  @Unique
  @Override
  public void getImaginaryCraft$LcImmuneType(final LcImmuneType lcImmuneType) {
    this.imaginaryCraft$lcImmuneType = lcImmuneType;
  }

  @Override
  public DamageContainer getImaginaryCraft$This() {
    return (DamageContainer) (Object) this;
  }
}
