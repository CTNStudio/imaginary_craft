package ctn.imaginarycraft.init.item;

import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import ctn.imaginarycraft.common.item.ego.EgoCurioItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.datagen.tag.DatagenItemTag;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Function;

public final class ModEgoCurios {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  //region 头饰
  // TODO 受到精神伤害时，减少5%的精神损耗。
  public static final DeferredItem<EgoCurioItem> SYRINX = register(
    "syrinx_curios",
    "泣婴",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(-2)
      .prudence(-2)
      .temperance(0)
      .justice(6)
      .addTooltip("受到精神伤害时，减少5%的精神损耗。")
  );
  public static final DeferredItem<EgoCurioItem> LAMP = register(
    "lamp_curios",
    "目灯",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(3)
      .prudence(0)
      .temperance(3)
      .justice(6)
  );
  public static final DeferredItem<EgoCurioItem> HORNET = register(
    "hornet_curios",
    "黄蜂",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(2)
      .prudence(3)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> LAETITIA = register(
    "laetitia_curios",
    "蕾蒂希娅",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(4)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> HEAVEN = register(
    "heaven_curios",
    "穿刺极乐",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(4)
      .prudence(0)
      .temperance(2)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> DIFFRACTION = register(
    "diffraction_curios",
    "虚无衍射体",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(6)
      .temperance(0)
      .justice(0)
  );
  // TODO 每受到一次不会导致死亡或恐慌的伤害，都有8%的概率免疫此次伤害。
  public static final DeferredItem<EgoCurioItem> DISCORD = register(
    "discord_curios",
    "不和",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(-10)
      .prudence(-10)
      .temperance(0)
      .justice(20)
      .addTooltip("每受到一次不会导致死亡或恐慌的伤害，都有8%的概率免疫此次伤害。")
  );
  // TODO 装备全套“粉红军备”E.G.O时，E.G.O武器“粉红军备”的攻击力将提高15%。
  public static final DeferredItem<EgoCurioItem> PINK = register(
    "pink_curios",
    "粉红军备",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(5)
      .temperance(0)
      .justice(0, 5)
      .addTooltip("装备全套“粉红军备”E.G.O时，E.G.O武器“粉红军备”的攻击力将提高15%。")
  );
  public static final DeferredItem<EgoCurioItem> HYPOCRISY = register(
    "hypocrisy_curios",
    "伪善",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(3)
      .prudence(3)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> ADORATION = register(
    "adoration_curios",
    "爱慕",
    CuriosType.HEADWEAR,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(5)
      .prudence(10)
      .temperance(-5)
      .justice(6)
  );
  //endregion

  //region 头
  public static final DeferredItem<EgoCurioItem> STANDARD_TRAINING_EGO = register(
    "standard_training_ego_curios",
    "教学用E.G.O",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(2)
      .prudence(2)
      .temperance(0)
      .justice(0)
  );
  // TODO 对异想体“一罪与百善”进行工作的成功率提高10%
  public static final DeferredItem<EgoCurioItem> PENITENCE = register(
    "penitence_curios",
    "忏悔",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(2)
      .temperance(0)
      .justice(0)
      .addTooltip("对异想体“一罪与百善”进行工作的成功率提高10%")
  );
  public static final DeferredItem<EgoCurioItem> IN_THE_NAME_OF_LOVE_AND_HATE = register(
    "in_the_name_of_love_and_hate_curios",
    "以爱与恨之名",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(2)
      .justice(4)
  );
  // TODO 沟通工作的成功率提高3%
  public static final DeferredItem<EgoCurioItem> BEAR_PAWS = register(
    "bear_paws_curios",
    "熊熊抱",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(4)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> HORN = register(
    "horn_curios",
    "犄角",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(2)
      .prudence(2)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> CHRISTMAS = register(
    "christmas_curios",
    "悲惨圣诞",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(-4)
      .prudence(8)
      .temperance(0)
      .justice(6)
  );
  public static final DeferredItem<EgoCurioItem> FAINT_AROMA = register(
    "faint_aroma_curios",
    "余香",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(0, 2, 0, 2)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> SHEEP_IS_CLOTHING = register(
    "sheep_is_clothing_curios",
    "羊皮",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(9)
      .prudence(-3)
      .temperance(0)
      .justice(0)
      .addTooltip("...这就是为什么我能在那匹狼饿着肚子的情况下安全回到这里！", Style.EMPTY.withColor(LcDamageType.PHYSICS.getColourValue())));
  public static final DeferredItem<EgoCurioItem> INSPIRED_BRAVERY_BLUE = register(
    "inspired_bravery_blue_curios",
    "内在勇气",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(0)
      .justice(10)
      .addTooltip("勇气愈发强大，一切都成为可能！", Style.EMPTY.withColor(LcDamageType.PHYSICS.getColourValue()))
  );
  // TODO 修改名称颜色为 blue
  public static final DeferredItem<EgoCurioItem> RECKLESS_FOOLISHNESS_BLUE = register(
    "reckless_foolishness_blue_curios",
    "匹夫之勇",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(-5)
      .temperance(0)
      .justice(10)
      .addTooltip("充满勇气的战士时刻准备着冲锋陷阵！", Style.EMPTY.withColor(LcDamageType.PHYSICS.getColourValue()))
  );
  // TODO 修改名称颜色为 orange
  public static final DeferredItem<EgoCurioItem> RECKLESS_FOOLISHNESS_ORANGE = register(
    "reckless_foolishness_orange_curios",
    "匹夫之勇",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(-10)
      .temperance(0)
      .justice(15)
      .addTooltip("过度的勇气可能会铸成大错。", Style.EMPTY.withColor(LcDamageType.PHYSICS.getColourValue()))
  );
  // TODO 修改名称颜色为 red
  public static final DeferredItem<EgoCurioItem> RECKLESS_FOOLISHNESS_RED = register(
    "reckless_foolishness_red_curios",
    "匹夫之勇",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(-20)
      .temperance(0)
      .justice(20)
      .addTooltip("匹夫之勇，终将葬送一切。", Style.EMPTY.withColor(LcDamageType.PHYSICS.getColourValue()))
  );
  public static final DeferredItem<EgoCurioItem> BLACK_SWAN = register(
    "black_swan_curios",
    "黑天鹅",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(-4)
      .prudence(-4)
      .temperance(10)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> ENGULFING_DREAM = register(
    "engulfing_dream_curios",
    "迷魂梦境",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(4)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> CHERRY_BLOSSOMS = register(
    "cherry_blossoms_curios",
    "落樱",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(2)
      .temperance(0)
      .justice(2)
  );
  public static final DeferredItem<EgoCurioItem> FEATHER_OF_HONOR = register(
    "feather_of_honor_curios",
    "荣耀之羽",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(2)
      .temperance(0)
      .justice(4)
  );
  public static final DeferredItem<EgoCurioItem> SO_CUTE = register(
    "so_cute_curios",
    "超特么可爱！！！",
    CuriosType.HEAD,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(4)
      .prudence(0)
      .temperance(-2)
      .justice(0)
  );
  //endregion

  //region 后脑
  public static final DeferredItem<EgoCurioItem> BENEDICTION = register(
    "benediction_curios",
    "祝福",
    CuriosType.HINDBRAIN,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(6)
      .prudence(6)
      .temperance(6)
      .justice(6)
  );
  //endregion

  //region 眼
  public static final DeferredItem<EgoCurioItem> SOLITUDE = register(
    "solitude_curios",
    "孤独",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(3)
      .justice(0)
  );
  // TODO 穿戴“沉默乐团”的E.G.O全套护甲时，能够吸收精神伤害。（免疫所有精神伤害并将之转化为恢复精神值）
  public static final DeferredItem<EgoCurioItem> DA_CAPO = register(
    "da_capo_curios",
    "Da Capo",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(4)
      .justice(0)
      .addTooltip("穿戴“沉默乐团”的E.G.O全套护甲时，能够吸收精神伤害。（免疫所有精神伤害并将之转化为恢复精神值）")
  );
  // TODO 洞察工作的成功率提高3%
  public static final DeferredItem<EgoCurioItem> GRINDER_MK4 = register(
    "grinder_mk4_curios",
    "粉碎机Mk4",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(4)
      .justice(0)
      .addTooltip("洞察工作的成功率提高3%")
  );
  public static final DeferredItem<EgoCurioItem> RED_EYES = register(
    "red_eyes_curios",
    "赤瞳",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(3)
      .justice(0)
  );
  // TODO 压迫工作的成功率提高6%
  public static final DeferredItem<EgoCurioItem> JUSTITIA = register(
    "justitia_curios",
    "正义裁决者",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(0)
      .justice(6)
      .addTooltip("压迫工作的成功率提高6%")
      .enderMask()
  );
  public static final DeferredItem<EgoCurioItem> SMILE = register(
    "smile_curios",
    "笑靥",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(5)
      .prudence(5)
      .temperance(0)
      .justice(0)
      .enderMask()
  );
  public static final DeferredItem<EgoCurioItem> CENSORED = register(
    "censored_curios",
    "CENSORED",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(10)
      .temperance(0)
      .justice(0)
      .enderMask()
  );
  public static final DeferredItem<EgoCurioItem> TODAY_IS_EXPRESSION = register(
    "today_is_expression_curios",
    "此刻的神色",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(-2)
      .temperance(4)
      .justice(0)
      .enderMask()
  );
  public static final DeferredItem<EgoCurioItem> SOUND_OF_A_STAR = register(
    "sound_of_a_star_curios",
    "新星之声",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder().fortitude(0)
      .prudence(0)
      .temperance(0)
      .justice(10, 0)
      .enderMask()
  );
  public static final DeferredItem<EgoCurioItem> TOUGH = register(
    "tough_curios",
    "谢顶之灾",
    CuriosType.EYE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(0)
      .justice(2)
  );
  //endregion

  //region 面
  public static final DeferredItem<EgoCurioItem> COBALT_SCAR = register(
    "cobalt_scar_curios",
    "郁蓝创痕",
    CuriosType.FACE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(4)
      .prudence(0)
      .temperance(0)
      .justice(2)
  );
  //endregion

  //region 脸颊
  // TODO 生命值治疗效果提高5%
  public static final DeferredItem<EgoCurioItem> MIMICRY = register(
    "mimicry_curios",
    "拟态",
    CuriosType.CHEEK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(10)
      .prudence(0)
      .temperance(0)
      .justice(0)
      .addTooltip("生命值治疗效果提高5%")
  );
  // TODO 受到精神伤害时，恢复伤害值20%的精神值，并暂时提高10点攻击速度。
  public static final DeferredItem<EgoCurioItem> HARMONY = register(
    "harmony_curios",
    "谐奏放射器",
    CuriosType.CHEEK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(8)
      .prudence(-4)
      .temperance(0)
      .justice(0)
      .addTooltip("受到精神伤害时，恢复伤害值20%的精神值，并暂时提高10点攻击速度。")
  );
  public static final DeferredItem<EgoCurioItem> THOSE_WHO_KNOW_THE_CRUELTY_OF_WINTER_AND_THE_AROMA_OF_ROSES = register(
    "those_who_know_the_cruelty_of_winter_and_the_aroma_of_roses_curios",
    "我深知严冬的残酷...和玫瑰的芬芳...",
    CuriosType.CHEEK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(6)
      .prudence(6)
      .temperance(0)
      .justice(0)
      .addTooltip("玫瑰盛开...雪宫崩塌...", Style.EMPTY.withColor(LcDamageType.THE_SOUL.getColourValue()))
      .addTooltip("欢笑的人们不曾记得...", Style.EMPTY.withColor(LcDamageType.THE_SOUL.getColourValue()))
      .addTooltip("在那有位沉睡的美人...", Style.EMPTY.withColor(LcDamageType.THE_SOUL.getColourValue()))
  );
  public static final DeferredItem<EgoCurioItem> THE_SWORD_SHARPENED_WITH_TEARS = register(
    "the_sword_sharpened_with_tears_curios",
    "盈泪之剑",
    CuriosType.CHEEK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(2)
      .temperance(0)
      .justice(4)
  );
  //endregion

  //region 口罩
  public static final DeferredItem<EgoCurioItem> REGRET = register(
    "regret_curios",
    "悔恨",
    CuriosType.MASK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(2)
      .prudence(2)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> CRIMSON_SCAR = register(
    "crimson_scar_curios",
    "猩红创痕",
    CuriosType.MASK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(2)
      .prudence(0)
      .temperance(0)
      .justice(4)
  );
  //endregion

  //region 口
  public static final DeferredItem<EgoCurioItem> FOURTH_MATCH_FLAME = register(
    "fourth_match_flame_curios",
    "终末火柴之光",
    CuriosType.MOUTH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(4)
      .prudence(0)
      .temperance(0)
      .justice(0)
  );
  // TODO 持有“红舞鞋”E.G.O武器时，降低10点成功率和工作速度，提高10点攻击速度。
  public static final DeferredItem<EgoCurioItem> SANGUINE_DESIRE = register(
    "sanguine_desire_curios",
    "血之渴望",
    CuriosType.MOUTH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(4)
      .prudence(0)
      .temperance(0)
      .justice(0)
      .addTooltip("持有“红舞鞋”E.G.O武器时，降低10点成功率和工作速度，提高10点攻击速度。")
  );
  public static final DeferredItem<EgoCurioItem> SODA = register(
    "soda_curios",
    "美味苏打",
    CuriosType.MOUTH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(2)
      .prudence(0)
      .temperance(0)
      .justice(0)
  );
  // TODO 持有“魔弹”E.G.O武器时，提高3点最大与最小攻击力。
  public static final DeferredItem<EgoCurioItem> MAGIC_BULLET = register(
    "magic_bullet_curios",
    "魔弹",
    CuriosType.MOUTH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .model("magic_bullet")
      .fortitude(-5)
      .prudence(-5)
      .temperance(0)
      .justice(10)
      .addTooltip("持有“魔弹”E.G.O武器时，提高3点最大与最小攻击力。")
  );
  public static final DeferredItem<EgoCurioItem> ECSTASY = register(
    "ecstasy_curios",
    "沉醉",
    CuriosType.MOUTH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(5)
      .prudence(0)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> LANTERN = register(
    "lantern_curios",
    "诱捕幻灯",
    CuriosType.MOUTH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(6)
      .temperance(0)
      .justice(0)
  );
  //endregion

  //region 颈
  // TODO 每隔一段时间为佩戴者恢复少量生命值。
  public static final DeferredItem<EgoCurioItem> OUR_GALAXY = register(
    "our_galaxy_curios",
    "小小银河",
    CuriosType.NECK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(3)
      .justice(0)
      .addTooltip("每隔一段时间为佩戴者恢复少量生命值。")
  );
  public static final DeferredItem<EgoCurioItem> BEAK = register(
    "beak_curios",
    "小喙",
    CuriosType.NECK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(0)
      .justice(2)
  );
  public static final DeferredItem<EgoCurioItem> HARVEST = register(
    "harvest_curios",
    "猎头长耙",
    CuriosType.NECK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(4)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> PLEASURE = register(
    "pleasure_curios",
    "因乐癫狂",
    CuriosType.NECK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(10)
      .temperance(-6)
      .justice(0)
  );
  //endregion

  //region 胸针
  // TODO 穿戴“噪音”E.G.O护甲全套时，减少10点最高精神值并增加10点攻击速度。
  public static final DeferredItem<EgoCurioItem> NOISE = register(
    "noise_curios",
    "噪音",
    CuriosType.BROOCH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(0)
      .justice(2)
      .addTooltip("穿戴“噪音”E.G.O护甲全套时，减少10点最高精神值并增加10点攻击速度。")
  );
  public static final DeferredItem<EgoCurioItem> LOGGING = register(
    "logging_curios",
    "伐木者",
    CuriosType.BROOCH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(2)
      .prudence(0)
      .temperance(2)
      .justice(0)
  );
  // TODO 持有“绿色枝干”E.G.O武器时，最大与最小攻击力会提高5点。
  public static final DeferredItem<EgoCurioItem> GREEN_STEM = register(
    "green_stem_curios",
    "绿色枝干",
    CuriosType.BROOCH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(6)
      .temperance(0)
      .justice(0)
      .addTooltip("持有“绿色枝干”E.G.O武器时，最大与最小攻击力会提高5点。")
  );
  public static final DeferredItem<EgoCurioItem> FRAGMENTS_FROM_SOMEWHERE = register(
    "fragments_from_somewhere_curios",
    "彼方的裂片",
    CuriosType.BROOCH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(2)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> MOONLIGHT = register(
    "moonlight_curios",
    "月光",
    CuriosType.BROOCH,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(1)
      .prudence(1)
      .temperance(1)
      .justice(1)
  );
  //endregion

  //region 手
  // TODO 本能工作的成功率提高6%
  public static final DeferredItem<EgoCurioItem> GOLD_RUSH = register(
    "gold_rush_curios",
    "闪金冲锋",
    CuriosType.HAND,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(6)
      .prudence(0)
      .temperance(0)
      .justice(0)
      .addTooltip("本能工作的成功率提高6%")
  );
  public static final DeferredItem<EgoCurioItem> AMITA = register(
    "amita_curios",
    "无量",
    CuriosType.HAND,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(10)
      .prudence(-4)
      .temperance(0)
      .justice(0)
  );
  //endregion

  //region 手套
  public static final DeferredItem<EgoCurioItem> WRIST_CUTTER = register(
    "wrist_cutter_curios",
    "割腕者",
    CuriosType.GLOVE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(2)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> SPORE = register(
    "spore_curios",
    "荧光菌孢",
    CuriosType.GLOVE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(5)
      .temperance(2)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> EXUVIAE = register(
    "exuviae_curios",
    "脱落之皮",
    CuriosType.GLOVE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(5)
      .prudence(2)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> GAZE = register(
    "gaze_curios",
    "凝视",
    CuriosType.GLOVE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(4)
      .prudence(0)
      .temperance(0)
      .justice(0)
  );
  public static final DeferredItem<EgoCurioItem> WINGBEAT = register(
    "wingbeat_curios",
    "翅振",
    CuriosType.GLOVE,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(0)
      .prudence(0)
      .temperance(2)
      .justice(0)
  );
  //endregion

  //region 右背
  public static final DeferredItem<EgoCurioItem> SOLEMN_LAMENT = register(
    "solemn_lament_curios",
    "圣宣",
    CuriosType.RIGHT_BACK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(2)
      .prudence(2)
      .temperance(2)
      .justice(2)
  );
  public static final DeferredItem<EgoCurioItem> THROUGH_THE_DARK_TWILIGHT = register(
    "through_the_dark_twilight_curios",
    "破晓",
    CuriosType.RIGHT_BACK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .fortitude(7)
      .prudence(7)
      .temperance(7)
      .justice(7)
      .addTooltip("人们最终战胜了黄昏的黑暗，准备面对黎明的光辉。", Style.EMPTY.withColor(0xffcb1d))
      .addTooltip("而在那片昏暗的森林中，鸟儿的叽喳鸣唱依旧响彻着吗？", Style.EMPTY.withColor(0xffcb1d))
  );
  //endregion

  //region 左背
  public static final DeferredItem<EgoCurioItem> PARADISE_LOST = register(
    "paradise_lost_curios",
    "失乐园",
    CuriosType.LEFT_BACK,
    EgoCurioItem::new,
    new EgoCurioItem.Builder()
      .model("paradise_lost")
      .fortitude(10)
      .prudence(10)
      .temperance(0)
      .justice(10)
  );
  //endregion

  private static <T extends EgoCurioItem> DeferredItem<T> register(
    EntityType<?> entityType,
    String zhName,
    CuriosType type,
    Function<EgoCurioItem.Builder, ? extends T> item,
    EgoCurioItem.Builder builder
  ) {
    return register("%s_curios_%s".formatted(entityType.getDescriptionId(), type), zhName, type, item, builder);
  }

  private static <T extends EgoCurioItem> DeferredItem<T> register(
    String name,
    String zhName,
    CuriosType type,
    Function<EgoCurioItem.Builder, ? extends T> item,
    EgoCurioItem.Builder builder
  ) {
    DeferredItem<T> deferredItem = REGISTRY.register(name, () -> item.apply(builder));
    type.addCurio(deferredItem);
    ZhCn.ITEMS.put(deferredItem, zhName);
    return deferredItem;
  }

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  public enum CuriosType {
    HEADWEAR("headwear", DatagenItemTag.EGO_CURIOS_HEADWEAR),
    CHEEK("cheek", DatagenItemTag.EGO_CURIOS_CHEEK),
    HEAD("head", DatagenItemTag.EGO_CURIOS_HEAD),
    HINDBRAIN("hindbrain", DatagenItemTag.EGO_CURIOS_HINDBRAIN),
    EYE("eye", DatagenItemTag.EGO_CURIOS_EYE),
    FACE("face", DatagenItemTag.EGO_CURIOS_FACE),
    MASK("mask", DatagenItemTag.EGO_CURIOS_MASK),
    MOUTH("mouth", DatagenItemTag.EGO_CURIOS_MOUTH),
    NECK("neck", DatagenItemTag.EGO_CURIOS_NECK),
    BROOCH("brooch", DatagenItemTag.EGO_CURIOS_NECK),
    HAND("hand", DatagenItemTag.EGO_CURIOS_HAND),
    GLOVE("glove", DatagenItemTag.EGO_CURIOS_GLOVE),
    RIGHT_BACK("right_back", DatagenItemTag.EGO_CURIOS_RIGHT_BACK),
    LEFT_BACK("left_back", DatagenItemTag.EGO_CURIOS_LEFT_BACK),
    ;
    private final String name;
    private final Set<DeferredItem<EgoCurioItem>> set;

    CuriosType(final String name, final Set<DeferredItem<EgoCurioItem>> set) {
      this.name = name;
      this.set = set;
    }

    public String getName() {
      return name;
    }

    public <T extends EgoCurioItem> void addCurio(DeferredItem<T> item) {
      this.set.add((DeferredItem<EgoCurioItem>) item);
    }
  }
}
