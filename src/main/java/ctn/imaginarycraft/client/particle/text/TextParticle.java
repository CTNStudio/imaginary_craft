package ctn.imaginarycraft.client.particle.text;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.imaginarycraft.client.ModParticleRenderTypes;
import ctn.imaginarycraft.init.ModParticleTypes;
import ctn.imaginarycraft.network.codec.CompositeStreamCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO 拆分成伤害，BOSS说话文本，普通文本
// TODO 待修复显示问题
public class TextParticle extends TextureSheetParticle {
  protected final Minecraft minecraft;
  protected final Font font;
  protected final Options options;
  protected final Font.DisplayMode displayMode;
  protected final int strokeColor;
  protected final int fontColor;
  protected final float baseSize;
  protected float size;
  protected float xRot;
  protected float yRot;

  protected TextParticle(ClientLevel level, double x, double y, double z, Options options) {
    super(level, x, y, z);
    this.options = options;
    this.xRot = options.xRot;
    this.yRot = options.yRot;
    this.baseSize = options.size;
    size = baseSize;
    this.quadSize = options.size;
    setSize(options.size, options.size);
    setLifetime(options.particleLifeTime);
    this.minecraft = Minecraft.getInstance();
    this.font = this.minecraft.font;
    this.fontColor = options.fontColor;
    this.strokeColor = options.strokeColor;
    this.displayMode = options.isThrough ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL;
  }

  @Override
  public void render(@NotNull VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
    size = quadSize;

    int textListSize = options.textComponent.size();
    if (textListSize == 0) {
      return;
    }

    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
    if (options.isThrough) {
      RenderSystem.disableDepthTest();
    }
    RenderSystem.disableCull();
    RenderSystem.setShaderColor(1, 1, 1, alpha);
    Vec3 camPos = camera.getPosition();
    int getLightColor = getLightColor(partialTicks);
    Font font = this.font;
    int fontHeight = font.lineHeight;
    StringSplitter splitter = font.getSplitter();

    PoseStack poseStack = new PoseStack();
    poseStack.pushPose();
    poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
    poseStack.translate(getX(partialTicks), getY(partialTicks), getZ(partialTicks));
    if (options.isTargetingPlayers) {
      poseStack.mulPose(camera.rotation());
      poseStack.mulPose(Axis.XP.rotationDegrees(180));
    } else {
      poseStack.mulPose(Axis.YP.rotationDegrees(this.yRot));
      poseStack.mulPose(Axis.XP.rotationDegrees(this.xRot));
    }
    poseStack.scale(size, size, size);
    float textY = textListSize * -(fontHeight / 2f + 1);

    poseStack.pushPose();
    var bufferSource = this.minecraft.renderBuffers().bufferSource();
    for (Component component : this.options.textComponent) {
      float fontWidth = splitter.stringWidth(component);
      float textX = switch (this.options.alignType) {
        case LEFT -> 0;
        case CENTER -> -fontWidth / 2f;
        case RIGHT -> fontWidth;
      };
      renderText(component, font, textX, textY, poseStack, bufferSource, getLightColor);
      textY -= -(fontHeight / 2f + 1);
    }
    poseStack.popPose();
    poseStack.popPose();
    bufferSource.endBatch();
    RenderSystem.setShaderColor(1, 1, 1, 1);
    if (options.isThrough) {
      RenderSystem.enableDepthTest();
    }
    RenderSystem.enableCull();
  }

  private void drawInBatch(Component text, Font font, float textX, float textY, Matrix4f matrix, MultiBufferSource bufferSource, int getLightColor, int fontColor) {
    font.drawInBatch(text, textX, textY, fontColor, false, matrix, bufferSource, displayMode, 0, getLightColor);
  }

  protected void renderText(Component text, Font font, float textX, float textY, PoseStack poseStack, MultiBufferSource bufferSource, int getLightColor) {
    poseStack.pushPose();
    renderStroke(text, font, textX, textY, poseStack, bufferSource, getLightColor, strokeColor);
    drawInBatch(text, font, textX, textY, poseStack.last().pose(), bufferSource, getLightColor, fontColor);
    poseStack.popPose();
  }

  protected void renderStroke(Component text, Font font, float textX, float textY, PoseStack poseStack, MultiBufferSource bufferSource, int getLightColor, int strokeColor) {
    poseStack.pushPose();
    switch (options.strokeType()) {
      case SHADOW -> {
        poseStack.translate(1, 1, 1.5);
        drawInBatch(text, font, textX, textY, poseStack.last().pose(), bufferSource, getLightColor, strokeColor);
      }
      case STROKE -> {
        poseStack.translate(0, 0, 1.5);
        float[][] directions = {
          {-1, 1}, {0, 1}, {1, 1},
          {-1, 0}, {1, 0},
          {-1, -1}, {0, -1}, {1, -1}
        };

        for (float[] dir : directions) {
          poseStack.pushPose();
          poseStack.translate(dir[0], dir[1], 0);
          drawInBatch(text, font, textX, textY, poseStack.last().pose(), bufferSource, getLightColor, strokeColor);
          poseStack.popPose();
        }
      }
    }
    poseStack.popPose();
  }

