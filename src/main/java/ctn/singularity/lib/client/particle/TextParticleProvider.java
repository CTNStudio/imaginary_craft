package ctn.singularity.lib.client.particle;

import com.google.common.collect.HashBiMap;
import ctn.ctnapi.client.util.ColorUtil;
import ctn.singularity.lib.api.lobotomycorporation.LcDamage;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static ctn.singularity.lib.api.lobotomycorporation.LcDamage.PHYSICS;

public record TextParticleProvider(SpriteSet spriteSet) implements ParticleProvider<TextParticleOptions> {
  public static final Map<Integer, String> TEXTURE_MAP = HashBiMap.create();
  static {
    TEXTURE_MAP.put(0, "damage_type/physics");
    TEXTURE_MAP.put(1, "damage_type/spirit");
    TEXTURE_MAP.put(2, "damage_type/erosion");
    TEXTURE_MAP.put(3, "damage_type/the_soul");
    TEXTURE_MAP.put(4, "damage_type/rationality_add");
    TEXTURE_MAP.put(5, "damage_type/rationality_reduce");
    TEXTURE_MAP.put(6, "damage_type/magic");
  }

  @Override
  @NotNull
  public Particle createParticle(@NotNull TextParticleOptions type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
    Result result = getResult(type);
    return new TextParticle(level, result.isTexture() ? result.textureAtlasSprite() : null, x, y, z, type.component(), result.fontColor(), result.strokeColor(), type.isHeal());
  }

  private Result getResult(@NotNull TextParticleOptions type) {
    boolean isTexture = type.isTexture();
    boolean isRationality = type.isRationality();
    boolean isHeal = type.isHeal();

    if (isRationality) {
      int index = isHeal ? 4 : 5;
      int fontColor = ColorUtil.rgbColor(isHeal ? "#78f5ff" : "#a81919");
      int strokeColor = ColorUtil.rgbColor(isHeal ? "#2c80d0" : "#4d0000");
      return new Result(isTexture, getSprite(index), fontColor, strokeColor);
    }

    ResourceKey<DamageType> damageTypeResourceKey = type.damageType().orElse(null);

    if (damageTypeResourceKey == null) {
      return new Result(false, getSprite(0), type.fontColor(), type.strokeColor());
    }

    if (isHeal) {
      return new Result(false, getSprite(0), ColorUtil.rgbColor("#89ff6a"), ColorUtil.rgbColor("#1c501f"));
    }

    // TODO 补充魔法类型

    int index;
    int fontColor;
    int strokeColor;
    LcDamage lcDamage = LcDamage.byDamageType(damageTypeResourceKey);
    switch (lcDamage) {
      case SPIRIT -> {
        fontColor = lcDamage.getColourValue();
        strokeColor = ColorUtil.rgbColor("#9c4e80");
        index = 1;
      }
      case EROSION -> {
        fontColor = lcDamage.getColourValue();
        strokeColor = ColorUtil.rgbColor("#28054a");
        index = 2;
      }
      case THE_SOUL -> {
        fontColor = lcDamage.getColourValue();
        strokeColor = ColorUtil.rgbColor("#074161");
        index = 3;
      }
      case null, default -> {
        fontColor = PHYSICS.getColourValue();
        strokeColor = ColorUtil.rgbColor("#4d0000");
        index = 0;
      }
    }

    return new Result(isTexture, getSprite(index), fontColor, strokeColor);
  }

  private TextureAtlasSprite getSprite(int index) {
    return ((ParticleEngine.MutableSpriteSet) this.spriteSet).sprites.get(index);
  }

  private record Result(boolean isTexture, @Nullable TextureAtlasSprite textureAtlasSprite, int fontColor, int strokeColor) { }
}
