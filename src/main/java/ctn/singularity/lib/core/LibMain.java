package ctn.singularity.lib.core;

import ctn.singularity.lib.ModMainkt;
import ctn.singularity.lib.api.lobotomycorporation.LcDamage;
import ctn.singularity.lib.init.LibAttachments;
import ctn.singularity.lib.init.LibAttributes;
import ctn.singularity.lib.init.LibDamageTypes;
import ctn.singularity.lib.init.LibParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Mod(LibMain.LIB_ID)
public final class LibMain {
  public static final String LIB_ID = "singularity_lib";
  public static final String LIB_NAME = "SingularityLib";
  public static final Logger LOGGER = LogManager.getLogger(LIB_ID);

  public LibMain(IEventBus modEventBus, ModContainer modContainer) {
    LOGGER.error("Server {}", LIB_NAME);
    LibConfig.init(modContainer);
    LibAttributes.REGISTRY.register(modEventBus);
    LibDamageTypes.REGISTRY.register(modEventBus);
    LibAttachments.REGISTRY.register(modEventBus);
    LibParticleTypes.REGISTRY.register(modEventBus);
    ModMainkt.modMain(modEventBus);
    modEventBus.addListener(this::commonSetup);
  }

  private void commonSetup(FMLCommonSetupEvent event) {
    LcDamage.init();
  }

  @Contract("_ -> new")
  public static @NotNull ResourceLocation modRl(final String name) {
    return ResourceLocation.fromNamespaceAndPath(LIB_ID, name);
  }

  @Contract(pure = true)
  public static @NotNull String modRlText(final String name) {
    return LIB_ID + ":" + name;
  }

  public static <T> @NotNull DeferredRegister<T> modRegister(Registry<T> registry) {
    return DeferredRegister.create(registry, LIB_ID);
  }

  public static <T> @NotNull DeferredRegister<T> modRegister(ResourceKey<Registry<T>> registry) {
    return DeferredRegister.create(registry, LIB_ID);
  }
}
