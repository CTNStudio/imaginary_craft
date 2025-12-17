package ctn.imaginarycraft.init.item.ego;

import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import ctn.imaginarycraft.client.model.ModGeoArmorModel;
import ctn.imaginarycraft.client.renderer.providers.ModGeoArmourRenderProvider;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.common.item.ego.armor.EgoArmorItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.datagen.tag.DatagenItemTag;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class EgoArmorItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static final EgoArmor STANDARD_TRAINING_EGO = registerSuit(
    "standard_training_ego", "教学用E.G.O", LcLevelType.TETH,
    0.5, 1.0, 1.5, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("standard_training_ego"));

  //region ZAYIN

  public static final EgoArmor PENITENCE = registerSuit(
    "penitence", "忏悔", LcLevelType.ZAYIN,
    0.9, 0.8, 0.9, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("penitence"));

  public static final EgoArmor SODA = registerSuit(
    "soda", "美味苏打", LcLevelType.ZAYIN,
    0.8, 1.0, 1.0, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("soda"));

  public static final EgoArmor WINGBEAT = registerSuit(
    "wingbeat", "翅振", LcLevelType.ZAYIN,
    0.8, 0.8, 1.0, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("wingbeat"));
  //endregion

  //region TETH

  public static final EgoArmor FOURTH_MATCH_FLAME = registerSuit(
    "fourth_match_flame", "终末火柴之光", LcLevelType.TETH,
    0.6, 1.0, 1.5, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("fourth_match_flame"));

  public static final EgoArmor IN_THE_NAME_OF_LOVE_AND_HATE = registerSuit(
    "in_the_name_of_love_and_hate", "以爱与恨之名", LcLevelType.TETH,
    0.7, 0.8, 0.4, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("in_the_name_of_love_and_hate"));

  public static final EgoArmor RED_EYES = registerSuit(
    "red_eyes", "赤瞳", LcLevelType.TETH,
    0.8, 0.8, 0.8, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("red_eyes"));

  public static final EgoArmor HORN = registerSuit(
    "horn", "犄角", LcLevelType.TETH,
    0.8, 0.8, 1.5, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("horn"));

  public static final EgoArmor SOLITUDE = registerSuit(
    "solitude", "孤独", LcLevelType.TETH,
    1.5, 0.8, 0.8, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("solitude"));

  public static final EgoArmor SCREAMING_WEDGE = registerSuit(
    "screaming_wedge", "刺耳嚎叫", LcLevelType.TETH,
    1.2, 0.6, 1.0, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("screaming_wedge"));

  public static final EgoArmor NOISE = registerSuit(
    "noise", "噪音", LcLevelType.TETH,
    1.2, 0.7, 0.6, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("noise"));

  public static final EgoArmor WRIST_CUTTER = registerSuit(
    "wrist_cutter", "割腕者", LcLevelType.TETH,
    1.0, 0.6, 1.2, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("wrist_cutter"));

  public static final EgoArmor FRAGMENTS_FROM_SOMEWHERE = registerSuit(
    "fragments_from_somewhere", "彼方的裂片", LcLevelType.TETH,
    1.0, 1.2, 0.6, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("fragments_from_somewhere"));

  public static final EgoArmor REGRET = registerSuit(
    "regret", "悔恨", LcLevelType.TETH,
    0.7, 1.2, 0.8, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("regret"));

  public static final EgoArmor BEAK = registerSuit(
    "beak", "小喙", LcLevelType.TETH,
    0.7, 0.8, 1.2, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("beak"));

  public static final EgoArmor LANTERN = registerSuit(
    "lantern", "诱捕幻灯", LcLevelType.TETH,
    0.8, 0.7, 1.2, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("lantern"));

  public static final EgoArmor TODAY_IS_EXPRESSION = registerSuit(
    "today_is_expression", "此刻的神色", LcLevelType.TETH,
    0.7, 0.6, 1.5, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("today_is_expression"));

  public static final EgoArmor SO_CUTE = registerSuit(
    "so_cute", "超特么可爱！！！", LcLevelType.TETH,
    0.8, 1.5, 0.8, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("so_cute"));

  public static final EgoArmor LIFE_FOR_A_DAREDEVIL = registerSuit(
    "life_for_a_daredevil", "决死之心", LcLevelType.TETH,
    0.6, 0.9, 0.9, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("life_for_a_daredevil"));

  public static final EgoArmor ENGULFING_DREAM = registerSuit(
    "engulfing_dream", "迷魂梦境", LcLevelType.TETH,
    1.2, 0.8, 0.7, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("engulfing_dream"));

  public static final EgoArmor CHERRY_BLOSSOMS = registerSuit(
    "cherry_blossoms", "落樱", LcLevelType.TETH,
    1.2, 0.6, 0.7, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("cherry_blossoms"));

  public static final EgoArmor TOUGH = registerSuit(
    "tough", "谢顶之灾", LcLevelType.TETH,
    1.0, 1.0, 0.8, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("tough"));
  //endregion

  //region HE

  public static final EgoArmor BEAR_PAWS = registerSuit(
    "bear_paws", "熊熊抱", LcLevelType.HE,
    0.8, 1.0, 1.0, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("bear_paws"));

  public static final EgoArmor SANGUINE_DESIRE = registerSuit(
    "sanguine_desire", "血之渴望", LcLevelType.HE,
    0.5, 1.2, 0.8, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("sanguine_desire"));

  public static final EgoArmor SYRINX = registerSuit(
    "syrinx", "泣婴", LcLevelType.HE,
    1.2, 0.5, 0.8, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("syrinx"));

  public static final EgoArmor DA_CAPO = registerSuit(
    "da_capo", "Da Capo", LcLevelType.HE,
    0.5, 0.2, 0.5, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("da_capo"));

  public static final EgoArmor LOGGING = registerSuit(
    "logging", "伐木者", LcLevelType.HE,
    0.8, 1.2, 0.8, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("logging"));

  public static final EgoArmor FROST_SPLINTER = registerSuit(
    "frost_splinter", "霜之碎片", LcLevelType.HE,
    1.3, 0.6, 0.8, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("frost_splinter"));

  public static final EgoArmor GRINDER_MK4 = registerSuit(
    "grinder_mk4", "粉碎机Mk4", LcLevelType.HE,
    0.6, 1.3, 0.9, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("grinder_mk4"));

  public static final EgoArmor CHRISTMAS = registerSuit(
    "christmas", "悲惨圣诞", LcLevelType.HE,
    0.8, 0.6, 1.3, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("christmas"));

  public static final EgoArmor HORNET = registerSuit(
    "hornet", "黄蜂", LcLevelType.HE,
    0.7, 0.7, 0.7, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("hornet"));

  public static final EgoArmor OUR_GALAXY = registerSuit(
    "our_galaxy", "小小银河", LcLevelType.HE,
    0.8, 0.8, 1.2, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("our_galaxy"));

  public static final EgoArmor LAETITIA = registerSuit(
    "laetitia", "蕾蒂希娅", LcLevelType.HE,
    0.7, 0.7, 0.7, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("laetitia"));

  public static final EgoArmor SOLEMN_LAMENT = registerSuit(
    "solemn_lament", "圣宣", LcLevelType.HE,
    1.2, 0.8, 0.5, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("solemn_lament"));

  public static final EgoArmor MAGIC_BULLET = registerSuit(
    "magic_bullet", "魔弹", LcLevelType.HE,
    0.7, 0.7, 0.7, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("magic_bullet"));

  public static final EgoArmor BLACK_SWAN = registerSuit(
    "black_swan", "黑天鹅", LcLevelType.HE,
    0.6, 1.2, 0.8, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("black_swan"));

  public static final EgoArmor PLEASURE = registerSuit(
    "pleasure", "因乐癫狂", LcLevelType.HE,
    1.2, 0.8, 0.8, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("pleasure"));

  public static final EgoArmor GAZE = registerSuit(
    "gaze", "凝视", LcLevelType.HE,
    1.0, 0.8, 1.0, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("gaze"));

  public static final EgoArmor HARVEST = registerSuit(
    "harvest", "猎头长耙", LcLevelType.HE,
    0.6, 0.8, 1.3, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("harvest"));
  //endregion

  //region WAW

  public static final EgoArmor LAMP = registerSuit(
    "lamp", "目灯", LcLevelType.WAW,
    0.8, 0.7, 0.4, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("lamp"));

  public static final EgoArmor GREEN_STEM = registerSuit(
    "green_stem", "绿色枝干", LcLevelType.WAW,
    0.8, 1.2, 0.6, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("green_stem"));

  public static final EgoArmor CRIMSON_SCAR = registerSuit(
    "crimson_scar", "猩红创痕", LcLevelType.WAW,
    0.6, 0.6, 0.6, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("crimson_scar"));

  public static final EgoArmor COBALT_SCAR = registerSuit(
    "cobalt_scar", "郁蓝创痕", LcLevelType.WAW,
    0.4, 0.8, 0.7, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("cobalt_scar"));

  public static final EgoArmor FAINT_AROMA = registerSuit(
    "faint_aroma", "余香", LcLevelType.WAW,
    1.2, 0.6, 0.8, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("faint_aroma"));

  public static final EgoArmor GOLD_RUSH = registerSuit(
    "gold_rush", "闪金冲锋", LcLevelType.WAW,
    0.4, 0.7, 0.8, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("gold_rush"));

  public static final EgoArmor SPORE = registerSuit(
    "spore", "荧光菌孢", LcLevelType.WAW,
    0.8, 0.6, 1.2, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("spore"));

  public static final EgoArmor ECSTASY = registerSuit(
    "ecstasy", "沉醉", LcLevelType.WAW,
    0.8, 0.8, 0.8, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("ecstasy"));

  public static final EgoArmor HEAVEN = registerSuit(
    "heaven", "穿刺极乐", LcLevelType.WAW,
    1.2, 0.8, 0.6, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("heaven"));

  public static final EgoArmor THE_SWORD_SHARPENED_WITH_TEARS = registerSuit(
    "the_sword_sharpened_with_tears", "盈泪之剑", LcLevelType.WAW,
    0.8, 0.8, 0.8, 0.8,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("the_sword_sharpened_with_tears"));

  public static final EgoArmor EXUVIAE = registerSuit(
    "exuviae", "脱落之皮", LcLevelType.WAW,
    0.6, 0.8, 1.2, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("exuviae"));

  public static final EgoArmor FEATHER_OF_HONOR = registerSuit(
    "feather_of_honor", "荣耀之羽", LcLevelType.WAW,
    0.6, 0.6, 1.3, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("feather_of_honor"));

  public static final EgoArmor DISCORD = registerSuit(
    "discord", "不和", LcLevelType.WAW,
    1.2, 0.8, 0.6, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("discord"));

  public static final EgoArmor MOONLIGHT = registerSuit(
    "moonlight", "月光", LcLevelType.WAW,
    0.8, 0.4, 0.7, 2.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("moonlight"));

  public static final EgoArmor HYPOCRISY = registerSuit(
    "hypocrisy", "伪善", LcLevelType.WAW,
    0.7, 0.5, 1.3, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("hypocrisy"));

  public static final EgoArmor AMITA = registerSuit(
    "amita", "无量", LcLevelType.WAW,
    0.5, 1.3, 0.7, 1.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("amita"));
  //endregion

  //region ALEPH

  public static final EgoArmor MIMICRY = registerSuit(
    "mimicry", "拟态", LcLevelType.ALEPH,
    0.2, 0.5, 0.5, 1.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("mimicry"));

  public static final EgoArmor PARADISE_LOST = registerSuit(
    "paradise_lost", "失乐园", LcLevelType.ALEPH,
    0.5, 0.5, 0.5, 0.3,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("paradise_lost"));

  public static final EgoArmor JUSTITIA = registerSuit(
    "justitia", "正义裁决者", LcLevelType.ALEPH,
    0.5, 0.5, 0.5, 0.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("justitia"));

  public static final EgoArmor TWILIGHT = registerSuit(
    "twilight", "薄暝", LcLevelType.ALEPH,
    0.3, 0.3, 0.3, 0.5,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("twilight"));

  public static final EgoArmor SMILE = registerSuit(
    "smile", "笑靥", LcLevelType.ALEPH,
    0.5, 0.5, 0.2, 1.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("smile"));

  public static final EgoArmor SOUND_OF_A_STAR = registerSuit(
    "sound_of_a_star", "新星之声", LcLevelType.ALEPH,
    0.4, 0.4, 0.4, 1.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("sound_of_a_star"));

  public static final EgoArmor ADORATION = registerSuit(
    "adoration", "爱慕", LcLevelType.ALEPH,
    0.3, 0.6, 0.3, 1.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("adoration"));

  public static final EgoArmor PINK = registerSuit(
    "pink", "粉红军备", LcLevelType.ALEPH,
    0.5, 0.3, 0.4, 1.0,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new ModGeoArmorModel<>("pink"));
  //endregion

  static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  private static EgoArmor registerSuit(String id, String zhName, LcLevelType lcLevelType,
                                       double physics, double spirit, double erosion, double theSoul,
                                       ItemVirtueUsageReq.Builder virtueUsageReqBuilder, EgoArmorItem.Builder builder,
                                       Holder<ArmorMaterial> material, ModGeoArmourRenderProvider<EgoArmorItem> renderProvider) {
    return registerSuit(id, zhName, lcLevelType, physics, spirit, erosion, theSoul,
      virtueUsageReqBuilder, builder, material, renderProvider,
      EgoArmorItem::new, EgoArmorItem::new, EgoArmorItem::new);
  }

  private static EgoArmor registerSuit(String id, String zhName, LcLevelType lcLevelType,
                                       double physics, double spirit, double erosion, double theSoul,
                                       ItemVirtueUsageReq.Builder virtueUsageReqBuilder, EgoArmorItem.Builder builder,
                                       ModGeoArmorModel<EgoArmorItem> model) {
    return registerSuit(id, zhName, lcLevelType, physics, spirit, erosion, theSoul,
      virtueUsageReqBuilder, builder, model,
      EgoArmorItem::new, EgoArmorItem::new, EgoArmorItem::new);
  }

  private static <C extends EgoArmorItem, L extends EgoArmorItem, B extends EgoArmorItem> EgoArmor registerSuit(String id, String zhName,
                                                                                                                LcLevelType lcLevelType,
                                                                                                                double physics,
                                                                                                                double spirit,
                                                                                                                double erosion,
                                                                                                                double theSoul,
                                                                                                                ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
                                                                                                                EgoArmorItem.Builder builder,
                                                                                                                ModGeoArmorModel<EgoArmorItem> renderProvider,
                                                                                                                Function<EgoArmorItem.Builder, ? extends C> chestplate,
                                                                                                                Function<EgoArmorItem.Builder, ? extends L> leggings,
                                                                                                                Function<EgoArmorItem.Builder, ? extends B> boots) {
    return registerSuit(id, zhName, lcLevelType, physics, spirit, erosion, theSoul,
      virtueUsageReqBuilder, builder, new ModGeoArmourRenderProvider<>(renderProvider, null),
      chestplate, leggings, boots);
  }

  private static <C extends EgoArmorItem, L extends EgoArmorItem, B extends EgoArmorItem> EgoArmor registerSuit(String id, String zhName,
                                                                                                                LcLevelType lcLevelType,
                                                                                                                double physics,
                                                                                                                double spirit,
                                                                                                                double erosion,
                                                                                                                double theSoul,
                                                                                                                ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
                                                                                                                EgoArmorItem.Builder builder,
                                                                                                                ModGeoArmourRenderProvider<EgoArmorItem> renderProvider,
                                                                                                                Function<EgoArmorItem.Builder, ? extends C> chestplate,
                                                                                                                Function<EgoArmorItem.Builder, ? extends L> leggings,
                                                                                                                Function<EgoArmorItem.Builder, ? extends B> boots) {
    return registerSuit(id, zhName, lcLevelType, physics, spirit, erosion, theSoul,
      virtueUsageReqBuilder, builder, switch (lcLevelType) {
        case ZAYIN -> ModArmorMaterials.ZAYIN;
        case TETH -> ModArmorMaterials.TETH;
        case HE -> ModArmorMaterials.HE;
        case WAW -> ModArmorMaterials.WAW;
        case ALEPH -> ModArmorMaterials.ALEPH;
      }, renderProvider, chestplate, leggings, boots);
  }

  private static <C extends EgoArmorItem, L extends EgoArmorItem, B extends EgoArmorItem> EgoArmor registerSuit(String id, String zhName,
                                                                                                                LcLevelType lcLevelType,
                                                                                                                double physics,
                                                                                                                double spirit,
                                                                                                                double erosion,
                                                                                                                double theSoul,
                                                                                                                ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
                                                                                                                EgoArmorItem.Builder builder,
                                                                                                                Holder<ArmorMaterial> material,
                                                                                                                ModGeoArmourRenderProvider<EgoArmorItem> renderProvider,
                                                                                                                Function<EgoArmorItem.Builder, ? extends C> chestplateFunction,
                                                                                                                Function<EgoArmorItem.Builder, ? extends L> leggingsFunction,
                                                                                                                Function<EgoArmorItem.Builder, ? extends B> bootsFunction) {

    double[] physicsArray = splitIntoThreeUnequalParts(physics - ModAttributes.PHYSICS_VULNERABLE.get().getDefaultValue());
    double[] spiritArray = splitIntoThreeUnequalParts(spirit - ModAttributes.SPIRIT_VULNERABLE.get().getDefaultValue());
    double[] erosionArray = splitIntoThreeUnequalParts(erosion - ModAttributes.EROSION_VULNERABLE.get().getDefaultValue());
    double[] theSoulArray = splitIntoThreeUnequalParts(theSoul - ModAttributes.THE_SOUL_VULNERABLE.get().getDefaultValue());
    return new EgoArmor(
      getArmorItemDeferredItem(id, zhName, lcLevelType, ArmorItem.Type.CHESTPLATE,
        virtueUsageReqBuilder, builder, material, renderProvider, chestplateFunction,
        physicsArray[2], spiritArray[2], erosionArray[2], theSoulArray[2]),
      getArmorItemDeferredItem(id, zhName, lcLevelType, ArmorItem.Type.BOOTS,
        virtueUsageReqBuilder, builder, material, renderProvider, leggingsFunction,
        physicsArray[1], spiritArray[1], erosionArray[1], theSoulArray[1]),
      getArmorItemDeferredItem(id, zhName, lcLevelType, ArmorItem.Type.BOOTS,
        virtueUsageReqBuilder, builder, material, renderProvider, bootsFunction,
        physicsArray[0], spiritArray[0], erosionArray[0], theSoulArray[0]));
  }

  private static <C extends EgoArmorItem> @NotNull DeferredItem<EgoArmorItem>
  getArmorItemDeferredItem(String id, String zhName,
                           LcLevelType lcLevelType,
                           ArmorItem.Type armorItemType,
                           ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
                           EgoArmorItem.Builder builder,
                           Holder<ArmorMaterial> material,
                           ModGeoArmourRenderProvider<EgoArmorItem> renderProvider,
                           Function<EgoArmorItem.Builder, ? extends C> chestplateFunction,
                           double physics,
                           double spirit,
                           double erosion,
                           double theSoul) {

    return register(id + "_" + armorItemType.getName(), zhName, armorItemType, lcLevelType,
      builder.init(material, armorItemType, renderProvider),
      physics, spirit, erosion, theSoul, virtueUsageReqBuilder, chestplateFunction);
  }

  /**
   * 注册一个EGO护甲物品
   *
   * @param id                    物品的唯一标识符
   * @param zhName                物品的中文名称
   * @param lcLevelType           Lobotomy Corporation中的等级类型（ZAYIN, TETH, HE, WAW, ALEPH）
   * @param builder               EGO护甲构建器
   * @param physics               物理属性值
   * @param spirit                理性属性值
   * @param erosion               侵蚀属性值
   * @param theSoul               灵魂属性值
   * @param virtueUsageReqBuilder 德性使用需求构建器
   * @param item                  用于创建具体EGO护甲物品的函数
   * @return 返回注册后的EGO护甲物品DeferredItem对象
   */
  @NotNull
  private static <I extends EgoArmorItem> DeferredItem<I> register(String id, String zhName, ArmorItem.Type armorItemType,
                                                                   LcLevelType lcLevelType, EgoArmorItem.Builder builder,
                                                                   double physics, double spirit, double erosion, double theSoul,
                                                                   ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
                                                                   Function<EgoArmorItem.Builder, ? extends I> item) {
    DeferredItem<I> deferredItem = REGISTRY.register(id, () -> item.apply(builder
      .virtueUsageReqBuilder(virtueUsageReqBuilder)
      .vulnerable(physics, spirit, erosion, theSoul)));
    LcLevelUtil.addItemLcLevelCapability(lcLevelType, deferredItem);
    switch (armorItemType) {
      case HELMET -> DatagenItemTag.HEAD_ARMOR.add(deferredItem);
      case CHESTPLATE -> DatagenItemTag.CHEST_ARMOR.add(deferredItem);
      case LEGGINGS -> DatagenItemTag.LEG_ARMOR.add(deferredItem);
      case BOOTS -> DatagenItemTag.FOOT_ARMOR.add(deferredItem);
      case BODY -> {
      }
    }
    DatagenItemTag.EGO_ARMOUR.add(deferredItem);
    ZhCn.ITEMS.put(deferredItem, zhName);
    return deferredItem;
  }

  /**
   *
   * @param chestplate 胸
   * @param leggings   腿
   * @param boots      脚
   */
  public record EgoArmor(DeferredItem<EgoArmorItem> chestplate, DeferredItem<EgoArmorItem> leggings,
                         DeferredItem<EgoArmorItem> boots) {
  }

  /**
   * 拆分数值为不等的三份（无无限循环小数，优先整数）
   *
   * @param n 待拆分数值（整数/小数均可）
   * @return 三个不等的数（数组顺序：小、中、大）
   */
  private static double[] splitIntoThreeUnequalParts(double n) {
    double avg = n / 3.0;
    return new double[]{avg - 0.01, avg, avg + 0.01};
  }
}
