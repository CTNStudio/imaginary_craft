package ctn.imaginarycraft.core;

import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.init.world.ModAttachments;
import ctn.imaginarycraft.init.world.ModAttributes;
import ctn.imaginarycraft.init.world.ModDamageTypes;
import ctn.imaginarycraft.init.ModParticleTypes;
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

@Mod(ImaginaryCraft.ID)
public final class ImaginaryCraft {
  public static final String ID = "imaginarycraft";
  public static final String LIB_NAME = "ImaginaryCraft";
  public static final Logger LOGGER = LogManager.getLogger(ID);

  public ImaginaryCraft(IEventBus eventBus, ModContainer container) {
    LOGGER.debug("Server {}", LIB_NAME);
    ModConfig.init(container);
    ModAttributes.REGISTRY.register(eventBus);
    ModDamageTypes.REGISTRY.register(eventBus);
    ModAttachments.REGISTRY.register(eventBus);
    ModParticleTypes.REGISTRY.register(eventBus);
  }

  @Contract("_ -> new")
  public static @NotNull ResourceLocation modRl(final String name) {
    return ResourceLocation.fromNamespaceAndPath(ID, name);
  }

  @Contract(pure = true)
  public static @NotNull String modRlText(final String name) {
    return ID + ":" + name;
  }

  public static <T> @NotNull DeferredRegister<T> modRegister(Registry<T> registry) {
    return DeferredRegister.create(registry, ID);
  }

  public static <T> @NotNull DeferredRegister<T> modRegister(ResourceKey<Registry<T>> registry) {
    return DeferredRegister.create(registry, ID);
  }
}
