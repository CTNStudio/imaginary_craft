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
  private boolean imaginaryCraft$isLcLevelNull;

  @Unique
  private boolean imaginaryCraft$isLcDamageTypeNull;

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

    // 初始化默认值
    LcDamageType lcDamageType = null;
    LcLevel lcDamageLevel = null;

    // 从物品获取信息
    if (itemStack != null) {
      if (itemStack.isEmpty()) {
        lcDamageType = LcDamageType.byDamageType(type);
        if (lcDamageType == null) {
          lcDamageType = LcDamageTypeUtil.getLcDamageType(itemStack);
        }
      } else {
        lcDamageType = LcDamageTypeUtil.getLcDamageType(itemStack);
      }

      if (lcDamageLevel == null) {
        lcDamageLevel = LcLevelUtil.getLevel(itemStack);
      }
    }

    if (lcDamageLevel == null && directEntity != null) {
      lcDamageLevel = LcLevelUtil.getLevel(directEntity);
    }

    if (lcDamageLevel == null && causingEntity != null) {
      lcDamageLevel = LcLevelUtil.getLevel(causingEntity);
    }

    // 应用最终值
    this.imaginaryCraft$lcDamageLevel = lcDamageLevel;
    this.imaginaryCraft$lcDamageType = lcDamageType == null ? LcDamageType.byDamageType(type) : lcDamageType;
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

  @Override
  public boolean imaginaryCraft$isLcLevelNull() {
    return imaginaryCraft$isLcLevelNull;
  }

  @Override
  public void imaginaryCraft$setLcLevelNull(final boolean lcLevelNull) {
    imaginaryCraft$isLcLevelNull = lcLevelNull;
  }

  @Override
  public boolean imaginaryCraft$isLcDamageTypeNull() {
    return imaginaryCraft$isLcDamageTypeNull;
  }

  @Override
  public void imaginaryCraft$setLcDamageTypeNull(final boolean lcDamageTypeNull) {
    imaginaryCraft$isLcDamageTypeNull = lcDamageTypeNull;
  }

  @WrapMethod(method = "getWeaponItem")
  private ItemStack imaginaryCraft$getWeaponItem(Operation<ItemStack> original) {
    return imaginaryCraft$attackItemStack == null ? original.call() : imaginaryCraft$attackItemStack;
  }
}
