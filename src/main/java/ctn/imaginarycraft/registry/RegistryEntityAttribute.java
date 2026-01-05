package ctn.imaginarycraft.registry;

import ctn.imaginarycraft.common.entity.abnormalities.ordeals.violet.GrantUsLove;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.entiey.AbnormalitiesEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class RegistryEntityAttribute {

  /**
   * 注册实体属性
   */
  @SubscribeEvent
  public static void registry(EntityAttributeCreationEvent event) {
    event.put(AbnormalitiesEntityTypes.GRANT_US_LOVE.get(), GrantUsLove.createAttributes().build());

  }

  /**
   * 添加或修改属性 等级在{@link RegistryCapability}类注册
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void registry(EntityAttributeModificationEvent event) {
    // 对所有实体打入基础属性
    event.getTypes().forEach(entityType -> lcAttributesVulnerable(event, entityType));
    vanilla(event);
    addPlayerAttributes(event, EntityType.PLAYER);
  }

  private static void vanilla(EntityAttributeModificationEvent event) {
    lcAttributesVulnerable(event, EntityType.WARDEN, 0.6, 1.2, 0.8, 0.2);
    lcAttributesVulnerable(event, EntityType.ENDER_DRAGON, 0.5, 0.5, 0.5, 0.5);
    lcAttributesVulnerable(event, EntityType.WITHER, 0.5, 0.7, -1.0, 1.0);
    lcAttributesVulnerable(event, EntityType.IRON_GOLEM, 0.5, 0.6, 1.5, 1.0);
    lcAttributesVulnerable(event, EntityType.ELDER_GUARDIAN, 0.5, 0.8, 0.9, 1.2);
    lcAttributesVulnerable(event, EntityType.RAVAGER, 0.5, 1.0, 1.5, 1.3);
    lcAttributesVulnerable(event, EntityType.GUARDIAN, 0.5, 0.8, 0.9, 1.2);
    lcAttributesVulnerable(event, EntityType.ENDERMAN, 0.8, 0.5, 1.2, 1.5);
    lcAttributesVulnerable(event, EntityType.GHAST, 0.5, 0.5, 1.2, 1.5);
    lcAttributesVulnerable(event, EntityType.HOGLIN, 0.8, 1.2, 1.1, 1.2);
    lcAttributesVulnerable(event, EntityType.PIGLIN_BRUTE, 0.6, 1.3, 1.0, 1.1);
    lcAttributesVulnerable(event, EntityType.SHULKER, 0.2, 1.5, 1.0, 1.1);
    lcAttributesVulnerable(event, EntityType.ZOGLIN, 0.5, 1.2, 1.2, 1.3);
    lcAttributesVulnerable(event, EntityType.EVOKER, 1.0, 1.2, 1.3, 1.3);
    lcAttributesVulnerable(event, EntityType.VINDICATOR, 0.8, 1.2, 1.3, 1.3);
    lcAttributesVulnerable(event, EntityType.WITCH, 1.2, 1.1, 1.0, 1.3);
    lcAttributesVulnerable(event, EntityType.WITHER_SKELETON, 0.8, 0.8, -1.0, 1.1);
    lcAttributesVulnerable(event, EntityType.BLAZE, 0.7, 0.8, 1.3, 1.2);
    lcAttributesVulnerable(event, EntityType.BOGGED, 1.0, 0.5, 0.7, 1.0);
    lcAttributesVulnerable(event, EntityType.SKELETON, 0.9, 0.6, 0.8, 1.0);
    lcAttributesVulnerable(event, EntityType.STRAY, 0.8, 0.6, 0.8, 1.0);
    lcAttributesVulnerable(event, EntityType.ZOMBIE, 0.7, 0.8, 0.9, 1.1);
    lcAttributesVulnerable(event, EntityType.ZOMBIFIED_PIGLIN, 0.6, 0.7, 0.5, 1.3);
    lcAttributesVulnerable(event, EntityType.DROWNED, 0.8, 0.8, 1.0, 1.1);
    lcAttributesVulnerable(event, EntityType.BREEZE, 0.5, 0.8, 1.3, 1.2);
    lcAttributesVulnerable(event, EntityType.CREEPER, 1.2, 0.8, 1.2, 1.2);
    lcAttributesVulnerable(event, EntityType.HUSK, 0.6, 0.6, 0.8, 1.2);
    lcAttributesVulnerable(event, EntityType.MAGMA_CUBE, 0.4, 0.6, 1.4, 1.2);
    lcAttributesVulnerable(event, EntityType.PHANTOM, 0.6, 1.0, 0.8, 1.3);
    lcAttributesVulnerable(event, EntityType.ENDERMITE, 0.9, 1.2, 1.1, 1.3);
    lcAttributesVulnerable(event, EntityType.SILVERFISH, 0.8, 1.2, 1.3, 1.3);
    lcAttributesVulnerable(event, EntityType.VEX, 0.8, 1.3, 1.1, 1.5);
    lcAttributesVulnerable(event, EntityType.PILLAGER, 0.8, 1.2, 1.3, 1.3);
    lcAttributesVulnerable(event, EntityType.PIGLIN, 0.8, 1.2, 1.3, 1.2);
    lcAttributesVulnerable(event, EntityType.SPIDER, 0.7, 1.1, 1.3, 1.1);
    lcAttributesVulnerable(event, EntityType.CAVE_SPIDER, 0.7, 1.1, 1.0, 1.1);
    lcAttributesVulnerable(event, EntityType.SLIME, 0.5, 0.7, 1.2, 1.1);
  }

  /**
   * 脑叶属性抗性
   *
   * @param physics 物理
   * @param spirit  精神
   * @param erosion 侵蚀
   * @param theSoul 灵魂
   */
  private static void lcAttributesVulnerable(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType, double physics, double spirit, double erosion, double theSoul) {
    event.add(entityType, ModAttributes.PHYSICS_VULNERABLE, physics);
    event.add(entityType, ModAttributes.SPIRIT_VULNERABLE, spirit);
    event.add(entityType, ModAttributes.EROSION_VULNERABLE, erosion);
    event.add(entityType, ModAttributes.THE_SOUL_VULNERABLE, theSoul);
  }

  /**
   * 脑叶属性抗性
   */
  private static void lcAttributesVulnerable(EntityAttributeModificationEvent event, EntityType<? extends LivingEntity> entityType) {
    if (!event.has(entityType, ModAttributes.PHYSICS_VULNERABLE)) {
      event.add(entityType, ModAttributes.PHYSICS_VULNERABLE);
    }
    if (!event.has(entityType, ModAttributes.SPIRIT_VULNERABLE)) {
      event.add(entityType, ModAttributes.SPIRIT_VULNERABLE);
    }
    if (!event.has(entityType, ModAttributes.EROSION_VULNERABLE)) {
      event.add(entityType, ModAttributes.EROSION_VULNERABLE);
    }
    if (!event.has(entityType, ModAttributes.THE_SOUL_VULNERABLE)) {
      event.add(entityType, ModAttributes.THE_SOUL_VULNERABLE);
    }
  }

  /**
   * 添加玩家属性
   */
  private static void addPlayerAttributes(EntityAttributeModificationEvent event, EntityType<? extends Player> entityType) {
    event.add(entityType, ModAttributes.MAX_RATIONALITY);
    event.add(entityType, ModAttributes.RATIONALITY_NATURAL_RECOVERY_WAIT_TIME);
    event.add(entityType, ModAttributes.RATIONALITY_RECOVERY_AMOUNT);

    event.add(entityType, ModAttributes.INTELLIGENCE_DEPARTMENT_ACTIVATION);

    event.add(entityType, ModAttributes.PHYSICS_VULNERABLE);
    event.add(entityType, ModAttributes.SPIRIT_VULNERABLE);
    event.add(entityType, ModAttributes.EROSION_VULNERABLE);
    event.add(entityType, ModAttributes.THE_SOUL_VULNERABLE);

    event.add(entityType, ModAttributes.FORTITUDE_POINTS);
    event.add(entityType, ModAttributes.PRUDENCE_POINTS);
    event.add(entityType, ModAttributes.TEMPERANCE_POINTS);
    event.add(entityType, ModAttributes.JUSTICE_POINTS);

    event.add(entityType, ModAttributes.ATTACK_SPEED_MAIN_HAND);
    event.add(entityType, ModAttributes.ATTACK_SPEED_OFF_HAND);
  }
}
