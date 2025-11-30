package ctn.imaginarycraft.events.entity;

import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.damage.util.LcDamageUtil;
import ctn.imaginarycraft.api.lobotomycorporation.level.LcLevel;
import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.client.util.ParticleUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.eventexecute.LcDamageEventExecutes;
import ctn.imaginarycraft.mixinextend.IDamageSource;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 实体事件
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class EntityEvents {
  /**
   * 实体死亡事件
   */
  @SubscribeEvent
  public static void deathEvent(LivingDeathEvent event) {
  }

  @SubscribeEvent
  public static void addSpiritAttribute(EntityJoinLevelEvent event) {
  }

  @SubscribeEvent
  public static void entityTickEvent(EntityTickEvent.Pre event) {
  }

  @SubscribeEvent
  public static void entityHealEvent(LivingHealEvent event) {
    float amount = event.getAmount();
    LivingEntity entity = event.getEntity();

    if (amount > 0) {
      ParticleUtil.createTextParticles(entity, ParticleUtil.getText(amount), false, true);
    }
  }

  /**
   * 即将受到伤害但还没处理
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
    DamageSource damageSource = event.getSource();
    IDamageSource modDamageSource = (IDamageSource) damageSource;
    LcLevel lcDamageLevel = modDamageSource.getLcDamageLevel();
    LcDamageType lcDamageType = modDamageSource.getLcDamageType();
    LivingEntity entity = event.getEntity();
    if (entity.level() instanceof ServerLevel serverLevel) {
      LcDamageEventExecutes.vulnerableTreatment(event, damageSource, entity, lcDamageLevel, lcDamageType);
    }
  }

  /**
   * 处理伤害效果
   */
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void dealingWithDamageEffects(LivingDamageEvent.Pre event) {
    DamageContainer container = event.getContainer();
    LivingEntity entity = event.getEntity();
    DamageSource source = event.getSource();
    Entity directEntity = source.getDirectEntity();
    Entity causingEntity = source.getEntity();
    Holder<DamageType> damageTypeHolder = source.typeHolder();
    LcDamageType lcDamageType = LcDamageType.byDamageType(damageTypeHolder);
    // 建议在每个有返回值的方法执行后再获取一次以求准确
    float newDamage = event.getNewDamage();
    boolean modifyRationality = lcDamageType == LcDamageType.SPIRIT || lcDamageType == LcDamageType.EROSION;

    if (lcDamageType == LcDamageType.THE_SOUL) {
      newDamage = LcDamageUtil.theSoulDamage(newDamage, entity, directEntity == null ? causingEntity : directEntity, source);
    }

    if (newDamage > 0) {
      if (entity instanceof Player player && modifyRationality) {
        RationalityUtil.modifyValue(player, -newDamage, true);
        if (lcDamageType == LcDamageType.SPIRIT) {
//          ParticleUtil.createTextParticles(player, newDamage, true, false);
          event.getContainer().setPostAttackInvulnerabilityTicks(0);
          event.setNewDamage(0);
          return;
        }
      }

      event.setNewDamage(newDamage);
      return;
    }

    if (newDamage == 0) {
      return;
    }

    // TODO 要注入判断要判断是否符合恢复条件
    // 如果低于0则恢复生命值
    event.getContainer().setPostAttackInvulnerabilityTicks(0);
    event.setNewDamage(0);
    newDamage = -newDamage;

    if (entity instanceof Player player && modifyRationality) {
      RationalityUtil.modifyValue(player, newDamage, true);
      ParticleUtil.createTextParticles(player, newDamage, true, true);
    }

    LcDamageEventExecutes.heal(event, newDamage, entity);
  }

  /**
   * 已应用伤害至实体事件
   */
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void appliedDamageToEntityEvent(LivingDamageEvent.Post event) {
    LivingEntity entity = event.getEntity();
    Level level = entity.level();
    DamageSource source = event.getSource();
    float newDamage = event.getNewDamage();

    if (level instanceof ServerLevel serverLevel) {
      LcDamageEventExecutes.appliedDamageToEntity(serverLevel, entity, source, newDamage);
    }
  }
}
