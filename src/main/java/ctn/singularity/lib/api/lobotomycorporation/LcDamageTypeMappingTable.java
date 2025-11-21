package ctn.singularity.lib.api.lobotomycorporation;

import ctn.singularity.lib.init.LibDamageTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import java.util.*;

import static ctn.singularity.lib.core.LibMain.LOGGER;

/**
 * 初始化伤害类型映射表
 */
final class LcDamageTypeMappingTable {
  /**
   * 所有伤害类型映射
   */
  static final Map<ResourceKey<DamageType>, LcDamage> DAMAGE_TYPE_MAP = new HashMap<>();
  /**
   * 绕过的伤害类型
   */
  static final Set<ResourceKey<DamageType>> BYPASS_KEYS = new HashSet<>();

  static void init() {
    LOGGER.info("Initialize the damage type mapping table");

    // 清空映射表
    DAMAGE_TYPE_MAP.clear();
    BYPASS_KEYS.clear();

    // 收集所有重复项
    Set<ResourceKey<DamageType>> allKeys = new HashSet<>();
    List<String> duplicates = new ArrayList<>();

    // 添加所有伤害类型映射
    // 绕过伤害
    addDamageTypes(null, allKeys, duplicates, true,
      DamageTypes.IN_WALL,
      DamageTypes.GENERIC,
      DamageTypes.FREEZE,
      DamageTypes.DRAGON_BREATH,
      DamageTypes.MAGIC,
      DamageTypes.FELL_OUT_OF_WORLD,
      DamageTypes.OUTSIDE_BORDER,
      DamageTypes.STARVE,
      DamageTypes.CRAMMING,
      DamageTypes.GENERIC_KILL
    );
    // 灵魂伤害
    addDamageTypes(LcDamage.THE_SOUL, allKeys, duplicates, false,
      DamageTypes.SONIC_BOOM,
      LibDamageTypes.THE_SOUL
    );

    // 侵蚀伤害
    addDamageTypes(LcDamage.EROSION, allKeys, duplicates, false,
      DamageTypes.WITHER_SKULL,
      DamageTypes.WITHER,
      LibDamageTypes.EROSION
    );

    // 精神伤害
    addDamageTypes(LcDamage.SPIRIT, allKeys, duplicates, false,
      DamageTypes.MOB_PROJECTILE,
      LibDamageTypes.SPIRIT
    );

    // 物理伤害
    addDamageTypes(LcDamage.PHYSICS, allKeys, duplicates, false,
      DamageTypes.FALLING_ANVIL,
      DamageTypes.FALLING_BLOCK,
      DamageTypes.FALLING_STALACTITE,
      DamageTypes.FIREWORKS,
      DamageTypes.MOB_ATTACK,
      DamageTypes.MOB_ATTACK_NO_AGGRO,
      DamageTypes.PLAYER_ATTACK,
      DamageTypes.SPIT,
      DamageTypes.STING,
      DamageTypes.SWEET_BERRY_BUSH,
      DamageTypes.THORNS,
      DamageTypes.THROWN,
      DamageTypes.TRIDENT,
      DamageTypes.UNATTRIBUTED_FIREBALL,
      DamageTypes.WIND_CHARGE,
      DamageTypes.ARROW,
      DamageTypes.CACTUS,
      DamageTypes.BAD_RESPAWN_POINT,
      DamageTypes.FALL,
      DamageTypes.FIREBALL,
      DamageTypes.FLY_INTO_WALL,
      LibDamageTypes.PHYSICS
    );

    // 如果有重复项，抛出异常
    if (!duplicates.isEmpty()) {
      throw new RuntimeException("Duplicate damage type keys found: " + String.join(", ", duplicates));
    }
  }

  @SafeVarargs
  private static void addDamageTypes(LcDamage damage, Set<ResourceKey<DamageType>> allKeys, List<String> duplicates, boolean isBypass, ResourceKey<DamageType>... keys) {
    for (ResourceKey<DamageType> key : keys) {
      if (!allKeys.add(key)) {
        duplicates.add(key.toString());
      } else {
        if (isBypass) {
          BYPASS_KEYS.add(key);
        } else {
          DAMAGE_TYPE_MAP.put(key, damage);
        }
      }
    }
  }
}
