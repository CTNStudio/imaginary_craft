package ctn.imaginarycraft.init.item.ego.weapon;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueRating;
import ctn.imaginarycraft.client.model.ModGeoItemModel;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.GeoEgoWeaponItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static ctn.imaginarycraft.init.item.ego.weapon.EgoWeaponItems.minuteToSpeedConversion;

// TODO 远程类不应该使用attackDistance增加攻击距离
// TODO dot：在计时器正常运作的前提下，每1秒受到x点指定种类的无来源伤害。该伤害不受等级压制影响。
public final class EgoSpecialWeaponItems {
  //region TETH

  // TODO 这把武器一次攻击动画会造成三次伤害。
  public static final DeferredItem<GeoEgoWeaponItem> ENGULFING_DREAM = register(
    "engulfing_dream_weapon", "迷魂梦境",
    LcLevelType.TETH, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(3, 1.5f)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(1))
      .attackDistance(0, 8 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("engulfing_dream")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器一次攻击动画会造成三次伤害。
  public static final DeferredItem<GeoEgoWeaponItem> CHERRY_BLOSSOMS = register(
    "cherry_blossoms_weapon", "落樱",
    LcLevelType.TETH, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(3, 1.5f)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(1))
      .attackDistance(0, 5 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("cherry_blossoms")),
    GeoEgoWeaponItem::new);
  //endregion

  //region HE

