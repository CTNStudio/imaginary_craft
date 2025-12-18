package ctn.imaginarycraft.common.entity.projectile;

import ctn.imaginarycraft.init.entiey.AbnormalitiesEntityTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

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

  public MagicBulletEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
    super(entityType, level);
    this.setNoGravity(true);
  }

  public MagicBulletEntity(Level level, LivingEntity shooter) {
    super(AbnormalitiesEntityTypes.MAGIC_BULLET_ENTITY.get(), shooter, level);
    this.setNoGravity(true);
  }

  public static MagicBulletEntity create(@Nonnull Level level, @Nonnull LivingEntity shooter,
                                         @Nonnegative float damage, @Nonnull LivingEntity target) {
    MagicBulletEntity entity = new MagicBulletEntity(level, shooter);
    entity.setOwner(shooter);
    entity.setDamage(damage);
    entity.setTarget(target);

    return entity;
  }

  public void setDamage(float damage) {
    this.damage = damage;
  }

  public void setTarget(@Nonnull LivingEntity target) {
    this.target = target;
  }

  /**
   * 轨迹修正。
   */
  protected void correctTrajectory() {
    // TODO
  }

  protected void calculateCanHitTarget() {
    // TODO
  }

  protected void setDead() {
    this.discard();
  }

  @Override
  public void tick() {
    if (Objects.isNull(this.target) || this.damage < 0.0f) {
      this.setDead();
      return;
    }

    var oPos = this.getOnPos();

    super.tick();

    var nPos = this.getOnPos();
    var tPos = this.target.getOnPos();

    if (oPos.distSqr(tPos) > nPos.distSqr(tPos)) {
      this.correctTrajectory();
    }
  }

  @Override
  protected void onHitBlock(BlockHitResult result) {
    this.correctTrajectory();
    // TODO
  }

  @Override
  protected void onHitEntity(EntityHitResult result) {
    super.onHitEntity(result);
    // TODO - end.
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
  }
}
