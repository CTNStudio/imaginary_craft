package ctn.imaginarycraft.init.tag;

import ctn.imaginarycraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;

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
