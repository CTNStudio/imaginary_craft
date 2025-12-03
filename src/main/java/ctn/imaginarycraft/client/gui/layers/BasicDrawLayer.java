package ctn.imaginarycraft.client.gui.layers;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;

import java.util.Objects;

public abstract class BasicDrawLayer implements LayeredDraw.Layer {
  protected final Minecraft minecraft;
  protected LocalPlayer player;
  protected final Font font;
  protected int leftPos;
  protected int topPos;
  protected int screenWidth;
  protected int screenHeight;

  public BasicDrawLayer() {
    this.minecraft = Minecraft.getInstance();
    this.font = this.minecraft.font;
  }

  @Override
  public void render(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker) {
    if (this.minecraft.options.hideGui) {
      return;
    }
    init(guiGraphics, deltaTracker);

    renderDrawLayer(guiGraphics, deltaTracker);
  }

  /**
   * 绘制
   */
  protected abstract void renderDrawLayer(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker);

  public void init(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker) {
    int newScreenWidth = guiGraphics.guiWidth();
    int newScreenHeight = guiGraphics.guiHeight();

    boolean isWidthChange = newScreenWidth != this.screenWidth;
    boolean isHeightChange = newScreenHeight != this.screenHeight;
    if (isWidthChange || isHeightChange) {
      sizeChange(isWidthChange, isHeightChange, newScreenWidth, newScreenHeight);
    }

    var newPlayer = Objects.requireNonNull(this.minecraft.player);
    if (this.player != newPlayer) {
      playerChange(newPlayer);
    }
  }

  protected void playerChange(final LocalPlayer newPlayer) {
    this.player = newPlayer;
  }

  protected void sizeChange(final boolean isWidthChange, final boolean isHeightChange, final int newScreenWidth, final int newScreenHeight) {
    if (isHeightChange) {
      this.screenWidth = newScreenWidth;
    }

    if (isWidthChange) {
      this.screenHeight = newScreenHeight;
    }
  }
}
