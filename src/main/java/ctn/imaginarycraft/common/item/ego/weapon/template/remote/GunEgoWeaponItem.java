package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.IPlayer;
import ctn.imaginarycraft.api.ItemLeftEmptyClick;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nullable;

public abstract class GunEgoWeaponItem extends GeoRemoteEgoWeaponItem implements ItemLeftEmptyClick {
  public GunEgoWeaponItem(Properties properties, Builder builder, GeoModel<GeoRemoteEgoWeaponItem> model, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(properties, builder, model, guiModel);
  }

  public GunEgoWeaponItem(Properties properties, Builder builder, String modPath) {
    super(properties, builder, modPath);
  }

  /**
   * 是否可以瞄准
   */
  public boolean isAim(LivingEntity entity, ItemStack itemStack) {
    return true;
  }

  /**
   * 瞄准的时候是否可以移动
   */
  public boolean isAimMove(LivingEntity entity, ItemStack itemStack) {
    return true;
  }

  public void aimShoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon,
                       float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
    shoot(level, shooter, hand, weapon, velocity, inaccuracy, isCrit, target);
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
    ItemStack itemStack = player.getItemInHand(usedHand);
    if (player.getAttackStrengthScale(0.0F) < 1) {
      return InteractionResultHolder.fail(itemStack);
    }
    if (isAim(player, itemStack)) {
      aimAiming(player, itemStack);
    }
    player.startUsingItem(usedHand);
    return InteractionResultHolder.consume(itemStack);
  }

  @Override
  public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
    return (int) (20 * entity.getAttributeValue(Attributes.ATTACK_SPEED));
  }

  public void aim(Player player, ItemStack itemStack) {

  }

  @Override
  public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int remainingUseDuration) {
    if (remainingUseDuration - 1 == 0) {
      livingEntity.stopUsingItem();
    }
  }

  @Override
  public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
    return stack;
  }

  @Override
  public void onStopUsing(@NotNull ItemStack stack, @NotNull LivingEntity entity, int count) {
    if (isAim(entity, stack)) {
      endAimAiming(entity, stack);
      return;
    }
    endAiming(entity, stack);
  }

  @Override
  public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int timeCharged) {

  }

  @Override
  protected void shootProjectile(@NotNull LivingEntity shooter, @NotNull Projectile projectile, int index, float velocity, float inaccuracy, float angle, @org.jetbrains.annotations.Nullable LivingEntity target) {

  }

  public void aimAiming(@NotNull LivingEntity livingEntity, @NotNull ItemStack stack) {
    // TODO 发送瞄准
  }

  public void endAimAiming(@NotNull LivingEntity livingEntity, @NotNull ItemStack stack) {
    // TODO 发送结束瞄准
  }

  public void endAiming(@NotNull LivingEntity livingEntity, @NotNull ItemStack stack) {
    // TODO 发送结束
  }

  public void leftClickAiming(@NotNull Player livingEntity, @NotNull ItemStack stack) {
    // TODO 发送攻击
  }

  @Override
  public void leftClick(ItemStack stack, Player player) {
    float attackStrengthScale = player.getAttackStrengthScale(1.0F);
    if (attackStrengthScale < 1) {
      IPlayer.of(player).setImaginarycraft$AttackStrengthTicker((int) (player.getAttributeValue(Attributes.ATTACK_SPEED) * 20.0));
      return;
    }
    leftClickAiming(player, stack);
    player.resetAttackStrengthTicker();
  }
}
