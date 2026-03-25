package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.config.ModConfig;
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
 * 护盾添加方法：
 * 1.注册新的护盾效果
 * 2.注册ModAttachments
 * 3.在此注册即可
 *
 * <P>
 * 2026/3/18 尘昨暄
 */
public final class ModAbsorptionShieldsRegistry {
  public record ShieldEntry(
    Holder<MobEffect> effect,
    ResourceLocation damageTypeTag,
    Supplier<AttachmentType<Float>> attachment,
    BiFunction<Integer, Float, Float> initialAmount,
    SoundEvent shieldBreakSound,
    boolean shieldConflict
  ) {
    public void playShieldBreakSound(Player player) {
      if (shieldBreakSound != null) {
        player.playSound(shieldBreakSound);
      }
    }
    public boolean isShieldConflict(){
      return shieldConflict;
    }
  }

  private static final List<ShieldEntry> SHIELDS = new ArrayList<>();

  private static void register(
    Holder<MobEffect> effect,
    ResourceLocation damageTypeTag,
    Supplier<AttachmentType<Float>> attachment,
    BiFunction<Integer, Float, Float> initialAmount,
    SoundEvent shieldBreakSound,
    boolean shieldConflict
  ) {
    SHIELDS.add(new ShieldEntry(effect, damageTypeTag, attachment, initialAmount, shieldBreakSound,shieldConflict));
  }

  private static void register(
    Holder<MobEffect> effect,
    ResourceLocation damageTypeTag,
    Supplier<AttachmentType<Float>> attachment,
    BiFunction<Integer, Float, Float> initialAmount,
    SoundEvent shieldBreakSound
  ) {
    register(effect, damageTypeTag, attachment, initialAmount, shieldBreakSound, true);
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
