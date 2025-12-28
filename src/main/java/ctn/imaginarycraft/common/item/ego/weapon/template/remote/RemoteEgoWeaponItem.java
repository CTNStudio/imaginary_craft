package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.capability.item.IItemEgo;
import ctn.imaginarycraft.api.capability.item.IItemInvincibleTick;
import ctn.imaginarycraft.api.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.api.capability.item.IItemUsageReq;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.common.item.ego.weapon.template.EgoWeaponItem;
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

import java.util.Set;
import java.util.function.Predicate;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeaponItem extends ProjectileWeaponItem implements IItemEgo, IItemLcDamageType, IItemUsageReq, IItemInvincibleTick {
  private final float attackDistance;
  private final @Nullable LcDamageType lcDamageType;
  private final @Nullable Set<LcDamageType> canCauseLcDamageTypes;
  private final int invincibleTick;
  private final @Nullable CreateProjectile<? extends Projectile> createProjectile;

  public RemoteEgoWeaponItem(@NotNull Properties itemProperties, @NotNull Builder remoteEgoWeaponBuilder) {
    super(itemProperties.stacksTo(1)
      .attributes(remoteEgoWeaponBuilder.getItemAttributeModifiers())
      .component(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, remoteEgoWeaponBuilder.virtueUsageReqBuilder.build())
      .component(ModDataComponents.IS_RESTRAIN, false));
    this.lcDamageType = remoteEgoWeaponBuilder.lcDamageType;
    this.canCauseLcDamageTypes = remoteEgoWeaponBuilder.canCauseLcDamageTypes;
    this.invincibleTick = remoteEgoWeaponBuilder.invincibleTick;
    this.attackDistance = remoteEgoWeaponBuilder.attackDistance;
    this.createProjectile = remoteEgoWeaponBuilder.createProjectile;
  }

  @Override
  public int getDefaultProjectileRange() {
    return (int) attackDistance;
  }

  protected float getProjectileInaccuracy(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    return Math.max(1F / attackDistance, 0);
  }

  protected float getProjectileVelocity(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    return 10.0F;
  }

  protected void shoot(ServerLevel world, LivingEntity shooterEntity, InteractionHand handUsed, ItemStack weaponItem,
                       float projectileVelocity, float projectileInaccuracy, boolean isCrit, @Nullable LivingEntity targetEntity) {
    Projectile projectile = this.createProjectile(world, shooterEntity, weaponItem, null, isCrit);
    this.shootProjectile(shooterEntity, projectile, 0, projectileVelocity, projectileInaccuracy, 0, targetEntity);
    world.addFreshEntity(projectile);
    weaponItem.hurtAndBreak(1, shooterEntity, LivingEntity.getSlotForHand(handUsed));
  }

  protected void shoot(ServerLevel world, LivingEntity shooterEntity, InteractionHand handUsed, ItemStack weaponItem,
                       float projectileVelocity, float projectileInaccuracy, @Nullable LivingEntity targetEntity) {
    Projectile projectile = this.createProjectile(world, shooterEntity, weaponItem, null);
    this.shootProjectile(shooterEntity, projectile, 0, projectileVelocity, projectileInaccuracy, 0, targetEntity);
    world.addFreshEntity(projectile);
    weaponItem.hurtAndBreak(1, shooterEntity, LivingEntity.getSlotForHand(handUsed));
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

  /**
   * 获取武器攻击时造成的无敌帧
   */
  @Override
  public int getInvincibleTick(ItemStack itemStack) {
    return invincibleTick;
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
    public Builder attackInterval(float weaponAttackInterval) {
      this.attackInterval = weaponAttackInterval;
      return this;
    }

    /**
     * 远程攻击精准度
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
      return builder.build();
    }
  }

  @FunctionalInterface
  public interface CreateProjectile<T extends Projectile> {
    T createProjectile(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon);
  }
}
