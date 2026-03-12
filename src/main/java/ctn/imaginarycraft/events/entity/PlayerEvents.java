package ctn.imaginarycraft.events.entity;

import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.eventexecute.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.*;

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

  /**
   * 玩家重生或维度切换后
   */
  @SubscribeEvent
  public static void reset(PlayerEvent.Clone event) {
  }

  /**
   * 登录到世界-此时客户端玩家已创建
   */
  @SubscribeEvent
  public static void loggedIn(PlayerEvent.PlayerLoggedInEvent event) {
  }
}
