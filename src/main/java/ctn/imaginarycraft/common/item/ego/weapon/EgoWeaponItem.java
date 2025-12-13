package ctn.imaginarycraft.common.item.ego.weapon;

import ctn.imaginarycraft.api.capability.item.IItemEgo;
import ctn.imaginarycraft.api.capability.item.IItemInvincibleTick;
import ctn.imaginarycraft.api.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.api.capability.item.IItemUsageReq;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueRating;
import ctn.imaginarycraft.client.model.ModGeoItemModel;
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
import software.bernie.geckolib.model.GeoModel;

import java.util.Set;

/**
 * EGO武器
 */
public class EgoWeaponItem extends EgoItem implements IItemEgo, IItemLcDamageType, IItemUsageReq, IItemInvincibleTick {
  private final int invincibleTick;
  private final @Nullable Set<LcDamageType> canCauseLcDamageTypes;
  private final @Nullable LcDamageType meleeLcDamageType;

  public EgoWeaponItem(Builder builder) {
    super(builder.buildProperties().attributes(builder.getItemAttributeModifiers())
      .stacksTo(1)
      .component(ModDataComponents.IS_RESTRAIN, false));
    this.meleeLcDamageType = builder.meleeLcDamageType;
    this.invincibleTick = builder.invincibleTick;
    this.canCauseLcDamageTypes = builder.canCauseLcDamageTypes;
  }

  public EgoWeaponItem(Properties properties, Builder builder) {
    this(builder.properties(properties));
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

  /// 是否可以挖掘方块
  @Override
  public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player) {
    return !player.isCreative();
  }

  /// 武器属性构造器
  public static class Builder {
    private Properties properties = new Properties();
    protected ItemVirtueUsageReq.Builder virtueUsageReqBuilder;
    protected @Nullable LcDamageType meleeLcDamageType;
    protected @Nullable Set<LcDamageType> canCauseLcDamageTypes;
    protected @Nullable GeoModel<GeoEgoWeaponItem> model;
    protected @Nullable GeoModel<GeoEgoWeaponItem> guiModel;
    protected float damage;
    protected float remoteDamage;
    /**
     * 攻击速度
     */
    protected float attackSpeed;
    protected float remoteAttackSpeed;
    /**
     * 近战攻击距离 & 可以摸到方块的距离
     */
    protected float attackDistance;
    protected float remoteAttackDistance;
    /**
     * 耐久
     */
    protected int durability;
    protected int invincibleTick = 20;
    protected int remoteInvincibleTick = 20;
    protected boolean isRemote;

    public Builder damage(float damage) {
      this.damage = damage;
      this.remoteDamage = damage;
      return this;
    }

    public Builder damage(float damage, float remoteDamage) {
      this.damage = damage;
      this.remoteDamage = remoteDamage;
      return this;
    }

    public Builder meleeLcDamageType(LcDamageType meleeLcDamageType, Set<LcDamageType> canCauseLcDamageTypes) {
      this.meleeLcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = canCauseLcDamageTypes;
      return this;
    }

    public Builder meleeLcDamageType(LcDamageType meleeLcDamageType, LcDamageType... canCauseLcDamageTypes) {
      this.meleeLcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = Set.of(canCauseLcDamageTypes);
      return this;
    }

    public Builder meleeLcDamageType(LcDamageType meleeLcDamageType) {
      this.meleeLcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = Set.of(meleeLcDamageType);
      return this;
    }

    public Builder model(GeoModel<GeoEgoWeaponItem> model) {
      this.model = model;
      return this;
    }

    public Builder guiModel(GeoModel<GeoEgoWeaponItem> model) {
      this.guiModel = model;
      return this;
    }

    public Builder model(GeoModel<GeoEgoWeaponItem> model, GeoModel<GeoEgoWeaponItem> guiModel) {
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

    public Builder remote() {
      this.isRemote = true;
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

    /**
     * @param fortitude  勇气
     * @param prudence   谨慎
     * @param temperance 自律
     * @param justice    正义
     * @param composite  综合等级
     */
    public Builder virtueUsageReq(VirtueRating fortitude, VirtueRating prudence, VirtueRating temperance, VirtueRating justice, VirtueRating composite) {
      this.virtueUsageReqBuilder = new ItemVirtueUsageReq.Builder();
      if (fortitude != null) {
        this.virtueUsageReqBuilder.fortitude(fortitude);
      }
      if (prudence != null) {
        this.virtueUsageReqBuilder.prudence(prudence);
      }
      if (temperance != null) {
        this.virtueUsageReqBuilder.temperance(temperance);
      }
      if (justice != null) {
        this.virtueUsageReqBuilder.justice(justice);
      }
      if (composite != null) {
        this.virtueUsageReqBuilder.composite(composite);
      }
      return this;
    }

    /**
     * @param fortitude  勇气
     * @param prudence   谨慎
     * @param temperance 自律
     * @param justice    正义
     * @param composite  综合等级
     */
    public Builder virtueUsageReq(int fortitude, int prudence, int temperance, int justice, int composite) {
      this.virtueUsageReqBuilder = new ItemVirtueUsageReq.Builder();
      if (fortitude != 0) {
        this.virtueUsageReqBuilder.fortitude(fortitude);
      }
      if (prudence != 0) {
        this.virtueUsageReqBuilder.prudence(prudence);
      }
      if (temperance != 0) {
        this.virtueUsageReqBuilder.temperance(temperance);
      }
      if (justice != 0) {
        this.virtueUsageReqBuilder.justice(justice);
      }
      if (composite != 0) {
        this.virtueUsageReqBuilder.composite(composite);
      }
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
      this.remoteInvincibleTick = invincibleTick;
      return this;
    }

    public Builder invincibleTick(int invincibleTick, int remoteInvincibleTick) {
      this.invincibleTick = invincibleTick;
      this.remoteInvincibleTick = remoteInvincibleTick;
      return this;
    }

    public Builder attackSpeed(float attackSpeed) {
      this.attackSpeed = attackSpeed;
      this.remoteAttackSpeed = attackSpeed;
      return this;
    }

    public Builder attackSpeed(float attackSpeed, float remoteAttackSpeed) {
      this.attackSpeed = attackSpeed;
      this.remoteAttackSpeed = remoteAttackSpeed;
      return this;
    }

    public Builder attackDistance(float attackDistance) {
      this.attackDistance = attackDistance;
      this.remoteAttackDistance = attackDistance;
      return this;
    }

    public Builder attackDistance(float attackDistance, float remoteAttackDistance) {
      this.attackDistance = attackDistance;
      this.remoteAttackDistance = remoteAttackDistance;
      return this;
    }

    public Builder properties(Properties properties) {
      this.properties = properties;
      return this;
    }

    public int getRemoteInvincibleTick() {
      return remoteInvincibleTick;
    }

    public float getRemoteDamage() {
      return remoteDamage;
    }

    public boolean isRemote() {
      return isRemote;
    }

    public float getRemoteAttackDistance() {
      return remoteAttackDistance;
    }
  }
}
