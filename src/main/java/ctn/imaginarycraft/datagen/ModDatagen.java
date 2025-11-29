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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * 数据生成主类
 */
@EventBusSubscriber(modid = ImaginaryCraft.ID)
public final class ModDatagen {
  public static final MutableComponent TRANSLATABLE = Component.translatable("pack." + ImaginaryCraft.ID + ".description");

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    DataGenerator.PackGenerator packGenerator = generator.getVanillaPack(event.includeServer());
    PackOutput output = generator.getPackOutput();
    ExistingFileHelper fileHelper = event.getExistingFileHelper();
    CompletableFuture<HolderLookup.Provider> completableFuture = event.getLookupProvider();

    generator.addProvider(true, new PackMetadataGenerator(output)
      .add(PackMetadataSection.TYPE, new PackMetadataSection(TRANSLATABLE, DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA))));


    // 客户端数据生成
    buildClient(event, generator, new DatagenI18ZhCn(output));
    buildClient(event, generator, new DatagenParticle(output, fileHelper));

    // 服务端数据生成
    buildServer(event, generator, new DatagenDatapackBuiltinEntries(output, completableFuture));
    DatagenBlockTag blockTag = new DatagenBlockTag(output, completableFuture, fileHelper);
    buildServer(event, generator, blockTag);
    buildServer(event, generator, new DatagenItemTag(output, completableFuture, blockTag.contentsGetter(), fileHelper));
    buildServer(event, generator, new DatagenDamageTypeTag(output, completableFuture, fileHelper));
  }

  private static <T extends DataProvider> @NotNull T buildClient(GatherDataEvent event,
                                                                 DataGenerator generator,
                                                                 T provider) {
    return generator.addProvider(true, provider);
  }

  private static <T extends DataProvider> @NotNull T buildServer(GatherDataEvent event,
                                                                 DataGenerator generator,
                                                                 T provider) {
    return generator.addProvider(true, provider);
  }
}
