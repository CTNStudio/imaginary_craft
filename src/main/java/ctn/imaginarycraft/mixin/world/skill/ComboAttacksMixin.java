package ctn.imaginarycraft.mixin.world.skill;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import ctn.imaginarycraft.mixed.IWeaponCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.common.ComboAttacks;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

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
