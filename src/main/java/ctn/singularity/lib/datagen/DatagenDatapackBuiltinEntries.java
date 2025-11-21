package ctn.singularity.lib.datagen;

import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.LibDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * 创建一个数据包内置条目
 */
public class DatagenDatapackBuiltinEntries extends DatapackBuiltinEntriesProvider {
  public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
    .add(Registries.DAMAGE_TYPE, LibDamageTypes::bootstrap);

  public DatagenDatapackBuiltinEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, BUILDER, Set.of(LibMain.LIB_ID));
  }
}
