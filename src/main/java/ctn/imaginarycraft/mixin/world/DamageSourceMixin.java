package ctn.imaginarycraft.mixin.world;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.LcLevel;
import ctn.imaginarycraft.mixed.IDamageSource;
import ctn.imaginarycraft.util.LcDamageTypeUtil;
import ctn.imaginarycraft.util.LcLevelUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

/**
 * @author 尽
 */
@Mixin(DamageSource.class)
public abstract class DamageSourceMixin implements IDamageSource {
	@Unique
	@Nullable
	private LcDamageType imaginaryCraft$lcDamageType;

	@Unique
	@Nullable
	private LcLevel imaginaryCraft$lcDamageLevel;

	@Unique
	private ItemStack imaginaryCraft$attackItemStack;

	@Inject(method = "<init>(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
	private void imaginaryCraft$DamageSource(
		Holder<DamageType> type,
		Entity directEntity,
		Entity causingEntity,
		Vec3 damageSourcePosition,
		CallbackInfo ci
	) {
		DamageSource damageSource = (DamageSource) (Object) this;
		ItemStack itemStack = LcDamageTypeUtil.getDamageItemStack(damageSource);
		this.imaginaryCraft$attackItemStack = itemStack;

		@Nullable LcDamageType itemLcDamageType = null;
		@Nullable LcDamageType damageLcDamageType = LcDamageType.byDamageType(type);


		// 从物品获取信息
		if (itemStack != null) {
			if (itemStack.isEmpty()) {
				itemLcDamageType = LcDamageType.byDamageType(type);
				if (itemLcDamageType == null) {
					itemLcDamageType = LcDamageTypeUtil.getLcDamageType(itemStack);
				}
			} else {
				itemLcDamageType = LcDamageTypeUtil.getLcDamageType(itemStack);
			}
		}

		if (directEntity != null || causingEntity != null) {
			Entity entity = directEntity == null ? causingEntity : directEntity;
			var level = LcLevelUtil.getLevel(entity);
			if (level == LcLevel.ZAYIN || entity instanceof Player) {
				@Nullable LcLevel itemLcDamageLevel = null;
				if (itemStack != null) {
					if (itemLcDamageLevel == null) {
						itemLcDamageLevel = LcLevelUtil.getLevel(itemStack);
					}
				}

				level = itemLcDamageLevel;
			}

			this.imaginaryCraft$lcDamageLevel = level;
		}

		this.imaginaryCraft$lcDamageType = itemLcDamageType == null ? damageLcDamageType : itemLcDamageType;
	}

	@Override
	public void imaginaryCraft$setWeaponItem(ItemStack itemStack) {
		imaginaryCraft$attackItemStack = itemStack;
	}

	@Nullable
	@Override
	public LcDamageType imaginaryCraft$getLcDamageType() {
		return imaginaryCraft$lcDamageType;
	}

	@Override
	public void imaginaryCraft$setLcDamageType(LcDamageType type) {
		this.imaginaryCraft$lcDamageType = type;
	}

	@Nullable
	@Override
	public LcLevel imaginaryCraft$getLcDamageLevel() {
		return imaginaryCraft$lcDamageLevel;
	}

	@Override
	public void imaginaryCraft$setDamageLevel(@Nullable LcLevel level) {
		this.imaginaryCraft$lcDamageLevel = level;
	}

	@WrapMethod(method = "getWeaponItem")
	private ItemStack imaginaryCraft$getWeaponItem(Operation<ItemStack> original) {
		return imaginaryCraft$attackItemStack == null ? original.call() : imaginaryCraft$attackItemStack;
	}
}
