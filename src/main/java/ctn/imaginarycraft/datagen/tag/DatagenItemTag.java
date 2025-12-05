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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class DatagenItemTag extends ItemTagsProvider {
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_HEADWEAR = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_HEAD = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_HINDBRAIN = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_EYE_AREA = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_FACE = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_CHEEK = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_MASK = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_MOUTH = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_NECK = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_CHEST = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_HAND = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_GLOVE = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_RIGHT_BACK = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_CURIOS_LEFT_BACK = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_ARMOUR = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_WEAPON = new ArrayList<>();
  public static final List<DeferredItem<EgoCurioItem>> EGO_TOOL = new ArrayList<>();
  // 例如道具类的物品等没有特殊分类的物品，例如：安吉拉的图书馆
  public static final List<DeferredItem<EgoItem>> EGO = new ArrayList<>();

  public DatagenItemTag(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, final CompletableFuture<TagLookup<Block>> blockTags, final ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, blockTags, ImaginaryCraft.ID, existingFileHelper);
  }

  @Override
  protected void addTags(final HolderLookup.Provider provider) {
    //region E.G.O
    //region 饰品
    addList(ModItemTags.EGO_CURIOS_HEADWEAR, EGO_CURIOS_HEADWEAR);
    addList(ModItemTags.EGO_CURIOS_HEAD, EGO_CURIOS_HEAD);
    addList(ModItemTags.EGO_CURIOS_HINDBRAIN, EGO_CURIOS_HINDBRAIN);
    addList(ModItemTags.EGO_CURIOS_EYE_AREA, EGO_CURIOS_EYE_AREA);
    addList(ModItemTags.EGO_CURIOS_FACE, EGO_CURIOS_FACE);
    addList(ModItemTags.EGO_CURIOS_CHEEK, EGO_CURIOS_CHEEK);
    addList(ModItemTags.EGO_CURIOS_MASK, EGO_CURIOS_MASK);
    addList(ModItemTags.EGO_CURIOS_MOUTH, EGO_CURIOS_MOUTH);
    addList(ModItemTags.EGO_CURIOS_NECK, EGO_CURIOS_NECK);
    addList(ModItemTags.EGO_CURIOS_CHEST, EGO_CURIOS_CHEST);
    addList(ModItemTags.EGO_CURIOS_HAND, EGO_CURIOS_HAND);
    addList(ModItemTags.EGO_CURIOS_GLOVE, EGO_CURIOS_GLOVE);
    addList(ModItemTags.EGO_CURIOS_RIGHT_BACK, EGO_CURIOS_RIGHT_BACK);
    addList(ModItemTags.EGO_CURIOS_LEFT_BACK, EGO_CURIOS_LEFT_BACK);
    tag(ModItemTags.EGO_CURIOS).addTags(
      ModItemTags.EGO_CURIOS_HEADWEAR,
      ModItemTags.EGO_CURIOS_CHEEK,
      ModItemTags.EGO_CURIOS_HEAD,
      ModItemTags.EGO_CURIOS_HINDBRAIN,
      ModItemTags.EGO_CURIOS_EYE_AREA,
      ModItemTags.EGO_CURIOS_FACE,
      ModItemTags.EGO_CURIOS_MASK,
      ModItemTags.EGO_CURIOS_MOUTH,
      ModItemTags.EGO_CURIOS_NECK,
      ModItemTags.EGO_CURIOS_CHEST,
      ModItemTags.EGO_CURIOS_HAND,
      ModItemTags.EGO_CURIOS_GLOVE,
      ModItemTags.EGO_CURIOS_RIGHT_BACK,
      ModItemTags.EGO_CURIOS_LEFT_BACK
    );
    //endregion

    addList(ModItemTags.EGO_ARMOUR, EGO_ARMOUR);
    addList(ModItemTags.EGO_TOOL, EGO_TOOL);
    addList(ModItemTags.EGO_WEAPON, EGO_WEAPON);
    addList(ModItemTags.EGO, EGO).addTags(
      ModItemTags.EGO_CURIOS,
      ModItemTags.EGO_ARMOUR,
      ModItemTags.EGO_WEAPON
    );
    //endregion
  }

  private @NotNull <T extends Item> IntrinsicTagAppender<Item> addList(TagKey<Item> tag, @NotNull List<DeferredItem<T>> list) {
    return tag(tag).add(list.stream().map(DeferredHolder::get).toArray(Item[]::new));
  }
}
