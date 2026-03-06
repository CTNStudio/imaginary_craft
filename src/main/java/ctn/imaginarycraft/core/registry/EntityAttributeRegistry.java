package ctn.imaginarycraft.core.registry;

import ctn.imaginarycraft.common.world.entity.abnormalities.ordeals.violet.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.init.world.*;
import ctn.imaginarycraft.init.world.entiey.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.entity.*;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class EntityAttributeRegistry {

  /**
   * 注册实体属性
   */
  @SubscribeEvent
  public static void registry(EntityAttributeCreationEvent event) {
    event.put(AbnormalitiesEntityTypes.GRANT_US_LOVE.get(), GrantUsLove.createAttributes().build());
  }

  /**
   * 添加或修改属性 等级在{@link CapabilityRegistry}类注册
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void registry(EntityAttributeModificationEvent event) {
    // 对所有实体打入基础属性
    event.getTypes().forEach(entityType -> lcAttributesVulnerable(event, entityType));
    vanilla(event);
    addPlayerAttributes(event, EntityType.PLAYER);
  }

  private static void vanilla(EntityAttributeModificationEvent event) {
    // 抗性（前4个）+ 伤害倍数（后4个）
    configureEntityAttributes(event, EntityType.WARDEN, 0.6, 1.2, 0.8, 0.2, 0.6, 1.2, 0.8, 0.2);
    configureEntityAttributes(event, EntityType.ENDER_DRAGON, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5);
    configureEntityAttributes(event, EntityType.WITHER, 0.5, 0.7, -1.0, 1.0, 0.5, 0.7, -1.0, 1.0);
    configureEntityAttributes(event, EntityType.IRON_GOLEM, 0.5, 0.6, 1.5, 1.0, 0.5, 0.6, 1.5, 1.0);
    configureEntityAttributes(event, EntityType.ELDER_GUARDIAN, 0.5, 0.8, 0.9, 1.2, 0.5, 0.8, 0.9, 1.2);
    configureEntityAttributes(event, EntityType.RAVAGER, 0.5, 1.0, 1.5, 1.3, 0.5, 1.0, 1.5, 1.3);
    configureEntityAttributes(event, EntityType.GUARDIAN, 0.5, 0.8, 0.9, 1.2, 0.5, 0.8, 0.9, 1.2);
    configureEntityAttributes(event, EntityType.ENDERMAN, 0.8, 0.5, 1.2, 1.5, 0.8, 0.5, 1.2, 1.5);
    configureEntityAttributes(event, EntityType.GHAST, 0.5, 0.5, 1.2, 1.5, 0.5, 0.5, 1.2, 1.5);
    configureEntityAttributes(event, EntityType.HOGLIN, 0.8, 1.2, 1.1, 1.2, 0.8, 1.2, 1.1, 1.2);
    configureEntityAttributes(event, EntityType.PIGLIN_BRUTE, 0.6, 1.3, 1.0, 1.1, 0.6, 1.3, 1.0, 1.1);
    configureEntityAttributes(event, EntityType.SHULKER, 0.2, 1.5, 1.0, 1.1, 0.2, 1.5, 1.0, 1.1);
    configureEntityAttributes(event, EntityType.ZOGLIN, 0.5, 1.2, 1.2, 1.3, 0.5, 1.2, 1.2, 1.3);
    configureEntityAttributes(event, EntityType.EVOKER, 1.0, 1.2, 1.3, 1.3, 1.0, 1.2, 1.3, 1.3);
    configureEntityAttributes(event, EntityType.VINDICATOR, 0.8, 1.2, 1.3, 1.3, 0.8, 1.2, 1.3, 1.3);
    configureEntityAttributes(event, EntityType.WITCH, 1.2, 1.1, 1.0, 1.3, 1.2, 1.1, 1.0, 1.3);
    configureEntityAttributes(event, EntityType.WITHER_SKELETON, 0.8, 0.8, -1.0, 1.1, 0.8, 0.8, -1.0, 1.1);
    configureEntityAttributes(event, EntityType.BLAZE, 0.7, 0.8, 1.3, 1.2, 0.7, 0.8, 1.3, 1.2);
    configureEntityAttributes(event, EntityType.BOGGED, 1.0, 0.5, 0.7, 1.0, 1.0, 0.5, 0.7, 1.0);
    configureEntityAttributes(event, EntityType.SKELETON, 0.9, 0.6, 0.8, 1.0, 0.9, 0.6, 0.8, 1.0);
    configureEntityAttributes(event, EntityType.STRAY, 0.8, 0.6, 0.8, 1.0, 0.8, 0.6, 0.8, 1.0);
    configureEntityAttributes(event, EntityType.ZOMBIE, 0.7, 0.8, 0.9, 1.1, 0.7, 0.8, 0.9, 1.1);
    configureEntityAttributes(event, EntityType.ZOMBIFIED_PIGLIN, 0.6, 0.7, 0.5, 1.3, 0.6, 0.7, 0.5, 1.3);
    configureEntityAttributes(event, EntityType.DROWNED, 0.8, 0.8, 1.0, 1.1, 0.8, 0.8, 1.0, 1.1);
    configureEntityAttributes(event, EntityType.BREEZE, 0.5, 0.8, 1.3, 1.2, 0.5, 0.8, 1.3, 1.2);
    configureEntityAttributes(event, EntityType.CREEPER, 1.2, 0.8, 1.2, 1.2, 1.2, 0.8, 1.2, 1.2);
    configureEntityAttributes(event, EntityType.HUSK, 0.6, 0.6, 0.8, 1.2, 0.6, 0.6, 0.8, 1.2);
    configureEntityAttributes(event, EntityType.MAGMA_CUBE, 0.4, 0.6, 1.4, 1.2, 0.4, 0.6, 1.4, 1.2);
    configureEntityAttributes(event, EntityType.PHANTOM, 0.6, 1.0, 0.8, 1.3, 0.6, 1.0, 0.8, 1.3);
    configureEntityAttributes(event, EntityType.ENDERMITE, 0.9, 1.2, 1.1, 1.3, 0.9, 1.2, 1.1, 1.3);
    configureEntityAttributes(event, EntityType.SILVERFISH, 0.8, 1.2, 1.3, 1.3, 0.8, 1.2, 1.3, 1.3);
    configureEntityAttributes(event, EntityType.VEX, 0.8, 1.3, 1.1, 1.5, 0.8, 1.3, 1.1, 1.5);
    configureEntityAttributes(event, EntityType.PILLAGER, 0.8, 1.2, 1.3, 1.3, 0.8, 1.2, 1.3, 1.3);
    configureEntityAttributes(event, EntityType.PIGLIN, 0.8, 1.2, 1.3, 1.2, 0.8, 1.2, 1.3, 1.2);
    configureEntityAttributes(event, EntityType.SPIDER, 0.7, 1.1, 1.3, 1.1, 0.7, 1.1, 1.3, 1.1);
    configureEntityAttributes(event, EntityType.CAVE_SPIDER, 0.7, 1.1, 1.0, 1.1, 0.7, 1.1, 1.0, 1.1);
    configureEntityAttributes(event, EntityType.SLIME, 0.5, 0.7, 1.2, 1.1, 0.5, 0.7, 1.2, 1.1);
  }

  /**
   * 统一配置实体属性：抗性 + 伤害倍数
   * <p>
   * 参数顺序：抗性（4个）+ 伤害倍数（4个）
   *
   * @param event          属性修改事件
   * @param entityType     实体类型
   * @param physicsRes     物理抗性
   * @param spiritRes      精神抗性
   * @param erosionRes     侵蚀抗性
   * @param theSoulRes     灵魂抗性
   * @param physicsDmg     物理伤害倍数
   * @param spiritDmg      精神伤害倍数
   * @param erosionDmg     侵蚀伤害倍数
   * @param theSoulDmg     灵魂伤害倍数
   */
  private static void configureEntityAttributes(EntityAttributeModificationEvent event,
                                                  EntityType<? extends LivingEntity> entityType,
                                                  double physicsRes, double spiritRes, double erosionRes, double theSoulRes,
                                                  double physicsDmg, double spiritDmg, double erosionDmg, double theSoulDmg) {
    // 设置抗性属性
    lcAttributesVulnerable(event, entityType, physicsRes, spiritRes, erosionRes, theSoulRes);
    // 设置伤害倍数
    EntityDamageMultiplier.setMultiplierAll(entityType, physicsRes, spiritRes, erosionRes, theSoulRes,
                                                  physicsDmg, spiritDmg, erosionDmg, theSoulDmg);
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
