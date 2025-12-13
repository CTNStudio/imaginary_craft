package ctn.imaginarycraft.init.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModBlockTags {

  private static TagKey<Block> createTag(String name) {
    return createTag(ImaginaryCraft.modRl(name));
  }

  private static TagKey<Block> createTag(ResourceLocation location) {
    return BlockTags.create(location);
  }

  private static TagKey<Block> createCTag(String name) {
    return createTag(ResourceLocation.fromNamespaceAndPath("c", name));
  }

  private static TagKey<Block> createMcTag(String name) {
    return createTag(ResourceLocation.withDefaultNamespace(name));
  }
}
