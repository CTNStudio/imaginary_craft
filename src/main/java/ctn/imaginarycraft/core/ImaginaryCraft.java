package ctn.imaginarycraft.core;

import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.core.registry.CurioRegistry;
import ctn.imaginarycraft.core.registry.epicfight.EntityTypeRegistry;
import ctn.imaginarycraft.init.ModSoundEvents;
import ctn.imaginarycraft.init.epicfight.ModArmatures;
import ctn.imaginarycraft.init.epicfight.ModEntieyConditions;
import ctn.imaginarycraft.init.epicfight.ModMeshes;
import ctn.imaginarycraft.init.world.*;
import ctn.imaginarycraft.init.world.entity.ModEntityDataSerializers;
import ctn.imaginarycraft.init.world.entity.ModEntityTypes;
import ctn.imaginarycraft.init.world.item.ModArmorMaterials;
import ctn.imaginarycraft.init.world.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Mod(ImaginaryCraft.ID)
public final class ImaginaryCraft {
  public static final String ID = "imaginarycraft";
  public static final String NAME = "ImaginaryCraft";
  public static final Logger LOGGER = LogManager.getLogger(ID);

  public ImaginaryCraft(IEventBus eventBus, ModContainer container) {
	  ModEpicjightEventHooks.listenerRegister();
	  ModConfig.init(container);

	  ModArmatures.init();
	  ModMeshes.init();
	  ModColliders.init();

	  ModEntityDataSerializers.REGISTRY.register(eventBus);
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
    ModAbsorptionShieldsRegistry.init();

    ModCreativeModeTabs.REGISTRY.register(eventBus);
    CurioRegistry.registry();
	  EntityTypeRegistry.register();
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
