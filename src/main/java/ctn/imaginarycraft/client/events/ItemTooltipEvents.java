package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.GatherEffectScreenTooltipsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

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
    LcLevelType lcLevelType = LcLevelUtil.getLevel(itemStack);
    if (lcLevelType != null) {
      toolTip.add(Math.clamp(size, 0, 1), Component
        .literal(lcLevelType.getName().toUpperCase())
        .withColor(lcLevelType.getColourValue()));
    }
  }

  @SubscribeEvent
  public static void gatherEffectScreenTooltipsEvent(GatherEffectScreenTooltipsEvent event) {
  }
}
