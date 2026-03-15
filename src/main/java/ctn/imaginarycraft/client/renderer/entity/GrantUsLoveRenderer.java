package ctn.imaginarycraft.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import ctn.imaginarycraft.client.model.ModGeoEntityModel;
import ctn.imaginarycraft.common.world.entity.abnormalities.ordeals.violet.GrantUsLove;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class GrantUsLoveRenderer extends GeoEntityRenderer<GrantUsLove> {
  private static final ResourceLocation GLOWMASK_TEXTURE = ModGeoEntityModel.getTexturePath("grant_us_love_glowmask");
  public static final RenderStateShard.TextureStateShard TEXTURE_STATE_SHARD = new RenderStateShard.TextureStateShard(GLOWMASK_TEXTURE, false, false);

  public GrantUsLoveRenderer(EntityRendererProvider.Context context) {
    super(context, new ModGeoEntityModel<>("grant_us_love"));
    this.shadowRadius = 1.5f;
    addRenderLayer(new AutoGlowingGeoLayer<>(this) {
      @Override
      @NotNull
      protected RenderType getRenderType(GrantUsLove animatable, @Nullable MultiBufferSource bufferSource) {
        long cycleTime = System.currentTimeMillis() % 10000;
        float radians = (cycleTime / 2500.0f * (float) Math.PI * 2);
        float value = cycle(0.9f, 2f, Math.sin(radians));
        return RenderType.create("imaginarycraft:glowmask",
          DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, false, true,
          RenderType.CompositeState.builder()
            .setColorLogicState(getColorLogicState(value))
            .setShaderState(RenderType.RENDERTYPE_EYES_SHADER)
            .setTextureState(getTextureState())
            .setTransparencyState(RenderStateShard.ADDITIVE_TRANSPARENCY)
            .setWriteMaskState(RenderType.COLOR_WRITE)
            .createCompositeState(true));
      }

      @Override
      public void render(PoseStack poseStack, GrantUsLove animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer vertexConsumer, float partialTick, int packedLight, int packedOverlay) {
        renderType = getRenderType(animatable, bufferSource);
        var vertexConsumer1 = bufferSource.getBuffer(renderType);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, renderType,
          vertexConsumer1, partialTick, LightTexture.FULL_BRIGHT, packedOverlay,
          getRenderer().getRenderColor(animatable, partialTick, packedLight).argbInt());
      }
    });
  }

  private static RenderStateShard.TextureStateShard getTextureState() {
    return TEXTURE_STATE_SHARD;
  }

  private static RenderStateShard.ColorLogicStateShard getColorLogicState(float value) {
    return new RenderStateShard.ColorLogicStateShard("imaginarycraft:glowmask",
      getRunnable(value),
      () -> RenderSystem.setShaderColor(1, 1, 1, 1));
  }

  private static Runnable getRunnable(float value) {
    return () -> {
      RenderSystem.disableColorLogicOp();
      RenderSystem.setShaderColor(value, value, value, 1);
    };
  }

  private float cycle(float min, float max, double sin) {
    return (float) (min + (1 + sin) * (max - min) / 2);
  }
}
