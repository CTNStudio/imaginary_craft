package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.IAbnormalitiesEntity;
import ctn.imaginarycraft.client.particle.magicbullet.MagicBulletMagicCircleParticle;
import ctn.imaginarycraft.init.ModSoundEvents;
import ctn.imaginarycraft.init.world.ModAttributes;
import ctn.imaginarycraft.init.world.ModDamageSources;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.Vec2i;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.world.damagesource.EpicFightDamageSources;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

// TODO 技能或大招剩余时间要持久化
// TODO 需要免疫中毒，细雪，火焰，漂浮

/**
 * 英文编号：ordeals--violet noon
 * <p>
 * 中文编号：考验--紫罗兰色正午
 * <p>
 * 英文名：Grant Us Love
 * <p>
 * 中文名： 请给我们爱
 * <p>
 * 2025/12/22 尘昨喧
 */
public class GrantUsLove extends Mob implements IAbnormalitiesEntity, GeoEntity {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final List<LivingEntity> recentAttackers = new ArrayList<>(); // 最近攻击者列表
  private final Map<LivingEntity, Integer> lastAttackTimeMap = new HashMap<>(); // 记录攻击时间
  private LivingEntity primaryTarget = null; // 主要目标（优先玩家）
  private int attackCooldown = Config.NORMAL_ATTACK_COOLDOWN;
  private int crashAttackCooldown = 0;
  private int stateDuration = 0; // 当前状态持续时间
  private boolean crashAttackReady = false; //一个状态，大招冷却结束但未释放
  private int crashPortalOpeningTime = 1;
  private Vec3 crashPortalPosition = null;
  private int idleSoundCooldown = 0;

  public GrantUsLove(EntityType<? extends Mob> entityType, Level level) {
    super(entityType, level);
  }

  public static AttributeSupplier.Builder createAttributes() {
    return createMobAttributes()
      .add(Attributes.KNOCKBACK_RESISTANCE, 1)
      .add(Attributes.MAX_HEALTH, 350.0)
      .add(Attributes.ATTACK_DAMAGE, 1.0)
      .add(Attributes.MOVEMENT_SPEED, 0)
      .add(Attributes.ATTACK_KNOCKBACK, 0)
      .add(Attributes.GRAVITY, 0.08)
      .add(ModAttributes.PHYSICS_VULNERABLE, 0.8)
      .add(ModAttributes.SPIRIT_VULNERABLE, 2.0)
      .add(ModAttributes.EROSION_VULNERABLE, 0.8)
      .add(ModAttributes.THE_SOUL_VULNERABLE, 1.0);
  }

