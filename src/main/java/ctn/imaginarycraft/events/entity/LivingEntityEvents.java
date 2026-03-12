package ctn.imaginarycraft.events.entity;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.client.util.*;
import ctn.imaginarycraft.common.payload.toc.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.eventexecute.*;
import ctn.imaginarycraft.init.*;
import ctn.imaginarycraft.mixed.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.common.damagesource.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.atomic.*;

import static net.minecraft.world.effect.MobEffects.*;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class LivingEntityEvents {
  /**
   * 恢复事件
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void entityHealEvent(LivingHealEvent event) {
    float amount = event.getAmount();
    LivingEntity entity = event.getEntity();

    if (amount > 0) {
      ParticleUtil.createDamageTextParticles(entity, ParticleUtil.getText(amount), false, true);
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void livingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
    LivingEntity entity = event.getEntity();
    EquipmentSlot slot = event.getSlot();

    if (entity.isAlive()) {
      DelayTaskHolder delayTaskHolder = entity.getExistingDataOrNull(ModAttachments.DELAY_TASK_HOLDER);
      if (delayTaskHolder != null && !ItemStack.isSameItem(event.getFrom(), event.getTo())) {
        delayTaskHolder.removeTask(slot);
      }

      if (slot.getType() == EquipmentSlot.Type.HAND) {
        if (entity instanceof Player player) {
          boolean isHandUsed = slot == EquipmentSlot.MAINHAND;
          GunWeaponUtil.setIsAttack(player, true, isHandUsed);
          GunWeaponUtil.resetChargeUp(player, isHandUsed);
        }
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void tickPre(EntityTickEvent.Pre event) {
    Entity entity = event.getEntity();
    if (entity instanceof LivingEntity livingEntity) {
      if (livingEntity.isAlive()) {
        DelayTaskHolder timingRun = livingEntity.getExistingDataOrNull(ModAttachments.DELAY_TASK_HOLDER);
        if (timingRun != null) {
          timingRun.tick();
        }
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void livingSwapItemsEvent(LivingSwapItemsEvent.Hands event) {
    LivingEntity livingEntity = event.getEntity();
    if (livingEntity.isAlive()) {
      ItemStack itemSwappedToMainHand = event.getItemSwappedToMainHand();
      ItemStack itemSwappedToOffHand = event.getItemSwappedToOffHand();
      DelayTaskHolder delayTaskHolder = livingEntity.getExistingDataOrNull(ModAttachments.DELAY_TASK_HOLDER);

      if (!itemSwappedToMainHand.getItem().shouldCauseBlockBreakReset(itemSwappedToMainHand, itemSwappedToOffHand)) {
        if (delayTaskHolder != null) {
          delayTaskHolder.removeTask(InteractionHand.MAIN_HAND);
        }
        if (livingEntity instanceof Player player) {
          GunWeaponUtil.setIsAttack(player, true, true);
          GunWeaponUtil.resetChargeUp(player, true);
        }
      }

      if (!itemSwappedToOffHand.getItem().shouldCauseBlockBreakReset(itemSwappedToOffHand, itemSwappedToMainHand)) {
        if (delayTaskHolder != null) {
          delayTaskHolder.removeTask(InteractionHand.OFF_HAND);
        }
        if (livingEntity instanceof Player player) {
          GunWeaponUtil.setIsAttack(player, true, false);
          GunWeaponUtil.resetChargeUp(player, false);
        }
      }
    }
  }

  /**
   * 即将受到伤害但还没处理
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
    LivingEntity entity = event.getEntity();
    if (entity.level() instanceof ServerLevel) {
      DamageSource damageSource = event.getSource();
      DamageContainer damageContainer = event.getContainer();

      LcDamageEventExecutes.levelReduction(entity, damageContainer, damageSource);
      LcDamageEventExecutes.vulnerableReduction(entity, damageContainer, damageSource);
    }
  }

  /**
   * 处理护甲受伤事件，根据伤害类型和护甲等级计算最终伤害值
   *
   * @param event 护甲受伤事件，包含伤害源、装备槽位和伤害信息
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void armorHurtEvent(ArmorHurtEvent event) {
    DamageSource damageSource = event.getDamageSource();
    LcDamageType lcDamageType = damageSource.imaginaryCraft$getLcDamageType();
    Holder<Attribute> vulnerable = lcDamageType == null ? null : lcDamageType.getVulnerable();
    Holder<Attribute> defense = lcDamageType == null ? null : lcDamageType.getDefense();
    LcLevel lcLevel = damageSource.imaginaryCraft$getLcDamageLevel();
    // 遍历所有装备槽位的护甲物品
    event.getArmorMap().forEach((slot, armorEntry) -> {
      LcLevel armorLevel = LcLevelUtil.getLevel(armorEntry.armorItemStack);
      AtomicBoolean hasResistance = new AtomicBoolean(false);
      // 检查护甲是否具有对该伤害类型的抗性属性
      armorEntry.armorItemStack.getAttributeModifiers().forEach(slot, (attribute, modifier) -> {
        if (lcDamageType != null) {
          if (attribute == vulnerable || attribute == defense) {
            hasResistance.set(true);
          }
        }
      });
      // 根据物品等级来减少物品受到的伤害
      float reducedDamage = armorEntry.originalDamage * LcLevelUtil.getDamageMultiple(lcLevel, armorLevel);
      float resistanceMultiplier = hasResistance.get() ? 1.2f : 1f;
      armorEntry.newDamage = reducedDamage * resistanceMultiplier;
    });
  }

  /**
   * 处理伤害效果
   */
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void dealingWithDamageEffects(LivingDamageEvent.Pre event) {
    LivingEntity attackedEntity = event.getEntity();
    DamageSource damageSource = event.getSource();
    Entity sourceDirectEntity = damageSource.getDirectEntity();
    Entity sourceCausingEntity = damageSource.getEntity();
    LcDamageType lcDamageType = damageSource.imaginaryCraft$getLcDamageType();

    if (lcDamageType == LcDamageType.THE_SOUL) {
      // 处理灵魂伤害转换成对应比例的生命值
      event.setNewDamage(LcDamageEventExecutes.theSoulDamage(event.getNewDamage(), attackedEntity, sourceDirectEntity == null ? sourceCausingEntity : sourceDirectEntity, damageSource));
    }

    // 如果低于0则恢复生命值
    if (event.getNewDamage() < 0) {
      // 恢复理智
      if (attackedEntity instanceof Player player &&
        (lcDamageType == LcDamageType.SPIRIT || lcDamageType == LcDamageType.EROSION)
      ) {
        RationalityUtil.modifyValue(player, event.getOriginalDamage(), true);
        ParticleUtil.createDamageTextParticles(player, event.getOriginalDamage(), true, true);
      }

      // 恢复血量
      float healed = Math.abs(event.getOriginalDamage());
      attackedEntity.heal(healed);
      ParticleUtil.createDamageTextParticles(attackedEntity, healed, false, true);

      // 最后修改伤害为0表示不造成伤害
      event.getContainer().setPostAttackInvulnerabilityTicks(0);
      event.setNewDamage(0);
      return;
    }

    if (event.getNewDamage() > 0) {
      // 修改理智
      if (attackedEntity instanceof Player player &&
        (lcDamageType == LcDamageType.SPIRIT || lcDamageType == LcDamageType.EROSION)
      ) {
        RationalityUtil.modifyValue(player, -event.getNewDamage(), true, lcDamageType == LcDamageType.SPIRIT);
        if (lcDamageType == LcDamageType.SPIRIT) {
          event.getContainer().setPostAttackInvulnerabilityTicks(0);
          event.setNewDamage(0);
          return;
        }
      }

      event.setNewDamage(event.getNewDamage());
    }
  }

  /**
   * 已应用伤害至实体事件
   */
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void appliedDamageToEntityEvent(LivingDamageEvent.Post event) {
    LivingEntity entity = event.getEntity();
    Level level = entity.level();

    if (!(level instanceof ServerLevel serverLevel)) {
      return;
    }

    DamageSource source = event.getSource();
    float newDamage = event.getNewDamage();
    // 设置理智恢复计时
    if (entity instanceof Player player) {
      RationalityUtil.setRecoveryTick(player, 10 * 20);
    }

    // 低抗缓慢
    @Nullable LcDamageType lcDamageType = IDamageSource.of(source).imaginaryCraft$getLcDamageType();
    if (lcDamageType != null) {
      AttributeInstance attributeInstance = entity.getAttribute(lcDamageType.getVulnerable());
      if (attributeInstance != null && attributeInstance.getValue() > LcDamageEventExecutes.VULNERABILITY_DECELERATE_THRESHOLD) {
        // TODO 替换成专属效果
        entity.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 20, 2));
      }
    }

    // 生成粒子
    Holder<DamageType> damageType = source.typeHolder();
    ParticleUtil.createDamageTextParticles(entity, damageType, lcDamageType, newDamage, false, false);

    // TODO 速度快的子弹无法正常显示位置
//    if (source.getDirectEntity() instanceof Projectile projectile) {
//      Vec3 position = source.getSourcePosition();
//      double x;
//      double y;
//      double z;
//      if (position != null) {
//        x = position.x;
//        y = position.y;
//        z = position.z;
//      } else {
//        Vec3 sourcePosition = projectile.position();
//        x = sourcePosition.x;
//        y = sourcePosition.y;
//        z = sourcePosition.z;
//      }
//      MutableComponent text = ParticleUtil.getText(newDamage, false);
//      ParticleUtil.randomDamageTextParticles(serverLevel, text, damageType, lcDamageType, false, false, x, y, z);
//    } else {
//      ParticleUtil.createDamageTextParticles(entity, damageType, lcDamageType, newDamage, false, false);
//    }

    if (entity instanceof ServerPlayer player) {
      PlayerDamagePayload.send(player, lcDamageType, newDamage);
    }
  }
}
