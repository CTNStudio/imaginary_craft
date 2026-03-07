package ctn.imaginarycraft.api.data;

import com.google.common.collect.*;
import com.mojang.datafixers.util.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import org.apache.logging.log4j.*;
import org.jetbrains.annotations.*;
import yesman.epicfight.data.conditions.*;
import yesman.epicfight.registry.entries.*;
import yesman.epicfight.world.capabilities.entitypatch.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * 模组武器类型重载监听器 - 提供条件化配置解析工具方法
 * <p>支持基于实体条件的动态配置，用于武器能力的条件化设置</p>
 */
public final class ModWeaponTypeReloadListener {
  private static final Logger LOGGER = ImaginaryCraft.LOGGER;
  private static final String CASES_TAG = "cases";
  private static final String CONDITIONS_TAG = "conditions";
  private static final String VALUE_TAG = "value";
  private static final String DEFAULT_TAG = "default";
  private static final String PREDICATE_TAG = "predicate";

  /**
   * 从父标签中解析带条件的配置项列表（使用函数获取 Tag 值，验证 Tag）
   *
   * @param parentTag    父配置标签
   * @param tagName      标签名称
   * @param getValueFunc 获取值的函数
   * @param validator    验证器
   * @param <T>          配置值类型
   * @return 条件配置项列表，解析失败时返回 null
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromTag(
    CompoundTag parentTag,
    String tagName,
    Function<Tag, T> getValueFunc,
    BiPredicate<T, Tag> validator
  ) {
    return parseConditionalEntriesFromParentTag(parentTag, tagName, tag -> tag.get(VALUE_TAG), getValueFunc, validator);
  }

  /**
   * 从配置复合标签中解析带条件的配置项列表（使用函数获取 Tag 值，验证 Tag）
   *
   * @param configCompTag 配置复合标签
   * @param getValueFunc  获取值的函数
   * @param validator     验证器
   * @param <T>           配置值类型
   * @return 条件配置项列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromTag(
    CompoundTag configCompTag,
    Function<Tag, T> getValueFunc,
    BiPredicate<T, Tag> validator
  ) {
    return parseConditionalEntries(configCompTag, tag -> tag.get(VALUE_TAG), getValueFunc, validator);
  }

  /**
   * 解析单个条件配置项（使用函数获取 Tag 值，验证 Tag）
   *
   * @param getValueFunc 获取值的函数
   * @param validator    验证器
   * @param caseCompTag  配置复合标签
   * @param <T>          配置值类型
   * @return 条件配置项，包含谓词和值
   */
  public static <T> Pair<Predicate<LivingEntityPatch<?>>, T> parseCaseEntryFromTag(
    Function<Tag, T> getValueFunc,
    BiPredicate<T, Tag> validator,
    CompoundTag caseCompTag
  ) {
    return parseCaseEntryInternal(tag -> caseCompTag.get(VALUE_TAG), getValueFunc, validator, caseCompTag);
  }

  /**
   * 从父标签中解析带条件的配置项列表（使用 Registry，验证 String）
   *
   * @param parentTag 父配置标签
   * @param tagName   标签名称
   * @param registry  注册表
   * @param validator 验证器
   * @param <T>       配置值类型
   * @return 条件配置项列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromString(
    CompoundTag parentTag,
    String tagName,
    Registry<T> registry,
    BiPredicate<T, String> validator
  ) {
    return parseConditionalEntriesFromParentTag(parentTag, tagName, tag -> tag.getString(VALUE_TAG), valueString -> registry.get(ResourceLocation.parse(valueString)), validator);
  }

  /**
   * 从父标签中解析带条件的配置项列表（使用函数获取值）
   *
   * @param parentTag    父配置标签
   * @param tagName      标签名称
   * @param getValueFunc 获取值的函数
   * @param validator    验证器
   * @param <T>          配置值类型
   * @return 条件配置项列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntries(
    CompoundTag parentTag,
    String tagName,
    Function<String, T> getValueFunc,
    BiPredicate<T, String> validator
  ) {
    return parseConditionalEntriesFromParentTag(parentTag, tagName, tag -> tag.getString(VALUE_TAG), getValueFunc, validator);
  }

  /**
   * 从配置复合标签中解析带条件的配置项列表
   *
   * @param configCompTag 配置复合标签
   * @param getValueFunc  获取值的函数
   * @param validator     验证器
   * @param <T>           配置值类型
   * @return 条件配置项列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntries(
    CompoundTag configCompTag,
    Function<String, T> getValueFunc,
    BiPredicate<T, String> validator
  ) {
    return parseConditionalEntries(configCompTag, tag -> tag.getString(VALUE_TAG), getValueFunc, validator);
  }

  /**
   * 解析单个条件配置项
   *
   * @param getValueFunc 获取值的函数
   * @param validator    验证器
   * @param caseCompTag  配置复合标签
   * @param <T>          配置值类型
   * @return 条件配置项，包含谓词和值
   */
  public static <T> Pair<Predicate<LivingEntityPatch<?>>, T> parseCaseEntry(
    Function<String, T> getValueFunc,
    BiPredicate<T, String> validator,
    CompoundTag caseCompTag
  ) {
    return parseCaseEntryInternal(tag -> tag.getString(VALUE_TAG), getValueFunc, validator, caseCompTag);
  }

