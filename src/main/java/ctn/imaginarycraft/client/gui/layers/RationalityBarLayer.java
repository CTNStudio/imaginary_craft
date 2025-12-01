package ctn.imaginarycraft.client.gui.layers;

import ctn.imaginarycraft.api.lobotomycorporation.util.RationalityUtil;
import ctn.imaginarycraft.client.gui.widget.HorizontalStatusBar;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

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

  private float rationality;
  private float renderRationality;
  private float oldRationality;
  private float maxRationality;
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
    float gameTimeDeltaTicks = deltaTracker.getRealtimeDeltaTicks();

    if (this.renderRationality >= this.maxRationality * 0.7) {
      this.tallBar.render(guiGraphics, 0, 0, gameTimeDeltaTicks);
      return;
    }

    if (this.renderRationality <= -this.maxRationality * 0.99) {
      this.lowDynamicBar.render(guiGraphics, 0, 0, gameTimeDeltaTicks);
      return;
    }

    if (this.renderRationality >= 0) {
      this.defaultBar.render(guiGraphics, 0, 0, gameTimeDeltaTicks);
      return;
    }

    if (this.renderRationality < 0) {
      this.lowBar.render(guiGraphics, 0, 0, gameTimeDeltaTicks);
    }
  }

  @Override
  public void init(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker) {
    super.init(guiGraphics, deltaTracker);

    var newMaxRationality = RationalityUtil.getMaxValue(this.player);
    if (newMaxRationality != this.maxRationality) {
      this.maxRationality = newMaxRationality;
      this.defaultBar.setMaxValue(Math.abs(this.maxRationality));
      this.lowBar.setMaxValue(Math.abs(this.maxRationality));
      this.tallBar.setMaxValue(Math.abs(this.maxRationality));
    }

    float gameTimeDeltaTicks = deltaTracker.getRealtimeDeltaTicks();
    var newRationality = RationalityUtil.getValue(this.player);
    var oldRationality = this.rationality;

    if (newRationality != oldRationality) {
      this.rationality = newRationality;
      this.defaultBar.setLight();
      this.lowBar.setLight();
      this.tallBar.setLight();
    }

    this.renderRationality = Mth.lerp(Math.clamp(gameTimeDeltaTicks, 0, 1), this.oldRationality, this.rationality);

    float renderValue = Math.abs(this.renderRationality);
    this.defaultBar.setOldValue(renderValue);
    this.lowBar.setOldValue(renderValue);
    this.tallBar.setOldValue(renderValue);

    if (this.renderRationality < 0) {
      float v = Math.abs(oldRationality);
      float v1 = Math.abs(this.rationality);
      this.defaultBar.setLightWidth(v, v1);
      this.lowBar.setLightWidth(v, v1);
      this.tallBar.setLightWidth(v, v1);
    } else {
      this.defaultBar.setLightWidth(0);
      this.lowBar.setLightWidth(0);
      this.tallBar.setLightWidth(0);
    }

    if (this.oldRationality != this.renderRationality) {
      this.oldRationality = this.renderRationality;
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
