package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.ModDamageTypes;
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
public final class DatagenDatapackBuiltinEntries extends DatapackBuiltinEntriesProvider {

  public DatagenDatapackBuiltinEntries(PackOutput output, RegistrySetBuilder datapackEntriesBuilder, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, datapackEntriesBuilder, Set.of(ImaginaryCraft.ID));
  }
}
