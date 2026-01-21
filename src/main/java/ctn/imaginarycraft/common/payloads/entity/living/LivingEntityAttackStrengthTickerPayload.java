package ctn.imaginarycraft.common.payloads.entity.living;

import ctn.imaginarycraft.mixed.ILivingEntity;
import ctn.imaginarycraft.core.ImaginaryCraft;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record LivingEntityAttackStrengthTickerPayload(
  int attackStrengthTicker
) implements CustomPacketPayload {
  public static final Type<LivingEntityAttackStrengthTickerPayload> TYPE = new Type<>(ImaginaryCraft.modRl("living_entity_attack_strength_ticker_payload"));
  public static final StreamCodec<ByteBuf, LivingEntityAttackStrengthTickerPayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.INT, LivingEntityAttackStrengthTickerPayload::attackStrengthTicker,
    LivingEntityAttackStrengthTickerPayload::new);

  public static void to(final LivingEntityAttackStrengthTickerPayload data, final IPayloadContext context) {
    context.enqueueWork(() -> ILivingEntity.of(context.player()).setImaginarycraft$AttackStrengthTicker(data.attackStrengthTicker()));
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
