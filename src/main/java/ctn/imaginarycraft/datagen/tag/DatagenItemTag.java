package ctn.imaginarycraft.datagen.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public final class DatagenItemTag extends ItemTagsProvider {

  public DatagenItemTag(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, final CompletableFuture<TagLookup<Block>> blockTags, final ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, blockTags, ImaginaryCraft.ID, existingFileHelper);
  }

  @Override
  protected void addTags(final HolderLookup.Provider provider) {

  }
}
