package ctn.imaginarycraft.client.gui.layers;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

public class NewHealthBarLayer extends BasicDrawLayer {
  public static final NewHealthBarLayer INSTANCE = new NewHealthBarLayer();
  public static final ResourceLocation TEXTURE = ImaginaryCraft.modRl("hud_bar/rationality_bar");
  private static final int WIDTH = 80;
  private static final int HEIGHT = 9;
  private static final int SHIELD_WIDTH = 80;
  private static final int SHIELD_HEIGHT = 9;

  private float hp;
  private float maxHp;
  //  private final HorizontalStatusBar statusBar;
//  private final HorizontalStatusBar shieldStatusBar;
  private Gui.HeartType heartType = Gui.HeartType.NORMAL;

  public NewHealthBarLayer() {
//    this.statusBar = new HorizontalStatusBar(WIDTH, HEIGHT,
//      new HorizontalStatusBar.TextureLayer(0, 0, 43, 36, 80, 9, 5),
//      new HorizontalStatusBar.TextureLayer(2, 2, 51, 3, 80, 78, 7),
//      new HorizontalStatusBar.TextureLayer(0, 0, 209, 16, 80, 9, 5),
//      512, 128,
//      TEXTURE);
//    this.shieldStatusBar = new HorizontalStatusBar(SHIELD_WIDTH, SHIELD_HEIGHT,
//      new HorizontalStatusBar.TextureLayer(0, 0, 42, 1, 82, 11, 0),
//      new HorizontalStatusBar.TextureLayer(0, 0, 125, 1, 82, 11, 0),
//      new HorizontalStatusBar.TextureLayer(0, 0, 208, 1, 82, 11, 0),
//      512, 128,
//      TEXTURE);
  }

  @Override
  public void init(final GuiGraphics guiGraphics) {
    super.init(guiGraphics);

    LocalPlayer player = this.player;
    float newHp = player.getHealth();
    if (newHp != this.hp) {
      this.hp = newHp;
    }

    float newMaxHp = player.getMaxHealth();
    if (newMaxHp != this.maxHp) {
      this.maxHp = newMaxHp;
    }

    Gui.HeartType heartType = Gui.HeartType.forPlayer(player);
    if (heartType != this.heartType) {
      this.heartType = heartType;
    }
  }

  @Override
  public void renderDrawLayer(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker) {

  }
}
