package ctn.imaginarycraft.init.item.ego;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.LcLevelType;
import ctn.imaginarycraft.api.virtue.VirtueRating;
import ctn.imaginarycraft.common.item.ego.weapon.melee.*;
import ctn.imaginarycraft.common.item.ego.weapon.remote.*;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EgoWeaponItems extends EgoWeaponRegisterUtil {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  //region ZAYIN
  public static final DeferredItem<?> SODA = onRemote()
    .id("soda_weapon")
    .zhName("美味苏打")
    .lcLevelType(LcLevelType.ZAYIN)
    .type(RemoteTemplateType.PISTOL)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(0.667f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/soda")
    .buildAndRegister();

  public static final DeferredItem<?> PENITENCE = onMelee()
    .id("penitence_weapon")
    .zhName("忏悔")
    .lcLevelType(LcLevelType.ZAYIN)
    .type(MeleeTemplateType.MACE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(6)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/penitence")
    .buildAndRegister();

  public static final DeferredItem<?> WINGBEAT = onMelee()
    .id("wingbeat_weapon")
    .zhName("翅振")
    .lcLevelType(LcLevelType.ZAYIN)
    .type(MeleeTemplateType.MACE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(6)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/wingbeat")
    .buildAndRegister();
  //endregion

  //region TETH
  public static final DeferredItem<?> FOURTH_MATCH_FLAME = onRemote()
    .id("fourth_match_flame_weapon")
    .zhName("终末火柴之光")
    .lcLevelType(LcLevelType.TETH)
    .type(RemoteTemplateType.CANNON)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(25)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/fourth_match_flame")
    .buildAndRegister();

  public static final DeferredItem<?> SOLITUDE = onRemote()
    .id("solitude_weapon")
    .zhName("孤独")
    .lcLevelType(LcLevelType.TETH)
    .type(RemoteTemplateType.PISTOL)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(2.5f)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/solitude")
    .buildAndRegister();

  public static final DeferredItem<?> BEAK = onRemote()
    .id("beak_weapon")
    .zhName("小喙")
    .lcLevelType(LcLevelType.TETH)
    .type(RemoteTemplateType.PISTOL)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(2.5f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/beak")
    .buildAndRegister();

  public static final DeferredItem<?> TODAY_IS_EXPRESSION = onRemote()
    .id("today_is_expression_weapon")
    .zhName("此刻的神色")
    .lcLevelType(LcLevelType.TETH)
    .type(RemoteTemplateType.PISTOL)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(2.5f)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/today_is_expression")
    .buildAndRegister();

  // TODO 只有秃顶或是地中海的帅气员工才能使用这件屌爆的装备！
  // 这是什么阴的没边的效果（
  public static final DeferredItem<?> TOUGH = onRemote()
    .id("tough_weapon")
    .zhName("谢顶之灾")
    .lcLevelType(LcLevelType.TETH)
    .type(RemoteTemplateType.PISTOL)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(3)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null)) // TODO 光头（
    .modelPath("tough")
    .buildAndRegister();

  // TODO 这把武器一次攻击动画会造成三次伤害。
  public static final DeferredItem<EngulfingDreamWeaponItem> ENGULFING_DREAM = onRemote()
    .id("engulfing_dream_weapon")
    .zhName("迷魂梦境")
    .lcLevelType(LcLevelType.TETH)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(1.5f)
      .attackIntervalMainHand(minuteToSpeedConversion(1))
      .attackDistance(8)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/engulfing_dream")
    .buildAndRegister((p, b) -> new EngulfingDreamWeaponItem(p, b, "weapon/engulfing_dream"));

  // TODO 这把武器一次攻击动画会造成三次伤害。
  public static final DeferredItem<CherryBlossomsWeaponItem> CHERRY_BLOSSOMS = onRemote()
    .id("cherry_blossoms_weapon")
    .zhName("落樱")
    .lcLevelType(LcLevelType.TETH)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(1.5f)
      .attackIntervalMainHand(minuteToSpeedConversion(1))
      .attackDistance(5)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/cherry_blossoms")
    .buildAndRegister((p, b) -> new CherryBlossomsWeaponItem(p, b, "weapon/cherry_blossoms"));

  // TODO 如果持有者的勇气等级高于2级，进入战斗后会提高2.5点移动速度。
  public static final DeferredItem<?> RED_EYES = onMelee()
    .id("red_eyes_weapon")
    .zhName("赤瞳")
    .lcLevelType(LcLevelType.TETH)
    .type(MeleeTemplateType.MACE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(8)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/red_eyes")
    .buildAndRegister();

  public static final DeferredItem<?> RED_EYES_TACHI = onMelee()
    .id("red_eyes_tachi_weapon")
    .zhName("赤瞳-太刀")
    .lcLevelType(LcLevelType.TETH)
    .type(MeleeTemplateType.SWORDS)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(8)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .buildAndRegister((p, b) -> new RedEyesTachiItem(p, b, "weapon/red_eyes_tachi"));

  public static final DeferredItem<?> HORN = onMelee()
    .id("horn_weapon")
    .zhName("犄角")
    .lcLevelType(LcLevelType.TETH)
    .type(MeleeTemplateType.SPEAR)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(7)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/horn")
    .buildAndRegister();

  public static final DeferredItem<?> WRIST_CUTTER = onMelee()
    .id("wrist_cutter_weapon")
    .zhName("割腕者")
    .lcLevelType(LcLevelType.TETH)
    .type(MeleeTemplateType.KNIFE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(2.5f)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/wrist_cutter")
    .buildAndRegister();

  public static final DeferredItem<?> REGRET = onMelee()
    .id("regret_weapon")
    .zhName("悔恨")
    .lcLevelType(LcLevelType.TETH)
    .type(MeleeTemplateType.HAMMER)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(15)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/regret")
    .buildAndRegister();

  // TODO 如果持有者的谨慎等级低于5级，进入战斗后有10%的概率提升40%的最大精神值。该效果持续30秒。
  public static final DeferredItem<?> FRAGMENTS_FROM_SOMEWHERE = onMelee()
    .id("fragments_from_somewhere_weapon")
    .zhName("彼方的裂片")
    .lcLevelType(LcLevelType.TETH)
    .type(MeleeTemplateType.SPEAR)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(7)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/fragments_from_somewhere")
    .buildAndRegister();

  public static final DeferredItem<?> LANTERN = onMelee()
    .id("lantern_weapon")
    .zhName("诱捕幻灯")
    .lcLevelType(LcLevelType.TETH)
    .type(MeleeTemplateType.HAMMER)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(15)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/lantern")
    .buildAndRegister();

  public static final DeferredItem<?> SO_CUTE = onMelee()
    .id("so_cute_weapon")
    .zhName("超特么可爱！！！")
    .lcLevelType(LcLevelType.TETH)
    .type(MeleeTemplateType.FIST)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(5)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/so_cute")
    .buildAndRegister();

  public static final DeferredItem<?> STANDARD_TRAINING_EGO = onMelee()
    .id("standard_training_ego_weapon")
    .zhName("教学用E.G.O武器")
    .lcLevelType(LcLevelType.TETH)
    .type(MeleeTemplateType.MACE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(6)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/standard_training_ego")
    .buildAndRegister();
  //endregion

  //region HE
  public static final DeferredItem<?> SCREAMING_WEDGE = onRemote()
    .id("screaming_wedge_weapon")
    .zhName("刺耳嚎叫")
    .lcLevelType(LcLevelType.HE)
    .type(RemoteTemplateType.CROSSBOW)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(12)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, VirtueRating.II, null, null, null))
    .modelPath("weapon/screaming_wedge")
    .buildAndRegister();

  // TODO 如果持有者的生命值维持在10%及以上，那么持有者将消耗一定生命值发动更加强大的攻击。（造成30%的额外伤害）
  public static final DeferredItem<?> HARMONY = onRemote()
    .id("harmony_weapon")
    .zhName("谐奏放射器")
    .lcLevelType(LcLevelType.HE)
    .type(RemoteTemplateType.CANNON)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(40)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(VirtueRating.II, null, null, null, VirtueRating.II))
    .modelPath("weapon/harmony")
    .buildAndRegister();

  public static final DeferredItem<?> LAETITIA = onRemote()
    .id("laetitia_weapon")
    .zhName("蕾蒂希娅")
    .lcLevelType(LcLevelType.HE)
    .type(RemoteTemplateType.RIFLE)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(5.5f)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.II, null, null))
    .modelPath("weapon/laetitia")
    .buildAndRegister();

  public static final DeferredItem<SyrinxWeaponItem> SYRINX = onRemote()
    .id("syrinx_weapon")
    .zhName("泣婴")
    .lcLevelType(LcLevelType.HE)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(3)
      .attackIntervalMainHand(minuteToSpeedConversion(0.5f))
      .attackDistance(10)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, VirtueRating.III, null, null))
    .modelPath("weapon/syrinx")
    .buildAndRegister((p, b) -> new SyrinxWeaponItem(p, b, "weapon/syrinx"));

  // TODO 这把武器一次攻击会造成6次伤害。
  public static final DeferredItem<GrinderMk4WeaponItem> GRINDER_MK4 = onMelee()
    .id("grinder_mk4_weapon")
    .zhName("粉碎机Mk4")
    .lcLevelType(LcLevelType.HE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(1.5f)
      .attackSpeed(minuteToSpeedConversion(1.67f))
      .attackDistance(4)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.II, null, null, null, null))
    .modelPath("weapon/grinder_mk4")
    .buildAndRegister((p, b) -> new GrinderMk4WeaponItem(p, b, "weapon/grinder_mk4"));

  public static final DeferredItem<OurGalaxyWeaponItem> OUR_GALAXY = onMelee()
    .id("our_galaxy_weapon")
    .zhName("小小银河")
    .lcLevelType(LcLevelType.HE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(11)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(8)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, VirtueRating.II, null, null, VirtueRating.II))
    .modelPath("weapon/our_galaxy")
    .buildAndRegister((p, b) -> new OurGalaxyWeaponItem(p, b, "weapon/our_galaxy"));

  public static final DeferredItem<LifeForADaredevilWeaponItem> LIFE_FOR_A_DAREDEVIL = onMelee()
    .id("life_for_a_daredevil_weapon")
    .zhName("决死之心")
    .lcLevelType(LcLevelType.HE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(8)
      .attackSpeed(minuteToSpeedConversion(1.33f))
      .attackDistance(4)
      .meleeLcDamageType(LcDamageType.THE_SOUL)
      .virtueUsageReq(null, null, null, VirtueRating.II, null))
    .modelPath("weapon/life_for_a_daredevil")
    .buildAndRegister((p, b) -> new LifeForADaredevilWeaponItem(p, b, "weapon/life_for_a_daredevil"));

  // TODO 这把武器每次攻击会造成9次伤害。
  // TODO 在攻击到敌人时，为敌人附加一个每秒受到2点物理伤害的dot，持续5秒，不可叠加。
  public static final DeferredItem<GazeWeaponItem> GAZE = onMelee()
    .id("gaze_weapon")
    .zhName("凝视")
    .lcLevelType(LcLevelType.HE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(3)
      .attackSpeed(minuteToSpeedConversion(2.9f))
      .attackDistance(4)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, VirtueRating.II, VirtueRating.II, null, null))
    .modelPath("weapon/gaze")
    .buildAndRegister((p, b) -> new GazeWeaponItem(p, b, "weapon/gaze"));

  // TODO 这把武器击中目标时，会给目标附加每秒受到2点精神伤害的dot，持续5秒，不可叠加。
  public static final DeferredItem<PleasureWeaponItem> PLEASURE = onMelee()
    .id("pleasure_weapon")
    .zhName("因乐癫狂")
    .lcLevelType(LcLevelType.HE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(2.5f)
      .attackSpeed(minuteToSpeedConversion(1.67f))
      .attackDistance(5)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.III, null, null))
    .modelPath("weapon/pleasure")
    .buildAndRegister((p, b) -> new PleasureWeaponItem(p, b, "weapon/pleasure"));

  public static final DeferredItem<?> BEAR_PAWS = onMelee()
    .id("bear_paws_weapon")
    .zhName("熊熊抱")
    .lcLevelType(LcLevelType.HE)
    .type(MeleeTemplateType.FIST)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(7)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.II, null, null, null, null))
    .modelPath("weapon/bear_paws")
    .buildAndRegister();

  // TODO 如果持有者的自律等级低于3级，每次攻击都会提高最大与最小攻击力。但代价是每次攻击都会丧失等同于最大精神值4%的精神值。
  public static final DeferredItem<?> SANGUINE_DESIRE = onMelee()
    .id("sanguine_desire_weapon")
    .zhName("血之渴望")
    .lcLevelType(LcLevelType.HE)
    .type(MeleeTemplateType.AXE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(6)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/sanguine_desire")
    .buildAndRegister();

  public static final DeferredItem<?> LOGGING = onMelee()
    .id("logging_weapon")
    .zhName("伐木者")
    .lcLevelType(LcLevelType.HE)
    .type(MeleeTemplateType.HAMMER)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(17)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, VirtueRating.II, null, null))
    .modelPath("weapon/logging")
    .buildAndRegister();

  // TODO 被这支武器刺中的目标会减少30%的移动速度，持续3秒。需要单独效果。
  public static final DeferredItem<?> FROST_SPLINTER = onMelee()
    .id("frost_splinter_weapon")
    .zhName("霜之碎片")
    .lcLevelType(LcLevelType.HE)
    .type(MeleeTemplateType.SPEAR)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(7)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/frost_splinter")
    .buildAndRegister();

  public static final DeferredItem<?> CHRISTMAS = onMelee()
    .id("christmas_weapon")
    .zhName("悲惨圣诞")
    .lcLevelType(LcLevelType.HE)
    .type(MeleeTemplateType.MACE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(12)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/christmas")
    .buildAndRegister();

  public static final DeferredItem<?> HARVEST = onMelee()
    .id("harvest_weapon")
    .zhName("猎头长耙")
    .lcLevelType(LcLevelType.HE)
    .type(MeleeTemplateType.SPEAR)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(8)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, null, null))
    .modelPath("weapon/harvest")
    .buildAndRegister();
  //endregion

  //region WAW
  public static final DeferredItem<?> HORNET = onRemote()
    .id("hornet_weapon")
    .zhName("黄蜂")
    .lcLevelType(LcLevelType.WAW)
    .type(RemoteTemplateType.RIFLE)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(7.5f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.III, null, null, null, null))
    .modelPath("weapon/hornet")
    .buildAndRegister();

  public static final DeferredItem<?> FAINT_AROMA = onRemote()
    .id("faint_aroma_weapon")
    .zhName("余香")
    .lcLevelType(LcLevelType.WAW)
    .type(RemoteTemplateType.CROSSBOW)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(15)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, VirtueRating.III, null, null, null))
    .modelPath("weapon/faint_aroma")
    .buildAndRegister();

  // TODO 攻击时有25%的概率给目标添加一个易伤效果，使其受到的物理伤害加深。
  public static final DeferredItem<?> EXUVIAE = onRemote()
    .id("exuviae_weapon")
    .zhName("脱落之皮")
    .lcLevelType(LcLevelType.WAW)
    .type(RemoteTemplateType.CANNON)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(42.5f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, VirtueRating.IV))
    .modelPath("weapon/exuviae")
    .buildAndRegister();

  public static final DeferredItem<?> HYPOCRISY = onRemote()
    .id("hypocrisy_weapon")
    .zhName("伪善")
    .lcLevelType(LcLevelType.WAW)
    .type(RemoteTemplateType.CROSSBOW)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(15)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, VirtueRating.III, null, null, null))
    .modelPath("weapon/hypocrisy")
    .buildAndRegister();

  // TODO 这件E.G.O在攻击时会造成随机类型的伤害。这件E.G.O命中员工时，会根据伤害类型为员工恢复生命值和精神值。
  public static final DeferredItem<InTheNameOfLoveAndHateWeaponItem> IN_THE_NAME_OF_LOVE_AND_HATE = onRemote()
    .id("in_the_name_of_love_and_hate_weapon")
    .zhName("以爱与恨之名")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(6.5f)
      .attackIntervalMainHand(minuteToSpeedConversion(0.83f))
      .attackDistance(80)
      .meleeLcDamageType(null, LcDamageType.PHYSICS, LcDamageType.SPIRIT, LcDamageType.EROSION, LcDamageType.THE_SOUL)
      .virtueUsageReq(VirtueRating.III, null, null, VirtueRating.III, VirtueRating.IV))
    .modelPath("weapon/in_the_name_of_love_and_hate")
    .buildAndRegister((p, b) -> new InTheNameOfLoveAndHateWeaponItem(p, b, "weapon/in_the_name_of_love_and_hate"));

  // TODO 如果持有者的生命值低于50%，武器的伤害会额外增加50%。但是，持有者在该状态下攻击时会对其他员工造成无差别伤害。
  public static final DeferredItem<CrimsonScarWeaponItem> CRIMSON_SCAR = onMelee()
    .id("crimson_scar_weapon")
    .zhName("猩红创痕")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(12)
      .attackSpeed(minuteToSpeedConversion(1.53f))
      .attackDistance(-12) // 3 - 15 = -12
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.III, null, null, VirtueRating.III, VirtueRating.III))
    .modelPath("weapon/crimson_scar")
    .buildAndRegister((p, b) -> new CrimsonScarWeaponItem(p, b, "weapon/crimson_scar"));

  // TODO 持有者攻击时会在一定时间内增加物理伤害。如果持有者的生命值小于或等于50%，武器的伤害会额外增加50%。但是，持有者在该状态下攻击时会对其他员工造成无差别伤害。
  public static final DeferredItem<CobaltScarWeaponItem> COBALT_SCAR = onMelee()
    .id("cobalt_scar_weapon")
    .zhName("郁蓝创痕")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(15)
      .attackSpeed(minuteToSpeedConversion(1.83f))
      .attackDistance(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.III, null, VirtueRating.III, null, VirtueRating.II))
    .modelPath("weapon/cobalt_scar")
    .buildAndRegister((p, b) -> new CobaltScarWeaponItem(p, b, "weapon/cobalt_scar"));

  public static final DeferredItem<SolemnLamentWeaponItem> SOLEMN_LAMENT_BLACK = onRemote()
    .id("solemn_lament_black_weapon")
    .zhName("圣宣-黑")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(2)
      .attackIntervalHand(minuteToSpeedConversion(0.5f))
      .attackDistance(10)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, null, VirtueRating.III, null))
    .modelPath("weapon/solemn_lament_black")
    .buildAndRegister((p, b) -> new SolemnLamentWeaponItem(p, b, "weapon/solemn_lament_black"));

  public static final DeferredItem<SolemnLamentWeaponItem> SOLEMN_LAMENT_WHITE = onRemote()
    .id("solemn_lament_white_weapon")
    .zhName("圣宣-白")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(2)
      .attackIntervalHand(minuteToSpeedConversion(0.5f))
      .attackDistance(10)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, VirtueRating.III, null))
    .modelPath("weapon/solemn_lament_white")
    .buildAndRegister((p, b) -> new SolemnLamentWeaponItem(p, b, "weapon/solemn_lament_white"));

  public static final DeferredItem<MagicBulletWeaponItem> MAGIC_BULLET = onRemote()
    .id("magic_bullet_weapon")
    .zhName("魔弹")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(21)
      .attackIntervalMainHand(minuteToSpeedConversion(2.33f))
      .attackDistance(50)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.III, null, null))
    .modelPath("weapon/magic_bullet")
    .buildAndRegister((p, b) -> new MagicBulletWeaponItem(p, b, "weapon/magic_bullet"));

  // TODO 这把武器一次攻击动画会造成2次侵蚀伤害。
  // TODO 持有者进入战斗后，有一定概率反弹自身受到的伤害。
  public static final DeferredItem<BlackSwanWeaponItem> BLACK_SWAN = onMelee()
    .id("black_swan_weapon")
    .zhName("黑天鹅")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(6)
      .attackSpeed(minuteToSpeedConversion(1.63f))
      .attackDistance(0)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, VirtueRating.III, null, null, null))
    .modelPath("weapon/black_swan")
    .buildAndRegister((p, b) -> new BlackSwanWeaponItem(p, b, "weapon/black_swan"));

  public static final DeferredItem<EcstasyWeaponItem> ECSTASY = onMelee()
    .id("ecstasy_weapon")
    .zhName("沉醉")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(3)
      .attackSpeed(minuteToSpeedConversion(0.83f))
      .attackDistance(10)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(VirtueRating.II, null, null, null, VirtueRating.II))
    .modelPath("weapon/ecstasy")
    .buildAndRegister((p, b) -> new EcstasyWeaponItem(p, b, "weapon/ecstasy"));

  // TODO 这把武器攻击时有30%的概率使用特殊攻击，持有者先举起刺剑，然后对目标进行快速戳刺，造成9次1-2点精神伤害和1次9-12点精神伤害。
  public static final DeferredItem<TheSwordSharpenedWithTearsWeaponItem> THE_SWORD_SHARPENED_WITH_TEARS = onMelee()
    .id("the_sword_sharpened_with_tears_weapon")
    .zhName("盈泪之剑")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(10.5f)
      .attackSpeed(minuteToSpeedConversion(1.33f))
      .attackDistance(4)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, null, null, VirtueRating.IV))
    .modelPath("weapon/the_sword_sharpened_with_tears")
    .buildAndRegister((p, b) -> new TheSwordSharpenedWithTearsWeaponItem(p, b, "weapon/the_sword_sharpened_with_tears"));

  public static final DeferredItem<FeatherOfHonorWeaponItem> FEATHER_OF_HONOR = onRemote()
    .id("feather_of_honor_weapon")
    .zhName("荣耀之羽")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(4.5f)
      .attackIntervalMainHand(minuteToSpeedConversion(0.5f))
      .attackDistance(15)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(VirtueRating.III, null, null, null, null))
    .modelPath("weapon/feather_of_honor")
    .buildAndRegister((p, b) -> new FeatherOfHonorWeaponItem(p, b, "weapon/feather_of_honor"));

  // TODO 这把武器在攻击时有10%的概率使用特殊攻击，具体效果为：持有者对目标进行4次快速戳刺和一次劈砍，造成4次2.5点侵蚀伤害和一次9-12点侵蚀伤害。
  public static final DeferredItem<DiscordWeaponItem> DISCORD = onMelee()
    .id("discord_weapon")
    .zhName("不和")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(9)
      .attackSpeed(minuteToSpeedConversion(1.33f))
      .attackDistance(5)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.III, null, VirtueRating.III))
    .modelPath("weapon/discord")
    .buildAndRegister((p, b) -> new DiscordWeaponItem(p, b, "weapon/discord"));

  // TODO 这把武器在攻击时有15%的概率使用特殊攻击，持有者对目标进行一次快速上挑和下砸然后以拐杖点地，造成2次5.5点侵蚀伤害和10点侵蚀伤害。
  // TODO 这把武器发动特殊攻击时，能够为同房间的所有职员附加一层和中央本部科技完全相同的反侵蚀力场盾。这层反侵蚀力场盾不会影响其余类型护盾的效果并可以和它们共存，若已经存在附加的反侵蚀力场盾，那么该护盾的承伤能力将恢复为最大值。
  public static final DeferredItem<MoonlightWeaponItem> MOONLIGHT = onMelee()
    .id("moonlight_weapon")
    .zhName("月光")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(9)
      .attackSpeed(minuteToSpeedConversion(1.17f))
      .attackDistance(0)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(VirtueRating.III, null, null, null, VirtueRating.IV))
    .modelPath("weapon/moonlight")
    .buildAndRegister((p, b) -> new MoonlightWeaponItem(p, b, "weapon/moonlight"));

  // TODO 这把武器每次攻击时会造成3次物理伤害。
  public static final DeferredItem<AmitaWeaponItem> AMITA = onMelee()
    .id("amita_weapon")
    .zhName("无量")
    .lcLevelType(LcLevelType.WAW)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(3.5f)
      .attackSpeed(minuteToSpeedConversion(2.33f))
      .attackDistance(4)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, VirtueRating.III, null, VirtueRating.III))
    .modelPath("weapon/amita")
    .buildAndRegister((p, b) -> new AmitaWeaponItem(p, b, "weapon/amita"));

  public static final DeferredItem<?> LAMP = onMelee()
    .id("lamp_weapon")
    .zhName("目灯")
    .lcLevelType(LcLevelType.WAW)
    .type(MeleeTemplateType.HAMMER)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(24)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(VirtueRating.III, VirtueRating.III, null, null, null))
    .modelPath("weapon/lamp")
    .buildAndRegister();

  public static final DeferredItem<?> GREEN_STEM = onMelee()
    .id("green_stem_weapon")
    .zhName("绿色枝干")
    .lcLevelType(LcLevelType.WAW)
    .type(MeleeTemplateType.SPEAR)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(12)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.III, null, null))
    .modelPath("weapon/green_stem")
    .buildAndRegister();

  // TODO 持有者攻击时有25%的概率给目标添加一个易伤效果，使其受到的精神伤害加深。
  public static final DeferredItem<?> SPORE = onMelee()
    .id("spore_weapon")
    .zhName("荧光菌孢")
    .lcLevelType(LcLevelType.WAW)
    .type(MeleeTemplateType.SPEAR)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(12)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, null, VirtueRating.II, null, VirtueRating.II))
    .modelPath("weapon/spore")
    .buildAndRegister();

  public static final DeferredItem<?> HEAVEN = onMelee()
    .id("heaven_weapon")
    .zhName("穿刺极乐")
    .lcLevelType(LcLevelType.WAW)
    .type(MeleeTemplateType.SPEAR)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(10)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, null, null, VirtueRating.III))
    .modelPath("weapon/heaven")
    .buildAndRegister();

  public static final DeferredItem<?> DIFFRACTION = onMelee()
    .id("diffraction_weapon")
    .zhName("虚无衍射体")
    .lcLevelType(LcLevelType.WAW)
    .type(MeleeTemplateType.MACE)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(16)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, VirtueRating.III, null, null, null))
    .modelPath("weapon/diffraction")
    .buildAndRegister();
  //endregion

  //region ALEPH
  // TODO 造成伤害的25%会转化成生命值。
  public static final DeferredItem<MimicryWeaponItem> MIMICRY = onMelee()
    .id("mimicry_weapon")
    .zhName("拟态")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(12)
      .attackSpeed(minuteToSpeedConversion(1.7f))
      .attackDistance(4)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.V, null, null, null, VirtueRating.V))
    .modelPath("weapon/mimicry")
    .buildAndRegister((p, b) -> new MimicryWeaponItem(p, b, "weapon/mimicry"));

  // TODO 这把武器每次攻击时会造成5次灵魂伤害。
  public static final DeferredItem<JustitiaWeaponItem> JUSTITIA = onMelee()
    .id("justitia_weapon")
    .zhName("正义裁决者")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(3)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(4)
      .meleeLcDamageType(LcDamageType.THE_SOUL)
      .virtueUsageReq(null, null, null, VirtueRating.V, VirtueRating.V))
    .modelPath("weapon/justitia")
    .buildAndRegister((p, b) -> new JustitiaWeaponItem(p, b, "weapon/justitia"));

  // TODO 持有者无法通过各部门的"再生反应堆"恢复生命值和精神值。
  // TODO 发起普通攻击时，这把武器能减少被击中单位的移动速度，同时恢复持有者的生命值和精神值。(伤害量和恢复量取决于击中单位的数量。)
  // TODO 发起特殊攻击时，这把武器会生成一个力场盾，减少受到的所有类型的伤害。(如果设施内没有收容"白夜"，就不能发起特殊攻击。)
  public static final DeferredItem<ParadiseLostWeaponItem> PARADISE_LOST = onRemote()
    .id("paradise_lost_weapon")
    .zhName("失乐园")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(24)
      .attackIntervalMainHand(minuteToSpeedConversion(2))
      .attackDistance(80)
      .meleeLcDamageType(LcDamageType.THE_SOUL)
      .virtueUsageReq(VirtueRating.V, VirtueRating.V, VirtueRating.V, VirtueRating.V, null))
    .modelPath("weapon/paradise_lost")
    .buildAndRegister((p, b) -> new ParadiseLostWeaponItem(p, b, "weapon/paradise_lost"));

  // TODO 只有全属性超过110的员工才能拿起这把武器。
  // TODO 这把武器会同时造成物理，精神，侵蚀和灵魂伤害。
  // TODO 这把武器每隔一段时间会进行一次特殊攻击。
  public static final DeferredItem<TwilightWeaponItem> TWILIGHT = onMelee()
    .id("twilight_weapon")
    .zhName("薄暝")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(14)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(6)
      .meleeLcDamageType(null,
        LcDamageType.PHYSICS,
        LcDamageType.SPIRIT,
        LcDamageType.EROSION,
        LcDamageType.THE_SOUL)
      .virtueUsageReq(111, 111, 111, 111, 0))
    .modelPath("weapon/twilight")
    .buildAndRegister((p, b) -> new TwilightWeaponItem(p, b, "weapon/twilight"));

  // TODO 持有者每次攻击时都有10%的概提高5点最大与最小攻击力，该效果持续12秒。代价是，120秒内，持有者的自律相关属性会降低50%。
  // TODO 这把武器每次攻击时会造成3次物理伤害。
  public static final DeferredItem<GoldRushWeaponItem> GOLD_RUSH = onMelee()
    .id("gold_rush_weapon")
    .zhName("闪金冲锋")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(5.5f)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(2)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(VirtueRating.V, null, null, null, VirtueRating.V))
    .modelPath("weapon/gold_rush")
    .buildAndRegister((p, b) -> new GoldRushWeaponItem(p, b, "weapon/gold_rush"));

  // TODO 每击杀一个目标，持有者的勇气与正义的相关属性会提高3点。该效果仅在当天有效。
  // TODO 发起普通攻击时，这把武器能减少被击中单位的移动速度。
  // TODO 发起特殊攻击时，这把武器能减少当前区域中所有敌对单位的移动速度。
  // TODO 这把武器在攻击时有30%的概率使用特殊攻击，持有者举起武器，跳起后重砸，对小范围内的目标造成1次35-45点侵蚀伤害和5次4点侵蚀伤害。
  public static final DeferredItem<SmileWeaponItem> SMILE = onMelee()
    .id("smile_weapon")
    .zhName("笑靥")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(13)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(5)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(null, null, VirtueRating.V, null, VirtueRating.V))
    .modelPath("weapon/smile")
    .buildAndRegister((p, b) -> new SmileWeaponItem(p, b, "weapon/smile"));

  // TODO 每次攻击造成2次伤害
  // TODO 持有这「CENSORED」的员工受到伤害时，「CENSORED」会为其恢复相当于伤害数值40%的生命
  // TODO 这把武器的持有者在受到伤害时，会立刻得到和伤害类型相对应，且等同于伤害量40%的治疗效果。但若持有者在受到伤害后死亡，则无法得到对应的回复。(任何类型的伤害都会计入，包括恐惧伤害)
  public static final DeferredItem<CensoredWeaponItem> CENSORED = onMelee()
    .id("censored_weapon")
    .zhName("CENSORED")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .properties(b -> b
      .damage(9)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(0)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(VirtueRating.V, null, null, null, VirtueRating.V))
    .modelPath("weapon/censored")
    .buildAndRegister((p, b) -> new CensoredWeaponItem(p, b, "weapon/censored"));

  // TODO 这把武器在攻击时会造成贯穿伤害，但该武器的伤害不会伤害到中立目标和友方目标。
  // TODO 这把武器的伤害和攻击次数与员工的最大精神值数值无关，仅与员工当前精神值占最大精神值的百分比有关。
  // TODO 持有者的精神值在低于最大精神值的30%时，武器产生的"新星之声"仅有一个，伤害为和面板相同的8-12点精神伤害。
  // TODO 持有者的精神值大于最大精神值的30%且小于60%时，每次攻击时武器产生的"新星之声"变为2个，增加的"新星之声"伤害为9-11点精神伤害
  // TODO 持有者的精神值大于最大精神值的60%时，每次攻击时武器产生的"新星之声"变为3个，再额外增加的"新星之声"伤害为11-16点精神伤害
  public static final DeferredItem<SoundOfAStarWeaponItem> SOUND_OF_A_STAR = onRemote()
    .id("sound_of_a_star_weapon")
    .zhName("新星之声")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(9)
      .attackIntervalMainHand(minuteToSpeedConversion(1.67f))
      .attackDistance(25)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(null, VirtueRating.V, VirtueRating.V, null, VirtueRating.V))
    .modelPath("weapon/sound_of_a_star")
    .buildAndRegister((p, b) -> new SoundOfAStarWeaponItem(p, b, "weapon/sound_of_a_star"));

  // TODO 这把武器每次攻击之前需要花费等同于一次攻击间隔的时间趴下来准备攻击；若目标始终处于射程范围内且保持与武器持有者相对方向相同，则不需要再次准备。
  public static final DeferredItem<PinkWeaponItem> PINK = onRemote()
    .id("pink_weapon")
    .zhName("粉红军备")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(22)
      .attackIntervalMainHand(minuteToSpeedConversion(2))
      .attackDistance(35)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, VirtueRating.V, null, null, VirtueRating.V))
    .modelPath("weapon/pink")
    .buildAndRegister((p, b) -> new PinkWeaponItem(p, b, "weapon/pink"));

  // TODO 这把武器的子弹命中时，会给目标附加每秒受到 2 点侵蚀伤害的dot，持续 5 秒，不可叠加。
  // TODO 这把武器的子弹命中时，会使目标的移动速度在 5 秒内降低 30%。
  public static final DeferredItem<AdorationWeaponItem> ADORATION = onRemote()
    .id("adoration_weapon")
    .zhName("爱慕")
    .lcLevelType(LcLevelType.ALEPH)
    .properties(new Item.Properties())
    .weaponBuilder(b -> b
      .damage(33)
      .attackIntervalMainHand(minuteToSpeedConversion(3.47f))
      .attackDistance(15)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(null, null, VirtueRating.V, null, VirtueRating.V))
    .modelPath("weapon/adoration")
    .buildAndRegister((p, b) -> new AdorationWeaponItem(p, b, "weapon/adoration"));
  //endregion

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }
}
