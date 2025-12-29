package ctn.imaginarycraft.eventexecute;

import ctn.imaginarycraft.api.IDamageContainer;
import ctn.imaginarycraft.api.IDamageSource;
import ctn.imaginarycraft.api.capability.entity.IEntityAbnormalities;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.client.util.ParticleUtil;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

public final class LcDamageEventExecutes {

  public static final double VULNERABILITY_DECELERATE_THRESHOLD = 1.2;

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
  public static void vulnerableTreatment(
    IDamageContainer damageContainer,
    LivingEntity entity,
    @Nullable LcLevelType attackerLevel,
    @Nullable LcDamageType lcDamageTypeTypes
  ) {
    DamageContainer container = damageContainer.getImaginaryCraft$This();
    float newDamageAmount = levelJudgment(entity, attackerLevel, container.getNewDamage());

    // 伤害类型
    if (lcDamageTypeTypes != null) {
      // 易伤处理
      Holder<Attribute> vulnerable = lcDamageTypeTypes.getVulnerable();
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
   * 等级判断
   *
   * @param entity        目标
   * @param attackerLevel 攻击者等级
   * @param damage        伤害
   * @return 新伤害
   */
  private static float levelJudgment(final LivingEntity entity, @Nullable final LcLevelType attackerLevel, final float damage) {
    if (attackerLevel == null) {
      return damage;
    }

    // 盔甲判断
    if (entity instanceof IEntityAbnormalities) {
      return ontologyLevelCalculate(entity, attackerLevel, damage);
    }
    // 盔甲等级
    int armorItemStackLaval = 0;
    // 护甲数量
    int number = 0;
    int voidNumber = 0;
    // 盔甲
    for (final ItemStack armorItemStack : entity.getArmorAndBodyArmorSlots()) {
      if (armorItemStack == null || armorItemStack.isEmpty()) {
        continue;
      }

      // 盔甲等级
      @Nullable LcLevelType level = LcLevelUtil.getLevel(armorItemStack);
      if (level == null) {
        voidNumber++;
      } else {
        armorItemStackLaval += level.getLevelValue();
      }

      number++;
    }

    if (number == 0) {
      return ontologyLevelCalculate(entity, attackerLevel, damage);
    }

    if (voidNumber == number) {
      armorItemStackLaval = -1;
    }

    // 获取平均等级
    if (armorItemStackLaval != -1) {
      armorItemStackLaval /= number;
    }
    return damage * LcLevelUtil.getDamageMultiple(LcLevelType.byLevel(armorItemStackLaval), attackerLevel);
  }

  /**
   * 本体计算
   *
   * @param entity        目标
   * @param attackerLevel 攻击者等级
   * @param damage        伤害
   * @return 新伤害
   */
  private static float ontologyLevelCalculate(final LivingEntity entity, @Nullable final LcLevelType attackerLevel, final float damage) {
    return damage * LcLevelUtil.getDamageMultiple(LcLevelUtil.getLevel(entity), attackerLevel);
  }

  /**
   * 伤害应用并生成粒子和易伤减速
   */
  public static void appliedDamageToEntity(final ServerLevel serverLevel, final LivingEntity entity, final DamageSource source, final float newDamage) {
    if (entity instanceof Player player) {
      RationalityUtil.setRecoveryTick(player, 10 * 20);
    }

    // 低抗缓慢
    @Nullable LcDamageType lcDamageType = IDamageSource.of(source).getImaginaryCraft$LcDamageType();
    if (lcDamageType != null) {
      vulnerabilityDecelerate(entity, lcDamageType);
    }

    // 生成粒子
    Holder<DamageType> damageType = source.typeHolder();

    // TODO 需要改良 以实现攻击位置
    if (source.getEntity() instanceof Projectile projectile) {
      @Nullable Vec3 sourcePosition = source.getSourcePosition();
      if (sourcePosition != null && !projectile.position().equals(sourcePosition)) {
        double x = sourcePosition.x;
        double y = sourcePosition.y;
        double z = sourcePosition.z;
        ParticleUtil.randomColorTextParticles(serverLevel,
            ParticleUtil.getText(newDamage, false), damageType, lcDamageType, false, true, x, y, z);
        return;
      }
    }

    ParticleUtil.createTextParticles(entity, damageType, lcDamageType, newDamage, false, false);
  }

  public static void vulnerabilityDecelerate(LivingEntity entity, @NotNull LcDamageType lcDamageType) {
    AttributeInstance attributeInstance = entity.getAttribute(lcDamageType.getVulnerable());
    if (attributeInstance != null && attributeInstance.getValue() > VULNERABILITY_DECELERATE_THRESHOLD) {
      // TODO 替换成专属效果
      entity.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 20, 2));
    }
  }
}
