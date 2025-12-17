package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeaponItem extends EgoWeaponItem {
  private final float attackPrecise;

  public RemoteEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
    this.attackPrecise = builder.attackPrecise;
  }

  public float getAttackPrecise() {
    return attackPrecise;
  }

  public static class Builder extends EgoWeaponItem.Builder<Builder> {
    /**
     * 远程攻击间隔
     */
    public float attackInterval;
    /**
     * 远程攻击精准度
     */
    public float attackPrecise;

    /**
     * 远程攻击间隔
     */
    public Builder attackInterval(float attackInterval) {
      this.attackInterval = attackInterval;
      return this;
    }

    /**
     * 远程攻击精准度
     */
    public Builder attackPrecise(float attackPrecise) {
      this.attackPrecise = attackPrecise;
      return this;
    }

    @Override
    protected Builder self() {
      return this;
    }

    @Override
    public ItemAttributeModifiers getItemAttributeModifiers() {
      ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID, this.damage, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_SPEED, BASE_ATTACK_SPEED_ID, this.attackInterval, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      return builder.build();
    }
  }
}
