package ctn.imaginarycraft.init.item.ego;

import ctn.imaginarycraft.api.LcLevelType;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.*;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.*;
import ctn.imaginarycraft.core.ImaginaryCraftConstants;
import ctn.imaginarycraft.core.capability.item.IEgoItem;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.util.LcLevelUtil;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 优化后的Ego武器注册工具类，采用构建者模式统一管理武器创建和注册
 */
public abstract class EgoWeaponRegisterUtil {

  /**
   * 创建远程武器构建器
   */
  public static RemoteEgoWeaponBuilder createRemoteWeapon() {
    return new RemoteEgoWeaponBuilder();
  }

  /**
   * 创建近战武器构建器
   */
  public static MeleeEgoWeaponBuilder createMeleeWeapon() {
    return new MeleeEgoWeaponBuilder();
  }

  private static @NotNull <I extends Item & IEgoItem, B extends EgoWeaponItem.Builder<B>> DeferredItem<I> register(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    @NotNull EgoWeaponRegisterUtil.TemplateType templateType,
    Item.Properties properties,
    B builder,
    BiFunction<Item.Properties, B, ? extends I> itemFactory
  ) {
    DeferredItem<I> deferredItem = EgoWeaponItems.REGISTRY.register(id, () -> itemFactory.apply(properties, builder));
    LcLevelUtil.addItemLcLevelCapability(lcLevelType, deferredItem);
    ImaginaryCraftConstants.EGO_WEAPON.add(deferredItem);
    templateType.addItem(deferredItem);
    ZhCn.clientAddI18nItemText(zhName, deferredItem);
    return deferredItem;
  }

  public static float minuteToSpeedConversion(float attackSpeedTick) {
    if (attackSpeedTick <= 0) {
      return 0;
    }

    return 20f / (attackSpeedTick * 20f) - 4f;
  }

  public enum SpecialTemplateType implements TemplateType {
    /**
     * 特殊武器
     */
    SPECIAL("special", ImaginaryCraftConstants.SPECIAL),
    /**
     * 近战武器
     */
    MELEE("melee", ImaginaryCraftConstants.MELEE),
    /**
     * 远程武器
     */
    REMOTE("remote", ImaginaryCraftConstants.REMOTE);
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

  public enum RemoteTemplateType implements TemplateType {
    /**
     * 加农炮 蓄力射击 攻击速度 5 攻击距离 15
     */
    CANNON("cannon", ImaginaryCraftConstants.CANNON, 5, 15),
    /**
     * 枪
     */
    GUN("gun", ImaginaryCraftConstants.GUN, -1, -1),
    /**
     * 手枪 攻击速度 0.667 攻击距离 10
     */
    PISTOL("pistol", ImaginaryCraftConstants.PISTOL, 0.667f, 10),
    /**
     * 来复枪 攻击速度 1 攻击距离 15
     */
    RIFLE("rifle", ImaginaryCraftConstants.RIFLE, 1, 15),
    /**
     * 弩 攻击速度 2 攻击距离 20
     */
    CROSSBOW("crossbow", ImaginaryCraftConstants.CROSSBOW, 2, 20);
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

    public float getAttackSpeedTick() {
      return attackSpeedTick;
    }

    public float getAttackSpeed() {
      return minuteToSpeedConversion(getAttackSpeedTick());
    }

    public float getAttackDistance() {
      return attackDistance != -1 ? attackDistance - 3 : -1;
    }
  }

  public enum MeleeTemplateType implements TemplateType {
    /**
     * 斧 攻击速度 1 攻击距离 2
     */
    AXE("axe", ImaginaryCraftConstants.AXE, 1, 2),
    /**
     * 拳套 攻击速度 2 攻击距离 2 每次造成2次伤害
     */
    FIST("fist", ImaginaryCraftConstants.FIST, 2, 2),
    /**
     * 锤 攻击速度 3 攻击距离 5
     */
    HAMMER("hammer", ImaginaryCraftConstants.HAMMER, 3, 5),
    /**
     * 刀 攻击速度 0.667 攻击距离 2
     */
    KNIFE("knife", ImaginaryCraftConstants.KNIFE, 0.667f, 2),
    /**
     * 梲 攻击速度 2 攻击距离 3
     */
    MACE("mace", ImaginaryCraftConstants.MACE, 2, 3),
    /**
     * 矛 攻击速度 1.5 攻击距离 4
     */
    SPEAR("spear", ImaginaryCraftConstants.SPEAR, 1.5f, 4),
    /**
     * 剑  攻击速度 1.667 攻击距离 3
     */
    SWORDS("swords", ImaginaryCraftConstants.SWORDS, 1.667f, 0),
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
      return attackSpeedTick;
    }

    public float getAttackDistance() {
      return attackDistance != -1 ? attackDistance - 3 : -1;
    }
  }

  public interface TemplateType {
    String getName();

    void addItem(DeferredItem<? extends Item> item);
  }

  private abstract static class EgoWeaponBuilder<B extends EgoWeaponBuilder<B>> {
    protected String id;
    protected String zhName;
    protected LcLevelType lcLevelType;
    protected Item.Properties properties;
    protected String modelPath;

    public B id(String id) {
      this.id = id;
      return (B) this;
    }

    public B zhName(String zhName) {
      this.zhName = zhName;
      return (B) this;
    }

    public B lcLevelType(LcLevelType lcLevelType) {
      this.lcLevelType = lcLevelType;
      return (B) this;
    }