  public static Set<Entity> spreadShockwave(Level level, Vec3 center, Vec3 direction, double length, int edgeX, int edgeZ) {
    Set<Entity> entityBeingHit = new HashSet<>();

    if (direction.lengthSqr() == 0) {
      return entityBeingHit;
    }

    Vec3 edgeOfShockwave = center.add(direction.normalize().scale((float) length));
    int xFrom = (int) Math.min(Math.floor(center.x), edgeX);
    int xTo = (int) Math.max(Math.floor(center.x), edgeX);
    int zFrom = (int) Math.min(Math.floor(center.z), edgeZ);
    int zTo = (int) Math.max(Math.floor(center.z), edgeZ);

    List<Entity> entitiesInArea = level.isClientSide ? null : level.getEntities(null, new AABB(xFrom, center.y - length, zFrom, xTo, center.y + length, zTo));

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

        if (LevelUtil.canTransferShockWave(level, aboveBp, aboveState)) {
          BlockPos aboveTwoBp = aboveBp.above();
          BlockState aboveTwoState = level.getBlockState(aboveTwoBp);

          if (!LevelUtil.canTransferShockWave(level, aboveTwoBp, aboveTwoState)) {
            bp = aboveBp;
            bs = aboveState;
          }
        }

        if (!LevelUtil.canTransferShockWave(level, bp, bs)) {
          BlockPos belowBp = bp.below();
          BlockState belowState = level.getBlockState(belowBp);

          if (LevelUtil.canTransferShockWave(level, belowBp, belowState)) {
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

  private float rot(float rot) {
    return (float) Math.floor(rot / 90.0) * 90.0F;
  }

  @Override
  public float getYRot() {
    return Math.round(super.getYRot() / 90.0F) * 90.0F;
  }

  @Override
  public void setYRot(float xRot) {
    super.setYRot(rot(xRot));
  }

  @Override
  public void doWhenSpawnByEggs() {
    this.crashAttackReady = false;
    this.crashAttackCooldown = Config.CRASH_ATTACK_COOLDOWN;
    this.crashAttackCooldown = 20 * 2;
    this.crashPortalOpeningTime = Config.CRASH_PORTAL_OPENING_TIME;
  }

  //普攻
  private void executeAoeAttack() {
    AABB aoeBox = this.getBoundingBox()
      .inflate(Config.NORMAL_ATTACK_AOE_RADIUS,
        Config.NORMAL_ATTACK_AOE_HEIGHT / 2,
        Config.NORMAL_ATTACK_AOE_RADIUS)
      .move(0, Config.NORMAL_ATTACK_AOE_HEIGHT / 4, 0);

    List<LivingEntity> targets = this.level().getEntitiesOfClass(
      LivingEntity.class,
      aoeBox,
      entity -> entity != this && entity.isAlive() && this.isTarget(entity)
    );

    DamageSource damageSource = ModDamageSources.erosionDamage(this);//黑伤

    for (LivingEntity target : targets) {
      if (this.applyAttackToTarget(target, damageSource,
        Config.NORMAL_ATTACK_DAMAGE, Config.NORMAL_ATTACK_KNOCKBACK_STRENGTH)) {//命中时播放
        // 视觉效果
        this.showAttackEffect();

        // 音效
        this.playAttackSound();
      }
    }
    if (!targets.isEmpty()) {
      this.playAttackSound();
    } else if (idleSoundCooldown <= 0) {
      this.playIdleSound();
      idleSoundCooldown = 2;
    } else {
      idleSoundCooldown--;
    }
  }

  //大招
  private void executeCrashAttack() {
    final float crashAttackAoeRadius = 10.0F;
    final float crashAttackDamage = 200.0F;
    this.spawnGroundParticlesInAABB(crashAttackAoeRadius + 1);
    this.playCrashAttackSound();
    hurt(this, this.level(), position().subtract(0, 1, 0),
      crashAttackAoeRadius + 1, crashAttackDamage, this::canAttackTarget);
  }

  private void hurt(@Nullable LivingEntity caster, Level level, Vec3 center, double radius, float maxDamage, Predicate<Entity> filter) {
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
        entityBeingHit.addAll(spreadShockwave(level, center, direction, radius, l, k));
      }
    }

    if (level.isClientSide) {
      return;
    }

    for (Entity entity : entityBeingHit) {
      if (caster == null || entity.is(caster) || !filter.test(entity)) {
        continue;
      }

      double distance = entity.position().distanceTo(center);
      double damageRatio = 1.0D - (distance / radius);
      float damage = (float) Math.max(0, maxDamage * damageRatio);

      entity.hurt(EpicFightDamageSources
          .shockwave(caster)
          .setAnimation(Animations.EMPTY_ANIMATION)
          .setInitialPosition(center)
          .addRuntimeTag(EpicFightDamageTypeTags.FINISHER)
          .addRuntimeTag(DamageTypeTags.IS_EXPLOSION)
          .setStunType(StunType.KNOCKDOWN),
        damage);
    }
  }

  private boolean applyAttackToTarget(LivingEntity target, DamageSource damageSource, float damageAmount, double strength) {
    boolean hurt = target.hurt(damageSource, damageAmount);
    if (hurt) {
      this.applyKnockback(target, strength);
    }
    return hurt;
  }

  private void applyKnockback(LivingEntity target, double strength) {
    // 计算从自己指向目标的方向
    double dx = target.getX() - this.getX();
    double dz = target.getZ() - this.getZ();
    double distance = Math.sqrt(dx * dx + dz * dz);

    if (distance > 0) {
      target.knockback(strength, dx / distance, dz / distance);
    }
  }

  @Override
  protected void playAttackSound() {
    playSound(ModSoundEvents.VIOLET_NOON_ATK.value(), 1.0F, 1.0F);
  }

  private void showAttackEffect() {//TODO: 播放特效
  }

  private void playCrashAttackSound() {
    playSound(ModSoundEvents.VIOLET_NOON_DOWN.value(), 2.0F, 1.0F);
  }

  private void playIdleSound() {
    playSound(ModSoundEvents.VIOLET_NOON_idle.value(), 1.0F, 1.0F);
  }

  /**
   * 简单的地面尘土粒子效果
   */
  private void spawnGroundParticlesInAABB(double radius) {
    if (this.level().isClientSide) return;
    Vec3 position = position();
    ServerLevel level = (ServerLevel) this.level();
    LevelUtil.circleSlamFracture(this, level, position.subtract(0, 1, 0), radius, false, false, true);
    EpicFightParticles.AIR_BURST.get().spawnParticleWithArgument(level,
      position.x, position.y, position.z,
      0, 0, radius * 2);
  }

  private boolean isTarget(Entity entity) {
    // 不攻击自己
    if (entity == this) return false;

    // 对玩家的特殊处理
    if (entity instanceof Player player) {
      return !player.isCreative() && !player.isSpectator();
    }
    // 不攻击紫罗兰系列考验，由于没几个，我直接用硬编码
    // TODO 后续采用tag的形式处理
    return !(entity instanceof GrantUsLove);
  }

  private boolean canAttackTarget(Entity entity) {
    return entity.isAlive() && entity.isAttackable() && this.isTarget(entity);
  }

  @Override
  public void tick() {
    super.tick();

    if (this.level().isClientSide) {
      return;
    }

    stateDuration++;
    if (!this.crashAttackReady) {//无大招时
      if (crashAttackCooldown <= 0) {
        crashAttackCooldown = Config.CRASH_ATTACK_COOLDOWN;
        crashAttackCooldown = 20 * 3;
        this.crashAttackReady = true;
      } else {
        crashAttackCooldown--;
        if (attackCooldown > 0) {
          attackCooldown--;
        } else {
          this.executeAoeAttack();
          attackCooldown = Config.NORMAL_ATTACK_COOLDOWN;
        }
      }
    } else if (this.crashPortalOpeningTime > 0) {
      if (this.crashPortalOpeningTime == Config.CRASH_PORTAL_OPENING_TIME) {//砸击开始时,锁定传送位置
        this.crashPortalPosition = Objects.requireNonNullElse(this.primaryTarget, this).position()
          .add(0, 20, 0);
        this.createPortal();
      } else if (this.crashPortalOpeningTime == 1) {
        if (this.crashPortalPosition == null) {
          crashPortalPosition = this.position().add(0, 20, 0);
        }
        this.setPos(crashPortalPosition.x, crashPortalPosition.y, crashPortalPosition.z);
      }
      this.crashPortalOpeningTime--;
    } else if (this.onGround()) {//砸到地上时伤害
      this.crashAttackReady = false;
      this.crashPortalOpeningTime = Config.CRASH_PORTAL_OPENING_TIME;
      this.executeCrashAttack();
    }

    // 检查目标是否脱离锁定
    this.checkTargetOutOfRange();

    // 清理过期攻击者（每5秒一次）
    if (this.tickCount % 100 == 0) {
      this.cleanupAttackers();
    }

    // 状态超时检查
    this.checkStateTimeout();
  }

  private void createPortal() {
    if (!(this.level() instanceof ServerLevel serverLevel)) {
      return;
    }

    Vec3 pos = this.crashPortalPosition;
    serverLevel.sendParticles(new MagicBulletMagicCircleParticle.Builder(-90, 0)
      .radius(5.0f)
      .particleLifeTime(110)
      .buildOptions(0), pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
  }

  @Override
  protected void actuallyHurt(DamageSource source, float damageAmount) {//用于给砸人锁定目标
    super.actuallyHurt(source, damageAmount);
    if (!this.level().isClientSide && source.getEntity() instanceof LivingEntity attacker && attacker.isAttackable()) {
      // 1. 记录攻击者
      this.recordAttacker(attacker);

      // 2. 更新或设置主要目标
      this.updatePrimaryTarget();

      // 3. 重置状态持续时间
      this.stateDuration = 0;
    }
  }

  private void updatePrimaryTarget() {
    if (recentAttackers.isEmpty()) {
      primaryTarget = null;
      return;
    }

    // 如果已经有主要目标且目标还活着且在范围内，保持目标不变
    if (primaryTarget != null && primaryTarget.isAlive() &&
      this.distanceTo(primaryTarget) <= Config.TARGETED_ATTACK_RADIUS) {
      return;
    }

    // 否则重新计算最佳目标
    LivingEntity bestTarget = null;
    float bestScore = -1;

    for (LivingEntity attacker : recentAttackers) {
      if (!attacker.isAlive() || !this.isTarget(attacker)) continue;

      float score = this.calculateTargetScore(attacker);

      if (score > bestScore) {
        bestScore = score;
        bestTarget = attacker;
      }
    }

    primaryTarget = bestTarget;
  }

  /**
   * 计算目标得分（即追踪权重）
   *
   * @param entity 目标实体
   * @return 得分
   */
  private float calculateTargetScore(LivingEntity entity) {
    float score = 0;

    // 1. 玩家获得最高基础分
    if (entity instanceof Player) {
      score += Config.PLAYER_TARGET_PRIORITY;

      // 创造模式玩家分数降低
      if (((Player) entity).isCreative()) {
        score -= 5;
      }
    }
    // 2. 距离越近分数越高（0-5分）
    float distance = this.distanceTo(entity);
    score += Math.max(0, 5 - distance / 10);

    // 3. 最近攻击时间越近分数越高（0-3分）
    Integer lastAttackTime = lastAttackTimeMap.get(entity);
    if (lastAttackTime != null) {
      int timeSinceAttack = this.tickCount - lastAttackTime;
      score += Math.max(0, 3 - timeSinceAttack / 20.0f);
    }

    return score;
  }

  private void recordAttacker(LivingEntity attacker) {
    // 更新最后攻击时间
    lastAttackTimeMap.put(attacker, this.tickCount);

    // 添加到最近攻击者列表（如果不存在）
    if (!recentAttackers.contains(attacker)) {
      recentAttackers.add(attacker);
    }
  }

  /**
   * 检查目标是否脱离锁定半径
   */
  private void checkTargetOutOfRange() {
    if (primaryTarget != null && primaryTarget.isAlive()) {
      float distance = this.distanceTo(primaryTarget);

      if (distance > Config.TARGETED_ATTACK_RADIUS) {
        // 目标超出范围，解除锁定
        primaryTarget = null;
        // 可以触发目标丢失的动画或音效
        this.triggerAnim("controller", "target_lost");
      }
    }
  }

  /**
   * 检查状态超时
   */
  private void checkStateTimeout() {
    // 如果锁定持续时间超时，清除目标
    if (primaryTarget != null && stateDuration > Config.TARGET_LOCK_DURATION) {
      primaryTarget = null;
      // 触发超时动画
      this.triggerAnim("controller", "timeout");
    }
  }

  /**
   * 清理过期攻击者
   */
  private void cleanupAttackers() {
    // 清理攻击者列表
    recentAttackers.removeIf(attacker -> {
      if (!attacker.isAlive()) return true;

      Integer lastAttackTime = lastAttackTimeMap.get(attacker);
      if (lastAttackTime == null) return true;

      int timeSinceAttack = this.tickCount - lastAttackTime;
      return timeSinceAttack > Config.RECENT_ATTACKER_MEMORY;
    });

    // 清理时间映射
    lastAttackTimeMap.entrySet().removeIf(entry -> {
      return !entry.getKey().isAlive() ||
        (this.tickCount - entry.getValue()) > Config.RECENT_ATTACKER_MEMORY;
    });

    // 如果攻击者列表为空，清除主要目标
    if (recentAttackers.isEmpty() && primaryTarget != null) {
      primaryTarget = null;
    }
  }

  @Override
  protected void registerGoals() {
  }

  @Override
  public boolean isPushable() {
    return false;
  }

  @Override
  public boolean isPushedByFluid(@NotNull FluidType type) {
    return false;
  }

  @Override
  public boolean canBeCollidedWith() {
    return true;
  }

  @Override
  public void setDeltaMovement(Vec3 deltaMovement) {
    if (onGround()) {
      return;
    }
    if (deltaMovement.y > 0) {
      return;
    }
    super.setDeltaMovement(new Vec3(0, deltaMovement.y, 0));
  }

  @Override
  protected boolean isImmobile() {
    return true;
  }

  @Override
  public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
    return false;
  }

  @Override
  public boolean isInWater() {//无视水
    return false;
  }

  @Override
  public void lookAt(EntityAnchorArgument.Anchor anchor, Vec3 target) {
  }

  @Override
  public void lookAt(Entity entity, float maxYRotIncrease, float maxXRotIncrease) {
  }

  @Override
  protected void clampHeadRotationToBody() {
  }

  @Override
  public void makeStuckInBlock(@NotNull BlockState state, @NotNull Vec3 motionMultiplier) {
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
  }

  private PlayState predicate(AnimationState<GrantUsLove> animationState) {
    //TODO:动画
//    animationState.getController().setAnimation(RawAnimation.begin().thenPlay("idle"));
    return PlayState.CONTINUE;
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  // ========== 可配置参数 ==========
  private static final class Config {
    // 攻击参数
    public static final int NORMAL_ATTACK_COOLDOWN = 30; // 基础攻击间隔（1.5秒）
    public static final int CRASH_ATTACK_COOLDOWN = 600; // 大招攻击间隔（30秒）
    public static final int CRASH_PORTAL_OPENING_TIME = 100;
    public static final float TARGETED_ATTACK_RADIUS = 50.0F; // 锁定半径（针对玩家，脱离后解除锁定）

    // 目标锁定参数
    public static final int TARGET_LOCK_DURATION = 600; // 锁定目标持续时间（30秒）
    public static final int RECENT_ATTACKER_MEMORY = 200; // 记住攻击者的时间（10秒）
    public static final float PLAYER_TARGET_PRIORITY = 10.0F; // 玩家目标优先级权重

    // 普攻参数
    public static final float NORMAL_ATTACK_DAMAGE = 1.0F;
    public static final float NORMAL_ATTACK_AOE_RADIUS = 4.0F; // 普攻群攻半径
    public static final float NORMAL_ATTACK_AOE_HEIGHT = 4.0F;  // 普攻群攻高度
    public static final float NORMAL_ATTACK_KNOCKBACK_STRENGTH = 0.0F; // 击退强度
  }
}
