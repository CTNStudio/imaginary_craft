package ctn.imaginarycraft.core;

import ctn.imaginarycraft.api.event.rationality.RationalityModifyEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModLoader;

import java.util.Map;

public final class ModEventHooks {
  public static Map.Entry<Boolean, Float> sourceRationalityPre(Player player, float oldValue, float newValue) {
    var event = new RationalityModifyEvent.Pre(player, oldValue, newValue);
    ModLoader.postEvent(event);
    var canceled = event.isCanceled();
    return Map.entry(canceled,
      canceled ? event.getOldValue() : event.getNewValue());
  }

  public static void sourceRationalityPost(Player player, float oldValue, float newValue) {
    ModLoader.postEvent(new RationalityModifyEvent.Post(player, oldValue, newValue));
  }

  private ModEventHooks() {
  }
}
