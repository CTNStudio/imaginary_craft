/**
 * ordeals--violet noon
 * 考验--紫罗兰色正午
 * Grant Us Love
 * 请给我们爱
 * 2025/12/22
 * 尘昨喧
 */

package ctn.imaginarycraft.common.entity.abnormalities.ordeals.violet;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.model.ModGeoEntityModel;
import ctn.imaginarycraft.client.particle.magicbullet.MagicBulletMagicCircleParticle;
import ctn.imaginarycraft.common.entity.abnormalities.AbnormalitiesEntity;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.ModDamageSources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
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
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

public class GrantUsLove extends AbnormalitiesEntity {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public GrantUsLove(EntityType<? extends Mob> entityType, Level level) {
    super(entityType, level);
  }

  public static AttributeSupplier.Builder createAttributes() {
    return createAbnormalitiesAttributes()
      .add(Attributes.MAX_HEALTH, 350.0)
      .add(Attributes.ATTACK_DAMAGE, 1.0)
      .add(Attributes.MOVEMENT_SPEED, 0)
      .add(Attributes.ATTACK_KNOCKBACK, 0)
      .add(Attributes.GRAVITY, 0.20)

      .add(ModAttributes.PHYSICS_VULNERABLE, 0.8)
      .add(ModAttributes.SPIRIT_VULNERABLE, 2.0)
      .add(ModAttributes.EROSION_VULNERABLE, 0.8)
      .add(ModAttributes.THE_SOUL_VULNERABLE, 1.0)
      ;
  }

  private LivingEntity primaryTarget = null; // 主要目标（优先玩家）
  private int attackCooldown = Config.NORMAL_ATTACK_COOLDOWN;
  private int crashAttackCooldown = 0;
  private int stateDuration = 0; // 当前状态持续时间
  private boolean crashAttackReady = false; //一个状态，大招冷却结束但未释放
  private int crashPortalOpeningTime = Config.CRASH_PORTAL_OPENING_TIME;
  private final List<LivingEntity> recentAttackers = new ArrayList<>(); // 最近攻击者列表
  private final Map<LivingEntity, Integer> lastAttackTimeMap = new HashMap<>(); // 记录攻击时间
  private Vec3 crashPortalPosition = null;
  // ========== 可配置参数 ==========
  private static final class Config {
    // 攻击参数
    public static final int NORMAL_ATTACK_COOLDOWN = 60; // 基础攻击间隔（3秒）
    public static final int CRASH_ATTACK_COOLDOWN = 600; // 大招攻击间隔（30秒）
    public static final int CRASH_PORTAL_OPENING_TIME = 100;
    public static final float TARGETED_ATTACK_RADIUS = 50.0F; // 锁定半径（针对玩家，脱离后解除锁定）

    // 目标锁定参数
    public static final int TARGET_LOCK_DURATION = 400; // 锁定目标持续时间（20秒）
    public static final int RECENT_ATTACKER_MEMORY = 200; // 记住攻击者的时间（10秒）
    public static final float PLAYER_TARGET_PRIORITY = 10.0F; // 玩家目标优先级权重

    // 普攻参数
    public static final float NORMAL_ATTACK_DAMAGE = 1.0F;
    public static final float CRASH_ATTACK_DAMAGE = 100.0F;
    public static final float NORMAL_ATTACK_AOE_RADIUS = 4.0F; // 普攻群攻半径
    public static final float NORMAL_ATTACK_AOE_HEIGHT = 4.0F;  // 普攻群攻高度
    public static final float CRASH_ATTACK_AOE_RADIUS = 10.0F;
    public static final float CRASH_ATTACK_AOE_HEIGHT = 7.0F;
    public static final float NORMAL_ATTACK_KNOCKBACK_STRENGTH = 0.0F; // 击退强度
    public static final float CRASH_ATTACK_KNOCKBACK_STRENGTH = 1.0F;
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
      entity -> entity != this && entity.isAlive() && this.canAttackTarget(entity)
    );

    DamageSource damageSource = ModDamageSources.erosionDamage(this);//黑伤