    public B properties(Item.Properties properties) {
      this.properties = properties;
      return (B) this;
    }

    public B modelPath(String modelPath) {
      this.modelPath = modelPath;
      return (B) this;
    }
  }

  public static class RemoteEgoWeaponBuilder extends EgoWeaponBuilder<RemoteEgoWeaponBuilder> {
    private RemoteTemplateType templateType;
    private RemoteEgoWeaponItem.Builder weaponBuilder = new RemoteEgoWeaponItem.Builder();

    public RemoteEgoWeaponBuilder type(RemoteTemplateType templateType) {
      this.templateType = templateType;
      // 自动填充模板默认属性
      weaponBuilder.attackIntervalMainHand(templateType.getAttackSpeed())
        .attackDistance(templateType.getAttackDistance());
      return this;
    }

    public RemoteEgoWeaponBuilder weaponBuilder(Function<RemoteEgoWeaponItem.Builder, RemoteEgoWeaponItem.Builder> builder) {
      this.weaponBuilder = builder.apply(weaponBuilder);
      return this;
    }

    private BiFunction<Item.Properties, RemoteEgoWeaponItem.Builder, RemoteEgoWeaponItem> getRemoteItemFactory(RemoteTemplateType type) {
      return switch (type) {
        case CANNON -> (p, b) -> new CannonEgoWeaponItem(p, b, modelPath);
        case PISTOL -> (p, b) -> new PistolEgoWeaponItem(p, b, modelPath);
        case RIFLE -> (p, b) -> new RifleEgoWeaponItem(p, b, modelPath);
        case CROSSBOW -> (p, b) -> new CrossbowEgoWeaponItem(p, b, modelPath);
        default -> throw new IllegalStateException("Unsupported remote template type: " + type);
      };
    }

    public <I extends Item & IRemoteEgoWeaponItem> DeferredItem<I> buildAndRegister() {
      validateRequiredFields();
      if (templateType == null) {
        throw new IllegalStateException("Template type is required when using template");
      }

      return (DeferredItem<I>) register(id, zhName, lcLevelType, templateType, properties, weaponBuilder, getRemoteItemFactory(templateType));
    }

    public <I extends Item & IRemoteEgoWeaponItem> DeferredItem<I> buildAndRegister(BiFunction<Item.Properties, RemoteEgoWeaponItem.Builder, I> itemFactory) {
      this.templateType = null;
      validateRequiredFields();
      return register(id, zhName, lcLevelType, SpecialTemplateType.MELEE, properties, weaponBuilder, itemFactory);
    }

    private void validateRequiredFields() {
      if (id == null || zhName == null || lcLevelType == null || properties == null) {
        throw new IllegalStateException("Missing required fields for remote weapon registration");
      }
    }
  }

  public static class MeleeEgoWeaponBuilder extends EgoWeaponBuilder<MeleeEgoWeaponBuilder> {
    private MeleeTemplateType templateType;
    private MeleeEgoWeaponItem.Builder weaponBuilder = new MeleeEgoWeaponItem.Builder();

    public MeleeEgoWeaponBuilder type(MeleeTemplateType templateType) {
      this.templateType = templateType;
      // 自动填充模板默认属性
      weaponBuilder.attackSpeed(templateType.getAttackSpeed())
        .attackDistance(templateType.getAttackDistance());
      return this;
    }

    /**
     * 自定义武器构建器（用于非模板化武器）
     */
    public MeleeEgoWeaponBuilder weaponBuilder(Function<MeleeEgoWeaponItem.Builder, MeleeEgoWeaponItem.Builder> builder) {
      this.weaponBuilder = builder.apply(weaponBuilder);
      return this;
    }

    private BiFunction<Item.Properties, MeleeEgoWeaponItem.Builder, MeleeEgoWeaponItem> getMeleeItemFactory(MeleeTemplateType type) {
      return switch (type) {
        case AXE -> (p, b) -> new AxeEgoWeaponItem(p, b, modelPath);
        case FIST -> (p, b) -> new FistEgoWeaponItem(p, b, modelPath);
        case HAMMER -> (p, b) -> new HammerEgoWeaponItem(p, b, modelPath);
        case KNIFE -> (p, b) -> new KnifeEgoWeaponItem(p, b, modelPath);
        case MACE -> (p, b) -> new MaceEgoWeaponItem(p, b, modelPath);
        case SPEAR -> (p, b) -> new SpearEgoWeaponItem(p, b, modelPath);
        case SWORDS -> (p, b) -> new SwordsEgoWeaponGeoItem(p, b, modelPath);
      };
    }

    public <I extends Item & IMeleeEgoWeaponItem> DeferredItem<I> buildAndRegister() {
      validateRequiredFields();
      if (templateType == null) {
        throw new IllegalStateException("Template type is required when using template");
      }
      return (DeferredItem<I>) register(id, zhName, lcLevelType, templateType, properties, weaponBuilder, getMeleeItemFactory(templateType));
    }

    public <I extends Item & IMeleeEgoWeaponItem> DeferredItem<I> buildAndRegister(BiFunction<Item.Properties, MeleeEgoWeaponItem.Builder, I> itemFactory) {
      validateRequiredFields();
      return register(id, zhName, lcLevelType, SpecialTemplateType.REMOTE, properties, weaponBuilder, itemFactory);
    }

    private void validateRequiredFields() {
      if (id == null || zhName == null || lcLevelType == null || properties == null) {
        throw new IllegalStateException("Missing required fields for melee weapon registration");
      }
    }
  }
}
