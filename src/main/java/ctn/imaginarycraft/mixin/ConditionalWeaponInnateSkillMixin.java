package ctn.imaginarycraft.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.skill.weaponinnate.*;
import yesman.epicfight.world.capabilities.entitypatch.player.*;

@Mixin(ConditionalWeaponInnateSkill.class)
public interface ConditionalWeaponInnateSkillMixin {
  @Accessor("attackAnimations")
  AnimationManager.AnimationAccessor<? extends AttackAnimation>[] getAttackAnimations();

  @Invoker("getAnimationInCondition")
  int imaginarycraft$getAnimationInCondition(ServerPlayerPatch serverPlayerPatch);
}
