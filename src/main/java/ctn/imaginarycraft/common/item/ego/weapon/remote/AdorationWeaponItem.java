package ctn.imaginarycraft.common.item.ego.weapon.remote;

import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GeoRemoteEgoWeaponItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

public class AdorationWeaponItem extends GeoRemoteEgoWeaponItem {

  public AdorationWeaponItem(Properties properties, Builder builder, GeoModel<GeoRemoteEgoWeaponItem> model, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(properties, builder, model, guiModel);
  }

  public AdorationWeaponItem(Properties properties, Builder builder, String modPath) {
    super(properties, builder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }

  @Override
  protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {

  }
}