  /**
   * 组合多个条件为复合谓词（所有条件必须满足）
   *
   * @param conditionList 条件列表
   * @return 复合谓词
   */
  public static Predicate<LivingEntityPatch<?>> composePredicate(List<Condition.EntityPatchCondition> conditionList) {
    return entitypatch -> conditionList.stream()
      .allMatch(condition -> condition.predicate(entitypatch));
  }

  /**
   * 从配置复合标签中解析带条件的配置项列表（使用 Registry，验证 String）
   *
   * @param configCompTag 配置复合标签
   * @param registry      注册表
   * @param validator     验证器
   * @param <T>           配置值类型
   * @return 条件配置项列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromString(
    CompoundTag configCompTag,
    Registry<T> registry,
    BiPredicate<T, String> validator
  ) {
    return parseConditionalEntries(configCompTag, tag -> tag.getString(VALUE_TAG), valueString -> registry.get(ResourceLocation.parse(valueString)), validator);
  }

  //region 内部方法 - 提取的公共逻辑

  /**
   * 从父标签中解析条件配置项（内部实现）
   *
   * @param parentTag       父配置标签
   * @param tagName         标签名称
   * @param getValueObjFunc 获取值对象的函数
   * @param getValueFunc    获取最终值的函数
   * @param validator       验证器
   * @param <T>             最终值类型
   * @param <V>             值对象类型
   * @return 条件配置项列表，解析失败时返回 null
   */
  private static <T, V> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromParentTag(
    CompoundTag parentTag,
    String tagName,
    Function<CompoundTag, V> getValueObjFunc,
    Function<V, T> getValueFunc,
    BiPredicate<T, V> validator
  ) {
    Tag configTag = parentTag.get(tagName);
    if (configTag instanceof CompoundTag configCompTag) {
      return parseConditionalEntries(configCompTag, getValueObjFunc, getValueFunc, validator);
    }

    LOGGER.warn("Invalid config tag [{}]: expected CompoundTag, but got {}", tagName,
      configTag != null ? configTag.getClass().getSimpleName() : "null");
    return null;
  }


  /**
   * 从配置复合标签中解析条件配置项列表（内部实现）
   *
   * @param configCompTag   配置复合标签
   * @param getValueObjFunc 获取值对象的函数
   * @param getValueFunc    获取最终值的函数
   * @param validator       验证器
   * @param <T>             最终值类型
   * @param <V>             值对象类型
   * @return 条件配置项列表
   */
  public static <T, V> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntries(
    CompoundTag configCompTag,
    Function<CompoundTag, V> getValueObjFunc,
    Function<V, T> getValueFunc,
    BiPredicate<T, V> validator
  ) {
    ListTag casesList = configCompTag.getList(CASES_TAG, Tag.TAG_COMPOUND);
    return casesList.stream()
      .map(CompoundTag.class::cast)
      .map(caseTag -> parseCaseEntryInternal(getValueObjFunc, getValueFunc, validator, caseTag))
      .collect(Collectors.toList());
  }

