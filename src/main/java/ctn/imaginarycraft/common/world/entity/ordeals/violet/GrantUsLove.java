package ctn.imaginarycraft.common.world.entity.ordeals.violet;

import ctn.imaginarycraft.api.world.entity.IBehaviorTreeMob;
import ctn.imaginarycraft.api.world.entity.IPatch;
import ctn.imaginarycraft.api.world.entity.ISkillExpand;
import ctn.imaginarycraft.api.world.entity.ISpawnByEgg;
import ctn.imaginarycraft.api.world.entity.ai.behavior.BTFactory;
import ctn.imaginarycraft.api.world.entity.ai.behavior.BTNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.BTRoot;
import ctn.imaginarycraft.api.world.entity.ai.behavior.composite.ParallelNode;
import ctn.imaginarycraft.api.world.entity.ai.behavior.condition.ConditionBT;
import ctn.imaginarycraft.api.world.entity.ai.behavior.condition.TargetExistCondition;
import ctn.imaginarycraft.api.world.entity.skill.MobSkill;
import ctn.imaginarycraft.init.ModSoundEvents;
import ctn.imaginarycraft.init.epicfight.animations.GrantUsLoveAnimations;
import ctn.imaginarycraft.init.world.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.registry.entries.EpicFightAttributes;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

/**
 * 英文编号:ordeals--violet noon
 * <p>
 * 中文编号:考验--紫罗兰色正午
 * <p>
 * 英文名:Grant Us Love
 * <p>
 * 中文名: 请给我们爱
 * <p>
 * 2025/12/22 尘昨喧
 *
 * <h2>TODO 待办事项:</h2>
 * <ul>
 *   <li>技能或大招剩余时间要持久化</li>
 *   <li>需要免疫中毒,细雪,火焰,漂浮</li>
 * </ul>
 */
public class GrantUsLove extends Mob implements IOrdealsVioletEntity, ISpawnByEgg, IBehaviorTreeMob<GrantUsLove>, Enemy, IPatch<GrantUsLovePatch>, ISkillExpand {

	private GrantUsLovePatch patch;

	private final MobSkill ultimateSkill = new MobSkill("ultimate", 100) {

		@Override
		public void cdEnd() {

		}

		@Override
		public void launch() {
//			getPatch().playAnimationSynchronized();
		}
	};

	public GrantUsLove(EntityType<? extends GrantUsLove> entityType, Level level) {
		super(entityType, level);
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

	@Override
	public void registerGoals() {
		super.registerGoals();
		IOrdealsVioletEntity.super.registerGoals();
		targetSelector.addGoal(2, createBehaviorTree());
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
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
	}

	@Override
	public void onSpawnByEgg() {
		getPatch().playAnimationSynchronized(GrantUsLoveAnimations.EXTEND, 0);
	}

	@Override
	public boolean canTarget(Entity entity) {
		return IOrdealsVioletEntity.super.canTarget(entity);
	}

	@Override
	public BTRoot<GrantUsLove> createBehaviorTree() {
		return new GrantUsLoveBT(this);
	}

	@Override
	protected void actuallyHurt(DamageSource source, float damageAmount) {
		super.actuallyHurt(source, damageAmount);
	}

	//region 声音（音效集合）
	private void crashAtkSound() {
		playSound(ModSoundEvents.VIOLET_NOON_DOWN.value(), 2.0F, 1.0F);
	}
	//endregion

	//region 基本属性方法
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
	public GrantUsLovePatch getPatch() {
		if (patch == null) {
			patch = EpicFightCapabilities.getEntityPatch(this, GrantUsLovePatch.class);
		}
		return patch;
	}
	//endregion

	@Override
	public void tick() {
		super.tick();
		ultimateSkill.tick();
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		readData(compound);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		addData(compound);
	}

	@Override
	public void readData(CompoundTag compound) {
		ultimateSkill.readData(compound);
	}

	@Override
	public void addData(CompoundTag compound) {
		ultimateSkill.addData(compound);
	}

	public static class GrantUsLoveBT extends BTRoot<GrantUsLove> {

		public GrantUsLoveBT(GrantUsLove mob) {
			super(mob);
		}

		@Override
		protected @NotNull BTNode createBehaviorTree() {
			return BTFactory.parallel(ParallelNode.Policy.REQUIRE_ALL, ParallelNode.Policy.REQUIRE_ALL)
				.addChild(BTFactory.infinite(BTFactory.selector()
//        .addWithCondition(, )
						// 目标不存在
						.addWithCondition(ConditionBT.not(new TargetExistCondition(this.mob)), BTFactory.sequence())
						// 目标存在
						.addChild(BTFactory.infinite(BTFactory.selector()
//						// 释放大招
//						.addWithCondition(ConditionBT.and(new DistanceLowerThanCondition(this.mob, TARGET_ATK_RADIUS)), BTFactory.sequence()
//							// 设置基本
//							.addChild(BTFactory.success(() -> {
//							}))
//							// 等待5秒
//							.addChild(BTFactory.sequence().addChild(BTFactory.wait(100))
////                .addWithCondition(,)
//							))
						)))
				)
				// 其他处理例如：技能冷却
				.addChild(BTFactory.infinite(BTFactory.success(() -> {
//					if (mob.normalAtkCd != 0) {
//						mob.normalAtkCd--;
//					}
//					if (mob.crashAtkCd != 0) {
//						mob.crashAtkCd--;
//					}
				})));
		}
	}
}
