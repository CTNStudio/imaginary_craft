package ctn.imaginarycraft.client.particle.text;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
import net.minecraft.client.renderer.texture.TextureManager;
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

import java.util.List;

// TODO 拆分成伤害，BOSS说话文本，普通文本
public class TextParticle extends TextureSheetParticle {
  public static final ParticleRenderType RENDER_TYPE = new ParticleRenderType() {
    @Override
    public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
      RenderSystem.depthMask(true);
      RenderSystem.disableBlend();
      return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public String toString() {
      return "TEXT_PARTICLE";
    }
  };
  protected final Minecraft minecraft;
  protected final Font font;

  protected final List<Component> textComponentList;
  protected final int fontColor;
  protected final int shadowColor;
  protected final float size;
  /**
   * 文字对齐方式
   */
  protected final AlignType align;
  protected final float xRot;
  protected final float yRot;
  /**
   * 是否面向玩家
   */
  protected final boolean isTargetingPlayers;
  /**
   * 是否有阴影
   */
  protected final boolean isShadow;
  /**
   * 是否穿透
   */
  protected final boolean isSeeThrough;

  protected TextParticle(final ClientLevel level, final double x, final double y, final double z, Builder builder) {
    super(level, x, y, z);
    setLifetime(builder.particleLifeTime);
    this.minecraft = Minecraft.getInstance();
    this.font = this.minecraft.font;
    this.textComponentList = builder.textComponent;
    this.fontColor = builder.fontColor;
    this.shadowColor = builder.strokeColor;
    this.size = builder.size;
    this.align = builder.align;
    this.xRot = builder.xRot;
    this.yRot = builder.yRot;
    this.isTargetingPlayers = builder.isTargetingPlayers;
    this.isShadow = builder.isShadow;
    this.isSeeThrough = builder.isSeeThrough;
  }

  @Override
  public void render(@NotNull VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
    int textListSize = textComponentList.size();
    if (textListSize == 0) {
      return;
    }

    Vec3 camPos = camera.getPosition();
    //  TODO 后续添加正常光照
    int getLightColor = getLightColor(partialTicks);
    Font font = this.font;
    int fontHeight = font.lineHeight;
    StringSplitter splitter = font.getSplitter();

    PoseStack poseStack = new PoseStack();
    poseStack.pushPose();
    double x1 = getX(partialTicks) - camPos.x;
    double y1 = getY(partialTicks) - camPos.y;
    double z1 = getZ(partialTicks) - camPos.z;
    poseStack.translate(x1, y1, z1);
    poseStack.scale(this.size, this.size, this.size);
    if (this.isTargetingPlayers) {
      poseStack.mulPose(camera.rotation());
      poseStack.mulPose(Axis.XP.rotationDegrees(180));
    } else {
      poseStack.mulPose(Axis.XP.rotationDegrees(this.xRot));
      poseStack.mulPose(Axis.YP.rotationDegrees(this.yRot));
    }
    float textY = textListSize * -(fontHeight / 2f + 1);

    var bufferSource = this.minecraft.renderBuffers().outlineBufferSource();
    for (Component component : this.textComponentList) {
      float fontWidth = splitter.stringWidth(component);
      float textX = switch (this.align) {
        case LEFT -> 0;
        case CENTER -> -fontWidth / 2f;
        case RIGHT -> fontWidth;
      };
      poseStack.pushPose();
      renderText(component, font, textX, textY, poseStack.last().pose(), bufferSource, getLightColor);
      poseStack.popPose();
      textY -= -(fontHeight / 2f + 1);
    }
    bufferSource.endOutlineBatch();
    poseStack.popPose();
  }

  protected void renderText(Component text, final Font font, final float textX, final float textY, final Matrix4f matrix, MultiBufferSource bufferSource, final int getLightColor) {
    if (this.isShadow) {
      renderTextShadow(font, textX + 1, textY + 1, matrix, bufferSource, getLightColor, text);
    }
    int fontColor = this.fontColor;
    renderText(text, font, textX, textY, matrix, bufferSource, getLightColor, fontColor);
  }

