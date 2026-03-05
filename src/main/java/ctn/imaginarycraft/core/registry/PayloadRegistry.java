package ctn.imaginarycraft.core.registry;

import ctn.imaginarycraft.common.payload.*;
import ctn.imaginarycraft.common.payload.api.*;
import ctn.imaginarycraft.common.payload.toc.*;
import ctn.imaginarycraft.common.payload.tos.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.network.event.*;
import net.neoforged.neoforge.network.handling.*;
import net.neoforged.neoforge.network.registration.*;
import org.jetbrains.annotations.*;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class PayloadRegistry {
  @SubscribeEvent
  public static void register(final RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar("1.0");
    // 接收来自服务端和客户端的数据 发送到 客户端和服务端
    playToServerAndClient(registrar, LivingEntityAttackStrengthTickerPayload.TYPE, LivingEntityAttackStrengthTickerPayload.STREAM_CODEC);

    // 接收来自服务端的数据 发送到 客户端
    playToClient(registrar, PlayerDamagePayload.TYPE, PlayerDamagePayload.STREAM_CODEC);

    // 接收来自客户端的数据 发送到 服务端
    playToServer(registrar, PlayerIGunWeaponPayload.TYPE, PlayerIGunWeaponPayload.STREAM_CODEC);
    playToServer(registrar, PlayerKeyClickPayload.TYPE, PlayerKeyClickPayload.STREAM_CODEC);
    ImaginaryCraft.LOGGER.info("Registering payloads finish");
  }

  private static <T extends ToServerAndClientPayload> @NotNull PayloadRegistrar playToServerAndClient(PayloadRegistrar registrar, CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
    return registrar.playBidirectional(type, reader,
      new DirectionalPayloadHandler<>(ToServerAndClientPayload::handle, ToServerAndClientPayload::handle));
  }

  private static <T extends ToServerPayload> @NotNull PayloadRegistrar playToServer(PayloadRegistrar registrar, CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
    return registrar.playToServer(type, reader, ToServerAndClientPayload::handle);
  }

  private static <T extends ToClientPayload> @NotNull PayloadRegistrar playToClient(PayloadRegistrar registrar, CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> reader) {
    return registrar.playToClient(type, reader, ToServerAndClientPayload::handle);
  }
}
