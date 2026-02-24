package ctn.imaginarycraft.common.item.ego.weapon.template.melee;

import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.common.item.weapon.WeaponItem;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public abstract class MeleeEgoWeaponItem extends EgoWeaponItem implements IMeleeEgoWeaponItem {

  public MeleeEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder) {
    super(itemProperties, egoWeaponBuilder);
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
    public Builder attackSpeed(float meleeAttackSpeed) {
      this.attackSpeed = meleeAttackSpeed;
      return this;
    }

    /**
     * 近战攻击距离
     */
    public Builder attackDistance(float meleeAttackDistance) {
      this.attackDistance = meleeAttackDistance;
      return this;
    }

    @Override
    protected Builder self() {
      return this;
    }

    @Override
    public ItemAttributeModifiers getItemAttributeModifiers() {
      ItemAttributeModifiers.Builder attributeBuilder = ItemAttributeModifiers.builder();
      ItemBuilderUtil.addAttributeModifier(attributeBuilder, Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID, this.weaponDamage, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(attributeBuilder, Attributes.ATTACK_SPEED, BASE_ATTACK_SPEED_ID, this.attackSpeed, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(attributeBuilder, Attributes.ENTITY_INTERACTION_RANGE, WeaponItem.ENTITY_RANGE, this.attackDistance, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      return attributeBuilder.build();
    }
  }
}
