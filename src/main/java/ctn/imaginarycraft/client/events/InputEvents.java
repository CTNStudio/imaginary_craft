package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.client.eventexecute.InputEventExecute;
import ctn.imaginarycraft.common.payloads.entity.player.PlayerLeftEmptyClickPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.GunWeaponUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class InputEvents {

  @SubscribeEvent
  public static void onClientTick(ClientTickEvent.Post event) {
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft.screen != null) {
      return;
    }

    LocalPlayer player = minecraft.player;
    if (player != null) {
      InputEventExecute.iGunWeapon(player, minecraft);
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void interactionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
    Minecraft instance = Minecraft.getInstance();
    Player player = instance.player;
    if (player == null) {
      return;
    }

    // 不合并是因为合并会导致难以辨认代码
    if (instance.screen != null) {
      return;
    }

    if (!event.isAttack() || !GunWeaponUtil.isHoldGunWeapon(player)) {
      return;
    }

//    if (!event.isUseItem() || !player.isUsingItem()) {
//      return;
//    }
//
//    ItemStack useItem = player.getUseItem();
//    if (!(useItem.getItem() instanceof IGunWeapon iGunWeapon) || iGunWeapon.isAim(player, useItem)) {
//      return;
//    }

    event.setSwingHand(false);
    event.setCanceled(true);
  }

  /**
   * 左键点击空（客户端）
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void playerInteractEventLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
    Player entity = event.getEntity();
    if (entity.isUsingItem() && entity.getUseItem().getItem() instanceof IGunWeapon) {
      return;
    }
    PlayerLeftEmptyClickPayload.trigger(entity, event.getHand());
  }
}
