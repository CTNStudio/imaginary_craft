package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.config.ModConfig;
import ctn.imaginarycraft.init.ModAttachments;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 用于注册单一类型伤害吸收护盾
 * <p>
 * 2026/3/18 尘昨暄
 */
public class ModAbsorptionShieldRegistry {
  public record ShieldEntry(
    Holder<MobEffect> effect,
    ResourceLocation damageTypeTag,
    Supplier<AttachmentType<Float>> attachment,
    BiFunction<Integer, Float, Float> initialAmount,
    SoundEvent shieldBreakSound
  ) {
    public void playShieldBreakSound(Player player) {
      if (shieldBreakSound != null) {
        player.playSound(shieldBreakSound);
      }
    }
  }

  private static final List<ShieldEntry> SHIELDS = new ArrayList<>();

  private static void register(
    Holder<MobEffect> effect,
    ResourceLocation damageTypeTag,
    Supplier<AttachmentType<Float>> attachment,
    BiFunction<Integer, Float, Float> initialAmount,
    SoundEvent shieldBreakSound
  ) {
    SHIELDS.add(new ShieldEntry(effect, damageTypeTag, attachment, initialAmount, shieldBreakSound));
  }

  public static List<ShieldEntry> getAll() {
    return SHIELDS;
  }

  public static void init() {
    register(
      ModMobEffects.PHYSIC_ABSORPTION_SHIELD,
      ModDamageTypes.PHYSICS.location(),
      ModAttachments.PHYSIC_DAMAGE_ABSORPTION_AMOUNT,
      (amp, old) -> (float) ((amp + 1) * ModConfig.SERVER.shieldAdditionalValuePerLevel.get()),
      null
    );
    register(
      ModMobEffects.SPIRIT_ABSORPTION_SHIELD,
      ModDamageTypes.SPIRIT.location(),
      ModAttachments.SPIRIT_DAMAGE_ABSORPTION_AMOUNT,
      (amp, old) -> (float) ((amp + 1) * ModConfig.SERVER.shieldAdditionalValuePerLevel.get()),
      null
    );
    register(
      ModMobEffects.EROSION_ABSORPTION_SHIELD,
      ModDamageTypes.EROSION.location(),
      ModAttachments.EROSION_DAMAGE_ABSORPTION_AMOUNT,
      (amp, old) -> (float) ((amp + 1) * ModConfig.SERVER.shieldAdditionalValuePerLevel.get()),
      null
    );
    register(
      ModMobEffects.SOUL_ABSORPTION_SHIELD,
      ModDamageTypes.THE_SOUL.location(),
      ModAttachments.SOUL_DAMAGE_ABSORPTION_AMOUNT,
      (amp, old) -> (float) ((amp + 1) * ModConfig.SERVER.shieldAdditionalValuePerLevel.get()),
      null
    );
  }
}
