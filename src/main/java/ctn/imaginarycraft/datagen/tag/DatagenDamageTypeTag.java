package ctn.imaginarycraft.datagen.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.tag.ModDamageTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

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
      DamageTypes.FLY_INTO_WALL/*,
      ModDamageTypes.PHYSICS*/);
    // 精神伤害
    tag(ModDamageTypeTags.SPIRIT).add(
      DamageTypes.MOB_PROJECTILE/*,
      ModDamageTypes.SPIRIT*/);
    // 侵蚀伤害
    tag(ModDamageTypeTags.EROSION).add(
      DamageTypes.WITHER_SKULL,
      DamageTypes.WITHER/*,
      ModDamageTypes.EROSION*/);
    // 灵魂伤害
    tag(ModDamageTypeTags.THE_SOUL).add(
      DamageTypes.SONIC_BOOM/*,
      ModDamageTypes.THE_SOUL*/);
    // 绕过
    tag(ModDamageTypeTags.BYPASS_LC).add(
      DamageTypes.IN_WALL,
      DamageTypes.GENERIC,
      DamageTypes.FREEZE,
      DamageTypes.DRAGON_BREATH,
      DamageTypes.MAGIC,
      DamageTypes.FELL_OUT_OF_WORLD,
      DamageTypes.OUTSIDE_BORDER,
      DamageTypes.STARVE,
      DamageTypes.CRAMMING/*,
      DamageTypes.GENERIC_KILL*/);
  }

  @Override
  public String getName() {
    return ImaginaryCraft.NAME + " Damage Type Tags";
  }
}
