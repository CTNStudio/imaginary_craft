package ctn.singularity.lib.core;

import ctn.singularity.lib.datagen.DatagenDatapackBuiltinEntries;
import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * 数据生成主类
 */
@EventBusSubscriber(modid = LibMain.LIB_ID)
public final class LibDatagen {
  public static final MutableComponent TRANSLATABLE = Component.translatable("pack." + LibMain.LIB_ID + ".description");

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    boolean server = event.includeServer();
    boolean client = event.includeClient();
    var dataGenerator = event.getGenerator();
    var packGenerator = dataGenerator.getVanillaPack(server);
    var output = dataGenerator.getPackOutput();
    var helper = event.getExistingFileHelper();
    var completableFuture = event.getLookupProvider();

    dataGenerator.addProvider(true, new PackMetadataGenerator(output)
      .add(PackMetadataSection.TYPE, new PackMetadataSection(TRANSLATABLE, DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA))));


    // 客户端数据生成

    // 服务端数据生成
    dataGenerator.addProvider(event.includeServer(), new DatagenDatapackBuiltinEntries(output, completableFuture));
  }
}
