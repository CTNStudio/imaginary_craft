package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.common.world.attribute.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.function.*;


public final class ModAttributes {
  public static final DeferredRegister<Attribute> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.ATTRIBUTE);

  public static final double PHYSICS_VULNERABLE_DEFAULT_VALUE = 1.0;
  public static final double SPIRIT_VULNERABLE_DEFAULT_VALUE = 1.0;
  public static final double EROSION_VULNERABLE_DEFAULT_VALUE = 1.5;
  public static final double THE_SOUL_VULNERABLE_DEFAULT_VALUE = 2.0;

  /**
   * 攻击速度（主手）
   * <p>
   * 用物品攻击时处理冷却速度。该数值代表每秒可施展的满强度攻击次数
   */
  public static final DeferredHolder<Attribute, RangedAttribute> ATTACK_SPEED_MAIN_HAND = register("attack_speed_main_hand", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), PHYSICS_VULNERABLE_DEFAULT_VALUE, -1024, 1024);
  /**
   * 攻击速度（副手）
   * <p>
   * 用物品攻击时处理冷却速度。该数值代表每秒可施展的满强度攻击次数
   */
  public static final DeferredHolder<Attribute, RangedAttribute> ATTACK_SPEED_OFF_HAND = register("attack_speed_off_hand", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), PHYSICS_VULNERABLE_DEFAULT_VALUE, -1024, 1024);

  // TODO 添加防御类型的值的抗性属性
  /**
   * 物理易伤
   */
  public static final DeferredHolder<Attribute, RangedAttribute> PHYSICS_VULNERABLE = register("physics_vulnerable", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE), PHYSICS_VULNERABLE_DEFAULT_VALUE, -1024, 1024);
  // TODO 添加防御类型的值的抗性属性
  /**
   * 精神易伤
   */
  public static final DeferredHolder<Attribute, RangedAttribute> SPIRIT_VULNERABLE = register("spirit_vulnerable", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE), SPIRIT_VULNERABLE_DEFAULT_VALUE, -1024, 1024);
  // TODO 添加防御类型的值的抗性属性
  /**
   * 侵蚀易伤
   */
  public static final DeferredHolder<Attribute, RangedAttribute> EROSION_VULNERABLE = register("erosion_vulnerable", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE), EROSION_VULNERABLE_DEFAULT_VALUE, -1024, 1024);
  // TODO 添加防御类型的值的抗性属性
  /**
   * 灵魂易伤
   */
  public static final DeferredHolder<Attribute, RangedAttribute> THE_SOUL_VULNERABLE = register("the_soul_vulnerable", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE), THE_SOUL_VULNERABLE_DEFAULT_VALUE, -1024, 1024);

  /// 理智
  /**
   * 最大理智值
   */
  public static final DeferredHolder<Attribute, MinAttribute> MAX_RATIONALITY = registerMin("max_rationality", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 20, 0);
  /**
   * 理智值自然恢复等待时间
   */
  public static final DeferredHolder<Attribute, MinAttribute> RATIONALITY_NATURAL_RECOVERY_WAIT_TIME = registerMin("rationality_natural_recovery_wait_time", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE), 0.5, 0);
  /**
   * 理智值自然恢复量
   */
  public static final DeferredHolder<Attribute, MinAttribute> RATIONALITY_RECOVERY_AMOUNT = registerMin("rationality_recovery_amount", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 1, 0);

  /// 四徳
  /**
   * 勇气
   */
  public static final DeferredHolder<Attribute, BasicAttribute> FORTITUDE_POINTS = register("fortitude_points", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 0);
  /**
   * 谨慎
   */
  public static final DeferredHolder<Attribute, BasicAttribute> PRUDENCE_POINTS = register("prudence_points", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 0);
  /**
   * 自律
   */
  public static final DeferredHolder<Attribute, BasicAttribute> TEMPERANCE_POINTS = register("temperance_points", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 0);
  /**
   * 正义
   */
  public static final DeferredHolder<Attribute, BasicAttribute> JUSTICE_POINTS = register("justice_points", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 0);

  // 部门属性
  // TODO 完善情报部的功能
  /**
   * 情报部门激活
   */
  public static final DeferredHolder<Attribute, BooleanAttribute> INTELLIGENCE_DEPARTMENT_ACTIVATION = register("player.intelligence_department_activation", function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), false);

  private static @NotNull <T extends Attribute> DeferredHolder<Attribute, T> register(
    final String name,
    final Function<T, Attribute> function,
    final T attribute
  ) {
    return ModAttributes.REGISTRY.register(name, () -> (T) function.apply(attribute));
  }

  private static @NotNull DeferredHolder<Attribute, RangedAttribute> register(
    final String name,
    final Function<RangedAttribute, Attribute> function,
    final double defaultValue,
    final double minValue,
    final double maxValue
  ) {
    return register(name, function, new RangedAttribute(descriptionId(name), defaultValue, minValue, maxValue));
  }

  private static @NotNull DeferredHolder<Attribute, RangedAttribute> register(
    final String name,
    final Function<RangedAttribute, Attribute> function,
    final double minValue,
    final double maxValue
  ) {
    return register(name, function, minValue, minValue, maxValue);
  }

  private static @NotNull DeferredHolder<Attribute, BasicAttribute> register(
    final String name,
    final Function<BasicAttribute, Attribute> function,
    final double value
  ) {
    return register(name, function, new BasicAttribute(descriptionId(name), value));
  }

  private static @NotNull DeferredHolder<Attribute, MinAttribute> registerMin(
    final String name,
    final Function<MinAttribute, Attribute> function,
    final double defaultValue,
    final double minValue
  ) {
    return register(name, function, new MinAttribute(descriptionId(name), defaultValue, minValue));
  }

  private static @NotNull DeferredHolder<Attribute, MaxAttribute> registerMax(
    final String name,
    final Function<MaxAttribute, Attribute> function,
    final double defaultValue,
    final double maxValue
  ) {
    return register(name, function, new MaxAttribute(descriptionId(name), defaultValue, maxValue));
  }

  private static @NotNull DeferredHolder<Attribute, BooleanAttribute> register(
    final String name,
    final Function<BooleanAttribute, Attribute> function,
    final boolean defaultValue
  ) {
    return register(name, function, new BooleanAttribute(descriptionId(name), defaultValue));
  }

  private static @NotNull String descriptionId(String name) {
    return ImaginaryCraft.ID + ".attribute.name." + name;
  }
}
