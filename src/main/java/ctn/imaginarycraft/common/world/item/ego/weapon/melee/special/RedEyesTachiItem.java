package ctn.imaginarycraft.common.world.item.ego.weapon.melee.special;

import ctn.imaginarycraft.api.world.item.IMeleeEgoWeaponItem;
import ctn.imaginarycraft.client.renderer.item.RedEyesTachiItemWeaponRenderer;
import ctn.imaginarycraft.client.renderer.providers.ModGeoItemRenderProvider;
import ctn.imaginarycraft.common.world.item.ego.weapon.melee.MeleeEgoWeaponGeoItem;
import ctn.imaginarycraft.init.world.ModMobEffects;
import ctn.imaginarycraft.mixin.ConditionalWeaponInnateSkillMixin;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.model.GeoModel;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.event.types.player.TickPlayerEpicFightModeEvent;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.BattojutsuSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.function.Consumer;

public class RedEyesTachiItem extends MeleeEgoWeaponGeoItem {

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

    if (!playerPatch.getServerAnimator().animationPlayer.getRealAnimation().get().in(((ConditionalWeaponInnateSkillMixin) battojutsuSkill).getAttackAnimations())) {
      return;
    }

    ItemStack itemStack = playerPatch.getValidItemInHand(InteractionHand.MAIN_HAND);
    if (!(itemStack.getItem() instanceof RedEyesTachiItem redEyesTachiItem)) {
      return;
    }
    original.addEffect(new MobEffectInstance(ModMobEffects.RED_EYES_HUNTING, 20 * 10));
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    controllerRegistrar.add(new AnimationController<>(this, (state) -> PlayState.STOP));
  }

  @Override
  public void createGeoRenderer(@NotNull Consumer<GeoRenderProvider> rendererConsumer) {
    rendererConsumer.accept(new ModGeoItemRenderProvider<>((GeoModel<RedEyesTachiItem>) (GeoModel<?>) model, (GeoModel<RedEyesTachiItem>) (GeoModel<?>) guiModel, RedEyesTachiItemWeaponRenderer::new));
  }
}
