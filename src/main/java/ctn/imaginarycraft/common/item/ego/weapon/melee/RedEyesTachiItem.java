package ctn.imaginarycraft.common.item.ego.weapon.melee;

import ctn.imaginarycraft.api.DelayTaskHolder;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.IMeleeEgoWeaponItem;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.MeleeEgoWeaponGeoItem;
import ctn.imaginarycraft.mixin.ConditionalWeaponInnateSkillMixin;
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
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.BattojutsuSkill;
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
    Player original = playerPatch.getOriginal();
    if (!(original.level() instanceof ServerLevel level)) {
      return;
    }

    if (!playerPatch.getEntityState().getState(EntityState.ATTACKING)) {
      return;
    }

    if (!(playerPatch.getSkill(SkillSlots.WEAPON_INNATE).getSkill() instanceof BattojutsuSkill battojutsuSkill)) {
      return;
    }

    if (!playerPatch.getServerAnimator().animationPlayer.getRealAnimation().get()
      .in(((ConditionalWeaponInnateSkillMixin) battojutsuSkill).getAttackAnimations())) {
      return;
    }

    ItemStack itemStack = playerPatch.getValidItemInHand(InteractionHand.MAIN_HAND);
    if (!(itemStack.getItem() instanceof RedEyesTachiItem redEyesTachiItem)) {
      return;
    }

    redEyesTachiItem.phase1(itemStack, level);
    DelayTaskHolder.of(original).addTask(InteractionHand.MAIN_HAND, DelayTaskHolder.createTaskBilder()
      .removedRun(() -> redEyesTachiItem.phase(itemStack, level))
      .resultRun(() -> redEyesTachiItem.phase(itemStack, level))
      .removedTick(20 * 2)
      .build());
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    AnimationController<RedEyesTachiItem> controller = new AnimationController<>(this, (state) -> PlayState.STOP);
    controller.triggerableAnim("phase1", RawAnimation.begin().thenPlay("phase1"));
    controller.triggerableAnim("phase", RawAnimation.begin().thenPlay("phase"));
    controllerRegistrar.add(controller);
  }

  public void phase(ItemStack stack, ServerLevel level) {
    tryTriggerAnimation(stack, level, "phase");
  }

  public void phase1(ItemStack stack, ServerLevel level) {
    tryTriggerAnimation(stack, level, "phase1");
  }

  private void tryTriggerAnimation(ItemStack stack, ServerLevel level, String phase) {
    AnimationController<RedEyesTachiItem> controller = getController(stack, level);
    if (controller.getTriggeredAnimation() == null) {
      controller.tryTriggerAnimation(phase);
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
