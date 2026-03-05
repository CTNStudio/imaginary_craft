package ctn.imaginarycraft.common.world.item.ego.weapon.remote.crossbow;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.api.world.item.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

/**
 * 弩型EGO武器物品类
 */
public class CrossbowEgoWeaponItem extends CrossbowItem implements IRemoteEgoWeaponItem {
  private final float attackDistance;
  private final @Nullable CreateProjectile<? extends Projectile> createProjectile;

  public CrossbowEgoWeaponItem(Properties itemProperties, Builder builder) {
    super(IEgoWeaponItem.add(itemProperties, builder));
    this.attackDistance = builder.attackDistance;
    this.createProjectile = builder.createProjectile;
  }

  /**
   * 获取子弹最远距离（单位方块）
   */
  @Override
  public int getDefaultProjectileRange() {
    return (int) attackDistance;
  }

  /**
   * 获取子弹最远距离（单位：方块）
   */
  public float getProjectileRange(@NotNull LivingEntity entity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    return attackDistance;
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

  protected void notConsumingShoot(ServerLevel world, LivingEntity shooterEntity, InteractionHand handUsed, ItemStack weaponItem,
                                   float projectileVelocity, float projectileInaccuracy, @Nullable LivingEntity targetEntity) {
    Projectile projectile = this.createProjectile(world, shooterEntity, weaponItem, null);
    this.shootProjectile(shooterEntity, projectile, 0, projectileVelocity, projectileInaccuracy, 0, targetEntity);
    world.addFreshEntity(projectile);
  }

  @Override
  public @Nullable LcDamageType getLcDamageType(ItemStack stack) {
    return null;
  }
}
