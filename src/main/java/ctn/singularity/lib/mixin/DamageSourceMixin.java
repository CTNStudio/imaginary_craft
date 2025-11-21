package ctn.singularity.lib.mixin;

import ctn.singularity.lib.api.lobotomycorporation.LcDamage;
import ctn.singularity.lib.api.lobotomycorporation.LcLevel;
import ctn.singularity.lib.api.lobotomycorporation.util.LcDamageUtil;
import ctn.singularity.lib.capability.ILcLevel;
import ctn.singularity.lib.capability.entity.IInvincibleTickEntity;
import ctn.singularity.lib.capability.entity.ILcDamageTypeEntity;
import ctn.singularity.lib.capability.item.IInvincibleTickItem;
import ctn.singularity.lib.capability.item.ILcDamageTypeItem;
import ctn.singularity.lib.init.LibCapabilitys;
import ctn.singularity.lib.mixinextend.IModDamageSource;
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
@Implements(@Interface(iface = IModDamageSource.class, prefix = "iSingularityLib$"))
public abstract class DamageSourceMixin implements IModDamageSource {
  @Unique
  @Nullable
  private LcDamage singularityLib$ColorType;

  @Unique
  @Nullable
  private LcLevel singularityLib$damageLevel;

  @Unique
  private int singularityLib$invincibleTick = -1;

  @Inject(method = "<init>(Lnet/minecraft/core/Holder;" +
    "Lnet/minecraft/world/entity/Entity;" +
    "Lnet/minecraft/world/entity/Entity;" +
    "Lnet/minecraft/world/phys/Vec3;)V", at = @At("RETURN"))
  private void singularityLib$DamageSource(Holder<DamageType> type, Entity directEntity, Entity causingEntity, Vec3 damageSourcePosition, CallbackInfo ci) {
    DamageSource damageSource = (DamageSource) (Object) this;
    ItemStack itemStack = LcDamageUtil.getDamageItemStack(damageSource);
    ILcLevel level;
    if (itemStack != null) {
      ILcDamageTypeItem colorDamageTypeItem = itemStack.getCapability(LibCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ITEM);
      IInvincibleTickItem invincibleTickItem = itemStack.getCapability(LibCapabilitys.InvincibleTick.INVINCIBLE_TICK_ITEM);
      level = itemStack.getCapability(LibCapabilitys.LcLevel.LC_LEVEL_ITEM);
      if (colorDamageTypeItem != null) {
        this.singularityLib$ColorType = colorDamageTypeItem.getColorDamageType(itemStack);
      }
      if (invincibleTickItem != null) {
        this.singularityLib$invincibleTick = invincibleTickItem.getInvincibleTick(itemStack);
      }
      if (level != null) {
        this.singularityLib$damageLevel = level.getItemLevel();
      }
    }

    singularityLib$getEntityAttribute(directEntity);
    singularityLib$getEntityAttribute(causingEntity);

    this.singularityLib$ColorType = LcDamageUtil.getColorDamageType(this.singularityLib$ColorType, type);

    if (this.singularityLib$damageLevel == null) {
      this.singularityLib$damageLevel = LcLevel.ZAYIN;
    }

    if (this.singularityLib$invincibleTick == -1) {
      this.singularityLib$invincibleTick = 20;
    }
  }

  @Unique
  private void singularityLib$getEntityAttribute(Entity entity) {
    ILcDamageTypeEntity colorDamageTypeEntity;
    IInvincibleTickEntity invincibleTickEntity;
    ILcLevel level;
    if (entity == null) {
      return;
    }

    colorDamageTypeEntity = entity.getCapability(LibCapabilitys.LcDamageType.LC_DAMAGE_TYPE_ENTITY);
    invincibleTickEntity = entity.getCapability(LibCapabilitys.InvincibleTick.INVINCIBLE_TICK_ENTITY);

    if (singularityLib$ColorType == null) {
      if (colorDamageTypeEntity != null) {
        singularityLib$ColorType = colorDamageTypeEntity.getDamageType(entity);
      }
    }

    if (singularityLib$invincibleTick != -1 && invincibleTickEntity != null) {
      singularityLib$invincibleTick = invincibleTickEntity.getInvincibleTick(entity);
    }

    level = entity.getCapability(LibCapabilitys.LcLevel.LC_LEVEL_ENTITY);
    if (level != null) {
      singularityLib$damageLevel = level.getItemLevel();
    }
  }

  @Unique
  @Nullable
  public LcDamage iSingularityLib$getLcDamage() {
    return singularityLib$ColorType;
  }

  @Unique
  @Nullable
  public LcLevel iSingularityLib$getLcDamageLevel() {
    return singularityLib$damageLevel;
  }

  @Unique
  public int iSingularityLib$getInvincibleTick() {
    return singularityLib$invincibleTick;
  }

  @Unique
  public void iSingularityLib$setLcDamage(LcDamage type) {
    this.singularityLib$ColorType = type;
  }

  @Unique
  public void iSingularityLib$setDamageLevel(LcLevel level) {
    this.singularityLib$damageLevel = level;
  }

  @Unique
  public void iSingularityLib$setInvincibleTick(int tick) {
    this.singularityLib$invincibleTick = tick;
  }
}
