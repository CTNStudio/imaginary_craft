package ctn.imaginarycraft.common.entity.projectile;

import ctn.imaginarycraft.init.entiey.AbnormalitiesEntityTypes;
import ctn.imaginarycraft.util.PiercingUtil;
import ctn.imaginarycraft.util.PiercingUtil.PierceData;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.CheckForNull;
import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Objects;

public class MagicBulletEntity extends ModBulletEntity {
  @CheckForSigned
  private float damage;

  @CheckForNull
  private LivingEntity target;

  public MagicBulletEntity(EntityType<? extends ModBulletEntity> entityType, Level level) {
    super(entityType, level);
    this.setNoGravity(true);
  }

  public MagicBulletEntity(Level level, LivingEntity shooter) {
    super(AbnormalitiesEntityTypes.MAGIC_BULLET_ENTITY.get(), shooter, level);
    this.setNoGravity(true);
  }

  /**
   * 创建基础魔法子弹（无穿透）
   */
  public static MagicBulletEntity create(@Nonnull Level level, @Nonnull LivingEntity shooter,
                                         @Nonnegative float damage, @Nonnull LivingEntity target) {
    MagicBulletEntity entity = new MagicBulletEntity(level, shooter);
    entity.setOwner(shooter);
    entity.setDamage(damage);
    entity.setTarget(target);

    return entity;
  }

  /**
   * 创建带有穿透效果的魔法子弹
   * 
   * @param level 世界
   * @param shooter 射击者
   * @param damage 伤害
   * @param target 目标
   * @param maxPierce 最大穿透数（-1表示无限）
   * @param damageDecay 伤害衰减率（0-1）
   * @param wallPassThrough 是否穿墙
   * @return 魔法子弹实体
   */
  public static MagicBulletEntity createWithPiercing(@Nonnull Level level, @Nonnull LivingEntity shooter,
                                                      @Nonnegative float damage, @Nonnull LivingEntity target,
                                                      int maxPierce, float damageDecay, boolean wallPassThrough) {
    MagicBulletEntity entity = new MagicBulletEntity(level, shooter);
    entity.setOwner(shooter);
    entity.setDamage(damage);
    entity.setTarget(target);
    
    // 添加穿透标签
    PierceData config = new PierceData()
        .maxPierce(maxPierce)
        .damageDecay(damageDecay)
        .wallPassThrough(wallPassThrough)
        .originalDamage(damage);
    
    PiercingUtil.addPiercingTag(entity, config);
    
    return entity;
  }

  /**
   * 创建默认穿透效果的魔法子弹
   * 默认配置：穿透所有目标，0.75衰减，穿墙
   */
  public static MagicBulletEntity createWithDefaultPiercing(@Nonnull Level level, @Nonnull LivingEntity shooter,
                                                            @Nonnegative float damage, @Nonnull LivingEntity target) {
    return createWithPiercing(level, shooter, damage, target, -1, 0.75f, true);
  }

  public void setDamage(float damage) {
    this.damage = damage;
  }

  public float getDamage() {
    return damage;
  }

  public void setTarget(@Nonnull LivingEntity target) {
    this.target = target;
  }

  @CheckForNull
  public LivingEntity getTarget() {
    return target;
  }

  /**
   * 轨迹修正。
   */
  protected void correctTrajectory() {
    if (Objects.isNull(this.target)) {
      this.setDead();
      return;
    }

    final Vec3 vec = this.getViewVector(1).reverse();
    final Vec3 pos = this.target.getPosition(1).add(vec.scale(3));

    this.setPos(pos);

    // TODO - 新增特效。
  }

  protected void setDead() {
    this.discard();
  }

  @Override
  public void tick() {
        // 每tick强制同步noPhysics
        var config = PiercingUtil.getPiercingConfig(this);
        if (config != null && config.isWallPassThroughEnabled()) {
            this.noPhysics = true;
        }

        if (Objects.isNull(this.target) || this.damage < 0.0f) {
            this.setDead();
            return;
        }

        var oPos = this.getOnPos();

        // 如果有穿透标签，事件监听器会自动处理穿透逻辑
        // 这里只保留追踪逻辑
        super.tick();

        var nPos = this.getOnPos();
        var tPos = this.target.getOnPos();

        if (oPos.distSqr(tPos) > nPos.distSqr(tPos)) {
            this.correctTrajectory();
        }
    }

  @Override
  protected void onHitBlock(BlockHitResult result) {
    // 如果有穿透标签且启用了穿墙，忽略方块碰撞
    if (PiercingUtil.hasPiercingTag(this)) {
      PierceData config = PiercingUtil.getPiercingConfig(this);
      if(config != null){
        config.wallPassThrough(true);
        correctTrajectory();
        return;
      }
    }
    
    this.correctTrajectory();
  }

  @Override
  protected void onHitEntity(EntityHitResult result) {
    // 穿透逻辑由事件监听器处理，这里可以添加额外的命中效果
    
    // 如果没有穿透标签，使用默认行为
    if (!PiercingUtil.hasPiercingTag(this)) {
      super.onHitEntity(result);
    }
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
  }
}

