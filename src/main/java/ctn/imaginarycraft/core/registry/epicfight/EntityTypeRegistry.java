package ctn.imaginarycraft.core.registry.epicfight;

import ctn.imaginarycraft.client.renderer.entity.GrantUsLovePatchRenderer;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.GrantUsLovePatch;
import ctn.imaginarycraft.init.epicfight.animmodels.ModArmatures;
import ctn.imaginarycraft.init.world.entity.OrdealsEntityTypes;
import net.neoforged.fml.loading.FMLEnvironment;
import yesman.epicfight.api.client.event.EpicFightClientEventHooks;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.gameasset.Armatures;

public final class EntityTypeRegistry {
	/**
	 * 注册实体类型的骨架映射
	 * <p>
	 * 将特定实体类型与其对应的骨架模型进行绑定，
	 * 使Epic Fight能够正确渲染实体的动画效果。
	 * </p>
	 */
	static void registerEntityTypeArmatures() {
		Armatures.registerEntityTypeArmature(OrdealsEntityTypes.GRANT_US_LOVE.get(), ModArmatures.GRANT_US_LOVE);
	}

	public static void register() {
		if (FMLEnvironment.dist.isClient()) {
			registerPatchedEntityRenderers();
		}
		registerEntityPatch();
	}

	/**
	 * 注册补丁实体的渲染器
	 * <p>
	 * 仅在客户端环境中执行，为需要特殊渲染的实体注册自定义渲染器。
	 * 该方法会向Epic Fight客户端事件钩子注册一个事件监听器，
	 * </p>
	 */
	private static void registerPatchedEntityRenderers() {
		EpicFightClientEventHooks.Registry.ADD_PATCHED_ENTITY.registerEvent(event -> {
			event.addPatchedEntityRenderer(OrdealsEntityTypes.GRANT_US_LOVE.get(), entityType -> new GrantUsLovePatchRenderer(event.getContext()));
		});
	}

	/**
	 * 注册实体补丁
	 * <p>
	 * 向Epic Fight系统注册实体补丁，使普通实体能够获得Epic Fight的战斗能力。
	 * </p>
	 */
	private static void registerEntityPatch() {
		EpicFightEventHooks.Registry.ENTITY_PATCH.registerEvent(event -> {
			event.registerEntityPatch(OrdealsEntityTypes.GRANT_US_LOVE.get(), GrantUsLovePatch::new);
		});
	}
}
