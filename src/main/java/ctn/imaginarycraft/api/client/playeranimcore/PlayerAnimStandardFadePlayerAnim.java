package ctn.imaginarycraft.api.client.playeranimcore;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import com.zigythebird.playeranimcore.enums.FadeType;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PlayerAnimStandardFadePlayerAnim extends PlayerAnimWithFade<PlayerAnimStandardFadePlayerAnim> {
  public static final Codec<EasingType> EASING_TYPE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.BYTE.fieldOf("fromId").forGetter(type -> type.id)
  ).apply(instance, EasingType::fromId));

  public static final StreamCodec<ByteBuf, EasingType> EASING_TYPE_STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.BYTE, type -> type.id,
    EasingType::fromId);

  public static final Codec<PlayerAnimStandardFadePlayerAnim> CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("length").forGetter(PlayerAnimStandardFadePlayerAnim::getLength),
    EASING_TYPE_CODEC.fieldOf("ease").forGetter(PlayerAnimStandardFadePlayerAnim::getEase),
    Codec.FLOAT.optionalFieldOf("easingVariable").forGetter(playerAnimStandardFade -> Optional.ofNullable(playerAnimStandardFade.getEasingVariable())),
    PlayerAnimWithFade.FADE_TYPE_CODEC.fieldOf("fadeType").forGetter(PlayerAnimStandardFadePlayerAnim::getFadeType)
  ).apply(instance, PlayerAnimStandardFadePlayerAnim::new));

  public static final StreamCodec<ByteBuf, PlayerAnimStandardFadePlayerAnim> STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.INT, PlayerAnimStandardFadePlayerAnim::getLength,
    EASING_TYPE_STREAM_CODEC, PlayerAnimStandardFadePlayerAnim::getEase,
    ByteBufCodecs.optional(ByteBufCodecs.FLOAT), playerAnimStandardFade -> Optional.ofNullable(playerAnimStandardFade.getEasingVariable()),
    PlayerAnimWithFade.FADE_TYPE_STREAM_CODEC, PlayerAnimStandardFadePlayerAnim::getFadeType,
    PlayerAnimStandardFadePlayerAnim::new);

  private final EasingType ease;
  private final @Nullable Float easingVariable;

  public PlayerAnimStandardFadePlayerAnim(int length, EasingType ease, @Nullable Float easingVariable, FadeType fadeType) {
    super(length, fadeType);
    this.ease = ease;
    this.easingVariable = easingVariable;
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private PlayerAnimStandardFadePlayerAnim(int length, EasingType ease, Optional<Float> easingVariable, FadeType fadeType) {
    this(length, ease, easingVariable.orElse(null), fadeType);
  }

  public EasingType getEase() {
    return ease;
  }

  public @Nullable Float getEasingVariable() {
    return easingVariable;
  }

  @Override
  public AbstractFadeModifier toModifier() {
    return AbstractFadeModifier.standardFade(getLength(), getEase(), getEasingVariable(), getFadeType());
  }

  @Override
  public Codec<PlayerAnimStandardFadePlayerAnim> getCodec() {
    return CODEC;
  }

  @Override
  public StreamCodec<ByteBuf, PlayerAnimStandardFadePlayerAnim> getStreamCodec() {
    return STREAM_CODEC;
  }
}



