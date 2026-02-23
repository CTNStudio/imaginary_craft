package ctn.imaginarycraft.common.payload.tos;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.common.payload.api.ToServerPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.PayloadUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record PlayerIGunWeaponPayload(
  int operation
) implements ToServerPayload {
  public static final Type<PlayerIGunWeaponPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_gun_weapon_payload"));

  public static final StreamCodec<ByteBuf, PlayerIGunWeaponPayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.INT, PlayerIGunWeaponPayload::operation,
    PlayerIGunWeaponPayload::new);

  /**
   * @param hand  0b1:主手 0b0:副手
   * @param sim   0b1:瞄准 0b0:不瞄准
   * @param shoot 0b1:射击 0b0:不射击
   */
  public PlayerIGunWeaponPayload(InteractionHand hand, boolean sim, boolean shoot) {
    this((setHand(hand) | setSim(sim) | setShoot(shoot)));
  }

  /**
   * @param isMainHand 0b1:主手 0b0:副手
   * @param sim        0b1:瞄准 0b0:不瞄准
   * @param shoot      0b1:射击 0b0:不射击
   */
  public PlayerIGunWeaponPayload(boolean isMainHand, boolean sim, boolean shoot) {
    this(((isMainHand ? 0b1 : 0b0) | setSim(sim) | setShoot(shoot)));
  }

  public static void send(InteractionHand usedItemHand, boolean sim, boolean shoot) {
    PayloadUtil.sendToServer(new PlayerIGunWeaponPayload(usedItemHand, sim, shoot));
  }

  private static int setHand(InteractionHand hand) {
    return hand == InteractionHand.MAIN_HAND ? 0b1 : 0b0;
  }

  private static int setShoot(boolean shoot) {
    return (shoot ? 0b100 : 0b0);
  }

  private static int setSim(boolean sim) {
    return (sim ? 0b10 : 0b0);
  }

  public InteractionHand getHand() {
    return (operation & 0b1) == 1 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
  }

  /**
   * 瞄准
   */
  public boolean isSim() {
    return (operation & 0b10) >> 1 == 0b1;
  }

  /**
   * 射击
   */
  public boolean isShoot() {
    return (operation & 0b100) >> 2 == 0b1;
  }

  /**
   * 瞄准射击
   */
  public boolean isAimShoot() {
    return (operation & 0b110) >> 1 == 0b11;
  }

  /**
   * 发送到服务端
   */
  @Override
  public void work(ServerPlayer player) {
    InteractionHand hand = getHand();
    ItemStack itemStack = player.getItemInHand(hand);
    if (!(itemStack.getItem() instanceof IGunWeapon iGunWeapon)) {
      return;
    }
    if (iGunWeapon.isGunAim(player, itemStack) && isAimShoot()) {
      iGunWeapon.gunAimShoot(player, itemStack, hand);
      return;
    }
    if (isShoot()) {
      iGunWeapon.gunShoot(player, itemStack, hand);
    }
  }

  @Override
  public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}

