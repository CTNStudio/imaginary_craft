package ctn.singularity.lib.core;

import ctn.singularity.lib.config.ConfigUtil;
import ctn.singularity.lib.config.LibClientConfig;
import ctn.singularity.lib.config.LibCommonConfig;
import ctn.singularity.lib.config.LibServerConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = LibMain.LIB_ID)
public final class LibConfig extends ConfigUtil {
  public static final LibCommonConfig COMMON;
  public static final ModConfigSpec COMMON_SPEC;
  public static final LibServerConfig SERVER;
  public static final ModConfigSpec SERVER_SPEC;
  public static final LibClientConfig CLIENT;
  public static final ModConfigSpec CLIENT_SPEC;

  static {
    var commonPair = configure(LibCommonConfig::new);
    COMMON = commonPair.getLeft();
    COMMON_SPEC = commonPair.getRight();
    var serverPair = configure(LibServerConfig::new);
    SERVER = serverPair.getLeft();
    SERVER_SPEC = serverPair.getRight();
    var clientPair = configure(LibClientConfig::new);
    CLIENT = clientPair.getLeft();
    CLIENT_SPEC = clientPair.getRight();
  }

  public static void init(ModContainer modContainer) {
    LibMain.LOGGER.error("Initialize the {} config files", LibMain.LIB_NAME);
    modContainer.registerConfig(ModConfig.Type.COMMON, LibConfig.COMMON_SPEC);
    modContainer.registerConfig(ModConfig.Type.SERVER, LibConfig.SERVER_SPEC);
    modContainer.registerConfig(ModConfig.Type.CLIENT, LibConfig.CLIENT_SPEC);
  }

  @SubscribeEvent
  public static void onLoad(final ModConfigEvent.Loading configEvent) {
    LibMain.LOGGER.error("Loaded {} config file {}", LibMain.LIB_NAME, configEvent.getConfig().getFileName());
  }

  @SubscribeEvent
  public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
    LibMain.LOGGER.error("{} config just got changed on the file system!", LibMain.LIB_NAME);
  }
}
