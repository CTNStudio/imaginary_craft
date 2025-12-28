package ctn.imaginarycraft.events.entity;

import ctn.imaginarycraft.api.IDamageContainer;
import ctn.imaginarycraft.api.IDamageSource;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcDamageUtil;
import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.client.util.ParticleUtil;
import ctn.imaginarycraft.common.payloads.entity.player.PlayerDamagePayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.eventexecute.LcDamageEventExecutes;
import ctn.imaginarycraft.util.PayloadUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.Nullable;


@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class LivingEntityEvents {

  /**
   * 恢复事件
   */
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

    LcDamageEventExecutes.vulnerableTreatment(iDamageContainer, entity, lcDamageLevel, lcDamageType);
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
    boolean modifyRationality = lcDamageType == LcDamageType.SPIRIT || lcDamageType == LcDamageType.EROSION;

    // TODO 添加免疫，吸收，无效处理
    if (lcDamageType == LcDamageType.THE_SOUL) {
      newDamage = LcDamageUtil.theSoulDamage(newDamage, attackedEntity, sourceDirectEntity == null ? sourceCausingEntity : sourceDirectEntity, source);
    }

    if (newDamage > 0) {
      if (attackedEntity instanceof Player player && modifyRationality) {
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

    if (attackedEntity instanceof Player player && modifyRationality) {
      RationalityUtil.modifyValue(player, newDamage, true);
      ParticleUtil.createTextParticles(player, newDamage, true, true);
    }

    LcDamageEventExecutes.heal(event, newDamage, attackedEntity);
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
    LcDamageEventExecutes.appliedDamageToEntity(serverLevel, entity, source, newDamage);
    if (entity instanceof ServerPlayer player) {
      PayloadUtil.sendToClient(player, new PlayerDamagePayload(IDamageSource.of(event.getSource()).getImaginaryCraft$LcDamageType(), newDamage));
    }
  }
}
