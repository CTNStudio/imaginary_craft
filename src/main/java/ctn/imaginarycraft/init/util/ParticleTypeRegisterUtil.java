package ctn.imaginarycraft.init.util;

import com.mojang.serialization.MapCodec;
import ctn.imaginarycraft.init.ModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class ParticleTypeRegisterUtil {
  protected static <T extends ParticleOptions> @NotNull DeferredHolder<ParticleType<?>, ParticleType<T>> register(
    String id,
    boolean overrideLimiter,
    MapCodec<T> mapCodec,
    StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec
  ) {
    return register(
      id, () -> new SpecialParticleType<>(overrideLimiter, mapCodec, streamCodec));
  }

  protected static DeferredHolder<ParticleType<?>, SimpleParticleType> registerSimpleParticle(String id, boolean overrideLimiter) {
    return register(id, () -> new SimpleParticleType(overrideLimiter));
  }

  protected static <O extends ParticleType<?>> DeferredHolder<ParticleType<?>, O> register(String id, Supplier<O> particleType) {
    return ModParticleTypes.REGISTRY.register(id, particleType);
  }

  protected static class SpecialParticleType<T extends ParticleOptions> extends ParticleType<T> {
    private final StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;
    private final MapCodec<T> codec;

    protected SpecialParticleType(
      final boolean overrideLimitter,
      MapCodec<T> codec,
      StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec
    ) {
      super(overrideLimitter);
      this.codec = codec;
      this.streamCodec = streamCodec;
    }

    @Override
    public MapCodec<T> codec() {
      return codec;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
      return streamCodec;
    }
  }
}
