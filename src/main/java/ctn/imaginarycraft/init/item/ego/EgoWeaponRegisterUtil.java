package ctn.imaginarycraft.init.item.ego;

import ctn.imaginarycraft.api.capability.item.IItemEgo;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.*;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.*;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.datagen.tag.DatagenItemTag;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.BiFunction;

public abstract class EgoWeaponRegisterUtil {

  protected static @NotNull DeferredItem<RemoteEgoWeaponItem> registerRemoteTemplate(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    RemoteTemplateType remoteTemplateType,
    Item.Properties properties,
    RemoteEgoWeaponItem.Builder builder,
    String modelPath
  ) {
    return registerRemoteTemplate(id, zhName, lcLevelType, remoteTemplateType, properties, builder, switch (remoteTemplateType) {
      case CANNON -> (p, b) -> new CannonEgoWeaponItem(p, b, modelPath);
      case PISTOL -> (p, b) -> new PistolEgoWeaponItem(p, b, modelPath);
      case RIFLE -> (p, b) -> new RifleEgoWeaponItem(p, b, modelPath);
      case CROSSBOW -> (p, b) -> new CrossbowEgoWeaponItem(p, b, modelPath);
      default ->
        throw new IllegalStateException("Other types should be custom classes type: " + remoteTemplateType);
    });
  }

  protected static @NotNull <I extends RemoteEgoWeaponItem> DeferredItem<I> registerRemoteTemplate(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    @NotNull RemoteTemplateType remoteTemplateType,
    Item.Properties properties,
    RemoteEgoWeaponItem.@NotNull Builder builder,
    BiFunction<Item.Properties, RemoteEgoWeaponItem.Builder, I> item
  ) {
    var remoteBuilder = builder
      .attackInterval(remoteTemplateType.getAttackSpeed())
      .attackDistance(remoteTemplateType.getAttackDistance());
    builder.invincibleTick(remoteTemplateType.getInvincibleTick());
    return registerRemote(id, zhName, lcLevelType, remoteTemplateType, properties, remoteBuilder, item);
  }

  protected static @NotNull <I extends RemoteEgoWeaponItem> DeferredItem<I> registerRemote(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    RemoteTemplateType templateType,
    Item.Properties properties,
    RemoteEgoWeaponItem.Builder builder,
    BiFunction<Item.Properties, RemoteEgoWeaponItem.Builder, I> item
  ) {
    return register(id, zhName, lcLevelType, templateType, properties, builder, item);
  }

  protected static @NotNull DeferredItem<MeleeEgoWeaponItem> registerMeleeTemplate(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    MeleeTemplateType remoteTemplateType,
    Item.Properties properties,
    MeleeEgoWeaponItem.Builder builder,
    String modelPath
  ) {
    return registerMeleeTemplate(id, zhName, lcLevelType, remoteTemplateType, properties, builder, switch (remoteTemplateType) {
      case AXE -> (p, b) -> new AxeEgoWeaponItem(p, b, modelPath);
      case FIST -> (p, n) -> new FistEgoWeaponItem(p, n, modelPath);
      case HAMMER -> (p, b) -> new HammerEgoWeaponItem(p, b, modelPath);
      case KNIFE -> (p, b) -> new KnifeEgoWeaponItem(p, b, modelPath);
      case MACE -> (p, b) -> new MaceEgoWeaponItem(p, b, modelPath);
      case SPEAR -> (p, b) -> new SpearEgoWeaponItem(p, b, modelPath);
      case SWORDS -> SwordsEgoWeaponItem::new;
    });
  }

  protected static @NotNull <I extends MeleeEgoWeaponItem> DeferredItem<I> registerMeleeTemplate(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    @NotNull MeleeTemplateType remoteTemplateType,
    Item.Properties properties,
    MeleeEgoWeaponItem.@NotNull Builder builder,
    BiFunction<Item.Properties, MeleeEgoWeaponItem.Builder, I> item
  ) {
    var meleeBuilder = builder
      .attackSpeed(remoteTemplateType.getAttackSpeed())
      .attackDistance(remoteTemplateType.getAttackDistance());
    builder.invincibleTick(remoteTemplateType.getInvincibleTick());
    return registerMelee(id, zhName, lcLevelType, remoteTemplateType, properties, meleeBuilder, item);
  }

  protected static @NotNull <I extends MeleeEgoWeaponItem> DeferredItem<I> registerMelee(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    MeleeTemplateType remoteTemplateType,
    Item.Properties properties,
    MeleeEgoWeaponItem.Builder builder,
    BiFunction<Item.Properties, MeleeEgoWeaponItem.Builder, I> item
  ) {
    return register(id, zhName, lcLevelType, remoteTemplateType, properties, builder, item);
  }

