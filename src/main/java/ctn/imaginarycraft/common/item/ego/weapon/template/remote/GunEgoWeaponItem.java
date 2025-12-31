package ctn.imaginarycraft.common.item.ego.weapon.template.remote;

import com.mojang.datafixers.util.Function3;
import ctn.imaginarycraft.api.IGunWeapon;
import ctn.imaginarycraft.api.PlayerTimingRun;
import ctn.imaginarycraft.util.GunWeaponUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;

import java.util.function.Function;

/**
 * 枪械EGO武器物品抽象类
 * 继承自GeoRemoteEgoWeaponItem并实现IGunWeapon接口，提供枪械的基本功能实现
 * 包括普通射击、瞄准射击、充能机制等
 */
public abstract class GunEgoWeaponItem extends GeoRemoteEgoWeaponItem implements IGunWeapon {
  public GunEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoRemoteEgoWeaponItem> geoModel, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public GunEgoWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  //region Using

  /**
   * 处理物品使用操作
   * 当玩家使用物品时触发，根据是否可以瞄准来决定执行瞄准操作还是其他操作
   *
   * @param world        使用物品的游戏世界
   * @param playerEntity 使用物品的玩家实体
   * @param handUsed     使用的手（主手或副手）
   * @return 返回物品交互结果
   */
  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player playerEntity, @NotNull InteractionHand handUsed) {
    ItemStack itemStackInHand = playerEntity.getItemInHand(handUsed);
    if (isGunAim(playerEntity, itemStackInHand)) {
      gunAim(playerEntity, itemStackInHand);
      playerEntity.startUsingItem(handUsed);
      PlayerTimingRun.getInstance(playerEntity).removeTimingRun(handUsed);
    }
    return InteractionResultHolder.pass(itemStackInHand);
  }

  /**
   * 在使用物品期间每tick调用一次
   * 用于处理持续性的使用操作，如充能等
   *
   * @param world                物品使用的世界
   * @param usingEntity          使用物品的实体
   * @param itemStack            使用的物品栈
   * @param remainingUseDuration 剩余使用持续时间
   */
  @Override
  public void onUseTick(@NotNull Level world, @NotNull LivingEntity usingEntity, @NotNull ItemStack itemStack, int remainingUseDuration) {
    if (usingEntity instanceof ServerPlayer player) {
      GunWeaponUtil.modifyChargeUpValue(player, 1);
    }
  }

  /**
   * 当停止使用物品时调用
   * 重载方法，将参数转换为内部方法处理
   *
   * @param stack  停止使用的物品栈
   * @param entity 使用物品的实体
   * @param count  停止使用时的计数
   */
  @Override
  public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
    onStopUsing(stack, entity);
  }

  /**
   * 当物品使用完成时调用
   * 例如弓箭射出箭矢后调用此方法
   *
   * @param stack        使用完成的物品栈
   * @param level        物品使用的等级
   * @param livingEntity 使用物品的活体实体
   * @return 返回使用完成后的物品栈
   */
  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
    onStopUsing(stack, livingEntity);
    return super.finishUsingItem(stack, level, livingEntity);
  }

  /**
   * 当释放使用物品时调用
   * 例如松开右键停止使用物品时
   *
   * @param stack        释放使用的物品栈
   * @param level        物品使用的等级
   * @param livingEntity 使用物品的活体实体
   * @param timeCharged  充电时间
   */
  @Override
  public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
    onStopUsing(stack, livingEntity);
  }

  /**
   * 停止使用物品的统一处理方法
   * 根据当前状态执行结束瞄准或结束射击操作，并重置相关参数
   *
   * @param stack  停止使用的物品栈
   * @param entity 使用物品的实体
   */
  public void onStopUsing(ItemStack stack, LivingEntity entity) {
    if (!(entity instanceof ServerPlayer player)) {
      return;
    }

    if (isGunAim(player, stack)) {
      gunEndAim(player, stack);
    }
  }
  //endregion

  //region Aim
  @Override
  public boolean isGunAim(Player player, ItemStack itemStack) {
    return false;
  }

  @Override
  public boolean isGunAimMove(Player player, ItemStack itemStack) {
    return false;
  }

  /**
   * 开始瞄准状态
   * 初始化瞄准相关的参数，重置充能值并设置左键攻击状态
   *
   * @param playerEntity 玩家实体
   * @param itemStack    枪械物品栈
   */
  @Override
  public void gunAim(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    if (playerEntity instanceof ServerPlayer player) {
      GunWeaponUtil.resetChargeUp(player);
      GunWeaponUtil.setIsLeftKeyAttack(player, true);
    }
  }

  /**
   * 执行瞄准射击操作
   * 检查瞄准射击条件，如果满足则执行瞄准射击逻辑
   *
   * @param playerEntity 玩家实体
   * @param itemStack    枪械物品栈
   * @param handUsed     使用的手（主手或副手）
   * @return 如果成功执行瞄准射击则返回true，否则返回false
   */
  @Override
  public boolean gunAimShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    float chargeUpPercentage = GunWeaponUtil.getChargeUpPercentage(playerEntity);
    if (!gunAimShootCondition(playerEntity, itemStack, handUsed, chargeUpPercentage)) {
      return false;
    }
    return gunAimShootExecute(playerEntity, itemStack, handUsed, chargeUpPercentage);
  }

  /**
   * 检查瞄准射击的条件
   * 验证当前充能百分比是否满足瞄准射击要求
   *
   * @param playerEntity       玩家实体
   * @param itemStack          枪械物品栈
   * @param handUsed           使用的手（主手或副手）
   * @param chargeUpPercentage 当前充能百分比
   * @return 如果满足瞄准射击条件则返回true，否则返回false
   */
  @Override
  public boolean gunAimShootCondition(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    return chargeUpPercentage >= 1;
  }

  /**
   * 执行瞄准射击的具体操作
   * 在服务器端发射弹射物并重置充能状态
   *
   * @param playerEntity       玩家实体
   * @param itemStack          枪械物品栈
   * @param handUsed           使用的手（主手或副手）
   * @param chargeUpPercentage 当前充能百分比
   * @return 如果成功执行瞄准射击则返回true，否则返回false
   */
  @Override
  public boolean gunAimShootExecute(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    if (playerEntity.level() instanceof ServerLevel serverLevel) {
      this.shoot(serverLevel, playerEntity, playerEntity.getUsedItemHand(), itemStack, getProjectileVelocity(playerEntity, itemStack, handUsed), getProjectileInaccuracy(playerEntity, itemStack, handUsed), null);
      GunWeaponUtil.resetChargeUp(playerEntity);
    }
    return true;
  }

  /**
   * 结束瞄准状态
   * 清理瞄准相关状态，当前为空实现
   *
   * @param playerEntity 玩家实体
   * @param itemStack    枪械物品栈
   */
  @Override
  public void gunEndAim(@NotNull Player playerEntity, @NotNull ItemStack itemStack) {
    GunWeaponUtil.setIsLeftKeyAttack(playerEntity, true);
    GunWeaponUtil.resetChargeUp(playerEntity);
  }
  //endregion

  //region Shoot

  /**
   * 执行普通射击操作
   * 检查射击条件，如果满足则执行射击逻辑
   *
   * @param playerEntity 玩家实体
   * @param itemStack    枪械物品栈
   * @param handUsed     使用的手（主手或副手）
   * @return 如果成功执行射击则返回true，否则返回false
   */
  @Override
  public boolean gunShoot(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed) {
    float chargeUpPercentage = GunWeaponUtil.getChargeUpPercentage(playerEntity);
    if (!gunShootCondition(playerEntity, itemStack, handUsed, chargeUpPercentage)) {
      return false;
    }
    return gunShootExecute(playerEntity, itemStack, handUsed, chargeUpPercentage);
  }

  /**
   * 检查普通射击的条件
   * 验证玩家是否可以进行左键攻击
   *
   * @param playerEntity       玩家实体
   * @param itemStack          枪械物品栈
   * @param handUsed           使用的手（主手或副手）
   * @param chargeUpPercentage 当前充能百分比
   * @return 如果满足射击条件则返回true，否则返回false
   */
  @Override
  public boolean gunShootCondition(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    return GunWeaponUtil.isIsLeftKeyAttack(playerEntity);
  }

  /**
   * 执行普通射击的具体操作
   * 在服务器端添加定时运行任务以处理射击逻辑
   *
   * @param playerEntity       玩家实体
   * @param itemStack          枪械物品栈
   * @param handUsed           使用的手（主手或副手）
   * @param chargeUpPercentage 当前充能百分比
   * @return 如果成功执行射击则返回true，否则返回false
   */
  @Override
  public boolean gunShootExecute(@NotNull Player playerEntity, @NotNull ItemStack itemStack, @NotNull InteractionHand handUsed, float chargeUpPercentage) {
    if (!(playerEntity.level() instanceof ServerLevel serverLevel)) {
      return true;
    }
    gunShootExecuteFunction(playerEntity, handUsed, (tick, max, player) -> {
      GunWeaponUtil.modifyChargeUpPercentage(player, 1f / (max));
      return tick - 1;
    }, player -> {
      this.shoot(serverLevel, playerEntity, playerEntity.getUsedItemHand(), itemStack, getProjectileVelocity(playerEntity, itemStack, handUsed), getProjectileInaccuracy(playerEntity, itemStack, handUsed), null);
      GunWeaponUtil.setIsLeftKeyAttack(playerEntity, true);
      GunWeaponUtil.resetChargeUp(playerEntity);
      return 0;
    }, shootTick(playerEntity, itemStack, handUsed));
    return true;
  }

  protected void gunShootExecuteFunction(
    @NotNull Player playerEntity,
    @NotNull InteractionHand handUsed,
    Function3<Integer, Integer, Player, Integer> tickRun,
    Function<Player, Integer> resultRun, int maxTick
  ) {
    PlayerTimingRun.getInstance(playerEntity).addTimingRun(handUsed, PlayerTimingRun.createTimingRunBilder().tickRun(tickRun).build(resultRun, maxTick));
    GunWeaponUtil.setIsLeftKeyAttack(playerEntity, false);
    GunWeaponUtil.resetChargeUp(playerEntity);
  }

  /**
   * 获取射击所需的tick数
   * 根据玩家速度计算射击间隔
   *
   * @param player   玩家实体
   * @param stack    枪械物品栈
   * @param usedHand 使用的手（主手或副手）
   * @return 返回射击所需的tick数
   */
  @Override
  public int shootTick(@NotNull Player player, @NotNull ItemStack stack, @NotNull InteractionHand usedHand) {
    return (int) (GunWeaponUtil.getMaxChargeUpValue(player));// TODO 需要调整
  }

  /**
   * 射出弹射物的具体实现
   * 设置弹射物的发射角度、速度和精度
   *
   * @param shooter    发射者实体
   * @param projectile 被发射的弹射物
   * @param index      弹射物索引
   * @param velocity   发射速度
   * @param inaccuracy 发射精度（数值越大越不准确）
   * @param angle      发射角度
   * @param target     目标实体（可为空）
   */
  @Override
  protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
    projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + angle, 0.0F, velocity, inaccuracy);
  }

  /**
   * 处理左键点击实体的事件
   * 允许对实体进行攻击操作
   *
   * @param itemStack    当前物品栈
   * @param playerEntity 玩家实体
   * @param entity       被点击的实体
   * @return 返回是否允许攻击该实体
   */
  @Override
  public boolean onLeftClickEntity(ItemStack itemStack, Player playerEntity, Entity entity) {
    return true;
  }
  //endregion
}
