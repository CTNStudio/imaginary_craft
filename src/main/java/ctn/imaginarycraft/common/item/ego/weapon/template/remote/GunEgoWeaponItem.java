package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.api.IItemPlayerLeftClick;
import ctn.imaginarycraft.api.ILivingEntity;
import ctn.imaginarycraft.common.payloads.entity.living.LivingEntityAttackStrengthTickerPayload;
import ctn.imaginarycraft.util.PayloadUtil;
import net.minecraft.server.level.ServerPlayer;
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

public abstract class GunEgoWeaponItem extends GeoRemoteEgoWeaponItem implements IItemPlayerLeftClick, IGunWeapon {
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
  public void leftClickEmpty(Player player, ItemStack stack) {
    float attackStrengthScale = player.getAttackStrengthScale(0.5F);
    if (attackStrengthScale < 1) {
      int attackStrengthTicker = (int) (player.getAttributeValue(Attributes.ATTACK_SPEED) * 20.0);
      ILivingEntity.of(player).setImaginarycraft$AttackStrengthTicker(attackStrengthTicker);
      PayloadUtil.sendToClient((ServerPlayer) player, new LivingEntityAttackStrengthTickerPayload(attackStrengthTicker));
      return;
    }
    shoot(player, stack);
    player.resetAttackStrengthTicker();
  }
}
