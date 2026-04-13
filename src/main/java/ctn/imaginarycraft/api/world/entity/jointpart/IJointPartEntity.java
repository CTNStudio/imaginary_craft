package ctn.imaginarycraft.api.world.entity.jointpart;


import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface IJointPartEntity<T extends LivingEntity & IMultiJointPartEntity<?>> {

	/**
	 * 获取主体实体
	 */
	T getParent();

	/**
	 * 获取名称
	 */
	String getPartName();

	/**
	 * 获取包含的关节
	 */
	List<String> getJointsNames();

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
