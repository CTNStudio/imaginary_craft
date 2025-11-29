package ctn.imaginarycraft.mixin;

import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.level.LcLevel;
import ctn.imaginarycraft.api.lobotomycorporation.damage.util.LcDamageUtil;
import ctn.imaginarycraft.capability.ILcLevel;
import ctn.imaginarycraft.capability.entity.IInvincibleTickEntity;
import ctn.imaginarycraft.capability.entity.ILcDamageTypeEntity;
import ctn.imaginarycraft.capability.item.IInvincibleTickItem;
import ctn.imaginarycraft.capability.item.ILcDamageTypeItem;
import ctn.imaginarycraft.init.ModCapabilitys;
import ctn.imaginarycraft.mixinextend.IDamageSource;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
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
@Implements(@Interface(iface = IDamageSource.class, prefix = "iSingularityLib$"))
public abstract class DamageSourceMixin implements IDamageSource {
  @Unique
  @Nullable
  private LcDamageType imaginaryCraft$lcDamageTypeType;

  @Unique
  private LcLevel imaginaryCraft$lcDamageLevel;

  @Unique
  private int imaginaryCraft$invincibleTick;

  @Inject(method = "<init>(Lnet/minecraft/core/Holder;" +
    "Lnet/minecraft/world/entity/Entity;" +
    "Lnet/minecraft/world/entity/Entity;" +
    "Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
  private void imaginaryCraft$DamageSource(Holder<DamageType> type, Entity directEntity, Entity causingEntity, Vec3 damageSourcePosition, CallbackInfo ci) {
    DamageSource damageSource = (DamageSource) (Object) this;
    ItemStack itemStack = LcDamageUtil.getDamageItemStack(damageSource);
    ILcLevel iLcLevel;
    ILcDamageTypeEntity iLcDamageTypeEntity;
    IInvincibleTickEntity iInvincibleTickEntity;
    LcDamageType lcDamageType = null;
    LcLevel lcDamageLevel = null;
    int invincibleTick = -1;

    if (itemStack != null) {
      ILcDamageTypeItem colorDamageTypeItem = itemStack.getCapability(ModCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ITEM);
      IInvincibleTickItem invincibleTickItem = itemStack.getCapability(ModCapabilitys.InvincibleTick.INVINCIBLE_TICK_ITEM);

      if (colorDamageTypeItem != null) {
        lcDamageType = colorDamageTypeItem.getLcDamageColorDamageType(itemStack);
      }
      if (invincibleTickItem != null) {
        invincibleTick = invincibleTickItem.getInvincibleTick(itemStack);
      }

      // 等级处理 判断实体是否有护甲如果没有就用实体的等级
      iLcLevel = itemStack.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ITEM);
      if (iLcLevel != null) {
        lcDamageLevel = iLcLevel.getItemLevel();
      }
    }

    Entity entity = directEntity == null ? causingEntity : directEntity;
    if (entity != null) {
      iLcDamageTypeEntity = entity.getCapability(ModCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ENTITY);
      iInvincibleTickEntity = entity.getCapability(ModCapabilitys.InvincibleTick.INVINCIBLE_TICK_ENTITY);

      if (lcDamageType == null && iLcDamageTypeEntity != null) {
        lcDamageType = iLcDamageTypeEntity.getDamageType(entity);
      }

      if (invincibleTick != -1 && iInvincibleTickEntity != null) {
        invincibleTick = iInvincibleTickEntity.getInvincibleTick(entity);
      }

      iLcLevel = entity.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ENTITY);
      if (iLcLevel != null) {
        lcDamageLevel = iLcLevel.getItemLevel();
      }
    }

    if (lcDamageLevel == null) {
      lcDamageLevel = LcLevel.ZAYIN;
    }

    if (invincibleTick == -1) {
      invincibleTick = 20;
    }

    this.imaginaryCraft$lcDamageLevel = lcDamageLevel;
    this.imaginaryCraft$invincibleTick = invincibleTick;
    this.imaginaryCraft$lcDamageTypeType = lcDamageType == null ?
      LcDamageType.byDamageType(type) : lcDamageType;
  }

  @Unique
  @Nullable
  public LcDamageType iSingularityLib$getLcDamageType() {
    return imaginaryCraft$lcDamageTypeType;
  }

  @Unique
  @Nullable
  public LcLevel iSingularityLib$getLcDamageLevel() {
    return imaginaryCraft$lcDamageLevel;
  }

  @Unique
  public int iSingularityLib$getInvincibleTick() {
    return imaginaryCraft$invincibleTick;
  }

  @Unique
  public void iSingularityLib$setLcDamageType(LcDamageType type) {
    this.imaginaryCraft$lcDamageTypeType = type;
  }

  @Unique
  public void iSingularityLib$setDamageLevel(LcLevel level) {
    this.imaginaryCraft$lcDamageLevel = level;
  }

  @Unique
  public void iSingularityLib$setInvincibleTick(int tick) {
    this.imaginaryCraft$invincibleTick = tick;
  }
}
