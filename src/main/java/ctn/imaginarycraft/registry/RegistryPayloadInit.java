package ctn.imaginarycraft.registry;

import ctn.imaginarycraft.common.payloads.entity.living.LivingEntityAttackStrengthTickerPayload;
import ctn.imaginarycraft.common.payloads.entity.player.PlayerDamagePayload;
import ctn.imaginarycraft.common.payloads.entity.player.PlayerIGunWeaponPayload;
import ctn.imaginarycraft.common.payloads.entity.player.PlayerLeftEmptyClickPayload;
import ctn.imaginarycraft.common.payloads.entity.player.animation.PlayerAnimationPayload;
import ctn.imaginarycraft.common.payloads.entity.player.animation.PlayerRawAnimationPayload;
import ctn.imaginarycraft.common.payloads.entity.player.animation.PlayerStopAnimationPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class RegistryPayloadInit {
  @SubscribeEvent
  public static void register(final RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar("1.0");
    // 接收来自服务端和客户端的数据
    registrar.playBidirectional(PlayerRawAnimationPayload.TYPE, PlayerRawAnimationPayload.STREAM_CODEC,
      new DirectionalPayloadHandler<>(PlayerRawAnimationPayload::toClient, PlayerRawAnimationPayload::toServer));
    registrar.playBidirectional(PlayerStopAnimationPayload.TYPE, PlayerStopAnimationPayload.STREAM_CODEC,
      new DirectionalPayloadHandler<>(PlayerStopAnimationPayload::toClient, PlayerStopAnimationPayload::toServer));
    registrar.playBidirectional(PlayerAnimationPayload.TYPE, PlayerAnimationPayload.STREAM_CODEC,
      new DirectionalPayloadHandler<>(PlayerAnimationPayload::toClient, PlayerAnimationPayload::toServer));
    registrar.playBidirectional(LivingEntityAttackStrengthTickerPayload.TYPE, LivingEntityAttackStrengthTickerPayload.STREAM_CODEC,
      new DirectionalPayloadHandler<>(LivingEntityAttackStrengthTickerPayload::to, LivingEntityAttackStrengthTickerPayload::to));

    // 接收来自服务端的数据
    registrar.playToClient(PlayerDamagePayload.TYPE, PlayerDamagePayload.STREAM_CODEC, PlayerDamagePayload::toClient);

    // 接收来自客户端的数据
    registrar.playToServer(PlayerLeftEmptyClickPayload.TYPE, PlayerLeftEmptyClickPayload.STREAM_CODEC, PlayerLeftEmptyClickPayload::toServer);
    registrar.playToServer(PlayerIGunWeaponPayload.TYPE, PlayerIGunWeaponPayload.STREAM_CODEC, PlayerIGunWeaponPayload::toServer);
    ImaginaryCraft.LOGGER.info("Registering payloads finish");
  }
}
