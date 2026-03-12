package ctn.imaginarycraft.client.gui.hudlayers;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.config.ModConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public class LeftBarLayer extends CompositeHudLayer {
  public static final LeftBarLayer INSTANCE = new LeftBarLayer();
  public final RationalityBarLayer rationalityBarLayer = new RationalityBarLayer();
  public final NewHealthBarLayer newHealthBarLayer = new NewHealthBarLayer();

  public LeftBarLayer() {
    addLayer(this.newHealthBarLayer,
      () -> !this.minecraft.options.hideGui &&
        ModConfig.CLIENT.enableNewHealthBar.get() &&
        this.player != null &&
        !player.isSpectator() &&
        !this.player.isCreative());
    addLayer(this.rationalityBarLayer,
      () -> !this.minecraft.options.hideGui &&
        this.player != null &&
        !player.isSpectator());
  }

  @Override
  protected void updatePos(final boolean isWidthChange, final boolean isHeightChange, final int newScreenWidth, final int newScreenHeight) {
    super.updatePos(isWidthChange, isHeightChange, newScreenWidth, newScreenHeight);
    if (isWidthChange) {
      this.leftPos = newScreenWidth / 2 - 105;
    }
    if (isHeightChange) {
      this.topPos = newScreenHeight - 30;
    }
  }

  @Override
  protected void renderSubLayer(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker) {
    PoseStack pose = guiGraphics.pose();
    pose.pushPose();
    if (this.player.isCreative()) {
      pose.translate(0, 5, 0);
    } else if (!ModConfig.CLIENT.enableNewHealthBar.get()) {
      pose.translate(0, -10, 0);
    }
    super.renderSubLayer(guiGraphics, deltaTracker);
    pose.popPose();
  }
}
