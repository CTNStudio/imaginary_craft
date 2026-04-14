package ctn.imaginarycraft.mixin.epicfight.client;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.api.world.entity.INoRender;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.events.engine.RenderEngine;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(RenderEngine.class)
public abstract class RenderEngineMixin {

	@Inject(method = "renderEntityArmatureModel", at = @At("HEAD"), cancellable = true)
	private void imaginarycraft$renderEntityArmatureModel(LivingEntity livingEntity, LivingEntityPatch<?> entitypatch, EntityRenderer<? extends Entity> renderer, MultiBufferSource buffer, PoseStack matStack, int packedLight, float partialTicks, CallbackInfo ci) {
		if (livingEntity instanceof INoRender) {
			ci.cancel();
		}
	}
}
