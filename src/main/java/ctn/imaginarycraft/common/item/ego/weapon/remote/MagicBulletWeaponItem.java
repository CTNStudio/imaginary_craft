package ctn.imaginarycraft.common.item.ego.weapon.remote;

import com.zigythebird.playeranimcore.animation.Animation;
import ctn.imaginarycraft.api.IPlayer;
import ctn.imaginarycraft.api.client.playeranimcore.AnimCollection;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimRawAnimation;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GeoRemoteEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GunEgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

// TODO 禁止放到副手
public class MagicBulletWeaponItem extends GunEgoWeaponItem {
  public static final ResourceLocation STANDBY = ImaginaryCraft.modRl("magic_bullet_weapon.standby");
  public static final ResourceLocation GALLOP = ImaginaryCraft.modRl("magic_bullet_weapon.gallop");
  public static final ResourceLocation SHOOTING = ImaginaryCraft.modRl("magic_bullet_weapon.shooting");
  public static final ResourceLocation SHOOTING_LAUNCH = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.launch");
  public static final ResourceLocation SHOOTING_AIM = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim");
  public static final ResourceLocation SHOOTING_AIM_CYCLE = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim.cycle");
  public static final ResourceLocation SHOOTING_AIM_LAUNCH = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim.launch");
  public static final ResourceLocation SHOOTING_CYCLE = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.cycle");
  public static final ResourceLocation SHOOTING_CYCLE_AIM = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.cycle.aim");

  @SuppressWarnings("unchecked")
  public static final AnimCollection ANIM_COLLECTION = new AnimCollection(
    STANDBY, GALLOP, EgoWeaponItems.MAGIC_BULLET
  );

  public MagicBulletWeaponItem(Properties properties, Builder builder, GeoModel<GeoRemoteEgoWeaponItem> model, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(properties, builder, model, guiModel);
  }

  public MagicBulletWeaponItem(Properties properties, Builder builder, String modPath) {
    super(properties, builder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
    if (usedHand != InteractionHand.MAIN_HAND) {
      return InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }
    return super.use(level, player, usedHand);
  }

  @Override
  public void aimAiming(@NotNull LivingEntity livingEntity, @NotNull ItemStack stack) {
    if (livingEntity instanceof AbstractClientPlayer player) {
      PlayerAnimUtil.playRawAnimation(player, PlayerAnimUtil.NORMAL_STATE, PlayerAnimRawAnimation.begin()
        .then(SHOOTING_AIM, Animation.LoopType.PLAY_ONCE)
        .thenLoop(SHOOTING_AIM_CYCLE), PlayerAnimUtil.getDefaultStandardFade());
    }
  }

  @Override
  public void endAimAiming(@NotNull LivingEntity livingEntity, @NotNull ItemStack stack) {
    if (livingEntity instanceof Player player) {
      PlayerAnimUtil.stop(player, PlayerAnimUtil.NORMAL_STATE, true);
    }
  }

  @Override
  public void endAiming(@NotNull LivingEntity livingEntity, @NotNull ItemStack stack) {
    if (livingEntity instanceof Player player) {
      PlayerAnimUtil.stop(player, PlayerAnimUtil.NORMAL_STATE, true);
    }
  }

  @Override
  public void leftClick(ItemStack stack, Player player) {
    float attackStrengthScale = player.getAttackStrengthScale(1.0F);
    if (attackStrengthScale < 0.4f) {
      IPlayer.of(player).setImaginarycraft$AttackStrengthTicker((int) (player.getAttributeValue(Attributes.ATTACK_SPEED) * 20.0));
      return;
    }
    leftClickAiming(player, stack);
    player.resetAttackStrengthTicker();
  }

  @Override
  public void leftClickAiming(@NotNull Player player, @NotNull ItemStack stack) {
    PlayerAnimUtil.playAnimation(player, PlayerAnimUtil.NORMAL_STATE, SHOOTING, getUseDuration(stack, player), PlayerAnimUtil.getDefaultStandardFade());
  }
}
