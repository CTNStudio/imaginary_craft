package ctn.imaginarycraft.common.payloads.entity.player;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.core.ImaginaryCraft;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record PlayerIGunWeaponPayload(byte operation) implements CustomPacketPayload {
  public static final Type<PlayerIGunWeaponPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_i_gun_weapon_payload"));

  public static final StreamCodec<ByteBuf, PlayerIGunWeaponPayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.BYTE, PlayerIGunWeaponPayload::operation,
    PlayerIGunWeaponPayload::new
  );

  public PlayerIGunWeaponPayload(InteractionHand hand, boolean sim, boolean shoot) {
    this((byte) (setHand(hand) | setSim(sim) | setShoot(shoot)));
  }

  public PlayerIGunWeaponPayload(boolean isMainHand, boolean sim, boolean shoot) {
    this((byte) ((isMainHand ? 0b1 : 0b0) | setSim(sim) | setShoot(shoot)));
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
    return ((int) operation & 0b1) == 1 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
  }

  /**
   * 瞄准
   */
  public boolean isSim() {
    return ((int) operation & 0b10) >> 1 == 0b1;
  }

  /**
   * 射击
   */
  public boolean isShoot() {
    return ((int) operation & 0b100) >> 2 == 0b1;
  }

  /**
   * 瞄准射击
   */
  public boolean isAimShoot() {
    return ((int) operation & 0b110) >> 1 == 0b11;
  }

  /**
   * 发送到服务端
   */
  public static void toServer(final PlayerIGunWeaponPayload data, final IPayloadContext context) {
    context.enqueueWork(() -> {
      Player player = context.player();
      InteractionHand hand = data.getHand();
      ItemStack itemStack = player.getItemInHand(hand);
      if (!(itemStack.getItem() instanceof IGunWeapon iGunWeapon)) {
        return;
      }
      if (iGunWeapon.isAim(player, itemStack)) {
        if (data.isAimShoot()) {
          iGunWeapon.aimShoot(player, itemStack);
          return;
        }
      }
      if (data.isShoot()) {
        iGunWeapon.shoot(player, itemStack, hand);
      }
    });
  }

  @Override
  public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}

