package ctn.imaginarycraft.common.item.ego.weapon.template;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.virtue.VirtueRating;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.core.capability.item.IItemEgo;
import ctn.imaginarycraft.core.capability.item.IItemInvincibleTick;
import ctn.imaginarycraft.core.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.core.capability.item.IItemUsageReq;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
public abstract class EgoWeaponItem extends Item implements IItemEgo, IItemLcDamageType, IItemUsageReq, IItemInvincibleTick {
  private final @Nullable LcDamageType lcDamageType;
  private final @Nullable Set<LcDamageType> canCauseLcDamageTypes;
  private final int invincibleTick;

  public EgoWeaponItem(Properties itemProperties, Builder<?> egoWeaponBuilder) {
    super(itemProperties.stacksTo(1)
      .attributes(egoWeaponBuilder.getItemAttributeModifiers())
      .component(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, egoWeaponBuilder.virtueUsageReqBuilder.build())
      .component(ModDataComponents.IS_RESTRAIN, false));
    this.lcDamageType = egoWeaponBuilder.lcDamageType;
    this.canCauseLcDamageTypes = egoWeaponBuilder.canCauseLcDamageTypes;
    this.invincibleTick = egoWeaponBuilder.invincibleTick;
  }

  /**
   * 获取物品当前的伤害颜色
   */
  @Override
  @Nullable
  public LcDamageType getLcDamageColorDamageType(ItemStack itemStack) {
    return lcDamageType;
  }

  @Override
  @Nullable
  public Set<LcDamageType> getCanCauseLcDamageTypes(ItemStack itemStack) {
    return canCauseLcDamageTypes;
  }

  /// 获取武器攻击时造成的无敌帧
  @Override
  public int getInvincibleTick(ItemStack itemStack) {
    return invincibleTick;
  }

  /// 是否可以挖掘方块
  @Override
  public boolean canAttackBlock(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos blockPosition, Player playerEntity) {
    return !playerEntity.isCreative();
  }

  /// 武器属性构造器
  public static class Builder<T extends Builder<T>> {
    public Properties itemProperties = new Properties();
    public ItemVirtueUsageReq.Builder virtueUsageReqBuilder = new ItemVirtueUsageReq.Builder();
    public @Nullable LcDamageType lcDamageType;
    public @Nullable Set<LcDamageType> canCauseLcDamageTypes;
    public float weaponDamage;
    public int invincibleTick = 20;

    public T damage(float weaponDamageValue) {
      this.weaponDamage = weaponDamageValue;
      return self();
    }

    public T meleeLcDamageType(LcDamageType meleeLcDamageType, Set<LcDamageType> lcDamageTypesThatCanBeCaused) {
      this.lcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = lcDamageTypesThatCanBeCaused;
      return self();
    }

    public T meleeLcDamageType(LcDamageType meleeLcDamageType) {
      this.lcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = Set.of(meleeLcDamageType);
      return self();
    }

    public T meleeLcDamageType(LcDamageType meleeLcDamageType, LcDamageType... lcDamageTypesThatCanBeCaused) {
      this.lcDamageType = meleeLcDamageType;
      this.canCauseLcDamageTypes = Set.of(lcDamageTypesThatCanBeCaused);
      return self();
    }

    public T remote() {
      return self();
    }

    public T virtueUsageReq(ItemVirtueUsageReq.Builder virtueRequirementBuilder) {
      this.virtueUsageReqBuilder = virtueRequirementBuilder;
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

    public T invincibleTick(int weaponInvincibleTick) {
      this.invincibleTick = weaponInvincibleTick;
      return self();
    }

    public T properties(Properties itemProperties) {
      this.itemProperties = itemProperties;
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
