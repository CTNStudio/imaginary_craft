package ctn.imaginarycraft.common.world.entity.ordeals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public interface IOrdealsEntity {
	default void registerGoals() {
		getTargetSelector().addGoal(1, new NearestAttackableTargetGoal<>(getMob(), Player.class, true, this::canTarget));
		getTargetSelector().addGoal(2, new NearestAttackableTargetGoal<>(getMob(), Mob.class, true, this::canTarget));
	}

	/**
	 * 目标选择
	 */
	default boolean canTarget(Entity entity) {
		return this.isValidTarget(entity);
	}

	/**
	 * 判断是否是可以攻击目标
	 * TODO 还需要处理以便支持斗蛐蛐
	 */
	default boolean isValidTarget(Entity entity) {
		if (entity == this) return false;

		if (!entity.isAlive() || !entity.isAttackable()) {
			return false;
		}

		if (entity instanceof Player player) {
			return !player.isCreative() && !player.isSpectator();
		}

		return !isCamp(entity);
	}

	/**
	 * 判断是否是同阵营
	 * TODO 还需要处理以便支持斗蛐蛐
	 */
	default boolean isCamp(Entity entity) {
		return getMob().getType().equals(entity.getType());
	}

	default GoalSelector getTargetSelector() {
		return getMob().targetSelector;
	}

	default GoalSelector getGoalSelector() {
		return getMob().goalSelector;
	}

	default Mob getMob() {
		return (Mob) this;
	}
}
