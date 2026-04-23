package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.ISpawnByEgg;
import ctn.imaginarycraft.common.world.entity.ordeals.IOrdealsEntity;
import ctn.imaginarycraft.init.ModSoundEvents;
import ctn.imaginarycraft.init.world.ModAttributes;
import ctn.imaginarycraft.init.world.ModDamageSources;
import ctn.imaginarycraft.init.world.ModDamageTypes;
import ctn.imaginarycraft.init.world.entity.ProjectileEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

// TODO 优化渲染
// TODO 优化弹幕渲染，方向，和发射位置
public class FruitOfUnderstanding extends PathfinderMob implements IOrdealsVioletEntity, ISpawnByEgg, GeoEntity, Enemy {
	protected static final EntityDataAccessor<Boolean> SELF_DESTRUCT_CHARGING = SynchedEntityData.defineId(FruitOfUnderstanding.class, EntityDataSerializers.BOOLEAN);
	protected static final EntityDataAccessor<Float> CHARGE_DAMAGE_TAKEN = SynchedEntityData.defineId(FruitOfUnderstanding.class, EntityDataSerializers.FLOAT);

	public static final int SELF_DESTRUCT_ATTACK_COUNT = 3; //自爆计数器（近战攻击时有概率减少）
	public static final float SELF_DESTRUCT_CHARGE_TIME = 100; //自爆蓄力时间
	public static final int SELF_DESTRUCT_ANIM_DELAY = 74;  //自爆动画延迟（蓄力多久后播放自爆动画）
	public static final float INTERRUPT_DAMAGE_PERCENTAGE = 0.2F; //自爆中断所需伤害百分比（0.2F即为20%的血量最大值）

	public static final float NORMAL_ATK_DMG = 3.0F;
	public static final float NORMAL_ATK_RANGE = 3.5F;

	//自爆相关
	public static final float SELF_DESTRUCT_DMG = 30.0F;
	public static final float SELF_DESTRUCT_AOE_RADIUS = 8.0F;
	public static final float ATK_REDUCE_CHANCE = 0.3F; //普攻减少自爆计数器的概率

	//喷射攻击相关
	public static final float BULLET_ATTACK_RANGE_MIN = 5.0F;
	public static final float BULLET_ATTACK_RANGE_MAX = 10.0F;
	public static final int BULLET_COUNT_MIN = 5;
	public static final int BULLET_COUNT_MAX = 7;
	public static final float BULLET_SPREAD_ANGLE = 120.0F;
	public static final int BULLET_ATTACK_COOLDOWN = 60;
	public static final int BULLET_ATTACK_Windup = 40;

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	private int selfDestructCounter = SELF_DESTRUCT_ATTACK_COUNT;
	private int chargeTime = 0;
	private boolean isCharging = false;
	private boolean hasPlayedExplodeAnim = false;
	private float chargeDamageTaken = 0.0F;

	private int walkSoundCooldown = 0;
	private boolean isBulletAttacking = false;
	private int bulletAttackCooldown = 100;
	private int bulletAttackWindup = BULLET_ATTACK_Windup;

	public FruitOfUnderstanding(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
		entityData.set(SELF_DESTRUCT_CHARGING, false);
		entityData.set(CHARGE_DAMAGE_TAKEN, 0.0F);

	}

