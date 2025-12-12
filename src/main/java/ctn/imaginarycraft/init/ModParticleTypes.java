package ctn.imaginarycraft.init;

import ctn.imaginarycraft.client.particle.DyeingMagicCircleParticle;
import ctn.imaginarycraft.client.particle.LcDamageIconParticle;
import ctn.imaginarycraft.client.particle.magicbullet.MagicBulletMagicCircleParticle;
import ctn.imaginarycraft.client.particle.text.DamageTextParticle;
import ctn.imaginarycraft.client.particle.text.TextParticle;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.util.ParticleTypeRegisterUtil;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * 粒子类型
 */
public final class ModParticleTypes extends ParticleTypeRegisterUtil {
  public static final DeferredRegister<ParticleType<?>> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.PARTICLE_TYPE);

  public static final Supplier<ParticleType<TextParticle.Options>> TEXT = register("text",
    false, TextParticle.Options.CODEC, TextParticle.Options.STREAM_CODEC);
  public static final Supplier<ParticleType<DamageTextParticle.Options>> DAMAGE_TEXT = register("damage_text",
    false, DamageTextParticle.Options.CODEC, DamageTextParticle.Options.STREAM_CODEC);
  public static final Supplier<ParticleType<LcDamageIconParticle.Options>> LC_DAMAGE_ICON = register("lobotomycorporation_damage_icon",
    false, LcDamageIconParticle.Options.CODEC, LcDamageIconParticle.Options.STREAM_CODEC);
  public static final Supplier<ParticleType<DyeingMagicCircleParticle.Options>> DYEING_MAGIC_CIRCLE = register("dyeing_magic_circle",
    false, DyeingMagicCircleParticle.Options.CODEC, DyeingMagicCircleParticle.Options.STREAM_CODEC);
  public static final Supplier<ParticleType<MagicBulletMagicCircleParticle.Options>> MAGIC_BULLET_MAGIC_CIRCLE = register("magic_bullet_magic_circle",
    false, MagicBulletMagicCircleParticle.Options.CODEC, MagicBulletMagicCircleParticle.Options.STREAM_CODEC);
}
