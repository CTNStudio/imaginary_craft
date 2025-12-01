package ctn.imaginarycraft.client.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.util.GuiUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class HorizontalStatusBar extends AbstractWidget {
  private final @NotNull TextureLayer bottom;
  private final @NotNull TextureLayer internal;
  private final @NotNull TextureLayer light;
  private boolean isLight;
  private float lightTick;
  private float lightWidth;
  private float value;
  private float oldValue;
  private float maxValue;

  public HorizontalStatusBar(
    int width,
    int height,
    @NotNull TextureLayer bottom,
    @NotNull TextureLayer internal,
    @NotNull TextureLayer light
  ) {
    super(0, 0, width, height, Component.empty());
    this.bottom = bottom;
    this.internal = internal;
    this.light = light;
  }

  private void renderLayer(final @NotNull GuiGraphics guiGraphics, final TextureLayer layer, int x, int y) {
    GuiUtil.blitSprite(
      guiGraphics,
      layer.texture,
      x + layer.xPos,
      y + layer.yPos,
      layer.width, layer.height);
  }

  public float getOldValue() {
    return this.oldValue;
  }

  public void setOldValue(float value) {
    this.oldValue = value;
  }

  public float getValue() {
    return this.value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public void setLightWidth(float oldValue, float newValue) {
    float renderValue = oldValue - newValue;
    int maxWidth = this.internal.width;
    this.lightWidth += Math.clamp(renderValue / this.maxValue * maxWidth, -maxWidth + 1, maxWidth - 1);
    if (this.lightWidth <= 0) {
      this.lightWidth = 0;
    }
    this.value = newValue;
  }

  public void setLightWidth(float value) {
    this.lightWidth = value;
  }

  public float getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(float maxValue) {
    this.maxValue = maxValue;
  }

  public float getClampValue() {
    return Math.clamp(this.getOldValue(), 0, this.getMaxValue());
  }

  public void setLight() {
    this.isLight = true;
    this.lightTick = 1;
  }

  @Override
  protected void renderWidget(final GuiGraphics guiGraphics, final int mouseX, final int mouseY, final float partialTick) {
    PoseStack pose = guiGraphics.pose();
    int x = getX();
    int y = getY();

    renderLayer(guiGraphics, this.bottom, x, y);

    renderInternal(guiGraphics, partialTick, x, y);

    if (this.isLight) {
      renderLayer(guiGraphics, this.light, x, y);
    }

    if (this.lightTick <= 0) {
      this.lightTick = 0;
      isLight = false;
    } else {
      this.lightTick -= partialTick;
    }
  }

  private void renderInternal(final GuiGraphics guiGraphics, final float partialTick, final int x, final int y) {
    final int maxWidth = this.internal.width;
    float value = getClampValue();
    // 使用更平滑的插值算法
    float maxValue = getMaxValue();

    float internalHeight = this.internal.height;
    float internalUWidth = value / maxValue * maxWidth;

    float posX = x + this.internal.xPos;
    float posY = y + this.internal.yPos;

    if (this.lightWidth > 0) {
      float v = this.value / maxValue * maxWidth;
      float minX = Math.clamp(v, 0, posX + maxWidth);
      float maxX = Math.clamp(minX + this.lightWidth, 0, posX + maxWidth);
      float maxY = posY + internalHeight;
      int color = 255 << 24 | 255 << 16 | 255 << 8 | 255 / 2;
      if (this.isLight) {
        color = 0xffffffff;
      }
      GuiUtil.fill(guiGraphics, Math.clamp(posX + minX, posX + 1, posX + maxValue - 1), posY + 1, posX + maxX, maxY - 1, color);
      this.lightWidth = Mth.lerp(Math.clamp(partialTick * 0.2f, 0, 1), this.lightWidth, 0);
    }

    GuiUtil.blitSprite(guiGraphics, this.internal.texture, maxWidth, internalHeight,
      0, 0, posX, posY, internalUWidth, internalHeight);
  }

  @Override
  protected void updateWidgetNarration(final NarrationElementOutput narrationElementOutput) {

  }

  public void reset() {
    this.oldValue = 0;
    this.lightWidth = 0;
  }

  public record TextureLayer(
    float xPos,
    float yPos,
    int width,
    int height,
    @NotNull ResourceLocation texture
  ) {
  }
}
