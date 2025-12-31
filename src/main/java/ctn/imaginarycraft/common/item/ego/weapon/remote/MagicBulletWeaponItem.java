package ctn.imaginarycraft.common.item.ego.weapon.remote;

import com.zigythebird.playeranimcore.animation.Animation;
import ctn.imaginarycraft.api.client.playeranimcore.AnimCollection;
import ctn.imaginarycraft.api.client.playeranimcore.PlayerAnimRawAnimation;
import ctn.imaginarycraft.client.util.PlayerAnimUtil;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GeoRemoteEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GunEgoWeaponItem;
import ctn.imaginarycraft.common.payloads.entity.player.PlayerAnimationPayload;
import ctn.imaginarycraft.common.payloads.entity.player.PlayerRawAnimationPayload;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import ctn.imaginarycraft.util.GunWeaponUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

// TODO 禁止放到副手
// TODO 第一人称动画
// TODO 修复动画问题
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

  public static final PlayerAnimRawAnimation GUN_AIM_RAW_ANIMATION = PlayerAnimRawAnimation.begin()
    .then(SHOOTING_AIM, Animation.LoopType.PLAY_ONCE)
    .thenLoop(SHOOTING_AIM_CYCLE);
  public static final PlayerAnimRawAnimation GUN_AIM_SHOOT_RAW_ANIMATION = PlayerAnimRawAnimation.begin()
    .then(SHOOTING_AIM_LAUNCH, Animation.LoopType.PLAY_ONCE)
    .thenLoop(SHOOTING_AIM_CYCLE);

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
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player playerEntity, @NotNull InteractionHand handUsed) {
    if (handUsed != InteractionHand.MAIN_HAND) {
      return InteractionResultHolder.pass(playerEntity.getItemInHand(handUsed));
    }
    return super.use(world, playerEntity, handUsed);
  }

  @Override
  public boolean gunShootExecute(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    if (!(playerEntity.level() instanceof ServerLevel)) {
      return true;
    }
    super.gunShootExecute(playerEntity, itemStack, handUsed, chargeUpPercentage);
    PlayerAnimUtil.playAnimation(playerEntity, new PlayerAnimationPayload.Builder()
      .controllerId(PlayerAnimUtil.WEAPON_STATE)
      .animationId(SHOOTING)
      .withFade(PlayerAnimUtil.DEFAULT_FADE_IN));
    return true;
  }

  @Override
  public boolean gunAimShootExecute(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    if (!(playerEntity.level() instanceof ServerLevel)) {
      return true;
    }
    super.gunAimShootExecute(playerEntity, itemStack, handUsed, chargeUpPercentage);
    PlayerAnimUtil.playRawAnimation(playerEntity, new PlayerRawAnimationPayload.Builder()
      .controllerId(PlayerAnimUtil.WEAPON_STATE)
      .rawAnimation(GUN_AIM_SHOOT_RAW_ANIMATION)
      .withFade(PlayerAnimUtil.DEFAULT_FADE_IN));
    GunWeaponUtil.resetChargeUp(playerEntity);
    return true;
  }

  @Override
  public void gunAim(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    if (!(playerEntity instanceof ServerPlayer)) {
      return;
    }
    super.gunAim(playerEntity, itemStack);
    PlayerAnimUtil.playRawAnimation(playerEntity, new PlayerRawAnimationPayload.Builder()
//      .playSpeed()
      .controllerId(PlayerAnimUtil.WEAPON_STATE)
      .rawAnimation(GUN_AIM_RAW_ANIMATION)
      .withFade(PlayerAnimUtil.DEFAULT_FADE_IN));
  }

  @Override
  public float getProjectileInaccuracy(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    return 0;
  }

  @Override
  public boolean isGunAim(Player playerEntity, ItemStack itemStack) {
    return true;
  }

  @Override
  public boolean isGunAimMove(Player playerEntity, ItemStack itemStack) {
    return true;
  }

  @Override
  public void gunEndAim(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    if (!(playerEntity instanceof ServerPlayer)) {
      return;
    }
    PlayerAnimUtil.playAnimation(playerEntity, new PlayerAnimationPayload.Builder()
      .controllerId(PlayerAnimUtil.WEAPON_STATE)
      .animationId(SHOOTING_AIM_TERMINATE)
      .withFade(PlayerAnimUtil.DEFAULT_FADE_OUT));
  }
}
