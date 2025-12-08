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
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
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
import java.util.function.Function;
import java.util.function.IntFunction;

// TODO 拆分成伤害，BOSS说话文本，普通文本
public class TextParticle extends TextureSheetParticle {
  private static final Function<Tesselator, BufferBuilder> RENDER_TYPE_MEMOIZE = Util.memoize((Tesselator tesselator) ->
    tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE));
  public static final ParticleRenderType RENDER_TYPE = new ParticleRenderType() {
    @Override
    public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
      RenderSystem.setShader(GameRenderer::getParticleShader);
      RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
      return RENDER_TYPE_MEMOIZE.apply(tesselator);
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
  protected final int strokeColor;
  protected final int durationTick;
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
  protected final boolean shadow;

  protected TextParticle(
    final ClientLevel level,
    final double x,
    final double y,
    final double z,
    Build build
  ) {
    super(level, x, y, z);
    this.minecraft = Minecraft.getInstance();
    this.font = this.minecraft.font;
    this.textComponentList = build.textComponent;
    this.fontColor = build.fontColor;
    this.strokeColor = build.strokeColor;
    this.durationTick = build.durationTick;
    this.size = build.size;
    this.align = build.align;
    this.xRot = build.xRot;
    this.yRot = build.yRot;
    this.isTargetingPlayers = build.isTargetingPlayers;
    this.shadow = build.shadow;
  }

  @Override
  public void render(@NotNull VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
    OutlineBufferSource outlineBufferSource = this.minecraft.renderBuffers().outlineBufferSource();
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
    float textY = textComponentList.size() * -(fontHeight / 2f + 1);
    for (Component component : this.textComponentList) {
      float fontWidth = splitter.stringWidth(component);
      float textX = switch (this.align) {
        case LEFT -> 0;
        case CENTER -> -fontWidth / 2f;
        case RIGHT -> fontWidth;
      };
      poseStack.pushPose();
      renderText(component, font, textX, textY, poseStack.last().pose(), outlineBufferSource, getLightColor);
      poseStack.popPose();
      textY -= -(fontHeight / 2f + 1);
    }
    outlineBufferSource.endOutlineBatch();
    poseStack.popPose();
  }

  public void renderText(Component text, final Font font, final float textX, final float textY, final Matrix4f matrix, OutlineBufferSource outlineBufferSource, final int getLightColor) {
    float x1 = textX + 1;
    float y1 = textY + 1;
    int strokeColor = this.strokeColor;
    font.drawInBatch(text, x1, y1, strokeColor, false, matrix, outlineBufferSource, Font.DisplayMode.SEE_THROUGH, strokeColor, getLightColor);
    font.drawInBatch(text, x1, y1, strokeColor, false, matrix, outlineBufferSource, Font.DisplayMode.NORMAL, strokeColor, getLightColor);
    if (!this.shadow) {
      return;
    }
    renderTextShadow(font, textX, textY, matrix, outlineBufferSource, getLightColor, text);
  }

  private void renderTextShadow(final Font font, final float textX, final float textY, final Matrix4f matrix, final OutlineBufferSource outlineBufferSource, final int getLightColor, final Component text) {
    matrix.translate(0, 0, -0.03f);
    int fontColor = this.fontColor;
    font.drawInBatch(text, textX, textY, fontColor, false, matrix, outlineBufferSource, Font.DisplayMode.SEE_THROUGH, fontColor, getLightColor);
    font.drawInBatch(text, textX, textY, fontColor, false, matrix, outlineBufferSource, Font.DisplayMode.NORMAL, fontColor, getLightColor);
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
  public void tick() {
    this.age++;
    if (this.age > this.durationTick) {
      remove();
    }
  }

  @Override
  public @NotNull ParticleRenderType getRenderType() {
    return RENDER_TYPE;
  }

  public static class Build {
    protected List<Component> textComponent;
    protected int fontColor = 0xffffff;
    protected int strokeColor = 0xafafafaf;
    protected int durationTick = 20 * 3;
    protected float size = 0.135f;
    protected AlignType align = AlignType.LEFT;
    protected float xRot;
    protected float yRot;
    protected boolean isTargetingPlayers;
    protected boolean shadow;

    private Build(
      final List<Component> textComponent,
      final int fontColor,
      final int strokeColor,
      final int durationTick,
      final float size,
      final AlignType align,
      final float xRot,
      final float yRot,
      final boolean isTargetingPlayers,
      final boolean shadow
    ) {
      this.textComponent = textComponent;
      this.fontColor = fontColor;
      this.strokeColor = strokeColor;
      this.durationTick = durationTick;
      this.size = size;
      this.align = align;
      this.xRot = xRot;
      this.yRot = yRot;
      this.isTargetingPlayers = isTargetingPlayers;
      this.shadow = shadow;
    }

    public Build(Component... textComponent) {
      this.textComponent = List.of(textComponent);
    }

    public Build textComponent(Component... textComponent) {
      this.textComponent = List.of(textComponent);
      return this;
    }

    public Build align(AlignType type) {
      this.align = type;
      return this;
    }

    public Build fontColor(int fontColor) {
      this.fontColor = fontColor;
      return this;
    }

    public Build strokeColor(int strokeColor) {
      this.strokeColor = strokeColor;
      return this;
    }

    public Build durationTick(int durationTick) {
      this.durationTick = durationTick;
      return this;
    }

    public Build shadow() {
      this.shadow = true;
      return this;
    }

    public Build shadow(boolean shadow) {
      this.shadow = shadow;
      return this;
    }

    public Build size(float size) {
      this.size = size;
      return this;
    }

    public Build xRot(float xRot) {
      this.xRot = xRot;
      return this;
    }

    public Build yRot(float yRot) {
      this.yRot = yRot;
      return this;
    }

    public Build targetingPlayers() {
      this.isTargetingPlayers = true;
      return this;
    }

    public Build targetingPlayers(boolean targetingPlayers) {
      this.isTargetingPlayers = targetingPlayers;
      return this;
    }

    public TextParticle buildParticle(final ClientLevel level, final double x, final double y, final double z) {
      return new TextParticle(level, x, y, z, this);
    }

    public Options buildOptions() {
      return new Options(
        this.textComponent,
        this.fontColor,
        this.strokeColor,
        this.durationTick,
        this.size,
        this.align,
        this.xRot,
        this.yRot,
        this.isTargetingPlayers,
        this.shadow
      );
    }
  }

  public enum AlignType implements StringRepresentable {
    LEFT(0, "left"),
    CENTER(1, "center"),
    RIGHT(2, "right"),
    ;
    private final int index;
    private final String name;

    public static final StringRepresentable.EnumCodec<AlignType> CODEC = StringRepresentable.fromEnum(AlignType::values);
    public static final Codec<AlignType> VERTICAL_CODEC = CODEC.validate(DataResult::success);
    public static final IntFunction<AlignType> BY_ID = ByIdMap.continuous(AlignType::getIndex, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final StreamCodec<ByteBuf, AlignType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, AlignType::getIndex);

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

  public record Options(
    List<Component> textComponent,
    int fontColor,
    int strokeColor,
    int durationTick,
    float size,
    AlignType align,
    float xRot,
    float yRot,
    boolean isTargetingPlayers,
    boolean shadow
  ) implements ParticleOptions {
    public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec((thisOptionsInstance) -> thisOptionsInstance.group(
      Codec.list(ComponentSerialization.CODEC).fieldOf("textComponentList").forGetter(Options::textComponent),
      Codec.INT.fieldOf("fontColor").forGetter(Options::fontColor),
      Codec.INT.fieldOf("strokeColor").forGetter(Options::strokeColor),
      Codec.INT.fieldOf("durationTick").forGetter(Options::durationTick),
      Codec.FLOAT.fieldOf("size").forGetter(Options::size),
      AlignType.CODEC.fieldOf("align").forGetter(Options::align),
      Codec.FLOAT.fieldOf("xRot").forGetter(Options::xRot),
      Codec.FLOAT.fieldOf("yRot").forGetter(Options::yRot),
      Codec.BOOL.fieldOf("isTargetingPlayers").forGetter(Options::isTargetingPlayers),
      Codec.BOOL.fieldOf("shadow").forGetter(Options::shadow)
    ).apply(thisOptionsInstance, Options::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Options> STREAM_CODEC = CompositeStreamCodecBuilder.<RegistryFriendlyByteBuf, Options>builder()
      .withComponent(ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs.list()), Options::textComponent)
      .withComponent(ByteBufCodecs.INT, Options::fontColor)
      .withComponent(ByteBufCodecs.INT, Options::strokeColor)
      .withComponent(ByteBufCodecs.INT, Options::durationTick)
      .withComponent(ByteBufCodecs.FLOAT, Options::size)
      .withComponent(AlignType.STREAM_CODEC, Options::align)
      .withComponent(ByteBufCodecs.FLOAT, Options::xRot)
      .withComponent(ByteBufCodecs.FLOAT, Options::yRot)
      .withComponent(ByteBufCodecs.BOOL, Options::isTargetingPlayers)
      .withComponent(ByteBufCodecs.BOOL, Options::shadow)
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
        (boolean) components.next()
      )).build();

    public Build getBuild() {
      return new Build(
        this.textComponent,
        this.fontColor,
        this.strokeColor,
        this.durationTick,
        this.size,
        this.align,
        this.xRot,
        this.yRot,
        this.isTargetingPlayers,
        this.shadow
      );
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
