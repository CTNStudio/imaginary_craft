package ctn.singularity.lib.init.util;

import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.LibDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public abstract class DamageTypeRegisterUtil {

  /**
   * 创建伤害类型
   */
  protected static @NotNull ResourceKey<DamageType> register(final String name) {
    return ResourceKey.create(Registries.DAMAGE_TYPE, LibMain.modRl(name));
  }

  protected static @NotNull DeferredHolder<DamageType, DamageType> register(final String name, final DamageType damageType) {
    return LibDamageTypes.REGISTRY.register(name, () -> damageType);
  }

  protected static Holder.@NotNull Reference<DamageType> register(BootstrapContext<DamageType> context, ResourceKey<DamageType> damageType, DamageScaling damageScaling, float exhaustion, DamageEffects damageEffects, DeathMessageType deathMessageType) {
    return register(context, damageType, new DamageType(damageType.location().getPath(), damageScaling, exhaustion, damageEffects, deathMessageType));
  }

  protected static Holder.@NotNull Reference<DamageType> register(BootstrapContext<DamageType> context, ResourceKey<DamageType> damageType, float exhaustion) {
    return register(context, damageType, DamageScaling.ALWAYS, exhaustion, DamageEffects.HURT, DeathMessageType.DEFAULT);
  }

  protected static Holder.@NotNull Reference<DamageType> register(final BootstrapContext<DamageType> context, final ResourceKey<DamageType> damageType, final DamageType damageType1) {
    return context.register(damageType, damageType1);
  }
}
