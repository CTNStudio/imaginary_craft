package ctn.singularity.lib.core;

import ctn.singularity.lib.init.LibAttributes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import static net.minecraft.world.phys.HitResult.Type.ENTITY;

@EventBusSubscriber(modid = LibMain.LIB_ID)
public final class LibEntityAttribute {

	/**
	 * 注册实体属性
	 */
	@SubscribeEvent
	public static void entityAttribute(EntityAttributeCreationEvent event) {
	}

  /**
   *  添加或修改属性 等级在{@link LibCapability}类注册
   */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void addAttribute(EntityAttributeModificationEvent event) {
    BuiltInRegistries.ENTITY_TYPE.stream().forEach(entityType -> {
      entityType.getBaseClass().
      if (entityType instanceof EntityType<? extends LivingEntity> type ) {

      }
      lcAttributesResistance(event, entityType, 0.0, 0.0, 0.0, 0);
    });
    addAttributes(event, EntityType.PLAYER);
	}

  /**
   * 脑叶属性抗性
   * @param physics 物理
   * @param rationality 精神
   * @param erosion 侵蚀
   * @param theSoul 灵魂
   */
	private static void lcAttributesResistance(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType, double physics, double rationality, double erosion, double theSoul) {
		event.add(entityType, LibAttributes.PHYSICS_VULNERABLE, physics);
		event.add(entityType, LibAttributes.SPIRIT_VULNERABLE, rationality);
		event.add(entityType, LibAttributes.EROSION_VULNERABLE, erosion);
		event.add(entityType, LibAttributes.THE_SOUL_VULNERABLE, theSoul);
	}

	private static void addAttributes(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType) {
		event.add(entityType, LibAttributes.MAX_RATIONALITY);
		event.add(entityType, LibAttributes.RATIONALITY_NATURAL_RECOVERY_RATE);
		event.add(entityType, LibAttributes.RATIONALITY_RECOVERY_AMOUNT);
		event.add(entityType, LibAttributes.INFORMATION);
    event.add(entityType, LibAttributes.PHYSICS_VULNERABLE);
    event.add(entityType, LibAttributes.SPIRIT_VULNERABLE);
    event.add(entityType, LibAttributes.EROSION_VULNERABLE);
    event.add(entityType, LibAttributes.THE_SOUL_VULNERABLE);
	}
}
