package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.api.lobotomycorporation.damage.LcDamageType;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.util.DamageTypeRegisterUtil;
import ctn.imaginarycraft.mixinextend.IDamageSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 尽量用{@link IDamageSource#setLcDamageType(LcDamageType)}
 */
public final class ModDamageTypes extends DamageTypeRegisterUtil {
  public static final DeferredRegister<DamageType> REGISTRY = ImaginaryCraft.modRegister(Registries.DAMAGE_TYPE);
  /**
   * 物理
   */
  public static final ResourceKey<DamageType> PHYSICS = register("physics");
  /**
   * 精神
   */
  public static final ResourceKey<DamageType> SPIRIT = register("spirit");
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
    register(context, ModDamageTypes.PHYSICS, 0.1f);
    register(context, ModDamageTypes.SPIRIT, 0.2f);
    register(context, ModDamageTypes.EROSION, 0.3f);
    register(context, ModDamageTypes.THE_SOUL, 0.4f);
    register(context, ModDamageTypes.ABNOS, 0.3f);
    register(context, ModDamageTypes.EGO, 0.3f);
  }
}
