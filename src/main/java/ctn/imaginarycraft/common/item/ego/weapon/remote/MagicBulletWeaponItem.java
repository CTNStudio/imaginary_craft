package ctn.imaginarycraft.common.item.ego.weapon.remote;

import ctn.imaginarycraft.common.entity.projectile.MagicBulletEntity;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GunEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.RemoteEgoWeaponGeoItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.GunWeaponUtil;
import ctn.imaginarycraft.util.PiercingUtil;
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

  public MagicBulletWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<RemoteEgoWeaponGeoItem> geoModel, GeoModel<RemoteEgoWeaponGeoItem> guiModel) {
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
  public boolean gunShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    boolean isShoot = super.gunShoot(playerEntity, itemStack, handUsed);
    if (isShoot && playerEntity instanceof ServerPlayer) {
    }
    return isShoot;
  }

  @Override
  public boolean gunAimShootExecute(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    if (!(playerEntity.level() instanceof ServerLevel)) {
      return true;
    }
    super.gunAimShootExecute(playerEntity, itemStack, handUsed, chargeUpPercentage);
    GunWeaponUtil.resetChargeUp(playerEntity, handUsed);
    return true;
  }

  @Override
  public void gunAim(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    super.gunAim(playerEntity, itemStack, handUsed);
    if (playerEntity instanceof ServerPlayer) {
    }
  }

  @Override
  public boolean isGunAim(Player playerEntity, ItemStack itemStack) {
    return true;
  }

  @Override
  public void gunEndAim(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    if (!(playerEntity instanceof ServerPlayer)) {
      return;
    }
    super.gunEndAim(playerEntity, itemStack, handUsed);
  }

  @Override
  public int gunShootExecuteTick(@NotNull Player player, @NotNull ItemStack stack, @NotNull InteractionHand handUsed) {
    return super.gunShootExecuteTick(player, stack, handUsed);
  }
  @Override
  protected ProjectileFactory getProjectileFactory() {
      return (level, shooter, itemStack, handUsed) -> {
          MagicBulletEntity magicBullet = new MagicBulletEntity(level, shooter);
          float damage = getDamage(shooter, itemStack, handUsed);
          magicBullet.setDamage(damage);

          // 添加默认穿透标签，使其可以穿墙
          PiercingUtil.setPiercingDefault(magicBullet, damage);

          return magicBullet;
      };
  }
}
