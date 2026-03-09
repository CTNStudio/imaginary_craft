package ctn.imaginarycraft.util;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.core.capability.block.*;
import ctn.imaginarycraft.core.capability.entity.*;
import ctn.imaginarycraft.core.capability.item.*;
import ctn.imaginarycraft.core.registry.*;
import ctn.imaginarycraft.init.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.*;

public final class LcLevelUtil {
  // 仅内部开发使用
  @ApiStatus.Internal
  public static final Map<EntityType<?>, @Nullable LcLevel> ENTITY_TYPE_LEVEL = new HashMap<>();

  /**
   * 如果没注册到能力中返回LcLevel.ZAYIN
   * <p>
   * 如果有，则尝试从能力系统中获取如果获取的是null那就返回null
   */
  @Nullable
  public static LcLevel getLevel(@NotNull Entity entity) {
    IEntityLcLevel capability = entity.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ENTITY);
    return capability == null ? LcLevel.ZAYIN : capability.getLcLevel();
  }

  /**
   * 如果没注册到能力中返回LcLevel.ZAYIN
   * <p>
   * 如果有，则尝试从能力系统中获取如果获取的是null那就返回null
   */
  @Nullable
  public static LcLevel getLevel(@NotNull EntityType<?> entity) {
    if (!ENTITY_TYPE_LEVEL.containsKey(entity)) {
      ENTITY_TYPE_LEVEL.put(entity, LcLevel.ZAYIN);
      return LcLevel.ZAYIN;
    }
    return ENTITY_TYPE_LEVEL.get(entity);
  }

  /**
   * 如果没注册到能力中返回LcLevel.ZAYIN
   * <p>
   * 如果有，则尝试从能力系统中获取如果获取的是null那就返回null
   */
  @Nullable
  public static LcLevel getLevel(@NotNull ItemStack itemStack) {
    if (itemStack.getItem() instanceof SpawnEggItem spawnEggItem) {
      // 如果是刷怪蛋就按照生物等级来获取
      return getLevel(spawnEggItem.getType(itemStack));
    }
    IItemLcLevel capability = itemStack.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ITEM);
    return capability == null ? LcLevel.ZAYIN : capability.getLcLevel(itemStack);
  }

  /**
   * 如果没注册到能力中返回LcLevel.ZAYIN
   * <p>
   * 如果有，则尝试从能力系统中获取如果获取的是null那就返回null
   */
  @Nullable
  public static LcLevel getLevel(@NotNull Level level, @NotNull BlockPos pos) {
    return getLevel(level, pos, level.getBlockState(pos), level.getBlockEntity(pos));
  }

  /**
   * 如果没注册到能力中返回LcLevel.ZAYIN
   * <p>
   * 如果有，则尝试从能力系统中获取如果获取的是null那就返回null
   */
  @Nullable
  public static LcLevel getLevel(@NotNull Level level, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity) {
    IBlockLcLevel capability = level.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_BLOCK, pos, state, blockEntity);
    return capability == null ? LcLevel.ZAYIN : capability.getLcLevel(level, pos);
  }

  /**
   * 获取伤害比例
   *
   * @param attackedLevel 被攻击的等级
   * @param attackerLevel 攻击者的等级
   */
  public static float getDamageMultiple(@Nullable LcLevel attackedLevel, @Nullable LcLevel attackerLevel) {
    if (attackedLevel == null || attackerLevel == null) {
      return 1.0f;
    }
    int attackedLevelValue = attackedLevel.getLevelValue();
    int attackerLevelValue = attackerLevel.getLevelValue();
    int i = attackedLevelValue - attackerLevelValue;
    return switch (i) {
      case 4 -> 0.4F;
      case 3 -> 0.6F;
      case 2 -> 0.7F;
      case 1 -> 0.8F;
      case 0, -1 -> 1.0F;
      case -2 -> 1.2F;
      case -3 -> 1.5F;
      case -4 -> 2.0F;
      default -> throw new IllegalArgumentException("Invalid levelValue difference: " + i);
    };
  }

  /**
   * 添加物品等级能力
   *
   * @param lcLevel      物品等级
   * @param deferredItem 物品
   */
  public static <I extends Item> void addItemLcLevelCapability(LcLevel lcLevel, DeferredItem<I> deferredItem) {
    switch (lcLevel) {
      case ZAYIN -> CapabilityRegistry.ITEM_ZAYIN.add(deferredItem);
      case TETH -> CapabilityRegistry.ITEM_TETH.add(deferredItem);
      case HE -> CapabilityRegistry.ITEM_HE.add(deferredItem);
      case WAW -> CapabilityRegistry.ITEM_WAW.add(deferredItem);
      case ALEPH -> CapabilityRegistry.ITEM_ALEPH.add(deferredItem);
    }
  }
}
