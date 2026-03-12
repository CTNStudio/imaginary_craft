package ctn.imaginarycraft.mixed.client;

import com.mojang.blaze3d.vertex.*;
import ctn.imaginarycraft.api.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

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
