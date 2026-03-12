package ctn.imaginarycraft.mixin.world.skill;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import ctn.imaginarycraft.mixed.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.skill.common.*;
import yesman.epicfight.world.capabilities.entitypatch.player.*;
import yesman.epicfight.world.capabilities.item.*;

import java.util.*;

@Mixin(ComboAttacks.class)
public abstract class ComboAttacksMixin {
  @WrapOperation(method = "executeOnServer", at = @At(value = "INVOKE",
    target = "Lyesman/epicfight/world/capabilities/item/CapabilityItem;getMountAttackMotion()Ljava/util/List;"))
  public List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getImaginarycraft$autoAttackMotion(
    CapabilityItem instance,
    Operation<List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> original,
    @Local(name = "executor") ServerPlayerPatch executor,
    @Local(name = "cap") CapabilityItem cap
  ) {
    return cap instanceof IWeaponCapability weaponCapability ? weaponCapability.imaginaryCraft$getMountAttackMotion(executor) : original.call(instance);
  }
}
