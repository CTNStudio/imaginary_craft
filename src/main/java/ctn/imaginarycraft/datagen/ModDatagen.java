package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.tag.DatagenBlockTag;
import ctn.imaginarycraft.datagen.tag.DatagenDamageTypeTag;
import ctn.imaginarycraft.datagen.tag.DatagenItemTag;
import ctn.imaginarycraft.init.world.ModDamageTypes;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 数据生成主类
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class ModDatagen {
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput output = generator.getPackOutput();
    CompletableFuture<HolderLookup.Provider> completableFuture = event.getLookupProvider();

    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
    generator.addProvider(true, new PackMetadataGenerator(output)
      .add(PackMetadataSection.TYPE, new PackMetadataSection(
        Component.translatable("pack." + ImaginaryCraft.ID + ".description"),
        DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
        Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    // 服务端数据生成
    buildServer(event, generator, new DatagenDatapackBuiltinEntries(output, new RegistrySetBuilder()
      .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap),
      completableFuture));
    DatagenBlockTag blockTag = new DatagenBlockTag(output, completableFuture, existingFileHelper);
    buildServer(event, generator, blockTag);
    buildServer(event, generator, new DatagenItemTag(output, completableFuture, blockTag.contentsGetter(), existingFileHelper));
    buildServer(event, generator, new DatagenDamageTypeTag(output, completableFuture, existingFileHelper));

    // 客户端数据生成
    buildClient(event, generator, new DatagenI18ZhCn(output));
    buildClient(event, generator, new DatagenParticle(output, existingFileHelper));
  }

  private static <T extends DataProvider> @NotNull T buildClient(GatherDataEvent event,
                                                                 DataGenerator generator,
                                                                 T provider) {
    return generator.addProvider(event.includeClient(), provider);
  }

  private static <T extends DataProvider> @NotNull T buildServer(GatherDataEvent event,
                                                                 DataGenerator generator,
                                                                 T provider) {
    return generator.addProvider(event.includeServer(), provider);
  }
}
