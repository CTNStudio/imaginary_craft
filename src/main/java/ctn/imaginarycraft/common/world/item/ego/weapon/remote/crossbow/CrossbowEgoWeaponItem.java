package ctn.imaginarycraft.common.world.item.ego.weapon.remote.crossbow;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.world.item.IRemoteEgoWeaponItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 弩型EGO武器物品类
 */
public class CrossbowEgoWeaponItem extends CrossbowItem implements IRemoteEgoWeaponItem {
  private final float attackDistance;
  private final @Nullable CreateProjectile<? extends Projectile> createProjectile;

  public CrossbowEgoWeaponItem(Properties itemProperties, Builder builder) {
    super(itemProperties);
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
