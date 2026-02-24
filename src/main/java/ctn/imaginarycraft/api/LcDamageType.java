package ctn.imaginarycraft.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ctn.imaginarycraft.client.ModFontIcon;
import ctn.imaginarycraft.client.util.ColorUtil;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.ModDamageSources;
import ctn.imaginarycraft.init.ModDamageTypes;
import ctn.imaginarycraft.init.tag.ModDamageTypeTags;
import ctn.imaginarycraft.util.LcDamageUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 脑叶伤害类型
 */
public enum LcDamageType implements ColourText, StringRepresentable {
  /**
   * 物理
   */
  PHYSICS(0, "physics", ModFontIcon.PHYSICS, ModFontIcon.PHYSICS_8X, ModAttributes.PHYSICS_VULNERABLE, ModDamageTypes.PHYSICS, "#ff0000"),
  /**
   * 精神
   */
  SPIRIT(1, "spirit", ModFontIcon.SPIRIT, ModFontIcon.SPIRIT_8X, ModAttributes.SPIRIT_VULNERABLE, ModDamageTypes.SPIRIT, "#ffffff"),
  /**
   * 侵蚀
   * <p>
   * 同时造成物理和精神伤害
   */
  EROSION(2, "erosion", ModFontIcon.EROSION, ModFontIcon.EROSION_8X, ModAttributes.EROSION_VULNERABLE, ModDamageTypes.EROSION, "#8a2be2"),
  /**
   * 灵魂
   * <p>
   * 伤害计算参考 {@link LcDamageUtil#theSoulDamage(float, LivingEntity, Entity, DamageSource)}
   */
  THE_SOUL(3, "the_soul", ModFontIcon.THE_SOUL, ModFontIcon.THE_SOUL_8X, ModAttributes.THE_SOUL_VULNERABLE, ModDamageTypes.THE_SOUL, "#00ffff"),
  ;

  public static final Codec<LcDamageType> CODEC = StringRepresentable
    .fromEnum(LcDamageType::values).validate(DataResult::success);
  public static final StreamCodec<ByteBuf, LcDamageType> STREAM_CODEC = ByteBufCodecs
    .idMapper(ByIdMap.continuous(LcDamageType::getIndex, values(), ByIdMap.OutOfBoundsStrategy.WRAP), LcDamageType::getIndex);

  private final int index;
  private final String name;
  private final ModFontIcon charIcon;
  private final ModFontIcon char8xIcon;
  /**
   * 对应的易伤属性
   */
  private final Holder<Attribute> vulnerable;
  private final ResourceKey<DamageType> damageTypeResourceKey;
  private final String colour;
  private final int colourValue;

  LcDamageType(final int index,
               final String name,
               final ModFontIcon charIcon,
               final ModFontIcon char8xIcon,
               final Holder<Attribute> vulnerable,
               final ResourceKey<DamageType> damageType,
               final String colour) {
    this.index = index;
    this.name = name;
    this.charIcon = charIcon;
    this.char8xIcon = char8xIcon;
    this.vulnerable = vulnerable;
    this.damageTypeResourceKey = damageType;
    this.colour = colour;
    this.colourValue = ColorUtil.rgbColor(colour);
  }

  public static LcDamageType byName(String name) {
    return Arrays.stream(LcDamageType.values()).filter(d -> d.name.equals(name)).findFirst().orElse(null);
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

  public String getName() {
    return name;
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

  @Contract("_ -> new")
  public @NotNull DamageSource getDamageSources(Entity causingEntity) {
    return ModDamageSources.createDamage(getDamageTypeResourceKey(), causingEntity);
  }

  @Contract("_, _ -> new")
  public @NotNull DamageSource getDamageSources(@Nullable Entity directEntity, @NotNull Entity causingEntity) {
    return ModDamageSources.createDamage(getDamageTypeResourceKey(), directEntity, causingEntity);
  }

  @Contract("_ -> new")
  public @NotNull DamageSource getNoSourceDamageSources(@NotNull Entity entity) {
    return getNoSourceDamageSources(entity.level());
  }

  @Contract("_ -> new")
  public @NotNull DamageSource getNoSourceDamageSources(@NotNull Level level) {
    return getNoSourceDamageSources(level.registryAccess());
  }

  @Contract("_ -> new")
  public @NotNull DamageSource getNoSourceDamageSources(@NotNull RegistryAccess registryAccess) {
    return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(getDamageTypeResourceKey()), null, null);
  }

  @Contract(pure = true)
  @Override
  public @NotNull String getSerializedName() {
    return ImaginaryCraft.modRlText(getName());
  }

  public ModFontIcon getChar() {
    return charIcon;
  }

  public ModFontIcon getChar8x() {
    return char8xIcon;
  }


  public record Component(LcDamageType lcDamageType, Set<LcDamageType> canCauseLcDamageTypes) {
    public static final Codec<Set<LcDamageType>> SET_CODEC = Codec.list(LcDamageType.CODEC).xmap(Sets::newHashSet, Lists::newArrayList);
    public static final Codec<Component> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
        LcDamageType.CODEC.fieldOf("lc_damage_type").forGetter(Component::lcDamageType),
        SET_CODEC.fieldOf("can_cause_lc_damage_types")
          .forGetter(Component::canCauseLcDamageTypes)
      ).apply(instance, Component::new));
    public static final StreamCodec<ByteBuf, Component> STREAM_CODEC = StreamCodec.composite(
      LcDamageType.STREAM_CODEC, Component::lcDamageType,
      ByteBufCodecs.collection(HashSet::newHashSet, LcDamageType.STREAM_CODEC), Component::canCauseLcDamageTypes,
      Component::new);

    public Component(LcDamageType lcDamageType) {
      this(lcDamageType, Set.of(lcDamageType));
    }

    public Component(LcDamageType lcDamageType, LcDamageType... canCauseLcDamageTypes) {
      this(lcDamageType, Set.of(canCauseLcDamageTypes));
    }
  }
}
