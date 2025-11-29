package ctn.imaginarycraft.init.world.tag;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public final class ModDamageTypeTags {
  /**
   * 物理
   */
  public static final TagKey<DamageType> PHYSICS = createTag("physics");
  /**
   * 精神
   */
  public static final TagKey<DamageType> SPIRIT = createTag("spirit");
  /**
   * 侵蚀
   */
  public static final TagKey<DamageType> EROSION = createTag("erosion");
  /**
   * 灵魂
   */
  public static final TagKey<DamageType> THE_SOUL = createTag("the_soul");
  /**
   * 绕过脑叶伤害
   */
  public static final TagKey<DamageType> BYPASS_LC = createTag("bypass_lobotomy_corporation");

  private static TagKey<DamageType> createTag(String name) {
    return createTag(ImaginaryCraft.modRl(name));
  }

  private static TagKey<DamageType> createTag(ResourceLocation location) {
    return TagKey.create(Registries.DAMAGE_TYPE, location);
  }

  private static TagKey<DamageType> createCTag(String name) {
    return createTag(ResourceLocation.fromNamespaceAndPath("c", name));
  }

  private static TagKey<DamageType> createMcTag(String name) {
    return createTag(ResourceLocation.withDefaultNamespace(name));
  }
}
