package ctn.singularity.lib.init;

import ctn.singularity.lib.common.attribute.BasicAttribute;
import ctn.singularity.lib.common.attribute.MinAttribute;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.util.AttributeRegisterUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.BooleanAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public final class LibAttributes extends AttributeRegisterUtil {
  public static final DeferredRegister<Attribute> REGISTRY = LibMain.modRegister(BuiltInRegistries.ATTRIBUTE);

  /// 易伤 值越大越受到的伤害倍数越高
  /**
   * 物理易伤
   */
  public static final DeferredHolder<Attribute, RangedAttribute> PHYSICS_VULNERABLE = register("physics_vulnerable", function ->
      function.setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE), 1.0, -10, 10);
  /**
   * 精神易伤
   */
  public static final DeferredHolder<Attribute, RangedAttribute> SPIRIT_VULNERABLE = register("rationality_vulnerable", function ->
      function.setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE), 1.0, -10, 10);
  /**
   * 侵蚀易伤
   */
  public static final DeferredHolder<Attribute, RangedAttribute> EROSION_VULNERABLE = register("erosion_vulnerable", function ->
      function.setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE), 1.5, -10, 10);
  /**
   * 灵魂易伤
   */
  public static final DeferredHolder<Attribute, RangedAttribute> THE_SOUL_VULNERABLE = register("the_soul_vulnerable", function ->
      function.setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE), 2.0, -10, 10);

  /// 理智
  /**
   * 最大理智值
   */
  public static final DeferredHolder<Attribute, MinAttribute> MAX_RATIONALITY = registerMin("max_rationality", function ->
      function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 20, 0);
  /**
   * 理智值自然恢复效率
   */
  public static final DeferredHolder<Attribute, MinAttribute> RATIONALITY_NATURAL_RECOVERY_RATE = registerMin("rationality_natural_recovery_rate",function ->
      function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 0.5, 0);
  /**
   * 理智值自然恢复量
   */
  public static final DeferredHolder<Attribute, MinAttribute> RATIONALITY_RECOVERY_AMOUNT = registerMin("rationality_recovery_amount",function ->
      function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 1, 0);

  /// 四徳
  /**
   * 勇气
   */
  public static final DeferredHolder<Attribute, BasicAttribute> FORTITUDE_POINTS = register("fortitude_points",function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 0);
  /**
   * 谨慎
   */
  public static final DeferredHolder<Attribute, BasicAttribute> PRUDENCE_POINTS = register("prudence_points",function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 0);
  /**
   * 自律
   */
  public static final DeferredHolder<Attribute, BasicAttribute> TEMPERANCE_POINTS = register("temperance_points",function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 0);
  /**
   * 正义
   */
  public static final DeferredHolder<Attribute, BasicAttribute> JUSTICE_POINTS = register("justice_points",function ->
    function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), 0);

  // 部门属性
  /**
   * 情报部门激活
   * <p>
   * TODO 未完成
   */
  public static final DeferredHolder<Attribute, BooleanAttribute> INFORMATION = register("player.intelligence_department_activation",function ->
      function.setSyncable(true).setSentiment(Attribute.Sentiment.POSITIVE), false);
}
