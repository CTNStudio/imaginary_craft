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
  public static final ResourceLocation SCABBARD = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard");
  public static final ResourceLocation SCABBARD_BROKEN0 = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard_broken0");
  public static final ResourceLocation SCABBARD_BROKEN1 = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard_broken1");
  public static final ResourceLocation SCABBARD_BROKEN2 = ImaginaryCraft.modRl("chop_flavor/red_eyes_tachi/scabbard_broken2");

  @Override
  public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, int x, int y) {
    PoseStack pose = guiGraphics.pose();
    pose.pushPose();
    pose.translate(x, y, 0);
    pose.scale(1, 1, 1);
    pose.translate(-35f, 0, 0);

    LocalPlayer player = ChopFlavorLayer.INSTANCE.getPlayer();
    MobEffectInstance effect = player.getEffect(ModMobEffects.RED_EYES_HUNTING);
    guiGraphics.blitSprite(BOTTOM, 0, 0, 70, 16);

    int value;
    int maxValue;
    if (effect == null) {
      value = 1;
      maxValue = 1;
    } else {
      // TODO EGO共鸣后改成 200
      maxValue = 100;
      value = maxValue - effect.getDuration();
    }

    ImageProgressBar.renderProgressBar(guiGraphics,
      SCABBARD,
      20, 0,
      20, 0,
      70, 16,
      value, maxValue,
      false, true);
    pose.popPose();
  }
}
