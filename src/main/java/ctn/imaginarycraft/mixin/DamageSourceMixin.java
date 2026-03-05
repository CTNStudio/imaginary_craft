package ctn.imaginarycraft.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.mixed.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.core.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import javax.annotation.*;

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
  private LcLevelType imaginaryCraft$lcDamageLevel;

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
    ItemStack itemStack = LcDamageUtil.getDamageItemStack(damageSource);
    this.imaginaryCraft$attackItemStack = itemStack;

    // 初始化默认值
    LcDamageType lcDamageType = null;
    LcLevelType lcDamageLevel = null;

    // 从物品获取信息
    if (itemStack != null) {
      lcDamageType = LcDamageUtil.getLcDamageType(itemStack);

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

  @Unique
  @Override
  public void setImaginaryCraft$WeaponItem(ItemStack itemStack) {
    imaginaryCraft$attackItemStack = itemStack;
  }

  @Unique
  @Nullable
  @Override
  public LcDamageType getImaginaryCraft$LcDamageType() {
    return imaginaryCraft$lcDamageType;
  }

  @Unique
  @Override
  public void setImaginaryCraft$LcDamageType(LcDamageType type) {
    this.imaginaryCraft$lcDamageType = type;
  }

  @Unique
  @Nullable
  @Override
  public LcLevelType getImaginaryCraft$LcDamageLevel() {
    return imaginaryCraft$lcDamageLevel;
  }

  @Unique
  @Override
  public void setImaginaryCraft$DamageLevel(@Nullable LcLevelType level) {
    this.imaginaryCraft$lcDamageLevel = level;
  }

  @Unique
  @Override
  public boolean isImaginaryCraft$LcLevelNull() {
    return imaginaryCraft$isLcLevelNull;
  }

  @Unique
  @Override
  public void setImaginaryCraft$LcLevelNull(final boolean lcLevelNull) {
    imaginaryCraft$isLcLevelNull = lcLevelNull;
  }

  @Unique
  @Override
  public boolean isImaginaryCraft$LcDamageTypeNull() {
    return imaginaryCraft$isLcDamageTypeNull;
  }

  @Unique
  @Override
  public void setImaginaryCraft$LcDamageTypeNull(final boolean lcDamageTypeNull) {
    imaginaryCraft$isLcDamageTypeNull = lcDamageTypeNull;
  }

  @WrapMethod(method = "getWeaponItem")
  private ItemStack imaginaryCraft$getWeaponItem(Operation<ItemStack> original) {
    return imaginaryCraft$attackItemStack == null ? original.call() : imaginaryCraft$attackItemStack;
  }
}
