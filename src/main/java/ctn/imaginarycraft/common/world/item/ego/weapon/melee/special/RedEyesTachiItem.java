package ctn.imaginarycraft.common.world.item.ego.weapon.melee.special;

import ctn.imaginarycraft.api.world.item.*;
import ctn.imaginarycraft.client.renderer.item.*;
import ctn.imaginarycraft.client.renderer.providers.*;
import ctn.imaginarycraft.common.world.item.ego.weapon.melee.*;
import ctn.imaginarycraft.init.world.*;
import ctn.imaginarycraft.mixin.world.entity.*;
import ctn.imaginarycraft.mixin.world.skill.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
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

      if (!playerPatch.getServerAnimator().animationPlayer.getRealAnimation().get().in(((ConditionalWeaponInnateSkillAccessorMixin) battojutsuSkill).getAttackAnimations())) {
        return;
      }

      ItemStack itemStack = playerPatch.getValidItemInHand(InteractionHand.MAIN_HAND);
      if (!(itemStack.getItem() instanceof RedEyesTachiItem)) {
        return;
      }
      // TODO EGO共鸣后改成 400
      original.addEffect(new MobEffectInstance(ModMobEffects.RED_EYES_HUNTING, 200));
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

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    MobEffectInstance effect = attacker.getEffect(ModMobEffects.RED_EYES_HUNTING);
    if (effect != null) {
      return true;
    }
    return super.hurtEnemy(stack, target, attacker);
  }

  @Override
  public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    MobEffectInstance effect = attacker.getEffect(ModMobEffects.RED_EYES_HUNTING);
    if (effect != null) {
      int effectDuration = effect.getDuration();
      // TODO EGO共鸣后改成 20
      int increase = 10;
      // TODO EGO共鸣后改成 200
      int max = 100;
      effect.duration = Math.clamp(effectDuration + increase, 0, Math.max(effectDuration, max));
      ((LivingEntityAccessorMixin) attacker).onEffectUpdated(effect, true, attacker);
    }
  }
}
