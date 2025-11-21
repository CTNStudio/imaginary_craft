package ctn.singularity.lib.init.util;

import com.mojang.serialization.MapCodec;
import ctn.singularity.lib.init.LibParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class ParticleTypeRegisterUtil {
  protected static <T extends ParticleOptions> @NotNull Supplier<ParticleType<T>> register(String id,
                                                                                           boolean overrideLimiter,
                                                                                           MapCodec<T> mapCodec,
                                                                                           StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
    return LibParticleTypes.REGISTRY.register(
      id, () -> new SpecialParticleType<>(overrideLimiter, mapCodec, streamCodec));
  }

  protected static class SpecialParticleType<T extends ParticleOptions> extends ParticleType<T> {
    private final StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;
    private final MapCodec<T> codec;

    protected SpecialParticleType(final boolean overrideLimitter, MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
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
