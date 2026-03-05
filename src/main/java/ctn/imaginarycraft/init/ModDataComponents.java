package ctn.imaginarycraft.init;

import com.mojang.serialization.*;
import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.common.components.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.core.component.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

public final class ModDataComponents {
  public static final DeferredRegister<DataComponentType<?>> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.DATA_COMPONENT_TYPE);

  public static final Supplier<DataComponentType<LcDamageType.Component>> LC_DAMAGE_TYPE = register("lobotomy_corporation_damage_type",
    LcDamageType.Component.CODEC, LcDamageType.Component.STREAM_CODEC, true);

  public static final Supplier<DataComponentType<Boolean>> MODE_BOOLEAN = recordBoolean("mode_boolean", true);
  /**
   * 是否正在受到抑制器的影响属性
   */
  public static final Supplier<DataComponentType<Boolean>> IS_RESTRAIN = recordBoolean("is_restrain", true);
  /**
   * 物品四德属性能力使用要求
   */
  public static final Supplier<DataComponentType<ItemVirtueUsageReq>> ITEM_VIRTUE_USAGE_REQ = register("item_virtue_usage_req",
    ItemVirtueUsageReq.CODEC, ItemVirtueUsageReq.STREAM_CODEC, true);

  private static Supplier<DataComponentType<Boolean>> recordBoolean(String name, boolean isCacheEncoding) {
    return register(name, Codec.BOOL, ByteBufCodecs.BOOL, isCacheEncoding);
  }

  private static Supplier<DataComponentType<String>> recordString(String name, boolean isCacheEncoding) {
    return register(name, Codec.STRING, ByteBufCodecs.STRING_UTF8, isCacheEncoding);
  }

  private static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
    return register(name, () -> builder.apply(DataComponentType.builder()).build());
  }

  private static <T> Supplier<DataComponentType<T>> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, boolean isCacheEncoding) {
    return register(name, builder -> {
      builder.persistent(codec).networkSynchronized(streamCodec);
      if (isCacheEncoding) {
        builder.cacheEncoding();
      }
      return builder;
    });
  }

  private static <B extends DataComponentType<?>> DeferredHolder<DataComponentType<?>, B> register(String name, Supplier<? extends B> builder) {
    return ModDataComponents.REGISTRY.register("data_components." + name, builder);
  }
}
