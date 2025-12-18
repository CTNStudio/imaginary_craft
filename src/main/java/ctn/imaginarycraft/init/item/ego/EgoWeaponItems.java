package ctn.imaginarycraft.init.item.ego;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueRating;
import ctn.imaginarycraft.common.item.ego.weapon.melee.*;
import ctn.imaginarycraft.common.item.ego.weapon.remote.*;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.MeleeEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.RemoteEgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// TODO dot：在计时器正常运作的前提下，每1秒受到x点指定种类的无来源伤害。该伤害不受等级压制影响。
public final class EgoWeaponItems extends EgoWeaponRegisterUtil {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  //region ZAYIN
  public static final DeferredItem<RemoteEgoWeaponItem> SODA = registerRemoteTemplate(
    "soda_weapon", "美味苏打",
    LcLevelType.ZAYIN, RemoteTemplateType.PISTOL,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(0.667f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null),
    "soda");

  public static final DeferredItem<MeleeEgoWeaponItem> PENITENCE = registerMeleeTemplate(
    "penitence_weapon", "忏悔",
    LcLevelType.ZAYIN, MeleeTemplateType.MACE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(6)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null),
    "penitence");

  public static final DeferredItem<MeleeEgoWeaponItem> WINGBEAT = registerMeleeTemplate(
    "wingbeat_weapon", "翅振",
    LcLevelType.ZAYIN, MeleeTemplateType.MACE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(6)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null),
    "wingbeat");
  //endregion

  //region TETH
  public static final DeferredItem<RemoteEgoWeaponItem> FOURTH_MATCH_FLAME = registerRemoteTemplate(
    "fourth_match_flame_weapon", "终末火柴之光",
    LcLevelType.TETH, RemoteTemplateType.CANNON,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(25)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null),
    "fourth_match_flame");

  public static final DeferredItem<RemoteEgoWeaponItem> SOLITUDE = registerRemoteTemplate(
    "solitude_weapon", "孤独",
    LcLevelType.TETH, RemoteTemplateType.PISTOL,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(2.5f)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null),
    "solitude");

  public static final DeferredItem<RemoteEgoWeaponItem> BEAK = registerRemoteTemplate(
    "beak_weapon", "小喙",
    LcLevelType.TETH, RemoteTemplateType.PISTOL,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(2.5f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null),
    "beak");

  public static final DeferredItem<RemoteEgoWeaponItem> TODAY_IS_EXPRESSION = registerRemoteTemplate(
    "today_is_expression_weapon", "此刻的神色",
    LcLevelType.TETH, RemoteTemplateType.PISTOL,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(2.5f)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, null, null),
    "today_is_expression");

  // TODO 只有秃顶或是地中海的帅气员工才能使用这件屌爆的装备！
  // 这是什么阴的没边的效果（
  public static final DeferredItem<RemoteEgoWeaponItem> TOUGH = registerRemoteTemplate(
    "tough_weapon", "谢顶之灾",
    LcLevelType.TETH, RemoteTemplateType.PISTOL,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(3)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null), // TODO 光头（
    "tough");

  // TODO 这把武器一次攻击动画会造成三次伤害。
  public static final DeferredItem<EngulfingDreamWeaponItem> ENGULFING_DREAM = register(
    "engulfing_dream_weapon", "迷魂梦境",
    LcLevelType.TETH, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(1.5f)
      .attackInterval(minuteToSpeedConversion(1))
      .attackPrecise(8)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null),
    (p, b) -> new EngulfingDreamWeaponItem(p, b, "engulfing_dream"));

  // TODO 这把武器一次攻击动画会造成三次伤害。
  public static final DeferredItem<CherryBlossomsWeaponItem> CHERRY_BLOSSOMS = register(
    "cherry_blossoms_weapon", "落樱",
    LcLevelType.TETH, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(1.5f)
      .attackInterval(minuteToSpeedConversion(1))
      .attackPrecise(5)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null),
    (p, b) -> new CherryBlossomsWeaponItem(p, b, "cherry_blossoms"));

  // TODO 如果持有者的勇气等级高于2级，进入战斗后会提高2.5点移动速度。
  public static final DeferredItem<MeleeEgoWeaponItem> RED_EYES = registerMeleeTemplate(
    "red_eyes_weapon", "赤瞳",
    LcLevelType.TETH, MeleeTemplateType.MACE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(8)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null),
    "red_eyes");

  public static final DeferredItem<MeleeEgoWeaponItem> HORN = registerMeleeTemplate(
    "horn_weapon", "犄角",
    LcLevelType.TETH, MeleeTemplateType.SPEAR,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(7)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null),
    "horn");

  public static final DeferredItem<MeleeEgoWeaponItem> WRIST_CUTTER = registerMeleeTemplate(
    "wrist_cutter_weapon", "割腕者",
    LcLevelType.TETH, MeleeTemplateType.KNIFE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(2.5f)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null),
    "wrist_cutter");

  public static final DeferredItem<MeleeEgoWeaponItem> REGRET = registerMeleeTemplate(
    "regret_weapon", "悔恨",
    LcLevelType.TETH, MeleeTemplateType.HAMMER,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(15)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null),
    "regret");

  // TODO 如果持有者的谨慎等级低于5级，进入战斗后有10%的概率提升40%的最大精神值。该效果持续30秒。
  public static final DeferredItem<MeleeEgoWeaponItem> FRAGMENTS_FROM_SOMEWHERE = registerMeleeTemplate(
    "fragments_from_somewhere_weapon", "彼方的裂片",
    LcLevelType.TETH, MeleeTemplateType.SPEAR,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(7)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, null, null),
    "fragments_from_somewhere");

  public static final DeferredItem<MeleeEgoWeaponItem> LANTERN = registerMeleeTemplate(
    "lantern_weapon", "诱捕幻灯",
    LcLevelType.TETH, MeleeTemplateType.HAMMER,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(15)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, null, null),
    "lantern");

  public static final DeferredItem<MeleeEgoWeaponItem> SO_CUTE = registerMeleeTemplate(
    "so_cute_weapon", "超特么可爱！！！",
    LcLevelType.TETH, MeleeTemplateType.FIST,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(5)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null),
    "so_cute");

  public static final DeferredItem<MeleeEgoWeaponItem> STANDARD_TRAINING_EGO = registerMeleeTemplate(
    "standard_training_ego_weapon", "教学用E.G.O武器",
    LcLevelType.TETH, MeleeTemplateType.MACE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(6)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null),
    "standard_training_ego");
  //endregion

  //region HE
  public static final DeferredItem<RemoteEgoWeaponItem> SCREAMING_WEDGE = registerRemoteTemplate(
    "screaming_wedge", "刺耳嚎叫",
    LcLevelType.HE, RemoteTemplateType.CROSSBOW,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(12)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, VirtueRating.II, null, null, null),
    "screaming_wedge");

  // TODO 如果持有者的生命值维持在10%及以上，那么持有者将消耗一定生命值发动更加强大的攻击。（造成30%的额外伤害）
  public static final DeferredItem<RemoteEgoWeaponItem> HARMONY = registerRemoteTemplate(
    "harmony_weapon", "谐奏放射器",
    LcLevelType.HE, RemoteTemplateType.CANNON,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(40)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(VirtueRating.II, null, null, null, VirtueRating.II),
    "harmony");

  public static final DeferredItem<RemoteEgoWeaponItem> LAETITIA = registerRemoteTemplate(
    "laetitia_weapon", "蕾蒂希娅",
    LcLevelType.HE, RemoteTemplateType.RIFLE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(5.5f)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.II, null, null),
    "laetitia");

  public static final DeferredItem<SyrinxWeaponItem> SYRINX = register(
    "syrinx_weapon", "泣婴",
    LcLevelType.HE, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(3)
      .attackInterval(minuteToSpeedConversion(0.5f))
      .attackPrecise(10)
      .invincibleTick(10)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, VirtueRating.III, null, null),
    (p, b) -> new SyrinxWeaponItem(p, b, "syrinx"));

  // TODO 这把武器一次攻击会造成6次伤害。
  public static final DeferredItem<GrinderMk4WeaponItem> GRINDER_MK4 = register(
    "grinder_mk4_weapon", "粉碎机Mk4",
    LcLevelType.HE, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(1.5f)
      .attackSpeed(minuteToSpeedConversion(1.67f))
      .attackDistance(4)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.II, null, null, null, null),
    (p, b) -> new GrinderMk4WeaponItem(p, b, "grinder_mk4"));

  public static final DeferredItem<OurGalaxyWeaponItem> OUR_GALAXY = register(
    "our_galaxy_weapon", "小小银河",
    LcLevelType.HE, SpecialTemplateType.MELEE,// TODO 特殊
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(11)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(8)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, VirtueRating.II, null, null, VirtueRating.II),
    (p, b) -> new OurGalaxyWeaponItem(p, b, "our_galaxy"));

  public static final DeferredItem<LifeForADaredevilWeaponItem> LIFE_FOR_A_DAREDEVIL = register(
    "life_for_a_daredevil_weapon", "决死之心",
    LcLevelType.HE, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(8)
      .attackSpeed(minuteToSpeedConversion(1.33f))
      .attackDistance(4)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.THE_SOUL)
      .virtueUsageReq(null, null, null, VirtueRating.II, null),
    (p, b) -> new LifeForADaredevilWeaponItem(p, b, "life_for_a_daredevil"));

  // TODO 这把武器每次攻击会造成9次伤害。
  // TODO 在攻击到敌人时，为敌人附加一个每秒受到2点物理伤害的dot，持续5秒，不可叠加。
  public static final DeferredItem<GazeWeaponItem> GAZE = register(
    "gaze_weapon", "凝视",
    LcLevelType.HE, SpecialTemplateType.SPECIAL,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(3)
      .attackSpeed(minuteToSpeedConversion(2.9f))
      .attackDistance(4)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, VirtueRating.II, VirtueRating.II, null, null),
    (p, b) -> new GazeWeaponItem(p, b, "gaze"));

  // TODO 这把武器击中目标时，会给目标附加每秒受到2点精神伤害的dot，持续5秒，不可叠加。
  public static final DeferredItem<PleasureWeaponItem> PLEASURE = register(
    "pleasure_weapon", "因乐癫狂",
    LcLevelType.HE, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(2.5f)
      .attackSpeed(minuteToSpeedConversion(1.67f))
      .attackDistance(5)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.III, null, null),
    (p, b) -> new PleasureWeaponItem(p, b, "pleasure"));

  public static final DeferredItem<MeleeEgoWeaponItem> BEAR_PAWS = registerMeleeTemplate(
    "bear_paws_weapon", "熊熊抱",
    LcLevelType.HE, MeleeTemplateType.FIST,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(7)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.II, null, null, null, null),
    "bear_paws");

  // TODO 如果持有者的自律等级低于3级，每次攻击都会提高最大与最小攻击力。但代价是每次攻击都会丧失等同于最大精神值4%的精神值。
  public static final DeferredItem<MeleeEgoWeaponItem> SANGUINE_DESIRE = registerMeleeTemplate(
    "sanguine_desire", "血之渴望",
    LcLevelType.HE, MeleeTemplateType.AXE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(6)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null),
    "sanguine_desire");

  public static final DeferredItem<MeleeEgoWeaponItem> LOGGING = registerMeleeTemplate(
    "logging_weapon", "伐木者",
    LcLevelType.HE, MeleeTemplateType.HAMMER,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(17)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, VirtueRating.II, null, null),
    "logging");

  // TODO 被这支武器刺中的目标会减少30%的移动速度，持续3秒。需要单独效果。
  public static final DeferredItem<MeleeEgoWeaponItem> FROST_SPLINTER = registerMeleeTemplate(
    "frost_splinter", "霜之碎片",
    LcLevelType.HE, MeleeTemplateType.SPEAR,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(7)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null),
    "frost_splinter");

  public static final DeferredItem<MeleeEgoWeaponItem> CHRISTMAS = registerMeleeTemplate(
    "christmas_weapon", "悲惨圣诞",
    LcLevelType.HE, MeleeTemplateType.MACE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(12)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null),
    "christmas");

  public static final DeferredItem<MeleeEgoWeaponItem> HARVEST = registerMeleeTemplate(
    "harvest_weapon", "猎头长耙",
    LcLevelType.HE, MeleeTemplateType.SPEAR,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(8)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, null, null),
    "harvest");
  //endregion

  //region WAW
  public static final DeferredItem<RemoteEgoWeaponItem> HORNET = registerRemoteTemplate(
    "hornet_weapon", "黄蜂",
    LcLevelType.WAW, RemoteTemplateType.RIFLE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(7.5f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.III, null, null, null, null),
    "hornet");

  public static final DeferredItem<RemoteEgoWeaponItem> FAINT_AROMA = registerRemoteTemplate(
    "faint_aroma_weapon", "余香",
    LcLevelType.WAW, RemoteTemplateType.CROSSBOW,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(15)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, VirtueRating.III, null, null, null),
    "faint_aroma");

  // TODO 攻击时有25%的概率给目标添加一个易伤效果，使其受到的物理伤害加深。
  public static final DeferredItem<RemoteEgoWeaponItem> EXUVIAE = registerRemoteTemplate(
    "exuviae_weapon", "脱落之皮",
    LcLevelType.WAW, RemoteTemplateType.CANNON,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(42.5f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, VirtueRating.IV),
    "exuviae");

  public static final DeferredItem<RemoteEgoWeaponItem> HYPOCRISY = registerRemoteTemplate(
    "hypocrisy_weapon", "伪善",
    LcLevelType.WAW, RemoteTemplateType.CROSSBOW,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(15)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, VirtueRating.III, null, null, null),
    "hypocrisy");

  // TODO 这件E.G.O在攻击时会造成随机类型的伤害。这件E.G.O命中员工时，会根据伤害类型为员工恢复生命值和精神值。
  public static final DeferredItem<InTheNameOfLoveAndHateWeaponItem> IN_THE_NAME_OF_LOVE_AND_HATE = register(
    "in_the_name_of_love_and_hate_weapon", "以爱与恨之名",
    LcLevelType.WAW, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(6.5f)
      .attackInterval(minuteToSpeedConversion(0.83f))
      .attackPrecise(80)
      .invincibleTick((int) (20 * 0.83f))
      .meleeLcDamageType(null, LcDamageType.PHYSICS, LcDamageType.SPIRIT, LcDamageType.EROSION, LcDamageType.THE_SOUL)
      .virtueUsageReq(VirtueRating.III, null, null, VirtueRating.III, VirtueRating.IV),
    (p, b) -> new InTheNameOfLoveAndHateWeaponItem(p, b, "in_the_name_of_love_and_hate"));

  // TODO 如果持有者的生命值低于50%，武器的伤害会额外增加50%。但是，持有者在该状态下攻击时会对其他员工造成无差别伤害。
  public static final DeferredItem<CrimsonScarWeaponItem> CRIMSON_SCAR = register(
    "crimson_scar_weapon", "猩红创痕",
    LcLevelType.WAW, SpecialTemplateType.MELEE, // 有远程攻击手段但主要近战
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(12)
      .attackSpeed(minuteToSpeedConversion(1.53f))
      .attackDistance(3 - 15)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.III, null, null, VirtueRating.III, VirtueRating.III),
    (p, b) -> new CrimsonScarWeaponItem(p, b, "crimson_scar"));

  // TODO 持有者攻击时会在一定时间内增加物理伤害。如果持有者的生命值小于或等于50%，武器的伤害会额外增加50%。但是，持有者在该状态下攻击时会对其他员工造成无差别伤害。
  public static final DeferredItem<CobaltScarWeaponItem> COBALT_SCAR = register(
    "cobalt_scar_weapon", "郁蓝创痕",
    LcLevelType.WAW, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(15)
      .attackSpeed(minuteToSpeedConversion(1.83f))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.III, null, VirtueRating.III, null, VirtueRating.II),
    (p, b) -> new CobaltScarWeaponItem(p, b, "cobalt_scar"));

  public static final DeferredItem<SolemnLamentWeaponItem> SOLEMN_LAMENT_BLACK = register(
    "solemn_lament_black_weapon", "圣宣-黑",
    LcLevelType.WAW, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(2)
      .attackInterval(minuteToSpeedConversion(0.5f))
      .attackPrecise(10)
      .invincibleTick((int) (20 * 0.5f))
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, VirtueRating.III, null),
    (p, b) -> new SolemnLamentWeaponItem(p, b, "solemn_lament"));

  public static final DeferredItem<SolemnLamentWeaponItem> SOLEMN_LAMENT_WHITE = register(
    "solemn_lament_white_weapon", "圣宣-白",
    LcLevelType.WAW, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(2)
      .attackInterval(minuteToSpeedConversion(0.5f))
      .attackPrecise(10)
      .invincibleTick((int) (20 * 0.5f))
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, VirtueRating.III, null),
    (p, b) -> new SolemnLamentWeaponItem(p, b, "solemn_lament"));

  public static final DeferredItem<MagicBulletWeaponItem> MAGIC_BULLET = register(
    "magic_bullet_weapon", "魔弹",
    LcLevelType.WAW, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(21)
      .attackInterval(minuteToSpeedConversion(2.33f))
      .attackPrecise(50)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.III,
        null,
        null),
    (p, b) -> new MagicBulletWeaponItem(p, b, "magic_bullet"));

  // TODO 这把武器一次攻击动画会造成2次侵蚀伤害。
  // TODO 持有者进入战斗后，有一定概率反弹自身受到的伤害。
  public static final DeferredItem<BlackSwanWeaponItem> BLACK_SWAN = register(
    "black_swan_weapon", "黑天鹅",
    LcLevelType.WAW, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(6)
      .attackSpeed(minuteToSpeedConversion(1.63f))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, VirtueRating.III, null, null, null),
    (p, b) -> new BlackSwanWeaponItem(p, b, "black_swan"));

  public static final DeferredItem<EcstasyWeaponItem> ECSTASY = register(
    "ecstasy_weapon", "沉醉",
    LcLevelType.WAW, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(3)
      .attackSpeed(minuteToSpeedConversion(0.83f))
      .attackDistance(10)
      .invincibleTick((int) (20 * 0.83f))
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(VirtueRating.II, null, null, null, VirtueRating.II),
    (p, b) -> new EcstasyWeaponItem(p, b, "ecstasy"));

  // TODO 这把武器攻击时有30%的概率使用特殊攻击，持有者先举起刺剑，然后对目标进行快速戳刺，造成9次1-2点精神伤害和1次9-12点精神伤害。
  public static final DeferredItem<TheSwordSharpenedWithTearsWeaponItem> THE_SWORD_SHARPENED_WITH_TEARS = register(
    "the_sword_sharpened_with_tears_weapon", "盈泪之剑",
    LcLevelType.WAW, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(10.5f)
      .attackSpeed(minuteToSpeedConversion(1.33f))
      .attackDistance(4)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, VirtueRating.IV),
    (p, b) -> new TheSwordSharpenedWithTearsWeaponItem(p, b, "the_sword_sharpened_with_tears"));

  public static final DeferredItem<FeatherOfHonorWeaponItem> FEATHER_OF_HONOR = register(
    "feather_of_honor_weapon", "荣耀之羽",
    LcLevelType.WAW, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(4.5f)
      .attackInterval(minuteToSpeedConversion(0.5f))
      .attackPrecise(15)
      .invincibleTick((int) (20 * 0.5f))
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(VirtueRating.III, null, null, null, null),
    (p, b) -> new FeatherOfHonorWeaponItem(p, b, "feather_of_honor"));

  // TODO 这把武器在攻击时有10%的概率使用特殊攻击，具体效果为：持有者对目标进行4次快速戳刺和一次劈砍，造成4次2.5点侵蚀伤害和一次9-12点侵蚀伤害。
  public static final DeferredItem<DiscordWeaponItem> DISCORD = register(
    "discord_weapon", "不和",
    LcLevelType.WAW, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(9)
      .attackSpeed(minuteToSpeedConversion(1.33f))
      .attackDistance(5)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.III,
        null,
        VirtueRating.III),
    (p, b) -> new DiscordWeaponItem(p, b, "discord"));

  // TODO 这把武器在攻击时有15%的概率使用特殊攻击，持有者对目标进行一次快速上挑和下砸然后以拐杖点地，造成2次5.5点侵蚀伤害和10点侵蚀伤害。
  // TODO 这把武器发动特殊攻击时，能够为同房间的所有职员附加一层和中央本部科技完全相同的反侵蚀力场盾。这层反侵蚀力场盾不会影响其余类型护盾的效果并可以和它们共存，若已经存在附加的反侵蚀力场盾，那么该护盾的承伤能力将恢复为最大值。
  public static final DeferredItem<MoonlightWeaponItem> MOONLIGHT = register(
    "moonlight_weapon", "月光",
    LcLevelType.WAW, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(9)
      .attackSpeed(minuteToSpeedConversion(1.17f))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(VirtueRating.III, null, null, null, VirtueRating.IV),
    (p, b) -> new MoonlightWeaponItem(p, b, "moonlight"));

  // TODO 这把武器每次攻击时会造成3次物理伤害。
  public static final DeferredItem<AmitaWeaponItem> AMITA = register(
    "amita_weapon", "无量",
    LcLevelType.WAW, SpecialTemplateType.SPECIAL,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(3.5f)
      .attackSpeed(minuteToSpeedConversion(2.33f))
      .attackDistance(4)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.III,
        null,
        VirtueRating.III),
    (p, b) -> new AmitaWeaponItem(p, b, "amita"));

  public static final DeferredItem<MeleeEgoWeaponItem> LAMP = registerMeleeTemplate(
    "lamp_weapon", "目灯",
    LcLevelType.WAW, MeleeTemplateType.HAMMER,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(24)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(VirtueRating.III, VirtueRating.III, null, null, null),
    "lamp");

  public static final DeferredItem<MeleeEgoWeaponItem> GREEN_STEM = registerMeleeTemplate(
    "green_stem_weapon", "绿色枝干",
    LcLevelType.WAW, MeleeTemplateType.SPEAR,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(12)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.III, null, null),
    "green_stem");

  // TODO 持有者攻击时有25%的概率给目标添加一个易伤效果，使其受到的精神伤害加深。
  public static final DeferredItem<MeleeEgoWeaponItem> SPORE = registerMeleeTemplate(
    "spore_weapon", "荧光菌孢",
    LcLevelType.WAW, MeleeTemplateType.SPEAR,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(12)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, VirtueRating.II, null, VirtueRating.II),
    "spore");

  public static final DeferredItem<MeleeEgoWeaponItem> HEAVEN = registerMeleeTemplate(
    "heaven_weapon", "穿刺极乐",
    LcLevelType.WAW, MeleeTemplateType.SPEAR,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(10)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, VirtueRating.III),
    "heaven");

  public static final DeferredItem<MeleeEgoWeaponItem> DIFFRACTION = registerMeleeTemplate(
    "diffraction_weapon", "虚无衍射体",
    LcLevelType.WAW, MeleeTemplateType.MACE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(16)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, VirtueRating.III, null, null, null),
    "diffraction");
  //endregion

  //region ALEPH
  // TODO 造成伤害的25%会转化成生命值。
  public static final DeferredItem<MimicryWeaponItem> MIMICRY = register(
    "mimicry_weapon", "拟态",
    LcLevelType.ALEPH, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(12)
      .attackSpeed(minuteToSpeedConversion(1.7f))
      .attackDistance(4)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        VirtueRating.V,
        null,
        null,
        null,
        VirtueRating.V),
    (p, b) -> new MimicryWeaponItem(p, b, "mimicry"));

  // TODO 这把武器每次攻击时会造成5次灵魂伤害。
  public static final DeferredItem<JustitiaWeaponItem> JUSTITIA = register(
    "justitia_weapon", "正义裁决者",
    LcLevelType.ALEPH, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(3)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(4)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.THE_SOUL)
      .virtueUsageReq(null, null, null, VirtueRating.V, VirtueRating.V),
    (p, b) -> new JustitiaWeaponItem(p, b, "justitia"));

  // TODO 持有者无法通过各部门的"再生反应堆"恢复生命值和精神值。
  // TODO 发起普通攻击时，这把武器能减少被击中单位的移动速度，同时恢复持有者的生命值和精神值。(伤害量和恢复量取决于击中单位的数量。)
  // TODO 发起特殊攻击时，这把武器会生成一个力场盾，减少受到的所有类型的伤害。(如果设施内没有收容"白夜"，就不能发起特殊攻击。)
  public static final DeferredItem<ParadiseLostWeaponItem> PARADISE_LOST = register(
    "paradise_lost_weapon", "失乐园",
    LcLevelType.ALEPH, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(24)
      .attackInterval(minuteToSpeedConversion(2))
      .attackPrecise(80)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.THE_SOUL)
      .virtueUsageReq(VirtueRating.V, VirtueRating.V, VirtueRating.V, VirtueRating.V, null),
    (p, b) -> new ParadiseLostWeaponItem(p, b, "paradise_lost"));

  // TODO 只有全属性超过110的员工才能拿起这把武器。
  // TODO 这把武器会同时造成物理，精神，侵蚀和灵魂伤害。
  // TODO 这把武器每隔一段时间会进行一次特殊攻击。
  public static final DeferredItem<TwilightWeaponItem> TWILIGHT = register(
    "twilight_weapon", "薄暝",
    LcLevelType.ALEPH, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(14)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(6)
      .invincibleTick(20)
      .meleeLcDamageType(null,
        LcDamageType.PHYSICS,
        LcDamageType.SPIRIT,
        LcDamageType.EROSION,
        LcDamageType.THE_SOUL)
      .virtueUsageReq(111, 111, 111, 111, 0),
    (p, b) -> new TwilightWeaponItem(p, b, "twilight"));

  // TODO 持有者每次攻击时都有10%的概提高5点最大与最小攻击力，该效果持续12秒。代价是，120秒内，持有者的自律相关属性会降低50%。
  // TODO 这把武器每次攻击时会造成3次物理伤害。
  public static final DeferredItem<GoldRushWeaponItem> GOLD_RUSH = register(
    "gold_rush_weapon", "闪金冲锋",
    LcLevelType.ALEPH, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(5.5f)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(2)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.V, null, null, null, VirtueRating.V),
    (p, b) -> new GoldRushWeaponItem(p, b, "gold_rush"));

  // TODO 每击杀一个目标，持有者的勇气与正义的相关属性会提高3点。该效果仅在当天有效。
  // TODO 发起普通攻击时，这把武器能减少被击中单位的移动速度。
  // TODO 发起特殊攻击时，这把武器能减少当前区域中所有敌对单位的移动速度。
  // TODO 这把武器在攻击时有30%的概率使用特殊攻击，持有者举起武器，跳起后重砸，对小范围内的目标造成1次35-45点侵蚀伤害和5次4点侵蚀伤害。
  public static final DeferredItem<SmileWeaponItem> SMILE = register(
    "smile_weapon", "笑靥",
    LcLevelType.ALEPH, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(13)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(5)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.V, null, VirtueRating.V),
    (p, b) -> new SmileWeaponItem(p, b, "smile"));

  // TODO 每次攻击造成2次伤害
  // TODO 持有这「CENSORED」的员工受到伤害时，「CENSORED」会为其恢复相当于伤害数值40%的生命
  // TODO 这把武器的持有者在受到伤害时，会立刻得到和伤害类型相对应，且等同于伤害量40%的治疗效果。但若持有者在受到伤害后死亡，则无法得到对应的回复。(任何类型的伤害都会计入，包括恐惧伤害)
  public static final DeferredItem<CensoredWeaponItem> CENSORED = register(
    "censored_weapon", "CENSORED",
    LcLevelType.ALEPH, SpecialTemplateType.MELEE,
    new Item.Properties(),
    new MeleeEgoWeaponItem.Builder()
      .damage(9)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(VirtueRating.V, null, null, null, VirtueRating.V),
    (p, b) -> new CensoredWeaponItem(p, b, "censored"));

  // TODO 这把武器在攻击时会造成贯穿伤害，但该武器的伤害不会伤害到中立目标和友方目标。
  // TODO 这把武器的伤害和攻击次数与员工的最大精神值数值无关，仅与员工当前精神值占最大精神值的百分比有关。
  // TODO 持有者的精神值在低于最大精神值的30%时，武器产生的"新星之声"仅有一个，伤害为和面板相同的8-12点精神伤害。
  // TODO 持有者的精神值大于最大精神值的30%且小于60%时，每次攻击时武器产生的"新星之声"变为2个，增加的"新星之声"伤害为9-11点精神伤害
  // TODO 持有者的精神值大于最大精神值的60%时，每次攻击时武器产生的"新星之声"变为3个，再额外增加的"新星之声"伤害为11-16点精神伤害
  public static final DeferredItem<SoundOfAStarWeaponItem> SOUND_OF_A_STAR = register(
    "sound_of_a_star_weapon", "新星之声",
    LcLevelType.ALEPH, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(9)
      .attackInterval(minuteToSpeedConversion(1.67f))
      .attackPrecise(25)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, VirtueRating.V, VirtueRating.V, null, VirtueRating.V),
    (p, b) -> new SoundOfAStarWeaponItem(p, b, "sound_of_a_star"));

  // TODO 这把武器每次攻击之前需要花费等同于一次攻击间隔的时间趴下来准备攻击；若目标始终处于射程范围内且保持与武器持有者相对方向相同，则不需要再次准备。
  public static final DeferredItem<PinkWeaponItem> PINK = register(
    "pink_weapon", "粉红军备",
    LcLevelType.ALEPH, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(22)
      .attackInterval(minuteToSpeedConversion(2))
      .attackPrecise(35)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, VirtueRating.V, null, null, VirtueRating.V),
    (p, b) -> new PinkWeaponItem(p, b, "pink"));

  // TODO 这把武器的子弹命中时，会给目标附加每秒受到 2 点侵蚀伤害的dot，持续 5 秒，不可叠加。
  // TODO 这把武器的子弹命中时，会使目标的移动速度在 5 秒内降低 30%。
  public static final DeferredItem<AdorationWeaponItem> ADORATION = register(
    "adoration_weapon", "爱慕",
    LcLevelType.ALEPH, SpecialTemplateType.REMOTE,
    new Item.Properties(),
    new RemoteEgoWeaponItem.Builder()
      .damage(33)
      .attackInterval(minuteToSpeedConversion(3.47f))
      .attackPrecise(15)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, VirtueRating.V, null, VirtueRating.V),
    (p, b) -> new AdorationWeaponItem(p, b, "adoration"));
  //endregion

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }
}
