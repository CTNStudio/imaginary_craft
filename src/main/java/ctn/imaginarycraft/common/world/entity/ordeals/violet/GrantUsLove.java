package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.IAbnormalitiesEntity;
import ctn.imaginarycraft.api.world.entity.IBehaviorTreeMob;
import ctn.imaginarycraft.api.world.entity.ai.ModHurtByTargetGoal;
import ctn.imaginarycraft.api.world.entity.ai.behavior.BTFactory;
import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.BTRoot;
import ctn.imaginarycraft.api.world.entity.ai.behavior.composite.ParallelNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.condition.ConditionBT;
import ctn.imaginarycraft.api.world.entity.ai.behavior.condition.DistanceLowerThanCondition;
import ctn.imaginarycraft.api.world.entity.ai.behavior.condition.TargetExistCondition;
import ctn.imaginarycraft.client.particle.magicbullet.MagicBulletMagicCircleParticle;
import ctn.imaginarycraft.init.ModSoundEvents;
import ctn.imaginarycraft.init.animmodels.ModAnimations;
import ctn.imaginarycraft.init.world.ModAttributes;
import ctn.imaginarycraft.init.world.ModDamageSources;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.registry.entries.EpicFightAttributes;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class GrantUsLove extends Mob implements IAbnormalitiesEntity, IBehaviorTreeMob<GrantUsLove>, Enemy {
  // 普通攻击冷却时间 (tick)
  public static final int NORMAL_ATK_CD = 20 * 3;
  // 大招冷却时间 (tick)
  public static final int CRASH_ATK_CD = 600;
  // 大招 Portal 开启持续时间 (tick)
  public static final int CRASH_PORTAL_OPEN_TIME = 100;
  // 目标锁定攻击半径
  public static final float TARGET_ATK_RADIUS = 50.0F;
  // 目标锁定持续时间 (tick)
  public static final int TARGET_LOCK_TIME = 600;
  // 攻击者记忆时间 (tick)
  public static final int ATTACKER_MEM_TIME = 500;
  // 玩家目标优先级基础分
  public static final float PLAYER_PRIORITY = 10.0F;
  // 普通攻击伤害
  public static final float NORMAL_ATK_DMG = 1.0F;
  // 普通攻击 AOE 半径
  public static final float NORMAL_ATK_AOE_RADIUS = 4.0F;
  // 普通攻击 AOE 高度
  public static final float NORMAL_ATK_AOE_HEIGHT = 4.0F;
  // 普通攻击击退强度
  public static final float NORMAL_ATK_KNOCKBACK = 0.0F;
  // 大招生成 Portal 时的高度偏移
  public static final int CRASH_PORTAL_HEIGHT_OFFSET = 20;
  // 大招落地检测后的冷却时间 (tick)
  public static final int CRASH_ATTACK_LANDING_DELAY = 60;
  // 闲置音效最小间隔 (tick)
  public static final int IDLE_SOUND_MIN_INTERVAL = 2;
  // 大招 AOE 半径
  public static final float CRASH_ATK_AOE_RADIUS = 10.0F;
  // 大招伤害
  public static final float CRASH_ATK_DMG = 200.0F;
  // 大招读条状态
  protected static final EntityDataAccessor<Boolean> CRASH_ATK_READING = SynchedEntityData.defineId(GrantUsLove.class, EntityDataSerializers.BOOLEAN);
  private final List<LivingEntity> attackers = new ArrayList<>();
  private final Map<LivingEntity, Integer> lastAtkTimeMap = new HashMap<>();
  private final Vec3 portalPos = null; // 大招 Portal 位置
  // TODO 后面替换成触手的独立处理
  private int normalAtkCd = NORMAL_ATK_CD; // 普通攻击冷却时间
  private int crashAtkCd = 0; // 大招冷却时间
  private int stateTime = 0;
  private boolean crashAtkReady = false; // 大招是否准备就绪
  private int portalOpenTime = 1; // 大招 Portal 开启时间
  private int idleSoundCd = 0; // 闲置音效间隔

  public GrantUsLove(EntityType<? extends Mob> entityType, Level level) {
    super(entityType, level);
    entityData.set(CRASH_ATK_READING, false);
  }

  public static AttributeSupplier.Builder createAttributes() {
    return createMobAttributes()
      .add(Attributes.KNOCKBACK_RESISTANCE, 1)
      .add(Attributes.MAX_HEALTH, 350)
      .add(Attributes.ATTACK_DAMAGE, 7)
      .add(Attributes.MOVEMENT_SPEED, 0)
      .add(Attributes.ATTACK_KNOCKBACK, 1)
      .add(Attributes.GRAVITY, 0.1)
      .add(ModAttributes.PHYSICS_VULNERABLE, 0.8)
      .add(ModAttributes.SPIRIT_VULNERABLE, 2.0)
      .add(ModAttributes.EROSION_VULNERABLE, 0.8)
      .add(ModAttributes.THE_SOUL_VULNERABLE, 1)
      .add(EpicFightAttributes.IMPACT, 8)
      .add(EpicFightAttributes.MAX_STRIKES, 8);
  }

  /**
   * 快速规范化角度到 [-180, 180] 范围
   */
  private static double normalizeAngle(double angle) {
    // 使用取模运算代替循环，性能更优
    angle = angle % 360;
    if (angle > 180) {
      angle -= 360;
    } else if (angle < -180) {
      angle += 360;
    }
    return angle;
  }

  @Override
  protected void registerGoals() {
    Predicate<LivingEntity> predicate = entity -> canTarget(entity) && entity.distanceTo(entity) <= 2.5;
    this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true, predicate));
    this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, true, predicate));
    this.targetSelector.addGoal(2, new ModHurtByTargetGoal(this).setAlertOthers(GrantUsLove.class)); // TODO 以及其他考验等
  }

  @Override
  public void tick() {
    super.tick();
//
//    if (this.level().isClientSide) {
//      return;
//    }
//
//    stateTime++;
//    if (!this.crashAtkReady) {
//      if (crashAtkCd <= 0) {
//        crashAtkCd = CRASH_ATK_CD;
////        crashAtkCd = CRASH_ATTACK_LANDING_DELAY; // 3 秒后释放大招
//        this.crashAtkReady = true;
//      } else {
//        crashAtkCd--;
//        if (normalAtkCd > 0) {
//          normalAtkCd--;
//        } else {
////          this.aoeAttack();
//          if (tentacleAttack(getTarget())) {
//            normalAtkCd = NORMAL_ATK_CD;
//          }
//        }
//      }
//    } else if (this.portalOpenTime > 0) {
//      if (this.portalOpenTime == CRASH_PORTAL_OPEN_TIME) {
//        // 在目标上方生成传送门
//        this.portalPos = Objects.requireNonNullElse(getTarget(), this).position()
//          .add(0, CRASH_PORTAL_HEIGHT_OFFSET, 0);
//        this.createPortal(this.portalPos);
//      } else if (this.portalOpenTime == 1) {
//        if (this.portalPos == null) {
//          portalPos = this.position().add(0, CRASH_PORTAL_HEIGHT_OFFSET, 0);
//        }
//        this.setPos(portalPos.x, portalPos.y, portalPos.z);
//      }
//      this.portalOpenTime--;
//    } else if (this.onGround()) {
//      this.crashAtkReady = false;
//      this.portalOpenTime = CRASH_PORTAL_OPEN_TIME;
//      this.crashAttack();
//    }
//
//    this.checkTargetRange();
//
//    if (this.tickCount % 100 == 0) { // 每 5 秒清理一次攻击者记录
//      this.cleanAttackers();
//    }
//
//    this.checkStateTime();
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
    builder.define(CRASH_ATK_READING, false);
  }

  @Override
  public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
    super.onSyncedDataUpdated(key);
  }

  @Override
  public void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> dataValues) {
    super.onSyncedDataUpdated(dataValues);
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
  public void onSpawnByEgg() {
    this.crashAtkReady = false;
    this.crashAtkCd = CRASH_ATK_CD;
    this.portalOpenTime = CRASH_PORTAL_OPEN_TIME;
    // TODO 调试用：缩短大招冷却时间
//    this.crashAtkCd = 20 * 2; // 2 秒后释放大招
  }

  private void aoeAttack() {
    AABB aoeBox = this.getBoundingBox()
      .inflate(NORMAL_ATK_AOE_RADIUS, NORMAL_ATK_AOE_HEIGHT / 2, NORMAL_ATK_AOE_RADIUS)
      .move(0, NORMAL_ATK_AOE_HEIGHT / 4, 0);

    List<LivingEntity> targets = this.level().getEntitiesOfClass(
      LivingEntity.class,
      aoeBox,
      entity -> entity != this && entity.isAlive() && this.isTarget(entity));

    DamageSource damageSource = ModDamageSources.erosionDamage(this);

    for (LivingEntity target : targets) {
      if (this.hurtTarget(target, damageSource, NORMAL_ATK_DMG, NORMAL_ATK_KNOCKBACK)) {
        this.atkEffect();
        this.onAttack();
      }
    }

    if (!targets.isEmpty()) {
      this.onAttack();
    } else if (idleSoundCd <= 0) {
      this.idleSound();
      idleSoundCd = IDLE_SOUND_MIN_INTERVAL;
    } else {
      idleSoundCd--;
    }
  }

  private void crashAttack() {
    this.spawnParticle(CRASH_ATK_AOE_RADIUS + 1);
    this.crashAtkSound();
    ctn.imaginarycraft.util.LevelUtil.playCrashAttackSoundHurt(this, this.level(), position().subtract(0, 1, 0),
      CRASH_ATK_AOE_RADIUS + 1,
      CRASH_ATK_DMG,
      true,
      this::canTarget,
      entity -> !entity.is(this));
  }

  private boolean hurtTarget(LivingEntity target, DamageSource dmgSrc, float dmgAmt, double strength) {
    boolean hurt = target.hurt(dmgSrc, dmgAmt);
    if (hurt) {
      this.knockback(target, strength);
    }
    return hurt;
  }

  private void knockback(LivingEntity target, double strength) {
    // 计算从自己指向目标的方向
    double dx = target.getX() - this.getX();
    double dz = target.getZ() - this.getZ();
    double distance = Math.sqrt(dx * dx + dz * dz);

    if (distance > 0) {
      target.knockback(strength, dx / distance, dz / distance);
    }
  }

  protected void onAttack() {
    playSound(ModSoundEvents.VIOLET_NOON_ATK.value(), 1.0F, 1.0F);
  }

  private void atkEffect() {
    //TODO: 播放特效
  }

  private void crashAtkSound() {
    playSound(ModSoundEvents.VIOLET_NOON_DOWN.value(), 2.0F, 1.0F);
  }

  private void idleSound() {
    playSound(ModSoundEvents.VIOLET_NOON_idle.value(), 1.0F, 1.0F);
  }

  private void spawnParticle(double radius) {
    if (this.level().isClientSide) return;
    Vec3 position = position();
    ServerLevel level = (ServerLevel) this.level();
    LevelUtil.circleSlamFracture(this, level, position().subtract(0, 1, 0), radius, false, false, false);
    EpicFightParticles.AIR_BURST.get().spawnParticleWithArgument(level,
      position.x, position.y, position.z,
      0, 0, radius * 2);
  }

  private boolean isTarget(Entity entity) {
    if (entity == this) return false;

    if (entity instanceof Player player) {
      return !player.isCreative() && !player.isSpectator();
    }
    return !isEtHnicGroup(entity);
  }

  @Override
  public boolean isEtHnicGroup(Entity entity) {
    // TODO 后续采用tag的形式处理
    return IAbnormalitiesEntity.super.isEtHnicGroup(entity) || entity instanceof GrantUsLove;
  }

  protected boolean canTarget(Entity entity) {
    return entity.isAlive() && entity.isAttackable() && this.isTarget(entity);
  }

  /**
   * 生成传送门
   */
  protected void createPortal(Vec3 pos) {
    if (!(this.level() instanceof ServerLevel serverLevel)) {
      return;
    }
    // TODO 替换成独立粒子
    serverLevel.sendParticles(new MagicBulletMagicCircleParticle.Builder(-90, 0) // -90 度旋转的魔法阵粒子
      .radius(5.0f) // 魔法阵半径
      .particleLifeTime(110) // 粒子存活时间 (比 portalOpenTime 多 10 tick)
      .buildOptions(0), pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
  }

  @Override
  protected void actuallyHurt(DamageSource source, float damageAmount) {
    super.actuallyHurt(source, damageAmount);
    if (!this.level().isClientSide && source.getEntity() instanceof LivingEntity attacker && attacker.isAttackable()) {
      this.addAttacker(attacker);
      this.updateTarget();
      this.stateTime = 0;
    }
  }

  private void updateTarget() {
    if (attackers.isEmpty()) {
      setTarget(null);
      return;
    }

    if (getTarget() != null && getTarget().isAlive() &&
      this.distanceTo(getTarget()) <= TARGET_ATK_RADIUS) {
      return;
    }

    LivingEntity bestTarget = null;
    float bestScore = -1;

    for (LivingEntity attacker : attackers) {
      if (!attacker.isAlive() || !this.isTarget(attacker)) continue;

      float score = this.calcTargetScore(attacker);

      if (score > bestScore) {
        bestScore = score;
        bestTarget = attacker;
      }
    }

    setTarget(bestTarget);
  }

  private float calcTargetScore(LivingEntity entity) {
    float score = 0;

    if (entity instanceof Player) {
      score += PLAYER_PRIORITY; // 玩家基础优先级

      if (((Player) entity).isCreative()) {
        score -= 5; // 创造模式玩家降低优先级
      }
    }
    float distance = this.distanceTo(entity);
    score += Math.max(0, 5 - distance / 10); // 距离越近分数越高 (最大 5 分)

    Integer lastAtkTime = lastAtkTimeMap.get(entity);
    if (lastAtkTime != null) {
      int timeSinceAtk = this.tickCount - lastAtkTime;
      score += Math.max(0, 3 - timeSinceAtk / 20.0f); // 最近攻击的玩家加分 (最大 3 分，持续 3 秒)
    }

    return score;
  }

  private void addAttacker(LivingEntity attacker) {
    lastAtkTimeMap.put(attacker, this.tickCount);

    if (!attackers.contains(attacker)) {
      attackers.add(attacker);
    }
  }

  private void checkTargetRange() {
    if (getTarget() != null && getTarget().isAlive()) {
      float distance = this.distanceTo(getTarget());

      if (distance > TARGET_ATK_RADIUS) {
        setTarget(null);
      }
    }
  }

  private void checkStateTime() {
    if (getTarget() != null && stateTime > TARGET_LOCK_TIME) {
      setTarget(null);
    }
  }

  private void cleanAttackers() {
    attackers.removeIf(attacker -> {
      if (!attacker.isAlive()) return true;

      Integer lastAtkTime = lastAtkTimeMap.get(attacker);
      if (lastAtkTime == null) return true;

      int timeSinceAtk = this.tickCount - lastAtkTime;
      return timeSinceAtk > ATTACKER_MEM_TIME;
    });

    lastAtkTimeMap.entrySet().removeIf(entry -> !entry.getKey().isAlive() || (this.tickCount - entry.getValue()) > ATTACKER_MEM_TIME);

    if (attackers.isEmpty() && getTarget() != null) {
      setTarget(null);
    }
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
    return false;
  }

  @Override
  public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
    return false;
  }

  @Override
  public boolean isInWater() {
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

  public boolean tentacleAttack(LivingEntity target) {
    return target != null;
//
//    // 计算相对位置（使用平方距离避免开方运算）
//    double dx = target.getX() - this.getX();
//    double dz = target.getZ() - this.getZ();
//    double dy = target.getY() - this.getY();
//    double horizontalDistSq = dx * dx + dz * dz;
//
//    // 快速路径：距离检查（使用平方比较）
//    if (horizontalDistSq > 10 * 10) {
//      return false;
//    }
//
//    // 快速路径：高度差检查（避免打空气）
//    if (Math.abs(dy) > getBbHeight() * 1.5f) {
//      return false;
//    }
//
//    // 计算相对角度并优化规范化到 [-180, 180]
//    double angleToTarget = Math.toDegrees(Math.atan2(dz, dx)) - this.getYRot();
//    angleToTarget = normalizeAngle(angleToTarget);
//
//    // 获取动画（内联优化，避免方法调用开销）
//    AnimationManager.AnimationAccessor<AttackAnimation> animation;
//
//    // 角度绝对值预计算
//    double absAngle = Math.abs(angleToTarget);
//
//    if (absAngle <= 45) {
//      // 重叠区域（-45° 到 45°）：左右随机选择
//      int tentacleIndex = this.random.nextInt(3) + 1;
//      boolean useLeft = this.random.nextBoolean();
//      animation = getTentacleAnimation(useLeft, tentacleIndex);
//    } else if (angleToTarget > 45) {
//      // 左侧区域（45° 到 180°）
//      int tentacleIndex = this.random.nextInt(3) + 1;
//      animation = getLeftTentacleAnimation(tentacleIndex);
//    } else {
//      // 右侧区域（-180° 到 -45°）
//      int tentacleIndex = this.random.nextInt(3) + 1;
//      animation = getRightTentacleAnimation(tentacleIndex);
//    }
//    // 播放动画
//    getGrantUsLovePatch().playAnimationSynchronized(animation, 1);
  }

  /**
   * 根据方向和触手索引获取动画（内联版本）
   */
  private AnimationManager.AnimationAccessor<AttackAnimation> getTentacleAnimation(boolean useLeft, int index) {
    return useLeft ? getLeftTentacleAnimation(index) : getRightTentacleAnimation(index);
  }

  /**
   * 获取左侧触手攻击动画
   */
  private AnimationManager.AnimationAccessor<AttackAnimation> getLeftTentacleAnimation(int index) {
    return switch (index) {
      case 2 -> ModAnimations.GRANT_US_LOVE_SWING_L2;
      case 3 -> ModAnimations.GRANT_US_LOVE_SWING_L3;
      default -> ModAnimations.GRANT_US_LOVE_SWING_L1;
    };
  }

  /**
   * 获取右侧触手攻击动画
   */
  private AnimationManager.AnimationAccessor<AttackAnimation> getRightTentacleAnimation(int index) {
    return switch (index) {
      case 2 -> ModAnimations.GRANT_US_LOVE_SWING_R2;
      case 3 -> ModAnimations.GRANT_US_LOVE_SWING_R3;
      default -> ModAnimations.GRANT_US_LOVE_SWING_R1;
    };
  }

  private GrantUsLovePatch getGrantUsLovePatch() {
    return EpicFightCapabilities.getEntityPatch(this, GrantUsLovePatch.class);
  }

  @Override
  public BTRoot<GrantUsLove> createBehaviorTree() {
    return new GrantUsLoveBT(this);
  }

  public static class GrantUsLoveBT extends BTRoot<GrantUsLove> {

    public GrantUsLoveBT(GrantUsLove mob) {
      super(mob);
    }

    @Override
    protected @NotNull BTNode createBehaviorTree() {
      return BTFactory.parallel(ParallelNode.Policy.REQUIRE_ALL, ParallelNode.Policy.REQUIRE_ALL)
        .addChild(BTFactory.infinite(BTFactory.selector()
//          .addWithCondition(, )
          // 目标不存在
          .addWithCondition(ConditionBT.not(new TargetExistCondition(this.mob)), BTFactory.sequence())
          // 目标存在
          .addChild(BTFactory.infinite(BTFactory.selector()
            // 释放大招
            .addWithCondition(ConditionBT.and(new DistanceLowerThanCondition(this.mob, TARGET_ATK_RADIUS)), BTFactory.sequence()
              // 设置基本
              .addChild(BTFactory.success(() -> {
              }))
              // 等待5秒
              .addChild(BTFactory.sequence()
                  .addChild(BTFactory.wait(100))
//                  .addWithCondition(,)
              ))))))
        // 其他处理例如：技能冷却
        .addChild(BTFactory.infinite(BTFactory.success(() -> {
          if (mob.normalAtkCd != 0) {
            mob.normalAtkCd--;
          }
          if (mob.crashAtkCd != 0) {
            mob.crashAtkCd--;
          }
        })));
    }
  }
}
