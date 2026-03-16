package ctn.imaginarycraft.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec2i;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.damagesource.EpicFightDamageSources;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public final class LevelUtil {
  public static void playCrashAttackSoundHurt(@Nullable LivingEntity caster, Level level, Vec3 center, double radius, float maxDamage, boolean isBeatBack, Predicate<Entity> hurtFilter, Predicate<Entity> filter) {
    if (radius <= 0) {
      return;
    }

    int xFrom = (int) Math.floor(center.x - radius);
    int xTo = (int) Math.ceil(center.x + radius);
    int zFrom = (int) Math.floor(center.z - radius);
    int zTo = (int) Math.ceil(center.z + radius);
    Set<Entity> entityBeingHit = new HashSet<>();

    for (int k = zFrom; k <= zTo; k++) {
      for (int l = xFrom; l <= xTo; l++) {
        Vec3 blockCenter = new Vec3(l + 0.5D, center.y, k + 0.5D);

        if (blockCenter.subtract(center).horizontalDistance() > radius) {
          continue;
        }

        Vec3 direction = blockCenter.subtract(center).normalize();
        entityBeingHit.addAll(spreadShockwave(level, center, direction, radius, l, k, filter));
      }
    }

    if (level.isClientSide) {
      return;
    }

    for (Entity entity : entityBeingHit) {
      if (caster == null) {
        continue;
      }

      Vec3 position = entity.position();
      double distance = position.distanceTo(center);
      double damageRatio = 1.0D - (distance / radius);
      float damage = (float) Math.max(0, maxDamage * damageRatio);

      if (hurtFilter.test(entity)) {
        entity.hurt(EpicFightDamageSources
            .shockwave(caster)
            .setAnimation(Animations.EMPTY_ANIMATION)
            .setInitialPosition(center)
            .addRuntimeTag(EpicFightDamageTypeTags.FINISHER)
            .addRuntimeTag(DamageTypeTags.IS_EXPLOSION)
            .setStunType(StunType.KNOCKDOWN),
          damage);
      }

      if (isBeatBack) {
        Vec3 normalize = center.subtract(position).normalize().reverse();
        double v1 = damageRatio * 2.1;
        if (entity instanceof LivingEntity livingEntity) {
          v1 *= Math.min(((RangedAttribute) Attributes.KNOCKBACK_RESISTANCE.value()).getMaxValue() - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1);
        }
        entity.setDeltaMovement(entity.getDeltaMovement().add(normalize.x * v1, damageRatio * 1.3, normalize.z * v1));
      }
    }
  }

  public static Set<Entity> spreadShockwave(Level level, Vec3 center, Vec3 direction, double length, int edgeX, int edgeZ, Predicate<Entity> filter) {
    Set<Entity> entityBeingHit = new HashSet<>();

    if (direction.lengthSqr() == 0) {
      return entityBeingHit;
    }

    Vec3 edgeOfShockwave = center.add(direction.normalize().scale((float) length));
    int xFrom = (int) Math.min(Math.floor(center.x), edgeX);
    int xTo = (int) Math.max(Math.floor(center.x), edgeX);
    int zFrom = (int) Math.min(Math.floor(center.z), edgeZ);
    int zTo = (int) Math.max(Math.floor(center.z), edgeZ);

    List<Entity> entitiesInArea = level.isClientSide ? null :
      level.getEntities(null, new AABB(xFrom, center.y - length, zFrom, xTo, center.y + length, zTo))
        .stream().filter(filter).toList();

    for (int k = zFrom; k <= zTo; k++) {
      for (int l = xFrom; l <= xTo; l++) {
        Vec2i blockCoord = new Vec2i(l, k);

        if (!isBlockOverlapLine(blockCoord, center, edgeOfShockwave)) {
          continue;
        }

        BlockPos bp = new BlockPos.MutableBlockPos(blockCoord.x, (int) center.y, blockCoord.y);

        Vec3 blockCenter = new Vec3(bp.getX() + 0.5D, bp.getY(), bp.getZ() + 0.5D);
        double distance = blockCenter.subtract(center).horizontalDistance();

        if (length < distance) {
          continue;
        }

        BlockState bs = level.getBlockState(bp);
        BlockPos aboveBp = bp.above();
        BlockState aboveState = level.getBlockState(aboveBp);

        if (yesman.epicfight.api.utils.LevelUtil.canTransferShockWave(level, aboveBp, aboveState)) {
          BlockPos aboveTwoBp = aboveBp.above();
          BlockState aboveTwoState = level.getBlockState(aboveTwoBp);

          if (!yesman.epicfight.api.utils.LevelUtil.canTransferShockWave(level, aboveTwoBp, aboveTwoState)) {
            bp = aboveBp;
            bs = aboveState;
          }
        }

        if (!yesman.epicfight.api.utils.LevelUtil.canTransferShockWave(level, bp, bs)) {
          BlockPos belowBp = bp.below();
          BlockState belowState = level.getBlockState(belowBp);

          if (yesman.epicfight.api.utils.LevelUtil.canTransferShockWave(level, belowBp, belowState)) {
            bp = belowBp;
            bs = belowState;
          }
        }

        if (level.isClientSide) {
          continue;
        }

        for (Entity entity : entitiesInArea) {
          if (bp.getX() != entity.getBlockX()) {
            continue;
          }

          if (bp.getZ() != entity.getBlockZ()) {
            continue;
          }

          double entityY = entity.getY();
          if (bp.getY() + 1 < entityY || bp.getY() > entityY) {
            continue;
          }

          entityBeingHit.add(entity);
        }
      }
    }

    return entityBeingHit;
  }

  private static boolean isBlockOverlapLine(Vec2i vec2, Vec3 from, Vec3 to) {
    return isLinesCross(vec2.x, vec2.y, vec2.x + 1, vec2.y, from.x, from.z, to.x, to.z)
      || isLinesCross(vec2.x, vec2.y, vec2.x, vec2.y + 1, from.x, from.z, to.x, to.z)
      || isLinesCross(vec2.x + 1, vec2.y, vec2.x + 1, vec2.y + 1, from.x, from.z, to.x, to.z)
      || isLinesCross(vec2.x, vec2.y + 1, vec2.x + 1, vec2.y + 1, from.x, from.z, to.x, to.z);
  }

  private static boolean isLinesCross(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
    double v1 = (x2 - x1) * (y4 - y3) - (x4 - x3) * (y2 - y1);
    double u = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / v1;
    double v = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / v1;

    return 0 < u && u < 1 && 0 < v && v < 1;
  }
}
