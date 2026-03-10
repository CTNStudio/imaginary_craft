package ctn.imaginarycraft.core;

import ctn.imaginarycraft.config.*;
import ctn.imaginarycraft.core.registry.*;
import ctn.imaginarycraft.init.*;
import ctn.imaginarycraft.init.world.*;
import ctn.imaginarycraft.init.world.entity.*;
import ctn.imaginarycraft.init.world.item.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.registries.*;
import org.apache.logging.log4j.*;
import org.jetbrains.annotations.*;

@Mod(ImaginaryCraft.ID)
public final class ImaginaryCraft {
  public static final String ID = "imaginarycraft";
  public static final String NAME = "ImaginaryCraft";
  public static final Logger LOGGER = LogManager.getLogger(ID);

  public ImaginaryCraft(IEventBus eventBus, ModContainer container) {
    ModEpicjightEventHooks.listenerRegister();
    ModConfig.init(container);
    ModSoundEvents.REGISTRY.register(eventBus);
    ModMobEffects.REGISTRY.register(eventBus);
    ModAttributes.REGISTRY.register(eventBus);
    ModAttachments.REGISTRY.register(eventBus);
    ModParticleTypes.REGISTRY.register(eventBus);
    ModDataComponents.REGISTRY.register(eventBus);
    ModArmorMaterials.REGISTRY.register(eventBus);
    ModEntieyConditions.REGISTRY.register(eventBus);
    ModItems.init(eventBus);
    ModEntityTypes.init(eventBus);

    ModCreativeModeTabs.REGISTRY.register(eventBus);
    CurioRegistry.registry();
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