  protected double getX(float partialTicks) {
    return Mth.lerp(partialTicks, this.xo, this.x);
  }

  protected double getY(float partialTicks) {
    return Mth.lerp(partialTicks, this.yo, this.y);
  }

  protected double getZ(float partialTicks) {
    return Mth.lerp(partialTicks, this.zo, this.z);
  }

  @Override
  protected int getLightColor(float partialTick) {
    return options.isShine ? LightTexture.FULL_BRIGHT : super.getLightColor(partialTick);
  }

  @Override
  public @NotNull ParticleRenderType getRenderType() {
    if (options.isThrough) {
      return ModParticleRenderTypes.TEXT_PARTICLE_THROUGH;
    }
    return ModParticleRenderTypes.TEXT_PARTICLE;
  }

  public enum StrokeType implements StringRepresentable {
    /**
     * 无描边
     */
    NONE(0, "none"),
    /**
     * 阴影
     */
    SHADOW(1, "shadow"),
    /**
     * 描边
     */
    STROKE(2, "stroke");

    public static Codec<StrokeType> CODEC = StringRepresentable
      .fromEnum(StrokeType::values).validate(DataResult::success);
    public static StreamCodec<ByteBuf, StrokeType> STREAM_CODEC = ByteBufCodecs
      .idMapper(ByIdMap.continuous(StrokeType::getIndex, values(), ByIdMap.OutOfBoundsStrategy.WRAP), StrokeType::getIndex);
    private final int index;
    private final String name;

    StrokeType(int index, String name) {
      this.index = index;
      this.name = name;
    }

    public int getIndex() {
      return index;
    }

    public String getName() {
      return name;
    }

    @Override
    public @NotNull String getSerializedName() {
      return name;
    }
  }

  public enum AlignType implements StringRepresentable {
    /**
     * 左对齐
     */
    LEFT(0, "left"),
    /**
     * 居中对齐
     */
    CENTER(1, "center"),
    /**
     * 右对齐
     */
    RIGHT(2, "right");
    public static final Codec<AlignType> CODEC = StringRepresentable
      .fromEnum(AlignType::values).validate(DataResult::success);
    public static final StreamCodec<ByteBuf, AlignType> STREAM_CODEC = ByteBufCodecs
      .idMapper(ByIdMap.continuous(AlignType::getIndex, values(), ByIdMap.OutOfBoundsStrategy.WRAP), AlignType::getIndex);
    private final int index;
    private final String name;

    AlignType(int index, String name) {
      this.index = index;
      this.name = name;
    }

    public int getIndex() {
      return index;
    }

    public String getName() {
      return name;
    }

    @Override
    public @NotNull String getSerializedName() {
      return name;
    }
  }

  public static class Builder {
    protected List<Component> textComponent = new ArrayList<>();
    protected int fontColor = 0xffffff;
    protected int strokeColor = 0xafafafaf;
    /**
     * 持续时间
     */
    protected int particleLifeTime = 20 * 3;
    /**
     * 文字大小
     */
    protected float size = 0.02f;
    /**
     * 文字对齐方式
     */
    protected AlignType alignType = AlignType.CENTER;
    /**
     * 是否发光
     */
    protected boolean isShine;
    /**
     * 阴影类型
     */
    protected StrokeType strokeType;
    protected float xRot;
    protected float yRot;
    /**
     * 是否面向玩家
     */
    protected boolean isTargetingPlayers;
    /**
     * 是否顶层渲染
     */
    protected boolean isSeeThrough;

    private Builder(
      List<Component> textComponent,
      int fontColor,
      int strokeColor,
      int particleLifeTime,
      float size,
      AlignType alignType,
      boolean isShine,
      StrokeType strokeType,
      float xRot,
      float yRot,
      boolean isTargetingPlayers,
      boolean isSeeThrough
    ) {
      this.textComponent = textComponent;
      this.fontColor = fontColor;
      this.strokeColor = strokeColor;
      this.particleLifeTime = particleLifeTime;
      this.size = size;
      this.alignType = alignType;
      this.isShine = isShine;
      this.strokeType = strokeType;
      this.xRot = xRot;
      this.yRot = yRot;
      this.isTargetingPlayers = isTargetingPlayers;
      this.isSeeThrough = isSeeThrough;
    }

    public Builder() {
    }

    public Builder addTextComponent(Component... textComponent) {
      this.textComponent.addAll(Arrays.stream(textComponent).toList());
      return this;
    }

    public Builder setTextComponent(Component... textComponent) {
      this.textComponent = List.of(textComponent);
      return this;
    }

    /**
     * 文字对齐方式
     */
    public Builder align(AlignType alignType) {
      this.alignType = alignType;
      return this;
    }

    /**
     * 文字颜色
     */
    public Builder fontColor(int fontColor) {
      this.fontColor = fontColor;
      return this;
    }

    /**
     * 描边颜色
     */
    public Builder strokeColor(int strokeColor) {
      this.strokeColor = strokeColor;
      return this;
    }

