package ctn.imaginarycraft.client.particle.text;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import ctn.imaginarycraft.init.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import org.jetbrains.annotations.*;

// TODO 等待完成动画
public class DamageTextParticle extends TextParticle {
  protected final boolean isHeal;

  protected DamageTextParticle(final ClientLevel level, final double x, final double y, final double z, final Builder builder, final boolean isHeal) {
    super(level, x, y, z, builder);
    this.isHeal = isHeal;
  }

  public record Options(TextParticle.Options options, boolean isHeal) implements ParticleOptions {
    public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
      TextParticle.Options.CODEC.fieldOf("options").forGetter(Options::options),
      Codec.BOOL.fieldOf("isHeal").forGetter(Options::isHeal)
    ).apply(instance, Options::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Options> STREAM_CODEC = StreamCodec.composite(
      TextParticle.Options.STREAM_CODEC, Options::options,
      ByteBufCodecs.BOOL, Options::isHeal,
      Options::new);

    @Override
    public @NotNull ParticleType<?> getType() {
      return ModParticleTypes.DAMAGE_TEXT.get();
    }
  }

  public static class Provider implements ParticleProvider<Options> {
    @Override
    @NotNull
    public Particle createParticle(@NotNull DamageTextParticle.Options type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      return new DamageTextParticle(level, x, y, z, type.options.getBuild(), type.isHeal);
    }
  }
}
