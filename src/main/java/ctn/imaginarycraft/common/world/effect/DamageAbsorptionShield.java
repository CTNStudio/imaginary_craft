package ctn.imaginarycraft.common.world.effect;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * 2026/3/18 尘昨暄
 */
public class DamageAbsorptionShield extends ModMobEffect {
  public DamageAbsorptionShield(MobEffectCategory category, int color, SoundEvent sound) {
    super(category, color);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
    return false;
  }
}
