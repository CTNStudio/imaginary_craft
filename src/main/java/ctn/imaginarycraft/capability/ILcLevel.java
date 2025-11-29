package ctn.imaginarycraft.capability;

import ctn.imaginarycraft.api.lobotomycorporation.level.LcLevel;
import ctn.imaginarycraft.init.ModCapabilitys;
import net.minecraft.world.item.ItemStack;

import javax.annotation.CheckForNull;

@FunctionalInterface
public interface ILcLevel {
  /**
   * 返回之间的等级差值
   */
  static int leveDifferenceValue(ItemStack item, ItemStack item2) {
    return getItemLevelValue(item) - getItemLevelValue(item2);
  }

  /**
   * 返回物品等级
   */
  static int getItemLevelValue(ItemStack item) {
    var level = getItemLevel(item);
    if (level == null) {
      return 0;
    }
    return level.getLevel();

  }

  /**
   * @return {@link LcLevel}
   */
  static LcLevel getItemLevel(ItemStack item) {
    var capability = item.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ITEM);
    if (capability == null) {
      return null;
    }
    return capability.getItemLevel();
  }

  @CheckForNull
  LcLevel getItemLevel();
}
