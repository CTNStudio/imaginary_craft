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

public record PlayerGunWeaponAimShootPayload(boolean mainHand) implements CustomPacketPayload {
  public static final Type<PlayerGunWeaponAimShootPayload> TYPE = new Type<>(ImaginaryCraft.modRl("player_gun_weapon_aim_shoot_payload"));

  public static final StreamCodec<ByteBuf, PlayerGunWeaponAimShootPayload> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.BOOL, PlayerGunWeaponAimShootPayload::mainHand,
    PlayerGunWeaponAimShootPayload::new
  );

  /**
   * 发送到服务端
   */
  public static void toServer(final PlayerGunWeaponAimShootPayload data, final IPayloadContext context) {
    context.enqueueWork(() -> {
      Player player = context.player();
      ItemStack itemStack = player.getItemInHand(data.mainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
      if (itemStack.getItem() instanceof IGunWeapon iGunWeapon) {
        iGunWeapon.aimShoot(player, itemStack);
      }
    });
  }

  @Override
  public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}

