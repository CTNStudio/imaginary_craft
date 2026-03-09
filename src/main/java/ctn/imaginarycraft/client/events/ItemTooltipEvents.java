package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.player.*;

import java.util.*;

/**
 * 物品提示/描述处理
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class ItemTooltipEvents {

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void itemTooltip(final ItemTooltipEvent event) {
    ItemStack itemStack = event.getItemStack();
    List<Component> toolTip = event.getToolTip();
    int size = toolTip.size();

    // 添加物品等级 ToolTip
    LcLevel lcLevel = LcLevelUtil.getLevel(itemStack);
    if (lcLevel != null) {
      MutableComponent component = Component
        .literal(lcLevel.getName().toUpperCase())
        .withColor(lcLevel.getColourValue());
      toolTip.add(Math.clamp(size, 0, 1), component);
    }
  }

  @SubscribeEvent
  public static void gatherEffectScreenTooltipsEvent(GatherEffectScreenTooltipsEvent event) {
  }
}
