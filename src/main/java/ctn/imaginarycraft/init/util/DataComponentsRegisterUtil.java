package ctn.imaginarycraft.init.util;

import com.mojang.serialization.Codec;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class DataComponentsRegisterUtil {
  protected static Supplier<DataComponentType<Boolean>> recordBoolean(String name, boolean isCacheEncoding) {
    return register(name, Codec.BOOL, ByteBufCodecs.BOOL, isCacheEncoding);
  }

  protected static Supplier<DataComponentType<String>> recordString(String name, boolean isCacheEncoding) {
    return register(name, Codec.STRING, ByteBufCodecs.STRING_UTF8, isCacheEncoding);
  }

  protected static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
    return register(name, () -> builder.apply(DataComponentType.builder()).build());
  }

  protected static <T> Supplier<DataComponentType<T>> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, boolean isCacheEncoding) {
    return register(name, builder -> {
      builder.persistent(codec).networkSynchronized(streamCodec);
      if (isCacheEncoding) {
        builder.cacheEncoding();
      }
      return builder;
    });
  }

  protected static <B extends DataComponentType<?>> DeferredHolder<DataComponentType<?>, B> register(String name, Supplier<? extends B> builder) {
    return ModDataComponents.REGISTRY.register("data_components." + name, builder);
  }
}
