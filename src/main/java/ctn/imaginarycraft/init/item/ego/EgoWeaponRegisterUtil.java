package ctn.imaginarycraft.init.item.ego;

import ctn.imaginarycraft.api.LcLevelType;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.*;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.*;
import ctn.imaginarycraft.core.ImaginaryCraftConstants;
import ctn.imaginarycraft.core.capability.item.IEgoItem;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.util.LcLevelUtil;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;
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
  public static RemoteEgoWeaponBuilder onRemote() {
    return new RemoteEgoWeaponBuilder();
  }

  /**
   * 创建近战武器构建器
   */
  public static MeleeEgoWeaponBuilder onMelee() {
    return new MeleeEgoWeaponBuilder();
  }

  private static @NotNull <I extends Item & IEgoItem, B extends IEgoItem.Builder<?>> DeferredItem<I> register(
    String id,
    String zhName,
    LcLevelType lcLevelType,
    @NotNull TemplateType templateType,
    Item.Properties properties,
    B builder,
    BiFunction<Item.Properties, B, I> itemFactory
  ) {
    DeferredItem<I> deferredItem = EgoWeaponItems.REGISTRY.register(id, () -> itemFactory.apply(properties, builder));
    LcLevelUtil.addItemLcLevelCapability(lcLevelType, deferredItem);
    ImaginaryCraftConstants.EGO_WEAPON.add(deferredItem);
    templateType.addItem(deferredItem);
    ZhCn.clientAddI18nItemText(zhName, deferredItem);
    return deferredItem;
  }

  public static float minuteToSpeedConversion(float attackSpeed) {
    return (attackSpeed - 4);
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
     * 加农炮
     */
    CANNON("cannon", ImaginaryCraftConstants.CANNON, 5, 15),
    /**
     * 枪
     */
    GUN("gun", ImaginaryCraftConstants.GUN, -1, -1),
    /**
     * 手枪
     */
    PISTOL("pistol", ImaginaryCraftConstants.PISTOL, 0.667f, 10),
    /**
     * 来复枪
     */
    RIFLE("rifle", ImaginaryCraftConstants.RIFLE, 1, 15),
    /**
     * 弩
     */
    CROSSBOW("crossbow", ImaginaryCraftConstants.CROSSBOW, 2, 20);
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;
    private final float attackSpeed;
    private final float attackDistance;

    RemoteTemplateType(final String name, final Set<DeferredItem<? extends Item>> set, float attackSpeed, float attackDistance) {
      this.name = name;
      this.set = set;
      this.attackSpeed = attackSpeed;
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
      return minuteToSpeedConversion(attackSpeed);
    }

    public float getAttackDistance() {
      return attackDistance != -1 ? attackDistance - 3 : -1;
    }
  }

  public enum MeleeTemplateType implements TemplateType {
    /**
     * 斧
     */
    AXE("axe", ImaginaryCraftConstants.AXE, 1, 2),
    /**
     * 拳套
     */
    FIST("fist", ImaginaryCraftConstants.FIST, 4, 2),
    /**
     * 锤
     */
    HAMMER("hammer", ImaginaryCraftConstants.HAMMER, 1.15f, 5),
    /**
     * 刀
     */
    KNIFE("knife", ImaginaryCraftConstants.KNIFE, 2.4f, 2),
    /**
     * 梲
     */
    MACE("mace", ImaginaryCraftConstants.MACE, 1.6f, 3),
    /**
     * 矛
     */
    SPEAR("spear", ImaginaryCraftConstants.SPEAR, 1.2f, 4),
    /**
     * 剑
     */
    SWORDS("swords", ImaginaryCraftConstants.SWORDS, 1.6f, 0),
    ;
    private final String name;
    private final Set<DeferredItem<? extends Item>> set;
    private final float attackSpeed;
    private final float attackDistance;

    MeleeTemplateType(final String name, final Set<DeferredItem<? extends Item>> set, float attackSpeed, float attackDistance) {
      this.name = name;
      this.set = set;
      this.attackSpeed = attackSpeed;
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
      return minuteToSpeedConversion(attackSpeed);
    }

    public float getAttackDistance() {
      return attackDistance != -1 ? attackDistance - 3 : -1;
    }
  }

  public interface TemplateType {
    String getName();

    void addItem(DeferredItem<? extends Item> item);
  }

  private abstract static class EgoWeaponBuilder<I extends EgoWeaponBuilder<I>> {
    protected String id;
    protected String zhName;
    protected LcLevelType lcLevelType;
    protected Item.Properties properties;
    protected String modelPath;

    public I id(String id) {
      this.id = id;
      return (I) this;
    }

    public I zhName(String zhName) {
      this.zhName = zhName;
      return (I) this;
    }

    public I lcLevelType(LcLevelType lcLevelType) {
      this.lcLevelType = lcLevelType;
      return (I) this;
    }

    public I properties(Item.Properties properties) {
      this.properties = properties;
      return (I) this;
    }

    public I modelPath(String modelPath) {
      this.modelPath = modelPath;
      return (I) this;
    }

    protected int getEnchantmentValue() {
      return switch (lcLevelType) {
        case ZAYIN -> 0;
        case TETH -> 2;
        case HE -> 4;
        case WAW -> 8;
        case ALEPH -> 12;
      };
    }

    protected TagKey<Block> getIncorrectBlocksForDrops() {
      return switch (lcLevelType) {
        case ZAYIN, TETH -> BlockTags.INCORRECT_FOR_IRON_TOOL;
        case HE -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
        case WAW, ALEPH -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
      };
    }
  }

  public static class RemoteEgoWeaponBuilder extends EgoWeaponBuilder<RemoteEgoWeaponBuilder> {
    private RemoteTemplateType templateType;
    private RemoteEgoWeaponItem.Builder weaponBuilder = new RemoteEgoWeaponItem.Builder();

    public RemoteEgoWeaponBuilder type(RemoteTemplateType templateType) {
      this.templateType = templateType;
      weaponBuilder.attackIntervalMainHand(templateType.getAttackSpeed())
        .attackDistance(templateType.getAttackDistance());
      return this;
    }

    public RemoteEgoWeaponBuilder weaponBuilder(Function<RemoteEgoWeaponItem.Builder, RemoteEgoWeaponItem.Builder> builder) {
      this.weaponBuilder = builder.apply(weaponBuilder);
      return this;
    }

    private <I extends Item & IRemoteEgoWeaponItem> BiFunction<Item.Properties, IRemoteEgoWeaponItem.Builder, I> getRemoteItemFactory(RemoteTemplateType type) {
      return switch (type) {
        case CANNON -> (p, b) -> (I) new CannonEgoWeaponItem(p, b, modelPath);
        case PISTOL -> (p, b) -> (I) new PistolEgoWeaponItem(p, b, modelPath);
        case RIFLE -> (p, b) -> (I) new RifleEgoWeaponItem(p, b, modelPath);
        case CROSSBOW -> (p, b) -> (I) new CrossbowEgoWeaponGeoItem(p, b, modelPath);
        default -> throw new IllegalStateException("Unsupported remote template type: " + type);
      };
    }

    public <I extends Item & IRemoteEgoWeaponItem> DeferredItem<I> buildAndRegister() {
      validateRequiredFields();
      if (templateType == null) {
        throw new IllegalStateException("Template type is required when using template");
      }

      return register(id, zhName, lcLevelType, templateType, properties, weaponBuilder, getRemoteItemFactory(templateType));
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
    private IMeleeEgoWeaponItem.Builder weaponBuilder = new IMeleeEgoWeaponItem.Builder();

    public MeleeEgoWeaponBuilder type(MeleeTemplateType templateType) {
      this.templateType = templateType;
      // 自动填充模板默认属性
      weaponBuilder.attackSpeed(templateType.getAttackSpeed())
        .attackDistance(templateType.getAttackDistance());
      return this;
    }

    public MeleeEgoWeaponBuilder properties(Function<IMeleeEgoWeaponItem.Builder, IMeleeEgoWeaponItem.Builder> builder) {
      this.weaponBuilder = builder.apply(weaponBuilder);
      return this;
    }

    private <I extends Item & IMeleeEgoWeaponItem> BiFunction<Item.Properties, IMeleeEgoWeaponItem.Builder, I> getMeleeItemFactory() {
      return switch (templateType) {
        case AXE ->
          (p, b) -> (I) new AxeEgoWeaponGeoItem(getSimpleTier(weaponBuilder), p, b, modelPath);
        case FIST -> (p, b) -> (I) new FistEgoWeaponItem(p, b, modelPath);
        case HAMMER -> (p, b) -> (I) new HammerEgoWeaponItem(p, b, modelPath);
        case KNIFE -> (p, b) -> (I) new KnifeEgoWeaponItem(p, b, modelPath);
        case MACE -> (p, b) -> (I) new MaceEgoWeaponItem(p, b, modelPath);
        case SPEAR -> (p, b) -> (I) new SpearEgoWeaponItem(p, b, modelPath);
        case SWORDS -> (p, b) -> (I) new SwordsEgoWeaponGeoItem(p, b, modelPath);
      };
    }

    public <I extends Item & IMeleeEgoWeaponItem> DeferredItem<I> buildAndRegister() {
      validateRequiredFields();
      if (templateType == null) {
        throw new IllegalStateException("Template type is required when using template");
      }
      return register(id, zhName, lcLevelType, templateType, properties, weaponBuilder, getMeleeItemFactory());
    }

    public <I extends Item & IMeleeEgoWeaponItem> DeferredItem<I> buildAndRegister(BiFunction<Item.Properties, IMeleeEgoWeaponItem.Builder, I> itemFactory) {
      validateRequiredFields();
      return register(id, zhName, lcLevelType, SpecialTemplateType.REMOTE, properties, weaponBuilder, itemFactory);
    }

    private void validateRequiredFields() {
      if (id == null || zhName == null || lcLevelType == null || properties == null) {
        throw new IllegalStateException("Missing required fields for melee weapon registration");
      }
    }

    private @NotNull SimpleTier getSimpleTier(IMeleeEgoWeaponItem.Builder b) {
      return new SimpleTier(getIncorrectBlocksForDrops(), 0, b.attackSpeed, b.weaponDamage, getEnchantmentValue(), Ingredient::of);
    }
  }
}
