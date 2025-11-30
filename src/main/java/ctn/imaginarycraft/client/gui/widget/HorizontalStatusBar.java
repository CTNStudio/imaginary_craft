package ctn.imaginarycraft.client.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.util.GuiUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HorizontalStatusBar extends AbstractWidget {
  private final @NotNull TextureLayer bottom;
  private final @NotNull TextureLayer internal;
  private final @NotNull TextureLayer light;
  private boolean isLight;
  private float lightTick;
  private double value;
  private double maxValue;

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


  /**
   * 同时设置当前值和最大值。
   *
   * @param energy  当前进度值
   * @param maxTick 最大进度值
   */
  public void setValue(double energy, double maxTick) {
    this.setValue(energy);
    this.setMaxValue(maxTick);
  }

  /**
   * 获取当前进度值。
   *
   * @return 当前进度值
   */
  public double getValue() {
    return this.value;
  }

  /**
   * 设置当前进度值。
   *
   * @param value 新的进度值
   */
  public void setValue(double value) {
    this.value = value;
  }

  /**
   * 获取最大进度值。
   *
   * @return 最大进度值
   */
  public double getMaxValue() {
    return maxValue;
  }

  /**
   * 设置最大进度值。
   *
   * @param maxValue 新的最大进度值
   */
  public void setMaxValue(double maxValue) {
    this.maxValue = maxValue;
  }

  /**
   * 获取要渲染的进度值，确保不小于 0。
   *
   * @return 渲染用的进度值
   */
  public double getRenderValue() {
    return Math.min(Math.max(0, this.getValue()), this.getMaxValue());
  }

  public void setLight(boolean light) {
    isLight = true;
  }

  @Override
  protected void renderWidget(final GuiGraphics guiGraphics, final int mouseX, final int mouseY, final float partialTick) {
    PoseStack pose = guiGraphics.pose();
    int x = getX();
    int y = getY();

    renderLayer(guiGraphics, this.bottom, x, y);

    GuiUtil.blitSprite(
      guiGraphics,
      this.internal.texture,
      x + this.internal.xPos,
      y + this.internal.yPos,
      (float) (getRenderValue() / getMaxValue() * this.internal.width()),
      this.internal.height);

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

  @Override
  protected void updateWidgetNarration(final NarrationElementOutput narrationElementOutput) {

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
