package ctn.imaginarycraft.api;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * 枪械武器接口
 * 定义了枪械的基本行为和功能，包括普通射击、瞄准射击等操作
 * 该接口允许实现类自定义枪械的射击逻辑、瞄准行为和相关条件判断
 */
public interface IGunWeapon {
  //region Shoot
  int shootTick(@NotNull Player player, @NotNull ItemStack stack, @NotNull InteractionHand usedHand);

  /**
   * 执行枪械射击操作
   * 在手持该物品时按攻击键触发
   *
   * @param player 玩家实体
   * @param stack 枪械物品栈
   * @param usedHand 使用的手（主手或副手）
   * @return 返回false则不在服务器执行射击逻辑
   */
  boolean gunShoot(@NotNull Player player, @NotNull ItemStack stack, @NotNull InteractionHand usedHand);

  /**
   * 检查射击条件是否满足
   * 用于验证玩家在特定条件下是否可以进行射击
   *
   * @param playerEntity 玩家实体
   * @param itemStack 枪械物品栈
   * @param handUsed 使用的手（主手或副手）
   * @param chargeUpPercentage 充能百分比（用于需要充能的武器）
   * @return 如果满足射击条件则返回true，否则返回false
   */
  boolean gunShootCondition(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage);

  /**
   * 执行射击操作的具体实现
   * 当射击条件满足时执行实际的射击逻辑
   *
   * @param playerEntity 玩家实体
   * @param itemStack 枪械物品栈
   * @param handUsed 使用的手（主手或副手）
   * @param chargeUpPercentage 充能百分比（用于需要充能的武器）
   * @return 如果射击执行成功则返回true，否则返回false
   */
  boolean gunShootExecute(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage);

  //endregion

  //region Aim
  /**
   * 判断玩家是否可以使用该枪械进行瞄准
   *
   * @param player 玩家实体
   * @param itemStack 枪械物品栈
   * @return 如果可以瞄准则返回true，否则返回false
   */
  boolean isGunAim(Player player, ItemStack itemStack);

  // TODO 待添加实际效果
  // TODO 攻击以及瞄准移动速度可配置
  /**
   * 判断玩家在瞄准状态下是否可以移动
   *
   * @param player 玩家实体
   * @param itemStack 枪械物品栈
   * @return 如果瞄准时可以移动则返回true，否则返回false
   */
  boolean isGunAimMove(Player player, ItemStack itemStack);


  /**
   * 开始瞄准状态
   * 当玩家开始瞄准时调用此方法设置相关状态
   *
   * @param player 玩家实体
   * @param stack  枪械物品栈
   */
  void gunAim(@NotNull Player player, @NotNull ItemStack stack);

  /**
   * 执行瞄准射击操作
   * 在使用该物品时同时按住使用键和攻击键触发
   *
   * @param player   玩家实体
   * @param stack    枪械物品栈
   * @param usedHand 使用的手（主手或副手）
   * @return 返回false则不在服务器执行瞄准射击逻辑
   */
  boolean gunAimShoot(@NotNull Player player, @NotNull ItemStack stack, @NotNull InteractionHand usedHand);

  /**
   * 检查瞄准射击条件是否满足
   * 用于验证玩家在瞄准状态下是否可以进行射击
   *
   * @param playerEntity       玩家实体
   * @param itemStack          枪械物品栈
   * @param handUsed           使用的手（主手或副手）
   * @param chargeUpPercentage 充能百分比（用于需要充能的武器）
   * @return 如果满足瞄准射击条件则返回true，否则返回false
   */
  boolean gunAimShootCondition(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage);

  /**
   * 执行瞄准射击操作的具体实现
   * 当瞄准射击条件满足时执行实际的瞄准射击逻辑
   *
   * @param playerEntity       玩家实体
   * @param itemStack          枪械物品栈
   * @param handUsed           使用的手（主手或副手）
   * @param chargeUpPercentage 充能百分比（用于需要充能的武器）
   * @return 如果瞄准射击执行成功则返回true，否则返回false
   */
  boolean gunAimShootExecute(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage);

  /**
   * 结束瞄准状态
   * 当玩家停止瞄准时调用此方法清理相关状态
   *
   * @param player 玩家实体
   * @param stack  枪械物品栈
   */
  void gunEndAim(@NotNull Player player, @NotNull ItemStack stack);
  //endregion
}
