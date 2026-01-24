package ctn.imaginarycraft.api;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.util.StringMap;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 左键->攻击键
 * <p>
 * 右键->使用键
 */
public interface IPlayerItemLeftClick {

  /**
   * 空点击
   */
  default void leftClickEmpty(Player player, ItemStack stack) {

  }

  /**
   * 点击
   */
  default void onClick(Player player, InteractionHand hand) {

  }

  /**
   * 持续点击
   */
  default void onClickSustain(Player player, InteractionHand hand) {

  }

  /**
   * 松开点击
   */
  default void onRelease(Player player, InteractionHand hand) {

  }

  /**
   * 滚轮
   *
   * @return 返回false表示通过滚轮切换物品
   */
  default boolean onWhellScroll(Player player, InteractionHand hand, int scrollAmount) {
    return false;
  }
}
