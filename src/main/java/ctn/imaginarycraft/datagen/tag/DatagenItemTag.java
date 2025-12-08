package ctn.imaginarycraft.datagen.tag;

import ctn.imaginarycraft.common.item.ego.EgoCurioItem;
import ctn.imaginarycraft.common.item.ego.EgoItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class DatagenItemTag extends ItemTagsProvider {
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_HEADWEAR = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_HEAD = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_HINDBRAIN = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_EYE = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_FACE = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_CHEEK = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_MASK = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_MOUTH = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_NECK = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_BROOCH = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_HAND = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_GLOVE = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_RIGHT_BACK = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_CURIOS_LEFT_BACK = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_ARMOUR = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_WEAPON = new HashSet<>();
  public static final Set<DeferredItem<EgoCurioItem>> EGO_TOOL = new HashSet<>();
  // 例如道具类的物品等没有特殊分类的物品，例如：安吉拉的图书馆
  public static final Set<DeferredItem<EgoItem>> EGO = new HashSet<>();

  public DatagenItemTag(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, final CompletableFuture<TagLookup<Block>> blockTags, final ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, blockTags, ImaginaryCraft.ID, existingFileHelper);
  }

  @Override
  protected void addTags(final HolderLookup.Provider provider) {
    //region E.G.O
    //region 饰品
    addSet(ModItemTags.EGO_CURIOS_HEADWEAR, EGO_CURIOS_HEADWEAR);
    addSet(ModItemTags.EGO_CURIOS_HEAD, EGO_CURIOS_HEAD);
    addSet(ModItemTags.EGO_CURIOS_HINDBRAIN, EGO_CURIOS_HINDBRAIN);
    addSet(ModItemTags.EGO_CURIOS_EYE, EGO_CURIOS_EYE);
    addSet(ModItemTags.EGO_CURIOS_FACE, EGO_CURIOS_FACE);
    addSet(ModItemTags.EGO_CURIOS_CHEEK, EGO_CURIOS_CHEEK);
    addSet(ModItemTags.EGO_CURIOS_MASK, EGO_CURIOS_MASK);
    addSet(ModItemTags.EGO_CURIOS_MOUTH, EGO_CURIOS_MOUTH);
    addSet(ModItemTags.EGO_CURIOS_NECK, EGO_CURIOS_NECK);
    addSet(ModItemTags.EGO_CURIOS_BROOCH, EGO_CURIOS_BROOCH);
    addSet(ModItemTags.EGO_CURIOS_HAND, EGO_CURIOS_HAND);
    addSet(ModItemTags.EGO_CURIOS_GLOVE, EGO_CURIOS_GLOVE);
    addSet(ModItemTags.EGO_CURIOS_RIGHT_BACK, EGO_CURIOS_RIGHT_BACK);
    addSet(ModItemTags.EGO_CURIOS_LEFT_BACK, EGO_CURIOS_LEFT_BACK);
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
      ModItemTags.EGO_CURIOS_LEFT_BACK
    );
    //endregion

    addSet(ModItemTags.EGO_ARMOUR, EGO_ARMOUR);
    addSet(ModItemTags.EGO_TOOL, EGO_TOOL);
    addSet(ModItemTags.EGO_WEAPON, EGO_WEAPON);
    addSet(ModItemTags.EGO, EGO).addTags(
      ModItemTags.EGO_CURIOS,
      ModItemTags.EGO_ARMOUR,
      ModItemTags.EGO_WEAPON,
      ModItemTags.EGO_TOOL
    );
    //endregion
  }

  private @NotNull <T extends Item> IntrinsicTagAppender<Item> addList(TagKey<Item> tag, @NotNull List<DeferredItem<T>> list) {
    return tag(tag).add(list.stream().map(DeferredHolder::get).toArray(Item[]::new));
  }

  private @NotNull <T extends Item> IntrinsicTagAppender<Item> addSet(TagKey<Item> tag, @NotNull Set<DeferredItem<T>> set) {
    return tag(tag).add(set.stream().map(DeferredHolder::get).toArray(Item[]::new));
  }
}
