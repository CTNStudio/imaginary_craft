package ctn.imaginarycraft.mixin;

import ctn.imaginarycraft.api.IDamageSource;
import ctn.imaginarycraft.api.capability.item.IItemInvincibleTick;
import ctn.imaginarycraft.api.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevel;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcDamageUtil;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import ctn.imaginarycraft.init.ModCapabilitys;
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
  private int imaginaryCraft$invincibleTick;

  @Inject(method = "<init>(Lnet/minecraft/core/Holder;" +
    "Lnet/minecraft/world/entity/Entity;" +
    "Lnet/minecraft/world/entity/Entity;" +
    "Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
  private void imaginaryCraft$DamageSource(
    Holder<DamageType> type,
    Entity directEntity,
    Entity causingEntity,
    Vec3 damageSourcePosition,
    CallbackInfo ci
  ) {
    DamageSource damageSource = (DamageSource) (Object) this;
    ItemStack itemStack = LcDamageUtil.getDamageItemStack(damageSource);

    // 初始化默认值
    LcDamageType lcDamageType = null;
    LcLevel lcDamageLevel = null;
    int invincibleTick = 20;

    // 从物品获取信息
    if (itemStack != null) {
      IItemLcDamageType colorDamageTypeItem = itemStack.getCapability(ModCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ITEM);
      if (colorDamageTypeItem != null) {
        lcDamageType = colorDamageTypeItem.getLcDamageColorDamageType(itemStack);
      }

      IItemInvincibleTick invincibleTickItem = itemStack.getCapability(ModCapabilitys.InvincibleTick.INVINCIBLE_TICK_ITEM);
      if (invincibleTickItem != null) {
        invincibleTick = invincibleTickItem.getInvincibleTick(itemStack);
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
    this.imaginaryCraft$invincibleTick = invincibleTick;
    this.imaginaryCraft$lcDamageType = lcDamageType == null ?
      LcDamageType.byDamageType(type) : lcDamageType;
  }

  @Unique
  @Nullable
  @Override
  public LcDamageType getImaginaryCraft$LcDamageType() {
    return imaginaryCraft$lcDamageType;
  }

  @Unique
  @Nullable
  @Override
  public LcLevel getImaginaryCraft$LcDamageLevel() {
    return imaginaryCraft$lcDamageLevel;
  }

  @Unique
  @Override
  public int getImaginaryCraft$InvincibleTick() {
    return imaginaryCraft$invincibleTick;
  }

  @Unique
  @Override
  public void setImaginaryCraft$LcDamageType(LcDamageType type) {
    this.imaginaryCraft$lcDamageType = type;
  }

  @Unique
  @Override
  public void setImaginaryCraft$DamageLevel(@Nullable LcLevel level) {
    this.imaginaryCraft$lcDamageLevel = level;
  }

  @Unique
  @Override
  public void setImaginaryCraft$InvincibleTick(int tick) {
    this.imaginaryCraft$invincibleTick = tick;
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
}
