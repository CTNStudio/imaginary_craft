package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.core.capability.item.IEgoItem;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.ModDataComponents;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface IRemoteEgoWeaponItem extends IEgoItem {
  static Item.Properties add(Item.Properties properties, Builder builder) {
    properties.attributes(builder.getItemAttributeModifiers())
      .component(ModDataComponents.LC_DAMAGE_TYPE.get(), new LcDamageType.Component(builder.lcDamageType, builder.canCauseLcDamageTypes))
      .component(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, builder.virtueUsageReqBuilder.build())
      .component(ModDataComponents.IS_RESTRAIN, false)
      .stacksTo(1);
    return properties;
  }

  @FunctionalInterface
  interface CreateProjectile<T extends Projectile> {
    T createProjectile(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon);
  }

  class Builder extends EgoWeaponItem.Builder<Builder> {
    /**
     * 远程攻击间隔
     */
    public float attackInterval;
    public float attackIntervalMainHand;
    public float attackIntervalOffHand;
    /**
     * 远程攻击距离
     */
    public float attackDistance;

    public CreateProjectile<? extends Projectile> createProjectile;

    public Builder createProjectile(CreateProjectile<? extends Projectile> projectileCreator) {
      this.createProjectile = projectileCreator;
      return this;
    }

    /**
     * 远程攻击间隔
     */
    public Builder attackIntervalHand(float weaponAttackInterval) {
      this.attackIntervalMainHand = weaponAttackInterval;
      this.attackIntervalOffHand = weaponAttackInterval;
      return this;
    }

    /**
     * 远程攻击间隔
     */
    public Builder attackIntervalMainHand(float weaponAttackInterval) {
      this.attackIntervalMainHand = weaponAttackInterval;
      return this;
    }

    /**
     * 远程攻击间隔
     */
    public Builder attackIntervalOffHand(float weaponAttackInterval) {
      this.attackIntervalOffHand = weaponAttackInterval;
      return this;
    }

    /**
     * 远程攻击距离
     */
    public Builder attackDistance(float weaponAttackDistance) {
      this.attackDistance = weaponAttackDistance;
      return this;
    }

    @Override
    protected Builder self() {
      return this;
    }

    @Override
    public ItemAttributeModifiers getItemAttributeModifiers() {
      ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_DAMAGE, Item.BASE_ATTACK_DAMAGE_ID, this.weaponDamage, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_SPEED, Item.BASE_ATTACK_SPEED_ID, this.attackInterval, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.ATTACK_SPEED_MAIN_HAND, Item.BASE_ATTACK_SPEED_ID, this.attackIntervalMainHand, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.MAINHAND);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.ATTACK_SPEED_OFF_HAND, Item.BASE_ATTACK_SPEED_ID, this.attackIntervalOffHand, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.OFFHAND);
      return builder.build();
    }
  }
}
