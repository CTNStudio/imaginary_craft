package ctn.imaginarycraft.init.item.ego.weapon;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueRating;
import ctn.imaginarycraft.client.model.ModGeoItemModel;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.GeoEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

// TODO 根据类型切换类
// TODO 远程武器的近战攻击采用物理伤害
// TODO 远程攻击速度采用近战攻击速度
// TODO 在远程攻击中近战攻击距离是弹道偏移
public final class EgoRemoteWeaponItems {

  public static final DeferredItem<RemoteEgoWeaponItem> SODA = registerTemplate(
    "soda_weapon", "美味苏打",
    LcLevelType.ZAYIN, Type.RemoteTemplateType.PISTOL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(1.5f, 0.667f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("soda")));

  public static final DeferredItem<RemoteEgoWeaponItem> FOURTH_MATCH_FLAME = registerTemplate(
    "fourth_match_flame_weapon", "终末火柴之光",
    LcLevelType.TETH, Type.RemoteTemplateType.CANNON,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 25)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("fourth_match_flame")));

  public static final DeferredItem<RemoteEgoWeaponItem> SOLITUDE = registerTemplate(
    "solitude_weapon", "孤独",
    LcLevelType.TETH, Type.RemoteTemplateType.PISTOL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 2.5f)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("solitude")));

  public static final DeferredItem<RemoteEgoWeaponItem> BEAK = registerTemplate(
    "beak_weapon", "小喙",
    LcLevelType.TETH, Type.RemoteTemplateType.PISTOL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 2.5f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("beak")));

  public static final DeferredItem<RemoteEgoWeaponItem> TODAY_IS_EXPRESSION = registerTemplate(
    "today_is_expression_weapon", "此刻的神色",
    LcLevelType.TETH, Type.RemoteTemplateType.PISTOL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 2.5f)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("today_is_expression")));

  // TODO 只有秃顶或是地中海的帅气员工才能使用这件屌爆的装备！
  // 这是什么阴的没边的效果（
  public static final DeferredItem<RemoteEgoWeaponItem> TOUGH = registerTemplate(
    "tough_weapon", "谢顶之灾",
    LcLevelType.TETH, Type.RemoteTemplateType.PISTOL,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 3)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(// TODO 光头（
        null,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("tough")));

  // TODO 如果持有者的勇气或谨慎等级低于3级，每次攻击都有50%的概率丧失5点精神值。
  public static final DeferredItem<RemoteEgoWeaponItem> SCREAMING_WEDGE = registerTemplate(
    "screaming_wedge", "刺耳嚎叫",
    LcLevelType.HE, Type.RemoteTemplateType.CROSSBOW,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 12)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        VirtueRating.II,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("screaming_wedge")));

  // TODO 如果持有者的生命值维持在10%及以上，那么持有者将消耗一定生命值发动更加强大的攻击。（造成30%的额外伤害）
  public static final DeferredItem<RemoteEgoWeaponItem> HARMONY = registerTemplate(
    "harmony_weapon", "谐奏放射器",
    LcLevelType.HE, Type.RemoteTemplateType.CANNON,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 40)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        VirtueRating.II,
        null,
        null,
        null,
        VirtueRating.II)
      .model(new ModGeoItemModel<>("harmony")));

  public static final DeferredItem<RemoteEgoWeaponItem> LAETITIA = registerTemplate(
    "laetitia_weapon", "蕾蒂希娅",
    LcLevelType.HE, Type.RemoteTemplateType.RIFLE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 5.5f)
      .meleeLcDamageType(LcDamageType.EROSION)
      .virtueUsageReq(
        null,
        null,
        VirtueRating.II,
        null,
        null)
      .model(new ModGeoItemModel<>("laetitia")));

  public static final DeferredItem<RemoteEgoWeaponItem> HORNET = registerTemplate(
    "hornet_weapon", "黄蜂",
    LcLevelType.WAW, Type.RemoteTemplateType.RIFLE,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 7.5f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        VirtueRating.III,
        null,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("hornet")));

  public static final DeferredItem<RemoteEgoWeaponItem> FAINT_AROMA = registerTemplate(
    "faint_aroma_weapon", "余香",
    LcLevelType.WAW, Type.RemoteTemplateType.CROSSBOW,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 15)
      .meleeLcDamageType(LcDamageType.SPIRIT)
      .virtueUsageReq(
        null,
        VirtueRating.III,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("faint_aroma")));

  // TODO 攻击时有25%的概率给目标添加一个易伤效果，使其受到的物理伤害加深。
  public static final DeferredItem<RemoteEgoWeaponItem> EXUVIAE = registerTemplate(
    "exuviae_weapon", "脱落之皮",
    LcLevelType.WAW, Type.RemoteTemplateType.CANNON,
    new GeoEgoWeaponItem.Builder()
      .remote()
      .damage(0, 42.5f)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        null,
        null,
        null,
        VirtueRating.IV)
      .model(new ModGeoItemModel<>("exuviae")));

  public static final DeferredItem<RemoteEgoWeaponItem> HYPOCRISY = registerTemplate(
    "hypocrisy_weapon", "伪善",
    LcLevelType.WAW, Type.RemoteTemplateType.CROSSBOW,
    new EgoWeaponItem.Builder()
      .remote()
      .damage(0, 15)
      .meleeLcDamageType(LcDamageType.PHYSICS)
      .virtueUsageReq(
        null,
        VirtueRating.III,
        null,
        null,
        null)
      .model(new ModGeoItemModel<>("hypocrisy")));

  public static void init(IEventBus bus) {
  }

  private static DeferredItem<RemoteEgoWeaponItem> registerTemplate(String id, String zhName,
                                                                    LcLevelType lcLevelType,
                                                                    Type.RemoteTemplateType remoteTemplateType,
                                                                    EgoWeaponItem.Builder builder) {
    return registerTemplate(id, zhName, lcLevelType, remoteTemplateType, builder, switch (remoteTemplateType) {
      case CANNON -> CannonEgoWeaponItem::new;
      case PISTOL -> PistolEgoWeaponItem::new;
      case RIFLE -> RifleEgoWeaponItem::new;
      case CROSSBOW -> CrossbowEgoWeaponItem::new;
      default ->
        throw new IllegalStateException("Other types should be custom classes type: " + remoteTemplateType);
    });
  }

  private static <I extends RemoteEgoWeaponItem> DeferredItem<I> registerTemplate(String id, String zhName,
                                                                                  LcLevelType lcLevelType,
                                                                                  Type.RemoteTemplateType remoteTemplateType,
                                                                                  EgoWeaponItem.Builder builder,
                                                                                  Function<EgoWeaponItem.Builder, I> item) {
    return register(id, zhName, lcLevelType, remoteTemplateType, item, builder
      .attackSpeed(remoteTemplateType.getAttackSpeed())
      .attackDistance(remoteTemplateType.getAttackDistance())
      .invincibleTick(remoteTemplateType.getInvincibleTick()));
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
}
