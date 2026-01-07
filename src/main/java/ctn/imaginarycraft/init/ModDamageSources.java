package ctn.imaginarycraft.init;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * 伤害来源
 */
public final class ModDamageSources extends DamageSources {
  private final DamageSource physics;
  private final DamageSource rationality;
  private final DamageSource erosion;
  private final DamageSource theSoul;
  private final DamageSource abnormalities;
  private final DamageSource ego;

  private final DamageSource melee;
  private final DamageSource remote;

  public ModDamageSources(RegistryAccess registry) {
    super(registry);
    physics = source(ModDamageTypes.PHYSICS);
    rationality = source(ModDamageTypes.SPIRIT);
    erosion = source(ModDamageTypes.EROSION);
    theSoul = source(ModDamageTypes.THE_SOUL);
    abnormalities = source(ModDamageTypes.ABNORMALITIES);
    ego = source(ModDamageTypes.EGO);
    melee = source(ModDamageTypes.MELEE);
    remote = source(ModDamageTypes.REMOTE);
  }

  public static @NotNull DamageSource meleeDamage(Entity causer) {
    return createDamage(ModDamageTypes.MELEE, causer);
  }

  public static @NotNull DamageSource remoteDamage(Entity causer) {
    return createDamage(ModDamageTypes.REMOTE, causer);
  }

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
  public static @NotNull DamageSource abnormalitiesDamage(Entity causer) {
    return createDamage(ModDamageTypes.ABNORMALITIES, causer);
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

  public DamageSource physics() {
    return physics;
  }

  public DamageSource rationality() {
    return rationality;
  }

  public DamageSource erosion() {
    return erosion;
  }

  public DamageSource theSoul() {
    return theSoul;
  }

  public DamageSource abnormalities() {
    return abnormalities;
  }

  public DamageSource melee() {
    return melee;
  }

  public DamageSource remote() {
    return remote;
  }

  public DamageSource ego() {
    return ego;
  }
}
