package ctn.imaginarycraft.core.capability.item;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.virtue.VirtueRating;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface IEgoItem {

  static Item.Properties add(Item.Properties properties, Builder<?> builder) {
    return properties.attributes(builder.getItemAttributeModifiers())
      .component(ModDataComponents.LC_DAMAGE_TYPE.get(), new LcDamageType.Component(builder.lcDamageType, builder.canCauseLcDamageTypes))
      .component(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, builder.virtueUsageReqBuilder.build())
      .component(ModDataComponents.IS_RESTRAIN, false)
      .stacksTo(1);
  }

  /**
   * 武器属性构造器
   */
  class Builder<T extends Builder<T>> {
    public ItemVirtueUsageReq.Builder virtueUsageReqBuilder = new ItemVirtueUsageReq.Builder();
    public @Nullable LcDamageType lcDamageType;
    public @Nullable Set<LcDamageType> canCauseLcDamageTypes;
    public float weaponDamage;

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

    @SuppressWarnings("unchecked")
    protected T self() {
      return (T) this;
    }
  }
}
