package ctn.imaginarycraft.common.payloads.player;

import com.zigythebird.playeranimcore.animation.Animation;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimRawAnimation;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimStandardFadePlayerAnim;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.player.AbstractClientPlayer;
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

/**
 * 玩家动画数据包
 *
 * @param controller    动画控制器id
 * @param startAnimFrom 动画开始播放的时间
 * @param rawAnimation  动画链
 * @param withFade      动画淡入淡出 如果为空则无淡入淡出
 */
public record PlayerRawAnimationPayload(ResourceLocation controller,
                                        PlayerAnimRawAnimation rawAnimation,
                                        float startAnimFrom,
                                        Optional<PlayerAnimStandardFadePlayerAnim> withFade) implements CustomPacketPayload {
  public static final CustomPacketPayload.Type<PlayerRawAnimationPayload> TYPE = new CustomPacketPayload.Type<>(ImaginaryCraft.modRl("player_raw_animation_payload"));

  public static final StreamCodec<ByteBuf, PlayerRawAnimationPayload> STREAM_CODEC = StreamCodec.composite(
    ResourceLocation.STREAM_CODEC, PlayerRawAnimationPayload::controller,
    PlayerAnimRawAnimation.STREAM_CODEC, PlayerRawAnimationPayload::rawAnimation,
    ByteBufCodecs.FLOAT, PlayerRawAnimationPayload::startAnimFrom,
    ByteBufCodecs.optional(PlayerAnimStandardFadePlayerAnim.STREAM_CODEC), PlayerRawAnimationPayload::withFade,
    PlayerRawAnimationPayload::new);

  /**
   * 玩家动画数据包(无过度动画，无加速)
   *
   * @param controller  动画控制器id
   * @param animationId 动画id 如果为空则停止动画
   */
  public PlayerRawAnimationPayload(@NotNull ResourceLocation controller, @Nullable ResourceLocation animationId) {
    this(controller, animationId, 0);
  }

  /**
   * 玩家动画数据包(无过度动画，无加速)
   *
   * @param controller  动画控制器id
   * @param animationId 动画id 如果为空则停止动画
   */
  public PlayerRawAnimationPayload(@NotNull ResourceLocation controller, @Nullable ResourceLocation animationId,
                                   float startAnimFrom) {
    this(controller, animationId, startAnimFrom, null);
  }

  /**
   * 玩家动画数据包
   *
   * @param controller  动画控制器id
   * @param animationId 动画id 如果为空则停止动画
   * @param withFade    动画淡入淡出 如果为空则无淡入淡出
   */
  public PlayerRawAnimationPayload(@NotNull ResourceLocation controller, ResourceLocation animationId,
                                   float startAnimFrom, @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(controller, PlayerAnimRawAnimation.begin().then(animationId, Animation.LoopType.DEFAULT), startAnimFrom, withFade);
  }

  /**
   * 玩家动画数据包
   *
   * @param controller   动画控制器id
   * @param rawAnimation 动画id 如果为空则停止动画
   * @param withFade     动画淡入淡出 如果为空则无淡入淡出
   */
  public PlayerRawAnimationPayload(@NotNull ResourceLocation controller, PlayerAnimRawAnimation rawAnimation,
                                   float startAnimFrom, @Nullable PlayerAnimStandardFadePlayerAnim withFade) {
    this(controller, rawAnimation, startAnimFrom, Optional.ofNullable(withFade));
  }

  public static void toServer(final PlayerRawAnimationPayload data, final IPayloadContext context) {
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
    for (ServerPlayer player : level.players()) {
      if (player.getUUID().equals(senderUUID)) {
        continue;
      }
      PayloadUtil.sendToClient(player, data);
    }
  }

  public static void toClient(final PlayerRawAnimationPayload data, final IPayloadContext context) {
    PlayerAnimUtil.playRawAnimationClient((AbstractClientPlayer) context.player(), data.controller, data.rawAnimation,
      data.startAnimFrom, data.withFade.orElse(null));
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
