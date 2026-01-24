package ctn.imaginarycraft.common.payload.animation;

import ctn.imaginarycraft.common.payload.api.ToServerAndClientPayload;
import ctn.imaginarycraft.util.UUIDFilterUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public abstract class IPlayerAnimationPayload implements ToServerAndClientPayload {
  protected final UUIDFilterUtil playPlayerUUID;
  protected final @Nullable UUIDFilterUtil receivePlayerUUID;
  protected final ResourceLocation controllerId;

  protected IPlayerAnimationPayload(UUIDFilterUtil playPlayerUUID, @Nullable UUIDFilterUtil receivePlayerUUID, ResourceLocation controllerId) {
    this.playPlayerUUID = playPlayerUUID;
    this.receivePlayerUUID = receivePlayerUUID;
    this.controllerId = controllerId;
  }

  protected IPlayerAnimationPayload(UUIDFilterUtil playPlayerUUID, Optional<@Nullable UUIDFilterUtil> receivePlayerUUID, ResourceLocation controllerId) {
    this(playPlayerUUID, receivePlayerUUID.orElse(null), controllerId);
  }

  @Override
  public void work(IPayloadContext context) {
    Player player = context.player();
    if (player instanceof AbstractClientPlayer) {
      getPlayers(playPlayerUUID, player, AbstractClientPlayer.class)
        .forEach(this::toClient);
    } else if (player instanceof ServerPlayer) {
      getPlayers(playPlayerUUID, player, ServerPlayer.class)
        .forEach(this::toServer);
    }
  }

  public <T extends Player> Collection<T> getPlayers(@Nullable UUIDFilterUtil uuidFilterUtil, Player player, Class<T> playerClass) {
    Collection<T> players = player.level().players().stream().map(playerClass::cast).toList();
    return uuidFilterUtil == null ? players : uuidFilterUtil.filter(players);
  }

  public UUIDFilterUtil getPlayPlayerUUID() {
    return playPlayerUUID;
  }

  public @Nullable UUIDFilterUtil getReceivePlayerUUID() {
    return receivePlayerUUID;
  }

  public ResourceLocation getControllerId() {
    return controllerId;
  }
}
