package ctn.imaginarycraft.common.payloads.player;


import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public record PlayerAnimationPayload(ResourceLocation controller,
                                     Optional<ResourceLocation> animation) implements CustomPacketPayload {
  public static final CustomPacketPayload.Type<PlayerAnimationPayload> TYPE = new CustomPacketPayload.Type<>(ImaginaryCraft.modRl("player_animation_payload"));

  public static final StreamCodec<ByteBuf, PlayerAnimationPayload> STREAM_CODEC = StreamCodec.composite(
    ResourceLocation.STREAM_CODEC, PlayerAnimationPayload::controller,
    ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), PlayerAnimationPayload::animation,
    PlayerAnimationPayload::new);

  /**
   * @param controller 动画控制器id
   * @param animation 动画id 如果为空则停止动画
   */
  public PlayerAnimationPayload(@NotNull ResourceLocation controller, @Nullable ResourceLocation animation) {
    this(controller, Optional.ofNullable(animation));
  }

  public static void toServer(final PlayerAnimationPayload data, final IPayloadContext context) {
    MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
    if (minecraftServer == null) {
      return;
    }
    Player senderPlayer = context.player();
    ServerLevel level = minecraftServer.getLevel(senderPlayer.level().dimension());
    if (level == null) {
      return;
    }
    UUID senderUUID = senderPlayer.getUUID();
    PlayerAnimationPayload customPacketPayload = new PlayerAnimationPayload(data.controller, data.animation);

    for (ServerPlayer player : level.players()) {
      if (player.getUUID().equals(senderUUID)) {
        continue;
      }
      PayloadUtil.sendToClient(player, customPacketPayload);
    }
  }

  public static void toClient(final PlayerAnimationPayload data, final IPayloadContext context) {
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
