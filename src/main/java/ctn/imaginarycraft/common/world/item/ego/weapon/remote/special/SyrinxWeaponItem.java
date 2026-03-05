package ctn.imaginarycraft.common.world.item.ego.weapon.remote.special;

import ctn.imaginarycraft.common.world.item.ego.weapon.remote.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.model.*;

public class SyrinxWeaponItem extends RemoteEgoWeaponGeoItem {

  public SyrinxWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<RemoteEgoWeaponGeoItem> geoModel, GeoModel<RemoteEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public SyrinxWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }


  @Override
  protected void shootProjectile(LivingEntity shooterEntity, Projectile projectileEntity, int projectileIndex, float projectileVelocity, float projectileInaccuracy, float shootingAngle, @Nullable LivingEntity targetEntity) {

  }
}
