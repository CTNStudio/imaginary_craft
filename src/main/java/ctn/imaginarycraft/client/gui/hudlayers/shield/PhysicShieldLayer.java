package ctn.imaginarycraft.client.gui.hudlayers.shield;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.ModMobEffects;
import net.minecraft.resources.ResourceLocation;

public class PhysicShieldLayer extends ShieldBarLayer{
  protected static final ResourceLocation TEXTURE = ImaginaryCraft.modRl("hud_bar/shield/physic_shield_bar");
  protected static final ResourceLocation BOTTOM_TEXTURE = ImaginaryCraft.modRl("hud_bar/shield/physic_shield_bar_bottom");
  protected static final ResourceLocation LIGHT_TEXTURE = ImaginaryCraft.modRl("hud_bar/shield/physic_shield_bar_light");

  public PhysicShieldLayer() {
    super(TEXTURE, BOTTOM_TEXTURE, LIGHT_TEXTURE, ModMobEffects.PHYSIC_ABSORPTION_SHIELD);
  }
}