  protected static @NotNull <I extends Item & IItemEgo, B extends EgoWeaponItem.Builder<B>> DeferredItem<I> register(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    @NotNull Type type,
    Item.Properties properties,
    B builder,
    BiFunction<Item.Properties, B, ? extends I> item
  ) {
    DeferredItem<I> deferredItem = EgoWeaponItems.REGISTRY.register(id, () -> item.apply(properties, builder));
    LcLevelUtil.addItemLcLevelCapability(lcLevelType, deferredItem);
    DatagenItemTag.EGO_WEAPON.add(deferredItem);
    type.addItem(deferredItem);
    ZhCn.clientAddI18nItemText(zhName, deferredItem);
    return deferredItem;
  }

  protected static float minuteToSpeedConversion(float attackSpeedTick) {
    return 20.0f / (attackSpeedTick * 20) - 4;
  }

  public enum SpecialTemplateType implements Type {
    /**
     * 特殊武器
     */
    SPECIAL("special", DatagenItemTag.SPECIAL),
    /**
     * 近战武器
     */
    MELEE("melee", DatagenItemTag.MELEE),
    /**
     * 远程武器
     */
    REMOTE("remote", DatagenItemTag.REMOTE),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;

    SpecialTemplateType(final String name, final Set<DeferredItem<? extends Item>> set) {
      this.name = name;
      this.set = set;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public void addItem(DeferredItem<? extends Item> item) {
      this.set.add(item);
    }
  }

  public enum RemoteTemplateType implements Type {
    /**
     * 加农炮 蓄力射击 攻击速度 5 攻击距离 15
     */
    CANNON("cannon", DatagenItemTag.CANNON, 5, 15),
    /**
     * 枪
     */
    GUN("gun", DatagenItemTag.GUN, -1, -1),
    /**
     * 手枪 攻击速度 0.667 攻击距离 10
     */
    PISTOL("pistol", DatagenItemTag.PISTOL, 0.667f, 10),
    /**
     * 来复枪 攻击速度 1 攻击距离 15
     */
    RIFLE("rifle", DatagenItemTag.RIFLE, 1, 15),
    /**
     * 弩 攻击速度 2 攻击距离 20
     */
    CROSSBOW("crossbow", DatagenItemTag.CROSSBOW, 2, 20),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;
    private final float attackSpeedTick;
    private final float attackDistance;

    RemoteTemplateType(final String name, final Set<DeferredItem<? extends Item>> set, float attackSpeedTick, float attackDistance) {
      this.name = name;
      this.set = set;
      this.attackSpeedTick = attackSpeedTick;
      this.attackDistance = attackDistance;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public void addItem(DeferredItem<? extends Item> item) {
      this.set.add(item);
    }

    public int getInvincibleTick() {
      return (int) (20 * Math.min(1, getAttackSpeedTick()));
    }

    public float getAttackSpeedTick() {
      return this.attackSpeedTick;
    }

    public float getAttackSpeed() {
      return minuteToSpeedConversion(getAttackSpeedTick());
    }

    public float getAttackDistance() {
      return this.attackDistance != -1 ? this.attackDistance - 3 : -1;
    }
  }

  public enum MeleeTemplateType implements Type {
    // 近战武器
    /**
     * 斧 攻击速度 1 攻击距离 2
     */
    AXE("axe", DatagenItemTag.AXE, 1, 2),
    /**
     * 拳套 攻击速度 2 攻击距离 2 每次造成2次伤害
     */
    FIST("fist", DatagenItemTag.FIST, 2, 2),
    /**
     * 锤 攻击速度 3 攻击距离 5
     */
    HAMMER("hammer", DatagenItemTag.HAMMER, 3, 5),
    /**
     * 刀 攻击速度 0.667 攻击距离 2
     */
    KNIFE("knife", DatagenItemTag.KNIFE, 0.667f, 2),
    /**
     * 棁 攻击速度 2 攻击距离 3
     */
    MACE("mace", DatagenItemTag.MACE, 2, 3),
    /**
     * 矛 攻击速度 1.5 攻击距离 4
     */
    SPEAR("spear", DatagenItemTag.SPEAR, 1.5f, 4),
    /**
     * 剑  攻击速度 1.667 攻击距离 3
     */
    SWORDS("swords", DatagenItemTag.SWORDS, 1.667f, 0),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;
    private final float attackSpeedTick;
    private final float attackDistance;

    MeleeTemplateType(final String name, final Set<DeferredItem<? extends Item>> set, float attackSpeedTick, float attackDistance) {
      this.name = name;
      this.set = set;
      this.attackSpeedTick = attackSpeedTick;
      this.attackDistance = attackDistance;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public void addItem(DeferredItem<? extends Item> item) {
      this.set.add(item);
    }

    public float getAttackSpeed() {
      return minuteToSpeedConversion(getAttackSpeedTick());
    }

    public float getAttackSpeedTick() {
      return this.attackSpeedTick;
    }

    public int getInvincibleTick() {
      return (int) (20 * Math.min(1, getAttackSpeedTick()));
    }

    public float getAttackDistance() {
      return this.attackDistance != -1 ? this.attackDistance - 3 : -1;
    }
  }

  public interface Type {
    String getName();

    void addItem(DeferredItem<? extends Item> item);
  }
}
