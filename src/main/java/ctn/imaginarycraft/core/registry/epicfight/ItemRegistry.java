package ctn.imaginarycraft.core.registry.epicfight;

import ctn.imaginarycraft.api.epicfight.capabilities.MobBuilderEntrys;
import ctn.imaginarycraft.api.epicfight.capabilities.ModExCapDataSets;
import ctn.imaginarycraft.api.epicfight.capabilities.ModMovesets;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.core.ImaginaryCraftConstants;
import ctn.imaginarycraft.init.world.item.ego.EgoWeaponItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.registry.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.api.ex_cap.modules.assets.Builders;
import yesman.epicfight.api.ex_cap.modules.core.data.BuilderEntry;
import yesman.epicfight.api.ex_cap.modules.core.events.*;
import yesman.epicfight.api.ex_cap.modules.core.managers.BuilderManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets;

import java.util.Set;
import java.util.function.Function;

public final class ItemRegistry {
	/**
	 * 添加Epic Fight扩展能力的各种注册表事件监听器
	 * <p>
	 * 该方法向Epic Fight的事件钩子系统注册多个回调函数，用于处理：
	 * </p>
	 * <ul>
	 *   <li>扩展能力数据创建</li>
	 *   <li>扩展能力构建器创建</li>
	 *   <li>条件判断器注册</li>
	 *   <li>动作集注册</li>
	 *   <li>扩展能力数据填充</li>
	 *   <li>武器能力预设注册</li>
	 * </ul>
	 */
	static void register() {
		EpicFightEventHooks.Registry.EX_CAP_DATA_CREATION.registerEvent(ItemRegistry::registerData, 1);
		EpicFightEventHooks.Registry.EX_CAP_BUILDER_CREATION.registerEvent(ItemRegistry::registerExCapBuilders, 1);
		EpicFightEventHooks.Registry.EX_CAP_CONDITIONAL_REGISTRATION.registerEvent(ItemRegistry::registerConditionals, 1);
		EpicFightEventHooks.Registry.EX_CAP_MOVESET_REGISTRY.registerEvent(ItemRegistry::registerExCapMovesets, 1);
		EpicFightEventHooks.Registry.EX_CAP_DATA_POPULATION.registerEvent(ItemRegistry::registerExCapMethods, 1);
		EpicFightEventHooks.Registry.WEAPON_CAPABILITY_PRESET.registerEvent(ItemRegistry::registerWeaponCapabilities, 1);
	}

	/**
	 * 注册扩展能力构建器的数据关联关系
	 * <p>
	 * 将各种武器类型的构建器ID与其对应的数据集ID进行绑定，
	 * 使Epic Fight能够正确识别和处理不同武器类型的能力数据。
	 * </p>
	 *
	 * @param event 扩展能力构建器填充事件对象，用于注册数据关联
	 */
	private static void registerExCapMethods(ExCapabilityBuilderPopulationEvent event) {
		event.registerData(MobBuilderEntrys.HAMMER.id(), ModExCapDataSets.HAMMER.id());
		event.registerData(MobBuilderEntrys.MACE.id(), ModExCapDataSets.MACE.id());
		event.registerData(MobBuilderEntrys.CANNON.id(), ModExCapDataSets.CANNON.id());
		event.registerData(MobBuilderEntrys.GUN.id(), ModExCapDataSets.GUN.id());
		event.registerData(MobBuilderEntrys.PISTOL.id(), ModExCapDataSets.PISTOL.id());
		event.registerData(MobBuilderEntrys.RIFLE.id(), ModExCapDataSets.RIFLE.id());
	}

	/**
	 * 注册扩展能力数据集
	 * <p>
	 * 向Epic Fight系统注册所有自定义的扩展能力数据集，
	 * 这些数据集定义了各种武器类型的行为属性和配置。
	 * </p>
	 *
	 * @param event 扩展能力数据注册事件对象，用于批量添加数据集
	 */
	private static void registerData(ExCapDataRegistrationEvent event) {
		event.addData(
			ModExCapDataSets.HAMMER,
			ModExCapDataSets.MACE,
			ModExCapDataSets.CANNON,
			ModExCapDataSets.GUN,
			ModExCapDataSets.PISTOL,
			ModExCapDataSets.RIFLE
		);
	}

	/**
	 * 注册扩展能力构建器
	 * <p>
	 * 向Epic Fight系统注册所有自定义的扩展能力构建器，
	 * 这些构建器用于创建和初始化不同武器类型的能力实例。
	 * </p>
	 *
	 * @param event 扩展能力构建器创建事件对象，用于批量添加构建器
	 */
	private static void registerExCapBuilders(ExCapBuilderCreationEvent event) {
		event.addBuilder(
			MobBuilderEntrys.HAMMER,
			MobBuilderEntrys.MACE,
			MobBuilderEntrys.CANNON,
			MobBuilderEntrys.GUN,
			MobBuilderEntrys.PISTOL,
			MobBuilderEntrys.RIFLE
		);
	}

	/**
	 * 注册条件判断器
	 * <p>
	 * 用于注册自定义的条件判断逻辑，控制武器能力在特定条件下的激活或禁用。
	 * 当前未启用任何条件判断器（已注释）。
	 * </p>
	 *
	 * @param event 条件判断器注册事件对象
	 */
	private static void registerConditionals(ConditionalRegistryEvent event) {
		event.addConditional(
//			ModMainConditionals.HAMMER,
		);
	}

