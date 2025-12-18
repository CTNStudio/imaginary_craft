package ctn.imaginarycraft.events.entity.player;

import ctn.imaginarycraft.client.util.ParticleUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.event.rationality.RationalityModifyEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class RationalityModifyEvents {

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void sourceRationalityPost(RationalityModifyEvent.Post event) {
    Player player = event.getEntity();
    float difference = event.getOldValue() - event.getNewValue();

    if (player instanceof ServerPlayer serverPlayer && difference != 0) {
      ParticleUtil.createTextParticles(player, difference, true, difference < 0);
    }
  }
}
