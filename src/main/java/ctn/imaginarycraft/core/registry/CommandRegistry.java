package ctn.imaginarycraft.core.registry;

import com.mojang.brigadier.*;
import ctn.imaginarycraft.common.command.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.commands.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.*;

/**
 * 指令事件
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class CommandRegistry {
  @SubscribeEvent
  public static void registry(RegisterCommandsEvent event) {
    CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
    RationalityCommands.processRationality(dispatcher);
  }
}
