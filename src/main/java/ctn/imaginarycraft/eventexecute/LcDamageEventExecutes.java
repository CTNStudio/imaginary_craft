package ctn.imaginarycraft.eventexecute;

import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.damage.util.LcDamageUtil;
import ctn.imaginarycraft.api.lobotomycorporation.level.LcLevel;
import ctn.imaginarycraft.api.lobotomycorporation.level.util.LcLevelUtil;
import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.capability.entity.IAbnormalities;
import ctn.imaginarycraft.client.util.ParticleUtil;
import ctn.imaginarycraft.mixinextend.IDamageSource;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import javax.annotation.Nullable;

import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

public final class LcDamageEventExecutes {
  /**
   * 伤害回血
   */
  public static void heal(final LivingDamageEvent.Pre event, final float newDamage, final LivingEntity entity) {
    float healed = Math.abs(newDamage);
    entity.heal(healed);
    ParticleUtil.createTextParticles(entity, healed, false, true);
    event.getContainer().setPostAttackInvulnerabilityTicks(0);
    event.setNewDamage(0);
  }

  /**
   * 伤害计算
   */
  public static void vulnerableTreatment(LivingIncomingDamageEvent event,
                                         DamageSource damageSource,
                                         LivingEntity entity,
                                         LcLevel attackerLevel,
                                         @Nullable LcDamageType lcDamageTypeTypes) {
    if (LcDamageType.byDamageType(damageSource.typeHolder()) == null && lcDamageTypeTypes == null) {
      return;
    }

    // 新伤害
    float newDamageAmount = armorJudgment(entity, attackerLevel, event.getAmount());

    // 伤害类型
    if (lcDamageTypeTypes != null) {
      // 抗性处理
      var attributeInstance = entity.getAttribute(lcDamageTypeTypes.getVulnerable());
      if (attributeInstance != null) {
        newDamageAmount *= (float) attributeInstance.getValue();
      }
    }

    event.setAmount(newDamageAmount);
  }

  /**
   * 盔甲判断
   *
   * @param entity        目标
   * @param attackerLevel 攻击者等级
   * @param damage        伤害
   * @return 新伤害
   */
  private static float armorJudgment(final LivingEntity entity, final LcLevel attackerLevel, final float damage) {
    // 盔甲判断
    if (entity instanceof IAbnormalities) {
      return ontologyCalculate(entity, attackerLevel, damage);
    }

    // 盔甲等级
    int armorItemStackLaval = 0;
    // 护甲数量
    int number = 0;
    int voidNumber = 0;
    // 盔甲
    for (final ItemStack armorItemStack : entity.getArmorAndBodyArmorSlots()) {
      // 盔甲等级
      if (armorItemStack != null && !armorItemStack.isEmpty()) {
        LcLevel level = LcLevelUtil.getLevel(armorItemStack);
        if (level == LcLevel.VOID) {
          voidNumber++;
        } else {
          armorItemStackLaval += level.getLevelValue();
        }
      }
      number++;
    }

    if (voidNumber == number) {
      armorItemStackLaval = -1;
    }

    if (number == 0) {
      return ontologyCalculate(entity, attackerLevel, damage);
    }

    // 获取平均等级
    if (armorItemStackLaval != -1) {
      armorItemStackLaval /= number;
    }
    return damage * LcDamageUtil.getDamageMultiple(LcLevel.byLevel(armorItemStackLaval), attackerLevel);
  }

  /**
   * 本体计算
   *
   * @param entity        目标
   * @param attackerLevel 攻击者等级
   * @param damage        伤害
   * @return 新伤害
   */
  private static float ontologyCalculate(final LivingEntity entity, final LcLevel attackerLevel, final float damage) {
    return damage * LcDamageUtil.getDamageMultiple(LcLevelUtil.getLevel(entity), attackerLevel);
  }

  /**
   * 伤害应用
   */
  public static void appliedDamageToEntity(final ServerLevel serverLevel, final LivingEntity entity, final DamageSource source, final float newDamage) {
    if (entity instanceof Player player) {
      RationalityUtil.setRecoveryTick(player, 10 * 20);
    }

    // 低抗缓慢
    AttributeInstance attributeInstance;
    @Nullable LcDamageType lcDamageType = IDamageSource.of(source).getLcDamageType();
    if (lcDamageType != null &&
      (attributeInstance = entity.getAttribute(lcDamageType.getVulnerable())) != null &&
      attributeInstance.getValue() > 1.0) {
      entity.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 20, 2));
    }

    // 生成粒子
    Holder<DamageType> damageType = source.typeHolder();

    // TODO 需要改良 以实现攻击位置
//    @Nullable Vec3 sourcePosition = source.getSourcePosition();
//    @Nullable Entity entity1 = source.getEntity();
//    @Nullable Entity entity2 = source.getDirectEntity();
//    if (sourcePosition != null && (
//        entity1 != null && !entity1.position().equals(sourcePosition))) {
//      double x = sourcePosition.x;
//      double y = sourcePosition.y;
//      double z = sourcePosition.z;
//      ParticleUtil.randomColorTextParticles(serverLevel,
//        ParticleUtil.getText(newDamage, false), damageType, false, false, true, x, y, z);
//      return;
//    }
    ParticleUtil.createTextParticles(entity, damageType, lcDamageType, newDamage, false, false);
  }
}
