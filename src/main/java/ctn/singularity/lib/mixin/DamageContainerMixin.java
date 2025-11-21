package ctn.singularity.lib.mixin;

import ctn.singularity.lib.api.lobotomycorporation.util.LcDamageUtil;
import ctn.singularity.lib.capability.IRandomDamage;
import ctn.singularity.lib.init.LibCapabilitys;
import ctn.singularity.lib.mixinextend.IModDamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageContainer.class)
public abstract class DamageContainerMixin {

	@Shadow
	public abstract void setPostAttackInvulnerabilityTicks(int ticks);

  @Shadow
  public abstract void setNewDamage(final float damage);

  /**
   * 处理随机伤害及伤害无敌
   */
	@Inject(method = "<init>", at = @At("RETURN"))
	private void singularityLib$DamageContainer(DamageSource source, float originalDamage, CallbackInfo ci) {
		setPostAttackInvulnerabilityTicks(IModDamageSource.of(source).getInvincibleTick());

    IRandomDamage capability = null;
		ItemStack stack = LcDamageUtil.getDamageItemStack(source);
		if (stack != null) {
      capability = stack.getCapability(LibCapabilitys.RANDOM_DAMAGE_ITEM);
		}

    // TODO 后续引入其他可以造成随机伤害的
    if (capability == null) {
      return;
    }

		RandomSource randomSource;
		Entity entity = source.getEntity();
		if (entity == null) {
      entity = source.getDirectEntity();
		}

    if (entity != null) {
      randomSource = entity.getRandom();
    } else {
      randomSource = RandomSource.create();
    }

    setNewDamage(capability.getDamageValue(randomSource) + (originalDamage - capability.getMaxDamage()));
	}
}
