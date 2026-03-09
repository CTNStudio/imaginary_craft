package ctn.imaginarycraft.init.world.item.ego;

import com.mojang.datafixers.util.*;
import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.client.model.*;
import ctn.imaginarycraft.client.renderer.providers.*;
import ctn.imaginarycraft.common.components.*;
import ctn.imaginarycraft.common.world.item.ego.armor.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.datagen.i18n.*;
import ctn.imaginarycraft.init.world.*;
import ctn.imaginarycraft.util.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.client.*;

import java.util.*;

public final class EgoArmorItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  //region ZAYIN

  public static final EgoArmor PENITENCE = registerSuit(
    "penitence", "忏悔", LcLevel.ZAYIN,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("penitence")),
    0.9, 0.8, 0.9, 2.0);

  public static final EgoArmor SODA = registerSuit(
    "soda", "美味苏打", LcLevel.ZAYIN,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("soda")),
    0.8, 1.0, 1.0, 2.0);

  public static final EgoArmor WINGBEAT = registerSuit(
    "wingbeat", "翅振", LcLevel.ZAYIN,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("wingbeat")),
    0.8, 0.8, 1.0, 2.0);
  //endregion

  //region TETH

  public static final EgoArmor STANDARD_TRAINING_EGO = registerSuit(
    "standard_training_ego", "教学用E.G.O", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("standard_training_ego")),
    0.5, 1.0, 1.5, 2.0);

  public static final EgoArmor FOURTH_MATCH_FLAME = registerSuit(
    "fourth_match_flame", "终末火柴之光", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("fourth_match_flame")),
    0.6, 1.0, 1.5, 2.0);

  public static final EgoArmor IN_THE_NAME_OF_LOVE_AND_HATE = registerSuit(
    "in_the_name_of_love_and_hate", "以爱与恨之名", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("in_the_name_of_love_and_hate")),
    0.7, 0.8, 0.4, 2.0);

  public static final EgoArmor RED_EYES = registerSuit(
    "red_eyes", "赤瞳", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("red_eyes")),
    0.8, 0.8, 0.8, 2.0);

  public static final EgoArmor HORN = registerSuit(
    "horn", "犄角", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("horn")),
    0.8, 0.8, 1.5, 2.0);

  public static final EgoArmor SOLITUDE = registerSuit(
    "solitude", "孤独", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("solitude")),
    1.5, 0.8, 0.8, 2.0);

  public static final EgoArmor SCREAMING_WEDGE = registerSuit(
    "screaming_wedge", "刺耳嚎叫", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("screaming_wedge")),
    1.2, 0.6, 1.0, 2.0);

  public static final EgoArmor NOISE = registerSuit(
    "noise", "噪音", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("noise")),
    1.2, 0.7, 0.6, 2.0);

  public static final EgoArmor WRIST_CUTTER = registerSuit(
    "wrist_cutter", "割腕者", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("wrist_cutter")),
    1.0, 0.6, 1.2, 2.0);

  public static final EgoArmor FRAGMENTS_FROM_SOMEWHERE = registerSuit(
    "fragments_from_somewhere", "彼方的裂片", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("fragments_from_somewhere")),
    1.0, 1.2, 0.6, 2.0);

  public static final EgoArmor REGRET = registerSuit(
    "regret", "悔恨", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("regret")),
    0.7, 1.2, 0.8, 2.0);

  public static final EgoArmor BEAK = registerSuit(
    "beak", "小喙", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("beak")),
    0.7, 0.8, 1.2, 2.0);

  public static final EgoArmor LANTERN = registerSuit(
    "lantern", "诱捕幻灯", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("lantern")),
    0.8, 0.7, 1.2, 2.0);

  public static final EgoArmor TODAY_IS_EXPRESSION = registerSuit(
    "today_is_expression", "此刻的神色", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("today_is_expression")),
    0.7, 0.6, 1.5, 2.0);

  public static final EgoArmor SO_CUTE = registerSuit(
    "so_cute", "超特么可爱！！！", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("so_cute")),
    0.8, 1.5, 0.8, 2.0);

  public static final EgoArmor LIFE_FOR_A_DAREDEVIL = registerSuit(
    "life_for_a_daredevil", "决死之心", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("life_for_a_daredevil")),
    0.6, 0.9, 0.9, 2.0);

  public static final EgoArmor ENGULFING_DREAM = registerSuit(
    "engulfing_dream", "迷魂梦境", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("engulfing_dream")),
    1.2, 0.8, 0.7, 2.0);

  public static final EgoArmor CHERRY_BLOSSOMS = registerSuit(
    "cherry_blossoms", "落樱", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("cherry_blossoms")),
    1.2, 0.6, 0.7, 2.0);

  public static final EgoArmor TOUGH = registerSuit(
    "tough", "谢顶之灾", LcLevel.TETH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("tough")),
    1.0, 1.0, 0.8, 2.0);
  //endregion

  //region HE

  public static final EgoArmor BEAR_PAWS = registerSuit(
    "bear_paws", "熊熊抱", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("bear_paws")),
    0.8, 1.0, 1.0, 1.5);

  public static final EgoArmor SANGUINE_DESIRE = registerSuit(
    "sanguine_desire", "血之渴望", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("sanguine_desire")),
    0.5, 1.2, 0.8, 1.5);

  public static final EgoArmor SYRINX = registerSuit(
    "syrinx", "泣婴", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("syrinx")),
    1.2, 0.5, 0.8, 1.5);

  public static final EgoArmor DA_CAPO = registerSuit(
    "da_capo", "Da Capo", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("da_capo")),
    0.5, 0.2, 0.5, 1.5);

  public static final EgoArmor LOGGING = registerSuit(
    "logging", "伐木者", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("logging")),
    0.8, 1.2, 0.8, 1.5);

  public static final EgoArmor FROST_SPLINTER = registerSuit(
    "frost_splinter", "霜之碎片", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("frost_splinter")),
    1.3, 0.6, 0.8, 1.5);

  public static final EgoArmor GRINDER_MK4 = registerSuit(
    "grinder_mk4", "粉碎机Mk4", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("grinder_mk4")),
    0.6, 1.3, 0.9, 1.5);

  public static final EgoArmor CHRISTMAS = registerSuit(
    "christmas", "悲惨圣诞", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("christmas")),
    0.8, 0.6, 1.3, 1.5);

  public static final EgoArmor HORNET = registerSuit(
    "hornet", "黄蜂", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("hornet")),
    0.7, 0.7, 0.7, 1.5);

  public static final EgoArmor OUR_GALAXY = registerSuit(
    "our_galaxy", "小小银河", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("our_galaxy")),
    0.8, 0.8, 1.2, 1.5);

  public static final EgoArmor LAETITIA = registerSuit(
    "laetitia", "蕾蒂希娅", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("laetitia")),
    0.7, 0.7, 0.7, 1.5);

  public static final EgoArmor SOLEMN_LAMENT = registerSuit(
    "solemn_lament", "圣宣", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("solemn_lament")),
    1.2, 0.8, 0.5, 1.5);

  // TODO 披风适配
  public static final EgoArmor MAGIC_BULLET = registerSuit(
    "magic_bullet", "魔弹", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("magic_bullet")),
    0.7, 0.7, 0.7, 1.5);

  public static final EgoArmor BLACK_SWAN = registerSuit(
    "black_swan", "黑天鹅", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("black_swan")),
    0.6, 1.2, 0.8, 1.5);

  public static final EgoArmor PLEASURE = registerSuit(
    "pleasure", "因乐癫狂", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("pleasure")),
    1.2, 0.8, 0.8, 1.5);

  public static final EgoArmor GAZE = registerSuit(
    "gaze", "凝视", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("gaze")),
    1.0, 0.8, 1.0, 1.5);

  public static final EgoArmor HARVEST = registerSuit(
    "harvest", "猎头长耙", LcLevel.HE,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("harvest")),
    0.6, 0.8, 1.3, 1.5);
  //endregion

  //region WAW

  public static final EgoArmor LAMP = registerSuit(
    "lamp", "目灯", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("lamp")),
    0.8, 0.7, 0.4, 1.5);

  public static final EgoArmor GREEN_STEM = registerSuit(
    "green_stem", "绿色枝干", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("green_stem")),
    0.8, 1.2, 0.6, 1.5);

  public static final EgoArmor CRIMSON_SCAR = registerSuit(
    "crimson_scar", "猩红创痕", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("crimson_scar")),
    0.6, 0.6, 0.6, 1.5);

  public static final EgoArmor COBALT_SCAR = registerSuit(
    "cobalt_scar", "郁蓝创痕", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("cobalt_scar")),
    0.4, 0.8, 0.7, 2.0);

  public static final EgoArmor FAINT_AROMA = registerSuit(
    "faint_aroma", "余香", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("faint_aroma")),
    1.2, 0.6, 0.8, 1.5);

  public static final EgoArmor GOLD_RUSH = registerSuit(
    "gold_rush", "闪金冲锋", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("gold_rush")),
    0.4, 0.7, 0.8, 2.0);

  public static final EgoArmor SPORE = registerSuit(
    "spore", "荧光菌孢", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("spore")),
    0.8, 0.6, 1.2, 1.5);

  public static final EgoArmor ECSTASY = registerSuit(
    "ecstasy", "沉醉", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("ecstasy")),
    0.8, 0.8, 0.8, 1.5);

  public static final EgoArmor HEAVEN = registerSuit(
    "heaven", "穿刺极乐", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("heaven")),
    1.2, 0.8, 0.6, 1.5);

  public static final EgoArmor THE_SWORD_SHARPENED_WITH_TEARS = registerSuit(
    "the_sword_sharpened_with_tears", "盈泪之剑", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("the_sword_sharpened_with_tears")),
    0.8, 0.8, 0.8, 0.8);

  public static final EgoArmor EXUVIAE = registerSuit(
    "exuviae", "脱落之皮", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("exuviae")),
    0.6, 0.8, 1.2, 1.5);

  public static final EgoArmor FEATHER_OF_HONOR = registerSuit(
    "feather_of_honor", "荣耀之羽", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("feather_of_honor")),
    0.6, 0.6, 1.3, 2.0);

  public static final EgoArmor DISCORD = registerSuit(
    "discord", "不和", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("discord")),
    1.2, 0.8, 0.6, 1.5);

  public static final EgoArmor MOONLIGHT = registerSuit(
    "moonlight", "月光", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("moonlight")),
    0.8, 0.4, 0.7, 2.0);

  public static final EgoArmor HYPOCRISY = registerSuit(
    "hypocrisy", "伪善", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("hypocrisy")),
    0.7, 0.5, 1.3, 1.5);

  public static final EgoArmor AMITA = registerSuit(
    "amita", "无量", LcLevel.WAW,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("amita")),
    0.5, 1.3, 0.7, 1.5);
  //endregion

  //region ALEPH

  public static final EgoArmor MIMICRY = registerSuit(
    "mimicry", "拟态", LcLevel.ALEPH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("mimicry")),
    0.2, 0.5, 0.5, 1.0);

  public static final EgoArmor PARADISE_LOST = registerSuit(
    "paradise_lost", "失乐园", LcLevel.ALEPH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("paradise_lost")),
    0.5, 0.5, 0.5, 0.3);

  public static final EgoArmor JUSTITIA = registerSuit(
    "justitia", "正义裁决者", LcLevel.ALEPH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("justitia")),
    0.5, 0.5, 0.5, 0.5);

  public static final EgoArmor TWILIGHT = registerSuit(
    "twilight", "薄暝", LcLevel.ALEPH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("twilight")),
    0.3, 0.3, 0.3, 0.5);

  public static final EgoArmor SMILE = registerSuit(
    "smile", "笑靥", LcLevel.ALEPH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("smile")),
    0.5, 0.5, 0.2, 1.0);

  public static final EgoArmor SOUND_OF_A_STAR = registerSuit(
    "sound_of_a_star", "新星之声", LcLevel.ALEPH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("sound_of_a_star")),
    0.4, 0.4, 0.4, 1.0);

  public static final EgoArmor ADORATION = registerSuit(
    "adoration", "爱慕", LcLevel.ALEPH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("adoration")),
    0.3, 0.6, 0.3, 1.0);

  public static final EgoArmor PINK = registerSuit(
    "pink", "粉红军备", LcLevel.ALEPH,
    new ItemVirtueUsageReq.Builder(),
    new EgoArmorItem.Builder(),
    new Item.Properties(),
    new ModGeoArmourRenderProvider<>(new ModGeoArmorModel<>("pink")),
    0.5, 0.3, 0.4, 1.0);
  //endregion

  static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  private static EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevel lcLevel,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul
  ) {
    return registerSuit(id, zhName, lcLevel, virtueUsageReqBuilder,
      builder, properties, renderProvider, physics, spirit, erosion, theSoul, EgoArmorItem::new);
  }

  public static Holder<ArmorMaterial> getArmorMaterialHolder(LcLevel lcLevel) {
    return switch (lcLevel) {
      case ZAYIN -> ModArmorMaterials.ZAYIN;
      case TETH -> ModArmorMaterials.TETH;
      case HE -> ModArmorMaterials.HE;
      case WAW -> ModArmorMaterials.WAW;
      case ALEPH -> ModArmorMaterials.ALEPH;
    };
  }

  private static EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevel lcLevel,
    Holder<ArmorMaterial> material,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul
  ) {
    return registerSuit(id, zhName, lcLevel, material, virtueUsageReqBuilder,
      builder, properties, renderProvider, physics, spirit, erosion, theSoul, EgoArmorItem::new);
  }

  private static <C extends EgoArmorItem> EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevel lcLevel,
    Holder<ArmorMaterial> material,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends C> function
  ) {
    return registerSuit(id, zhName, lcLevel, material, virtueUsageReqBuilder, builder, properties,
      renderProvider, physics, spirit, erosion, theSoul, function, function, function);
  }

  private static <C extends EgoArmorItem> EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevel lcLevel,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends C> function
  ) {
    return registerSuit(id, zhName, lcLevel, getArmorMaterialHolder(lcLevel), virtueUsageReqBuilder,
      builder, properties, renderProvider, physics, spirit, erosion, theSoul, function, function, function);
  }

  private static <C extends EgoArmorItem, L extends EgoArmorItem, B extends EgoArmorItem> EgoArmor registerSuit(
    String id,
    String zhName,
    LcLevel lcLevel,
    Holder<ArmorMaterial> material,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends C> chestplateFunction,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends L> leggingsFunction,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends B> bootsFunction
  ) {
    double[] physicsArray = splitIntoThreeUnequalParts(physics - ModAttributes.PHYSICS_VULNERABLE_DEFAULT_VALUE);
    double[] spiritArray = splitIntoThreeUnequalParts(spirit - ModAttributes.SPIRIT_VULNERABLE_DEFAULT_VALUE);
    double[] erosionArray = splitIntoThreeUnequalParts(erosion - ModAttributes.EROSION_VULNERABLE_DEFAULT_VALUE);
    double[] theSoulArray = splitIntoThreeUnequalParts(theSoul - ModAttributes.THE_SOUL_VULNERABLE_DEFAULT_VALUE);
    return new EgoArmor(
      register(id + "_" + ArmorItem.Type.CHESTPLATE.getName(), zhName, lcLevel, ArmorItem.Type.CHESTPLATE, material, virtueUsageReqBuilder, builder, properties, renderProvider, physicsArray[2], spiritArray[2], erosionArray[2], theSoulArray[2], chestplateFunction),
      register(id + "_" + ArmorItem.Type.LEGGINGS.getName(), zhName, lcLevel, ArmorItem.Type.LEGGINGS, material, virtueUsageReqBuilder, builder, properties, renderProvider, physicsArray[1], spiritArray[1], erosionArray[1], theSoulArray[1], leggingsFunction),
      register(id + "_" + ArmorItem.Type.BOOTS.getName(), zhName, lcLevel, ArmorItem.Type.BOOTS, material, virtueUsageReqBuilder, builder, properties, renderProvider, physicsArray[0], spiritArray[0], erosionArray[0], theSoulArray[0], bootsFunction));
  }

  /**
   * 注册一个EGO护甲物品
   *
   * @param id                    物品的唯一标识符
   * @param zhName                物品的中文名称
   * @param lcLevel           Lobotomy Corporation中的等级类型（ZAYIN, TETH, HE, WAW, ALEPH）
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
  private static <I extends EgoArmorItem> DeferredItem<I> register(
    String id,
    String zhName,
    LcLevel lcLevel,
    ArmorItem.Type armorItemType,
    Holder<ArmorMaterial> material,
    ItemVirtueUsageReq.Builder virtueUsageReqBuilder,
    EgoArmorItem.Builder builder,
    Item.Properties properties,
    GeoRenderProvider renderProvider,
    double physics,
    double spirit,
    double erosion,
    double theSoul,
    Function5<Holder<ArmorMaterial>, ArmorItem.Type, Item.Properties, EgoArmorItem.Builder, GeoRenderProvider, ? extends I> item
  ) {
    DeferredItem<I> deferredItem = EgoArmorItems.REGISTRY.register(id, () -> item.apply(material, armorItemType, properties, builder
      .virtueUsageReqBuilder(virtueUsageReqBuilder)
      .vulnerable(physics, spirit, erosion, theSoul), renderProvider));
    LcLevelUtil.addItemLcLevelCapability(lcLevel, deferredItem);
    switch (armorItemType) {
      case HELMET -> ImaginaryCraftConstants.HEAD_ARMOR.add(deferredItem);
      case CHESTPLATE -> ImaginaryCraftConstants.CHEST_ARMOR.add(deferredItem);
      case LEGGINGS -> ImaginaryCraftConstants.LEG_ARMOR.add(deferredItem);
      case BOOTS -> ImaginaryCraftConstants.FOOT_ARMOR.add(deferredItem);
    }
    ImaginaryCraftConstants.EGO_ARMOUR.add(deferredItem);
    ZhCn.addI18nItemText(zhName, deferredItem);
    return deferredItem;
  }

  /**
   *
   * @param chestplate 胸
   * @param leggings   腿
   * @param boots      脚
   */
  public record EgoArmor(
    DeferredItem<EgoArmorItem> chestplate,
    DeferredItem<EgoArmorItem> leggings,
    DeferredItem<EgoArmorItem> boots
  ) implements Iterable<DeferredItem<EgoArmorItem>> {
    @Override
    public @NotNull Iterator<DeferredItem<EgoArmorItem>> iterator() {
      return getSet().iterator();
    }

    public @NotNull Set<DeferredItem<EgoArmorItem>> getSet() {
      return Set.of(this.chestplate, this.leggings, this.boots);
    }

    public @NotNull Map<EquipmentSlot, DeferredItem<EgoArmorItem>> getMap() {
      return Map.of(
        EquipmentSlot.CHEST, this.chestplate,
        EquipmentSlot.LEGS, this.leggings,
        EquipmentSlot.FEET, this.boots
      );
    }
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