  /**
   * 解析单个条件配置项（内部实现）
   *
   * @param getValueObjFunc 获取值对象的函数
   * @param getValueFunc    获取最终值的函数
   * @param validator       验证器
   * @param caseCompTag     配置复合标签
   * @param <T>             最终值类型
   * @param <V>             值对象类型
   * @return 条件配置项，包含谓词和值
   */
  public static <T, V> Pair<Predicate<LivingEntityPatch<?>>, T> parseCaseEntryInternal(
    Function<CompoundTag, V> getValueObjFunc,
    Function<V, T> getValueFunc,
    BiPredicate<T, V> validator,
    CompoundTag caseCompTag
  ) {
    List<Condition.EntityPatchCondition> conditionList = Lists.newArrayList();

    // 解析值 - 通过传入的 Function 获取 valueObj
    V valueObj = getValueObjFunc.apply(caseCompTag);
    if (valueObj == null) {
      LOGGER.warn("Missing '{}' field in conditional case entry", VALUE_TAG);
      return Pair.of(entitypatch -> false, null);
    }

    T value = getValueFunc.apply(valueObj);

    if (!validator.test(value, valueObj)) {
      LOGGER.warn("Conditional case entry validation failed: value={}, originalData={}", value, valueObj);
      return Pair.of(entitypatch -> false, value);
    }

    // 解析条件列表
    ListTag conditionsList = caseCompTag.getList(CONDITIONS_TAG, Tag.TAG_COMPOUND);
    for (int i = 0; i < conditionsList.size(); i++) {
      CompoundTag conditionCompTag = (CompoundTag) conditionsList.get(i);
      String predicateId = conditionCompTag.getString(PREDICATE_TAG);

      try {
        Supplier<Condition.EntityPatchCondition> conditionProvider =
          EpicFightConditions.getConditionOrThrow(ResourceLocation.parse(predicateId));
        Condition.EntityPatchCondition entityPatchCondition = conditionProvider.get();
        entityPatchCondition.read(conditionCompTag);
        conditionList.add(entityPatchCondition);
      } catch (Exception e) {
        LOGGER.error("Failed to parse condition predicate [index={}, predicate={}, error={}]: {}",
          i, predicateId, e.getClass().getSimpleName(), e.getMessage());
      }
    }

    // 创建复合谓词
    Predicate<LivingEntityPatch<?>> composedPredicate = composePredicate(conditionList);
    return Pair.of(composedPredicate, value);
  }
  //endregion

  /**
   * 从配置标签获取默认值
   *
   * @param parentTag 父配置标签
   * @param tagName   标签名称
   * @return 默认值字符串，解析失败时返回 null
   */
  @Nullable
  public static String getConfigDefaultValue(CompoundTag parentTag, String tagName) {
    Tag configTag = parentTag.get(tagName);
    if (configTag instanceof CompoundTag configCompTag) {
      return getConfigDefaultValue(configCompTag);
    }
    LOGGER.warn("Invalid config tag [{}]: expected CompoundTag, but got {}", tagName,
      configTag != null ? configTag.getClass().getSimpleName() : "null");
    return null;
  }

  /**
   * 从配置复合标签获取默认值
   *
   * @param configCompTag 配置复合标签
   * @return 默认值字符串，不存在时返回 null
   */
  @Nullable
  public static String getConfigDefaultValue(CompoundTag configCompTag) {
    return configCompTag.contains(DEFAULT_TAG) ? configCompTag.getString(DEFAULT_TAG) : null;
  }

  /**
   * 获取条件化提供者函数
   * <p>根据实体条件动态返回值，若无匹配条件则返回默认值</p>
   *
   * @param defaultValue 默认值
   * @param conditions   条件列表（谓词 + 值对）
   * @param <T>          返回值类型
   * @return 条件化提供者函数
   */
  public static <T> Function<LivingEntityPatch<?>, T> getProvider(
    T defaultValue,
    List<Pair<Predicate<LivingEntityPatch<?>>, @Nullable T>> conditions
  ) {
    return entitypatch -> {
      if (conditions == null || conditions.isEmpty()) {
        return defaultValue;
      }

      return conditions.stream()
        .filter(entry -> {
          Predicate<LivingEntityPatch<?>> predicate = entry.getFirst();
          return predicate != null && predicate.test(entitypatch);
        })
        .findFirst()
        .map(Pair::getSecond)
        .orElse(defaultValue);
    };
  }
}
