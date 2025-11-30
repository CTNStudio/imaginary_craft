package ctn.imaginarycraft.init;

import ctn.imaginarycraft.client.particle.TextParticleOptions;
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

  public static final Supplier<ParticleType<TextParticleOptions>> TEXT_PARTICLE_TYPE = register("text_particle",
    false, TextParticleOptions.CODEC, TextParticleOptions.STREAM_CODEC);
}
