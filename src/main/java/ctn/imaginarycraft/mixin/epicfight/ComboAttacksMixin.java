package ctn.imaginarycraft.mixin.epicfight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ctn.imaginarycraft.mixed.IWeaponCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.common.ComboAttacks;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

@Mixin(ComboAttacks.class)
public abstract class ComboAttacksMixin {
	@WrapOperation(method = "executeOnServer", at = @At(value = "INVOKE",
		target = "Lyesman/epicfight/world/capabilities/item/CapabilityItem;getMountAttackMotion(Lyesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch;)Ljava/util/List;"))
	public List<AnimationManager.AnimationAccessor<? extends AttackAnimation>> getImaginarycraft$autoAttackMotion(
		CapabilityItem instance,
		PlayerPatch<?> playerPatch,
		Operation<List<AnimationManager.AnimationAccessor<? extends AttackAnimation>>> original
	) {
		return instance instanceof IWeaponCapability weaponCapability ? weaponCapability.imaginaryCraft$getMountAttackMotion(playerPatch) : original.call(instance, playerPatch);
	}
}
