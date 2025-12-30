package ctn.imaginarycraft.util;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModAttachments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public final class GunWeaponUtil {
  public static final ResourceLocation GUN_SHOOT_MODIFY_TICK = ImaginaryCraft.modRl("gun_shoot_modify_tick");

  public static boolean isIsLeftKeyAttack(Player player) {
    return player.getData(ModAttachments.IS_GUN_LEFT_KEY_ATTACK);
  }

  public static void setIsLeftKeyAttack(Player player, boolean is) {
    player.setData(ModAttachments.IS_GUN_LEFT_KEY_ATTACK, is);
  }

  public static float getChargeUpValue(Player player) {
    return player.getData(ModAttachments.GUN_CHARGE_UP);
  }

  public static void setChargeUpValue(Player player, float newValue) {
    float value = Math.max(0, newValue);
    if (getChargeUpValue(player) != value) {
      player.setData(ModAttachments.GUN_CHARGE_UP, value);
    }
  }

  public static void modifyChargeUpValue(Player player, float value) {
    float chargeUpValue = getChargeUpValue(player);
    if (chargeUpValue >= getMaxChargeUpValue(player)) {
      return;
    }
    setChargeUpValue(player, chargeUpValue + value);
  }

  public static void resetChargeUp(Player player) {
    setChargeUpValue(player, 0);
  }

  public static float getChargeUpPercentage(Player player) {
    return (float) Mth.clamp(getChargeUpValue(player) / getMaxChargeUpValue(player), 0, 1);
  }

  public static void setChargeUpPercentage(Player player, float newValue) {
    float chargeUpValue = getChargeUpValue(player);
    float value = (float) (Mth.clamp(newValue, 0, 1) * getMaxChargeUpValue(player));
    if (chargeUpValue != value) {
      setChargeUpValue(player, value);
    }
  }

  public static void modifyChargeUpPercentage(Player player, float value) {
    float chargeUpPercentage = getChargeUpPercentage(player);
    if (chargeUpPercentage >= 1) {
      return;
    }
    setChargeUpPercentage(player, chargeUpPercentage + value);
  }

  public static double getMaxChargeUpValue(Player player) {
    return player.getAttributeValue(Attributes.ATTACK_SPEED) * 20.0;
  }

  public static float getSpeed(Player player) {
    // TODO
    return player.getCurrentItemAttackStrengthDelay();
  }

  public static boolean isHoldGunWeapon(LivingEntity livingEntity) {
    return livingEntity.getMainHandItem().getItem() instanceof IGunWeapon || livingEntity.getOffhandItem().getItem() instanceof IGunWeapon;
  }
}
