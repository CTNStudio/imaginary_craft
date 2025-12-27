package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.IGunWeapon;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.GeoModel;

public abstract class GunEgoWeaponItem extends GeoRemoteEgoWeaponItem implements IGunWeapon {
  public GunEgoWeaponItem(Properties properties, Builder builder, GeoModel<GeoRemoteEgoWeaponItem> model, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(properties, builder, model, guiModel);
  }

  public GunEgoWeaponItem(Properties properties, Builder builder, String modPath) {
    super(properties, builder, modPath);
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
    ItemStack itemStack = player.getItemInHand(usedHand);
    if (isAim(player, itemStack)) {
      aim(player, itemStack);
    }
    player.startUsingItem(usedHand);
    return InteractionResultHolder.consume(itemStack);
  }

  @Override
  public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int remainingUseDuration) {

  }

  @Override
  public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
    return stack;
  }

  @Override
  public void onStopUsing(@NotNull ItemStack stack, @NotNull LivingEntity entity, int count) {

  }

  @Override
  public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int timeCharged) {
    if (!(livingEntity instanceof Player player)) {
      return;
    }

    if (isAim(player, stack)) {
      endAim(player, stack);
      return;
    }

    end(player, stack);
  }

  @Override
  protected void shootProjectile(@NotNull LivingEntity shooter, @NotNull Projectile projectile, int index, float velocity, float inaccuracy, float angle, @org.jetbrains.annotations.Nullable LivingEntity target) {

  }

  @Override
  public boolean aimShoot(@NotNull Player player, @NotNull ItemStack stack) {
    float attackStrengthScale = player.getAttackStrengthScale(0.5F);
    return !(attackStrengthScale < 1);
  }

  @Override
  public boolean shoot(@NotNull Player player, @NotNull ItemStack stack, @NotNull InteractionHand usedHand) {
    float attackStrengthScale = player.getAttackStrengthScale(0.5F);
    return !(attackStrengthScale < 1);
  }
}
