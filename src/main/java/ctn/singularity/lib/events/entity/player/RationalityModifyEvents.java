package ctn.singularity.lib.events.entity.player;

import ctn.singularity.lib.client.particles.TextParticle;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.event.rationality.RationalityModifyEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = LibMain.LIB_ID)
public final class RationalityModifyEvents {
  @SubscribeEvent
  public static void sourceRationalityPre(RationalityModifyEvent.Pre event) {

  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void sourceRationalityPost(RationalityModifyEvent.Post event) {
    Player player = event.getEntity();
    float difference = event.getNewValue() - event.getOldValue();

    if (player instanceof ServerPlayer serverPlayer) {
      if (difference > 0) {
        TextParticle.createHealParticles(player, TextParticle.getText(difference), true);
      }
    }
  }
}
