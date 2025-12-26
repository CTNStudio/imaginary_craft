package ctn.imaginarycraft.client.particle.magicbullet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.imaginarycraft.client.particle.DyeingMagicCircleParticle;
import ctn.imaginarycraft.client.particle.text.TextParticle;
import ctn.imaginarycraft.init.ModParticleTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MagicBulletMagicCircleParticle extends DyeingMagicCircleParticle {

  protected MagicBulletMagicCircleParticle(
    TextureAtlasSprite sprite,
    ClientLevel level,
    double x,
    double y,
    double z,
    float xRot,
    float yRot,
    float radius
  ) {
    super(sprite, level, x, y, z, xRot, yRot, 0xFFFFFF, radius);
  }

  public static class Builder {
    protected final float xRot;
    protected final float yRot;
    protected float radius;

    public Builder(float xRot, float yRot) {
      this.xRot = xRot;
      this.yRot = yRot;
    }

    public Builder radius(float radius) {
      this.radius = radius;
      return this;
    }

    public Options buildOptions() {
      return new Options(this.xRot, this.yRot, this.radius);
    }
  }

  public record Options(float xRot, float yRot, float radius) implements ParticleOptions {
    public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec((thisOptionsInstance) -> thisOptionsInstance.group(
      Codec.FLOAT.fieldOf("xRot").forGetter(Options::xRot),
      Codec.FLOAT.fieldOf("yRot").forGetter(Options::yRot),
      Codec.FLOAT.fieldOf("radius").forGetter(Options::radius)
    ).apply(thisOptionsInstance, Options::new));

    public static final StreamCodec<ByteBuf, Options> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.FLOAT, Options::xRot,
      ByteBufCodecs.FLOAT, Options::yRot,
      ByteBufCodecs.FLOAT, Options::radius,
      Options::new);

    @Override
    public @NotNull ParticleType<TextParticle.Options> getType() {
      return ModParticleTypes.TEXT.get();
    }
  }

  public static abstract class Provider implements ParticleProvider<Options> {
    private final SpriteSet sprite;

    public Provider(SpriteSet sprite) {
      this.sprite = sprite;
    }

    @Override
    @NotNull
    public Particle createParticle(@NotNull Options type, @NotNull ClientLevel level,
                                   double x, double y, double z,
                                   double xSpeed, double ySpeed, double zSpeed) {
      return new MagicBulletMagicCircleParticle(getSprite(), level, x, y, z, type.xRot(), type.yRot(), type.radius());
    }

    protected List<TextureAtlasSprite> getSpriteLis() {
      return ((ParticleEngine.MutableSpriteSet) sprite).sprites;
    }

    protected abstract TextureAtlasSprite getSprite();
  }

  public static class Provider16x extends Provider {
    public Provider16x(SpriteSet sprite) {
      super(sprite);
    }

    @Override
    protected TextureAtlasSprite getSprite() {
      return getSpriteLis().get(0);
    }
  }

  public static class Provider32x extends Provider {
    public Provider32x(SpriteSet sprite) {
      super(sprite);
    }

    @Override
    protected TextureAtlasSprite getSprite() {
      return getSpriteLis().get(1);
    }
  }

  public static class Provider128x extends Provider {
    public Provider128x(SpriteSet sprite) {
      super(sprite);
    }

    @Override
    protected TextureAtlasSprite getSprite() {
      return getSpriteLis().get(2);
    }
  }
}
