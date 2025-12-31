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
    player.setData(ModAttachments.GUN_CHARGE_UP, Mth.clamp(newValue, 0.0f, getMaxChargeUpValue(player)));
  }

  public static void modifyChargeUpValue(Player player, float value) {
    setChargeUpValue(player, Mth.clamp(getChargeUpValue(player) + value, 0.0f, getMaxChargeUpValue(player)));
  }

  public static void resetChargeUp(Player player) {
    setChargeUpValue(player, 0);
  }

  public static float getChargeUpPercentage(Player player) {
    float maxChargeUpValue = getMaxChargeUpValue(player);
    if (maxChargeUpValue <= 0) {
      return 0;
    }
    return Mth.clamp(getChargeUpValue(player) / maxChargeUpValue, 0, 1);
  }

  public static void setChargeUpPercentage(Player player, float newValue) {
    float maxChargeUpValue = getMaxChargeUpValue(player);
    if (maxChargeUpValue <= 0) {
      return;
    }
    setChargeUpValue(player, Mth.clamp(newValue / maxChargeUpValue, 0, 1));
  }

  public static void modifyChargeUpPercentage(Player player, float value) {
    setChargeUpPercentage(player, getChargeUpPercentage(player) + value);
  }

  public static float getMaxChargeUpValue(Player player) {
    double attributeValue = player.getAttributeValue(Attributes.ATTACK_SPEED);
    if (attributeValue <= 0) {
      return 0;
    }
    return (float) (20.0f / attributeValue);
  }

  public static boolean isHoldGunWeapon(LivingEntity livingEntity) {
    return livingEntity.getMainHandItem().getItem() instanceof IGunWeapon || livingEntity.getOffhandItem().getItem() instanceof IGunWeapon;
  }
}
