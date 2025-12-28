package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.util.ChargeUpUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.GeoModel;

public abstract class GunEgoWeaponItem extends GeoRemoteEgoWeaponItem implements IGunWeapon {
  public GunEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoRemoteEgoWeaponItem> geoModel, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public GunEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player playerEntity, @NotNull InteractionHand handUsed) {
    ItemStack itemStackInHand = playerEntity.getItemInHand(handUsed);
    if (isAim(playerEntity, itemStackInHand)) {
      aim(playerEntity, itemStackInHand);
    }
    playerEntity.startUsingItem(handUsed);
    return InteractionResultHolder.consume(itemStackInHand);
  }

  @Override
  public void aim(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    ChargeUpUtil.reset(playerEntity);
  }

  @Override
  public void onUseTick(@NotNull Level world, @NotNull LivingEntity usingEntity, @NotNull ItemStack itemStack, int remainingUseDuration) {
    if (!(usingEntity instanceof ServerPlayer player)) {
      return;
    }
    int chargeUpValue = ChargeUpUtil.getValue(player);
    if (player.getCurrentItemAttackStrengthDelay() > chargeUpValue) {
      ChargeUpUtil.setValue(player, chargeUpValue + 1);
    }
  }

  @Override
  public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level world, @NotNull LivingEntity usingEntity, int timeCharged) {
    if (!(usingEntity instanceof Player player)) {
      return;
    }

    if (isAim(player, itemStack)) {
      endAim(player, itemStack);
      return;
    }

    end(player, itemStack);
  }

  @Override
  protected void shootProjectile(@NotNull LivingEntity shooterEntity, @NotNull Projectile projectileEntity, int projectileIndex, float projectileVelocity, float projectileInaccuracy, float shootingAngle, @org.jetbrains.annotations.Nullable LivingEntity targetEntity) {

  }

  @Override
  public boolean aimShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    float attackStrengthScale = ChargeUpUtil.getPercentage(playerEntity);
    if (attackStrengthScale < 1) {
      return false;
    }
    if (playerEntity.level() instanceof ServerLevel serverLevel) {
      shoot(playerEntity, itemStack, handUsed, serverLevel);
    }
    return true;
  }

  @Override
  public boolean shoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    float attackStrengthScale = ChargeUpUtil.getPercentage(playerEntity);
    if (attackStrengthScale < 1) {
      return false;
    }
    if (playerEntity.level() instanceof ServerLevel serverLevel) {
      shoot(playerEntity, itemStack, handUsed, serverLevel);
    }
    return true;
  }

  protected void shoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, ServerLevel serverLevel) {
    this.shoot(serverLevel, playerEntity, playerEntity.getUsedItemHand(), itemStack, getProjectileVelocity(playerEntity, itemStack, handUsed), getProjectileInaccuracy(playerEntity, itemStack, handUsed), null);
  }

  @Override
  public boolean onLeftClickEntity(ItemStack itemStack, Player playerEntity, Entity entity) {
    return true;
  }
}
