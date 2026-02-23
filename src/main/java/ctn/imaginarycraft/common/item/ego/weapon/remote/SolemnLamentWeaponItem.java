package ctn.imaginarycraft.common.item.ego.weapon.remote;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.api.lobotomycorporation.util.LcLevelUtil;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GeoRemoteEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GunEgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModDamageSources;
import ctn.imaginarycraft.init.ModParticleTypes;
import ctn.imaginarycraft.init.ModSoundEvents;
import ctn.imaginarycraft.mixed.IDamageSource;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class SolemnLamentWeaponItem extends GunEgoWeaponItem {
  public static final ResourceLocation STANDBY = ImaginaryCraft.modRl("solemn_lament_weapon.standby");
  public static final ResourceLocation GALLOP = ImaginaryCraft.modRl("solemn_lament_weapon.gallop");
  public static final ResourceLocation SHOOTING = ImaginaryCraft.modRl("solemn_lament_weapon.shooting");
  public static final ResourceLocation SHOOTING1 = ImaginaryCraft.modRl("solemn_lament_weapon.shooting1");
  public static final ResourceLocation SHOOTING2 = ImaginaryCraft.modRl("solemn_lament_weapon.shooting2");
  public static final ResourceLocation SHOOTING3 = ImaginaryCraft.modRl("solemn_lament_weapon.shooting3");

  public static final ResourceLocation TWIN_STANDBY = ImaginaryCraft.modRl("solemn_lament_weapon.twin.standby");
  public static final ResourceLocation TWIN_GALLOP = ImaginaryCraft.modRl("solemn_lament_weapon.twin.gallop");
  public static final ResourceLocation TWIN_SHOOTING = ImaginaryCraft.modRl("solemn_lament_weapon.twin.shooting");
  public static final ResourceLocation TWIN_SHOOTING1 = ImaginaryCraft.modRl("solemn_lament_weapon.twin.shooting1");
  public static final ResourceLocation TWIN_SHOOTING2 = ImaginaryCraft.modRl("solemn_lament_weapon.twin.shooting2");
  public static final ResourceLocation TWIN_SHOOTING3 = ImaginaryCraft.modRl("solemn_lament_weapon.twin.shooting3");

  public SolemnLamentWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoRemoteEgoWeaponItem> geoModel, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public SolemnLamentWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }

  @Override
  public boolean gunShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    boolean isShoot = super.gunShoot(playerEntity, itemStack, handUsed);
    if (!isShoot || !(playerEntity instanceof ServerPlayer)) {
      return isShoot;
    }
    return true;
  }

  @Override
  public boolean isOffHandShoot(@NotNull Player player, @NotNull ItemStack stack) {
    return true;
  }

  @Override
  protected void notConsumingShoot(ServerLevel world, LivingEntity shooterEntity, InteractionHand handUsed, ItemStack weaponItem, float projectileVelocity, float projectileInaccuracy, @Nullable LivingEntity targetEntity) {
    boolean isBlack = false;
    HitResult hitResult = getHitResult(world, shooterEntity, handUsed, weaponItem);

    LcDamageType lcDamageColorDamageType = getLcDamageColorDamageType(weaponItem);
    if (hitResult instanceof EntityHitResult entityHitResult) {
      Entity entity = entityHitResult.getEntity();
      DamageSource damageSources = ModDamageSources.remoteDamage(shooterEntity);
      // 一般来说使用物品攻击时这些会自动添加，但是因为原版的机制导致副手攻击物品时可能无法正确识别，所以这里手动添加
      IDamageSource iDamageSource = IDamageSource.of(damageSources);
      iDamageSource.setImaginaryCraft$WeaponItem(weaponItem);
      iDamageSource.setImaginaryCraft$InvincibleTick(getInvincibleTick(weaponItem));
      iDamageSource.setImaginaryCraft$DamageLevel(LcLevelUtil.getLevel(weaponItem));
      iDamageSource.setImaginaryCraft$LcDamageType(lcDamageColorDamageType);
      entity.hurt(damageSources, getDamage(shooterEntity, weaponItem, handUsed));
    }

    if (lcDamageColorDamageType == LcDamageType.EROSION) {
      isBlack = true;
    }

    SimpleParticleType particleType = (isBlack ? ModParticleTypes.SOLEMN_LAMENT_BUTTERFLY_BLACK : ModParticleTypes.SOLEMN_LAMENT_BUTTERFLY_WHITE).get();
    Vec3 hitResultLocationPos = hitResult.getLocation();
    RandomSource random = shooterEntity.getRandom();

    Vec3 position = shooterEntity.position();
    SoundEvent soundEvent = (isBlack ? ModSoundEvents.SOLEMN_LAMENT_WEAPON_ATTACK_BLACK : ModSoundEvents.SOLEMN_LAMENT_WEAPON_ATTACK_WHITE).value();
    world.playSound(null,
      position.x,
      position.y,
      position.z,
      soundEvent,
      shooterEntity.getSoundSource(),
      0.1f + 1 * random.nextFloat(),
      0.1f + 1 * random.nextFloat());

    int count = Math.max(3, (int) (5 * random.nextFloat()));
    for (int i = 0; i < count; i++) {
      world.sendParticles(particleType,
        hitResultLocationPos.x,
        hitResultLocationPos.y,
        hitResultLocationPos.z,
        1,
        random.nextFloat() * 0.25,
        random.nextFloat() * 0.25,
        random.nextFloat() * 0.25,
        0);
    }
  }

  private @NotNull HitResult getHitResult(ServerLevel world, LivingEntity shooterEntity, InteractionHand handUsed, ItemStack weaponItem) {
    float distance = getProjectileRange(shooterEntity, weaponItem, handUsed);
    Vec3 from = shooterEntity.getEyePosition(1.0f);
    Vec3 vec31 = shooterEntity.getViewVector(1.0f);
    Vec3 to = from.add(vec31.x * distance, vec31.y * distance, vec31.z * distance);
    AABB aabb = shooterEntity.getBoundingBox().expandTowards(vec31.scale(distance)).inflate(1.0, 1.0, 1.0);

    HitResult hitResult = ProjectileUtil.getEntityHitResult(shooterEntity, from, to, aabb, entity -> !entity.isSpectator() && entity.isPickable(), Mth.square(distance));

    if (hitResult instanceof EntityHitResult entityHitResult) {
      Entity entity = entityHitResult.getEntity();
      if (!entity.isAlive() || entity.getUUID().equals(shooterEntity.getUUID())) {
        hitResult = null;
      }
    }

    if (hitResult == null || hitResult.getType() == HitResult.Type.MISS) {
      hitResult = world.clip(new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, shooterEntity));
    }

    return hitResult;
  }
}
