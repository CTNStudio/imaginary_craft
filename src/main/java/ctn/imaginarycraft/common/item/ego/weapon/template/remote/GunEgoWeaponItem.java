package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.api.PlayerTimingRun;
import ctn.imaginarycraft.util.GunWeaponUtil;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

// TODO 延迟攻击
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
    if (isGunAim(playerEntity, itemStackInHand)) {
      gunAim(playerEntity, itemStackInHand);
      playerEntity.startUsingItem(handUsed);
      PlayerTimingRun.getInstance(playerEntity).removeTimingRun(handUsed);
    }
    return InteractionResultHolder.pass(itemStackInHand);
  }

  @Override
  public void gunAim(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    GunWeaponUtil.setIsLeftKeyAttack(playerEntity, true);
    GunWeaponUtil.resetChargeUp(playerEntity);
  }

  @Override
  public void onUseTick(@NotNull Level world, @NotNull LivingEntity usingEntity, @NotNull ItemStack itemStack, int remainingUseDuration) {
    if (!(usingEntity instanceof ServerPlayer player)) {
      return;
    }
    GunWeaponUtil.modifyChargeUpValue(player, 1);
  }

  @Override
  public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
    if (!(entity instanceof ServerPlayer player)) {
      return;
    }

    if (isGunAim(player, stack)) {
      gunEndAim(player, stack);
    } else {
      gunEnd(player, stack);
    }

    GunWeaponUtil.setIsLeftKeyAttack(player, true);
    GunWeaponUtil.resetChargeUp(player);
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
    if (livingEntity instanceof ServerPlayer player) {
      GunWeaponUtil.setIsLeftKeyAttack(player, true);
      GunWeaponUtil.resetChargeUp(player);
    }
    return super.finishUsingItem(stack, level, livingEntity);
  }

  @Override
  public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
    if (livingEntity instanceof ServerPlayer player) {
      GunWeaponUtil.setIsLeftKeyAttack(player, true);
      GunWeaponUtil.resetChargeUp(player);
    }
  }

  @Override
  protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity,
                                 float inaccuracy, float angle, @Nullable LivingEntity target) {
    projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + angle, 0.0F, velocity, inaccuracy);
  }

  @Override
  public boolean gunAimShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    float chargeUpPercentage = GunWeaponUtil.getChargeUpPercentage(playerEntity);
    if (!gunAimShootCondition(playerEntity, itemStack, handUsed, chargeUpPercentage)) {
      return false;
    }
    return gunAimShootExecute(playerEntity, itemStack, handUsed, chargeUpPercentage);
  }

  @Override
  public boolean gunShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    float chargeUpPercentage = GunWeaponUtil.getChargeUpPercentage(playerEntity);
    if (!gunShootCondition(playerEntity, itemStack, handUsed, chargeUpPercentage)) {
      return false;
    }
    return gunShootExecute(playerEntity, itemStack, handUsed, chargeUpPercentage);
  }

  @Override
  public boolean onLeftClickEntity(ItemStack itemStack, Player playerEntity, Entity entity) {
    return true;
  }

  protected boolean gunShootCondition(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    return GunWeaponUtil.isIsLeftKeyAttack(playerEntity);
  }

  protected boolean gunShootExecute(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    if (playerEntity.level() instanceof ServerLevel serverLevel) {
      PlayerTimingRun.getInstance(playerEntity).addTimingRun(handUsed, PlayerTimingRun.createTimingRun((player) -> {
        this.shoot(serverLevel, playerEntity, playerEntity.getUsedItemHand(), itemStack,
          getProjectileVelocity(playerEntity, itemStack, handUsed),
          getProjectileInaccuracy(playerEntity, itemStack, handUsed), null);
        GunWeaponUtil.setIsLeftKeyAttack(playerEntity, true);
        GunWeaponUtil.resetChargeUp(playerEntity);
        return 0;
      }, 20));
      PlayerTimingRun.getInstance(playerEntity).addTimingRun(GunWeaponUtil.GUN_SHOOT_MODIFY_TICK, PlayerTimingRun.createTimingRun((player) -> {
        GunWeaponUtil.modifyChargeUpValue(player, 1);
        if (GunWeaponUtil.getChargeUpPercentage(player) >= 1) {
          return 0;
        }
        return 1;
      }, 1));
      GunWeaponUtil.setIsLeftKeyAttack(playerEntity, false);
      GunWeaponUtil.resetChargeUp(playerEntity);
    }
    return true;
  }

  protected boolean gunAimShootCondition(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    return chargeUpPercentage >= 1;
  }

  protected boolean gunAimShootExecute(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    if (playerEntity.level() instanceof ServerLevel serverLevel) {
      this.shoot(serverLevel, playerEntity, playerEntity.getUsedItemHand(), itemStack,
        getProjectileVelocity(playerEntity, itemStack, handUsed),
        getProjectileInaccuracy(playerEntity, itemStack, handUsed), null);
      GunWeaponUtil.resetChargeUp(playerEntity);
    }
    return true;
  }
}
