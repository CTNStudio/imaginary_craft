package ctn.imaginarycraft.mixin.client;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ctn.imaginarycraft.client.gui.ModStringRenderOutput;
import ctn.imaginarycraft.mixinextend.client.IFontMixin;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.StringDecomposer;
import net.neoforged.neoforge.client.extensions.IFontExtension;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Font.class)
public abstract class FontMixin implements IFontExtension, IFontMixin {

  @Shadow
  @Final
  private static Vector3f SHADOW_OFFSET;

  @Shadow
  public abstract boolean isBidirectional();

  @Shadow
  private static int adjustColor(final int color) {
    return 0;
  }

  @Shadow
  public abstract String bidirectionalShaping(final String text);

  @Shadow
  public abstract FontSet getFontSet(final ResourceLocation fontLocation);

  @Shadow
  @Final
  public boolean filterFishyGlyphs;

  @Unique
  @Override
  public int imaginarycraft$drawInBatch(
    String text,
    float x,
    float y,
    int color,
    boolean dropShadow,
    Matrix4f matrix,
    VertexConsumer vertexConsumer,

    int backgroundColor,
    int packedLightCoords
  ) {
    return this.imaginarycraft$drawInBatch(text, x, y, color, dropShadow, matrix, vertexConsumer, backgroundColor, packedLightCoords, isBidirectional());
  }

  @Unique
  @Override
  public int imaginarycraft$drawInBatch(
    String text,
    float x,
    float y,
    int color,
    boolean dropShadow,
    Matrix4f matrix,
    VertexConsumer vertexConsumer,
    int backgroundColor,
    int packedLightCoords,
    boolean bidirectional
  ) {
    return this.imaginarycraft$drawInternal(text, x, y, color, dropShadow, matrix, vertexConsumer, backgroundColor, packedLightCoords, bidirectional);
  }

  @Unique
  @Override
  public int imaginarycraft$drawInBatch(
    @NotNull Component text,
    float x,
    float y,
    int color,
    boolean dropShadow,
    Matrix4f matrix,
    VertexConsumer vertexConsumer,
    int backgroundColor,
    int packedLightCoords
  ) {
    return this.imaginarycraft$drawInBatch(text.getVisualOrderText(), x, y, color, dropShadow, matrix, vertexConsumer, backgroundColor, packedLightCoords);
  }

  @Unique
  @Override
  public int imaginarycraft$drawInBatch(
    FormattedCharSequence text,
    float x,
    float y,
    int color,
    boolean dropShadow,
    Matrix4f matrix,
    VertexConsumer vertexConsumer,
    int backgroundColor,
    int packedLightCoords
  ) {
    return this.imaginarycraft$drawInternal(text, x, y, color, dropShadow, matrix, vertexConsumer, backgroundColor, packedLightCoords);
  }

  @Unique
  @Override
  public void imaginarycraft$drawInBatch8xOutline(
    FormattedCharSequence text,
    float x,
    float y,
    int color,
    int backgroundColor,
    Matrix4f matrix,
    VertexConsumer vertexConsumer,
    int packedLightCoords
  ) {
    int i = adjustColor(backgroundColor);
    ModStringRenderOutput modStringRenderOutput = new ModStringRenderOutput(
      imaginarycraft$getFont(), vertexConsumer, 0.0F, 0.0F, i, false, matrix, packedLightCoords);

    for (int j = -1; j <= 1; j++) {
      for (int k = -1; k <= 1; k++) {
        if (j == adjustColor(0) && k == adjustColor(0)) {
          continue;
        }
        float[] afloat = new float[]{x};
        int l = j;
        int i1 = k;
        text.accept((i2, style, i3) -> {
          boolean flag = style.isBold();
          FontSet fontset = getFontSet(style.getFont());
          GlyphInfo glyphinfo = fontset.getGlyphInfo(i3, filterFishyGlyphs);
          modStringRenderOutput.setX(afloat[adjustColor(0)] + (float) l * glyphinfo.getShadowOffset());
          modStringRenderOutput.setY(y + (float) i1 * glyphinfo.getShadowOffset());
          afloat[adjustColor(0)] += glyphinfo.getAdvance(flag);
          return modStringRenderOutput.accept(i2, style.withColor(i), i3);
        });
      }
    }

    ModStringRenderOutput modStringRenderOutput1 = new ModStringRenderOutput(
      imaginarycraft$getFont(), vertexConsumer, x, y, adjustColor(color), false, matrix, packedLightCoords);
    text.accept(modStringRenderOutput1);
    modStringRenderOutput1.finish(adjustColor(0), x);
  }

