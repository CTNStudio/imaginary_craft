package ctn.imaginarycraft.api.client.playeranimcore;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.enums.FadeType;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public abstract class PlayerAnimWithFade<T extends PlayerAnimWithFade<T>> {
  public static final Codec<FadeType> FADE_TYPE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.STRING.fieldOf("name").forGetter(FadeType::name)
  ).apply(instance, FadeType::valueOf));

  public static final StreamCodec<ByteBuf, FadeType> FADE_TYPE_STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.STRING_UTF8, FadeType::name,
    FadeType::valueOf);

  private final int length;
  private final FadeType fadeType;

  public PlayerAnimWithFade(int length, FadeType fadeType) {
    this.fadeType = fadeType;
    this.length = length;
  }

  public int getLength() {
    return length;
  }

  public FadeType getFadeType() {
    return fadeType;
  }

  public abstract AbstractFadeModifier toModifier();

  public abstract Codec<T> getCodec();

  public abstract StreamCodec<ByteBuf, T> getStreamCodec();
}
