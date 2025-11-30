package ctn.imaginarycraft.api.lobotomycorporation.damage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import ctn.ctnapi.client.util.ColorUtil;
import ctn.imaginarycraft.api.ColourText;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.ModAttributes;
import ctn.imaginarycraft.init.world.ModDamageTypes;
import ctn.imaginarycraft.init.world.tag.ModDamageTypeTags;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.IntFunction;

/**
 * 脑叶伤害类型
 */
public enum LcDamageType implements ColourText, StringRepresentable {
  /**
   * 物理
   */
  PHYSICS(0, "physics", ModAttributes.PHYSICS_VULNERABLE, ModDamageTypes.PHYSICS, "#ff0000"),
  /**
   * 精神
   */
  SPIRIT(1, "spirit", ModAttributes.SPIRIT_VULNERABLE, ModDamageTypes.SPIRIT, "#ffffff"),
  /**
   * 侵蚀
   * <p>
   * 同时造成物理和精神伤害
   */
  EROSION(2, "erosion", ModAttributes.EROSION_VULNERABLE, ModDamageTypes.EROSION, "#8a2be2"),
  /**
   * 灵魂
   * <p>
   * 造成的伤害为：<br/>
   * 伤害*造成的伤害的生物的生命上限百分之一*生物等级差<br/>
   * 如果该生物没有生命上限，则伤害为：<br/>
   * 造成的伤害的生物的伤害*20*0.01*生物等级差
   */
  THE_SOUL(3, "the_soul", ModAttributes.THE_SOUL_VULNERABLE, ModDamageTypes.THE_SOUL, "#00ffff"),
  ;

  public static final StringRepresentable.EnumCodec<LcDamageType> CODEC = StringRepresentable.fromEnum(LcDamageType::values);
  public static final Codec<LcDamageType> VERTICAL_CODEC = CODEC.validate(DataResult::success);
  public static final IntFunction<LcDamageType> BY_ID = ByIdMap.continuous(LcDamageType::getIndex, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
  public static final StreamCodec<ByteBuf, LcDamageType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, LcDamageType::getIndex);

  private final int index;
  private final String name;
  /**
   * 对应的抗性属性
   */
  private final Holder<Attribute> vulnerable;
  private final ResourceKey<DamageType> damageTypeResourceKey;
  private final String colour;
  private final int colourValue;

  LcDamageType(int index,
               String name,
               Holder<Attribute> vulnerable,
               ResourceKey<DamageType> damageType,
               String colour) {
    this.index = index;
    this.name = name;
    this.vulnerable = vulnerable;
    this.damageTypeResourceKey = damageType;
    this.colour = colour;
    this.colourValue = ColorUtil.rgbColor(colour);
  }

  public static LcDamageType byName(String name) {
    return Arrays.stream(LcDamageType.values()).filter(d -> d.name.equals(name)).findFirst().orElse(null);
  }

  public String getName() {
    return name;
  }

  /**
   * 根据{@link Holder}获取对应的{@link LcDamageType}
   *
   * @param damageType 伤害类型
   * @return 伤害类型对应的伤害类型，返回NULL则绕过LC伤害系统
   */
  @Nullable
  public static LcDamageType byDamageType(@NotNull Holder<DamageType> damageType) {
    if (damageType.is(ModDamageTypeTags.BYPASS_LC)) {
      return null;
    }
    if (damageType.is(ModDamageTypeTags.PHYSICS)) {
      return PHYSICS;
    }
    if (damageType.is(ModDamageTypeTags.SPIRIT)) {
      return SPIRIT;
    }
    if (damageType.is(ModDamageTypeTags.EROSION)) {
      return EROSION;
    }
    if (damageType.is(ModDamageTypeTags.THE_SOUL)) {
      return THE_SOUL;
    }
    // 默认为物理伤害
    return PHYSICS;
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

  public Holder<Attribute> getVulnerable() {
    return vulnerable;
  }

  public ResourceKey<DamageType> getDamageTypeResourceKey() {
    return damageTypeResourceKey;
  }

  @Contract(pure = true)
  @Override
  public @NotNull String getSerializedName() {
    return ImaginaryCraft.modRlText(getName());
  }
}
