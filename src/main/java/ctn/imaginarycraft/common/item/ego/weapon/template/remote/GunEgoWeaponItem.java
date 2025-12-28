package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.util.GunChargeUpUtil;
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

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class GunEgoWeaponItem extends GeoRemoteEgoWeaponItem implements IGunWeapon {
  public GunEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoRemoteEgoWeaponItem> geoModel, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public GunEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  protected void defaultGunShootServerLevelConsumer(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, ServerLevel serverLevel) {
    this.shoot(serverLevel, playerEntity, playerEntity.getUsedItemHand(), itemStack, getProjectileVelocity(playerEntity, itemStack, handUsed), getProjectileInaccuracy(playerEntity, itemStack, handUsed), null);
  }

  protected boolean gunShootFunction(@NotNull Player playerEntity, Predicate<Float> percentagePredicate, Consumer<ServerLevel> serverLevelConsumer) {
    if (!percentagePredicate.test(GunChargeUpUtil.getPercentage(playerEntity))) {
      return false;
    }
    if (playerEntity.level() instanceof ServerLevel serverLevel) {
      serverLevelConsumer.accept(serverLevel);
    }
    return true;
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player playerEntity, @NotNull InteractionHand handUsed) {
    ItemStack itemStackInHand = playerEntity.getItemInHand(handUsed);
    if (isGunAim(playerEntity, itemStackInHand)) {
      gunAim(playerEntity, itemStackInHand);
    }
    playerEntity.startUsingItem(handUsed);
    GunChargeUpUtil.reset(playerEntity);
    return InteractionResultHolder.consume(itemStackInHand);
  }

  @Override
  public void gunAim(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    GunChargeUpUtil.reset(playerEntity);
  }

  @Override
  public void onUseTick(@NotNull Level world, @NotNull LivingEntity usingEntity, @NotNull ItemStack itemStack, int remainingUseDuration) {
    if (!(usingEntity instanceof ServerPlayer player)) {
      return;
    }
    int chargeUpValue = GunChargeUpUtil.getValue(player);
    if (player.getCurrentItemAttackStrengthDelay() > chargeUpValue) {
      GunChargeUpUtil.setValue(player, chargeUpValue + 1);
    }
  }

  @Override
  public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
    if (!(entity instanceof Player player)) {
      return;
    }

    if (isGunAim(player, stack)) {
      gunEndAim(player, stack);
      return;
    }

    gunEnd(player, stack);
  }

  @Override
  public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level world, @NotNull LivingEntity usingEntity, int timeCharged) {

  }

  @Override
  protected void shootProjectile(
    LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @javax.annotation.Nullable LivingEntity target
  ) {
    projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + angle, 0.0F, velocity, inaccuracy);
  }

  @Override
  public boolean gunAimShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    return gunShootFunction(playerEntity,
      chargeUpPercentage -> chargeUpPercentage >= 1,
      serverLevel -> defaultGunShootServerLevelConsumer(playerEntity, itemStack, handUsed, serverLevel));
  }

  @Override
  public boolean gunShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    return gunShootFunction(playerEntity,
      chargeUpPercentage -> chargeUpPercentage >= 1,
      serverLevel -> defaultGunShootServerLevelConsumer(playerEntity, itemStack, handUsed, serverLevel));
  }

  @Override
  public boolean onLeftClickEntity(ItemStack itemStack, Player playerEntity, Entity entity) {
    return true;
  }
}
