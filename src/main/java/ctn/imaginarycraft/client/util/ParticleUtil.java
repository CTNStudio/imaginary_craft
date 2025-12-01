package ctn.imaginarycraft.client.util;

import ctn.ctnapi.util.TextUtil;
import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import ctn.imaginarycraft.client.particle.TextParticleOptions;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class ParticleUtil {

  public static void createTextParticles(
    ServerLevel world,
    Component component,
    @Nullable Holder<DamageType> damageType,
    @Nullable LcDamageType lcDamageType,
    int fontColor,
    int strokeColor,
    boolean isRationality,
    boolean isHeal,
    boolean isTexture,
    double x,
    double y,
    double z,
    double xOffset, double yOffset, double zOffset
  ) {
    TextParticleOptions options = new TextParticleOptions(component, Optional.ofNullable(damageType), Optional.ofNullable(lcDamageType), fontColor, strokeColor, isRationality, isHeal, isTexture);
    world.sendParticles(options, x, y, z, 1, xOffset, yOffset, zOffset, 0);
  }

  public static void randomColorTextParticles(
    ServerLevel world,
    Component component,
    @Nullable Holder<DamageType> damageType,
    @Nullable LcDamageType lcDamageType,
    int fontColor,
    int strokeColor,
    boolean isRationality,
    boolean isHeal,
    boolean isTexture,
    double x,
    double y,
    double z
  ) {
    createTextParticles(world, component, damageType, lcDamageType, fontColor, strokeColor, isRationality, isHeal, isTexture,
      x, y, z, 0.1, 0.1, 0.1);
  }

  public static void randomColorTextParticles(
    ServerLevel world,
    Component component,
    @Nullable Holder<DamageType> damageType,
    @Nullable LcDamageType lcDamageType,
    boolean isRationality,
    boolean isHeal,
    boolean isTexture,
    double x,
    double y,
    double z
  ) {
    randomColorTextParticles(world, component, damageType, lcDamageType, -1, -1, isRationality, isHeal, isTexture,
      x, y, z);
  }

  public static void randomColorTextParticles(
    ServerLevel world,
    Component component,
    boolean isRationality,
    boolean isHeal,
    boolean isTexture,
    double x,
    double y,
    double z
  ) {
    randomColorTextParticles(world, component, null, null, -1, -1, isRationality, isHeal, isTexture,
      x, y, z);
  }

  public static void createTextParticles(
    LivingEntity entity,
    Component component,
    @Nullable Holder<DamageType> damageType,
    @Nullable LcDamageType lcDamageType,
    boolean isRationality,
    boolean isHeal
  ) {
    createTextParticles(entity, component, damageType, lcDamageType, -1, -1, isRationality, isHeal, true);
  }

  public static void createTextParticles(LivingEntity entity, Component component, boolean isRationality, boolean isHeal) {
    createTextParticles(entity, component, null, null, 0, 0, isRationality, isHeal, true);
  }

  public static void createTextParticles(LivingEntity entity, float value, boolean isRationality, boolean isHeal) {
    createTextParticles(entity, getText(value, isHeal), null, null, -1, -1, isRationality, isHeal, true);
  }

  public static void createTextParticles(
    LivingEntity entity,
    @Nullable Holder<DamageType> damageType,
    @Nullable LcDamageType lcDamageType,
    float value,
    boolean isRationality,
    boolean isHeal
  ) {
    createTextParticles(entity, getText(value, isHeal), damageType, lcDamageType, -1, -1, isRationality, isHeal, true);
  }

  public static void createTextParticles(
    LivingEntity entity,
    Component component,
    @Nullable Holder<DamageType> damageType,
    @Nullable LcDamageType lcDamageType,
    int fontColor,
    int strokeColor,
    boolean isRationality,
    boolean isHeal,
    boolean isTexture
  ) {
    if (!(entity.level() instanceof ServerLevel serverLevel)) {
      return;
    }
    Vec3 pos = entity.position();
    AABB aabb = entity.getHitbox();
    double xOffset = (aabb.maxX - aabb.minX) / 2;
    double yOffset = (aabb.maxY - aabb.minY) / 2;
    double zOffset = (aabb.maxZ - aabb.minZ) / 2;
    double x = pos.x;
    double y = pos.y + yOffset;
    double z = pos.z;
    createTextParticles(serverLevel, component, damageType, lcDamageType, fontColor, strokeColor, isRationality, isHeal, isTexture,
      x, y, z, xOffset / 2, yOffset / 2, zOffset / 2);
  }

  public static @NotNull MutableComponent getText(float value) {
    return getText(value, value > 0);
  }

  // TODO 新增免疫字段
  // TODO 新增吸收字段
  // TODO 新增无效字段
  public static @NotNull MutableComponent getText(float value, boolean isHeal) {
    return Component.literal((isHeal ? "+%s" : "-%s").formatted(TextUtil.formatNumber(Math.abs(value), 2)));
  }
}
