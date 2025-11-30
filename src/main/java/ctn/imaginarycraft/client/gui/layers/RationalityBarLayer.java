package ctn.imaginarycraft.client.gui.layers;

import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.client.gui.widget.HorizontalStatusBar;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.resources.ResourceLocation;

public class RationalityBarLayer extends BasicDrawLayer {
  public static final ResourceLocation DEFAULT_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality");
  public static final ResourceLocation DEFAULT_BOTTOM_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality_bottom");
  public static final ResourceLocation DEFAULT_LIGHT_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality_light");
  public static final ResourceLocation LOW_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality_low");
  public static final ResourceLocation LOW_BOTTOM_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality_low_bottom");
  public static final ResourceLocation LOW_DYNAMIC_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality_low_dynamic");
  public static final ResourceLocation LOW_LIGHT_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality_low_light");
  public static final ResourceLocation TALL_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality_tall");
  public static final ResourceLocation TALL_BOTTOM_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality_tall_bottom");
  public static final ResourceLocation TALL_LIGHT_TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality/rationality_tall_light");

  public static final int WIDTH = 98;
  public static final int HEIGHT = 14;

  public static final RationalityBarLayer INSTANCE = new RationalityBarLayer();

  private double rationality;
  private double maxRationality;
  private final HorizontalStatusBar defaultBar;
  private final HorizontalStatusBar lowBar;
  private final ImageWidget lowDynamicBar;
  private final HorizontalStatusBar tallBar;

  public RationalityBarLayer() {
    this.defaultBar = new HorizontalStatusBar(WIDTH, HEIGHT,
      new HorizontalStatusBar.TextureLayer(0, 0, WIDTH, HEIGHT, DEFAULT_BOTTOM_TEXTURE),
      new HorizontalStatusBar.TextureLayer(16, 2, 80, 9, DEFAULT_TEXTURE),
      new HorizontalStatusBar.TextureLayer(0, 0, WIDTH, HEIGHT, DEFAULT_LIGHT_TEXTURE));
    this.lowBar = new HorizontalStatusBar(WIDTH, HEIGHT,
      new HorizontalStatusBar.TextureLayer(0, 0, WIDTH, HEIGHT, LOW_BOTTOM_TEXTURE),
      new HorizontalStatusBar.TextureLayer(16, 2, 80, 9, LOW_TEXTURE),
      new HorizontalStatusBar.TextureLayer(0, 0, WIDTH, HEIGHT, LOW_LIGHT_TEXTURE));
    this.lowDynamicBar = ImageWidget.sprite(98, 14, LOW_DYNAMIC_TEXTURE);
    this.tallBar = new HorizontalStatusBar(WIDTH, HEIGHT,
      new HorizontalStatusBar.TextureLayer(0, 0, WIDTH, HEIGHT, TALL_BOTTOM_TEXTURE),
      new HorizontalStatusBar.TextureLayer(16, 2, 80, 9, TALL_TEXTURE),
      new HorizontalStatusBar.TextureLayer(0, 0, WIDTH, HEIGHT, TALL_LIGHT_TEXTURE));
  }

  @Override
  public void renderDrawLayer(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker) {
    float gameTimeDeltaTicks = deltaTracker.getGameTimeDeltaTicks();

    if (rationality == maxRationality) {
      this.tallBar.render(guiGraphics, 0, 0, gameTimeDeltaTicks);
      return;
    }

    if (rationality > 0) {
      this.defaultBar.render(guiGraphics, 0, 0, gameTimeDeltaTicks);
      return;
    }

    if (rationality < 0) {
      this.lowBar.render(guiGraphics, 0, 0, gameTimeDeltaTicks);
      return;
    }

    if (rationality <= -this.maxRationality) {
      this.lowDynamicBar.render(guiGraphics, 0, 0, gameTimeDeltaTicks);
    }
  }

  @Override
  public void init(final GuiGraphics guiGraphics) {
    super.init(guiGraphics);

    var newRationality = RationalityUtil.getValue(this.player);
    var newMaxRationality = RationalityUtil.getMaxValue(this.player);
    if (newRationality != this.rationality) {
      this.rationality = newRationality;
      this.defaultBar.setValue(this.rationality);
      this.lowBar.setValue(this.rationality);
      this.tallBar.setValue(this.rationality);
    }

    if (newMaxRationality != this.maxRationality) {
      this.maxRationality = newMaxRationality;
      this.defaultBar.setMaxValue(this.maxRationality);
      this.lowBar.setMaxValue(this.maxRationality);
      this.tallBar.setMaxValue(this.maxRationality);
    }
  }

  @Override
  protected void sizeChange(final boolean isWidthChange, final boolean isHeightChange, final int width, final int height) {
    super.sizeChange(isWidthChange, isHeightChange, width, height);
    if (isWidthChange) {
      this.leftPos = width / 2;
      this.defaultBar.setX(this.leftPos - defaultBar.getWidth());
      this.lowBar.setX(this.leftPos - defaultBar.getWidth());
      this.lowDynamicBar.setX(this.leftPos - defaultBar.getWidth());
      this.tallBar.setX(this.leftPos - defaultBar.getWidth());
    }
    if (isHeightChange) {
      this.topPos = height - 65;
      this.defaultBar.setY(this.topPos);
      this.lowBar.setY(this.topPos);
      this.lowDynamicBar.setY(this.topPos);
      this.tallBar.setY(this.topPos);
    }
  }
}
