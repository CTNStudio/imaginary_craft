package ctn.imaginarycraft.client.gui.hudlayers.chop_flavor;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.gui.widget.ImageProgressBar;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.ModMobEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;

public class RedEyesTachiChopFlavor extends ChopFlavorLayer.ChopFlavorBar {
  public static final ResourceLocation BOTTOM = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/bottom");
  public static final ResourceLocation BOTTOM_GLOWMASK = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/bottom_glowmask");
  public static final ResourceLocation SCABBARD = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard");
  public static final ResourceLocation SCABBARD_ACTION = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard_action");
  public static final int WIDTH = 32;
  public static final int HEIGHT = 16;
  private float time;
  private int castSkill;

  @Override
  public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, int x, int y) {
    PoseStack pose = guiGraphics.pose();
    pose.pushPose();
    pose.translate(x, y, 0);
    pose.scale(2, 2, 2);
    pose.translate(-WIDTH / 2, 0, 0);

    LocalPlayer player = ChopFlavorLayer.INSTANCE.getPlayer();
    MobEffectInstance effect = player.getEffect(ModMobEffects.RED_EYES_HUNTING);
    guiGraphics.blitSprite(BOTTOM, 0, 0, WIDTH, HEIGHT);
    // TODO EGO共鸣后
    if (false) {
      guiGraphics.setColor(2, 2, 2, 1);
      guiGraphics.blitSprite(BOTTOM_GLOWMASK, 0, 0, WIDTH, HEIGHT);
      guiGraphics.setColor(1, 1, 1, 1);
    }

    int value;
    int maxValue;
    if (effect == null) {
      value = 1;
      maxValue = 1;
    } else {
      // TODO EGO共鸣后改成 200
      if (false) {
        maxValue = 200;
      } else {
        maxValue = 100;
      }
      value = maxValue - effect.getDuration();
    }

    if (castSkill != 1) {
      ImageProgressBar.renderProgressBar(guiGraphics, SCABBARD, 11, 0, 11, 0, WIDTH, HEIGHT, value, maxValue, false, true);
      time = 0;
    }

    if (castSkill == 1) {
      int i = (int) time;
      guiGraphics.blitSprite(SCABBARD_ACTION, WIDTH, 352, 0, HEIGHT * i, 0, 0, WIDTH, HEIGHT);
      time += deltaTracker.getRealtimeDeltaTicks();
      if (i >= (352 / HEIGHT) - 1) {
        castSkill = 0;
      }
    }

    pose.popPose();
  }

  @Override
  public void castSkill() {
    LocalPlayer player = ChopFlavorLayer.INSTANCE.getPlayer();
    MobEffectInstance effect = player.getEffect(ModMobEffects.RED_EYES_HUNTING);
    if (effect == null) {
      castSkill = 1;
    }
  }
}
