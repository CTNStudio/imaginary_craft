package ctn.imaginarycraft.client.particle.text;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

//@EventBusSubscriber(value = Dist.CLIENT)
public class DamageNumberRenderer {

  private static final Map<Integer, DamageNumberRenderInfo> damageNumbers = new HashMap<>();
  private static int nextId = 0;

  public static int registerDamageNumber(String text, int damageTypeID, double x, double y, double z, float scale, double isCrit, int lifetime) {
    int id = nextId++;
    damageNumbers.put(id, new DamageNumberRenderInfo(text, damageTypeID, x, y, z, scale, isCrit, lifetime));
    return id;
  }

  public static void removeDamageNumber(int id) {
    damageNumbers.remove(id);
  }

  public static void updateDamageNumber(int id, double x, double y, double z, float alpha) {
    DamageNumberRenderInfo info = damageNumbers.get(id);
    if (info != null) {
      info.updatePosition(x, y, z);
      info.alpha = alpha;
    }
  }

  //  @SubscribeEvent
  public static void onClientTick(ClientTickEvent.Post event) {
    damageNumbers.values().forEach(DamageNumberRenderInfo::tick);
    damageNumbers.entrySet().removeIf(entry -> entry.getValue().shouldRemove());
  }

  //  @SubscribeEvent
  public static void onRenderLevelLast(RenderLevelStageEvent event) {
    if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
      return;
    }

    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft.level == null || minecraft.player == null) {
      return;
    }

    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
    RenderSystem.disableDepthTest();
    RenderSystem.depthMask(false);
    RenderSystem.disableCull();

    renderDamageNumbers(event.getPoseStack(), minecraft, event.getPartialTick().getGameTimeDeltaPartialTick(true));

    RenderSystem.depthMask(true);
    RenderSystem.enableDepthTest();
    RenderSystem.enableCull();
  }

  private static void renderDamageNumbers(PoseStack poseStack, Minecraft minecraft, float partialTick) {
    Camera camera = minecraft.gameRenderer.getMainCamera();
    Vec3 cameraPos = camera.getPosition();

    MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();

    for (DamageNumberRenderInfo info : damageNumbers.values()) {
      renderDamageNumber(poseStack, minecraft, camera, cameraPos, bufferSource, info, partialTick);
    }

    bufferSource.endBatch();
  }

  private static void renderDamageNumber(
    PoseStack poseStack,
    Minecraft minecraft,
    Camera camera,
    Vec3 cameraPos,
    MultiBufferSource.BufferSource bufferSource,
    DamageNumberRenderInfo info,
    float partialTick
  ) {
    double x = Mth.lerp(partialTick, info.prevX, info.x);
    double y = Mth.lerp(partialTick, info.prevY, info.y);
    double z = Mth.lerp(partialTick, info.prevZ, info.z);

    LocalPlayer localPlayer = Minecraft.getInstance().player;
    if (localPlayer == null) return;
    double dx = x - localPlayer.xo;
    double dy = y - localPlayer.yo;
    double dz = z - localPlayer.zo;
    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

    if (distance > 100) return;

    poseStack.pushPose();
    poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
    poseStack.translate(x, y, z);
    poseStack.mulPose(Axis.YP.rotationDegrees(-camera.getYRot()));
    poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
    poseStack.translate(0, 0, 0.001f);

    float baseScale = 0.02f;
    float distanceScale = 1.0f + (float) distance * 0.3f;
    float finalScale = baseScale * info.scale * distanceScale;

    float minScale = 0.3f;
    if (finalScale < baseScale * minScale) {
      finalScale = baseScale * minScale;
    }

    float maxScale = 5.0f;
    if (finalScale > baseScale * maxScale) {
      finalScale = baseScale * maxScale;
    }

    renderIconBeforeText(poseStack, minecraft, info, finalScale);
    poseStack.scale(-finalScale, -finalScale, finalScale);
    Font font = minecraft.font;
    String displayText = info.text;
    int textWidth = font.width(displayText);
    float xOffset = -textWidth / 2.0f;

    int infoColor = /*AdamUtil.getDamageColorBasedOnWhetherTtIsCritical(info.damageTypeID, info.isCrit > 0)*/0;
    int alpha = (int) (info.alpha * 255);
    int color = FastColor.ARGB32.color(alpha, FastColor.ARGB32.red(infoColor), FastColor.ARGB32.green(infoColor), FastColor.ARGB32.blue(infoColor));

    renderOutline(poseStack, bufferSource, info.text, font, xOffset, 0, alpha);
    poseStack.translate(0, 0, -0.8);
    font.drawInBatch(Component.literal(displayText).withStyle(style -> style.withBold(true)), xOffset, 0, color, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, 15728880);

    poseStack.popPose();
  }

  private static void renderIconBeforeText(PoseStack poseStack, Minecraft minecraft, DamageNumberRenderInfo info, float finalScale) {
    ResourceLocation texture = /*AdamUtil.getDamageTypeTexture(info.damageTypeID)*/null;
    if (texture == null) {
      return;
    }

    Font font = minecraft.font;
    float lineHeight = font.lineHeight;
    float iconSize = lineHeight * 1.2f;
    int textWidth = font.width(info.text);
    float iconOffset = (textWidth / 2.0f) + iconSize + 2.0f;
    float x = -iconOffset;
    float y = -iconSize / 4;

    poseStack.pushPose();
    poseStack.scale(-finalScale, -finalScale, finalScale);

    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, texture);
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, info.alpha);

    Matrix4f matrix = poseStack.last().pose();

    Tesselator tesselator = Tesselator.getInstance();
    BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
    bufferBuilder.addVertex(matrix, x, y, 0.0f).setUv(0, 0);
    bufferBuilder.addVertex(matrix, x + iconSize, y, 0.0f).setUv(1.0f, 0);
    bufferBuilder.addVertex(matrix, x + iconSize, y + iconSize, 0.0f).setUv(1.0f, 1);
    bufferBuilder.addVertex(matrix, x, y + iconSize, 0.0f).setUv(0.0f, 1);
    BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());

    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    poseStack.popPose();
  }

  private static void renderOutline(PoseStack poseStack, MultiBufferSource bufferSource, String text, Font font, float x, float y, int alpha) {
    int outlineAlpha = (int) (alpha * 0.7f);  // 描边略微透明
    int outlineColorWithAlpha = FastColor.ARGB32.color(outlineAlpha, FastColor.ARGB32.red(0x000000), FastColor.ARGB32.green(0x000000), FastColor.ARGB32.blue(0x000000));
    float outlineWidth = 1.0f;
    // 8方向描边
    float[][] directions = {
      {-outlineWidth, 0},  // 左
      {outlineWidth, 0},   // 右
      {0, -outlineWidth},  // 上
      {0, outlineWidth},   // 下
      {-outlineWidth * 0.707f, -outlineWidth * 0.707f},  // 左上
      {outlineWidth * 0.707f, -outlineWidth * 0.707f},   // 右上
      {-outlineWidth * 0.707f, outlineWidth * 0.707f},   // 左下
      {outlineWidth * 0.707f, outlineWidth * 0.707f}     // 右下
    };

    for (float[] dir : directions) {
      font.drawInBatch(Component.literal(text).withStyle(style -> style.withBold(true)), x + dir[0], y + dir[1], outlineColorWithAlpha, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, 15728880);
    }
  }

  public static class DamageNumberRenderInfo {
    public final String text;
    public final int damageTypeID;
    public double x, y, z;
    public float alpha = 1.0f;
    public float scale;
    public double isCrit;
    public int age = 0;
    public int lifetime;
    public double prevX, prevY, prevZ;

    public DamageNumberRenderInfo(String text, int damageTypeID, double x, double y, double z, float scale, double isCrit, int lifetime) {
      this.text = text;
      this.damageTypeID = damageTypeID;
      this.x = x;
      this.y = y;
      this.z = z;
      this.prevX = x;
      this.prevY = y;
      this.prevZ = z;
      this.scale = scale;
      this.isCrit = isCrit;
      this.lifetime = lifetime;
    }

    public void updatePosition(double newX, double newY, double newZ) {
      this.prevX = this.x;
      this.prevY = this.y;
      this.prevZ = this.z;
      this.x = newX;
      this.y = newY;
      this.z = newZ;
    }

    public void tick() {
      age++;
      if (age > lifetime - 10) {
        alpha = 1.0f - (age - (lifetime - 10)) / 10.0f;
      }
    }

    public boolean shouldRemove() {
      return age >= lifetime;
    }
  }
}
