package ctn.imaginarycraft.core;

import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.datagen.DatagenCuriosTest;
import ctn.imaginarycraft.init.*;
import ctn.imaginarycraft.init.item.ModItems;
import ctn.imaginarycraft.init.tag.ModItemTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static top.theillusivec4.curios.api.CuriosApi.registerCurioPredicate;

@Mod(ImaginaryCraft.ID)
public final class ImaginaryCraft {
  public static final String ID = "imaginarycraft";
  public static final String NAME = "ImaginaryCraft";
  public static final Logger LOGGER = LogManager.getLogger(ID);

  public ImaginaryCraft(IEventBus eventBus, ModContainer container) {
    LOGGER.info("Server {}", NAME);
    ModConfig.init(container);
    ModAttributes.REGISTRY.register(eventBus);
    ModAttachments.REGISTRY.register(eventBus);
    ModParticleTypes.REGISTRY.register(eventBus);
    ModDataComponents.REGISTRY.register(eventBus);
    ModItems.init(eventBus);

    ModCreativeModeTabs.REGISTRY.register(eventBus);
    createValidators();
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

  public static void createValidators(ResourceLocation name, TagKey<Item> tagKey) {
    registerCurioPredicate(name, (slotResult) -> slotResult.stack().is(tagKey));
  }

  // 饰品
  public void createValidators() {
    createValidators(DatagenCuriosTest.EGO_CURIOS_VALIDATOR, ModItemTags.EGO_CURIOS);
    createValidators(DatagenCuriosTest.EGO_CURIOS_HEADWEAR_VALIDATOR, ModItemTags.EGO_CURIOS_HEADWEAR);
    createValidators(DatagenCuriosTest.EGO_CURIOS_HEAD_VALIDATOR, ModItemTags.EGO_CURIOS_HEAD);
    createValidators(DatagenCuriosTest.EGO_CURIOS_HINDBRAIN_VALIDATOR, ModItemTags.EGO_CURIOS_HINDBRAIN);
    createValidators(DatagenCuriosTest.EGO_CURIOS_EYE_VALIDATOR, ModItemTags.EGO_CURIOS_EYE);
    createValidators(DatagenCuriosTest.EGO_CURIOS_FACE_VALIDATOR, ModItemTags.EGO_CURIOS_FACE);
    createValidators(DatagenCuriosTest.EGO_CURIOS_CHEEK_VALIDATOR, ModItemTags.EGO_CURIOS_CHEEK);
    createValidators(DatagenCuriosTest.EGO_CURIOS_MASK_VALIDATOR, ModItemTags.EGO_CURIOS_MASK);
    createValidators(DatagenCuriosTest.EGO_CURIOS_MOUTH_VALIDATOR, ModItemTags.EGO_CURIOS_MOUTH);
    createValidators(DatagenCuriosTest.EGO_CURIOS_NECK_VALIDATOR, ModItemTags.EGO_CURIOS_NECK);
    createValidators(DatagenCuriosTest.EGO_CURIOS_BROOCH_VALIDATOR, ModItemTags.EGO_CURIOS_BROOCH);
    createValidators(DatagenCuriosTest.EGO_CURIOS_HAND_VALIDATOR, ModItemTags.EGO_CURIOS_HAND);
    createValidators(DatagenCuriosTest.EGO_CURIOS_GLOVE_VALIDATOR, ModItemTags.EGO_CURIOS_GLOVE);
    createValidators(DatagenCuriosTest.EGO_CURIOS_RIGHT_BACK_VALIDATOR, ModItemTags.EGO_CURIOS_RIGHT_BACK);
    createValidators(DatagenCuriosTest.EGO_CURIOS_LEFT_BACK_VALIDATOR, ModItemTags.EGO_CURIOS_LEFT_BACK);
  }
}
