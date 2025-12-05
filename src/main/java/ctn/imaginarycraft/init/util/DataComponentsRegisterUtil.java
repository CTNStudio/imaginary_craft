package ctn.imaginarycraft.init.util;

import com.mojang.serialization.Codec;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class DataComponentsRegisterUtil {
  protected static Supplier<DataComponentType<Boolean>> recordBoolean(String name) {
    return register(name, builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
  }

  protected static Supplier<DataComponentType<String>> recordComponent(String name) {
    return register(name, builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));
  }

  protected static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
    return register(name, () -> builder.apply(DataComponentType.builder()).build());
  }

  protected static <B extends DataComponentType<?>> DeferredHolder<DataComponentType<?>, B> register(String name, Supplier<? extends B> builder) {
    return ModDataComponents.REGISTRY.register("data_components." + name, builder);
  }
}
