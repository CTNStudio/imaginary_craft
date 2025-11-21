package ctn.singularity.lib.client.util;

import ctn.singularity.lib.client.particle.TextParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class ParticleUtil {

  public static void createTextParticles(ServerLevel world,
                                         Component component,
                                         @Nullable ResourceKey<DamageType> damageType,
                                         int fontColor,
                                         int strokeColor,
                                         boolean isRationality,
                                         boolean isHeal,
                                         boolean isTexture,
                                         double x, double y, double z,
                                         double xOffset, double yOffset, double zOffset) {
    var options = new TextParticleOptions(component, Optional.ofNullable(damageType), fontColor, strokeColor, isRationality, isHeal, isTexture);
    world.sendParticles(options, x, y, z, 1, xOffset, yOffset, zOffset, 0);
  }

  public static void createTextParticles(LivingEntity entity,
                                         Component component,
                                         @Nullable ResourceKey<DamageType> damageType,
                                         boolean isRationality,
                                         boolean isHeal) {
    createTextParticles(entity, component, damageType, 0, 0, isRationality, isHeal, true);
  }

  public static void createTextParticles(LivingEntity entity,
                                         Component component,
                                         boolean isRationality,
                                         boolean isHeal) {
    createTextParticles(entity, component, null, 0, 0, isRationality, isHeal, true);
  }

  public static void createTextParticles(LivingEntity entity,
                                         float value,
                                         boolean isRationality,
                                         boolean isHeal) {
    createTextParticles(entity, getText(value, isHeal), null, 0, 0, isRationality, isHeal, true);
  }

  public static void createTextParticles(LivingEntity entity,
                                         @Nullable ResourceKey<DamageType> damageType,
                                         float value,
                                         boolean isRationality,
                                         boolean isHeal) {
    createTextParticles(entity, getText(value, isHeal), damageType, 0, 0, isRationality, isHeal, true);
  }

  public static void createTextParticles(LivingEntity entity,
                                         Component component,
                                         @Nullable ResourceKey<DamageType> damageType,
                                         int fontColor,
                                         int strokeColor,
                                         boolean isRationality,
                                         boolean isHeal,
                                         boolean isTexture) {
    if (!(entity.level() instanceof ServerLevel serverLevel)) {
      return;
    }
    Vec3 pos = entity.position();
    double x = pos.x;
    double y = pos.y + entity.getBbHeight();
    double z = pos.z;
    AABB aabb = entity.getHitbox();
    double xOffset = (aabb.maxX - aabb.minX) / 2;
    double zOffset = (aabb.maxZ - aabb.minZ) / 2;
    createTextParticles(serverLevel, component, damageType, fontColor, strokeColor, isRationality, isHeal, isTexture, x, y, z, xOffset, 0, zOffset);
  }

  public static @NotNull MutableComponent getText(float value) {
    return getText(value, value > 0);
  }

  public static @NotNull MutableComponent getText(float value, boolean isHeal) {
    return Component.literal((isHeal ? "+%.2f" : "-%.2f").formatted(Math.abs(value)));
  }
}
