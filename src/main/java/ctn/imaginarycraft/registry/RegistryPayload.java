package ctn.imaginarycraft.registry;

import ctn.imaginarycraft.common.payload.LivingEntityAttackStrengthTickerPayload;
import ctn.imaginarycraft.common.payload.api.ToClientPayload;
import ctn.imaginarycraft.common.payload.api.ToServerAndClientPayload;
import ctn.imaginarycraft.common.payload.api.ToServerPayload;
import ctn.imaginarycraft.common.payload.toc.PlayerDamagePayload;
import ctn.imaginarycraft.common.payload.tos.PlayerIGunWeaponPayload;
import ctn.imaginarycraft.common.payload.tos.PlayerKeyClickPayload;
import ctn.imaginarycraft.common.payload.tos.PlayerLeftEmptyClickPayload;
import ctn.imaginarycraft.common.payload.animation.PlayerAnimationPayload;
import ctn.imaginarycraft.common.payload.animation.PlayerRawAnimationPayload;
import ctn.imaginarycraft.common.payload.animation.PlayerStopAnimationPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class RegistryPayload {
  @SubscribeEvent
  public static void register(final RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar("1.0");
    // 接收来自服务端和客户端的数据 发送到 客户端和服务端
    playToServerAndClient(registrar, PlayerRawAnimationPayload.TYPE, PlayerRawAnimationPayload.STREAM_CODEC);
    playToServerAndClient(registrar, PlayerStopAnimationPayload.TYPE, PlayerStopAnimationPayload.STREAM_CODEC);
    playToServerAndClient(registrar, PlayerAnimationPayload.TYPE, PlayerAnimationPayload.STREAM_CODEC);
    playToServerAndClient(registrar, LivingEntityAttackStrengthTickerPayload.TYPE, LivingEntityAttackStrengthTickerPayload.STREAM_CODEC);

    // 接收来自服务端的数据 发送到 客户端
    playToClient(registrar, PlayerDamagePayload.TYPE, PlayerDamagePayload.STREAM_CODEC);

    // 接收来自客户端的数据 发送到 服务端
    playToServer(registrar, PlayerLeftEmptyClickPayload.TYPE, PlayerLeftEmptyClickPayload.STREAM_CODEC);
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
