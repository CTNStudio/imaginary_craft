package ctn.imaginarycraft.init.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {
  //region E.G.O
  /**
   * E.G.O
   */
  public static final TagKey<Item> EGO = createTag("ego");
  /**
   * E.G.O盔甲
   */
  public static final TagKey<Item> EGO_ARMOUR = createTag("ego/armour");
  /**
   * E.G.O武器
   */
  public static final TagKey<Item> EGO_WEAPON = createTag("ego/weapon");
  /**
   * E.G.O工具
   */
  public static final TagKey<Item> EGO_TOOL = createTag("ego/tool");

  //region E.G.O饰品tag
  /**
   * E.G.O饰品
   */
  public static final TagKey<Item> EGO_CURIOS = createTag("ego/curios");
  /**
   * E.G.O饰品 头饰
   */
  public static final TagKey<Item> EGO_CURIOS_HEADWEAR = createTag("ego/curios/headwear");
  /**
   * E.G.O饰品 头部
   */
  public static final TagKey<Item> EGO_CURIOS_HEAD = createTag("ego/curios/head");
  /**
   * E.G.O饰品 后脑
   */
  public static final TagKey<Item> EGO_CURIOS_HINDBRAIN = createTag("ego/curios/hindbrain");
  /**
   * E.G.O饰品 眼部
   */
  public static final TagKey<Item> EGO_CURIOS_EYE_AREA = createTag("ego/curios/eye_area");
  /**
   * E.G.O饰品 脸
   */
  public static final TagKey<Item> EGO_CURIOS_FACE = createTag("ego/curios/face");
  /**
   * E.G.O饰品 脸颊
   */
  public static final TagKey<Item> EGO_CURIOS_CHEEK = createTag("ego/curios/cheek");
  /**
   * E.G.O饰品 口罩
   */
  public static final TagKey<Item> EGO_CURIOS_MASK = createTag("ego/curios/mask");
  /**
   * E.G.O饰品 口部
   */
  public static final TagKey<Item> EGO_CURIOS_MOUTH = createTag("ego/curios/mouth");
  /**
   * E.G.O饰品 颈部
   */
  public static final TagKey<Item> EGO_CURIOS_NECK = createTag("ego/curios/neck");
  /**
   * E.G.O饰品 胸部
   */
  public static final TagKey<Item> EGO_CURIOS_CHEST = createTag("ego/curios/chest");
  /**
   * E.G.O饰品 手部
   */
  public static final TagKey<Item> EGO_CURIOS_HAND = createTag("ego/curios/hand");
  /**
   * E.G.O饰品 手套
   */
  public static final TagKey<Item> EGO_CURIOS_GLOVE = createTag("ego/curios/glove");
  /**
   * E.G.O饰品 右背
   */
  public static final TagKey<Item> EGO_CURIOS_RIGHT_BACK = createTag("ego/curios/right_back");
  /**
   * E.G.左背
   */
  public static final TagKey<Item> EGO_CURIOS_LEFT_BACK = createTag("ego/curios/left_back");
  //endregion
  //endregion

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