    /**
     * 粒子持续时间
     */
    public Builder particleLifeTime(int particleLifeTime) {
      this.particleLifeTime = particleLifeTime;
      return this;
    }

    /**
     * 描边类型
     */
    public Builder strokeType(StrokeType strokeType) {
      this.strokeType = strokeType;
      return this;
    }

    /**
     * 是否发光
     */
    public Builder shine(boolean isShine) {
      this.isShine = isShine;
      return this;
    }

    /**
     * 是否顶层渲染
     */
    public Builder seeThrough(boolean isSeeThrough) {
      this.isSeeThrough = isSeeThrough;
      return this;
    }

    public Builder size(float size) {
      this.size = size;
      return this;
    }

    public Builder xRot(float xRot) {
      this.xRot = xRot;
      return this;
    }

    public Builder yRot(float yRot) {
      this.yRot = yRot;
      return this;
    }

    public Builder targetingPlayers(boolean targetingPlayers) {
      this.isTargetingPlayers = targetingPlayers;
      return this;
    }

    public Options buildOptions() {
      return new Options(
        textComponent,
        fontColor,
        strokeColor,
        particleLifeTime,
        size,
        alignType,
        isShine,
        strokeType,
        xRot,
        yRot,
        isTargetingPlayers,
        isSeeThrough
      );
    }
  }

  public record Options(
    List<Component> textComponent,
    int fontColor,
    int strokeColor,
    int particleLifeTime,
    float size,
    AlignType alignType,
    boolean isShine,
    StrokeType strokeType,
    float xRot,
    float yRot,
    boolean isTargetingPlayers,
    boolean isThrough
  ) implements ParticleOptions {
    public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec((thisOptionsInstance) -> thisOptionsInstance.group(
      Codec.list(ComponentSerialization.CODEC).fieldOf("textComponentList").forGetter(Options::textComponent),
      Codec.INT.fieldOf("fontColor").forGetter(Options::fontColor),
      Codec.INT.fieldOf("strokeColor").forGetter(Options::strokeColor),
      Codec.INT.fieldOf("particleLifeTime").forGetter(Options::particleLifeTime),
      Codec.FLOAT.fieldOf("size").forGetter(Options::size),
      AlignType.CODEC.fieldOf("align").forGetter(Options::alignType),
      Codec.BOOL.fieldOf("isShine").forGetter(Options::isShine),
      StrokeType.CODEC.fieldOf("strokeType").forGetter(Options::strokeType),
      Codec.FLOAT.fieldOf("xRot").forGetter(Options::xRot),
      Codec.FLOAT.fieldOf("yRot").forGetter(Options::yRot),
      Codec.BOOL.fieldOf("isTargetingPlayers").forGetter(Options::isTargetingPlayers),
      Codec.BOOL.fieldOf("isSeeThrough").forGetter(Options::isThrough)
    ).apply(thisOptionsInstance, Options::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Options> STREAM_CODEC = CompositeStreamCodecBuilder.<RegistryFriendlyByteBuf, Options>builder()
      .withComponent(ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs.list()), Options::textComponent)
      .withComponent(ByteBufCodecs.INT, Options::fontColor)
      .withComponent(ByteBufCodecs.INT, Options::strokeColor)
      .withComponent(ByteBufCodecs.INT, Options::particleLifeTime)
      .withComponent(ByteBufCodecs.FLOAT, Options::size)
      .withComponent(AlignType.STREAM_CODEC, Options::alignType)
      .withComponent(ByteBufCodecs.BOOL, Options::isShine)
      .withComponent(StrokeType.STREAM_CODEC, Options::strokeType)
      .withComponent(ByteBufCodecs.FLOAT, Options::xRot)
      .withComponent(ByteBufCodecs.FLOAT, Options::yRot)
      .withComponent(ByteBufCodecs.BOOL, Options::isTargetingPlayers)
      .withComponent(ByteBufCodecs.BOOL, Options::isThrough)
      .decoderFactory(components -> new Options(
        (List<Component>) components.next(),
        (int) components.next(),
        (int) components.next(),
        (int) components.next(),
        (float) components.next(),
        (AlignType) components.next(),
        (boolean) components.next(),
        (StrokeType) components.next(),
        (float) components.next(),
        (float) components.next(),
        (boolean) components.next(),
        (boolean) components.next()
      )).build();

    public Builder getBuild() {
      return new Builder(
        this.textComponent,
        this.fontColor,
        this.strokeColor,
        this.particleLifeTime,
        this.size,
        this.alignType,
        this.isShine,
        this.strokeType,
        this.xRot,
        this.yRot,
        this.isTargetingPlayers,
        this.isThrough
      );
    }

    public TextParticle buildParticle(ClientLevel level, double x, double y, double z) {
      return new TextParticle(level, x, y, z, this);
    }

    @Override
    public @NotNull ParticleType<Options> getType() {
      return ModParticleTypes.TEXT.get();
    }
  }

  public static class Provider implements ParticleProvider<Options> {
    @Override
    @NotNull
    public Particle createParticle(@NotNull TextParticle.Options type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      return type.buildParticle(level, x, y, z);
    }
  }
}
