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

  // TODO 处理伤害异常
  /**
   * 即将受到伤害但还没处理
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void livingIncomingDamageEvent(LivingIncomingDamageEvent event) {
    LivingEntity entity = event.getEntity();
    if (!(entity.level() instanceof ServerLevel serverLevel)) {
      return;
    }

    DamageSource damageSource = event.getSource();
    DamageContainer damageContainer = event.getContainer();
    IDamageSource iDamageSource = IDamageSource.of(damageSource);
    IDamageContainer iDamageContainer = IDamageContainer.of(damageContainer);
    @Nullable LcLevel lcDamageLevel = iDamageSource.getImaginaryCraft$LcDamageLevel();
    LcDamageType lcDamageType = iDamageSource.getImaginaryCraft$LcDamageType();

    DamageContainer container = iDamageContainer.getImaginaryCraft$This();
    double newDamage = LcDamageEventExecutes.levelJudgment(entity, lcDamageLevel, container.getNewDamage());

    // 伤害类型
    if (lcDamageType != null) {
      // 应用伤害乘数表
      // TODO hyw
//      newDamage *= EntityDamageMultiplier.getMultiplier(entity, lcDamageType);

      // 易伤处理（如果乘数表未配置，则使用属性系统）
      Holder<Attribute> vulnerable = lcDamageType.getVulnerable();
      AttributeInstance attributeInstance = entity.getAttribute(vulnerable);
      if (attributeInstance != null) {
        newDamage *= attributeInstance.getValue();
      } else {
        newDamage *= vulnerable.value().getDefaultValue();
      }
    }

    container.setNewDamage((float) newDamage);
  }

  /**
   * 处理伤害效果
   */
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void dealingWithDamageEffects(LivingDamageEvent.Pre event) {
    DamageContainer container = event.getContainer();
    IDamageContainer iDamageContainer = IDamageContainer.of(container);
    LivingEntity attackedEntity = event.getEntity();
    DamageSource source = event.getSource();
    IDamageSource iDamageSource = IDamageSource.of(source);
    Entity sourceDirectEntity = source.getDirectEntity();
    Entity sourceCausingEntity = source.getEntity();
    LcDamageType lcDamageType = iDamageSource.getImaginaryCraft$LcDamageType();

    // 建议在每个有返回值的方法执行后再获取一次以求准确
    float newDamage = event.getNewDamage();
    // 是否会修改理智
    boolean isModifyRationality = lcDamageType == LcDamageType.SPIRIT || lcDamageType == LcDamageType.EROSION;

    // TODO 添加免疫，吸收，无效处理
    if (lcDamageType == LcDamageType.THE_SOUL) {
      newDamage = LcDamageUtil.theSoulDamage(newDamage, attackedEntity, sourceDirectEntity == null ? sourceCausingEntity : sourceDirectEntity, source);
    }

    if (!(newDamage > 0)) {
      if (newDamage != 0) {
        // TODO 要注入判断要判断是否符合恢复条件（免疫，吸收，无效处理）
        // 如果低于0则恢复生命值

        // TODO 应该很早就处理
        // 恢复理智
        if (attackedEntity instanceof Player player && isModifyRationality) {
          RationalityUtil.modifyValue(player, newDamage, true);
          ParticleUtil.createDamageTextParticles(player, newDamage, true, true);
        }

        // 恢复血量
        float healed = Math.abs(newDamage);
        attackedEntity.heal(healed);
        ParticleUtil.createDamageTextParticles(attackedEntity, healed, false, true);

        // 最后修改伤害为0表示不造成伤害
        event.getContainer().setPostAttackInvulnerabilityTicks(0);
        event.setNewDamage(0);
      }
    } else {
      // 修改理智
      if (attackedEntity instanceof Player player && isModifyRationality) {
        RationalityUtil.modifyValue(player, -newDamage, true);
        if (lcDamageType == LcDamageType.SPIRIT) {
          event.getContainer().setPostAttackInvulnerabilityTicks(0);
          event.setNewDamage(0);
          return;
        }
      }

      event.setNewDamage(newDamage);
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
    @Nullable LcDamageType lcDamageType = IDamageSource.of(source).getImaginaryCraft$LcDamageType();
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
