package ctn.imaginarycraft.init.world.effect;

import ctn.imaginarycraft.common.world.effect.ModMobEffect;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectCategory;

import static com.lowdragmc.lowdraglib2.gui.util.UISoundUtils.playSound;
/**
 * 2026/3/18 尘昨暄
 */
public class DamageAbsorptionShield extends ModMobEffect {
  public DamageAbsorptionShield(MobEffectCategory category, int color, SoundEvent  sound) {
    super(category, color);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
    return false;
  }
}
