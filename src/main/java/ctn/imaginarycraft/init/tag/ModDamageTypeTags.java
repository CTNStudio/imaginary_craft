package ctn.imaginarycraft.init.tag;

import ctn.imaginarycraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import org.jetbrains.annotations.*;

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
  public static final TagKey<DamageType> BYPASS_LC = createTag("bypass_lobotomy_corporation_damage");

  private static @NotNull TagKey<DamageType> createTag(String name) {
    return createTag(ImaginaryCraft.modRl(name));
  }

  private static @NotNull TagKey<DamageType> createTag(ResourceLocation location) {
    return TagKey.create(Registries.DAMAGE_TYPE, location);
  }

  private static @NotNull TagKey<DamageType> createCTag(String name) {
    return createTag(ResourceLocation.fromNamespaceAndPath("c", name));
  }

  private static @NotNull TagKey<DamageType> createMcTag(String name) {
    return createTag(ResourceLocation.withDefaultNamespace(name));
  }
}
