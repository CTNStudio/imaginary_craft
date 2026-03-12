package ctn.imaginarycraft.api.data;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.registry.entries.EpicFightConditions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 条件配置条目解析器
 * <p>负责从 NBT 标签中解析条件化的配置条目</p>
 */
public final class ConditionalEntryParser {

  private static final Logger LOGGER = ImaginaryCraft.LOGGER;

  // ==================== 公共解析方法（从父标签获取）====================

  /**
   * 从父标签解析条件配置列表（直接获取 Tag 值）
   *
   * @param parentTag    父级 NBT 标签
   * @param key          配置键名
   * @param getValueFunc 值获取函数
   * @param validator    验证器
   * @param <T>          返回值类型
   * @return 条件化配置列表，解析失败时返回 null
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromTag(
    CompoundTag parentTag,
    String key,
    Function<Tag, T> getValueFunc,
    BiPredicate<T, Tag> validator
  ) {
    return parseConditionalEntriesFromParentTag(parentTag, key, tag -> tag.get(ModWeaponTypeReloadListener.VALUE_TAG), getValueFunc, validator);
  }

  /**
   * 从配置复合标签解析条件配置列表（直接获取 Tag 值）
   *
   * @param configCompTag 配置复合标签
   * @param getValueFunc  值获取函数
   * @param validator     验证器
   * @param <T>           返回值类型
   * @return 条件化配置列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromTag(
    CompoundTag configCompTag,
    Function<Tag, T> getValueFunc,
    BiPredicate<T, Tag> validator
  ) {
    return parseConditionalEntriesValue(configCompTag, tag -> tag.get(ModWeaponTypeReloadListener.VALUE_TAG), getValueFunc, validator);
  }

  /**
   * 解析单个条件配置项（直接获取 Tag 值）
   *
   * @param getValueFunc 值获取函数
   * @param validator    验证器
   * @param caseCompTag  条件复合标签
   * @param <T>          返回值类型
   * @return 条件化配置项
   */
  public static <T> Pair<Predicate<LivingEntityPatch<?>>, T> parseCaseEntryFromTag(
    Function<Tag, T> getValueFunc,
    BiPredicate<T, Tag> validator,
    CompoundTag caseCompTag
  ) {
    return parseCaseEntryInternal(caseCompTag, tag -> caseCompTag.get(ModWeaponTypeReloadListener.VALUE_TAG), getValueFunc, validator);
  }

  // ==================== 公共解析方法（基于 Registry）====================

  /**
   * 从父标签解析条件配置列表（基于 Registry）
   *
   * @param parentTag 父级 NBT 标签
   * @param key       配置键名
   * @param registry  注册表
   * @param validator 验证器
   * @param <T>       返回值类型
   * @return 条件化配置列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromString(
    CompoundTag parentTag,
    String key,
    net.minecraft.core.Registry<T> registry,
    BiPredicate<T, String> validator
  ) {
    return parseConditionalEntriesFromParentTag(parentTag, key,
      tag -> tag.getString(ModWeaponTypeReloadListener.VALUE_TAG),
      valueString -> registry.get(ResourceLocation.parse(valueString)),
      validator);
  }

  /**
   * 从配置复合标签解析条件配置列表（基于 Registry）
   *
   * @param configCompTag 配置复合标签
   * @param registry      注册表
   * @param validator     验证器
   * @param <T>           返回值类型
   * @return 条件化配置列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromString(
    CompoundTag configCompTag,
    net.minecraft.core.Registry<T> registry,
    BiPredicate<T, String> validator
  ) {
    return parseConditionalEntriesValue(configCompTag,
      tag -> tag.getString(ModWeaponTypeReloadListener.VALUE_TAG),
      valueString -> registry.get(ResourceLocation.parse(valueString)),
      validator);
  }

  // ==================== 公共解析方法（基于 Function）====================

  /**
   * 从父标签解析条件配置列表（自定义值获取函数）
   *
   * @param parentTag    父级 NBT 标签
   * @param key          配置键名
   * @param getValueFunc 值获取函数
   * @param validator    验证器
   * @param <T>          返回值类型
   * @return 条件化配置列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntries(
    CompoundTag parentTag,
    String key,
    Function<String, T> getValueFunc,
    BiPredicate<T, String> validator
  ) {
    return parseConditionalEntriesFromParentTag(parentTag, key,
      tag -> tag.getString(ModWeaponTypeReloadListener.VALUE_TAG),
      getValueFunc,
      validator);
  }

  /**
   * 从配置复合标签解析条件配置列表（自定义值获取函数）
   *
   * @param configCompTag 配置复合标签
   * @param getValueFunc  值获取函数
   * @param validator     验证器
   * @param <T>           返回值类型
   * @return 条件化配置列表
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntries(
    CompoundTag configCompTag,
    Function<String, T> getValueFunc,
    BiPredicate<T, String> validator
  ) {
    return parseConditionalEntriesValue(configCompTag,
      tag -> tag.getString(ModWeaponTypeReloadListener.VALUE_TAG),
      getValueFunc,
      validator);
  }

  // ==================== 单个条件项解析方法 ====================

  /**
   * 解析单个条件配置项（自定义值获取函数）
   *
   * @param getValueFunc 值获取函数
   * @param validator    验证器
   * @param caseCompTag  条件复合标签
   * @param <T>          返回值类型
   * @return 条件化配置项
   */
  public static <T> Pair<Predicate<LivingEntityPatch<?>>, T> parseCaseEntry(
    Function<String, T> getValueFunc,
    BiPredicate<T, String> validator,
    CompoundTag caseCompTag
  ) {
    return parseCaseEntryInternal(caseCompTag,
      tag -> tag.getString(ModWeaponTypeReloadListener.VALUE_TAG),
      getValueFunc,
      validator);
  }

