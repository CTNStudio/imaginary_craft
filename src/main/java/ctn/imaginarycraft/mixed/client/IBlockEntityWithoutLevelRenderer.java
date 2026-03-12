package ctn.imaginarycraft.mixed.client;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.api.NoMixinException;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IBlockEntityWithoutLevelRenderer {
  static IBlockEntityWithoutLevelRenderer of(BlockEntityWithoutLevelRenderer renderer) {
    return renderer;
  }

  default void imaginarycraft$renderByItem(LivingEntity sourceLivingEntity, ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
    throw new NoMixinException();
  }

  default @Nullable LivingEntity imaginarycraft$getSourceLivingEntity() {
    throw new NoMixinException();
  }
}
