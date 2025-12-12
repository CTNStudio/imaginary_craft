package ctn.imaginarycraft.common.item.ego.weapon;

import ctn.imaginarycraft.api.capability.item.IItemEgo;
import ctn.imaginarycraft.api.capability.item.IItemInvincibleTick;
import ctn.imaginarycraft.api.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.api.capability.item.IItemUsageReq;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.client.model.ModGeoItemModel;
import ctn.imaginarycraft.client.renderer.providers.ModGeoItemRenderProvider;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.common.item.ego.EgoItem;
import ctn.imaginarycraft.common.item.weapon.WeaponItem;
import ctn.imaginarycraft.init.ModDataComponents;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Set;
import java.util.function.Consumer;

/**
 * EGO武器
 */
public class EgoWeaponItem extends EgoItem implements IItemEgo, IItemLcDamageType, IItemUsageReq, GeoItem, IItemInvincibleTick {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final int invincibleTick;
  private final @Nullable Set<LcDamageType> canCauseLcDamageTypes;
  private final @Nullable LcDamageType meleeLcDamageType;
  protected @Nullable GeoModel<EgoWeaponItem> model;
  protected @Nullable GeoModel<EgoWeaponItem> guiModel;

  public EgoWeaponItem(Builder builder) {
    super(builder.buildProperties().attributes(builder.getItemAttributeModifiers())
      .stacksTo(1)
      .component(ModDataComponents.IS_RESTRAIN, false));
    this.meleeLcDamageType = builder.meleeLcDamageType;
    this.invincibleTick = builder.invincibleTick;
    this.model = builder.model;
    this.guiModel = builder.guiModel;
    this.canCauseLcDamageTypes = builder.canCauseLcDamageTypes;
  }

  /**
   * 获取物品当前的伤害颜色
   */
  @Override
  @Nullable
  public LcDamageType getLcDamageColorDamageType(ItemStack stack) {
    return meleeLcDamageType;
  }

  @Override
  @Nullable
  public Set<LcDamageType> getCanCauseLcDamageTypes(ItemStack stack) {
    return this.canCauseLcDamageTypes;
  }

  /**
   * 使用物品时触发
   */
  @Override
  public void useImpede(ItemStack itemStack, Level level, LivingEntity entity) {

  }

  /**
   * 攻击时触发
   */
  @Override
  public void attackImpede(ItemStack itemStack, Level level, LivingEntity entity) {

  }

  /**
   * 在手上时触发
   */
  @Override
  public void onTheHandImpede(ItemStack itemStack, Level level, LivingEntity entity) {

  }

  /**
   * 物品在背包时里触发
   */
  @Override
  public void inTheBackpackImpede(ItemStack itemStack, Level level, LivingEntity entity) {

  }

  /**
   * 在装备里时触发，如盔甲，饰品
   */
  @Override
  public void equipmentImpede(ItemStack itemStack, Level level, LivingEntity entity) {

  }

  /// 获取武器攻击时造成的无敌帧
  @Override
  public int getInvincibleTick(ItemStack stack) {
    return invincibleTick;
  }

  /// 创建GEO模型渲染
  @Override
  public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
    consumer.accept(new ModGeoItemRenderProvider<>(this.model, this.guiModel));
  }

  /// 是否可以挖掘方块
  @Override
  public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player) {
    return !player.isCreative();
  }

  /// 创建动画控制器
  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
  }

  /// 获取动画实例缓存
  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  /// 武器属性构造器
  public static class Builder {
    private Properties properties = new Properties();
    protected ItemVirtueUsageReq.Builder virtueUsageReqBuilder;
    protected @Nullable LcDamageType meleeLcDamageType;
    protected @Nullable Set<LcDamageType> canCauseLcDamageTypes;
    protected @Nullable GeoModel<EgoWeaponItem> model;
    protected @Nullable GeoModel<EgoWeaponItem> guiModel;
    /**
     * 基本伤害
     */
    protected float damage;
    /**
     * 攻击速度
     */
    protected float attackSpeed;
    /**
     * 近战攻击距离 & 可以摸到方块的距离
     */
    protected float attackDistance;
    /**
     * 耐久
     */
    protected int durability;
    protected int invincibleTick;

    public Builder damage(float damage) {
      this.damage = damage;
      return this;
    }

    public Builder meleeLcDamageType(LcDamageType meleeLcDamageType, Set<LcDamageType> canCauseLcDamageTypes) {
      this.meleeLcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = canCauseLcDamageTypes;
      return this;
    }

    public Builder model(GeoModel<EgoWeaponItem> model) {
      this.model = model;
      return this;
    }

    public Builder guiModel(GeoModel<EgoWeaponItem> model) {
      this.guiModel = model;
      return this;
    }

    public Builder model(GeoModel<EgoWeaponItem> model, GeoModel<EgoWeaponItem> guiModel) {
      this.model = model;
      this.guiModel = guiModel;
      return this;
    }

    public Builder model(String modelRl) {
      this.model = new ModGeoItemModel<>(modelRl);
      return this;
    }

    public Builder guiModel(String modelRl) {
      this.guiModel = new ModGeoItemModel<>(modelRl);
      return this;
    }

    public Builder model(String modelRl, String guiModelRl) {
      this.model = new ModGeoItemModel<>(modelRl);
      this.guiModel = new ModGeoItemModel<>(guiModelRl);
      return this;
    }

    public Properties buildProperties() {
      Properties properties = this.properties;
      int durability = this.durability;
      properties.attributes(getItemAttributeModifiers());
      if (durability > 0) {
        properties.durability(durability);
      }
      ItemVirtueUsageReq.Builder.add(properties, this.virtueUsageReqBuilder);
      return properties;
    }

    public Builder virtueUsageReq(ItemVirtueUsageReq.Builder virtueUsageReqBuilder) {
      this.virtueUsageReqBuilder = virtueUsageReqBuilder;
      return this;
    }

    public LcDamageType getMeleeLcDamageType() {
      return meleeLcDamageType;
    }

    public ItemAttributeModifiers getItemAttributeModifiers() {
      ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID, this.damage, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_SPEED, BASE_ATTACK_SPEED_ID, this.attackSpeed, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ENTITY_INTERACTION_RANGE, WeaponItem.ENTITY_RANGE, this.attackDistance, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.BLOCK_INTERACTION_RANGE, WeaponItem.BLOCK_RANGE, this.attackDistance, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      return builder.build();
    }

    public Builder durability(int durability) {
      this.durability = durability;
      return this;
    }

    public Builder invincibleTick(int invincibleTick) {
      this.invincibleTick = invincibleTick;
      return this;
    }

    public Builder attackSpeed(float attackSpeed) {
      this.attackSpeed = attackSpeed;
      return this;
    }

    public Builder attackDistance(float attackDistance) {
      this.attackDistance = attackDistance;
      return this;
    }

    public Builder properties(Properties properties) {
      this.properties = properties;
      return this;
    }
  }
}
