package ctn.imaginarycraft.api.data;

import com.mojang.datafixers.util.*;

import java.util.*;
import java.util.function.*;

/**
 * 条件化提供者工厂
 * <p>创建根据实体条件动态返回值的提供者函数</p>
 */
public final class ConditionalProviderFactory {
  public static <T, I> Function<I, T> getProvider(
    T defaultValue,
    List<Pair<Predicate<I>, T>> conditions
  ) {
    return entitypatch -> {
      if (conditions == null || conditions.isEmpty()) {
        return defaultValue;
      }

      for (var condition : conditions) {
        var predicate = condition.getFirst();
        if (predicate == null || !predicate.test(entitypatch)) {
          continue;
        }

        var second = condition.getSecond();
        if (second == null) {
          continue;
        }

        return (T) second;
      }

      return defaultValue;
    };
  }
}