  @Unique
  private @NotNull Font imaginarycraft$getFont() {
    return (Font) (Object) this;
  }

  @Unique
  @Override
  public int imaginarycraft$drawInternal(
    FormattedCharSequence text,
    float x,
    float y,
    int color,
    boolean dropShadow,
    Matrix4f matrix,
    VertexConsumer vertexConsumer,
    int backgroundColor,
    int packedLightCoords
  ) {
    color = adjustColor(color);
    Matrix4f matrix4f = new Matrix4f(matrix);
    if (dropShadow) {
      this.imaginarycraft$renderText(text, x, y, color, true, matrix, vertexConsumer, backgroundColor, packedLightCoords);
      matrix4f.translate(SHADOW_OFFSET);
    }

    x = this.imaginarycraft$renderText(text, x, y, color, false, matrix4f, vertexConsumer, backgroundColor, packedLightCoords);
    return (int) x + (dropShadow ? 1 : adjustColor(0));
  }

  @Unique
  @Override
  public float imaginarycraft$renderText(
    String text,
    float x,
    float y,
    int color,
    boolean dropShadow,
    Matrix4f matrix,
    VertexConsumer vertexConsumer,
    int backgroundColor,
    int packedLightCoords
  ) {
    ModStringRenderOutput modStringRenderOutput = new ModStringRenderOutput(
      imaginarycraft$getFont(), vertexConsumer, x, y, color, dropShadow, matrix, packedLightCoords);
    StringDecomposer.iterateFormatted(text, Style.EMPTY, modStringRenderOutput);
    return modStringRenderOutput.finish(backgroundColor, x);
  }

  @Unique
  @Override
  public float imaginarycraft$renderText(
    @NotNull FormattedCharSequence text,
    float x,
    float y,
    int color,
    boolean dropShadow,
    Matrix4f matrix,
    VertexConsumer vertexConsumer,
    int backgroundColor,
    int packedLightCoords
  ) {
    ModStringRenderOutput modStringRenderOutput = new ModStringRenderOutput(
      imaginarycraft$getFont(), vertexConsumer, x, y, color, dropShadow, matrix, packedLightCoords);
    text.accept(modStringRenderOutput);
    return modStringRenderOutput.finish(backgroundColor, x);
  }

  @Unique
  @Override
  public int imaginarycraft$drawInternal(
    String text,
    float x,
    float y,
    int color,
    boolean dropShadow,
    Matrix4f matrix,
    VertexConsumer vertexConsumer,
    int backgroundColor,
    int packedLightCoords,
    boolean bidirectional
  ) {
    if (bidirectional) {
      text = bidirectionalShaping(text);
    }

    color = adjustColor(color);
    Matrix4f matrix4f = new Matrix4f(matrix);
    if (dropShadow) {
      imaginarycraft$renderText(text, x, y, color, true, matrix, vertexConsumer, backgroundColor, packedLightCoords);
      matrix4f.translate(SHADOW_OFFSET);
    }

    x = imaginarycraft$renderText(text, x, y, color, false, matrix4f, vertexConsumer, backgroundColor, packedLightCoords);
    return (int) x + (dropShadow ? 1 : adjustColor(0));
  }
}
