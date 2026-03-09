package ctn.imaginarycraft.api.data;

import com.mojang.datafixers.util.*;
import yesman.epicfight.world.capabilities.entitypatch.*;

import java.util.*;
import java.util.function.*;

/**
 * 条件化提供者工厂
 * <p>创建根据实体条件动态返回值的提供者函数</p>
 */
public final class ConditionalProviderFactory {

  /**
   * 创建条件化提供者函数
   * <p>根据实体条件动态返回值，无匹配条件时返回默认值</p>
   *
   * @param defaultValue 默认值
   * @param conditions   条件列表（谓词 - 值对）
   * @param <T>          返回值类型
   * @return 条件化提供者函数
   */
  public static <T> Function<LivingEntityPatch<?>, T> getProvider(
    T defaultValue,
    List<Pair<Predicate<LivingEntityPatch<?>>, T>> conditions
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
