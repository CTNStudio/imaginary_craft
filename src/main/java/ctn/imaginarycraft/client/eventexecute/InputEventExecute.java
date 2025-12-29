package ctn.imaginarycraft.client.eventexecute;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.common.payloads.entity.player.PlayerIGunWeaponPayload;
import ctn.imaginarycraft.util.PayloadUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public final class InputEventExecute {
  public static void iGunWeapon(LocalPlayer player, Minecraft minecraft) {
    Options options = minecraft.options;
    if (!options.keyAttack.isDown()) {
      return;
    }
    use:
    if (options.keyUse.isDown()) {
      if (!player.isUsingItem()) {
        return;
      }
      ItemStack useItem = player.getUseItem();
      if (useItem.isEmpty() || !(useItem.getItem() instanceof IGunWeapon iGunWeapon)) {
        return;
      }
      if (!iGunWeapon.isGunAim(player, useItem)) {
        break use;
      }
      InteractionHand usedItemHand = player.getUsedItemHand();
      if (!iGunWeapon.gunAimShoot(player, useItem, usedItemHand)) {
        return;
      }
      PayloadUtil.sendToServer(new PlayerIGunWeaponPayload(usedItemHand, true, true));
      return;
    }

    // 查找可射击的枪械
    IGunWeapon gunWeapon;
    ItemStack weaponItemStack;
    InteractionHand usedHand;

    ItemStack mainHandItem = player.getMainHandItem();
    if (!mainHandItem.isEmpty() && mainHandItem.getItem() instanceof IGunWeapon mainHandGun) {
      gunWeapon = mainHandGun;
      weaponItemStack = mainHandItem;
      usedHand = InteractionHand.MAIN_HAND;
    } else {
      ItemStack offhandItem = player.getOffhandItem();
      if (!offhandItem.isEmpty() && offhandItem.getItem() instanceof IGunWeapon offhandGun) {
        gunWeapon = offhandGun;
        weaponItemStack = offhandItem;
        usedHand = InteractionHand.OFF_HAND;
      } else {
        return;
      }
    }

    if (!gunWeapon.gunShoot(player, weaponItemStack, usedHand)) {
      return;
    }
    PayloadUtil.sendToServer(new PlayerIGunWeaponPayload(InteractionHand.MAIN_HAND, false, true));
  }
}
