package ctn.imaginarycraft.init.item.ego.weapon;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import ctn.imaginarycraft.client.model.ModGeoItemModel;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.common.item.ego.weapon.GeoEgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.datagen.tag.DatagenItemTag;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Function;

// TODO 根据类型切换类
// TODO 远程武器的近战攻击采用物理伤害
// TODO 远程攻击速度采用近战攻击速度
// TODO 在远程攻击中近战攻击距离是弹道偏移
public final class EgoMeleeWeaponItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  // TODO	如果持有者的正义等级高于2级,每次攻击都有5%的概率恢复10点精神值。
  public static final DeferredItem<GeoEgoWeaponItem> PENITENCE = register(
    "penitence_weapon", "忏悔",
    6, 2, 0, 20,
    LcLevelType.ZAYIN, TemplateType.MACE, LcDamageType.SPIRIT,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("penitence"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> WINGBEAT = register(
    "wingbeat_weapon", "翅振",
    6, 2, 0, 20,
    LcLevelType.ZAYIN, TemplateType.MACE, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("wingbeat"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> RED_EYES = register(
    "red_eyes_weapon", "赤瞳",
    0, 0, 0, 20,
    LcLevelType.TETH, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("red_eyes"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> HORN = register(
    "horn_weapon", "犄角",
    0, 0, 0, 20,
    LcLevelType.TETH, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("horn"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> WRIST_CUTTER = register(
    "wrist_cutter_weapon", "割腕者",
    0, 0, 0, 20,
    LcLevelType.TETH, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("wrist_cutter"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> REGRET = register(
    "regret_weapon", "悔恨",
    0, 0, 0, 20,
    LcLevelType.TETH, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("regret"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> FRAGMENTS_FROM_SOMEWHERE = register(
    "fragments_from_somewhere_weapon", "彼方的裂片",
    0, 0, 0, 20,
    LcLevelType.TETH, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("fragments_from_somewhere"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> LANTERN = register(
    "lantern_weapon", "诱捕幻灯",
    0, 0, 0, 20,
    LcLevelType.TETH, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("lantern"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> SO_CUTE = register(
    "so_cute_weapon", "超特么可爱！！！",
    0, 0, 0, 20,
    LcLevelType.TETH, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("so_cute"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> BEAR_PAWS = register(
    "bear_paws_weapon", "熊熊抱",
    0, 0, 0, 20,
    LcLevelType.HE, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("bear_paws"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> SANGUINE_DESIRE = register(
    "sanguine_desire", "血之渴望",
    0, 0, 0, 20,
    LcLevelType.HE, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("sanguine_desire"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> LOGGING = register(
    "logging_weapon", "伐木者",
    0, 0, 0, 20,
    LcLevelType.HE, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("logging"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> FROST_SPLINTER = register(
    "frost_splinter", "霜之碎片",
    0, 0, 0, 20,
    LcLevelType.HE, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("frost_splinter"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> CHRISTMAS = register(
    "christmas_weapon", "悲惨圣诞",
    0, 0, 0, 20,
    LcLevelType.HE, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("christmas"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> HARVEST = register(
    "harvest_weapon", "猎头长耙",
    0, 0, 0, 20,
    LcLevelType.HE, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("harvest"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> LAMP = register(
    "lamp_weapon", "目灯",
    0, 0, 0, 20,
    LcLevelType.WAW, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("lamp"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> GREEN_STEM = register(
    "green_stem_weapon", "绿色枝干",
    0, 0, 0, 20,
    LcLevelType.WAW, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("green_stem"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> SPORE = register(
    "spore_weapon", "荧光菌孢",
    0, 0, 0, 20,
    LcLevelType.WAW, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("spore"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> HEAVEN = register(
    "heaven_weapon", "穿刺极乐",
    0, 0, 0, 20,
    LcLevelType.WAW, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("heaven"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> DIFFRACTION = register(
    "diffraction_weapon", "虚无衍射体",
    0, 0, 0, 20,
    LcLevelType.HE, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("diffraction"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> HYPOCRISY = register(
    "hypocrisy_weapon", "伪善",
    0, 0, 0, 20,
    LcLevelType.WAW, TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("hypocrisy"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> STANDARD_TRAINING_EGO = register(
    "standard_training_ego_weapon", "教学用E.G.O武器",
    6, 2, 0, 20,
    LcLevelType.TETH, TemplateType.SPECIAL, LcDamageType.SPIRIT,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("standard_training_ego"),
    new GeoEgoWeaponItem.Builder());

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  private static <I extends GeoEgoWeaponItem> DeferredItem<I> register(String id, String zhName,
                                                                       LcLevelType lcLevelType,
                                                                       EgoSpecialWeaponItems.TemplateType templateType,
                                                                       GeoEgoWeaponItem.Builder builder,
                                                                       Function<GeoEgoWeaponItem.Builder, ? extends I> item) {
    return register(id, zhName, lcLevelType, templateType, item, builder);
  }

  @NotNull
  private static <I extends GeoEgoWeaponItem> DeferredItem<I> register(String id, String zhName, LcLevelType lcLevelType,
                                                                       EgoSpecialWeaponItems.TemplateType templateType,
                                                                       Function<GeoEgoWeaponItem.Builder, ? extends I> item,
                                                                       GeoEgoWeaponItem.Builder builder) {
    DeferredItem<I> deferredItem = REGISTRY.register(id, () -> item.apply(builder));
    LcLevelUtil.addItemLcLevelCapability(lcLevelType, deferredItem);
    DatagenItemTag.EGO_WEAPON.add(deferredItem);
    templateType.addItem(deferredItem);
    ZhCn.ITEMS.put(deferredItem, zhName);
    return deferredItem;
  }

  public enum TemplateType {
    /**
     * 斧 攻击速度 1 攻击距离 2
     */
    AXE("axe", DatagenItemTag.AXE, 20, 2),
    /**
     * 弩 攻击速度 2 攻击距离 20
     */
    CROSSBOW("crossbow", DatagenItemTag.CROSSBOW, 40, 20),
    /**
     * 拳套 攻击速度 2 攻击距离 2 每次造成2次伤害
     */
    FIST("fist", DatagenItemTag.FIST, 40, 2),
    /**
     * 锤 攻击速度 3 攻击距离 5
     */
    HAMMER("hammer", DatagenItemTag.HAMMER, 60, 5),
    /**
     * 刀 攻击速度 0.667 攻击距离 2
     */
    KNIFE("knife", DatagenItemTag.KNIFE, 10, 2),
    /**
     * 棁 攻击速度 2 攻击距离 3
     */
    MACE("mace", DatagenItemTag.MACE, 40, 3),
    /**
     * 矛 攻击速度 1.5 攻击距离 4
     */
    SPEAR("spear", DatagenItemTag.SPEAR, 30, 4),
    /**
     * 剑  攻击速度 1.667 攻击距离 原版默认
     */
    SWORDS("swords", DatagenItemTag.SWORDS, 12, 0),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;
    private final int attackSpeedTick;
    private final float attackDistance;

    TemplateType(final String name, final Set<DeferredItem<? extends Item>> set, int attackSpeedTick, float attackDistance) {
      this.name = name;
      this.set = set;
      this.attackSpeedTick = attackSpeedTick;
      this.attackDistance = attackDistance;
    }

    public String getName() {
      return name;
    }

    public void addItem(DeferredItem<? extends Item> item) {
      this.set.add(item);
    }

    public int getAttackSpeedTick() {
      return this.attackSpeedTick;
    }

    public float getAttackSpeed() {
      return 20.0f / this.attackSpeedTick - 4;
    }

    public float getAttackDistance() {
      return this.attackDistance != -1 ? 3 - this.attackDistance : -1;
    }
  }
}
