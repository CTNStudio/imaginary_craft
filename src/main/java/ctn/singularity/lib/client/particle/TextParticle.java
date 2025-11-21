package ctn.singularity.lib.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import ctn.ctnapi.client.util.ColorUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.util.StringMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Map;

import static ctn.singularity.lib.api.lobotomycorporation.LcDamage.PHYSICS;
import static net.minecraft.world.damagesource.DamageTypes.GENERIC_KILL;

public class TextParticle extends TextureSheetParticle {
  private final Component textComponent;
  private final boolean isHeal;
  private final int fontColor;
  private final int strokeColor;
  private final float maxSize;
  private final float maxTick;

  protected TextParticle(final ClientLevel level,
                         final @Nullable TextureAtlasSprite sprite,
                         final double x,
                         final double y,
                         final double z,
                         final Component textComponent,
                         final int fontColor,
                         final int strokeColor,
                         final boolean isHeal) {
    super(level, x, y, z);
    setSprite(sprite);
    this.textComponent = textComponent;
    this.isHeal = isHeal;
    this.fontColor = fontColor;
    this.strokeColor = strokeColor;
    float maxSize = 0.05f;
    float maxTick = isHeal ? 20 : 20 * 3;
    this.maxSize = maxSize;
    this.maxTick = maxTick;
  }

  @Override
  public void render(@NotNull VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
    Minecraft minecraft = Minecraft.getInstance();
    Font font = minecraft.font;
    Vec3 camPos = camera.getPosition();
    int getLightColor = getLightColor(partialTicks);
    PoseStack poseStack = new PoseStack();
    float partialAge = this.age + partialTicks;
    float sizeFactor = Math.abs((float) Math.sin((partialAge / this.maxTick) * Math.PI));
    float size = sizeFactor * maxSize;
    int width = font.width(this.textComponent);
    int height = font.lineHeight;
    float x = -width / 2f;
    float y = -height / 2f;

    if (sprite != null) {
      RenderSystem.disableDepthTest();
      super.render(vertexConsumer, camera, partialTicks);
      RenderSystem.disableDepthTest();
    }

    double dx = this.x - camPos.x;
    double dy = Mth.lerp(partialTicks, yo, y) - camPos.y;
    double dz = this.z - camPos.z;

    poseStack.pushPose();

    poseStack.translate(dx, dy, dz);
    poseStack.translate(0, partialAge * 0.05f, 0);
    poseStack.mulPose(camera.rotation());
    poseStack.mulPose(Axis.XP.rotationDegrees(180));

    poseStack.scale(size, size, size);
    Matrix4f matrix = new Matrix4f(poseStack.last().pose());

    MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().crumblingBufferSource();
    renderStroke(font, x, y, minecraft, matrix, bufferSource, getLightColor);
    Matrix4f translate = matrix.translate(0, 0, -0.1f);
    font.drawInBatch(this.textComponent, x, y,
      this.fontColor, false, translate, bufferSource, Font.DisplayMode.NORMAL, this.strokeColor, getLightColor);

    poseStack.popPose();
  }

  @Override
  protected int getLightColor(final float partialTick) {
    return LightTexture.FULL_BRIGHT;
  }

  /**
   * 绘制描边
   */
  private void renderStroke(Font font, float oldX, float oldY, Minecraft minecraft, Matrix4f matrix, MultiBufferSource.BufferSource bufferSource, int getLightColor) {
    oldX -= 1;
    oldY -= 1;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (i == 1 && j == 1) {
          continue;
        }
        float x = oldX + j;
        float y = oldY + i;
        font.drawInBatch(this.textComponent, x, y,
          this.strokeColor, false, matrix, bufferSource, Font.DisplayMode.NORMAL, this.strokeColor, getLightColor);
      }
    }
  }

  @Override
  public void tick() {
    this.age++;
    if (this.age > this.maxTick) {
      remove();
    }
  }

  @Override
  public @NotNull ParticleRenderType getRenderType() {
    return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
  }
}
