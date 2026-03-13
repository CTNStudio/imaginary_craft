package ctn.imaginarycraft.api.data;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.core.Registry;
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
 */
public final class ConditionalEntryParser {
  private static final Logger LOGGER = ImaginaryCraft.LOGGER;

  /**
   * 从配置标签解析（Tag 值）
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseCasesFromTag(
    CompoundTag configCompTag, Function<Tag, T> getValueFunc, BiPredicate<T, Tag> validator
  ) {
    return parseCasesValue(configCompTag, tag -> tag.get(ModWeaponTypeReloadListener.VALUE), getValueFunc, validator);
  }

  /**
   * 从配置标签解析（Registry）
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseCasesFromRegistryString(
    CompoundTag configCompTag, Registry<T> registry, BiPredicate<T, String> validator
  ) {
    return parseCasesValue(configCompTag, tag -> tag.getString(ModWeaponTypeReloadListener.VALUE), valueString -> registry.get(ResourceLocation.parse(valueString)), validator);
  }

  /**
   * 从配置标签解析（Function）
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseCases(
    CompoundTag configCompTag, Function<String, T> getValueFunc, BiPredicate<T, String> validator
  ) {
    return parseCasesValue(configCompTag, tag -> tag.getString(ModWeaponTypeReloadListener.VALUE), getValueFunc, validator);
  }

  /**
   * 从配置标签解析（Tag 值）
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseFromTag(
    ListTag list, Function<Tag, T> getValueFunc, BiPredicate<T, Tag> validator
  ) {
    return parseValue(list, tag -> tag.get(ModWeaponTypeReloadListener.VALUE), getValueFunc, validator);
  }

  /**
   * 从配置标签解析（Registry）
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseFromRegistryString(
    ListTag list, Registry<T> registry, BiPredicate<T, String> validator
  ) {
    return parseValue(list, tag -> tag.getString(ModWeaponTypeReloadListener.VALUE), valueString -> registry.get(ResourceLocation.parse(valueString)), validator);
  }

  /**
   * 从配置标签解析（Function）
   */
  public static <T> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parse(
    ListTag list, Function<String, T> getValueFunc, BiPredicate<T, String> validator
  ) {
    return parseValue(list, tag -> tag.getString(ModWeaponTypeReloadListener.VALUE), getValueFunc, validator);
  }

  /**
   * 解析单个配置项（Tag 值）
   */
  public static <T> Pair<Predicate<LivingEntityPatch<?>>, T> parseCase(
    CompoundTag caseCompTag, Function<Tag, T> getValueFunc, BiPredicate<T, Tag> validator
  ) {
    return parse(caseCompTag, tag -> tag.get(ModWeaponTypeReloadListener.VALUE), getValueFunc, validator);
  }

  /**
   * 解析单个配置项（Function）
   */
  public static <T> Pair<Predicate<LivingEntityPatch<?>>, T> parseCaseEntry(
    CompoundTag caseCompTag, Function<String, T> getValueFunc, BiPredicate<T, String> validator
  ) {
    return parse(caseCompTag, tag -> tag.getString(ModWeaponTypeReloadListener.VALUE), getValueFunc, validator);
  }

  /**
   * 组合多个条件为复合谓词
   */
  public static java.util.function.Predicate<LivingEntityPatch<?>> compose(
    List<Condition.EntityPatchCondition> conditionList
  ) {
    return entitypatch -> conditionList.stream().allMatch(condition -> condition.predicate(entitypatch));
  }

  /**
   * 解析配置列表核心实现
   */
  public static <T, V> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseCasesValue(
    CompoundTag configCompTag, Function<CompoundTag, V> getValueObjFunc, Function<V, T> getValueFunc, BiPredicate<T, V> validator
  ) {
    return parseValue(configCompTag.getList(ModWeaponTypeReloadListener.CASES, Tag.TAG_COMPOUND), getValueObjFunc, getValueFunc, validator);
  }

  public static <T, V> List<Pair<Predicate<LivingEntityPatch<?>>, T>> parseValue(
    ListTag list, Function<CompoundTag, V> getValueObjFunc, Function<V, T> getValueFunc, BiPredicate<T, V> validator
  ) {
    return list.stream()
      .map(CompoundTag.class::cast)
      .map(caseTag -> parse(caseTag, getValueObjFunc, getValueFunc, validator))
      .collect(Collectors.toList());
  }

  /**
   * 解析单个配置项核心实现
   */
  private static <T, V> Pair<Predicate<LivingEntityPatch<?>>, T> parse(
    CompoundTag caseCompTag, Function<CompoundTag, V> getValueObjFunc, Function<V, T> getValueFunc, BiPredicate<T, V> validator
  ) {
    List<Condition.EntityPatchCondition> conditionList = Lists.newArrayList();

    // 解析值对象
    V valueObj = getValueObjFunc.apply(caseCompTag);
    if (valueObj == null) {
      LOGGER.warn("Missing '{}' field in conditional case entry: {}", ModWeaponTypeReloadListener.VALUE, caseCompTag.getAsString());
      return Pair.of(entitypatch -> false, null);
    }

    // 验证值
    T value = getValueFunc.apply(valueObj);
    if (!validator.test(value, valueObj)) {
      LOGGER.warn("Conditional case entry validation failed: value={}, originalData={}", value, valueObj);
      return Pair.of(entitypatch -> false, value);
    }

    // 解析条件列表
    ListTag conditionsList = caseCompTag.getList(ModWeaponTypeReloadListener.CONDITIONS, Tag.TAG_COMPOUND);
    for (int i = 0; i < conditionsList.size(); i++) {
      CompoundTag conditionCompTag = (CompoundTag) conditionsList.get(i);
      String predicateId = conditionCompTag.getString(ModWeaponTypeReloadListener.PREDICATE);

      try {
        Supplier<Condition.EntityPatchCondition> conditionProvider = EpicFightConditions.getConditionOrThrow(ResourceLocation.parse(predicateId));
        Condition.EntityPatchCondition entityPatchCondition = conditionProvider.get();
        entityPatchCondition.read(conditionCompTag);
        conditionList.add(entityPatchCondition);
      } catch (Exception e) {
        LOGGER.error("Failed to parse condition [index={}, predicate={}, error={}]: {}", i, predicateId, e.getClass().getSimpleName(), e.getMessage(), e);
      }
    }

    return Pair.of(compose(conditionList), value);
  }
}
