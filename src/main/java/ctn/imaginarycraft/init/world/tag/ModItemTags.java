package ctn.imaginarycraft.init.world.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {
  private static TagKey<Item> createTag(String name) {
    return createTag(ImaginaryCraft.modRl(name));
  }

  private static TagKey<Item> createTag(ResourceLocation location) {
    return ItemTags.create(location);
  }

  private static TagKey<Item> createCTag(String name) {
    return createTag(ResourceLocation.fromNamespaceAndPath("c", name));
  }

  private static TagKey<Item> createMcTag(String name) {
    return createTag(ResourceLocation.withDefaultNamespace(name));
  }
}
