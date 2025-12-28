package ctn.imaginarycraft.api;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IGunWeapon {

  /**
   * 是否可以瞄准
   */
  default boolean isGunAim(Player player, ItemStack itemStack) {
    return false;
  }

  /**
   * 瞄准的时候是否可以移动
   */
  default boolean isGunAimMove(Player player, ItemStack itemStack) {
    return false;
  }

  /**
   * 射击
   * <p>
   * 在手持该物品时按攻击键触发
   *
   * @return 返回false则不在服务器执行
   */
  default boolean gunShoot(@NotNull Player player, @NotNull ItemStack stack, @NotNull InteractionHand usedHand) {
    return false;
  }

  /**
   * 瞄准
   */
  default void gunAim(@NotNull Player player, @NotNull ItemStack stack) {
  }

  /**
   * 瞄准射击
   * <p>
   * 在使用该物品时同时按住使用键和攻击键
   *
   * @return 返回false则不在服务器执行
   */
  default boolean gunAimShoot(@NotNull Player player, @NotNull ItemStack stack, @NotNull InteractionHand usedHand) {
    return false;
  }

  /**
   * 结束射击
   */
  default void gunEnd(@NotNull Player player, @NotNull ItemStack stack) {
  }

  /**
   * 瞄准结束
   */
  default void gunEndAim(@NotNull Player player, @NotNull ItemStack stack) {
  }

  static boolean isHoldGunWeapon(LivingEntity livingEntity) {
    return livingEntity.getMainHandItem().getItem() instanceof IGunWeapon || livingEntity.getOffhandItem().getItem() instanceof IGunWeapon;
  }
}
