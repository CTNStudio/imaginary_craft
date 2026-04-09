package ctn.imaginarycraft.mixin.client.world;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.renderer.item.RedEyesTachiItemWeaponRenderer;
import ctn.imaginarycraft.mixed.client.IBlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

@Mixin(value = BlockEntityWithoutLevelRenderer.class, priority = 10000)
public abstract class BlockEntityWithoutLevelRendererMixin implements IBlockEntityWithoutLevelRenderer {
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

  @Shadow
  public abstract void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay);

  @Override
  public @Nullable LivingEntity imaginarycraft$getSourceLivingEntity() {
    return imaginarycraft$sourceLivingEntity;
  }
}
