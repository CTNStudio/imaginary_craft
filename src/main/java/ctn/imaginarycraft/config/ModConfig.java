package ctn.imaginarycraft.config;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class ModConfig extends ConfigUtil {
  public static final ModCommonConfig COMMON;
  public static final ModConfigSpec COMMON_SPEC;
  public static final ModServerConfig SERVER;
  public static final ModConfigSpec SERVER_SPEC;
  public static final ModClientConfig CLIENT;
  public static final ModConfigSpec CLIENT_SPEC;

  static {
    var commonPair = configure(ModCommonConfig::new);
    COMMON = commonPair.getLeft();
    COMMON_SPEC = commonPair.getRight();
    var serverPair = configure(ModServerConfig::new);
    SERVER = serverPair.getLeft();
    SERVER_SPEC = serverPair.getRight();
    var clientPair = configure(ModClientConfig::new);
    CLIENT = clientPair.getLeft();
    CLIENT_SPEC = clientPair.getRight();
  }

  public static void init(ModContainer modContainer) {
    ImaginaryCraft.LOGGER.info("Initialize the {} config files", ImaginaryCraft.NAME);
    modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ModConfig.COMMON_SPEC);
    modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.SERVER, ModConfig.SERVER_SPEC);
    modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.CLIENT, ModConfig.CLIENT_SPEC);
  }

  @SubscribeEvent
  public static void onLoad(final ModConfigEvent.Loading configEvent) {
    ImaginaryCraft.LOGGER.info("Loaded {} config file {}", ImaginaryCraft.NAME, configEvent.getConfig().getFileName());
  }

  @SubscribeEvent
  public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
    ImaginaryCraft.LOGGER.info("{} config just got changed on the file system!", ImaginaryCraft.NAME);
  }
}
