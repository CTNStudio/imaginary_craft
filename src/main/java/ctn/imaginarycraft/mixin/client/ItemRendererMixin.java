package ctn.imaginarycraft.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import com.mojang.blaze3d.vertex.*;
import ctn.imaginarycraft.mixed.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
  @Unique
  private @Nullable LivingEntity imaginarycraft$sourceLivingEntity;

  @WrapOperation(method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V"))
  private void imaginarycraft$renderStatic(
    ItemRenderer instance,
    ItemStack itemStack,
    ItemDisplayContext diplayContext,
    boolean leftHand,
    PoseStack poseStack,
    MultiBufferSource bufferSource,
    int combinedLight,
    int combinedOverlay,
    BakedModel bakedmodel,
    Operation<Void> original,
    @Local(type = LivingEntity.class, argsOnly = true) LivingEntity livingEntity
  ) {
    imaginarycraft$sourceLivingEntity = livingEntity;
    original.call(instance, itemStack, diplayContext, leftHand, poseStack, bufferSource, combinedLight, combinedOverlay, bakedmodel);
    imaginarycraft$sourceLivingEntity = null;
  }

  @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockEntityWithoutLevelRenderer;renderByItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"))
  private void imaginarycraft$render(
    BlockEntityWithoutLevelRenderer instance,
    ItemStack itemStack,
    ItemDisplayContext displayContext,
    PoseStack poseStack,
    MultiBufferSource bufferSource,
    int combinedLight,
    int combinedOverlay,
    Operation<Void> original
  ) {
    if (instance instanceof IBlockEntityWithoutLevelRenderer renderer) {
      renderer.imaginarycraft$renderByItem(imaginarycraft$sourceLivingEntity, itemStack, displayContext, poseStack, bufferSource, combinedLight, combinedOverlay);
      return;
    }
    original.call(instance, itemStack, displayContext, poseStack, bufferSource, combinedLight, combinedOverlay);
  }
}
