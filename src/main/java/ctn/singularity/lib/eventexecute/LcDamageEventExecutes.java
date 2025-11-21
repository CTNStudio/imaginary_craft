package ctn.singularity.lib.eventexecute;

import ctn.singularity.lib.api.lobotomycorporation.LcDamage;
import ctn.singularity.lib.api.lobotomycorporation.LcLevel;
import ctn.singularity.lib.api.lobotomycorporation.util.LcDamageUtil;
import ctn.singularity.lib.api.lobotomycorporation.util.RationalityUtil;
import ctn.singularity.lib.capability.ILcLevel;
import ctn.singularity.lib.capability.entity.IAbnos;
import ctn.singularity.lib.client.particles.TextParticle;
import ctn.singularity.lib.mixinextend.IModDamageSource;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import javax.annotation.Nullable;
import java.util.Iterator;

import static net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;

public final class LcDamageEventExecutes {
  /**
   * 伤害回血
   */
  public static void heal(final LivingDamageEvent.Pre event, final float newDamage, final LivingEntity entity) {
    float healed = Math.abs(newDamage);
    entity.heal(healed);
    TextParticle.createHealParticles(entity, TextParticle.getText(healed, true), false);
    event.getContainer().setPostAttackInvulnerabilityTicks(0);
    event.setNewDamage(0);
  }

  /**
   * 伤害计算
   */
  public static void resistanceTreatment(LivingIncomingDamageEvent event,
                                         DamageSource damageSource,
                                         LivingEntity entity,
                                         @Nullable LcLevel damageLcLevel,
                                         @Nullable LcDamage lcDamageTypes) {
    Holder<DamageType> damageTypeHolder = damageSource.typeHolder();
    for (final ResourceKey<DamageType> damageType : LcDamage.getBypassKeys()) {
      if (damageTypeHolder.is(damageType)) {
        return;
      }
    }

    // 新伤害
    float newDamageAmount = event.getAmount();
    // 盔甲等级
    int armorItemStackLaval = 0;
    // 护甲数量
    int number = 0;
    boolean isArmorItemStackEmpty = true;

    //  盔甲
    Iterator<ItemStack> aitor = entity.getArmorAndBodyArmorSlots().iterator();
    ItemStack[] armorSlots = new ItemStack[4];
    for (int i = 0; aitor.hasNext(); i++) {
      armorSlots[i] = !(entity instanceof IAbnos) ?
        aitor.next() :
        ItemStack.EMPTY;
    }

    // 盔甲等级
    for (ItemStack armorItemStack : armorSlots) {
      if (armorItemStack != null && !armorItemStack.isEmpty()) {
        isArmorItemStackEmpty = false;
        armorItemStackLaval += ILcLevel.getItemLevelValue(armorItemStack);
        number++;
      }
    }

    // 等级处理 判断实体是否有护甲如果没有就用实体的等级
    if (damageLcLevel == null) {
      damageLcLevel = LcLevel.ZAYIN;
    }

    // 盔甲判断
    if (isArmorItemStackEmpty) {
      newDamageAmount *= LcDamageUtil.getDamageMultiple(LcLevel.getEntityLevel(entity), damageLcLevel);
    } else {
      armorItemStackLaval /= number;
      newDamageAmount *= LcDamageUtil.getDamageMultiple(armorItemStackLaval - damageLcLevel.getLevel());
    }

    // 伤害类型
    if (lcDamageTypes != null) {
      // 抗性处理
      newDamageAmount *= (float) entity.getAttributeValue(lcDamageTypes.getResistance());
    }

    event.setAmount(newDamageAmount);
  }

  /**
   * 伤害应用
   */
  public static void appliedDamageToEntity(final LivingEntity entity, final DamageSource source, final float newDamage) {
    if (entity instanceof Player player) {
//      RationalityUtil.setHurtTick(player, 10 * 20);
      RationalityUtil.setRationalityRecoveryTick(player, 10 * 20);
    }

    LcDamage lcDamage = IModDamageSource.of(source).getLcDamage();

    // 低抗缓慢
    if (lcDamage != null && entity.getAttributeValue(lcDamage.getResistance()) > 1.0) {
      entity.addEffect(new MobEffectInstance(MOVEMENT_SLOWDOWN, 20, 2));
    }

    // 生成粒子
    TextParticle.createDamageParticles(source.typeHolder().getKey(), lcDamage, entity, TextParticle.getText(newDamage, false), false);
  }
}
