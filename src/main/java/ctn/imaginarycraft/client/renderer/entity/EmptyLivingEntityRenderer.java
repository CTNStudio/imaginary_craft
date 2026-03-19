package ctn.imaginarycraft.client.renderer.entity;

import ctn.imaginarycraft.client.model.entity.EmptyEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EmptyLivingEntityRenderer<T extends LivingEntity> extends LivingEntityRenderer<T, EmptyEntityModel<T>> {
  private final ResourceLocation texture;

  public EmptyLivingEntityRenderer(EntityRendererProvider.Context context, float shadowRadius, ResourceLocation texture) {
    super(context, new EmptyEntityModel<>(), shadowRadius);
    this.texture = texture;
  }

  @Override
  public @NotNull ResourceLocation getTextureLocation(@NotNull T entity) {
    return texture;
  }
}
