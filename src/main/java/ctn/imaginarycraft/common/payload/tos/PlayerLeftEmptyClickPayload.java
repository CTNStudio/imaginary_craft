package ctn.imaginarycraft.common.payload.tos;

import ctn.imaginarycraft.common.payload.api.ToServerPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.event.PlayerLeftEmptyClickEvent;
import ctn.imaginarycraft.util.PayloadUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModLoader;
import org.jetbrains.annotations.NotNull;

/**
 * 左键空点击
 */
public record PlayerLeftEmptyClickPayload(
  boolean isMainHand
) implements ToServerPayload {
  public static final Type<PlayerLeftEmptyClickPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_left_click_empty_payloads"));

  public static final StreamCodec<ByteBuf, PlayerLeftEmptyClickPayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.BOOL, PlayerLeftEmptyClickPayload::isMainHand,
    PlayerLeftEmptyClickPayload::new);

  public PlayerLeftEmptyClickPayload(InteractionHand interactionHand) {
    this(interactionHand == InteractionHand.MAIN_HAND);
  }

  public static void trigger(final Player player, final InteractionHand hand) {
    PlayerLeftEmptyClickEvent.Pre event = new PlayerLeftEmptyClickEvent.Pre(player, hand);
    if (player instanceof AbstractClientPlayer) {
      PayloadUtil.sendToServer(new PlayerLeftEmptyClickPayload(hand));
    }
    ModLoader.postEvent(event);
    if (event.isCanceled()) {
      return;
    }
    ModLoader.postEvent(new PlayerLeftEmptyClickEvent.Post(player, hand));
  }

  @Override
  public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  @Override
  public void work(ServerPlayer player) {
    trigger(player, isMainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
  }
}