	public static AttributeSupplier.Builder createAttributes() {
		return createMobAttributes()
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
			.add(Attributes.MAX_HEALTH, 190.0)
			.add(Attributes.ATTACK_DAMAGE, 3.0)
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.ATTACK_KNOCKBACK, 0.0)
			.add(Attributes.GRAVITY, 0.08)
			.add(ModAttributes.PHYSICS_VULNERABLE, 1.0)
			.add(ModAttributes.SPIRIT_VULNERABLE, 1.5)
			.add(ModAttributes.EROSION_VULNERABLE, 1.0)
			.add(ModAttributes.THE_SOUL_VULNERABLE, 1.0);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SELF_DESTRUCT_CHARGING, false);
		builder.define(CHARGE_DAMAGE_TAKEN, 0.0F);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
		if (SELF_DESTRUCT_CHARGING.equals(key)) {
			this.isCharging = entityData.get(SELF_DESTRUCT_CHARGING);
		}
	}

	public void onSpawnByEgg() {
	}

	@Override
	public void registerGoals() {
		super.registerGoals();
		IOrdealsVioletEntity.super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(3, new FruitOfUnderstandingMeleeAttackGoal());
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6) {
			@Override
			public boolean canUse() {
				return !isCharging && !isBulletAttacking && super.canUse();
			}
		});

		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				return !isCharging && !isBulletAttacking && super.canUse();
			}
		});

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
			@Override
			public boolean canContinueToUse() {
				LivingEntity target = this.mob.getTarget();
				return target != null && !isCamp(target) && super.canContinueToUse();
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, false) {
			@Override
			public boolean canUse() {
				LivingEntity target = this.mob.getTarget();
				if (target != null && isCamp(target)) {
					return false;
				}
				return !isCharging && super.canUse();
			}
		});
	}


	private boolean canPerformNormalAttack() {
		LivingEntity target = this.getTarget();
		return !this.isCharging &&
			!this.isBulletAttacking &&
			target != null &&
			target.isAlive() &&
			!isCamp(target);
	}

	@Override
	public boolean canTarget(Entity entity) {
		return IOrdealsVioletEntity.super.canTarget(entity);
	}

	private void tryReduceSelfDestructCounter() {
		if (!this.isCharging && this.selfDestructCounter > 0) {
			if (this.random.nextFloat() < ATK_REDUCE_CHANCE) {
				this.selfDestructCounter--;
				if (this.selfDestructCounter <= 0) {
					startSelfDestructCharge();
				}
			}
		}
	}

	private void startSelfDestructCharge() {
		if (this.isCharging) return;

		this.isCharging = true;
		this.chargeTime = 0;
		this.hasPlayedExplodeAnim = false;
		this.chargeDamageTaken = 0.0F;
		entityData.set(SELF_DESTRUCT_CHARGING, true);
		entityData.set(CHARGE_DAMAGE_TAKEN, 0.0F);

		this.setTarget(null);

	}

	private void selfDestructAttack() {
		if (this.level().isClientSide) return;
		selfDestructSound();

		for (LivingEntity livingEntity : this.level().getEntitiesOfClass(
			LivingEntity.class,
			this.getBoundingBox().inflate(SELF_DESTRUCT_AOE_RADIUS),
			entity -> entity != this && entity.isAlive() && !isCamp(entity))) {

			livingEntity.hurt(ModDamageSources.erosionDamage(this), SELF_DESTRUCT_DMG);
		}

		this.discard();

		spawnExplosionParticles();
	}

	private void spawnExplosionParticles() { //TODO: 添加自爆粒子效果
	}

	protected void onAttack() {
	}

	private void atkEffect() {
		onAttack();
	}

	private void selfDestructSound() {
		playSound(ModSoundEvents.VIOLET_DAWN_SUICIDE.value(), 2.0F, 1.0F);
	}

	private void tryPlayWalkSound() {
		if (walkSoundCooldown > 0) {
			walkSoundCooldown--;
			return;
		}

		if (this.onGround() && this.getDeltaMovement().horizontalDistanceSqr() > 0.01) {
			playSound(ModSoundEvents.VIOLET_DAWN_WALK.value(), 0.5F, 1.0F);
			walkSoundCooldown = 60;
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.level().isClientSide || this.isDeadOrDying()) {
			return;
		}

		if (this.bulletAttackCooldown > 0) {
			this.bulletAttackCooldown--;
		}

		if (this.isBulletAttacking) {
			if (this.bulletAttackWindup > 0) {
				this.bulletAttackWindup--;

				if (this.bulletAttackWindup == 0) {
					executeBulletAttack();
					this.isBulletAttacking = false;
					this.bulletAttackCooldown = BULLET_ATTACK_COOLDOWN;
				}
			}
			return;
		}

		LivingEntity target = this.getTarget();
		if (target != null && target.isAlive() && !isCamp(target) && !this.isCharging) {
			tryStartBulletAttack();
		}

		if (!this.isCharging) {
			tryPlayWalkSound();
		}

		if (this.isCharging) {
			this.chargeTime++;

			if (!this.hasPlayedExplodeAnim && this.chargeTime >= SELF_DESTRUCT_ANIM_DELAY) {
				this.triggerAnim("controller", "self_destruct_explode");
				this.hasPlayedExplodeAnim = true;
			}

			float interruptThreshold = this.getMaxHealth() * INTERRUPT_DAMAGE_PERCENTAGE;
			if (this.chargeDamageTaken >= interruptThreshold) {
				interruptSelfDestruct();
				return;
			}

			if (this.chargeTime >= SELF_DESTRUCT_CHARGE_TIME) {
				selfDestructAttack();
				this.isCharging = false;
				this.chargeTime = 0;
				this.hasPlayedExplodeAnim = false;
				this.chargeDamageTaken = 0.0F;
				entityData.set(SELF_DESTRUCT_CHARGING, false);
				entityData.set(CHARGE_DAMAGE_TAKEN, 0.0F);
			}

			entityData.set(CHARGE_DAMAGE_TAKEN, this.chargeDamageTaken);
		}
	}

	private void tryStartBulletAttack() {
		if (this.isCharging || this.isBulletAttacking || this.bulletAttackCooldown > 0) {
			return;
		}

		LivingEntity target = this.getTarget();
		if (target == null || !target.isAlive() || isCamp(target)) {
			return;
		}

		float distance = this.distanceTo(target);

		boolean shouldUseBulletAttack = false;

		if (distance >= BULLET_ATTACK_RANGE_MIN && distance <= BULLET_ATTACK_RANGE_MAX) {
			shouldUseBulletAttack = true;
		} else if (distance < BULLET_ATTACK_RANGE_MIN) {
			shouldUseBulletAttack = this.random.nextFloat() < 0.3F;
		}

		if (shouldUseBulletAttack) {
			startBulletAttack();
		}
	}

	private void startBulletAttack() {
		this.isBulletAttacking = true;
		this.bulletAttackWindup = BULLET_ATTACK_Windup;

		triggerAnim("controller", "bullet_attack");
	}

	private void executeBulletAttack() {
		LivingEntity target = this.getTarget();
		if (target == null || !target.isAlive()) {
			return;
		}

		int bulletCount = BULLET_COUNT_MIN + this.random.nextInt(BULLET_COUNT_MAX - BULLET_COUNT_MIN + 1);

		Vec3 lookVec = this.getViewVector(1.0F);
		Vec3 startPos = this.position().add(0, 1.5, 0);

		for (int i = 0; i < bulletCount; i++) {
			float horizontalAngle = (this.random.nextFloat() - 0.5F) * BULLET_SPREAD_ANGLE;
			float verticalAngle = (this.random.nextFloat() - 0.3F) * 35.0F + 40.0F;

			double yaw = Math.toRadians(horizontalAngle);
			double pitch = Math.toRadians(verticalAngle);

			double cosYaw = Math.cos(yaw);
			double sinYaw = Math.sin(yaw);
			double cosPitch = Math.cos(pitch);
			double sinPitch = Math.sin(pitch);

			Vec3 bulletVec = new Vec3(
				(lookVec.x * cosYaw - lookVec.z * sinYaw) * cosPitch,
				lookVec.y * cosPitch + sinPitch,
				(lookVec.x * sinYaw + lookVec.z * cosYaw) * cosPitch
			);

			bulletVec = bulletVec.normalize().scale(FruitBullet.BULLET_SPEED);

			FruitBullet fruitBullet = new FruitBullet(this.level(), this, startPos.x, startPos.y, startPos.z);
			fruitBullet.setDeltaMovement(bulletVec);

			this.level().addFreshEntity(fruitBullet);
		}
	}

	private void interruptSelfDestruct() {
		this.isCharging = false;
		this.chargeTime = 0;
		this.hasPlayedExplodeAnim = false;
		this.chargeDamageTaken = 0.0F;
		this.selfDestructCounter = SELF_DESTRUCT_ATTACK_COUNT;
		entityData.set(SELF_DESTRUCT_CHARGING, false);
		entityData.set(CHARGE_DAMAGE_TAKEN, 0.0F);
		this.triggerAnim("controller", "self_destruct_interrupted");
	}

	@Override
	protected void actuallyHurt(DamageSource source, float damageAmount) {
		super.actuallyHurt(source, damageAmount);
		if (!this.level().isClientSide && source.getEntity() instanceof LivingEntity attacker && attacker.isAttackable()) {
			if (this.isCharging) {
				this.chargeDamageTaken += damageAmount;

				float interruptThreshold = this.getMaxHealth() * INTERRUPT_DAMAGE_PERCENTAGE;
				if (this.chargeDamageTaken >= interruptThreshold) {
					interruptSelfDestruct();
				}
			}
		}
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		var mainController = new AnimationController<>(this, "controller", 5, state -> {
			if (this.isCharging && !this.hasPlayedExplodeAnim) {
				return state.setAndContinue(RawAnimation.begin().thenLoop("self_destruct_charge"));
			}

			double movementSpeed = this.getDeltaMovement().horizontalDistanceSqr();
			if (movementSpeed > 0.0001) {
				return state.setAndContinue(RawAnimation.begin().thenLoop("walk"));
			} else {
				return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
			}
		});

		mainController.triggerableAnim("normal_attack", RawAnimation.begin().thenPlay("normal_attack"));
		mainController.triggerableAnim("bullet_attack", RawAnimation.begin().thenPlay("bullet_attack"));
		mainController.triggerableAnim("self_destruct_explode", RawAnimation.begin().thenPlay("self_destruct_explode"));
		mainController.triggerableAnim("self_destruct_interrupted", RawAnimation.begin().thenPlay("self_destruct_interrupted"));

		controllers.add(mainController);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	protected class FruitOfUnderstandingMeleeAttackGoal extends MeleeAttackGoal {
		private int attackCooldown = 0;
		private boolean isWindingUp = false;

		public FruitOfUnderstandingMeleeAttackGoal() {
			super(FruitOfUnderstanding.this, 0.4, true);
		}

		@Override
		public void tick() {
			super.tick();

			if (isWindingUp) {
				attackCooldown--;

				if (attackCooldown <= 0) {
					executeAttack();
					isWindingUp = false;
					attackCooldown = getAttackInterval();
				}
			} else if (attackCooldown > 0) {
				attackCooldown--;
			}
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity target) {
			if (attackCooldown <= 0 &&
				!isWindingUp &&
				canPerformNormalAttack() &&
				this.canPerformAttack(target)) {
				startAttackWindup();
			}
		}

		@Override
		protected boolean canPerformAttack(LivingEntity entity) {
			return this.isTimeToAttack() &&
				FruitOfUnderstanding.this.distanceToSqr(entity) <= NORMAL_ATK_RANGE * NORMAL_ATK_RANGE;
		}

		private void startAttackWindup() {
			triggerAnim("controller", "normal_attack");

			isWindingUp = true;
			attackCooldown = 15;
		}

		private void executeAttack() {
			LivingEntity target = FruitOfUnderstanding.this.getTarget();

			if (target != null && target.isAlive() &&
				FruitOfUnderstanding.this.distanceToSqr(target) <= NORMAL_ATK_RANGE * NORMAL_ATK_RANGE &&
				!isCamp(target)) {

				DamageSource damageSource = ModDamageSources.erosionDamage(FruitOfUnderstanding.this);
				if (target.hurt(damageSource, NORMAL_ATK_DMG)) {
					atkEffect();

					tryReduceSelfDestructCounter();
				}
			}
		}

		@Override
		public boolean canUse() {
			return canPerformNormalAttack() && super.canUse();
		}

		@Override
		public void stop() {
			super.stop();
			isWindingUp = false;
			attackCooldown = 0;
		}
	}

	public static class FruitBullet extends ThrowableProjectile implements GeoEntity {

		public static final float BULLET_DAMAGE = 8.0F;
		public static final float BULLET_SPEED = 0.6F;
		public static final float GRAVITY = 0.0005F;

		private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

		public FruitBullet(EntityType<? extends ThrowableProjectile> entityType, Level level) {
			super(entityType, level);
		}

		public FruitBullet(Level level, LivingEntity shooter, double x, double y, double z) {
			super(ProjectileEntityTypes.FRUIT_OF_UNDERSTANDING_BULLET.get(), shooter, level);
			this.setPos(x, y, z);
		}

		@Override
		protected void defineSynchedData(SynchedEntityData.Builder builder) {
		}

		@Override
		public void tick() {
			super.tick();

			Vec3 movement = this.getDeltaMovement();

			if (movement.length() > 0) {
				this.setRot(
					(float) (Math.atan2(movement.z, movement.x) * 180.0 / Math.PI) - 90.0f,
					(float) (Math.atan2(movement.y, movement.horizontalDistance()) * 180.0 / Math.PI)
				);
			}

//    if (this.level().isClientSide) {
//      return;
//    }

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

			@Nullable Entity target = result.getEntity();
			Entity shooter = this.getOwner();

			//noinspection ConstantValue
			if (shooter instanceof IOrdealsEntity fruit && target != null && !fruit.isCamp(target)) {
				target.hurt(ModDamageSources.createDamage(ModDamageTypes.EROSION, this, shooter), BULLET_DAMAGE);
			}

			discardBullet();
		}

		@Override
		protected void onHitBlock(BlockHitResult result) {
			super.onHitBlock(result);
			discardBullet();
		}

		public void discardBullet() {
			if (!this.level().isClientSide) {
				this.discard();
			} else {
				// TODO 添加子弹消失效果，例如粒子，音效
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
	}
}
