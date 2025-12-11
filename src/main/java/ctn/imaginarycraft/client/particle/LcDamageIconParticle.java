package ctn.imaginarycraft.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.init.ModParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LcDamageIconParticle extends TextureSheetParticle {
  public static final ParticleRenderType RENDER_TYPE = new ParticleRenderType() {
    @Override
    public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
      RenderSystem.disableDepthTest();
      RenderSystem.setShader(GameRenderer::getParticleShader);
      RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
      return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public String toString() {
      return "LOBOTOMY_CORPORATION_DAMAGE_ICON_PARTICLE";
    }
  };
  private final int durationTick;

  protected LcDamageIconParticle(
    final ClientLevel level,
    final TextureAtlasSprite sprite,
    final double x,
    final double y,
    final double z,
    final int durationTick
  ) {
    super(level, x, y, z);
    this.durationTick = durationTick;
    setSprite(sprite);
  }

  @Override
  protected int getLightColor(final float partialTick) {
    return LightTexture.FULL_BRIGHT;
  }

  @Override
  public void tick() {
    this.age++;
    if (this.age > this.durationTick) {
      remove();
    }
  }

  @Override
  public @NotNull ParticleRenderType getRenderType() {
    return RENDER_TYPE;
  }

  public record Provider(
    SpriteSet spriteSet) implements ParticleProvider<Options> {
    @Override
    @NotNull
    public Particle createParticle(@NotNull LcDamageIconParticle.Options type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      return new LcDamageIconParticle(level, getTextureAtlasSprite(type), x, y, z, type.isHeal() ? 20 : 20 * 3);
    }

    private TextureAtlasSprite getTextureAtlasSprite(@NotNull LcDamageIconParticle.Options options) {
      boolean isRationality = options.isRationality();
      boolean isHeal = options.isHeal();

      if (isRationality) {
        return getSprite(isHeal ? Type.RATIONALITY_ADD : Type.RATIONALITY_REDUCE);
      }

      if (isHeal) {
        return getSprite(Type.PHYSICS);
      }

      @Nullable Holder<DamageType> damageTypeResourceKey = options.damageType().orElse(null);
      @Nullable LcDamageType lcDamageTypeResourceKey = options.lcDamageType().orElse(null);

      if (damageTypeResourceKey == null) {
        return getSprite(Type.PHYSICS);
      }

      // TODO 补充魔法类型
      return getSprite(switch (lcDamageTypeResourceKey != null ?
        lcDamageTypeResourceKey : LcDamageType.byDamageType(damageTypeResourceKey)) {
        case SPIRIT -> Type.SPIRIT;
        case EROSION -> Type.EROSION;
        case THE_SOUL -> Type.THE_SOUL;
        case null, default -> Type.PHYSICS;
      });
    }

    private TextureAtlasSprite getSprite(Type index) {
      return ((ParticleEngine.MutableSpriteSet) this.spriteSet).sprites.get(index.getIndex());
    }
  }

  public enum Type {
    PHYSICS(0, "damage_type/physics"),
    SPIRIT(1, "damage_type/spirit"),
    EROSION(2, "damage_type/erosion"),
    THE_SOUL(3, "damage_type/the_soul"),
    RATIONALITY_ADD(4, "damage_type/rationality_add"),
    RATIONALITY_REDUCE(5, "damage_type/rationality_reduce"),
    MAGIC(6, "damage_type/magic");

    private final int index;
    private final @javax.annotation.Nullable String texturePl;

    Type(final int index, @javax.annotation.Nullable final String texturePl) {
      this.index = index;
      this.texturePl = texturePl;
    }

    public @javax.annotation.Nullable String getTexturePl() {
      return texturePl;
    }

    public int getIndex() {
      return index;
    }
  }

  public record Options(Optional<@Nullable Holder<DamageType>> damageType,
                        Optional<@Nullable LcDamageType> lcDamageType,
                        boolean isRationality,
                        boolean isHeal) implements ParticleOptions {
    public static final MapCodec<Options> CODEC = RecordCodecBuilder.mapCodec((thisOptionsInstance) -> thisOptionsInstance.group(
        DamageType.CODEC.optionalFieldOf("damageType").forGetter(Options::damageType),
        LcDamageType.CODEC.optionalFieldOf("lcDamageType").forGetter(Options::lcDamageType),
        Codec.BOOL.fieldOf("isRationality").forGetter(Options::isRationality),
        Codec.BOOL.fieldOf("isHeal").forGetter(Options::isHeal))
      .apply(thisOptionsInstance, Options::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Options> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.optional(DamageType.STREAM_CODEC), Options::damageType,
      ByteBufCodecs.optional(LcDamageType.STREAM_CODEC), Options::lcDamageType,
      ByteBufCodecs.BOOL, Options::isRationality,
      ByteBufCodecs.BOOL, Options::isHeal,
      Options::new);

    @Override
    public @NotNull ParticleType<Options> getType() {
      return ModParticleTypes.LC_DAMAGE_ICON.get();
    }
  }
}
