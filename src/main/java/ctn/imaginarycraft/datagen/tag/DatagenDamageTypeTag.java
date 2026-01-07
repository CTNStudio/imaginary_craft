package ctn.imaginarycraft.datagen.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModDamageTypes;
import ctn.imaginarycraft.init.tag.ModDamageTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class DatagenDamageTypeTag extends DamageTypeTagsProvider {
  public DatagenDamageTypeTag(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, final ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, ImaginaryCraft.ID, existingFileHelper);
  }

  @Override
  protected void addTags(final HolderLookup.Provider provider) {
    // 物理伤害
    tag(ModDamageTypeTags.PHYSICS).add(
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
      DamageTypes.FLY_INTO_WALL
    ).addOptional(ModDamageTypes.PHYSICS.location());
    // 精神伤害
    tag(ModDamageTypeTags.SPIRIT).add(
      DamageTypes.MOB_PROJECTILE
    ).addOptional(ModDamageTypes.SPIRIT.location());
    // 侵蚀伤害
    tag(ModDamageTypeTags.EROSION).add(
      DamageTypes.WITHER_SKULL,
      DamageTypes.WITHER
    ).addOptional(ModDamageTypes.EROSION.location());
    // 灵魂伤害
    tag(ModDamageTypeTags.THE_SOUL).add(
      DamageTypes.SONIC_BOOM
    ).addOptional(ModDamageTypes.THE_SOUL.location());
    // 绕过
    tag(ModDamageTypeTags.BYPASS_LC).add(
      DamageTypes.IN_WALL,
      DamageTypes.GENERIC,
      DamageTypes.FREEZE,
      DamageTypes.DRAGON_BREATH,
      DamageTypes.MAGIC,
      DamageTypes.INDIRECT_MAGIC,
      DamageTypes.FELL_OUT_OF_WORLD,
      DamageTypes.OUTSIDE_BORDER,
      DamageTypes.STARVE,
      DamageTypes.CRAMMING,
      DamageTypes.GENERIC_KILL
    ).addTag(Tags.DamageTypes.IS_MAGIC);

    tag(DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS)
      .addOptional(ModDamageTypes.PHYSICS.location())
      .addOptional(ModDamageTypes.SPIRIT.location())
      .addOptional(ModDamageTypes.EROSION.location())
      .addOptional(ModDamageTypes.THE_SOUL.location())
      .addOptional(ModDamageTypes.ABNORMALITIES.location())
      .addOptional(ModDamageTypes.EGO.location())
      .addOptional(ModDamageTypes.MELEE.location())
      .addOptional(ModDamageTypes.REMOTE.location());

    tag(DamageTypeTags.IS_PROJECTILE)
      .addOptional(ModDamageTypes.REMOTE.location());

    tag(DamageTypeTags.NO_ANGER)
      .addOptional(ModDamageTypes.REMOTE.location());
  }

  @Contract(pure = true)
  @Override
  public @NotNull String getName() {
    return ImaginaryCraft.NAME + " Damage Type Tags";
  }
}
