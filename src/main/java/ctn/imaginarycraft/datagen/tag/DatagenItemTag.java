package ctn.imaginarycraft.datagen.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.core.ImaginaryCraftConstants;
import ctn.imaginarycraft.init.item.ToolItems;
import ctn.imaginarycraft.init.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class DatagenItemTag extends ItemTagsProvider {


  public DatagenItemTag(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, final CompletableFuture<TagLookup<Block>> blockTags, final ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, blockTags, ImaginaryCraft.ID, existingFileHelper);
  }

  @Override
  protected void addTags(final HolderLookup.Provider provider) {
    //region E.G.O
    //region 饰品
    addSet(ModItemTags.EGO_CURIOS_HEADWEAR, ImaginaryCraftConstants.EGO_CURIOS_HEADWEAR);
    addSet(ModItemTags.EGO_CURIOS_HEAD, ImaginaryCraftConstants.EGO_CURIOS_HEAD);
    addSet(ModItemTags.EGO_CURIOS_HINDBRAIN, ImaginaryCraftConstants.EGO_CURIOS_HINDBRAIN);
    addSet(ModItemTags.EGO_CURIOS_EYE, ImaginaryCraftConstants.EGO_CURIOS_EYE);
    addSet(ModItemTags.EGO_CURIOS_FACE, ImaginaryCraftConstants.EGO_CURIOS_FACE);
    addSet(ModItemTags.EGO_CURIOS_CHEEK, ImaginaryCraftConstants.EGO_CURIOS_CHEEK);
    addSet(ModItemTags.EGO_CURIOS_MASK, ImaginaryCraftConstants.EGO_CURIOS_MASK);
    addSet(ModItemTags.EGO_CURIOS_MOUTH, ImaginaryCraftConstants.EGO_CURIOS_MOUTH);
    addSet(ModItemTags.EGO_CURIOS_NECK, ImaginaryCraftConstants.EGO_CURIOS_NECK);
    addSet(ModItemTags.EGO_CURIOS_BROOCH, ImaginaryCraftConstants.EGO_CURIOS_BROOCH);
    addSet(ModItemTags.EGO_CURIOS_HAND, ImaginaryCraftConstants.EGO_CURIOS_HAND);
    addSet(ModItemTags.EGO_CURIOS_GLOVE, ImaginaryCraftConstants.EGO_CURIOS_GLOVE);
    addSet(ModItemTags.EGO_CURIOS_RIGHT_BACK, ImaginaryCraftConstants.EGO_CURIOS_RIGHT_BACK);
    addSet(ModItemTags.EGO_CURIOS_LEFT_BACK, ImaginaryCraftConstants.EGO_CURIOS_LEFT_BACK);
    tag(ModItemTags.EGO_CURIOS).addTags(
      ModItemTags.EGO_CURIOS_HEADWEAR,
      ModItemTags.EGO_CURIOS_CHEEK,
      ModItemTags.EGO_CURIOS_HEAD,
      ModItemTags.EGO_CURIOS_HINDBRAIN,
      ModItemTags.EGO_CURIOS_EYE,
      ModItemTags.EGO_CURIOS_FACE,
      ModItemTags.EGO_CURIOS_MASK,
      ModItemTags.EGO_CURIOS_MOUTH,
      ModItemTags.EGO_CURIOS_NECK,
      ModItemTags.EGO_CURIOS_BROOCH,
      ModItemTags.EGO_CURIOS_HAND,
      ModItemTags.EGO_CURIOS_GLOVE,
      ModItemTags.EGO_CURIOS_RIGHT_BACK,
      ModItemTags.EGO_CURIOS_LEFT_BACK);
    //endregion

    addSet(ModItemTags.EGO_ARMOUR, ImaginaryCraftConstants.EGO_ARMOUR);
    addSet(ModItemTags.EGO_TOOL, ImaginaryCraftConstants.EGO_TOOL);
    addSet(ModItemTags.EGO_WEAPON, ImaginaryCraftConstants.EGO_WEAPON)
      .add(ToolItems.CHAOS_SWORD.get());
    addSet(ModItemTags.EGO, ImaginaryCraftConstants.EGO).addTags(
      ModItemTags.EGO_CURIOS,
      ModItemTags.EGO_ARMOUR,
      ModItemTags.EGO_WEAPON,
      ModItemTags.EGO_TOOL);

    addSet(ItemTags.HEAD_ARMOR, ImaginaryCraftConstants.HEAD_ARMOR);
    addSet(ItemTags.CHEST_ARMOR, ImaginaryCraftConstants.CHEST_ARMOR);
    addSet(ItemTags.LEG_ARMOR, ImaginaryCraftConstants.LEG_ARMOR);
    addSet(ItemTags.FOOT_ARMOR, ImaginaryCraftConstants.FOOT_ARMOR);
    tag(Tags.Items.ARMORS).addTag(ModItemTags.EGO_ARMOUR);
    //endregion

    //region 近战武器
    ImaginaryCraftConstants.MELEE.addAll(ImaginaryCraftConstants.KNIFE);
    ImaginaryCraftConstants.MELEE.addAll(ImaginaryCraftConstants.HAMMER);
    ImaginaryCraftConstants.MELEE.addAll(ImaginaryCraftConstants.FIST);
    ImaginaryCraftConstants.MELEE.addAll(ImaginaryCraftConstants.SPEAR);
    ImaginaryCraftConstants.MELEE.addAll(ImaginaryCraftConstants.MACE);
    addSet(ModItemTags.MELEE, ImaginaryCraftConstants.MELEE).addTag(ItemTags.SWORDS);
    //endregion

    //region 远程武器
    ImaginaryCraftConstants.GUN.addAll(ImaginaryCraftConstants.CANNON);
    ImaginaryCraftConstants.GUN.addAll(ImaginaryCraftConstants.PISTOL);
    ImaginaryCraftConstants.GUN.addAll(ImaginaryCraftConstants.RIFLE);
    addSet(ModItemTags.GUN, ImaginaryCraftConstants.GUN);

    addSet(ModItemTags.REMOTE, ImaginaryCraftConstants.REMOTE).addTags(
      Tags.Items.TOOLS_CROSSBOW,
      Tags.Items.TOOLS_BOW,
      ModItemTags.GUN);
    //endregion

    addSet(ItemTags.BOW_ENCHANTABLE, ImaginaryCraftConstants.BOW);
    addSet(ItemTags.CROSSBOW_ENCHANTABLE, ImaginaryCraftConstants.CROSSBOW);

    addSet(Tags.Items.TOOLS_CROSSBOW, ImaginaryCraftConstants.CROSSBOW);
    addSet(Tags.Items.TOOLS_BOW, ImaginaryCraftConstants.BOW);
    addSet(ItemTags.AXES, ImaginaryCraftConstants.AXE);

    ImaginaryCraftConstants.SWORDS.addAll(ImaginaryCraftConstants.KNIFE);
    addSet(ItemTags.SWORDS, ImaginaryCraftConstants.SWORDS)
      .add(ToolItems.CHAOS_SWORD.get());
    tag(ItemTags.SWORD_ENCHANTABLE).addTag(ModItemTags.MELEE);
    tag(ItemTags.BREAKS_DECORATED_POTS).addTag(ModItemTags.MELEE);
    addSet(ModItemTags.SPECIAL, ImaginaryCraftConstants.SPECIAL);
    tag(Tags.Items.TOOLS)
      .add(ToolItems.CREATIVE_RATIONALITY_TOOL.get())
      .addTags(
        ModItemTags.EGO_WEAPON,
        ModItemTags.EGO_TOOL,
        ModItemTags.SPECIAL,
        ModItemTags.REMOTE,
        ModItemTags.MELEE);
  }

  private @NotNull IntrinsicTagAppender<Item> addSet(TagKey<Item> tag, @NotNull Set<DeferredItem<? extends Item>> set) {
    return tag(tag).add(set.stream().map(DeferredHolder::get).toArray(Item[]::new));
  }
}
