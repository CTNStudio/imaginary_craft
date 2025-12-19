package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.zigythebird.playeranim.PlayerAnimLibMod.ANIMATION_LAYER_ID;

@EventBusSubscriber(modid = ImaginaryCraft.ID, value = Dist.CLIENT)
public final class PlayerEvents {
  @SubscribeEvent
  public static void tickPre(PlayerTickEvent.Pre event) {
    Player player = event.getEntity();
    ItemStack itemStack = player.getMainHandItem();
    if (itemStack.is(EgoWeaponItems.MAGIC_BULLET)) {
      PlayerAnimUtil.play(player, ANIMATION_LAYER_ID, (controller) ->
        controller.triggerAnimation(PlayerAnimUtil.STANDBY));
    }
  }
}
