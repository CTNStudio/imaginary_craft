package ctn.singularity.lib.api.lobotomycorporation;

import ctn.ctnapi.client.util.ColorUtil;
import ctn.singularity.lib.api.ColourText;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.LibAttributes;
import ctn.singularity.lib.init.LibDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 脑叶伤害类型
 */
public enum LcDamage implements ColourText, StringRepresentable {
  /**
   * 物理
   */
  PHYSICS(0, "physics", LibAttributes.PHYSICS_VULNERABLE, LibDamageTypes.PHYSICS, "#ff0000"),
  /**
   * 精神
   */
  SPIRIT(1, "spirit", LibAttributes.SPIRIT_VULNERABLE, LibDamageTypes.SPIRIT, "#ffffff"),
  /**
   * 侵蚀
   * <p>
   * 同时造成物理和精神伤害
   */
  EROSION(2, "erosion", LibAttributes.EROSION_VULNERABLE, LibDamageTypes.EROSION, "#8a2be2"),
  /**
   * 灵魂
   * <p>
   * 造成的伤害为：<br/>
   * 伤害*造成的伤害的生物的生命上限百分之一*生物等级差<br/>
   * 如果该生物没有生命上限，则伤害为：<br/>
   * 造成的伤害的生物的伤害*20*0.01*生物等级差
   */
  THE_SOUL(3, "the_soul", LibAttributes.THE_SOUL_VULNERABLE, LibDamageTypes.THE_SOUL, "#00ffff"),
  ;

  private final int index;
  private final String name;
  /**
   * 对应的抗性属性
   */
  private final Holder<Attribute> resistance;
  private final ResourceKey<DamageType> damageTypeResourceKey;
  private final String colour;
  private final int colourValue;

  LcDamage(int index,
           String name,
           Holder<Attribute> resistance,
           ResourceKey<DamageType> damageType,
           String colour) {
    this.index = index;
    this.name = name;
    this.resistance = resistance;
    this.damageTypeResourceKey = damageType;
    this.colour = colour;
    this.colourValue = ColorUtil.rgbColor(colour);
  }

  public static void init() {
    LcDamageTypeMappingTable.init();
  }

  public static LcDamage byName(String name) {
    return Arrays.stream(LcDamage.values()).filter(d -> d.name.equals(name)).findFirst().orElse(null);
  }

  public String getName() {
    return name;
  }

  /**
   * 判断是否包含指定的伤害类型
   *
   * @param damageType 伤害类型
   * @return 是否包含指定的伤害类型
   */
  public boolean contains(@NotNull ResourceKey<DamageType> damageType) {
    return getDamageTypes().contains(damageType);
  }

  /**
   * 判断是否包含指定的伤害类型
   *
   * @param damageType 伤害类型
   * @return 是否包含指定的伤害类型
   */
  public boolean contains(@NotNull Holder<DamageType> damageType) {
    return contains(Objects.requireNonNull(damageType.getKey()));
  }

  /**
   * 根据{@link ResourceKey}获取对应的{@link LcDamage}
   *
   * @param damageType 伤害类型
   * @return 伤害类型对应的伤害类型
   */
  @Nullable
  public static LcDamage byDamageType(@NotNull Holder<DamageType> damageType) {
    return byDamageType(Objects.requireNonNull(damageType.getKey()));
  }

  @Nullable
  public static LcDamage byDamageType(@NotNull ResourceKey<DamageType> damageType) {
    return LcDamageTypeMappingTable.DAMAGE_TYPE_MAP.get(damageType);
  }

  /**
   * 获取所有伤害类型的映射表
   *
   * @return 不可修改的伤害类型映射表
   */
  @Contract(pure = true)
  public static @NotNull @Unmodifiable Map<ResourceKey<DamageType>, LcDamage> getDamageTypeMap() {
    return Map.copyOf(LcDamageTypeMappingTable.DAMAGE_TYPE_MAP);
  }

  /**
   * 获取绕过的伤害类型
   *
   * @return 不可修改的绕过伤害类型集合
   */
  @Contract(pure = true)
  public static @NotNull @Unmodifiable Set<ResourceKey<DamageType>> getBypassKeys() {
    return Set.copyOf(LcDamageTypeMappingTable.BYPASS_KEYS);
  }

  /**
   * 获取伤害类型列表
   *
   * @return 不可修改的伤害类型列表
   */
  public @NotNull @Unmodifiable Set<ResourceKey<DamageType>> getDamageTypes() {
    return Set.copyOf(LcDamageTypeMappingTable.DAMAGE_TYPE_MAP.entrySet().stream()
      .filter(entry -> entry.getValue() == this)
      .map(Map.Entry::getKey)
      .collect(Collectors.toSet()));
  }

  @Override
  public int getColourValue() {
    return colourValue;
  }

  @Override
  public String getColourText() {
    return colour;
  }

  @Override
  public String getColourName() {
    return name;
  }

  public int getIndex() {
    return index;
  }

  public Holder<Attribute> getResistance() {
    return resistance;
  }

  public ResourceKey<DamageType> getDamageType() {
    return damageTypeResourceKey;
  }

  @Contract(pure = true)
  @Override
  public @NotNull String getSerializedName() {
    return LibMain.LIB_ID + "." + getName();
  }
}
