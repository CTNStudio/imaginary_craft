package ctn.imaginarycraft.client.gui.widget;

import ctn.ctnapi.client.gui.widget.ImageProgressBar;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HorizontalStatusBar extends ImageProgressBar.Horizontal {
  private final TextureLayer bottomInternal;
  private final TextureLayer internal;
  private final TextureLayer cover;
  private final int textureWidth;
  private final int textureHeight;

  public HorizontalStatusBar(int width, int height,
                             TextureLayer bottomInternal,
                             TextureLayer internal,
                             TextureLayer cover,
                             int textureWidth, int textureHeight,
                             ResourceLocation texture) {
    super(0, 0, width, height, 0, 0, texture, "", true);
    this.bottomInternal = bottomInternal;
    this.internal = internal;
    this.cover = cover;
    this.textureWidth = textureWidth;
    this.textureHeight = textureHeight;
  }

  @Override
  protected void renderTexture(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    var pose = guiGraphics.pose();
    var x = getX();
    var y = getY();
    var width = getWidth();
    var height = getHeight();

    ResourceLocation texture = getTexture();
    renderLayer(guiGraphics, texture, this.internal, x, y);
    renderLayer(guiGraphics, texture, this.bottomInternal, x, y);
    renderLayer(guiGraphics, texture, this.cover, x, y);
  }

  private void renderLayer(final @NotNull GuiGraphics guiGraphics, final ResourceLocation texture, final TextureLayer layer, int x, int y) {
    guiGraphics.blitSprite(texture, this.textureWidth, this.textureHeight,
      layer.uPos, layer.vPos, x + layer.xPos, y + layer.yPos, layer.width, layer.height);
  }

  public double getRenderHeightValue() {
    return getRenderValue() / getMaxValue() * this.internal.height();
  }

  public record TextureLayer(int xPos, int yPos, int uPos, int vPos, int width, int height,
                             int interval) {
  }
}
