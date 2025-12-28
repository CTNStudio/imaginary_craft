package ctn.imaginarycraft.common.item.ego.weapon.remote;

import com.zigythebird.playeranimcore.animation.Animation;
import ctn.imaginarycraft.api.client.playeranimcore.AnimCollection;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimRawAnimation;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GeoRemoteEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GunEgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import ctn.imaginarycraft.util.ChargeUpUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
  public static final ResourceLocation SHOOTING_AIM = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim");
  public static final ResourceLocation SHOOTING_AIM_CYCLE = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim.cycle");
  public static final ResourceLocation SHOOTING_AIM_LAUNCH = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim.launch");
  public static final ResourceLocation SHOOTING_AIM_TERMINATE = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim.terminate");
  public static final ResourceLocation SHOOTING_AIM_CHARGEUP = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim.chargeup");
  public static final ResourceLocation SHOOTING_CYCLE = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.cycle");

  @SuppressWarnings("unchecked")
  public static final AnimCollection ANIM_COLLECTION = new AnimCollection(
    STANDBY, GALLOP, EgoWeaponItems.MAGIC_BULLET
  );

  public MagicBulletWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoRemoteEgoWeaponItem> geoModel, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public MagicBulletWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }

  @Override
  public boolean isAim(Player playerEntity, ItemStack itemStack) {
    return true;
  }

  @Override
  public boolean isAimMove(Player playerEntity, ItemStack itemStack) {
    return true;
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player playerEntity, @NotNull InteractionHand handUsed) {
    if (handUsed != InteractionHand.MAIN_HAND) {
      return InteractionResultHolder.fail(playerEntity.getItemInHand(handUsed));
    }
    return super.use(world, playerEntity, handUsed);
  }

  @Override
  public void aim(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    if (playerEntity instanceof AbstractClientPlayer) {
      return;
    }
    PlayerAnimUtil.playRawAnimation(playerEntity, PlayerAnimUtil.NORMAL_STATE, PlayerAnimRawAnimation.begin()
      .then(SHOOTING_AIM, Animation.LoopType.PLAY_ONCE)
      .thenLoop(SHOOTING_AIM_CYCLE), PlayerAnimUtil.DEFAULT_FADE_IN);
  }

  @Override
  public void endAim(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    if (playerEntity instanceof AbstractClientPlayer) {
      return;
    }
    PlayerAnimUtil.playAnimation(playerEntity, PlayerAnimUtil.NORMAL_STATE, SHOOTING_AIM_TERMINATE, PlayerAnimUtil.DEFAULT_FADE_OUT);
  }

  @Override
  public void end(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    if (playerEntity instanceof AbstractClientPlayer) {
      return;
    }
    PlayerAnimUtil.playAnimation(playerEntity, PlayerAnimUtil.NORMAL_STATE, SHOOTING_AIM_TERMINATE, PlayerAnimUtil.DEFAULT_FADE_OUT);
  }

  @Override
  public boolean shoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    if (!super.shoot(playerEntity, itemStack, handUsed)) {
      return false;
    }
    if (playerEntity.level() instanceof ServerLevel serverLevel) {
      ChargeUpUtil.reset(playerEntity);
      PlayerAnimUtil.playAnimation(playerEntity, PlayerAnimUtil.NORMAL_STATE, SHOOTING,
        PlayerAnimUtil.DEFAULT_FADE_IN);
    }
    return true;
  }

  @Override
  public boolean aimShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    float attackStrengthScale = ChargeUpUtil.getPercentage(playerEntity);
    if (attackStrengthScale < 0.3) {
      return false;
    }
    if (playerEntity.level() instanceof ServerLevel serverLevel) {
      shoot(playerEntity, itemStack, handUsed, serverLevel);
      ChargeUpUtil.reset(playerEntity);
      PlayerAnimUtil.playRawAnimation(playerEntity, PlayerAnimUtil.NORMAL_STATE, PlayerAnimRawAnimation.begin()
        .then(SHOOTING_AIM_LAUNCH, Animation.LoopType.PLAY_ONCE)
        .thenLoop(SHOOTING_AIM_CYCLE), PlayerAnimUtil.DEFAULT_FADE_IN);
    }
    return true;
  }
}
