package ctn.imaginarycraft.common.payloads.entity.player;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.client.gui.layers.LcDamageScreenFilterLayer;
import ctn.imaginarycraft.core.ImaginaryCraft;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record PlayerDamagePayload(Optional<@Nullable LcDamageType> lcDamageType,
                                  float damage) implements CustomPacketPayload {
  public static final Type<PlayerDamagePayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_damage_payload"));

  public static final StreamCodec<ByteBuf, PlayerDamagePayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.optional(LcDamageType.STREAM_CODEC), PlayerDamagePayload::lcDamageType,
    ByteBufCodecs.FLOAT, PlayerDamagePayload::damage,
    PlayerDamagePayload::new
  );

  public PlayerDamagePayload(@Nullable LcDamageType lcDamageType, float damage) {
    this(Optional.ofNullable(lcDamageType), damage);
  }

  /**
   * 发送到客户端
   */
  public static void toClient(final PlayerDamagePayload data, final IPayloadContext context) {
    LcDamageScreenFilterLayer.INSTANCE.addFilter(data.lcDamageType().orElse(null));
  }

  @Override
  public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}

