package ctn.imaginarycraft.client.gui.hudlayers;

import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.client.gui.widget.ImageProgressBar;
import ctn.imaginarycraft.common.item.ego.weapon.remote.MagicBulletWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.util.GunWeaponUtil;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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
  private float mainHandValue;
  private float offHandValue;
  private HumanoidArm mainArm = HumanoidArm.RIGHT;

  public GunChargeUpHudLayer(AttackIndicatorStatus attackIndicatorStatus) {
    super();
    if (attackIndicatorStatus == null || attackIndicatorStatus == AttackIndicatorStatus.OFF) {
      throw new IllegalStateException("Unexpected value: " + attackIndicatorStatus);
    }
    this.attackIndicatorStatus = attackIndicatorStatus;
  }

  @Override
  protected void renderDrawLayer(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    ItemStack mainHandItem = player.getMainHandItem();
    ItemStack offHandItem = player.getOffhandItem();
    final Item mainHandItemItem = mainHandItem.getItem();
    final Item offHandItemItem = offHandItem.getItem();
    final boolean isMainArmRight = mainArm == HumanoidArm.RIGHT;

    switch (attackIndicatorStatus) {
      case CROSSHAIR -> {
        if (mainHandValue > 0 && mainHandItemItem instanceof IGunWeapon) {
          int x = leftPos + (isMainArmRight ? -16 / 2 + 15 : -16 / 2 - 15);
          guiGraphics.blitSprite(REMOTE_BOTTOM, x, topPos, 16, 16);
          ResourceLocation texture;
          if (mainHandItemItem instanceof MagicBulletWeaponItem) {
            texture = REMOTE_MAGIC_BULLET;
          } else {
            texture = REMOTE_GUN;
          }
          ImageProgressBar.renderProgressBar(guiGraphics, texture, x, topPos, 16, 16, mainHandValue, 1, true, true);
        }
        if (offHandValue > 0 && !offHandItem.isEmpty() && offHandItemItem instanceof IGunWeapon) {
          int x = leftPos + (!isMainArmRight ? -16 / 2 + 15 : -16 / 2 - 15);
          guiGraphics.blitSprite(REMOTE_BOTTOM, x, topPos, 16, 16);
          ImageProgressBar.renderProgressBar(guiGraphics, REMOTE_GUN, x, topPos, 16, 16, offHandValue, 1, true, true);
        }
      }
      case HOTBAR -> {
        if (mainHandValue > 0 && !mainHandItem.isEmpty() && mainHandItemItem instanceof IGunWeapon) {
          int x = leftPos + (!isMainArmRight ? -91 - 29 : 91);
          guiGraphics.blitSprite(BIG_REMOTE_BOTTOM, x, topPos, 32, 32);
          ResourceLocation texture;
          if (mainHandItemItem instanceof MagicBulletWeaponItem) {
            texture = BIG_REMOTE_MAGIC_BULLET;
          } else {
            texture = BIG_REMOTE_GUN;
          }
          ImageProgressBar.renderProgressBar(guiGraphics, texture, x, topPos, 32, 32, mainHandValue, 1, true, true);
        }
        if (offHandValue > 0 && !offHandItem.isEmpty() && offHandItemItem instanceof IGunWeapon) {
          int x = leftPos + (isMainArmRight ? -91 - 29 : 91);
          guiGraphics.blitSprite(BIG_REMOTE_BOTTOM, x, topPos, 32, 32);
          ImageProgressBar.renderProgressBar(guiGraphics, BIG_REMOTE_GUN, x, topPos, 32, 32, offHandValue, 1, true, true);
        }
      }
    }
  }

  @Override
  public void init(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    super.init(guiGraphics, deltaTracker);

    float mainHandValue = GunWeaponUtil.getChargeUpPercentage(player, InteractionHand.MAIN_HAND);
    if (this.mainHandValue != mainHandValue) {
      this.mainHandValue = mainHandValue;
    }

    float offHandValue = GunWeaponUtil.getChargeUpPercentage(player, InteractionHand.OFF_HAND);
    if (this.offHandValue != offHandValue) {
      this.offHandValue = offHandValue;
    }

    HumanoidArm mainArm = player.getMainArm();
    if (mainArm != this.mainArm) {
      this.mainArm = mainArm;
    }
  }

  @Override
  protected void sizeChange(boolean isWidthChange, boolean isHeightChange, int newScreenWidth, int newScreenHeight) {
    super.sizeChange(isWidthChange, isHeightChange, newScreenWidth, newScreenHeight);
    if (player == null) {
      return;
    }
    if (isWidthChange) {
      setLeftPos(newScreenWidth / 2);
    }
    if (isHeightChange) {
      switch (attackIndicatorStatus) {
        case CROSSHAIR -> setTopPos(newScreenHeight / 2 - 16 / 2);
        case HOTBAR -> setTopPos(newScreenHeight - 32);
        default -> throw new IllegalStateException("Unexpected value: " + attackIndicatorStatus);
      }
    }
  }
}
