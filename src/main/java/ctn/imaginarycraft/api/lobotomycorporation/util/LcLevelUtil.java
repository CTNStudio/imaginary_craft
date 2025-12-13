package ctn.imaginarycraft.api.lobotomycorporation.util;

import ctn.imaginarycraft.api.capability.block.IBlockLcLevel;
import ctn.imaginarycraft.api.capability.entity.IEntityLcLevel;
import ctn.imaginarycraft.api.capability.item.IItemLcLevel;
import ctn.imaginarycraft.api.lobotomycorporation.LcLevelType;
import ctn.imaginarycraft.init.ModCapabilitys;
import ctn.imaginarycraft.registry.RegistryCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public final class LcLevelUtil {
  @Nullable
  public static LcLevelType getLevel(@NotNull Entity entity) {
    IEntityLcLevel capability = entity.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ENTITY);
    return capability == null ? LcLevelType.ZAYIN : capability.getLcLevel();
  }

  @Nullable
  public static LcLevelType getLevel(@NotNull ItemStack item) {
    IItemLcLevel capability = item.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_ITEM);
    return capability == null ? LcLevelType.ZAYIN : capability.getLcLevel(item);
  }

  @Nullable
  public static LcLevelType getLevel(@NotNull Level level, @NotNull BlockPos pos) {
    IBlockLcLevel capability = level.getCapability(ModCapabilitys.LcLevel.LC_LEVEL_BLOCK, pos, level.getBlockState(pos), level.getBlockEntity(pos));
    return capability == null ? LcLevelType.ZAYIN : capability.getLcLevel(level, pos);
  }

  /**
   * 获取伤害比例
   *
   * @param attackedLevel 被攻击的等级
   * @param attackerLevel 攻击者的等级
   */
  public static float getDamageMultiple(@Nullable LcLevelType attackedLevel, @Nullable LcLevelType attackerLevel) {
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
   * @param lcLevelType  物品等级
   * @param deferredItem 物品
   */
  public static <I extends Item> void addItemLcLevelCapability(LcLevelType lcLevelType, DeferredItem<I> deferredItem) {
    switch (lcLevelType) {
      case ZAYIN -> RegistryCapability.ITEM_ZAYIN.add(deferredItem);
      case TETH -> RegistryCapability.ITEM_TETH.add(deferredItem);
      case HE -> RegistryCapability.ITEM_HE.add(deferredItem);
      case WAW -> RegistryCapability.ITEM_WAW.add(deferredItem);
      case ALEPH -> RegistryCapability.ITEM_ALEPH.add(deferredItem);
    }
  }
}
