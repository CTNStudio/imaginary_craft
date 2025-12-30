package ctn.imaginarycraft.client.gui.hudlayers;

import ctn.imaginarycraft.client.gui.widget.ImageProgressBar;
import ctn.imaginarycraft.common.item.ego.weapon.remote.MagicBulletWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.GunWeaponUtil;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class GunChargeUpHudLayer extends BasicHudLayer {

  public static final ResourceLocation REMOTE_BOTTOM = ImaginaryCraft.modRl("hud_bar/remote_bottom");
  public static final ResourceLocation REMOTE_GUN = ImaginaryCraft.modRl("hud_bar/remote_gun");
  public static final ResourceLocation REMOTE_MAGIC_BULLET = ImaginaryCraft.modRl("hud_bar/remote_magic_bullet");

  public static final ResourceLocation BIG_REMOTE_BOTTOM = ImaginaryCraft.modRl("hud_bar/big_remote_bottom");
  public static final ResourceLocation BIG_REMOTE_GUN = ImaginaryCraft.modRl("hud_bar/big_remote_gun");
  public static final ResourceLocation BIG_REMOTE_MAGIC_BULLET = ImaginaryCraft.modRl("hud_bar/big_remote_magic_bullet");

  public static final GunChargeUpHudLayer INSTANCE_CROSSHAIR = new GunChargeUpHudLayer(AttackIndicatorStatus.CROSSHAIR);
  public static final GunChargeUpHudLayer INSTANCE_HOTBAR = new GunChargeUpHudLayer(AttackIndicatorStatus.HOTBAR);

  private final AttackIndicatorStatus attackIndicatorStatus;
  private float gunChargeUpPercentageValue;

  public GunChargeUpHudLayer(AttackIndicatorStatus attackIndicatorStatus) {
    super();
    if (attackIndicatorStatus == null || attackIndicatorStatus == AttackIndicatorStatus.OFF) {
      throw new IllegalStateException("Unexpected value: " + attackIndicatorStatus);
    }
    this.attackIndicatorStatus = attackIndicatorStatus;
  }

  @Override
  protected void renderDrawLayer(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    switch (attackIndicatorStatus) {
      case CROSSHAIR -> {
        guiGraphics.blitSprite(REMOTE_BOTTOM, leftPos, topPos, 16, 16);
        ResourceLocation texture = REMOTE_GUN;
        if (player.getMainHandItem().getItem() instanceof MagicBulletWeaponItem) {
          texture = REMOTE_MAGIC_BULLET;
        }
        ImageProgressBar.renderProgressBar(guiGraphics, texture, leftPos, topPos, 16, 16, gunChargeUpPercentageValue, 1, true, true);
      }
      case HOTBAR -> {
        guiGraphics.blitSprite(BIG_REMOTE_BOTTOM, leftPos, topPos, 32, 32);
        ResourceLocation texture = BIG_REMOTE_GUN;
        if (player.getMainHandItem().getItem() instanceof MagicBulletWeaponItem) {
          texture = BIG_REMOTE_MAGIC_BULLET;
        }
        ImageProgressBar.renderProgressBar(guiGraphics, texture, leftPos, topPos, 32, 32, gunChargeUpPercentageValue, 1, true, true);
      }
    }
  }

  @Override
  public void init(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    super.init(guiGraphics, deltaTracker);
    float gunChargeUpPercentageValue = GunWeaponUtil.getChargeUpPercentage(player);

    if (this.gunChargeUpPercentageValue != gunChargeUpPercentageValue) {
      this.gunChargeUpPercentageValue = gunChargeUpPercentageValue;
    }
  }

  @Override
  protected void sizeChange(boolean isWidthChange, boolean isHeightChange, int newScreenWidth, int newScreenHeight) {
    super.sizeChange(isWidthChange, isHeightChange, newScreenWidth, newScreenHeight);
    if (player == null) {
      return;
    }
    if (isWidthChange) {
      switch (attackIndicatorStatus) {
        case CROSSHAIR -> setLeftPos(newScreenWidth / 2 - 16 / 2 + 15);
        case HOTBAR ->
          setLeftPos(newScreenWidth / 2 + (player.getMainArm() != HumanoidArm.RIGHT ? -91 - 29 : 91));
        default -> throw new IllegalStateException("Unexpected value: " + attackIndicatorStatus);
      }
    }
    if (isHeightChange) {
      switch (attackIndicatorStatus) {
        case CROSSHAIR -> setTopPos(newScreenHeight / 2 - 16 / 2);
        case HOTBAR -> setTopPos(newScreenHeight - 32);
        default -> throw new IllegalStateException("Unexpected value: " + attackIndicatorStatus);
      }
    }
  }

  @Override
  public int getWidth() {
    return switch (attackIndicatorStatus) {
      case CROSSHAIR -> 16;
      case HOTBAR -> 32;
      default -> throw new IllegalStateException("Unexpected value: " + attackIndicatorStatus);
    };
  }

  @Override
  public int getHeight() {
    return switch (attackIndicatorStatus) {
      case CROSSHAIR -> 16;
      case HOTBAR -> 32;
      default -> throw new IllegalStateException("Unexpected value: " + attackIndicatorStatus);
    };
  }
}
