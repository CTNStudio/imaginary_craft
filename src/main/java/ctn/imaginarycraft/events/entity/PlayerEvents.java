package ctn.imaginarycraft.events.entity;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.api.IPlayerItemAttackClick;
import ctn.imaginarycraft.client.util.ParticleUtil;
import ctn.imaginarycraft.common.payload.tos.PlayerLeftEmptyClickPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.event.PlayerLeftEmptyClickEvent;
import ctn.imaginarycraft.event.rationality.RationalityModifyEvent;
import ctn.imaginarycraft.eventexecute.RationalityEventExecutes;
import ctn.imaginarycraft.util.PlayerKeyClickUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * 玩家相关事件
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class PlayerEvents {

  @SubscribeEvent
  public static void tickPre(PlayerTickEvent.Pre event) {
    Player player = event.getEntity();
    if (player instanceof ServerPlayer serverPlayer) {
      RationalityEventExecutes.refreshRationalityValue(serverPlayer);
    }
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void rationalityModifyPost(RationalityModifyEvent.Post event) {
    Player player = event.getEntity();

    if (!(player instanceof ServerPlayer serverPlayer)) {
      return;
    }

    float difference = event.getOldValue() - event.getNewValue();
    if (difference == 0) {
      return;
    }

    ParticleUtil.createDamageTextParticles(player, difference, true, difference < 0);
  }

  /**
   * 左键点击方块
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void playerInteractEventLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
    Player entity = event.getEntity();
    if (entity.isUsingItem() && entity.getUseItem().getItem() instanceof IGunWeapon) {
      event.setCanceled(true);
      return;
    }

    PlayerLeftEmptyClickPayload.send(entity, event.getHand());
  }

  /**
   * 左键点击空
   */
  @SubscribeEvent
  public static void playerLeftClickEmptyEventPre(PlayerLeftEmptyClickEvent.Post event) {
    playerLeftClickEmpty(event.getItemStack(), event.getEntity());
  }

  private static void playerLeftClickEmpty(ItemStack itemStack, Player player) {
    if (itemStack.getItem() instanceof IPlayerItemAttackClick itemLeftClick) {
      itemLeftClick.attackClickEmpty(player, itemStack);
    }
  }

  /**
   * 玩家重生或维度切换后
   */
  @SubscribeEvent
  public static void reset(PlayerEvent.Clone event) {
  }

  /** 登录到世界-此时客户端玩家已创建 */
  @SubscribeEvent
  public static void loggedIn(PlayerEvent.PlayerLoggedInEvent event) {
  }
}
