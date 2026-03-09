package ctn.imaginarycraft.mixin.client;

import com.mojang.blaze3d.vertex.*;
import ctn.imaginarycraft.client.renderer.item.*;
import ctn.imaginarycraft.mixed.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import software.bernie.geckolib.animatable.client.*;

@Mixin(value = BlockEntityWithoutLevelRenderer.class, priority = 10000)
public abstract class BlockEntityWithoutLevelRendererMixin implements IBlockEntityWithoutLevelRenderer {
  @Shadow
  public abstract void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay);

  @Unique
  private @Nullable LivingEntity imaginarycraft$sourceLivingEntity;

  @Override
  public void imaginarycraft$renderByItem(LivingEntity sourceLivingEntity, ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
    imaginarycraft$sourceLivingEntity = sourceLivingEntity;

    final BlockEntityWithoutLevelRenderer geckolibRenderer = GeoRenderProvider.of(stack).getGeoItemRenderer();
    if (geckolibRenderer != null) {
      // TODO 扩展到更通用版本
      if (geckolibRenderer instanceof RedEyesTachiItemWeaponRenderer renderer) {
        renderer.renderByItem(sourceLivingEntity, stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        return;
      }
      geckolibRenderer.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
      return;
    } else {
      renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
    }

    imaginarycraft$sourceLivingEntity = null;
  }

  @Override
  public @Nullable LivingEntity getImaginarycraft$sourceLivingEntity() {
    return imaginarycraft$sourceLivingEntity;
  }
}
