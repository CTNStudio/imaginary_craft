package ctn.singularity.lib.capability;

import net.minecraft.util.RandomSource;

/**
 * 提供给PM伤害系统的随机伤害能力接口
 */
public interface IRandomDamage {
  /**
   * 获取指定范围随机伤害值
   *
   * @param randomSource 随机数源
   * @param maxDamage 最大伤害值
   * @param minDamage 最小伤害值
   * @return 随机伤害值
   */
  static int countDamageValue(RandomSource randomSource, int maxDamage, int minDamage) {
    if (maxDamage < minDamage) {
      int i = maxDamage;
      maxDamage = minDamage;
      minDamage = i;
    }
    if (maxDamage == minDamage) {
      return maxDamage;
    }
    return randomSource.nextInt(minDamage, maxDamage + 1);
  }

  /**
   * 获取最大伤害值
   *
   * @return 最大伤害值
   */
  int getMaxDamage();

  /**
   * 获取最小伤害值
   *
   * @return 最小伤害值
   */
  int getMinDamage();

  /**
   * 获取指定范围随机伤害值
   *
   * @param randomSource 随机数源
   * @return 随机伤害值
   */
  default int getDamageValue(RandomSource randomSource) {
    return countDamageValue(randomSource, getMaxDamage(), getMinDamage());
  }
}
