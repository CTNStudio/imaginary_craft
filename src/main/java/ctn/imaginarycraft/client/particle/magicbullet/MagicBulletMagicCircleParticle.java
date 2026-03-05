package ctn.imaginarycraft.client.particle.magicbullet;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import ctn.imaginarycraft.client.particle.*;
import ctn.imaginarycraft.init.*;
import io.netty.buffer.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.codec.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MagicBulletMagicCircleParticle extends DyeingMagicCircleParticle {

  protected MagicBulletMagicCircleParticle(
    TextureAtlasSprite sprite,
    ClientLevel level,
    double x,
    double y,
    double z,
    float xRot,
    float yRot,
    float radius,
    int particleLifeTime
  ) {
    super(sprite, level, x, y, z, xRot, yRot, 0xFFFFFF, radius, particleLifeTime);
  }

  public static class Builder {
    protected final float xRot;
    protected final float yRot;
    protected float radius;
    protected int particleLifeTime = 20;

    public Builder(float xRot, float yRot) {
      this.xRot = xRot;
      this.yRot = yRot;
    }

    public Builder radius(float radius) {
      this.radius = radius;
      return this;
    }

    public Builder particleLifeTime(int particleLifeTime) {
      this.particleLifeTime = particleLifeTime;
      return this;
    }

    public Options buildOptions(int index) {
      return new Options(this.xRot, this.yRot, this.radius, this.particleLifeTime, index);
    }
  }

  public record Options(float xRot, float yRot, float radius,
                        int particleLifeTime, int index) implements ParticleOptions {
    public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec((thisOptionsInstance) -> thisOptionsInstance.group(
      Codec.FLOAT.fieldOf("xRot").forGetter(Options::xRot),
      Codec.FLOAT.fieldOf("yRot").forGetter(Options::yRot),
      Codec.FLOAT.fieldOf("radius").forGetter(Options::radius),
      Codec.INT.fieldOf("particleLifeTime").forGetter(Options::particleLifeTime),
      Codec.INT.fieldOf("index").forGetter(Options::index)
    ).apply(thisOptionsInstance, Options::new));

    public static final StreamCodec<ByteBuf, Options> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.FLOAT, Options::xRot,
      ByteBufCodecs.FLOAT, Options::yRot,
      ByteBufCodecs.FLOAT, Options::radius,
      ByteBufCodecs.INT, Options::particleLifeTime,
      ByteBufCodecs.INT, Options::index,
      Options::new);

    @Override
    public @NotNull ParticleType<Options> getType() {
      return ModParticleTypes.MAGIC_BULLET_MAGIC_CIRCLE.get();
    }
  }

  public static class Provider implements ParticleProvider<Options> {
    private final SpriteSet sprite;

    public Provider(SpriteSet sprite) {
      this.sprite = sprite;
    }

    @Override
    @NotNull
    public Particle createParticle(@NotNull Options options, @NotNull ClientLevel level,
                                   double x, double y, double z,
                                   double xSpeed, double ySpeed, double zSpeed) {
      return new MagicBulletMagicCircleParticle(getSpriteLis().get(options.index()), level, x, y, z, options.xRot(), options.yRot(), options.radius(), options.particleLifeTime());
    }

    protected List<TextureAtlasSprite> getSpriteLis() {
      return ((ParticleEngine.MutableSpriteSet) sprite).sprites;
    }
  }
}
