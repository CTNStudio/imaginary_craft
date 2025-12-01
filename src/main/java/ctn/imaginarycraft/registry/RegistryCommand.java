package ctn.imaginarycraft.registry;

import com.mojang.brigadier.CommandDispatcher;
import ctn.imaginarycraft.common.command.RationalityCommands;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.commands.CommandSourceStack;
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
    CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
    RationalityCommands.processRationality(dispatcher);
  }

}
