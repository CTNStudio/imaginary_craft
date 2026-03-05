package ctn.imaginarycraft.datagen;

import ctn.imaginarycraft.core.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.neoforged.neoforge.common.data.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * 创建一个数据包内置条目
 */
public final class DatagenDatapackBuiltinEntries extends DatapackBuiltinEntriesProvider {

  public DatagenDatapackBuiltinEntries(PackOutput output, RegistrySetBuilder datapackEntriesBuilder, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, datapackEntriesBuilder, Set.of(ImaginaryCraft.ID));
  }
}
