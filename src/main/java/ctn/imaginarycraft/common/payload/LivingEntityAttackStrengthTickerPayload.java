package ctn.imaginarycraft.common.payload;

import ctn.imaginarycraft.common.payload.api.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.mixed.*;
import io.netty.buffer.*;
import net.minecraft.client.player.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;

public record LivingEntityAttackStrengthTickerPayload(
  int attackStrengthTicker
) implements ToServerAndClientPayload {
  public static final Type<LivingEntityAttackStrengthTickerPayload> TYPE = new Type<>(ImaginaryCraft.modRl("living_entity_attack_strength_ticker_payload"));
  public static final StreamCodec<ByteBuf, LivingEntityAttackStrengthTickerPayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.INT, LivingEntityAttackStrengthTickerPayload::attackStrengthTicker,
    LivingEntityAttackStrengthTickerPayload::new);

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  @Override
  public void toServer(ServerPlayer serverPlayer) {
    to(serverPlayer);
  }

  @Override
  public void toClient(AbstractClientPlayer clientPlayer) {
    to(clientPlayer);
  }

  public void to(Player player) {
    ILivingEntity.of(player).setImaginarycraft$AttackStrengthTicker(attackStrengthTicker());
  }
}
