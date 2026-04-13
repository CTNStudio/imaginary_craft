package ctn.imaginarycraft.common.world.item.ego.weapon.melee.special;

import ctn.imaginarycraft.api.world.item.IMeleeEgoWeaponItem;
import ctn.imaginarycraft.client.gui.hudlayers.chop_flavor.ChopFlavorLayer;
import ctn.imaginarycraft.client.renderer.item.RedEyesTachiItemWeaponRenderer;
import ctn.imaginarycraft.client.renderer.providers.ModGeoItemRenderProvider;
import ctn.imaginarycraft.common.world.item.ego.weapon.melee.MeleeEgoWeaponGeoItem;
import ctn.imaginarycraft.init.world.ModMobEffects;
import ctn.imaginarycraft.mixin.epicfight.ConditionalWeaponInnateSkillAccessorMixin;
import ctn.imaginarycraft.mixin.world.entity.LivingEntityAccessorMixin;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.model.GeoModel;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.BattojutsuSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.function.Consumer;

// TODO 补充特效
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
    EpicFightEventHooks.Player.CAST_SKILL.registerContextAwareEvent((event, eventContext) -> {
      if (!FMLEnvironment.dist.isClient()) {
        return;
      }
      PlayerPatch<?> playerPatch = event.getPlayerPatch();
      ItemStack itemStack = playerPatch.getValidItemInHand(InteractionHand.MAIN_HAND);
      if (!(itemStack.getItem() instanceof RedEyesTachiItem)) {
        return;
      }
      SkillContainer skill = playerPatch.getSkill(SkillSlots.WEAPON_INNATE);
      if (skill == null) {
        return;
      }
      if (!event.getSkillContainer().equals(skill)) {
        return;
      }
      ChopFlavorLayer.INSTANCE.castSkill();
    });
    EpicFightEventHooks.Player.TICK_EPICFIGHT_MODE.registerEvent(event -> {
      PlayerPatch<?> playerPatch = event.getPlayerPatch();
      Player original = playerPatch.getOriginal();
      if (!(original.level() instanceof ServerLevel)) {
        return;
      }
      ItemStack itemStack = playerPatch.getValidItemInHand(InteractionHand.MAIN_HAND);
      if (!(itemStack.getItem() instanceof RedEyesTachiItem)) {
        return;
      }
      if (!playerPatch.getEntityState().getState(EntityState.ATTACKING)) {
        return;
      }
      if (!(playerPatch.getSkill(SkillSlots.WEAPON_INNATE).getSkill() instanceof BattojutsuSkill battojutsuSkill)) {
        return;
      }
      if (!playerPatch.getServerAnimator().animationPlayer.getRealAnimation().get().in(((ConditionalWeaponInnateSkillAccessorMixin) battojutsuSkill).imaginarycraft$getAttackAnimations())) {
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
      ((LivingEntityAccessorMixin) attacker).imaginarycraft$onEffectUpdated(effect, true, attacker);
    }
  }
}
