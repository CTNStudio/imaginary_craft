package ctn.imaginarycraft.api.lobotomycorporation.level.util;

import ctn.imaginarycraft.api.lobotomycorporation.level.LcLevel;
import net.minecraft.world.item.ItemStack;

public final class LcLevelUtil {
  /**
   * 获取等级差值
   */
  public static int leveDifferenceValue(ItemStack item, ItemStack item2) {
    return getLcLevelValue(item) - getLcLevelValue(item2);
  }

  /**
   * 返回物品等级
   */
  public static int getLcLevelValue(ItemStack item) {
    return LcLevel.getLevel(item).getLevel();
  }
}
