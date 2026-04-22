package ctn.imaginarycraft.core.registry.epicfight;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.epicfight.animations.ModAnimations;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import yesman.epicfight.api.animation.AnimationManager;

/**
 * Epic Fight模组集成注册表类
 * <p>
 * 负责处理与Epic Fight模组的集成，包括武器能力、动画、实体补丁等内容的注册。
 * 该类通过事件总线订阅相关事件，完成各种Epic Fight相关组件的初始化工作。
 * </p>
 *
 * @author ImaginaryCraft Team
 * @version 1.0
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class EpicFightRegistry {
	/**
	 * 响应FML通用设置事件，执行延迟的注册任务
	 * <p>
	 * 该方法会在模组加载时被调用，通过enqueueWork将以下注册任务加入队列：
	 * </p>
	 * <ul>
	 *   <li>按类别注册武器类型</li>
	 *   <li>注册实体类型的骨架（Armature）</li>
	 *   <li>添加各种Epic Fight扩展能力注册表</li>
	 * </ul>
	 *
	 * @param event FML通用设置事件对象，用于管理初始化任务的执行
	 */
	@SubscribeEvent
	public static void register(FMLCommonSetupEvent event) {
		event.enqueueWork(ItemRegistry::registerWeaponTypesByClass);
		event.enqueueWork(EntityTypeRegistry::registerEntityTypeArmatures);
		event.enqueueWork(ItemRegistry::register);
	}

	/**
	 * 响应动画注册事件，注册自定义动画
	 * <p>
	 * 该方法会在Epic Fight动画管理器初始化时被调用，
	 * 通过ModAnimations类的build方法构建并注册所有自定义动画。
	 * </p>
	 *
	 * @param event 动画注册事件对象，提供动画构建器接口
	 */
	@SubscribeEvent
	public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
		event.newBuilder(ImaginaryCraft.ID, ModAnimations::build);
	}
}
