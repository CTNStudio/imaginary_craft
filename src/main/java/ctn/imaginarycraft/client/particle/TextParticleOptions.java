package ctn.imaginarycraft.client.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import ctn.imaginarycraft.init.ModParticleTypes;
import ctn.imaginarycraft.network.codec.ModStreamCodecs;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record TextParticleOptions(Component component,
                                  Optional<@Nullable Holder<DamageType>> damageType,
                                  Optional<@Nullable LcDamageType> lcDamageType,
                                  int fontColor,
                                  int strokeColor,
                                  boolean isRationality,
                                  boolean isHeal,
                                  boolean isTexture) implements ParticleOptions {
  public static final MapCodec<TextParticleOptions> CODEC = RecordCodecBuilder.mapCodec((thisOptionsInstance) -> thisOptionsInstance.group(
      ComponentSerialization.CODEC.fieldOf("component").forGetter(TextParticleOptions::component),
      DamageType.CODEC.optionalFieldOf("damageType").forGetter(TextParticleOptions::damageType),
      LcDamageType.CODEC.optionalFieldOf("lcDamageType").forGetter(TextParticleOptions::lcDamageType),
      Codec.INT.fieldOf("fontColor").forGetter(TextParticleOptions::fontColor),
      Codec.INT.fieldOf("strokeColor").forGetter(TextParticleOptions::strokeColor),
      Codec.BOOL.fieldOf("isRationality").forGetter(TextParticleOptions::isRationality),
      Codec.BOOL.fieldOf("isHeal").forGetter(TextParticleOptions::isHeal),
      Codec.BOOL.fieldOf("isTexture").forGetter(TextParticleOptions::isTexture))
    .apply(thisOptionsInstance, TextParticleOptions::new));

  public static final StreamCodec<RegistryFriendlyByteBuf, TextParticleOptions> STREAM_CODEC = ModStreamCodecs.composite(
    ComponentSerialization.STREAM_CODEC, TextParticleOptions::component,
    ByteBufCodecs.optional(DamageType.STREAM_CODEC), TextParticleOptions::damageType,
    ByteBufCodecs.optional(LcDamageType.STREAM_CODEC), TextParticleOptions::lcDamageType,
    ByteBufCodecs.INT, TextParticleOptions::fontColor,
    ByteBufCodecs.INT, TextParticleOptions::strokeColor,
    ByteBufCodecs.BOOL, TextParticleOptions::isRationality,
    ByteBufCodecs.BOOL, TextParticleOptions::isHeal,
    ByteBufCodecs.BOOL, TextParticleOptions::isTexture,
    TextParticleOptions::new);

  @Override
  public @NotNull ParticleType<TextParticleOptions> getType() {
    return ModParticleTypes.TEXT_PARTICLE_TYPE.get();
  }
}
