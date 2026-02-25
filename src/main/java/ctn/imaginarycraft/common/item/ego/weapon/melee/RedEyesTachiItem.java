package ctn.imaginarycraft.common.item.ego.weapon.melee;

import ctn.imaginarycraft.common.item.ego.weapon.template.melee.IMeleeEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.MeleeEgoWeaponGeoItem;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;

public class RedEyesTachiItem extends MeleeEgoWeaponGeoItem {

  public RedEyesTachiItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, GeoModel<MeleeEgoWeaponGeoItem> geoModel, GeoModel<MeleeEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public RedEyesTachiItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    AnimationController<RedEyesTachiItem> controller = new AnimationController<>(this, (state) -> PlayState.STOP);
    controller.triggerableAnim("phase1", RawAnimation.begin().thenPlay("phase1"));
    controllerRegistrar.add(controller);
  }

  public void phase(ItemStack stack) {
    getManager(stack).stopTriggeredAnimation("phase1");
  }

  public void phase1(ItemStack stack) {
    AnimationController<RedEyesTachiItem> controller = getController(stack);
    if (controller.getTriggeredAnimation() == null) {
      controller.tryTriggerAnimation("phase1");
    }
  }

  private void setAnimation(ItemStack stack, RawAnimation rawAnimation) {
    getController(stack).setAnimation(rawAnimation);
  }

  private AnimationController<RedEyesTachiItem> getController(ItemStack stack) {
    return getManager(stack).getAnimationControllers().get("base_controller");
  }

  private AnimatableManager<RedEyesTachiItem> getManager(ItemStack stack) {
    return getAnimatableInstanceCache().getManagerForId(GeoItem.getId(stack));
  }
}
