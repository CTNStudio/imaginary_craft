package ctn.imaginarycraft.client.eventexecute;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.common.payload.tos.PlayerIGunWeaponPayload;
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
      if (!player.isUsingItem()) {
        if (options.keyAttack.isDown()) {
          ItemStack useItem = player.getUseItem();
          if (useItem.isEmpty() ||
            !(useItem.getItem() instanceof IGunWeapon iGunWeapon) ||
            !iGunWeapon.isGunAim(player, useItem)) {
            break useDown;
          }

          InteractionHand usedItemHand = player.getUsedItemHand();
          if (!iGunWeapon.gunAimShoot(player, useItem, usedItemHand)) {
            return;
          }

          PlayerIGunWeaponPayload.send(usedItemHand, true);
          return;
        }
        break useDown;
      }

      ItemStack offHandItem = player.getOffhandItem();
      if (offHandItem.isEmpty() ||
        !(offHandItem.getItem() instanceof IGunWeapon iGunWeapon) ||
        !iGunWeapon.isOffHandShoot(player, offHandItem) ||
        !iGunWeapon.gunShoot(player, offHandItem, InteractionHand.OFF_HAND)) {
        break useDown;
      }

      PlayerIGunWeaponPayload.send(InteractionHand.OFF_HAND, false);
      return;
    }

    if (options.keyAttack.isDown()) {
      ItemStack mainHandItem = player.getMainHandItem();
      if (mainHandItem.isEmpty() ||
        !(mainHandItem.getItem() instanceof IGunWeapon mainHandGun) ||
        !mainHandGun.gunShoot(player, mainHandItem, InteractionHand.MAIN_HAND)) {
        return;
      }

      PlayerIGunWeaponPayload.send(InteractionHand.MAIN_HAND, false);
    }
  }
}
