package ctn.imaginarycraft.util;

import net.minecraft.util.RandomSource;

public final class RandomDamageUtil {
  /**
   * 获取指定范围随机波动伤害值，可分别指定向上和向下的波动范围
   *
   * @param randomSource              随机数源
   * @param positiveFluctuationDamage 正向波动伤害值（向上波动的最大值）
   * @param negativeFluctuationDamage 负向波动伤害值（向下波动的绝对值）
   * @return 随机波动伤害值，范围在 [-negativeFluctuationDamage, positiveFluctuationDamage]
   */
  public static float countBidirectionalFluctuationDamageValue(RandomSource randomSource, float positiveFluctuationDamage, float negativeFluctuationDamage) {
    assert positiveFluctuationDamage >= negativeFluctuationDamage : "positiveFluctuationDamage must be greater than or equal to negativeFluctuationDamage";
    if (negativeFluctuationDamage == positiveFluctuationDamage) {
      return 0;
    }
    return randomSource.nextFloat() * (positiveFluctuationDamage + negativeFluctuationDamage) - negativeFluctuationDamage;
  }
}
