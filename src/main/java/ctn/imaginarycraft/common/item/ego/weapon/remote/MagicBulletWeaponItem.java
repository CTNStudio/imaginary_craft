package ctn.imaginarycraft.common.item.ego.weapon.remote;

import ctn.imaginarycraft.client.eventexecute.PlayerEventAnimExecute;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GeoRemoteEgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

import java.util.function.Supplier;

public class MagicBulletWeaponItem extends GeoRemoteEgoWeaponItem {
  public static final ResourceLocation STANDBY = ImaginaryCraft.modRl("magic_bullet_weapon.standby");
  public static final ResourceLocation GALLOP = ImaginaryCraft.modRl("magic_bullet_weapon.gallop");
  public static final ResourceLocation SHOOTING = ImaginaryCraft.modRl("magic_bullet_weapon.shooting");
  public static final ResourceLocation SHOOTING_LAUNCH = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.launch");
  public static final ResourceLocation SHOOTING_AIM = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim");
  public static final ResourceLocation SHOOTING_AIM_CYCLE = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim.cycle");
  public static final ResourceLocation SHOOTING_AIM_LAUNCH = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.aim.launch");
  public static final ResourceLocation SHOOTING_CYCLE = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.cycle");
  public static final ResourceLocation SHOOTING_CYCLE_AIM = ImaginaryCraft.modRl("magic_bullet_weapon.shooting.cycle.aim");

  public static final Supplier<PlayerEventAnimExecute.AnimCollection> ANIM_COLLECTION = () -> new PlayerEventAnimExecute.AnimCollection(
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

}
