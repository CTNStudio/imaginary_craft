package ctn.imaginarycraft.common.payload.toc;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.client.gui.hudlayers.screenfilter.*;
import ctn.imaginarycraft.common.payload.api.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.util.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record PlayerDamagePayload(
  Optional<@Nullable LcDamageType> lcDamageType,
  float damage
) implements ToClientPayload {
  public static final Type<PlayerDamagePayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_damage_payload"));

  public static final StreamCodec<ByteBuf, PlayerDamagePayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.optional(LcDamageType.STREAM_CODEC), PlayerDamagePayload::lcDamageType,
    ByteBufCodecs.FLOAT, PlayerDamagePayload::damage,
    PlayerDamagePayload::new
  );

  public PlayerDamagePayload(@Nullable LcDamageType lcDamageType, float damage) {
    this(Optional.ofNullable(lcDamageType), damage);
  }

  public static void send(ServerPlayer player, LcDamageType lcDamageType, float newDamage) {
    PayloadUtil.sendToClient(player, new PlayerDamagePayload(lcDamageType, newDamage));
  }

  @Override
  public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  @Override
  public void work(Player player) {
    LcDamageScreenFilterLayer.INSTANCE.addFilter(lcDamageType().orElse(null));
  }
}

