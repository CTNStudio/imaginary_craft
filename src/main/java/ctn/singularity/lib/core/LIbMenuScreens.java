package ctn.singularity.lib.core;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = LibMain.LIB_ID, value = Dist.CLIENT)
public final class LIbMenuScreens {
	@SubscribeEvent
	public static void menuScreens(RegisterMenuScreensEvent event) {
	}
}
