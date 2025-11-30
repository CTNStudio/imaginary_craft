package ctn.imaginarycraft.registry;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * 指令事件
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class RegistryCommand {
  @SubscribeEvent
  public static void registry(RegisterCommandsEvent event) {
  }
}
