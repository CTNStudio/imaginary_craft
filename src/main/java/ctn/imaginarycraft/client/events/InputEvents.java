package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.common.payloads.entity.player.PlayerGunWeaponAimShootPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class InputEvents {

  @SubscribeEvent
  public static void onClientTick(ClientTickEvent.Post event) {
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft.screen != null) {
      return;
    }

    Options options = minecraft.options;
    if (!options.keyAttack.isDown() || !options.keyUse.isDown()) {
      return;
    }

    LocalPlayer player = minecraft.player;
    if (player == null || !player.isUsingItem()) {
      return;
    }

    ItemStack useItem = player.getUseItem();
    if (!(useItem.getItem() instanceof IGunWeapon iGunWeapon)) {
      return;
    }

    if (iGunWeapon.aimShoot(player, useItem)) {
      PayloadUtil.sendToServer(new PlayerGunWeaponAimShootPayload(player.getUsedItemHand() == InteractionHand.MAIN_HAND));
    }
  }
}
