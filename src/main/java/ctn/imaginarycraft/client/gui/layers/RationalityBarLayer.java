package ctn.imaginarycraft.client.gui.layers;

import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.client.gui.widget.HorizontalStatusBar;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class RationalityBarLayer extends BasicDrawLayer {
  public static final RationalityBarLayer INSTANCE = new RationalityBarLayer();
  public static final ResourceLocation TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality_bar");
  public static final int HEIGHT = 9;
  public static final int WIDTH = 80;

  private double rationality;
  private double maxRationality;
  private final HorizontalStatusBar statusBar;

  public RationalityBarLayer() {
    this.statusBar = new HorizontalStatusBar(WIDTH, HEIGHT,
      new HorizontalStatusBar.TextureLayer(0, 0, 133, 4, 78, 7, 11),
      new HorizontalStatusBar.TextureLayer(1, 1, 51, 3, 80, 9, 9),
      new HorizontalStatusBar.TextureLayer(0, 0, 213, 3, 80, 9, 9),
      512, 128,
      TEXTURE);
  }

  @Override
  public void renderDrawLayer(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker) {
    this.statusBar.renderWidget(guiGraphics, this.leftPos, this.topPos, deltaTracker.getGameTimeDeltaTicks());
  }

  @Override
  public void init(final GuiGraphics guiGraphics) {
    super.init(guiGraphics);

    var newRationality = RationalityUtil.getValue(this.player);
    var newMaxRationality = RationalityUtil.getMaxValue(this.player);
    if (newRationality != this.rationality) {
      this.rationality = newRationality;
    }

    if (newMaxRationality != this.maxRationality) {
      this.maxRationality = newMaxRationality;
    }
  }

  @Override
  protected void sizeChange(final boolean isWidthChange, final boolean isHeightChange, final int oldScreenWidth, final int oldScreenHeight) {
    super.sizeChange(isWidthChange, isHeightChange, oldScreenWidth, oldScreenHeight);
    this.leftPos = oldScreenWidth / 2;
    this.topPos = oldScreenHeight - 65;
    this.statusBar.setX(this.leftPos);
    this.statusBar.setY(this.topPos);
  }
}
