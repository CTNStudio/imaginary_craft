package ctn.singularity.lib.core;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * 指令事件
 */
@EventBusSubscriber(modid = LibMain.LIB_ID)
public class LibCommand {
	@SubscribeEvent
	public static void onRegisterCommands(RegisterCommandsEvent event) {
	}
}
