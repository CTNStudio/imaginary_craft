package ctn.imaginarycraft.api.world.entity.jointpart;


import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.Joint;

public interface IJointPartEntity<T extends LivingEntity> {

	/**
	 * 主体实体
	 */
	T getParent();

	/**
	 * 获取包含的关节
	 */
	Joint[] getJoints();

	/**
	 * 是否可从破坏状态恢复
	 */
	boolean isItRecoverable();

	/**
	 * 是否可破坏
	 */
	boolean isDestructible();

	/**
	 * 是否可传递伤害
	 */
	boolean isTransmittingDamage();

	/**
	 * 破坏
	 *
	 * @return 是否破坏成功
	 */
	boolean destructible();

	/**
	 * 从破坏状态恢复
	 *
	 * @return 是否恢复成功
	 */
	boolean recoverable();
}
