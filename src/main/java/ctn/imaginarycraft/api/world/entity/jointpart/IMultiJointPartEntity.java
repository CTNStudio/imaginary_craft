package ctn.imaginarycraft.api.world.entity.jointpart;

import net.minecraft.world.entity.LivingEntity;

/**
 * 多关节部件实体接口
 * <p>
 * 用于管理拥有多个关节部件的生物实体，通过 {@link JointPartManager} 提供对关节部件的完整管理能力
 *
 * @param <T> 关节部件实体类型，必须实现 {@link IJointPartEntity} 接口
 */
public interface IMultiJointPartEntity<T extends LivingEntity & IJointPartEntity<?>> {
	/**
	 * 获取关节部件管理器
	 * <p>
	 * 通过管理器可以进行所有子部件的增删改查操作，包括：
	 * <ul>
	 *   <li>按索引、名称、ID查询部件</li>
	 *   <li>添加、替换、移除部件</li>
	 *   <li>容量控制和包含检查</li>
	 * </ul>
	 *
	 * @return 关节部件管理器实例
	 */
	JointPartManager<T> getJointPartManager();
}
