package ctn.imaginarycraft.datagen.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.ModDamageTypes;
import ctn.imaginarycraft.init.world.tag.ModDamageTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class DatagenDamageTypeTag extends DamageTypeTagsProvider {
  /**
   * 物理伤害
   */
  public static final Set<ResourceKey<DamageType>> PHYSICS_KEYS = Set.of(
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
    ModDamageTypes.PHYSICS);
  /**
   * 精神伤害
   */
  public static final Set<ResourceKey<DamageType>> SPIRIT_KEYS = Set.of(
    DamageTypes.MOB_PROJECTILE,
    ModDamageTypes.SPIRIT);
  /**
   * 侵蚀伤害
   */
  public static final Set<ResourceKey<DamageType>> EROSION_KEYS = Set.of(
    DamageTypes.WITHER_SKULL,
    DamageTypes.WITHER,
    ModDamageTypes.EROSION);
  /**
   * 灵魂伤害
   */
  public static final Set<ResourceKey<DamageType>> THE_SOUL_KEYS = Set.of(
    DamageTypes.SONIC_BOOM,
    ModDamageTypes.THE_SOUL);
  /**
   * 绕过脑叶伤害
   */
  public static final Set<ResourceKey<DamageType>> BYPASS_LC_KEYS = Set.of(
    DamageTypes.IN_WALL,
    DamageTypes.GENERIC,
    DamageTypes.FREEZE,
    DamageTypes.DRAGON_BREATH,
    DamageTypes.MAGIC,
    DamageTypes.FELL_OUT_OF_WORLD,
    DamageTypes.OUTSIDE_BORDER,
    DamageTypes.STARVE,
    DamageTypes.CRAMMING,
    DamageTypes.GENERIC_KILL);

  public DatagenDamageTypeTag(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, final @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, ImaginaryCraft.ID, existingFileHelper);
  }

  @Override
  protected void addTags(final HolderLookup.Provider provider) {
    super.addTags(provider);
    // 物理伤害
    tag(ModDamageTypeTags.PHYSICS)
      .addAll(List.copyOf(PHYSICS_KEYS));
    // 精神伤害
    tag(ModDamageTypeTags.SPIRIT)
      .addAll(List.copyOf(SPIRIT_KEYS));
    // 侵蚀伤害
    tag(ModDamageTypeTags.EROSION)
      .addAll(List.copyOf(EROSION_KEYS));
    // 灵魂伤害
    tag(ModDamageTypeTags.THE_SOUL)
      .addAll(List.copyOf(THE_SOUL_KEYS));
    // 绕过
    tag(ModDamageTypeTags.BYPASS_LC)
      .addAll(List.copyOf(BYPASS_LC_KEYS));
  }
}
