package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.datagen.i18n.*;
import ctn.imaginarycraft.datagen.tag.*;
import ctn.imaginarycraft.init.world.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.data.event.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

/**
 * 数据生成主类
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class ModDatagen {
  @SubscribeEvent
  public static void gatherData(@NotNull GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput output = generator.getPackOutput();
    CompletableFuture<HolderLookup.Provider> completableFuture = event.getLookupProvider();

    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
    // 服务端数据生成
    buildServer(event, generator, new DatagenDatapackBuiltinEntries(output, new RegistrySetBuilder()
      .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap),
      completableFuture));
    DatagenBlockTag blockTag = new DatagenBlockTag(output, completableFuture, existingFileHelper);
    buildServer(event, generator, blockTag);
    buildServer(event, generator, new DatagenItemTag(output, completableFuture, blockTag.contentsGetter(), existingFileHelper));
    buildServer(event, generator, new DatagenDamageTypeTag(output, completableFuture, existingFileHelper));
    buildServer(event, generator, new DatagenCuriosTest(output, existingFileHelper, completableFuture));

    // 客户端数据生成
    DatagenI18n.init(output).forEach(i18 -> buildClient(event, generator, i18));
    buildClient(event, generator, new DatagenParticle(output, existingFileHelper));
    buildClient(event, generator, new DatagenItemModel(output, existingFileHelper));
    buildClient(event, generator, new DatagenBlockState(output, existingFileHelper));
    buildClient(event, generator, new DatagenSoundDefinitionsProvider(output, existingFileHelper));
  }

  private static <T extends DataProvider> @NotNull T buildClient(@NotNull GatherDataEvent event,
                                                                 @NotNull DataGenerator generator,
                                                                 T provider) {
    return generator.addProvider(event.includeClient(), provider);
  }

  private static <T extends DataProvider> @NotNull T buildServer(@NotNull GatherDataEvent event,
                                                                 @NotNull DataGenerator generator,
                                                                 T provider) {
    return generator.addProvider(event.includeServer(), provider);
  }
}
