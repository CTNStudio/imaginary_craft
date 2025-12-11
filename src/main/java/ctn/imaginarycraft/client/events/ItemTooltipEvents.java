package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.GatherEffectScreenTooltipsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

/**
 * 物品提示/描述处理
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public class ItemTooltipEvents {

  @SubscribeEvent
  public static void itemTooltip(final ItemTooltipEvent event) {
  }

  @SubscribeEvent
  public static void gatherEffectScreenTooltipsEvent(GatherEffectScreenTooltipsEvent event) {
  }
}
