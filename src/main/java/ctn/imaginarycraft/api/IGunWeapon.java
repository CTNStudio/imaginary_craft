package ctn.imaginarycraft.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IGunWeapon {

  /**
   * 是否可以瞄准
   */
  default boolean isAim(Player player, ItemStack itemStack) {
    return false;
  }

  /**
   * 瞄准的时候是否可以移动
   */
  default boolean isAimMove(Player player, ItemStack itemStack) {
    return false;
  }

  /**
   * 射击
   */
  default void shoot(@NotNull Player player, @NotNull ItemStack stack) {
  }

  /**
   * 瞄准
   */
  default void aim(@NotNull Player player, @NotNull ItemStack stack) {
  }

  /**
   * 瞄准射击
   *
   * @return 返回false则不在服务器执行
   */
  default boolean aimShoot(@NotNull Player player, @NotNull ItemStack stack) {
    return false;
  }

  /**
   * 结束射击
   */
  default void end(@NotNull Player player, @NotNull ItemStack stack) {
  }

  /**
   * 瞄准结束
   */
  default void endAim(@NotNull Player player, @NotNull ItemStack stack) {
  }

}
