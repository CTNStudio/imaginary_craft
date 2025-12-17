package ctn.imaginarycraft.common.item.ego.weapon.template;

import ctn.imaginarycraft.api.capability.item.IItemEgo;
import ctn.imaginarycraft.api.capability.item.IItemInvincibleTick;
import ctn.imaginarycraft.api.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.api.capability.item.IItemUsageReq;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueRating;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.common.item.ego.EgoItem;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * EGO武器
 */
public abstract class EgoWeaponItem extends EgoItem implements IItemEgo, IItemLcDamageType, IItemUsageReq, IItemInvincibleTick {
  private final @Nullable LcDamageType lcDamageType;
  private final @Nullable Set<LcDamageType> canCauseLcDamageTypes;
  private final int invincibleTick;

  public EgoWeaponItem(Properties properties, Builder<?> builder) {
    super(properties.stacksTo(1)
      .attributes(builder.getItemAttributeModifiers())
      .component(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, builder.virtueUsageReqBuilder.build())
      .component(ModDataComponents.IS_RESTRAIN, false));
    this.lcDamageType = builder.lcDamageType;
    this.canCauseLcDamageTypes = builder.canCauseLcDamageTypes;
    this.invincibleTick = builder.invincibleTick;
  }

  /**
   * 获取物品当前的伤害颜色
   */
  @Override
  @Nullable
  public LcDamageType getLcDamageColorDamageType(ItemStack stack) {
    return lcDamageType;
  }

  @Override
  @Nullable
  public Set<LcDamageType> getCanCauseLcDamageTypes(ItemStack stack) {
    return canCauseLcDamageTypes;
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
  public static class Builder<T extends Builder<T>> {
    public Properties properties = new Properties();
    public ItemVirtueUsageReq.Builder virtueUsageReqBuilder = new ItemVirtueUsageReq.Builder();
    public @Nullable LcDamageType lcDamageType;
    public @Nullable Set<LcDamageType> canCauseLcDamageTypes;
    public float damage;
    public int invincibleTick = 20;

    public T damage(float damage) {
      this.damage = damage;
      return self();
    }

    public T meleeLcDamageType(LcDamageType meleeLcDamageType, Set<LcDamageType> canCauseLcDamageTypes) {
      this.lcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = canCauseLcDamageTypes;
      return self();
    }

    public T meleeLcDamageType(LcDamageType meleeLcDamageType) {
      this.lcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = Set.of(meleeLcDamageType);
      return self();
    }

    public T meleeLcDamageType(LcDamageType meleeLcDamageType, LcDamageType... canCauseLcDamageTypes) {
      this.lcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = Set.of(canCauseLcDamageTypes);
      return self();
    }

    public T remote() {
      return self();
    }

    public T virtueUsageReq(ItemVirtueUsageReq.Builder virtueUsageReqBuilder) {
      this.virtueUsageReqBuilder = virtueUsageReqBuilder;
      return self();
    }

    /**
     * @param fortitude  勇气
     * @param prudence   谨慎
     * @param temperance 自律
     * @param justice    正义
     * @param composite  综合等级
     */
    public T virtueUsageReq(VirtueRating fortitude, VirtueRating prudence, VirtueRating temperance, VirtueRating justice, VirtueRating composite) {
      this.virtueUsageReqBuilder = ItemVirtueUsageReq.Builder.of(fortitude, prudence, temperance, justice, composite);
      return self();
    }

    /**
     * @param fortitude  勇气
     * @param prudence   谨慎
     * @param temperance 自律
     * @param justice    正义
     * @param composite  综合等级
     */
    public T virtueUsageReq(int fortitude, int prudence, int temperance, int justice, int composite) {
      this.virtueUsageReqBuilder = ItemVirtueUsageReq.Builder.of(fortitude, prudence, temperance, justice, composite);
      return self();
    }

    public ItemAttributeModifiers getItemAttributeModifiers() {
      return ItemAttributeModifiers.builder().build();
    }

    public T invincibleTick(int invincibleTick) {
      this.invincibleTick = invincibleTick;
      return self();
    }

    public T properties(Properties properties) {
      this.properties = properties;
      return self();
    }

    @SuppressWarnings("unchecked")
    protected T self() {
      return (T) this;
    }
  }

  public record DamageType(@Nullable LcDamageType lcDamageType,
                           @Nullable Set<LcDamageType> canCauseLcDamageTypes) {
  }
}
