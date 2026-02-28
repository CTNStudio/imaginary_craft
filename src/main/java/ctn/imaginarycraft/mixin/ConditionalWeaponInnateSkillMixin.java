package ctn.imaginarycraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.weaponinnate.ConditionalWeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mixin(ConditionalWeaponInnateSkill.class)
public interface ConditionalWeaponInnateSkillMixin {
  @Accessor("attackAnimations")
  AnimationManager.AnimationAccessor<? extends AttackAnimation>[] getAttackAnimations();

  @Invoker("getAnimationInCondition")
  int imaginarycraft$getAnimationInCondition(ServerPlayerPatch serverPlayerPatch);
}