  protected void renderTextShadow(final Font font, final float textX, final float textY, final Matrix4f matrix, final MultiBufferSource bufferSource, final int getLightColor, final Component text) {
    int shadowColor = this.shadowColor;
    renderText(text, font, textX, textY, new Matrix4f(matrix).translate(0, 0, 0.1f), bufferSource, getLightColor, shadowColor);
  }

  protected void renderText(final Component text, final Font font, final float textX, final float textY, final Matrix4f matrix, final MultiBufferSource bufferSource, final int getLightColor, final int fontColor) {
    font.drawInBatch(text, textX, textY, fontColor, false, matrix, bufferSource, Font.DisplayMode.NORMAL, fontColor, getLightColor);
    if (this.isSeeThrough) {
      font.drawInBatch(text, textX, textY, fontColor, false, matrix, bufferSource, Font.DisplayMode.SEE_THROUGH, fontColor, getLightColor);
      font.drawInBatch(text, textX, textY, fontColor, false, matrix, bufferSource, Font.DisplayMode.SEE_THROUGH, fontColor, getLightColor);
    }
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
  protected int getLightColor(final float partialTick) {
    return LightTexture.FULL_BRIGHT;
  }

  @Override
  public @NotNull ParticleRenderType getRenderType() {
    return RENDER_TYPE;
  }

  public static class Builder {
    protected List<Component> textComponent;
    protected int fontColor = 0xffffff;
    protected int strokeColor = 0xafafafaf;
    protected int particleLifeTime = 20 * 3;
    protected float size = 0.02f;
    protected AlignType align = AlignType.LEFT;
    protected float xRot;
    protected float yRot;
    protected boolean isTargetingPlayers;
    protected boolean isShadow;
    protected boolean isSeeThrough;

    private Builder(final List<Component> textComponent, final int fontColor, final int strokeColor, final int particleLifeTime,
                    final float size, final AlignType align, final float xRot, final float yRot, final boolean isTargetingPlayers,
                    final boolean isShadow, final boolean isSeeThrough
    ) {
      this.textComponent = textComponent;
      this.fontColor = fontColor;
      this.strokeColor = strokeColor;
      this.particleLifeTime = particleLifeTime;
      this.size = size;
      this.align = align;
      this.xRot = xRot;
      this.yRot = yRot;
      this.isTargetingPlayers = isTargetingPlayers;
      this.isShadow = isShadow;
      this.isSeeThrough = isSeeThrough;
    }

    public Builder(Component... textComponent) {
      this.textComponent = List.of(textComponent);
    }

    public Builder textComponent(Component... textComponent) {
      this.textComponent = List.of(textComponent);
      return this;
    }

    public Builder align(AlignType type) {
      this.align = type;
      return this;
    }

    public Builder fontColor(int fontColor) {
      this.fontColor = fontColor;
      return this;
    }

    public Builder strokeColor(int strokeColor) {
      this.strokeColor = strokeColor;
      return this;
    }

    public Builder particleLifeTime(int particleLifeTime) {
      this.particleLifeTime = particleLifeTime;
      return this;
    }

    public Builder shadow() {
      this.isShadow = true;
      return this;
    }

    public Builder shadow(boolean shadow) {
      this.isShadow = shadow;
      return this;
    }

    public Builder seeThrough() {
      this.isSeeThrough = true;
      return this;
    }

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

    public Builder targetingPlayers() {
      this.isTargetingPlayers = true;
      return this;
    }

    public Builder targetingPlayers(boolean targetingPlayers) {
      this.isTargetingPlayers = targetingPlayers;
      return this;
    }

    public TextParticle buildParticle(final ClientLevel level, final double x, final double y, final double z) {
      return new TextParticle(level, x, y, z, this);
    }

    public Options buildOptions() {
      return new Options(this.textComponent, this.fontColor, this.strokeColor, this.particleLifeTime,
        this.size, this.align, this.xRot, this.yRot, this.isTargetingPlayers, this.isShadow, this.isSeeThrough);
    }
  }

  public enum AlignType implements StringRepresentable {
    LEFT(0, "left"),
    CENTER(1, "center"),
    RIGHT(2, "right"),
    ;
    private final int index;
    private final String name;

    public static final Codec<AlignType> CODEC = StringRepresentable
      .fromEnum(AlignType::values).validate(DataResult::success);
    public static final StreamCodec<ByteBuf, AlignType> STREAM_CODEC = ByteBufCodecs
      .idMapper(ByIdMap.continuous(AlignType::getIndex, values(), ByIdMap.OutOfBoundsStrategy.WRAP), AlignType::getIndex);

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

  public record Options(List<Component> textComponent, int fontColor, int strokeColor,
                        int particleLifeTime, float size, AlignType align, float xRot,
                        float yRot, boolean isTargetingPlayers, boolean isShadow,
                        boolean isSeeThrough) implements ParticleOptions {
    public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec((thisOptionsInstance) -> thisOptionsInstance.group(
      Codec.list(ComponentSerialization.CODEC).fieldOf("textComponentList").forGetter(Options::textComponent),
      Codec.INT.fieldOf("fontColor").forGetter(Options::fontColor),
      Codec.INT.fieldOf("strokeColor").forGetter(Options::strokeColor),
      Codec.INT.fieldOf("particleLifeTime").forGetter(Options::particleLifeTime),
      Codec.FLOAT.fieldOf("size").forGetter(Options::size),
      AlignType.CODEC.fieldOf("align").forGetter(Options::align),
      Codec.FLOAT.fieldOf("xRot").forGetter(Options::xRot),
      Codec.FLOAT.fieldOf("yRot").forGetter(Options::yRot),
      Codec.BOOL.fieldOf("isTargetingPlayers").forGetter(Options::isTargetingPlayers),
      Codec.BOOL.fieldOf("shadow").forGetter(Options::isShadow),
      Codec.BOOL.fieldOf("isSeeThrough").forGetter(Options::isSeeThrough)
    ).apply(thisOptionsInstance, Options::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Options> STREAM_CODEC = CompositeStreamCodecBuilder.<RegistryFriendlyByteBuf, Options>builder()
      .withComponent(ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs.list()), Options::textComponent)
      .withComponent(ByteBufCodecs.INT, Options::fontColor)
      .withComponent(ByteBufCodecs.INT, Options::strokeColor)
      .withComponent(ByteBufCodecs.INT, Options::particleLifeTime)
      .withComponent(ByteBufCodecs.FLOAT, Options::size)
      .withComponent(AlignType.STREAM_CODEC, Options::align)
      .withComponent(ByteBufCodecs.FLOAT, Options::xRot)
      .withComponent(ByteBufCodecs.FLOAT, Options::yRot)
      .withComponent(ByteBufCodecs.BOOL, Options::isTargetingPlayers)
      .withComponent(ByteBufCodecs.BOOL, Options::isShadow)
      .withComponent(ByteBufCodecs.BOOL, Options::isSeeThrough)
      .decoderFactory(components -> new Options(
        (List<Component>) components.next(),
        (int) components.next(),
        (int) components.next(),
        (int) components.next(),
        (float) components.next(),
        (AlignType) components.next(),
        (float) components.next(),
        (float) components.next(),
        (boolean) components.next(),
        (boolean) components.next(),
        (boolean) components.next()
      )).build();

    public Builder getBuild() {
      return new Builder(this.textComponent, this.fontColor, this.strokeColor, this.particleLifeTime,
        this.size, this.align, this.xRot, this.yRot, this.isTargetingPlayers, this.isShadow, this.isSeeThrough);
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
      return type.getBuild().buildParticle(level, x, y, z);
    }
  }
}
