package ctn.imaginarycraft.client.gui.hudlayers;

import com.mojang.blaze3d.vertex.PoseStack;
import ctn.imaginarycraft.client.gui.hudlayers.shield.ErosionShieldLayer;
import ctn.imaginarycraft.client.gui.hudlayers.shield.PhysicShieldLayer;
import ctn.imaginarycraft.client.gui.hudlayers.shield.SoulShieldLayer;
import ctn.imaginarycraft.client.gui.hudlayers.shield.SpiritShieldLayer;
import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.init.world.ModMobEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public class LeftBarLayer extends CompositeHudLayer {
  public static final LeftBarLayer INSTANCE = new LeftBarLayer();
  public final RationalityBarLayer rationalityBarLayer = new RationalityBarLayer();
  public final NewHealthBarLayer newHealthBarLayer = new NewHealthBarLayer();
  public final PhysicShieldLayer physicShieldLayer = new PhysicShieldLayer();
  public final SpiritShieldLayer spiritShieldLayer = new SpiritShieldLayer();
  public final ErosionShieldLayer erosionShieldLayer = new ErosionShieldLayer();
  public final SoulShieldLayer soulShieldLayer = new SoulShieldLayer();

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
    addLayer(this.physicShieldLayer,
      ()-> !this.minecraft.options.hideGui &&
        this.player != null&&
        !player.isSpectator() &&
        !player.isCreative()&&
        player.hasEffect(ModMobEffects.PHYSIC_ABSORPTION_SHIELD));
    addLayer(this.spiritShieldLayer,
      ()-> !this.minecraft.options.hideGui &&
        this.player != null&&
        !player.isSpectator() &&
        !player.isCreative()&&
        player.hasEffect(ModMobEffects.SPIRIT_ABSORPTION_SHIELD));
    addLayer(this.erosionShieldLayer,
      ()-> !this.minecraft.options.hideGui &&
        this.player != null&&
        !player.isSpectator() &&
        !player.isCreative()&&
        player.hasEffect(ModMobEffects.EROSION_ABSORPTION_SHIELD));
    addLayer(this.soulShieldLayer,
      ()-> !this.minecraft.options.hideGui &&
        this.player != null&&
        !player.isSpectator() &&
        !player.isCreative()&&
        player.hasEffect(ModMobEffects.SOUL_ABSORPTION_SHIELD));
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
