package ctn.imaginarycraft.common.world.item.ego.weapon.remote;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.world.item.IEgoWeaponItem;
import ctn.imaginarycraft.api.world.item.IRemoteEgoWeaponItem;
import ctn.imaginarycraft.core.capability.item.IItemUsageReq;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Predicate;

/**
 * 远程EGO武器
 */
public abstract class RemoteEgoWeaponItem extends ProjectileWeaponItem implements IItemUsageReq, IRemoteEgoWeaponItem {
  private final @Nullable LcDamageType lcDamageType;
  private final Set<LcDamageType> canCauseLcDamageTypes;
  private final float attackDistance;
  private final @Nullable CreateProjectile<? extends Projectile> createProjectile;

  public RemoteEgoWeaponItem(@NotNull Properties itemProperties, @NotNull Builder builder) {
    super(IEgoWeaponItem.add(itemProperties, builder));
    this.attackDistance = builder.attackDistance;
    this.createProjectile = builder.createProjectile;
    this.lcDamageType = builder.lcDamageType;
    this.canCauseLcDamageTypes = builder.canCauseLcDamageTypes;
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

  @Override
  public @Nullable LcDamageType getLcDamageType(ItemStack stack) {
    return lcDamageType;
  }

  @Override
  public @NotNull Set<LcDamageType> getCanCauseLcDamageTypes(ItemStack stack) {
    return canCauseLcDamageTypes;
  }
}