  // ==================== 谓词组合方法 ====================

  /**
   * 组合多个条件为复合谓词（所有条件必须满足）
   *
   * @param conditionList 条件列表
   * @return 复合谓词
   */
  public static java.util.function.Predicate<LivingEntityPatch<?>> composePredicate(List<Condition.EntityPatchCondition> conditionList) {
    return entitypatch -> conditionList.stream()
      .allMatch(condition -> condition.predicate(entitypatch));
  }

  // ==================== 内部实现方法 ====================

  /**
   * 从父标签提取配置并委托解析
   */
  private static <T, V> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesFromParentTag(
    CompoundTag parentTag,
    String key,
    Function<CompoundTag, V> getValueObjFunc,
    Function<V, T> getValueFunc,
    BiPredicate<T, V> validator
  ) {
    Tag configTag = parentTag.get(key);
    if (configTag instanceof CompoundTag configCompTag) {
      return parseConditionalEntriesValue(configCompTag, getValueObjFunc, getValueFunc, validator);
    }

    LOGGER.warn("Invalid config tag [{}]: expected CompoundTag, but got {}",
      key, configTag != null ? configTag.getClass().getSimpleName() : "null");
    return null;
  }

  /**
   * 解析条件配置列表的核心实现
   */
  public static <T, V> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseConditionalEntriesValue(
    CompoundTag configCompTag,
    Function<CompoundTag, V> getValueObjFunc,
    Function<V, T> getValueFunc,
    BiPredicate<T, V> validator
  ) {
    ListTag casesList = configCompTag.getList(ModWeaponTypeReloadListener.CASES_TAG, Tag.TAG_COMPOUND);

    return casesList.stream()
      .map(CompoundTag.class::cast)
      .map(caseTag -> parseCaseEntryInternal(caseTag, getValueObjFunc, getValueFunc, validator))
      .collect(Collectors.toList());
  }

  /**
   * 解析单个条件配置项的内部实现
   */
  public static <T, V> Pair<Predicate<LivingEntityPatch<?>>, T> parseCaseEntryInternal(
    CompoundTag caseCompTag,
    Function<CompoundTag, V> getValueObjFunc,
    Function<V, T> getValueFunc,
    BiPredicate<T, V> validator
  ) {
    List<Condition.EntityPatchCondition> conditionList = Lists.newArrayList();

    // 解析值对象
    V valueObj = getValueObjFunc.apply(caseCompTag);
    if (valueObj == null) {
      LOGGER.warn("Missing '{}' field in conditional case entry: {}", ModWeaponTypeReloadListener.VALUE_TAG, caseCompTag.getAsString());
      return Pair.of(entitypatch -> false, null);
    }

    // 获取并验证最终值
    T value = getValueFunc.apply(valueObj);

    if (!validator.test(value, valueObj)) {
      LOGGER.warn("Conditional case entry validation failed: value={}, originalData={}", value, valueObj);
      return Pair.of(entitypatch -> false, value);
    }

    // 解析条件列表
    ListTag conditionsList = caseCompTag.getList(ModWeaponTypeReloadListener.CONDITIONS_TAG, Tag.TAG_COMPOUND);

    for (int i = 0; i < conditionsList.size(); i++) {
      CompoundTag conditionCompTag = (CompoundTag) conditionsList.get(i);
      String predicateId = conditionCompTag.getString(ModWeaponTypeReloadListener.PREDICATE_TAG);

      try {
        Supplier<Condition.EntityPatchCondition> conditionProvider = EpicFightConditions.getConditionOrThrow(ResourceLocation.parse(predicateId));
        Condition.EntityPatchCondition entityPatchCondition = conditionProvider.get();
        entityPatchCondition.read(conditionCompTag);
        conditionList.add(entityPatchCondition);
      } catch (Exception e) {
        LOGGER.error("Failed to parse condition [index={}, predicate={}, error={}]: {}",
          i, predicateId, e.getClass().getSimpleName(), e.getMessage(), e);
      }
    }

    return Pair.of(composePredicate(conditionList), value);
  }
}
