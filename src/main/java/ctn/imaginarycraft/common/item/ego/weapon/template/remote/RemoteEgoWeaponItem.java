package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
import ctn.imaginarycraft.core.capability.item.IEgoItem;
import ctn.imaginarycraft.core.capability.item.IItemUsageReq;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.ModDataComponents;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeaponItem extends ProjectileWeaponItem implements IEgoItem, IItemUsageReq {
  private final float attackDistance;
  private final @Nullable CreateProjectile<? extends Projectile> createProjectile;

  public RemoteEgoWeaponItem(@NotNull Properties itemProperties, @NotNull Builder remoteEgoWeaponBuilder) {
    super(itemProperties.stacksTo(1)
      .attributes(remoteEgoWeaponBuilder.getItemAttributeModifiers())
      .component(ModDataComponents.LC_DAMAGE_TYPE.get(), new LcDamageType.Component(remoteEgoWeaponBuilder.lcDamageType, remoteEgoWeaponBuilder.canCauseLcDamageTypes))
      .component(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, remoteEgoWeaponBuilder.virtueUsageReqBuilder.build())
      .component(ModDataComponents.IS_RESTRAIN, false));
    this.attackDistance = remoteEgoWeaponBuilder.attackDistance;
    this.createProjectile = remoteEgoWeaponBuilder.createProjectile;
  }

  /**
   * 获取子弹最远距离（单位方块）
   */
  @Override
  public int getDefaultProjectileRange() {
    return (int) attackDistance;
  }

  /**
   * 获取物品的攻击伤害
   */
  public float getDamage(@NotNull LivingEntity entity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    // TODO 引入主副手伤害
    return (float) entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
  }

  /**
   * 获取子弹最远距离（单位：方块）
   */
  public float getProjectileRange(@NotNull LivingEntity entity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    return attackDistance;
  }

  public float getProjectileInaccuracy(@NotNull LivingEntity entity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    return 0;
  }

  public float getProjectileVelocity(@NotNull LivingEntity entity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    return 10.0F;
  }

  /**
   * 此方法为{@link ProjectileWeaponItem#shoot}不消耗弹药的的版本简易版
   */
  protected void notConsumingShoot(ServerLevel world, LivingEntity shooterEntity, InteractionHand handUsed, ItemStack weaponItem,
                                   float projectileVelocity, float projectileInaccuracy, @Nullable LivingEntity targetEntity) {
    Projectile projectile = this.createProjectile(world, shooterEntity, weaponItem, null);
    this.shootProjectile(shooterEntity, projectile, 0, projectileVelocity, projectileInaccuracy, 0, targetEntity);
    world.addFreshEntity(projectile);
  }

  /**
   * 是否可以挖掘方块
   */
  @Override
  public boolean canAttackBlock(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos blockPosition, @NotNull Player playerEntity) {
    return !playerEntity.isCreative();
  }

  @Override
  public int getUseDuration(@NotNull ItemStack itemStack, @NotNull LivingEntity usingEntity) {
    return Integer.MAX_VALUE;
  }

  @Override
  public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
    return UseAnim.NONE;
  }

  @Override
  protected @NotNull Projectile createProjectile(@NotNull Level world, @NotNull LivingEntity shooterEntity, @NotNull ItemStack weaponItem,
                                                 @Nullable ItemStack ammoItem, boolean isCrit) {
    return this.createProjectile != null ?
      this.createProjectile.createProjectile(world, shooterEntity, weaponItem, ammoItem) :
      super.createProjectile(world, shooterEntity, weaponItem, Items.ARROW.getDefaultInstance(), isCrit);
  }

  protected @NotNull Projectile createProjectile(@NotNull Level world, @NotNull LivingEntity shooterEntity, @NotNull ItemStack weaponItem,
                                                 @Nullable ItemStack ammoItem) {
    if (this.createProjectile != null) {
      return this.createProjectile.createProjectile(world, shooterEntity, weaponItem, ammoItem);
    }
    ItemStack ammo1 = Items.ARROW.getDefaultInstance();
    ArrowItem arrowitem = ammo1.getItem() instanceof ArrowItem arrowitem1 ? arrowitem1 : (ArrowItem) Items.ARROW;
    AbstractArrow abstractarrow = arrowitem.createArrow(world, ammo1, shooterEntity, weaponItem);
    abstractarrow.setCritArrow(true);

    return customArrow(abstractarrow, ammo1, weaponItem);
  }

  @Override
  public @NotNull Predicate<ItemStack> getSupportedHeldProjectiles(@NotNull ItemStack itemStack) {
    return (stack1) -> false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public @NotNull Predicate<ItemStack> getSupportedHeldProjectiles() {
    return (stack1) -> false;
  }

  @Override
  public @NotNull Predicate<ItemStack> getAllSupportedProjectiles(@NotNull ItemStack itemStack) {
    return (stack1) -> false;
  }

  @Override
  public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
    return (stack1) -> false;
  }

  // TODO 如果是使用箭的武器如果没有箭就消耗一定量的理智（生成特殊箭），正常发射（有箭）消耗少量理智
  @Override
  public @NotNull ItemStack getDefaultCreativeAmmo(@Nullable Player playerEntity, @NotNull ItemStack projectileWeaponItem) {
    return Items.AIR.getDefaultInstance();
  }

  public static class Builder extends EgoWeaponItem.Builder<Builder> {
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
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_DAMAGE, BASE_ATTACK_DAMAGE_ID, this.weaponDamage, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ATTACK_SPEED, BASE_ATTACK_SPEED_ID, this.attackInterval, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.HAND);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.ATTACK_SPEED_MAIN_HAND, BASE_ATTACK_SPEED_ID, this.attackIntervalMainHand, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.MAINHAND);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.ATTACK_SPEED_OFF_HAND, BASE_ATTACK_SPEED_ID, this.attackIntervalOffHand, AttributeModifier.Operation.ADD_VALUE, EquipmentSlotGroup.OFFHAND);
      return builder.build();
    }
  }

  @FunctionalInterface
  public interface CreateProjectile<T extends Projectile> {
    T createProjectile(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon);
  }
}
