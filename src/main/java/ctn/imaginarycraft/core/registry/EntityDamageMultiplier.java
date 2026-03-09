package ctn.imaginarycraft.core.registry;

import ctn.imaginarycraft.api.*;
import net.minecraft.world.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * 实体伤害乘数表
 * <p>
 * 用于管理实体对不同伤害类型的伤害系数，方便统一调整
 *
 */
public final class EntityDamageMultiplier {

  /**
   * 乘数表：实体类型 -> 伤害类型 -> 倍数
   */
  private static final Map<EntityType<?>, Map<LcDamageType, Double>> MULTIPLIER_TABLE = new HashMap<>();

  /**
   * 默认倍数
   */
  private static final double DEFAULT_MULTIPLIER = 1.0;

  /**
   * 获取实体对指定伤害类型的倍数
   *
   * @param entityType 实体类型
   * @param damageType 伤害类型
   * @return 倍数
   */
  public static double getMultiplier(@NotNull EntityType<?> entityType, @NotNull LcDamageType damageType) {
    return MULTIPLIER_TABLE
      .getOrDefault(entityType, Map.of())
      .getOrDefault(damageType, DEFAULT_MULTIPLIER);
  }

  /**
   * 获取实体对指定伤害类型的倍数
   *
   * @param entity     实体
   * @param damageType 伤害类型
   * @return 倍数
   */
  public static double getMultiplier(@NotNull LivingEntity entity, @NotNull LcDamageType damageType) {
    return getMultiplier(entity.getType(), damageType);
  }

  /**
   * 设置实体对指定伤害类型的倍数
   *
   * @param entityType 实体类型
   * @param damageType 伤害类型
   * @param multiplier 倍数
   */
  public static void setMultiplier(@NotNull EntityType<?> entityType, @NotNull LcDamageType damageType, double multiplier) {
    MULTIPLIER_TABLE
      .computeIfAbsent(entityType, k -> new HashMap<>())
      .put(damageType, multiplier);
  }

  /**
   * 设置实体对所有伤害类型的倍数
   *
   * @param entityType 实体类型
   * @param physics    物理
   * @param spirit     精神
   * @param erosion    侵蚀
   * @param theSoul    灵魂
   */
  public static void setMultiplier(@NotNull EntityType<?> entityType,
                                   double physics,
                                   double spirit,
                                   double erosion,
                                   double theSoul) {
    setMultiplier(entityType, LcDamageType.PHYSICS, physics);
    setMultiplier(entityType, LcDamageType.SPIRIT, spirit);
    setMultiplier(entityType, LcDamageType.EROSION, erosion);
    setMultiplier(entityType, LcDamageType.THE_SOUL, theSoul);
  }

  /**
   * 构建器类，提供链式调用
   */
  public static final class Builder {
    private final EntityType<?> entityType;
    private double physics = DEFAULT_MULTIPLIER;
    private double spirit = DEFAULT_MULTIPLIER;
    private double erosion = DEFAULT_MULTIPLIER;
    private double theSoul = DEFAULT_MULTIPLIER;

    private Builder(EntityType<?> entityType) {
      this.entityType = entityType;
    }

    //设置物理伤害倍数
    public Builder physics(double multiplier) {
      this.physics = multiplier;
      return this;
    }

    /**
     * 设置精神伤害倍数
     */
    public Builder spirit(double multiplier) {
      this.spirit = multiplier;
      return this;
    }

    /**
     * 设置侵蚀伤害倍数
     */
    public Builder erosion(double multiplier) {
      this.erosion = multiplier;
      return this;
    }

    /**
     * 设置灵魂伤害倍数
     */
    public Builder theSoul(double multiplier) {
      this.theSoul = multiplier;
      return this;
    }

    /**
     * 设置所有伤害类型倍数
     */
    public Builder all(double multiplier) {
      this.physics = multiplier;
      this.spirit = multiplier;
      this.erosion = multiplier;
      this.theSoul = multiplier;
      return this;
    }

    /**
     * 应用配置
     */
    public void build() {
      setMultiplier(entityType, physics, spirit, erosion, theSoul);
    }
  }

  /**
   * 创建构建器
   *
   * @param entityType 实体类型
   * @return 构建器
   */
  public static Builder builder(EntityType<?> entityType) {
    return new Builder(entityType);
  }

  /**
   * 清空乘数表
   */
  public static void clear() {
    MULTIPLIER_TABLE.clear();
  }

  /**
   * 获取乘数表大小
   */
  public static int size() {
    return MULTIPLIER_TABLE.size();
  }
}
