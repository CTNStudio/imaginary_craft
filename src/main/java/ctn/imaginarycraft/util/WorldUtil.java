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

public final class WorldUtil {
	/**
	 * 播放撞击攻击音效并对范围内的实体造成伤害和击退效果
	 * <p>
	 * 该方法以中心点为圆心，在指定半径范围内搜索受冲击波影响的实体，
	 * 根据距离计算伤害值（距离越近伤害越高），并可选地施加击退效果。
	 * 伤害类型被标记为冲击波、终结技和爆炸类型。
	 *
	 * @param caster     施法者实体，用于创建伤害源，可为null
	 * @param level      游戏世界实例
	 * @param center     撞击的中心位置
	 * @param radius     影响范围的半径
	 * @param maxDamage  最大伤害值（在中心点处的伤害）
	 * @param isBeatBack 是否施加击退效果
	 * @param hurtFilter 伤害过滤器，决定哪些实体可以受到伤害
	 * @param filter     实体过滤器，用于筛选需要检测的实体
	 */
  public static void playCrashAttackSoundHurt(@Nullable LivingEntity caster, Level level, Vec3 center, double radius, float maxDamage, boolean isBeatBack, Predicate<Entity> hurtFilter, Predicate<Entity> filter) {
    // 半径无效时直接返回
	  if (radius <= 0) {
      return;
    }

    // 计算圆形搜索区域的边界框
	  int xFrom = (int) Math.floor(center.x - radius);
    int xTo = (int) Math.ceil(center.x + radius);
    int zFrom = (int) Math.floor(center.z - radius);
    int zTo = (int) Math.ceil(center.z + radius);
    Set<Entity> entityBeingHit = new HashSet<>();

    // 遍历边界框内的每个方块，收集受冲击波影响的实体
	  for (int k = zFrom; k <= zTo; k++) {
      for (int l = xFrom; l <= xTo; l++) {
        Vec3 blockCenter = new Vec3(l + 0.5D, center.y, k + 0.5D);

	      // 检查方块是否在圆形范围内
	      if (blockCenter.subtract(center).horizontalDistance() > radius) {
          continue;
        }

	      // 计算方向并传播冲击波
	      Vec3 direction = blockCenter.subtract(center).normalize();
        entityBeingHit.addAll(spreadShockwave(level, center, direction, radius, l, k, filter));
      }
    }

    // 仅在服务端执行伤害和击退逻辑
	  if (level.isClientSide) {
      return;
    }

    // 对每个受影响的实体应用伤害和击退效果
	  for (Entity entity : entityBeingHit) {
      if (caster == null) {
        continue;
      }

      // 根据距离计算伤害比例和实际伤害值
		  Vec3 position = entity.position();
      double distance = position.distanceTo(center);
      double damageRatio = 1.0D - (distance / radius);
      float damage = (float) Math.max(0, maxDamage * damageRatio);

      // 应用伤害效果
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

      // 应用击退效果
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

  /**
   * 在指定区域内传播冲击波，检测并收集受影响的实体
   * <p>
   * 该方法从中心点沿指定方向传播冲击波，通过线段与方块的相交检测确定冲击波路径，
   * 然后检查路径上的方块位置是否存在实体。冲击波会尝试调整到可传导的方块高度。
   *
   * @param level     游戏世界实例
   * @param center    冲击波的中心起始位置
   * @param direction 冲击波的传播方向向量
   * @param length    冲击波的传播长度
   * @param edgeX     搜索区域的X轴边界坐标
   * @param edgeZ     搜索区域的Z轴边界坐标
   * @param filter    实体过滤器，用于筛选需要检测的实体
   * @return 包含所有被冲击波击中的实体的集合，如果在客户端侧则返回空集合
   */
  public static Set<Entity> spreadShockwave(Level level, Vec3 center, Vec3 direction, double length, int edgeX, int edgeZ, Predicate<Entity> filter) {
    Set<Entity> entityBeingHit = new HashSet<>();

    // 如果方向向量为零向量，直接返回空集合
	  if (direction.lengthSqr() == 0) {
      return entityBeingHit;
    }

    // 计算冲击波的终点位置
	  Vec3 edgeOfShockwave = center.add(direction.normalize().scale((float) length));
    // 计算搜索区域的边界范围
	  int xFrom = (int) Math.min(Math.floor(center.x), edgeX);
    int xTo = (int) Math.max(Math.floor(center.x), edgeX);
    int zFrom = (int) Math.min(Math.floor(center.z), edgeZ);
    int zTo = (int) Math.max(Math.floor(center.z), edgeZ);

    // 获取搜索区域内的所有实体（仅服务端）
	  List<Entity> entitiesInArea = level.isClientSide ? null :
      level.getEntities(null, new AABB(xFrom, center.y - length, zFrom, xTo, center.y + length, zTo))
        .stream().filter(filter).toList();

    // 遍历搜索区域内的每个方块坐标
	  for (int k = zFrom; k <= zTo; k++) {
      for (int l = xFrom; l <= xTo; l++) {
        Vec2i blockCoord = new Vec2i(l, k);

	      // 检查方块是否与冲击波路径线段相交
	      if (!isBlockOverlapLine(blockCoord, center, edgeOfShockwave)) {
          continue;
        }

        BlockPos bp = new BlockPos.MutableBlockPos(blockCoord.x, (int) center.y, blockCoord.y);

	      // 检查方块距离是否在冲击波范围内
	      Vec3 blockCenter = new Vec3(bp.getX() + 0.5D, bp.getY(), bp.getZ() + 0.5D);
        double distance = blockCenter.subtract(center).horizontalDistance();

        if (length < distance) {
          continue;
        }

        BlockState bs = level.getBlockState(bp);
        BlockPos aboveBp = bp.above();
        BlockState aboveState = level.getBlockState(aboveBp);

	      // 尝试向上调整方块位置到可传导冲击波的高度
	      if (yesman.epicfight.api.utils.LevelUtil.canTransferShockWave(level, aboveBp, aboveState)) {
          BlockPos aboveTwoBp = aboveBp.above();
          BlockState aboveTwoState = level.getBlockState(aboveTwoBp);

          if (!yesman.epicfight.api.utils.LevelUtil.canTransferShockWave(level, aboveTwoBp, aboveTwoState)) {
            bp = aboveBp;
            bs = aboveState;
          }
        }

	      // 如果当前位置不可传导，尝试向下调整
	      if (!yesman.epicfight.api.utils.LevelUtil.canTransferShockWave(level, bp, bs)) {
          BlockPos belowBp = bp.below();
          BlockState belowState = level.getBlockState(belowBp);

          if (yesman.epicfight.api.utils.LevelUtil.canTransferShockWave(level, belowBp, belowState)) {
            bp = belowBp;
            bs = belowState;
          }
        }

	      // 客户端跳过实体检测
	      if (level.isClientSide) {
          continue;
        }

	      // 检查该方块位置上是否有实体存在
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
