package ctn.imaginarycraft.init.world;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * 伤害来源
 */
public final class ModDamageSources {

  @Contract("_ -> new")
  public static @NotNull DamageSource spiritDamage(Entity causer) {
    return createDamage(ModDamageTypes.SPIRIT, causer);
  }

  @Contract("_ -> new")
  public static @NotNull DamageSource erosionDamage(Entity causer) {
    return createDamage(ModDamageTypes.EROSION, causer);
  }

  @Contract("_ -> new")
  public static @NotNull DamageSource theSoulDamage(Entity causer) {
    return createDamage(ModDamageTypes.THE_SOUL, causer);
  }

  @Contract("_ -> new")
  public static @NotNull DamageSource physicsDamage(Entity causer) {
    return createDamage(ModDamageTypes.PHYSICS, causer);
  }

  @Contract("_ -> new")
  public static @NotNull DamageSource abnosDamage(Entity causer) {
    return createDamage(ModDamageTypes.ABNOS, causer);
  }

  @Contract("_ -> new")
  public static @NotNull DamageSource egoDamage(Entity causer) {
    return createDamage(ModDamageTypes.EGO, causer);
  }

  public static @NotNull DamageSource createDamage(ResourceKey<DamageType> damageTypes, Entity causingEntity) {
    return createDamage(damageTypes, causingEntity, causingEntity, null);
  }

  public static @NotNull DamageSource createDamage(ResourceKey<DamageType> damageTypes, @Nullable Entity directEntity, @NotNull Entity causingEntity) {
    return createDamage(damageTypes, directEntity, causingEntity, null);
  }

  public static @NotNull DamageSource createDamage(ResourceKey<DamageType> damageTypes, @Nullable Entity directEntity, @NotNull Entity causingEntity, Vec3 damageSourcePosition) {
    return createDamage(causingEntity.level().registryAccess(), damageTypes, directEntity, causingEntity, damageSourcePosition);
  }

  public static @NotNull DamageSource createDamage(RegistryAccess registryAccess, ResourceKey<DamageType> damageTypes, @Nullable Entity directEntity, @Nullable Entity causingEntity, Vec3 damageSourcePosition) {
    return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageTypes), directEntity, causingEntity, damageSourcePosition);
  }
}
