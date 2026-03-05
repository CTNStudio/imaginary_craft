package ctn.imaginarycraft.datagen.tag;

import ctn.imaginarycraft.core.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.neoforged.neoforge.common.data.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public final class DatagenBlockTag extends BlockTagsProvider {

  public DatagenBlockTag(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable final ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, ImaginaryCraft.ID, existingFileHelper);
  }

  @Override
  protected void addTags(final HolderLookup.Provider provider) {

  }
}
