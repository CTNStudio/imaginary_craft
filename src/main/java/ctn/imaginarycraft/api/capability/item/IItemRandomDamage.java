package ctn.imaginarycraft.api.capability.item;

import ctn.imaginarycraft.util.RandomDamageUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * 随机伤害能力接口
 */
public interface IItemRandomDamage {
  /**
   * 获取正向波动伤害值（向上波动的最大值）
   */
  float getPositiveFluctuationDamage(ItemStack stack);

  /**
   * 获取负向波动伤害值（向下波动的绝对值）
   */
  float getNegativeFluctuationDamage(ItemStack stack);

  /**
   * 获取指定范围随机波动伤害值
   *
   * @param randomSource 随机数源
   * @return 随机波动伤害值
   */
  default float countBidirectionalFluctuationDamageValue(float originalDamage, ItemStack stack, RandomSource randomSource) {
    return originalDamage + RandomDamageUtil.countBidirectionalFluctuationDamageValue(randomSource, getPositiveFluctuationDamage(stack), getNegativeFluctuationDamage(stack));
  }

  default float countBidirectionalFluctuationDamageValue(ItemStack stack, RandomSource randomSource) {
    return countBidirectionalFluctuationDamageValue(stack.getDamageValue(), stack, randomSource);
  }
}
