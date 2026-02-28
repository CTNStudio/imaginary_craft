package ctn.imaginarycraft.common.item.ego.weapon.melee;

import ctn.imaginarycraft.api.DelayTaskHolder;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.IMeleeEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.MeleeEgoWeaponGeoItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.event.types.player.TickPlayerEpicFightModeEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class RedEyesTachiItem extends MeleeEgoWeaponGeoItem {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public RedEyesTachiItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, GeoModel<MeleeEgoWeaponGeoItem> geoModel, GeoModel<MeleeEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
    GeoItem.registerSyncedAnimatable(this);
  }

  public RedEyesTachiItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
    GeoItem.registerSyncedAnimatable(this);
  }

  public static void phaseSwitch(TickPlayerEpicFightModeEvent event) {
    PlayerPatch<?> playerPatch = event.getPlayerPatch();

    SkillContainer container = playerPatch.getSkill(SkillSlots.WEAPON_INNATE);
    Skill skill = container.getSkill();
    if (skill == null) {
      return;
    }

    EntityState entityState = playerPatch.getEntityState();
    if (!entityState.getState(EntityState.ATTACKING)) {
      return;
    }

    ItemStack itemStack = playerPatch.getValidItemInHand(InteractionHand.MAIN_HAND);
    if (!(itemStack.getItem() instanceof RedEyesTachiItem redEyesTachiItem)) {
      return;
    }
//    EpicFightEventHooks.Animation.END
    Player original = playerPatch.getOriginal();
    if (original.level() instanceof ServerLevel level) {
      redEyesTachiItem.phase1(itemStack, level);
      DelayTaskHolder.of(original).addTask(InteractionHand.MAIN_HAND, DelayTaskHolder.createTaskBilder()
        .removedRun(() -> redEyesTachiItem.phase(itemStack, level))
        .resultRun(() -> redEyesTachiItem.phase(itemStack, level))
        .removedTick(20 * 2)
        .build());
    }
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    AnimationController<RedEyesTachiItem> controller = new AnimationController<>(this, (state) -> PlayState.STOP);
    controller.triggerableAnim("phase1", RawAnimation.begin().thenPlay("phase1"));
    controllerRegistrar.add(controller);
  }

  public void phase(ItemStack stack, ServerLevel level) {
    getManager(stack, level).stopTriggeredAnimation("phase1");
  }

  public void phase1(ItemStack stack, ServerLevel level) {
    AnimationController<RedEyesTachiItem> controller = getController(stack, level);
    if (controller.getTriggeredAnimation() == null) {
      controller.tryTriggerAnimation("phase1");
    }
  }

  private AnimationController<RedEyesTachiItem> getController(ItemStack stack, ServerLevel level) {
    return getManager(stack, level).getAnimationControllers().get("base_controller");
  }

  private AnimatableManager<RedEyesTachiItem> getManager(ItemStack stack, ServerLevel level) {
    return getAnimatableInstanceCache().getManagerForId(GeoItem.getOrAssignId(stack, level));
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }
}
