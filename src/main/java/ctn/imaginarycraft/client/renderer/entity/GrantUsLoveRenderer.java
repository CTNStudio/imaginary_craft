package ctn.imaginarycraft.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import ctn.imaginarycraft.client.model.ModGeoEntityModel;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.GrantUsLove;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class GrantUsLoveRenderer extends GeoEntityRenderer<GrantUsLove> {
  private static final ResourceLocation GLOWMASK_TEXTURE = ModGeoEntityModel.getTexturePath("grant_us_love_glowmask");
  public static final RenderStateShard.TextureStateShard TEXTURE_STATE_SHARD = new RenderStateShard.TextureStateShard(GLOWMASK_TEXTURE, false, false);
  //  private final Map<String, Map.Entry<GeoBone, ClientTentacle>> tentacles = new HashMap<>();
  //=左触手//=
  // 上
  public GeoBone tentacleLeft1_0;
  public GeoBone tentacleLeft1_1;
  public GeoBone tentacleLeft1_2;
  // 中
  public GeoBone tentacleLeft2_0;
  public GeoBone tentacleLeft2_1;
  public GeoBone tentacleLeft2_2;
  // 下
  public GeoBone tentacleLeft3_0;
  public GeoBone tentacleLeft3_1;
  public GeoBone tentacleLeft3_2;
  //=右触手//=
  // 上
  public GeoBone tentacleRight1_0;
  public GeoBone tentacleRight1_1;
  public GeoBone tentacleRight1_2;
  // 中
  public GeoBone tentacleRight2_0;
  public GeoBone tentacleRight2_1;
  public GeoBone tentacleRight2_2;
  // 下
  public GeoBone tentacleRight3_0;
  public GeoBone tentacleRight3_1;
  public GeoBone tentacleRight3_2;

  public GrantUsLoveRenderer(EntityRendererProvider.Context context) {
    super(context, new ModGeoEntityModel<>("grant_us_love"));
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

    addTentecles();
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

  private void addTentecles() {
    addTentacle("tentacle_left1_0", 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f);
    addTentacle("tentacle_left2_0", 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f);
    addTentacle("tentacle_left3_0", 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f);

    addTentacle("tentacle_right1_0", 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f);
    addTentacle("tentacle_right2_0", 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f);
    addTentacle("tentacle_right3_0", 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f);
  }

  private void addTentacle(String name, float maxPitch, float minPitch, float maxYaw, float minYaw, float swingSpeed, float swingAmplitude) {
//    GeoBone bone = getGeoModel().getBone(name).orElseThrow();
//    tentacles.put(name, Map.entry(bone, new ClientTentacle(bone, maxPitch, minPitch, maxYaw, minYaw, swingSpeed, swingAmplitude)));
  }

  @Override
  public void preRender(PoseStack poseStack, GrantUsLove animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
    tentacleLeft1_0 = model.getBone("tentacleLeft1_0 ").orElse(null);
    tentacleLeft1_1 = model.getBone("tentacleLeft1_1 ").orElse(null);
    tentacleLeft1_2 = model.getBone("tentacleLeft1_2 ").orElse(null);

    tentacleLeft2_0 = model.getBone("tentacleLeft2_0 ").orElse(null);
    tentacleLeft2_1 = model.getBone("tentacleLeft2_1 ").orElse(null);
    tentacleLeft2_2 = model.getBone("tentacleLeft2_2 ").orElse(null);

    tentacleLeft3_0 = model.getBone("tentacleLeft3_0 ").orElse(null);
    tentacleLeft3_1 = model.getBone("tentacleLeft3_1 ").orElse(null);
    tentacleLeft3_2 = model.getBone("tentacleLeft3_2 ").orElse(null);

    tentacleRight1_0 = model.getBone("tentacleRight1_0").orElse(null);
    tentacleRight1_1 = model.getBone("tentacleRight1_1").orElse(null);
    tentacleRight1_2 = model.getBone("tentacleRight1_2").orElse(null);

    tentacleRight2_0 = model.getBone("tentacleRight2_0").orElse(null);
    tentacleRight2_1 = model.getBone("tentacleRight2_1").orElse(null);
    tentacleRight2_2 = model.getBone("tentacleRight2_2").orElse(null);

    tentacleRight3_0 = model.getBone("tentacleRight3_0").orElse(null);
    tentacleRight3_1 = model.getBone("tentacleRight3_1").orElse(null);
    tentacleRight3_2 = model.getBone("tentacleRight3_2").orElse(null);

    super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
  }
}
