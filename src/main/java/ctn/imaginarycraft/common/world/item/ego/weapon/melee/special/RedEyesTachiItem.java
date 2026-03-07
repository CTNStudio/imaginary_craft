package ctn.imaginarycraft.common.world.item.ego.weapon.melee.special;

import ctn.imaginarycraft.api.world.item.*;
import ctn.imaginarycraft.client.renderer.item.*;
import ctn.imaginarycraft.client.renderer.providers.*;
import ctn.imaginarycraft.common.world.item.ego.weapon.melee.*;
import ctn.imaginarycraft.init.world.*;
import ctn.imaginarycraft.mixin.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.client.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.model.*;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.event.*;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.*;
import yesman.epicfight.world.capabilities.entitypatch.player.*;

import java.util.function.*;

public class RedEyesTachiItem extends MeleeEgoWeaponGeoItem {

  public RedEyesTachiItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, GeoModel<MeleeEgoWeaponGeoItem> geoModel, GeoModel<MeleeEgoWeaponGeoItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
    GeoItem.registerSyncedAnimatable(this);
  }

  public RedEyesTachiItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
    GeoItem.registerSyncedAnimatable(this);
  }

  public static void phaseSwitch() {
//    EpicFightEventHooks.Animation.BEGIN.registerEvent(event -> {
//      LivingEntityPatch<?> entityPatch = event.getEntityPatch();
//      AnimationManager.AnimationAccessor<? extends StaticAnimation> animation = event.getAnimation();
//    });
    EpicFightEventHooks.Player.TICK_EPICFIGHT_MODE.registerEvent(event -> {
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
    });
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
