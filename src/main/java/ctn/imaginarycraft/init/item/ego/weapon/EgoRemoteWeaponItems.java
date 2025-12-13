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
public final class EgoRemoteWeaponItems {
  public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(ImaginaryCraft.ID);

  public static final DeferredItem<GeoEgoWeaponItem> SODA = register(
    "soda_weapon", "美味苏打",
    1.5f, 0.667f, 1, 5,
    LcLevelType.ZAYIN, EgoSpecialWeaponItems.TemplateType.PISTOL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("soda"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> FOURTH_MATCH_FLAME = register(
    "fourth_match_flame_weapon", "终末火柴之光",
    0, 0, 0, 20,
    LcLevelType.TETH, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("fourth_match_flame"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> SOLITUDE = register(
    "solitude_weapon", "孤独",
    0, 0, 0, 20,
    LcLevelType.TETH, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("solitude"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> BEAK = register(
    "beak_weapon", "小喙",
    0, 0, 0, 20,
    LcLevelType.TETH, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("beak"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> TODAY_IS_EXPRESSION = register(
    "today_is_expression_weapon", "此刻的神色",
    0, 0, 0, 20,
    LcLevelType.TETH, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("today_is_expression"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> TOUGH = register(
    "tough_weapon", "谢顶之灾",
    0, 0, 0, 20,
    LcLevelType.TETH, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("tough"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> SCREAMING_WEDGE = register(
    "screaming_wedge", "刺耳嚎叫",
    0, 0, 0, 20,
    LcLevelType.TETH, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("screaming_wedge"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> HARMONY = register(
    "harmony_weapon", "谐奏放射器",
    0, 0, 0, 20,
    LcLevelType.TETH, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("harmony"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> LAETITIA = register(
    "laetitia_weapon", "蕾蒂希娅",
    0, 0, 0, 20,
    LcLevelType.HE, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("laetitia"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> HORNET = register(
    "hornet_weapon", "黄蜂",
    0, 0, 0, 20,
    LcLevelType.WAW, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("hornet"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> FAINT_AROMA = register(
    "faint_aroma_weapon", "余香",
    0, 0, 0, 20,
    LcLevelType.WAW, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("faint_aroma"),
    new GeoEgoWeaponItem.Builder());

  public static final DeferredItem<GeoEgoWeaponItem> EXUVIAE = register(
    "exuviae_weapon", "脱落之皮",
    0, 0, 0, 20,
    LcLevelType.WAW, EgoSpecialWeaponItems.TemplateType.SPECIAL, LcDamageType.PHYSICS,
    new ItemVirtueUsageReq.Builder(),
    new ModGeoItemModel<>("exuviae"),
    new GeoEgoWeaponItem.Builder());

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

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  public enum TemplateType {
    /**
     * 加农炮 蓄力射击 攻击速度 5 攻击距离 15
     */
    CANNON("cannon", DatagenItemTag.CANNON, 100, 15),
    /**
     * 枪
     */
    GUN("gun", DatagenItemTag.GUN, -1, -1),
    /**
     * 手枪 攻击速度 0.667 攻击距离 10
     */
    PISTOL("pistol", DatagenItemTag.PISTOL, 12, 10),
    /**
     * 来复枪 攻击速度 1 攻击距离 15
     */
    RIFLE("rifle", DatagenItemTag.RIFLE, 20, 15),
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
      return this.attackDistance != -1 ? this.attackDistance - 3 : -1;
    }
  }
}
