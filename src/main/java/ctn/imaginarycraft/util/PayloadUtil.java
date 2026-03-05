package ctn.imaginarycraft.util;

import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.neoforged.neoforge.network.*;

public final class PayloadUtil {
  /**
   * 发送玩家数据包（服务端到客户端）
   */
  public static void sendToClient(ServerPlayer serverPlayer, CustomPacketPayload customPacketPayload) {
    PacketDistributor.sendToPlayer(serverPlayer, customPacketPayload);
  }

  /**
   * 发送玩家数据包（客户端到服务端）
   */
  public static void sendToServer(CustomPacketPayload customPacketPayload) {
    PacketDistributor.sendToServer(customPacketPayload);
  }
}
