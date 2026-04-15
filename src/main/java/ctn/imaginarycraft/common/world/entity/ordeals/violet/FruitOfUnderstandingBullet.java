package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.client.model.entity.ModGeoEntityModel;
import ctn.imaginarycraft.init.world.ModDamageSources;
import ctn.imaginarycraft.init.world.entity.ProjectileEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FruitOfUnderstandingBullet extends ThrowableProjectile implements GeoEntity {

  public static final float BULLET_DAMAGE = 8.0F;
  public static final float BULLET_SPEED = 0.6F;
  public static final float GRAVITY = 0.0005F;

  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public FruitOfUnderstandingBullet(EntityType<? extends ThrowableProjectile> entityType, Level level) {
    super(entityType, level);
  }

  public FruitOfUnderstandingBullet(Level level, LivingEntity shooter, double x, double y, double z) {
    super(ProjectileEntityTypes.FRUIT_OF_UNDERSTANDING_BULLET.get(), shooter, level);
    this.setPos(x, y, z);
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
  }

  @Override
  public void tick() {
    super.tick();

    if (this.level().isClientSide) {
      Vec3 movement = this.getDeltaMovement();
      if (movement.length() > 0) {
        this.setRot(
          (float)(Math.atan2(movement.z, movement.x) * 180.0 / Math.PI) - 90.0f,
          (float)(Math.atan2(movement.y, movement.horizontalDistance()) * 180.0 / Math.PI)
        );
      }
      return;
    }

    Vec3 movement = this.getDeltaMovement();

    if (movement.length() > 0) {
      this.setRot(
        (float)(Math.atan2(movement.z, movement.x) * 180.0 / Math.PI) - 90.0f,
        (float)(Math.atan2(movement.y, movement.horizontalDistance()) * 180.0 / Math.PI)
      );
    }

    Vec3 newMovement = movement.add(0, -GRAVITY, 0);
    this.setDeltaMovement(newMovement.scale(0.98));
  }

  @Override
  protected void onHit(HitResult result) {
    super.onHit(result);

    if (!this.level().isClientSide) {
      this.discard();
    }
  }

  @Override
  protected void onHitEntity(EntityHitResult result) {
    super.onHitEntity(result);

    Entity target = result.getEntity();
    Entity shooter = this.getOwner();

    if (shooter instanceof FruitOfUnderstanding fruit && target != null && !fruit.isAlly(target)) {
      DamageSource damageSource = ModDamageSources.erosionDamage(fruit);
      target.hurt(damageSource, BULLET_DAMAGE);
    }

    if (!this.level().isClientSide) {
      this.discard();
    }
  }

  @Override
  protected void onHitBlock(BlockHitResult result) {
    super.onHitBlock(result);

    if (!this.level().isClientSide) {
      this.discard();
    }
  }

  @Override
  public void addAdditionalSaveData(CompoundTag compound) {
    super.addAdditionalSaveData(compound);
  }

  @Override
  public void readAdditionalSaveData(CompoundTag compound) {
    super.readAdditionalSaveData(compound);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    var mainController = new AnimationController<>(this, "controller", 0, state -> {
      return state.setAndContinue(RawAnimation.begin().thenLoop("bullet_idle"));
    });

    controllers.add(mainController);
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  public static ModGeoEntityModel<FruitOfUnderstandingBullet> getModel() {
    return new ModGeoEntityModel<>("fruit_of_understanding_bullet");
  }
}




