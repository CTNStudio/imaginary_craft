package ctn.imaginarycraft.common.item.ego.weapon.remote;

import ctn.imaginarycraft.common.entity.projectile.ParadiseLostSpikeweed;
import ctn.imaginarycraft.common.item.ego.weapon.template.remote.GeoRemoteEgoWeaponItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;

import java.util.List;

/**
 * 失乐园武器
 */
public class ParadiseLostWeaponItem extends GeoRemoteEgoWeaponItem {
  public static final String ATTACK = "player.paradise_lost.attack";
  public static final String CONTINUOUS_ATTACK = "player.paradise_lost.continuous_attack";
  public static final String END = "player.paradise_lost.end";
  private final int NORMAL_ATTACK_TICK = 8;
  private final int CHARGING_ATTACK_TICK = 10;

  public ParadiseLostWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, GeoModel<GeoRemoteEgoWeaponItem> geoModel, GeoModel<GeoRemoteEgoWeaponItem> guiModel) {
    super(itemProperties, egoWeaponBuilder, geoModel, guiModel);
  }

  public ParadiseLostWeaponItem(Properties itemProperties, Builder egoWeaponBuilder, String modPath) {
    super(itemProperties, egoWeaponBuilder, modPath);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }

  @Override
  protected void shootProjectile(LivingEntity shooterEntity, Projectile projectileEntity, int projectileIndex, float projectileVelocity, float projectileInaccuracy, float shootingAngle, @Nullable LivingEntity targetEntity) {

  }

  /**
   * 召唤一个
   */
  public static void normalAttack(Level world, LivingEntity attackingEntity) {
    if (!(world instanceof ServerLevel serverLevel)) {
      return;
    }
    final Vec3 position = attackingEntity.getEyePosition();
    double x = 0;
    double y = 0;
    double z = 0;
    int accuracy = 5;//探测精度（每1距离检测碰撞次数）
    for (int scale = 0; scale <= 30 * accuracy; scale++) {
      Vec3 vec3 = position.add(attackingEntity.getLookAngle().scale((double) scale / accuracy));
      x = vec3.x;
      y = vec3.y;
      z = vec3.z;
      double v = 2;
      AABB aabb = new AABB(x - v, y - v, z - v, x + v, y + v, z + v);
      List<LivingEntity> entityList = getAttackableTarget(attackingEntity, serverLevel, aabb);
      int i = entityList.size();
      if (i > 0) {
        LivingEntity livingEntity = entityList.get(attackingEntity.level().getRandom().nextInt(i));
        if (livingEntity != null) {
          x = livingEntity.position().x;
          y = livingEntity.blockPosition().getY();
          z = livingEntity.position().z;
          break;
        }
      } else if (!isArrivable(x, y, z, serverLevel)) {
        break;
      }
      while (isArrivable(x, y, z, serverLevel)) {
        y--;
        if (y < -64) {
          y = vec3.y;
          break;
        }
      }
    }
    serverLevel.addFreshEntityWithPassengers(ParadiseLostSpikeweed.create(serverLevel, x, y, z, 1, attackingEntity));
  }

  /**
   * 召唤多个
   */
  public static void chargingAttack(Level world, LivingEntity attackingEntity) {
    if (!(world instanceof ServerLevel serverLevel)) {
      return;
    }
    double x = attackingEntity.position().x;
    int y = attackingEntity.blockPosition().getY();
    double z = attackingEntity.position().z;
    double v = 8;
    AABB aabb = new AABB(x - v, y - 3, z - v, x + v, y + 3, z + v);
    List<LivingEntity> entityList = getAttackableTarget(attackingEntity, serverLevel, aabb);
    int i = entityList.size();
    if (i > 0) {
      for (LivingEntity livingEntity : entityList) {
        x = livingEntity.position().x;
        y = livingEntity.blockPosition().getY();
        z = livingEntity.position().z;
        while (serverLevel.getBlockState(new BlockPos((int) x, y - 1, (int) z)).isAir()) {
          y--;
          if (y < -64) {
            y = (int) livingEntity.position().y;
            break;
          }
        }
        serverLevel.addFreshEntityWithPassengers(ParadiseLostSpikeweed.create(serverLevel, x, y, z, i, attackingEntity, livingEntity));
      }
    }
  }

  /**
   * 获取可攻击目标
   */
  private static @NotNull List<LivingEntity> getAttackableTarget(LivingEntity attackingEntity, ServerLevel serverLevel, AABB aabb) {
    return serverLevel.getEntitiesOfClass(
      LivingEntity.class, aabb, (livingEntity) -> {
        boolean playerCreative = false;
        if (livingEntity instanceof Player player) {
          playerCreative = player.isCreative();
        }
        return !livingEntity.getUUID().equals(attackingEntity.getUUID()) && livingEntity.isAlive() && livingEntity.isAttackable() && !playerCreative;
      });
  }

  //延伸限制
  private static boolean isArrivable(double x, double y, double z, Level level) {
    BlockPos pos = new BlockPos(
      (int) (x >= 0 ? x : x - 1),
      (int) (y >= 0 ? y : y - 1),
      (int) (z >= 0 ? z : z - 1));
    return !isPointColliding(level, pos, x, y, z);
  }

  public static boolean isPointColliding(Level level, BlockPos pos, double worldX, double worldY, double worldZ) {
    BlockState state = level.getBlockState(pos);
    VoxelShape shape = state.getCollisionShape(level, pos);

    if (shape.isEmpty()) {
      return false;
    }

    double x = Math.abs(worldX - pos.getX());
    double y = Math.abs(worldY - pos.getY());
    double z = Math.abs(worldZ - pos.getZ());

    for (AABB aabb : shape.toAabbs()) {
      if (aabb.contains(x, y, z)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int getUseDuration(@NotNull ItemStack itemStack, @NotNull LivingEntity usingEntity) {
    return 666;
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player playerEntity, @NotNull InteractionHand handUsed) {
    ItemStack itemstack = super.use(world, playerEntity, handUsed).getObject();
    CompoundTag nbt = playerEntity.getPersistentData();
//    // 玩家移动、下方方块没有实体方块顶部、在使用中时不执行
//    if (hand == OFF_HAND ||
//      Minecraft.getInstance().player == null ||
//      !player.onGround() ||
//      nbt.getBoolean(PLAYER_USE_ITEM) ||
//      nbt.getBoolean(PLAYER_ATTACK) ||
//      isJumpCancellation(world, player)) {
//      return InteractionResultHolder.fail(itemstack);
//    }
//    enterAttackState(world, player, ATTACK);
//    nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, true);
//    nbt.putBoolean(CANNOT_PLAYER_MOVED, true);
//    player.startUsingItem(hand);
    return InteractionResultHolder.success(itemstack);
  }

  @Override
  public void onUseTick(@NotNull Level world, @NotNull LivingEntity usingEntity, @NotNull ItemStack itemStack, int remainingUseDuration) {
    if (!(usingEntity instanceof Player player) || !player.onGround() || isJumpCancellation(world, player)) {
    }
//    PmTool.incrementNbt(player, PLAYER_USE_ITEM_TICK, 1);
//    PmTool.incrementNbt(player, PLAYER_USE_TICK, 1);
//    PmTool.incrementNbt(player, ITEM_TICK, 1);
//    CompoundTag nbt = player.getPersistentData();
//    if (!nbt.getBoolean(CANNOT_PLAYER_SWITCH_ITEMS)) {
//      nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, true);
//    }
//    if (!nbt.getBoolean(CANNOT_PLAYER_MOVED)) {
//      nbt.putBoolean(CANNOT_PLAYER_MOVED, true);
//    }
//    if (nbt.getInt(PLAYER_USE_TICK) < NORMAL_ATTACK_TICK) {
//      return;
//    }
//    if (nbt.getInt(PLAYER_USE_TICK) == NORMAL_ATTACK_TICK) {
//      if (world instanceof ServerLevel serverLevel) {
//        PlayerAnimAPI.playPlayerAnim(
//          serverLevel, player, IPlayerAnim.getAnimationID(CONTINUOUS_ATTACK),
//          PlayerParts.allExceptHeadRot(), null, 2000);
//      }
//      nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, true);
//      chargingAttack(world, player);
//      nbt.putInt(ITEM_TICK, 0);
//    }
//    if (nbt.getInt(ITEM_TICK) >= CHARGING_ATTACK_TICK) {
//      chargingAttack(world, player);
//      nbt.putInt(ITEM_TICK, 0);
//    }
  }

  private boolean isJumpCancellation(Level world, Player playerEntity) {
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft.player != null) {
      if (minecraft.player.input.jumping) {
        forcedInterruption(world, playerEntity);
        return true;
      }
    }
    return false;
  }

  @Override
  public void onStopUsing(@NotNull ItemStack itemStack, @NotNull LivingEntity usingEntity, int count) {
    if (!(usingEntity instanceof Player player)) {
      return;
    }
    CompoundTag nbt = player.getPersistentData();
//    nbt.putBoolean(PLAYER_USE_ITEM, false);
//    nbt.putInt(ITEM_TICK, 0);
//    nbt.putInt(PLAYER_USE_TICK, 0);
//    if (nbt.getInt(PLAYER_USE_ITEM_TICK) < NORMAL_ATTACK_TICK) {
//      return;
//    }
//    if (player.level() instanceof ServerLevel serverLevel) {
////            PlayerAnim.stopAnimation(serverLevel, player, CONTINUOUS_ATTACK);
//      PlayerAnimAPI.playPlayerAnim(
//        serverLevel, player, IPlayerAnim.getAnimationID(END),
//        PlayerParts.allExceptHeadRot(), null, 3000);
//    }
//    nbt.putBoolean(PLAYER_ATTACK, false);
//    nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, false);
//    nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, false);
//    nbt.putBoolean(CANNOT_PLAYER_MOVED, false);
//    nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
    super.onStopUsing(itemStack, usingEntity, count);
  }

  @Override
  public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level world, @NotNull Entity entity, int slotIndex, boolean isCurrentlySelected) {
    if (!isCurrentlySelected || !(entity instanceof Player player) || player.isUsingItem()) {
      return;
    }
    CompoundTag nbt = player.getPersistentData();
//    if (nbt.getBoolean(PLAYER_USE_ITEM)) {
//      return;
//    }
//    if (nbt.getBoolean(PLAYER_ATTACK)) {
//      if (!player.onGround()) return;
//      Minecraft minecraft = Minecraft.getInstance();
//      // 因为不知名BUG因此这么写
//      if (minecraft != null && minecraft.player != null && minecraft.player.input != null) {
//        if (minecraft.player.input.jumping) {
//          forcedInterruption(world, player);
//          return;
//        }
//      }
//      PmTool.incrementNbt(player, PLAYER_USE_ITEM_TICK, 1);
//      if (nbt.getInt(PLAYER_USE_ITEM_TICK) == NORMAL_ATTACK_TICK) {
//        normalAttack(world, player);
//      }
//      if (nbt.getInt(PLAYER_USE_ITEM_TICK) == 10) {
//        nbt.putBoolean(PLAYER_ATTACK, false);
//        nbt.putInt(PLAYER_USE_ITEM_TICK, 0);
//        nbt.putBoolean(CANNOT_PLAYER_SWITCH_ITEMS, false);
//        nbt.putBoolean(CANNOT_PLAYER_ROTATING_PERSPECTIVE, false);
//        nbt.putBoolean(CANNOT_PLAYER_MOVED, false);
//      }
//    }
  }

  /**
   * 強制中断
   */
  public void forcedInterruption(Level world, Player playerEntity) {
  }
}
