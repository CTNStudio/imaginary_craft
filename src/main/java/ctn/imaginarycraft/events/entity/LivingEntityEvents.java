package ctn.imaginarycraft.events.entity;

import ctn.imaginarycraft.api.DelayTaskHolder;
import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.LcLevelType;
import ctn.imaginarycraft.client.util.ParticleUtil;
import ctn.imaginarycraft.common.payload.toc.PlayerDamagePayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.eventexecute.LcDamageEventExecutes;
import ctn.imaginarycraft.init.ModAttachments;
import ctn.imaginarycraft.mixed.IDamageContainer;
import ctn.imaginarycraft.mixed.IDamageSource;
import ctn.imaginarycraft.util.GunWeaponUtil;
import ctn.imaginarycraft.util.LcDamageUtil;
import ctn.imaginarycraft.util.RationalityUtil;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

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
    if (!(entity.level() instanceof ServerLevel serverLevel)) {
      return;
    }

    DamageSource damageSource = event.getSource();
    DamageContainer damageContainer = event.getContainer();
    IDamageSource iDamageSource = IDamageSource.of(damageSource);
    IDamageContainer iDamageContainer = IDamageContainer.of(damageContainer);
    @Nullable LcLevelType lcDamageLevel = iDamageSource.getImaginaryCraft$LcDamageLevel();
    LcDamageType lcDamageType = iDamageSource.getImaginaryCraft$LcDamageType();

    DamageContainer container = iDamageContainer.getImaginaryCraft$This();
    float newDamageAmount = LcDamageEventExecutes.levelJudgment(entity, lcDamageLevel, container.getNewDamage());

    // 伤害类型
    if (lcDamageType != null) {
      // 易伤处理
      Holder<Attribute> vulnerable = lcDamageType.getVulnerable();
      AttributeInstance attributeInstance = entity.getAttribute(vulnerable);
      if (attributeInstance != null) {
        newDamageAmount *= (float) attributeInstance.getValue();
      } else {
        newDamageAmount *= (float) vulnerable.value().getDefaultValue();
      }
    }

    container.setNewDamage(newDamageAmount);
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

    if (newDamage > 0) {
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
      return;
    }

    if (newDamage == 0) {
      return;
    }

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