  public static final DeferredItem<GeoEgoWeaponItem> SYRINX = register(
    "syrinx_weapon", "泣婴",
    LcLevelType.HE, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(4, 3)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(0.5f))
      .attackDistance(0, 10 - 3)
      .invincibleTick(20, 10)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.III,
        null,
        null)
      .model(new ModGeoItemModel<>("syrinx")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器一次攻击会造成6次伤害。
  public static final DeferredItem<GeoEgoWeaponItem> GRINDER_MK4 = register(
    "grinder_mk4_weapon", "粉碎机Mk4",
    LcLevelType.HE, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(1.5f)
      .attackSpeed(minuteToSpeedConversion(1.67f))
      .attackDistance(4 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        VirtueRating.II,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("grinder_mk4")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> OUR_GALAXY = register(
    "our_galaxy", "小小银河",
    LcLevelType.HE, Type.SpecialTemplateType.MELEE,// TODO 特殊 棁
    new GeoEgoWeaponItem.Builder()
      .damage(11)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(8 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        VirtueRating.II,
        null,
        null,
        VirtueRating.II)
      .model(new ModGeoItemModel<>("our_galaxy")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> LIFE_FOR_A_DAREDEVIL = register(
    "life_for_a_daredevil_weapon", "决死之心",
    LcLevelType.HE, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(8)
      .attackSpeed(minuteToSpeedConversion(1.33f))
      .attackDistance(4 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.THE_SOUL)
      .virtueUsageReq(
        null,
        null,
        null,
        VirtueRating.II,
        null)
      .model(new ModGeoItemModel<>("life_for_a_daredevil")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器每次攻击会造成9次伤害。
  // TODO 在攻击到敌人时，为敌人附加一个每秒受到2点物理伤害的dot，持续5秒，不可叠加。
  public static final DeferredItem<GeoEgoWeaponItem> GAZE = register(
    "gaze_weapon", "凝视",
    LcLevelType.HE, Type.SpecialTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(3)
      .attackSpeed(minuteToSpeedConversion(2.9f))
      .attackDistance(4 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        VirtueRating.II,
        VirtueRating.II,
        null,
        null)
      .model(new ModGeoItemModel<>("gaze")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器击中目标时，会给目标附加每秒受到2点精神伤害的dot，持续5秒，不可叠加。
  public static final DeferredItem<GeoEgoWeaponItem> PLEASURE = register(
    "pleasure_weapon", "因乐癫狂",
    LcLevelType.HE, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(2.5f)
      .attackSpeed(minuteToSpeedConversion(1.67f))
      .attackDistance(5 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.III,
        null,
        null)
      .model(new ModGeoItemModel<>("pleasure")),
    GeoEgoWeaponItem::new);
  //endregion

  //region WAW

  // TODO 这件E.G.O在攻击时会造成随机类型的伤害。这件E.G.O命中员工时，会根据伤害类型为员工恢复生命值和精神值。
  public static final DeferredItem<GeoEgoWeaponItem> IN_THE_NAME_OF_LOVE_AND_HATE = register(
    "in_the_name_of_love_and_hate_weapon", "以爱与恨之名",
    LcLevelType.WAW, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(5, 6.5f)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(0.83f))
      .attackDistance(0, 80 - 3)
      .invincibleTick(20, (int) (20 * 0.83f))
      .meleeLcDamageType(null,
        LcDamageType.PHYSICS,
        LcDamageType.SPIRIT,
        LcDamageType.EROSION,
        LcDamageType.THE_SOUL)
      .virtueUsageReq(
        VirtueRating.III,
        null,
        null,
        VirtueRating.III,
        VirtueRating.IV)
      .model(new ModGeoItemModel<>("in_the_name_of_love_and_hate")),
    GeoEgoWeaponItem::new);

  // TODO 如果持有者的生命值低于50%，武器的伤害会额外增加50%。但是，持有者在该状态下攻击时会对其他员工造成无差别伤害。
  public static final DeferredItem<GeoEgoWeaponItem> CRIMSON_SCAR = register(
    "crimson_scar_weapon", "猩红创痕",
    LcLevelType.WAW, Type.SpecialTemplateType.MELEE, // 有远程攻击手段但主要近战
    new GeoEgoWeaponItem.Builder()
      .damage(12)
      .attackSpeed(minuteToSpeedConversion(1.53f))
      .attackDistance(0, 3 - 15)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        VirtueRating.III,
        null,
        null,
        VirtueRating.III,
        VirtueRating.III)
      .model(new ModGeoItemModel<>("crimson_scar")),
    GeoEgoWeaponItem::new);

  // TODO 持有者攻击时会在一定时间内增加物理伤害。如果持有者的生命值小于或等于50%，武器的伤害会额外增加50%。但是，持有者在该状态下攻击时会对其他员工造成无差别伤害。
  public static final DeferredItem<GeoEgoWeaponItem> COBALT_SCAR = register(
    "cobalt_scar_weapon", "郁蓝创痕",
    LcLevelType.WAW, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(15)
      .attackSpeed(minuteToSpeedConversion(1.83f))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        VirtueRating.III,
        null,
        VirtueRating.III,
        null,
        VirtueRating.II)
      .model(new ModGeoItemModel<>("cobalt_scar")),
    GeoEgoWeaponItem::new);

  // TODO 该物品需要分成黑枪和白枪俩个物品
  // TODO 和面板不同，这把武器攻击时也会造成2-2的侵蚀伤害，伤害造成顺序略晚于精神伤害。
  public static final DeferredItem<GeoEgoWeaponItem> SOLEMN_LAMENT = register(
    "solemn_lament_weapon", "圣宣",
    LcLevelType.WAW, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(5, 2)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(0.5f))
      .attackDistance(0, 10 - 3)
      .invincibleTick(20, (int) (20 * 0.5f))
      .meleeLcDamageType(null,
        LcDamageType.PHYSICS,
        LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        null,
        null,
        VirtueRating.III,
        null)
      .model(new ModGeoItemModel<>("solemn_lament")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> MAGIC_BULLET = register(
    "magic_bullet_weapon", "魔弹",
    LcLevelType.WAW, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(5, 21)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(2.33f))
      .attackDistance(0, 50 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.III,
        null,
        null)
      .model(new ModGeoItemModel<>("magic_bullet")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器一次攻击动画会造成2次侵蚀伤害。
  // TODO 持有者进入战斗后，有一定概率反弹自身受到的伤害。
  public static final DeferredItem<GeoEgoWeaponItem> BLACK_SWAN = register(
    "black_swan_weapon", "黑天鹅",
    LcLevelType.WAW, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(6)
      .attackSpeed(minuteToSpeedConversion(1.63f))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        VirtueRating.III,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("black_swan")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> ECSTASY = register(
    "ecstasy_weapon", "沉醉",
    LcLevelType.WAW, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .damage(5, 3)
      .attackSpeed(minuteToSpeedConversion(0.83f))
      .attackDistance(0, 10 - 3)
      .invincibleTick(20, (int) (20 * 0.83f))
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        VirtueRating.II,
        null,
        null,
        null,
        VirtueRating.II)
      .model(new ModGeoItemModel<>("ecstasy")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器攻击时有30%的概率使用特殊攻击，持有者先举起刺剑，然后对目标进行快速戳刺，造成9次1-2点精神伤害和1次9-12点精神伤害。
  public static final DeferredItem<GeoEgoWeaponItem> THE_SWORD_SHARPENED_WITH_TEARS = register(
    "the_sword_sharpened_with_tears_weapon", "盈泪之剑",
    LcLevelType.WAW, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(10.5f)
      .attackSpeed(minuteToSpeedConversion(1.33f))
      .attackDistance(4 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        VirtueRating.IV)
      .model(new ModGeoItemModel<>("the_sword_sharpened_with_tears")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> FEATHER_OF_HONOR = register(
    "feather_of_honor_weapon", "荣耀之羽",
    LcLevelType.WAW, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(3, 4.5f)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(0.5f))
      .attackDistance(0, 15 - 3)
      .invincibleTick(20, (int) (20 * 0.5f))
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        VirtueRating.III,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("feather_of_honor")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器在攻击时有10%的概率使用特殊攻击，具体效果为：持有者对目标进行4次快速戳刺和一次劈砍，造成4次2.5点侵蚀伤害和一次20点侵蚀伤害。
  public static final DeferredItem<GeoEgoWeaponItem> DISCORD = register(
    "discord_weapon", "不和",
    LcLevelType.WAW, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(9)
      .attackSpeed(minuteToSpeedConversion(1.33f))
      .attackDistance(5 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.III,
        null,
        VirtueRating.III)
      .model(new ModGeoItemModel<>("discord")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器攻击时有15%的概率使用特殊攻击，持有者对目标进行一次快速上挑和下砸然后以拐杖点地，造成2次5.5点侵蚀伤害和10点侵蚀伤害。
  // TODO 这把武器发动特殊攻击时，能够为同房间的所有职员附加一层和中央本部科技完全相同的反侵蚀力场盾。这层反侵蚀力场盾不会影响其余类型护盾的效果并可以和它们共存，若已经存在附加的反侵蚀力场盾，那么该护盾的承伤能力将恢复为最大值。
  public static final DeferredItem<GeoEgoWeaponItem> MOONLIGHT = register(
    "moonlight_weapon", "月光",
    LcLevelType.WAW, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(9)
      .attackSpeed(minuteToSpeedConversion(1.17f))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        VirtueRating.III,
        null,
        null,
        null,
        VirtueRating.IV)
      .model(new ModGeoItemModel<>("moonlight")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器每次攻击时会造成3次物理伤害。
  public static final DeferredItem<GeoEgoWeaponItem> AMITA = register(
    "amita_weapon", "无量",
    LcLevelType.WAW, Type.SpecialTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(3.5f)
      .attackSpeed(minuteToSpeedConversion(2.33f))
      .attackDistance(4 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.III,
        null,
        VirtueRating.III)
      .model(new ModGeoItemModel<>("amita")),
    GeoEgoWeaponItem::new);
  //endregion

  //region ALEPH

  // TODO 造成伤害的25%会转化成生命值。
  public static final DeferredItem<GeoEgoWeaponItem> MIMICRY = register(
    "mimicry_weapon", "拟态",
    LcLevelType.ALEPH, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(12)
      .attackSpeed(minuteToSpeedConversion(1.7f))
      .attackDistance(4 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        VirtueRating.V,
        null,
        null,
        null,
        VirtueRating.V)
      .model(new ModGeoItemModel<>("mimicry")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器每次攻击时会造成5次精神伤害。
  public static final DeferredItem<GeoEgoWeaponItem> DA_CAPO = register(
    "da_capo_weapon", "Da Capo",
    LcLevelType.ALEPH, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(4.5f)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(4 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        VirtueRating.V,
        null,
        null,
        VirtueRating.V)
      .model(new ModGeoItemModel<>("da_capo")),
    GeoEgoWeaponItem::new);

  // TODO 持有者无法通过各部门的"再生反应堆"恢复生命值和精神值。
  // TODO 发起普通攻击时，这把武器能减少被击中单位的移动速度，同时恢复持有者的生命值和精神值。(伤害量和恢复量取决于击中单位的数量。)
  // TODO 发起特殊攻击时，这把武器会生成一个力场盾，减少受到的所有类型的伤害。(如果设施内没有收容"白夜"，就不能发起特殊攻击。)
  public static final DeferredItem<GeoEgoWeaponItem> PARADISE_LOST = register(
    "paradise_lost_weapon", "失乐园",
    LcLevelType.ALEPH, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(6, 24)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(2))
      .attackDistance(0, 80 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.THE_SOUL)
      .virtueUsageReq(
        VirtueRating.V,
        VirtueRating.V,
        VirtueRating.V,
        VirtueRating.V,
        null)
      .model(new ModGeoItemModel<>("paradise_lost")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器每次攻击时会造成5次灵魂伤害。
  public static final DeferredItem<GeoEgoWeaponItem> JUSTITIA = register(
    "justitia_weapon", "正义裁决者",
    LcLevelType.ALEPH, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(3)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(4 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.THE_SOUL)
      .virtueUsageReq(
        null,
        null,
        null,
        VirtueRating.V,
        VirtueRating.V)
      .model(new ModGeoItemModel<>("justitia")),
    GeoEgoWeaponItem::new);

  // TODO 只有全属性超过110的员工才能拿起这把武器。
  // TODO 这把武器会同时造成物理，精神，侵蚀和灵魂伤害。
  // TODO 这把武器每隔一段时间会进行一次特殊攻击。
  public static final DeferredItem<GeoEgoWeaponItem> TWILIGHT = register(
    "twilight_weapon", "薄暝",
    LcLevelType.ALEPH, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(14)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(6 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(null,
        LcDamageType.PHYSICS,
        LcDamageType.SPIRIT,
        LcDamageType.EROSION,
        LcDamageType.THE_SOUL)
      .virtueUsageReq(
        111,
        111,
        111,
        111,
        0)
      .model(new ModGeoItemModel<>("twilight")),
    GeoEgoWeaponItem::new);

  // TODO 持有者每次攻击时都有10%的概提高5点最大与最小攻击力，该效果持续12秒。代价是，120秒内，持有者的自律相关属性会降低50%。
  // TODO 这把武器每次攻击时会造成3次物理伤害。
  public static final DeferredItem<GeoEgoWeaponItem> GOLD_RUSH = register(
    "gold_rush_weapon", "闪金冲锋",
    LcLevelType.ALEPH, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(5.5f)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(2 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        VirtueRating.V,
        null,
        null,
        null,
        VirtueRating.V)
      .model(new ModGeoItemModel<>("gold_rush")),
    GeoEgoWeaponItem::new);

  // TODO 每击杀一个目标，持有者的勇气与正义的相关属性会提高3点。该效果仅在当天有效。
  // TODO 发起普通攻击时，这把武器能减少被击中单位的移动速度。
  // TODO 发起特殊攻击时，这把武器能减少当前区域中所有敌对单位的移动速度。
  // TODO 这把武器在攻击时有30%的概率使用特殊攻击，持有者举起武器，跳起后重砸，对小范围内的目标造成1次35-45点侵蚀伤害和5次4点侵蚀伤害。
  public static final DeferredItem<GeoEgoWeaponItem> SMILE = register(
    "smile_weapon", "笑靥",
    LcLevelType.ALEPH, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(13)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(5 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.V,
        null,
        VirtueRating.V)
      .model(new ModGeoItemModel<>("smile")),
    GeoEgoWeaponItem::new);

  // TODO 每次攻击造成2次伤害
  // TODO 持有这「CENSORED」的员工受到伤害时，「CENSORED」会为其恢复相当于伤害数值40%的生命
  // TODO 这把武器的持有者在受到伤害时，会立刻得到和伤害类型相对应，且等同于伤害量40%的治疗效果。但若持有者在受到伤害后死亡，则无法得到对应的回复。(任何类型的伤害都会计入，包括恐惧伤害)
  public static final DeferredItem<GeoEgoWeaponItem> CENSORED = register(
    "censored_weapon", "CENSORED",
    LcLevelType.ALEPH, Type.SpecialTemplateType.MELEE,
    new GeoEgoWeaponItem.Builder()
      .damage(9)
      .attackSpeed(minuteToSpeedConversion(1.5f))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        VirtueRating.V,
        null,
        null,
        null,
        VirtueRating.V)
      .model(new ModGeoItemModel<>("censored")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器在攻击时会造成贯穿伤害，但该武器的伤害不会伤害到中立目标和友方目标。
  // TODO 这把武器的伤害和攻击次数与员工的最大精神值数值无关，仅与员工当前精神值占最大精神值的百分比有关。
  // TODO 持有者的精神值在低于最大精神值的30%时，武器产生的"新星之声"仅有一个，伤害为和面板相同的8-12点精神伤害。
  // TODO 持有者的精神值大于最大精神值的30%且小于60%时，每次攻击时武器产生的"新星之声"变为2个，增加的"新星之声"伤害为9-11点精神伤害
  // TODO 持有者的精神值大于最大精神值的60%时，每次攻击时武器产生的"新星之声"变为3个，再额外增加的"新星之声"伤害为11-16点精神伤害
  public static final DeferredItem<GeoEgoWeaponItem> SOUND_OF_A_STAR = register(
    "sound_of_a_star_weapon", "新星之声",
    LcLevelType.ALEPH, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .damage(6, 9)
      .attackSpeed(minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(1.67f))
      .attackDistance(0, 25 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        VirtueRating.V,
        VirtueRating.V,
        null,
        VirtueRating.V)
      .model(new ModGeoItemModel<>("sound_of_a_star")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器每次攻击之前需要花费等同于一次攻击间隔的时间趴下来准备攻击；若目标始终处于射程范围内且保持与武器持有者相对方向相同，则不需要再次准备。
  public static final DeferredItem<GeoEgoWeaponItem> PINK = register(
    "pink_weapon", "粉红军备",
    LcLevelType.ALEPH, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(6, 22)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(2))
      .attackDistance(0, 35 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        VirtueRating.V,
        null,
        null,
        VirtueRating.V)
      .model(new ModGeoItemModel<>("pink")),
    GeoEgoWeaponItem::new);

  // TODO 这把武器的子弹命中时，会给目标附加每秒受到 2 点侵蚀伤害的dot，持续 5 秒，不可叠加。
  // TODO 这把武器的子弹命中时，会使目标的移动速度在 5 秒内降低 30%。
  public static final DeferredItem<GeoEgoWeaponItem> ADORATION = register(
    "adoration_weapon", "爱慕",
    LcLevelType.ALEPH, Type.SpecialTemplateType.REMOTE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(6, 33)
      .attackSpeed(
        minuteToSpeedConversion(1.5f),
        minuteToSpeedConversion(3.47f))
      .attackDistance(0, 15 - 3)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.V,
        null,
        VirtueRating.V)
      .model(new ModGeoItemModel<>("adoration")),
    GeoEgoWeaponItem::new);
  //endregion

  public static void init(IEventBus bus) {
  }

  private static <I extends EgoWeaponItem> DeferredItem<I> register(String id, String zhName,
                                                                    LcLevelType lcLevelType,
                                                                    Type.SpecialTemplateType templateType,
                                                                    EgoWeaponItem.Builder builder,
                                                                    Function<EgoWeaponItem.Builder, I> item) {
    return register(id, zhName, lcLevelType, templateType, item, builder);
  }

  @NotNull
  private static <I extends EgoWeaponItem> DeferredItem<I> register(String id, String zhName, LcLevelType lcLevelType,
                                                                    Type.SpecialTemplateType templateType,
                                                                    Function<EgoWeaponItem.Builder, I> item,
                                                                    EgoWeaponItem.Builder builder) {
    return EgoWeaponItems.register(id, zhName, lcLevelType, templateType, item, builder);
  }
}
