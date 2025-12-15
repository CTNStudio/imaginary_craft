package ctn.imaginarycraft.init.item.ego.weapon;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.client.model.ModGeoItemModel;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.GeoEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.RemoteEgoWeaponItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

// TODO 根据类型切换类
// TODO 远程武器的近战攻击采用物理伤害
// TODO 远程攻击速度采用近战攻击速度
// TODO 在远程攻击中近战攻击距离是弹道偏移
public final class EgoRemoteWeaponItems {

  public static final DeferredItem<RemoteEgoWeaponItem> SODA = register(
    "soda_weapon", "美味苏打",
    LcLevelType.ZAYIN, Type.RemoteTemplateType.PISTOL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(1.5f, 0.667f)
      .attackSpeed(
        minuteToSpeedConversion(1),
        minuteToSpeedConversion(5))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("soda")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> FOURTH_MATCH_FLAME = register(
    "fourth_match_flame_weapon", "终末火柴之光",
    LcLevelType.TETH, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("fourth_match_flame")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> SOLITUDE = register(
    "solitude_weapon", "孤独",
    LcLevelType.TETH, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("solitude")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> BEAK = register(
    "beak_weapon", "小喙",
    LcLevelType.TETH, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("beak")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> TODAY_IS_EXPRESSION = register(
    "today_is_expression_weapon", "此刻的神色",
    LcLevelType.TETH, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("today_is_expression")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> TOUGH = register(
    "tough_weapon", "谢顶之灾",
    LcLevelType.TETH, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("tough")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> SCREAMING_WEDGE = register(
    "screaming_wedge", "刺耳嚎叫",
    LcLevelType.TETH, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("screaming_wedge")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> HARMONY = register(
    "harmony_weapon", "谐奏放射器",
    LcLevelType.TETH, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("harmony")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> LAETITIA = register(
    "laetitia_weapon", "蕾蒂希娅",
    LcLevelType.HE, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("laetitia")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> HORNET = register(
    "hornet_weapon", "黄蜂",
    LcLevelType.WAW, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("hornet")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> FAINT_AROMA = register(
    "faint_aroma_weapon", "余香",
    LcLevelType.WAW, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("faint_aroma")),
    GeoEgoWeaponItem::new);

  public static final DeferredItem<RemoteEgoWeaponItem> EXUVIAE = register(
    "exuviae_weapon", "脱落之皮",
    LcLevelType.WAW, Type.RemoteTemplateType.SPECIAL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 0)
      .attackSpeed(
        minuteToSpeedConversion(0),
        minuteToSpeedConversion(1))
      .attackDistance(0, 0)
      .invincibleTick(20)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("exuviae")),
    GeoEgoWeaponItem::new);

  public static void init(IEventBus bus) {
  }

  private static <I extends RemoteEgoWeaponItem> DeferredItem<I> register(String id, String zhName,
                                                                          LcLevelType lcLevelType,
                                                                          Type.RemoteTemplateType templateType,
                                                                          EgoWeaponItem.Builder builder,
                                                                          Function<EgoWeaponItem.Builder, I> item) {
    return register(id, zhName, lcLevelType, templateType, item, builder);
  }

  @NotNull
  private static <I extends RemoteEgoWeaponItem> DeferredItem<I> register(String id, String zhName, LcLevelType lcLevelType,
                                                                          Type.RemoteTemplateType templateType,
                                                                          Function<EgoWeaponItem.Builder, I> item,
                                                                          EgoWeaponItem.Builder builder) {
    return EgoWeaponItems.register(id, zhName, lcLevelType, templateType, item, builder);
  }

  private static float minuteToSpeedConversion(float attacksPerMinute) {
    return 20 * (attacksPerMinute / 60);
  }
}
