package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.client.eventexecute.InputEventExecute;
import ctn.imaginarycraft.common.payload.tos.PlayerLeftEmptyClickPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.GunWeaponUtil;
import ctn.imaginarycraft.util.PlayerKeyClickUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
  public static void onClientTickPre(ClientTickEvent.Pre event) {
    Minecraft minecraft = Minecraft.getInstance();
    LocalPlayer player = minecraft.player;
    if (player != null) {
      Options options = minecraft.options;
      PlayerKeyClickUtil.clientTickProcess(options, minecraft, player);
      if (minecraft.screen == null) {
      InputEventExecute.iGunWeapon(player, minecraft);
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void interactionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
    Minecraft instance = Minecraft.getInstance();
    if (instance.screen != null) {
      return;
    }

    LocalPlayer player = instance.player;
    if (player == null || !GunWeaponUtil.isHoldGunWeapon(player)) {
      return;
    }

    if (event.isAttack()) {
      ItemStack mainHandItem = player.getMainHandItem();
      if (mainHandItem.getItem() instanceof IGunWeapon) {
        event.setSwingHand(false);
        event.setCanceled(true);
      }
    }

    if (event.isUseItem()) {
      ItemStack offHandItem = player.getOffhandItem();
      if (offHandItem.getItem() instanceof IGunWeapon iGunWeapon && iGunWeapon.isOffHandShoot(player, offHandItem)) {
        event.setSwingHand(false);
        event.setCanceled(true);
      }
    }
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
    PlayerLeftEmptyClickPayload.send(entity, event.getHand());
  }
}
