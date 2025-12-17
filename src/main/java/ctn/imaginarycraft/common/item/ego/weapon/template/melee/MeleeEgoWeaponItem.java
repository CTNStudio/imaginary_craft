package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.common.item.weapon.WeaponItem;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public abstract class MeleeEgoWeaponItem extends EgoWeaponItem {

  public MeleeEgoWeaponItem(Properties properties, Builder builder) {
    super(properties, builder);
  }

  public static class Builder extends EgoWeaponItem.Builder<Builder> {
    /**
     * 近战攻击速度
     */
    public float attackSpeed;
    /**
     * 近战攻击距离
     */
    public float attackDistance;

    /**
     * 近战攻击速度
     */
    public Builder attackSpeed(float attackSpeed) {
      this.attackSpeed = attackSpeed;
      return this;
    }

    /**
     * 近战攻击距离
     */
    public Builder attackDistance(float attackDistance) {
      this.attackDistance = attackDistance;
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
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_SPEED, BASE_ATTACK_SPEED_ID, this.attackSpeed, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ENTITY_INTERACTION_RANGE, WeaponItem.ENTITY_RANGE, this.attackDistance, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      return builder.build();
    }
  }
}
