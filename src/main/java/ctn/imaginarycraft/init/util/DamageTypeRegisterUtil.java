package ctn.imaginarycraft.init.util;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import org.jetbrains.annotations.NotNull;

public abstract class DamageTypeRegisterUtil {
  /**
   * 创建伤害类型
   */
  protected static @NotNull ResourceKey<DamageType> register(final String name) {
    return ResourceKey.create(Registries.DAMAGE_TYPE, ImaginaryCraft.modRl(name));
  }

  protected static Holder.@NotNull Reference<DamageType> register(
    final BootstrapContext<DamageType> context,
    final String name,
    final ResourceKey<DamageType> damageType,
    final DamageScaling damageScaling,
    final float exhaustion,
    final DamageEffects damageEffects,
    final DeathMessageType deathMessageType
  ) {
    return register(context, damageType, new DamageType(name, damageScaling, exhaustion, damageEffects, deathMessageType));
  }

  protected static Holder.@NotNull Reference<DamageType> register(
    final BootstrapContext<DamageType> context,
    final String name,
    final ResourceKey<DamageType> damageType,
    float exhaustion
  ) {
    return register(context, name, damageType, DamageScaling.ALWAYS, exhaustion, DamageEffects.HURT, DeathMessageType.DEFAULT);
  }

  protected static Holder.@NotNull Reference<DamageType> register(
    final @NotNull BootstrapContext<DamageType> context,
    final ResourceKey<DamageType> damageType,
    final DamageType damageType1
  ) {
    return context.register(damageType, damageType1);
  }
}
