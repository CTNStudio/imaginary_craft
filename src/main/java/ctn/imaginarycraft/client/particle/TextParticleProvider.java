package ctn.imaginarycraft.client.particle;

import ctn.ctnapi.client.util.ColorUtil;
import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType.PHYSICS;

public record TextParticleProvider(
  SpriteSet spriteSet) implements ParticleProvider<TextParticleOptions> {
  @Override
  @NotNull
  public Particle createParticle(@NotNull TextParticleOptions type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
    Result result = getResult(type);
    return new TextParticle(level, result.isTexture() ? result.textureAtlasSprite() : null, x, y, z, type.component(), result.fontColor(), result.strokeColor(), type.isHeal());
  }

  private Result getResult(@NotNull TextParticleOptions options) {
    boolean isTexture = options.isTexture();
    boolean isRationality = options.isRationality();
    boolean isHeal = options.isHeal();

    if (isRationality) {
      TextParticlesType index = isHeal ? TextParticlesType.RATIONALITY_ADD : TextParticlesType.RATIONALITY_REDUCE;
      int fontColor = ColorUtil.rgbColor(isHeal ? "#78f5ff" : "#a81919");
      int strokeColor = ColorUtil.rgbColor(isHeal ? "#2c80d0" : "#4d0000");
      return new Result(isTexture, getSprite(index), fontColor, strokeColor);
    }

    if (isHeal) {
      return new Result(false, getSprite(TextParticlesType.PHYSICS), ColorUtil.rgbColor("#89ff6a"), ColorUtil.rgbColor("#1c501f"));
    }

    @Nullable Holder<DamageType> damageTypeResourceKey = options.damageType().orElse(null);
    @Nullable LcDamageType lcDamageTypeResourceKey = options.lcDamageType().orElse(null);

    if (damageTypeResourceKey == null) {
      return new Result(false, getSprite(TextParticlesType.PHYSICS), options.fontColor(), options.strokeColor());
    }

    // TODO 补充魔法类型

    TextParticlesType type;
    int fontColor;
    int strokeColor;
    LcDamageType lcDamageType = lcDamageTypeResourceKey != null ?
      lcDamageTypeResourceKey : LcDamageType.byDamageType(damageTypeResourceKey);
    switch (lcDamageType) {
      case SPIRIT -> {
        fontColor = lcDamageType.getColourValue();
        strokeColor = ColorUtil.rgbColor("#9c4e80");
        type = TextParticlesType.SPIRIT;
      }
      case EROSION -> {
        fontColor = lcDamageType.getColourValue();
        strokeColor = ColorUtil.rgbColor("#28054a");
        type = TextParticlesType.EROSION;
      }
      case THE_SOUL -> {
        fontColor = lcDamageType.getColourValue();
        strokeColor = ColorUtil.rgbColor("#074161");
        type = TextParticlesType.THE_SOUL;
      }
      case null, default -> {
        fontColor = PHYSICS.getColourValue();
        strokeColor = ColorUtil.rgbColor("#4d0000");
        type = TextParticlesType.PHYSICS;
      }
    }

    return new Result(isTexture, getSprite(type), fontColor, strokeColor);
  }

  private TextureAtlasSprite getSprite(TextParticlesType index) {
    return ((ParticleEngine.MutableSpriteSet) this.spriteSet).sprites.get(index.getIndex());
  }

  private record Result(boolean isTexture, @Nullable TextureAtlasSprite textureAtlasSprite,
                        int fontColor, int strokeColor) {
  }
}
