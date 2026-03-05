package ctn.imaginarycraft.mixed.client;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.player.*;

public abstract class IHudLayer implements LayeredDraw.Layer {
  protected int leftPos;
  protected int topPos;
  protected Minecraft minecraft;
  protected LocalPlayer player;
  protected Font font;

  public IHudLayer() {
    this.minecraft = Minecraft.getInstance();
    this.font = this.minecraft.font;
  }

  public void init(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {

  }

  public void playerChange(LocalPlayer newPlayer) {
    this.player = newPlayer;
  }

  public void setLeftPos(int leftPos) {
    this.leftPos = leftPos;
  }

  public void setTopPos(int topPos) {
    this.topPos = topPos;
  }

  public int getWidth() {
    return -1;
  }

  public int getHeight() {
    return -1;
  }

  public Font getFont() {
    return font;
  }

  public void setFont(Font font) {
    this.font = font;
  }
}
