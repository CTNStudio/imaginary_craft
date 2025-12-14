package ctn.imaginarycraft.init.item.ego.weapon;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.client.model.ModGeoItemModel;
import ctn.imaginarycraft.common.item.ego.weapon.template.GeoEgoWeaponItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static ctn.imaginarycraft.init.item.ego.weapon.EgoWeaponItems.minuteToSpeedConversion;

// TODO 根据类型切换类
// TODO 远程武器的近战攻击采用物理伤害
// TODO 远程攻击速度采用近战攻击速度
// TODO 在远程攻击中近战攻击距离是弹道偏移
public final class EgoMeleeWeaponItems {

  // TODO	如果持有者的正义等级高于2级,每次攻击都有5%的概率恢复10点精神值。
  public static final DeferredItem<GeoEgoWeaponItem> PENITENCE = register(
    "penitence_weapon", "忏悔",
    LcLevelType.ZAYIN, Type.MeleeTemplateType.MACE,
    new GeoEgoWeaponItem.Builder()
      .damage(6)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("penitence")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> WINGBEAT = register(
    "wingbeat_weapon", "翅振",
    LcLevelType.ZAYIN, Type.MeleeTemplateType.MACE,
    new GeoEgoWeaponItem.Builder()
      .damage(6)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("wingbeat")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> RED_EYES = register(
    "red_eyes_weapon", "赤瞳",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("red_eyes")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> HORN = register(
    "horn_weapon", "犄角",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("horn")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> WRIST_CUTTER = register(
    "wrist_cutter_weapon", "割腕者",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("wrist_cutter")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> REGRET = register(
    "regret_weapon", "悔恨",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("regret")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> FRAGMENTS_FROM_SOMEWHERE = register(
    "fragments_from_somewhere_weapon", "彼方的裂片",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("fragments_from_somewhere")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> LANTERN = register(
    "lantern_weapon", "诱捕幻灯",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("lantern")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> SO_CUTE = register(
    "so_cute_weapon", "超特么可爱！！！",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("so_cute")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> BEAR_PAWS = register(
    "bear_paws_weapon", "熊熊抱",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("bear_paws")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> SANGUINE_DESIRE = register(
    "sanguine_desire", "血之渴望",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("sanguine_desire")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> LOGGING = register(
    "logging_weapon", "伐木者",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("logging")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> FROST_SPLINTER = register(
    "frost_splinter", "霜之碎片",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("frost_splinter")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> CHRISTMAS = register(
    "christmas_weapon", "悲惨圣诞",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("christmas")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> HARVEST = register(
    "harvest_weapon", "猎头长耙",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("harvest")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> LAMP = register(
    "lamp_weapon", "目灯",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("lamp")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> GREEN_STEM = register(
    "green_stem_weapon", "绿色枝干",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("green_stem")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> SPORE = register(
    "spore_weapon", "荧光菌孢",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("spore")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> HEAVEN = register(
    "heaven_weapon", "穿刺极乐",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("heaven")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> DIFFRACTION = register(
    "diffraction_weapon", "虚无衍射体",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("diffraction")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> HYPOCRISY = register(
    "hypocrisy_weapon", "伪善",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(0)
      .attackSpeed(minuteToSpeedConversion(0))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("hypocrisy")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<GeoEgoWeaponItem> STANDARD_TRAINING_EGO = register(
    "standard_training_ego_weapon", "教学用E.G.O武器",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .damage(6)
      .attackSpeed(minuteToSpeedConversion(2))
      .attackDistance(0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("standard_training_ego")),
    GeoEgoWeaponItem::new);

  public static void init(IEventBus bus) {
  }

  private static <I extends GeoEgoWeaponItem> DeferredItem<I> register(String id, String zhName, LcLevelType lcLevelType,
                                                                       Type.MeleeTemplateType remoteTemplateType,
                                                                       GeoEgoWeaponItem.Builder builder,
                                                                       Function<GeoEgoWeaponItem.Builder, ? extends I> item) {
    return register(id, zhName, lcLevelType, remoteTemplateType, item, builder);
  }

  @NotNull
  private static <I extends GeoEgoWeaponItem> DeferredItem<I> register(String id, String zhName, LcLevelType lcLevelType,
                                                                       Type.MeleeTemplateType remoteTemplateType,
                                                                       Function<GeoEgoWeaponItem.Builder, ? extends I> item,
                                                                       GeoEgoWeaponItem.Builder builder) {
    return EgoWeaponItems.register(id, zhName, lcLevelType, remoteTemplateType, item, builder);
  }
}
