package ctn.imaginarycraft.init.item.ego.weapon;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.client.model.ModGeoItemModel;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

// TODO 根据类型切换类
public final class EgoMeleeWeaponItems {

  // TODO	如果持有者的正义等级高于2级,每次攻击都有5%的概率恢复10点精神值。
  public static final DeferredItem<MeleeEgoWeaponItem> PENITENCE = registerTemplate(
    "penitence_weapon", "忏悔",
    LcLevelType.ZAYIN, Type.MeleeTemplateType.MACE,
    new EgoWeaponItem.Builder()
      .damage(6)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("penitence")));

  public static final DeferredItem<MeleeEgoWeaponItem> WINGBEAT = registerTemplate(
    "wingbeat_weapon", "翅振",
    LcLevelType.ZAYIN, Type.MeleeTemplateType.MACE,
    new EgoWeaponItem.Builder()
      .damage(6)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("wingbeat")));

  public static final DeferredItem<MeleeEgoWeaponItem> RED_EYES = registerTemplate(
    "red_eyes_weapon", "赤瞳",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("red_eyes")));

  public static final DeferredItem<MeleeEgoWeaponItem> HORN = registerTemplate(
    "horn_weapon", "犄角",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("horn")));

  public static final DeferredItem<MeleeEgoWeaponItem> WRIST_CUTTER = registerTemplate(
    "wrist_cutter_weapon", "割腕者",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("wrist_cutter")));

  public static final DeferredItem<MeleeEgoWeaponItem> REGRET = registerTemplate(
    "regret_weapon", "悔恨",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("regret")));

  public static final DeferredItem<MeleeEgoWeaponItem> FRAGMENTS_FROM_SOMEWHERE = registerTemplate(
    "fragments_from_somewhere_weapon", "彼方的裂片",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("fragments_from_somewhere")));

  public static final DeferredItem<MeleeEgoWeaponItem> LANTERN = registerTemplate(
    "lantern_weapon", "诱捕幻灯",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("lantern")));

  public static final DeferredItem<MeleeEgoWeaponItem> SO_CUTE = registerTemplate(
    "so_cute_weapon", "超特么可爱！！！",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("so_cute")));

  public static final DeferredItem<MeleeEgoWeaponItem> BEAR_PAWS = registerTemplate(
    "bear_paws_weapon", "熊熊抱",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("bear_paws")));

  public static final DeferredItem<MeleeEgoWeaponItem> SANGUINE_DESIRE = registerTemplate(
    "sanguine_desire", "血之渴望",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("sanguine_desire")));

  public static final DeferredItem<MeleeEgoWeaponItem> LOGGING = registerTemplate(
    "logging_weapon", "伐木者",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("logging")));

  public static final DeferredItem<MeleeEgoWeaponItem> FROST_SPLINTER = registerTemplate(
    "frost_splinter", "霜之碎片",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("frost_splinter")));

  public static final DeferredItem<MeleeEgoWeaponItem> CHRISTMAS = registerTemplate(
    "christmas_weapon", "悲惨圣诞",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("christmas")));

  public static final DeferredItem<MeleeEgoWeaponItem> HARVEST = registerTemplate(
    "harvest_weapon", "猎头长耙",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("harvest")));

  public static final DeferredItem<MeleeEgoWeaponItem> LAMP = registerTemplate(
    "lamp_weapon", "目灯",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("lamp")));

  public static final DeferredItem<MeleeEgoWeaponItem> GREEN_STEM = registerTemplate(
    "green_stem_weapon", "绿色枝干",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("green_stem")));

  public static final DeferredItem<MeleeEgoWeaponItem> SPORE = registerTemplate(
    "spore_weapon", "荧光菌孢",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("spore")));

  public static final DeferredItem<MeleeEgoWeaponItem> HEAVEN = registerTemplate(
    "heaven_weapon", "穿刺极乐",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("heaven")));

  public static final DeferredItem<MeleeEgoWeaponItem> DIFFRACTION = registerTemplate(
    "diffraction_weapon", "虚无衍射体",
    LcLevelType.HE, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("diffraction")));

  public static final DeferredItem<MeleeEgoWeaponItem> HYPOCRISY = registerTemplate(
    "hypocrisy_weapon", "伪善",
    LcLevelType.WAW, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(0)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("hypocrisy")));

  public static final DeferredItem<MeleeEgoWeaponItem> STANDARD_TRAINING_EGO = registerTemplate(
    "standard_training_ego_weapon", "教学用E.G.O武器",
    LcLevelType.TETH, Type.MeleeTemplateType.SPECIAL,
    new EgoWeaponItem.Builder()
      .damage(6)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("standard_training_ego")));

  public static void init(IEventBus bus) {
  }

  private static DeferredItem<MeleeEgoWeaponItem> registerTemplate(String id, String zhName,
                                                                   LcLevelType lcLevelType,
                                                                             Type.MeleeTemplateType remoteTemplateType,
                                                                   EgoWeaponItem.Builder builder) {
    return registerTemplate(id, zhName, lcLevelType, remoteTemplateType, builder, switch (remoteTemplateType) {
      case AXE -> AxeEgoWeaponItem::new;
      case FIST -> FistEgoWeaponItem::new;
      case HAMMER -> HammerEgoWeaponItem::new;
      case KNIFE -> KnifeEgoWeaponItem::new;
      case MACE -> MaceEgoWeaponItem::new;
      case SPEAR -> SpearEgoWeaponItem::new;
      case SWORDS -> SwordsEgoWeaponItem::new;
    });
  }

  private static <I extends MeleeEgoWeaponItem> DeferredItem<I> registerTemplate(String id, String zhName,
                                                                                 LcLevelType lcLevelType,
                                                                                 Type.MeleeTemplateType remoteTemplateType,
                                                                                 EgoWeaponItem.Builder builder,
                                                                                 Function<EgoWeaponItem.Builder, I> item) {
    return register(id, zhName, lcLevelType, remoteTemplateType, item, builder
      .attackSpeed(remoteTemplateType.getAttackSpeed())
      .attackDistance(remoteTemplateType.getAttackDistance())
      .invincibleTick(remoteTemplateType.getInvincibleTick()));
  }

  private static <I extends MeleeEgoWeaponItem> DeferredItem<I> register(String id, String zhName,
                                                                         LcLevelType lcLevelType,
                                                                         Type.MeleeTemplateType remoteTemplateType,
                                                                         EgoWeaponItem.Builder builder,
                                                                         Function<EgoWeaponItem.Builder, I> item) {
    return register(id, zhName, lcLevelType, remoteTemplateType, item, builder);
  }

  @NotNull
  private static <I extends MeleeEgoWeaponItem> DeferredItem<I> register(String id, String zhName,
                                                                         LcLevelType lcLevelType,
                                                                         Type.MeleeTemplateType remoteTemplateType,
                                                                         Function<EgoWeaponItem.Builder, I> item,
                                                                         EgoWeaponItem.Builder builder) {
    return EgoWeaponItems.register(id, zhName, lcLevelType, remoteTemplateType, item, builder);
  }
}
