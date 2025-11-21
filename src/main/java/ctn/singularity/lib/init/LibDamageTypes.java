package ctn.singularity.lib.init;

import ctn.singularity.lib.api.lobotomycorporation.LcDamage;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.util.DamageTypeRegisterUtil;
import ctn.singularity.lib.mixinextend.IModDamageSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 尽量用{@link IModDamageSource#setLcDamage(LcDamage)}
 */
public final class LibDamageTypes extends DamageTypeRegisterUtil {
  public static final DeferredRegister<DamageType> REGISTRY = LibMain.modRegister(Registries.DAMAGE_TYPE);
  /**
   * 物理
   */
  public static final ResourceKey<DamageType> PHYSICS = register("physics");
  /**
   * 精神
   */
  public static final ResourceKey<DamageType> SPIRIT = register("rationality");
  /**
   * 侵蚀
   */
  public static final ResourceKey<DamageType> EROSION = register("erosion");
  /**
   * 灵魂
   */
  public static final ResourceKey<DamageType> THE_SOUL = register("the_soul");
  /**
   * ABNORMALITIES 异想体
   */
  public static final ResourceKey<DamageType> ABNOS = register("abnos");
  /**
   * EGO
   * <p>
   * Extermination of Geometrical Organ 是的没错这玩意的全称就是这么长
   */
  public static final ResourceKey<DamageType> EGO = register("ego");

  public static void bootstrap(BootstrapContext<DamageType> context) {
    register(context, LibDamageTypes.PHYSICS, 0.1f);
    register(context, LibDamageTypes.SPIRIT, 0.2f);
    register(context, LibDamageTypes.EROSION, 0.3f);
    register(context, LibDamageTypes.THE_SOUL, 0.4f);
    register(context, LibDamageTypes.ABNOS, 0.3f);
    register(context, LibDamageTypes.EGO, 0.3f);
  }
}
