package ctn.imaginarycraft.client.events;

import ctn.imaginarycraft.api.LcLevel;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.LcLevelUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
		LcLevel lcLevel = LcLevelUtil.getLevel(itemStack);
		String upperCase;
		int colourValue;
		if (lcLevel != null) {
			upperCase = lcLevel.getName().toUpperCase();
			colourValue = lcLevel.getColourValue();
		} else {
			upperCase = "???";
			colourValue = 0xbd7118;
		}
		MutableComponent component = Component.literal(upperCase).withColor(colourValue);
		toolTip.add(Math.clamp(size, 0, 1), component);
	}

	@SubscribeEvent
	public static void gatherEffectScreenTooltipsEvent(GatherEffectScreenTooltipsEvent event) {
	}
}
