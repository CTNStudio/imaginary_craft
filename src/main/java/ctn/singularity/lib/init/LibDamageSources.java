package ctn.singularity.lib.init;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * 伤害来源
 */
public final class LibDamageSources extends DamageSources {
  private final DamageSource physics;
  private final DamageSource rationality;
  private final DamageSource erosion;
  private final DamageSource theSoul;
  private final DamageSource abnos;
  private final DamageSource ego;

  public LibDamageSources(RegistryAccess registry) {
    super(registry);
    physics = source(LibDamageTypes.PHYSICS);
    rationality = source(LibDamageTypes.SPIRIT);
    erosion = source(LibDamageTypes.EROSION);
    theSoul = source(LibDamageTypes.THE_SOUL);
    abnos = source(LibDamageTypes.ABNOS);
    ego = source(LibDamageTypes.EGO);
  }

  public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> physicsDamage() {
    return (attacker, target) -> getDamageSource().apply(attacker, target).physics();
  }

  public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> rationalityDamage() {
    return (attacker, target) -> getDamageSource().apply(attacker, target).rationality();
  }

  public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> erosionDamage() {
    return (attacker, target) -> getDamageSource().apply(attacker, target).erosion();
  }

  public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> theSoulDamage() {
    return (attacker, target) -> getDamageSource().apply(attacker, target).theSoul();
  }

  public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> abnosDamage() {
    return (attacker, target) -> getDamageSource().apply(attacker, target).abnos();
  }

  public static BiFunction<LivingEntity, LivingEntity, ? extends DamageSource> egoDamage() {
    return (attacker, target) -> getDamageSource().apply(attacker, target).ego();
  }

  public static DamageSource rationalityDamage(Entity causer) {
    return createDamage(causer, LibDamageTypes.SPIRIT);
  }

  public static DamageSource erosionDamage(Entity causer) {
    return createDamage(causer, LibDamageTypes.EROSION);
  }

  public static DamageSource theSoulDamage(Entity causer) {
    return createDamage(causer, LibDamageTypes.THE_SOUL);
  }

  public static DamageSource physicsDamage(Entity causer) {
    return createDamage(causer, LibDamageTypes.PHYSICS);
  }

  public static DamageSource abnosDamage(Entity causer) {
    return createDamage(causer, LibDamageTypes.ABNOS);
  }

  public static DamageSource egoDamage(Entity causer) {
    return createDamage(causer, LibDamageTypes.EGO);
  }

  private static @NotNull DamageSource createDamage(Entity causer, ResourceKey<DamageType> damageTypes) {
    return new DamageSource(causer.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageTypes), causer);
  }

  public static BiFunction<LivingEntity, LivingEntity, ? extends LibDamageSources> getDamageSource() {
    return (attacker, target) -> new LibDamageSources(attacker.level().registryAccess());
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

  public DamageSource abnos() {
    return abnos;
  }

  public DamageSource ego() {
    return ego;
  }
}
