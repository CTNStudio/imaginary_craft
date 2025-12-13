package ctn.imaginarycraft.datagen.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class DatagenItemTag extends ItemTagsProvider {
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_HEADWEAR = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_HEAD = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_HINDBRAIN = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_EYE = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_FACE = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_CHEEK = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_MASK = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_MOUTH = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_NECK = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_BROOCH = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_HAND = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_GLOVE = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_RIGHT_BACK = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_CURIOS_LEFT_BACK = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_ARMOUR = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_WEAPON = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> EGO_TOOL = new HashSet<>();
  // 例如道具类的物品等没有特殊分类的物品，例如：安吉拉的图书馆
  public static final Set<DeferredItem<? extends Item>> EGO = new HashSet<>();

  public static final Set<DeferredItem<? extends Item>> REMOTE = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> MELEE = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> SPECIAL = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> GUN = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> PISTOL = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> RIFLE = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> CANNON = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> CROSSBOW = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> BOW = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> KNIFE = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> HAMMER = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> FIST = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> SPEAR = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> AXE = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> MACE = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> SWORDS = new HashSet<>();

  public static final Set<DeferredItem<? extends Item>> HEAD_ARMOR = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> CHEST_ARMOR = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> LEG_ARMOR = new HashSet<>();
  public static final Set<DeferredItem<? extends Item>> FOOT_ARMOR = new HashSet<>();


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
      ModItemTags.EGO_CURIOS_LEFT_BACK);
    //endregion

    addSet(ModItemTags.EGO_ARMOUR, EGO_ARMOUR);
    addSet(ModItemTags.EGO_TOOL, EGO_TOOL);
    addSet(ModItemTags.EGO_WEAPON, EGO_WEAPON)
      .add(ToolItems.CHAOS_SWORD.get());
    addSet(ModItemTags.EGO, EGO).addTags(
      ModItemTags.EGO_CURIOS,
      ModItemTags.EGO_ARMOUR,
      ModItemTags.EGO_WEAPON,
      ModItemTags.EGO_TOOL);

    addSet(ItemTags.HEAD_ARMOR, HEAD_ARMOR);
    addSet(ItemTags.CHEST_ARMOR, CHEST_ARMOR);
    addSet(ItemTags.LEG_ARMOR, LEG_ARMOR);
    addSet(ItemTags.FOOT_ARMOR, FOOT_ARMOR);
    tag(Tags.Items.ARMORS).addTag(ModItemTags.EGO_ARMOUR);
    //endregion

    //region 近战武器

    MELEE.addAll(KNIFE);
    MELEE.addAll(HAMMER);
    MELEE.addAll(FIST);
    MELEE.addAll(SPEAR);
    MELEE.addAll(MACE);
    addSet(ModItemTags.MELEE, MELEE).addTag(ItemTags.SWORDS);
    //endregion

    //region 远程武器
    GUN.addAll(CANNON);
    GUN.addAll(PISTOL);
    GUN.addAll(RIFLE);
    addSet(ModItemTags.GUN, GUN);

    addSet(ModItemTags.REMOTE, REMOTE).addTags(
      Tags.Items.TOOLS_CROSSBOW,
      Tags.Items.TOOLS_BOW,
      ModItemTags.GUN);
    //endregion

    addSet(ItemTags.BOW_ENCHANTABLE, BOW);
    addSet(ItemTags.CROSSBOW_ENCHANTABLE, CROSSBOW);

    addSet(Tags.Items.TOOLS_CROSSBOW, CROSSBOW);
    addSet(Tags.Items.TOOLS_BOW, BOW);
    addSet(ItemTags.AXES, AXE);

    SWORDS.addAll(KNIFE);
    addSet(ItemTags.SWORDS, SWORDS)
      .add(ToolItems.CHAOS_SWORD.get());
    tag(ItemTags.SWORD_ENCHANTABLE).addTag(ModItemTags.MELEE);
    tag(ItemTags.BREAKS_DECORATED_POTS).addTag(ModItemTags.MELEE);
    addSet(ModItemTags.SPECIAL, SPECIAL);
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
