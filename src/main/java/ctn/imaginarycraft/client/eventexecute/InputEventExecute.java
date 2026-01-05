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
    useDown:
    if (options.keyUse.isDown()) {
      useDown1:
      if (options.keyAttack.isDown()) {
        if (!player.isUsingItem()) {
          break useDown1;
        }

        ItemStack useItem = player.getUseItem();
        if (useItem.isEmpty() ||
          !(useItem.getItem() instanceof IGunWeapon iGunWeapon) ||
          !iGunWeapon.isGunAim(player, useItem)) {
          break useDown1;
        }

        InteractionHand usedItemHand = player.getUsedItemHand();
        if (!iGunWeapon.gunAimShoot(player, useItem, usedItemHand)) {
          return;
        }

        PayloadUtil.sendToServer(new PlayerIGunWeaponPayload(usedItemHand, true, true));
        return;
      }

      ItemStack offHandItem = player.getOffhandItem();
      if (offHandItem.isEmpty() ||
        !(offHandItem.getItem() instanceof IGunWeapon iGunWeapon) ||
        !iGunWeapon.isOffHandShoot(player, offHandItem) ||
        !iGunWeapon.gunShoot(player, offHandItem, InteractionHand.OFF_HAND)) {
        break useDown;
      }

      PayloadUtil.sendToServer(new PlayerIGunWeaponPayload(InteractionHand.OFF_HAND, false, true));
      return;
    }

    if (options.keyAttack.isDown()) {
      ItemStack mainHandItem = player.getMainHandItem();
      if (mainHandItem.isEmpty() ||
        !(mainHandItem.getItem() instanceof IGunWeapon mainHandGun) ||
        !mainHandGun.gunShoot(player, mainHandItem, InteractionHand.MAIN_HAND)) {
        return;
      }

      PayloadUtil.sendToServer(new PlayerIGunWeaponPayload(InteractionHand.MAIN_HAND, false, true));
    }
  }
}
