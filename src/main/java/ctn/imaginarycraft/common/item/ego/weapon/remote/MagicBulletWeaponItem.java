package ctn.imaginarycraft.common.item.ego.weapon.remote;

import com.zigythebird.playeranimcore.animation.Animation;
import ctn.imaginarycraft.api.ILivingEntity;
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
  public boolean isAim(Player player, ItemStack itemStack) {
    return true;
  }

  @Override
  public boolean isAimMove(Player player, ItemStack itemStack) {
    return true;
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand) {
    if (usedHand != InteractionHand.MAIN_HAND) {
      return InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }
    return super.use(level, player, usedHand);
  }

  @Override
  public void aim(@NotNull Player player, @NotNull ItemStack stack) {
    if (player instanceof AbstractClientPlayer clientPlayer) {
      return;
    }
    PlayerAnimUtil.playRawAnimation(player, PlayerAnimUtil.NORMAL_STATE, PlayerAnimRawAnimation.begin()
      .then(SHOOTING_AIM, Animation.LoopType.PLAY_ONCE)
      .thenLoop(SHOOTING_AIM_CYCLE), PlayerAnimUtil.DEFAULT_FADE_IN);
  }

  @Override
  public boolean aimShoot(@NotNull Player player, @NotNull ItemStack stack) {
    float attackStrengthScale = getAttackStrengthScale(player);
    if (attackStrengthScale < 0.4f) {
      return false;
    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
      return true;
    }
    player.resetAttackStrengthTicker();
    ILivingEntity.of(player).setImaginarycraft$AttackStrengthTicker(0);
    PlayerAnimUtil.playRawAnimation(player, PlayerAnimUtil.NORMAL_STATE, PlayerAnimRawAnimation.begin()
      .then(SHOOTING_AIM_LAUNCH, Animation.LoopType.PLAY_ONCE)
      .thenLoop(SHOOTING_AIM_CYCLE), PlayerAnimUtil.DEFAULT_FADE_IN);
    return true;
  }

  @Override
  public void endAim(@NotNull Player player, @NotNull ItemStack stack) {
    if (player instanceof AbstractClientPlayer clientPlayer) {
      return;
    }
    PlayerAnimUtil.playAnimation(player, PlayerAnimUtil.NORMAL_STATE, SHOOTING_AIM_TERMINATE, PlayerAnimUtil.DEFAULT_FADE_OUT);
  }

  @Override
  public void end(@NotNull Player player, @NotNull ItemStack stack) {
    if (player instanceof AbstractClientPlayer clientPlayer) {
      return;
    }
    PlayerAnimUtil.playAnimation(player, PlayerAnimUtil.NORMAL_STATE, SHOOTING_AIM_TERMINATE, PlayerAnimUtil.DEFAULT_FADE_OUT);
  }

  private float getAttackStrengthScale(Player player) {
    return player.getAttackStrengthScale(1.0F);
  }

  @Override
  public boolean shoot(@NotNull Player player, @NotNull ItemStack stack, @NotNull InteractionHand usedHand) {
    float attackStrengthScale = getAttackStrengthScale(player);
//    if (attackStrengthScale < 0.4f) {
//      ILivingEntity.of(player).setImaginarycraft$AttackStrengthTicker((int) (player.getAttributeValue(Attributes.ATTACK_SPEED) * 20.0));
//      return false;
//    }
    if (player instanceof AbstractClientPlayer clientPlayer) {
      return true;
    }
//    player.resetAttackStrengthTicker();
    PlayerAnimUtil.playAnimation(player, PlayerAnimUtil.NORMAL_STATE, SHOOTING,
      PlayerAnimUtil.DEFAULT_FADE_IN);
    return true;
  }
}
