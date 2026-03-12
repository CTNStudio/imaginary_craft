package ctn.imaginarycraft.mixin.world.skill;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.skill.weaponinnate.*;
import yesman.epicfight.world.capabilities.entitypatch.player.*;

@Mixin(ConditionalWeaponInnateSkill.class)
public interface ConditionalWeaponInnateSkillAccessorMixin {
  @Accessor("attackAnimations")
  AnimationManager.AnimationAccessor<? extends AttackAnimation>[] getAttackAnimations();

  @Invoker("getAnimationInCondition")
  int getAnimationInCondition(ServerPlayerPatch serverPlayerPatch);
}