	/**
	 * 注册扩展能力动作集
	 * <p>
	 * 向Epic Fight系统注册所有自定义武器的动作集合，
	 * 包括攻击动画、连击模式等战斗行为定义。
	 * </p>
	 *
	 * @param event 扩展能力动作集注册事件对象，用于批量添加动作集
	 */
	private static void registerExCapMovesets(ExCapMovesetRegistryEvent event) {
		event.addMoveSet(
			ModMovesets.HAMMER,
			ModMovesets.MACE,
			ModMovesets.CANNON,
			ModMovesets.GUN,
			ModMovesets.PISTOL,
			ModMovesets.RIFLE
		);
	}

	/**
	 * 按类别注册所有武器类型到Epic Fight系统
	 * <p>
	 * 该方法遍历所有定义的武器物品集，将它们与对应的Epic Fight武器构建器关联，
	 * 使游戏能够正确识别和处理各种武器的战斗特性。
	 * </p>
	 */
	static void registerWeaponTypesByClass() {
		register(ImaginaryCraftConstants.GUN, MobBuilderEntrys.GUN);
		register(ImaginaryCraftConstants.PISTOL, MobBuilderEntrys.PISTOL);
		register(ImaginaryCraftConstants.RIFLE, MobBuilderEntrys.RIFLE);
		register(ImaginaryCraftConstants.CANNON, MobBuilderEntrys.CANNON);
		register(ImaginaryCraftConstants.CROSSBOW, Builders.CROSSBOW);
		register(ImaginaryCraftConstants.BOW, Builders.BOW);
		register(ImaginaryCraftConstants.KNIFE, Builders.DAGGER);
		register(ImaginaryCraftConstants.HAMMER, MobBuilderEntrys.HAMMER);
		register(ImaginaryCraftConstants.FIST, Builders.FIST);
		register(ImaginaryCraftConstants.SPEAR, Builders.SPEAR);
		register(ImaginaryCraftConstants.AXE, Builders.AXE);
		register(ImaginaryCraftConstants.MACE, MobBuilderEntrys.MACE);
		register(ImaginaryCraftConstants.SWORDS, Builders.SWORD);
		register(EgoWeaponItems.RED_EYES_TACHI.get(), (item) -> WeaponCapabilityPresets.exCapRegistration(BuilderManager.getEntry(Builders.TACHI.id()), item));
	}

	/**
	 * 注册武器能力预设
	 * <p>
	 * 用于向Epic Fight系统导出和管理武器能力预设。
	 * 当前未启用任何功能（已注释）。
	 * </p>
	 *
	 * @param event 武器能力预设注册事件对象
	 */
	private static void registerWeaponCapabilities(WeaponCapabilityPresetRegistryEvent event) {
//		BuilderManager.acceptExport(event);
	}


	/**
	 * 注册物品集的武器能力（使用预定义的构建器条目）
	 * <p>
	 * 这是一个重载方法，将物品集与指定的构建器条目关联，
	 * 内部会转换为函数式接口后调用另一个register方法。
	 * </p>
	 *
	 * @param items        待注册的物品集合，通常为同类型武器
	 * @param builderEntry 构建器条目，定义了武器的基本能力和行为
	 */
	private static void register(Set<DeferredItem<? extends Item>> items, BuilderEntry builderEntry) {
		register(items, (item) -> WeaponCapabilityPresets.exCapRegistration(BuilderManager.getEntry(builderEntry.id()), item));
	}

	/**
	 * 注册物品集的武器能力（使用自定义构建器函数）
	 * <p>
	 * 遍历物品集合，将每个物品转换为Item对象后逐个注册其武器能力。
	 * </p>
	 *
	 * @param items   延迟加载的物品集合
	 * @param builder 构建器函数，接收Item对象并返回对应的CapabilityItem.Builder实例
	 */
	private static void register(Set<DeferredItem<? extends Item>> items, Function<Item, ? extends CapabilityItem.Builder<?>> builder) {
		for (DeferredItem<? extends Item> item : items) {
			Item item1 = item.asItem();
			register(item1, builder);
		}
	}

	/**
	 * 注册单个物品的武器能力
	 * <p>
	 * 这是武器能力注册的核心方法，负责：
	 * </p>
	 * <ol>
	 *   <li>使用构建器函数创建武器能力实例</li>
	 *   <li>验证能力实例是否成功创建，失败则记录警告日志并跳过</li>
	 *   <li>将成功创建的能力注册到Epic Fight的物品能力提供者映射中</li>
	 * </ol>
	 *
	 * @param item1   要注册能力的物品对象
	 * @param builder 构建器函数，用于创建该物品的武器能力
	 */
	private static void register(Item item1, Function<Item, ? extends CapabilityItem.Builder<?>> builder) {
		CapabilityItem capability = builder.apply(item1).build();
		if (capability == null) {
			ImaginaryCraft.LOGGER.warn("Failed to build weapon capability for item: {}, skipping registration", item1.getDescriptionId());
			return;
		}

		EpicFightCapabilities.ITEM_CAPABILITY_PROVIDER.put(item1, capability);
	}
}