    for (LivingEntity target : targets) {
      if (this.applyAttackToTarget(target, damageSource,
        Config.NORMAL_ATTACK_DAMAGE, Config.NORMAL_ATTACK_KNOCKBACK_STRENGTH)) {//命中时播放
        // 视觉效果
        this.showAttackEffect();

        // 音效（如果有命中目标，播放不同音效）
        this.playAttackSound();
      }
    }
  }

  //大招
  private void executeCrashAttack() {
    AABB aoeBox = this.getBoundingBox()
      .inflate(Config.CRASH_ATTACK_AOE_RADIUS,
        Config.CRASH_ATTACK_AOE_HEIGHT / 2,
        Config.CRASH_ATTACK_AOE_RADIUS)
      .move(0, Config.CRASH_ATTACK_AOE_HEIGHT / 4, 0);
    List<LivingEntity> targets = this.level().getEntitiesOfClass(
        LivingEntity.class,
        aoeBox,
        entity -> entity != this && entity.isAlive() && this.canAttackTarget(entity)
      );

    DamageSource damageSource = ModDamageSources.physicsDamage(this);

    for (LivingEntity target : targets) {
      this.applyAttackToTarget(target,
        damageSource, Config.CRASH_ATTACK_DAMAGE, Config.CRASH_ATTACK_KNOCKBACK_STRENGTH);
    }

    this.spawnGroundParticlesInAABB(aoeBox); //在整个AABB区域的地表生成粒子

    this.playCrashAttackSound();
  }

  private boolean applyAttackToTarget(LivingEntity target, DamageSource damageSource, float damageAmount, double strength) {
    boolean hurt = target.hurt(damageSource, damageAmount);
    if (hurt) {
      this.applyKnockback(target,strength);
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
  protected void playAttackSound() {//TODO: 播放音效
  }

  private void showAttackEffect() {//TODO: 播放特效
  }

  private void playCrashAttackSound() {//TODO: 播放大招音效
    playSound(SoundEvents.ANVIL_LAND, 2.0F, 1.0F);
  }

  /**
   * 简单的地面尘土粒子效果
   */
  private void spawnGroundParticlesInAABB(AABB area) {
    if (this.level().isClientSide) return;

    ServerLevel serverLevel = (ServerLevel) this.level();

    // 在AABB底部平面上均匀生成粒子
    double minX = area.minX;
    double maxX = area.maxX;
    double minZ = area.minZ;
    double maxZ = area.maxZ;
    double baseY = area.minY + 3.1;

    // 采样点数量（根据区域大小调整）
    int samples = Math.min(40, (int)((maxX - minX) * (maxZ - minZ) / 4));

    for (int i = 0; i < samples; i++) {
      // 在区域内随机位置
      double x = minX + this.random.nextDouble() * (maxX - minX);
      double z = minZ + this.random.nextDouble() * (maxZ - minZ);

      // 向下检测找到地面
      double groundY = baseY;
      for (int dy = 0; dy < 8; dy++) {
        BlockPos pos = new BlockPos(Mth.floor(x), Mth.floor(baseY - dy), Mth.floor(z));
        if (!this.level().getBlockState(pos).isAir()) {
          groundY = pos.getY() + 1.1; // 在地面之上
          break;
        }
      }

      // 生成尘土粒子
      serverLevel.sendParticles(
        ParticleTypes.DUST_PLUME,
        x, groundY, z,
        3, // 每个点生成3个粒子
        0.2, 0.1, 0.2, // 粒子扩散范围
        0.05 // 基础速度
      );
    }
  }


  private boolean canAttackTarget(LivingEntity entity) {
    // 不攻击自己
    if (entity == this) return false;

    // 对玩家的特殊处理
    if (entity instanceof Player player) {
      return !player.isCreative();
    }
    // 不攻击紫罗兰系列考验，由于没几个，我直接用硬编码
    return !(entity instanceof GrantUsLove);
  }


  @Override
  public void tick() {
    super.tick();

    if(!this.level().isClientSide){
      stateDuration++;
      if(!this.crashAttackReady) {//无大招时
        if (crashAttackCooldown <= 0) {
          crashAttackCooldown = Config.CRASH_ATTACK_COOLDOWN;
//          this.teleportTo(this.getX(), this.getY() + 30.0F, this.getZ());
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
      }else if (this.crashPortalOpeningTime>0){
        if (this.crashPortalOpeningTime == Config.CRASH_PORTAL_OPENING_TIME){//砸击开始时,锁定传送位置
          this.crashPortalPosition = Objects.requireNonNullElse(this.primaryTarget, this).position()
            .add(0, 20, 0);
          this.createPortal();
        }else if(this.crashPortalOpeningTime == 1){
          this.teleportTo(this.crashPortalPosition.x, this.crashPortalPosition.y, this.crashPortalPosition.z);
        }
        this.crashPortalOpeningTime--;
      } else if (this.isOnGround()){//砸到地上时伤害
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
  }

  private void createPortal() {
    if (!(this.level() instanceof ServerLevel serverLevel)) {
      return;
    }

    Vec3 pos = this.crashPortalPosition;
    serverLevel.sendParticles(new MagicBulletMagicCircleParticle.Builder(0, 0)
      .radius(5.0f)
      .particleLifeTime(110)
      .buildOptions(0), pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
  }

  private boolean isOnGround() {
    return this.onGround();
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

  private void updatePrimaryTarget(){
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
      if (!attacker.isAlive()) continue;

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
  }//原版ai

  @Override
  public boolean isPushable() {
    return false;
  }

  @Override
  public boolean isPushedByFluid(@NotNull FluidType type) {
    return false;
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
  public void makeStuckInBlock(@NotNull BlockState state, @NotNull Vec3 motionMultiplier) {
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
  }

  private PlayState predicate(AnimationState<GrantUsLove> animationState) {
    animationState.getController().setAnimation(RawAnimation.begin().thenPlay("idle"));
    return PlayState.CONTINUE;
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  public static class GrantUsLoveRenderer extends GeoEntityRenderer<GrantUsLove> {
    public GrantUsLoveRenderer(EntityRendererProvider.Context context) {
      super(context, new ModGeoEntityModel<>("grant_us_love"));
      this.shadowRadius = 1.5f;
    }

    @Override
    public void render(GrantUsLove entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
      poseStack.pushPose();
      float scale = 0.3f;
      poseStack.scale(scale, scale, scale);
      super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
      poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GrantUsLove animatable) {
      return ModGeoEntityModel.getTexturePath("grant_us_love");
    }
  }
}
