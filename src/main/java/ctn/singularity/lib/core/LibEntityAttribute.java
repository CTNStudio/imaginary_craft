package ctn.singularity.lib.core;

import ctn.singularity.lib.init.LibAttributes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

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
    // 对所有实体打入基础属性
    BuiltInRegistries.ENTITY_TYPE.stream().forEach(entityType -> {
      if (!entityType.getBaseClass().isInstance(LivingEntity.class)) {
        return;
      }
      lcAttributesResistance(event, (EntityType<? extends LivingEntity>) entityType, 0.0, 0.0, 0.0, 0);
    });

    addPlayerAttributes(event, EntityType.PLAYER);
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

  /**
   * 脑叶属性抗性
   */
	private static void lcAttributesResistance(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType) {
		event.add(entityType, LibAttributes.PHYSICS_VULNERABLE);
		event.add(entityType, LibAttributes.SPIRIT_VULNERABLE);
		event.add(entityType, LibAttributes.EROSION_VULNERABLE);
		event.add(entityType, LibAttributes.THE_SOUL_VULNERABLE);
	}

  /**
   * 添加玩家属性
   */
	private static void addPlayerAttributes(EntityAttributeModificationEvent event, EntityType<? extends Player> entityType) {
		event.add(entityType, LibAttributes.MAX_RATIONALITY);
		event.add(entityType, LibAttributes.RATIONALITY_NATURAL_RECOVERY_RATE);
		event.add(entityType, LibAttributes.RATIONALITY_RECOVERY_AMOUNT);

		event.add(entityType, LibAttributes.INFORMATION);

    event.add(entityType, LibAttributes.PHYSICS_VULNERABLE);
    event.add(entityType, LibAttributes.SPIRIT_VULNERABLE);
    event.add(entityType, LibAttributes.EROSION_VULNERABLE);
    event.add(entityType, LibAttributes.THE_SOUL_VULNERABLE);

    event.add(entityType, LibAttributes.FORTITUDE_POINTS);
    event.add(entityType, LibAttributes.PRUDENCE_POINTS);
    event.add(entityType, LibAttributes.TEMPERANCE_POINTS);
    event.add(entityType, LibAttributes.JUSTICE_POINTS);
	}
}
