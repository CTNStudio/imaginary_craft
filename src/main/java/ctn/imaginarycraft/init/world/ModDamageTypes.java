package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.init.util.DamageTypeRegisterUtil;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;

public final class ModDamageTypes extends DamageTypeRegisterUtil {
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
   * 异想体
   */
  public static final ResourceKey<DamageType> ABNORMALITIES = register("abnormalities");
  /**
   * E.G.O
   * <p>
   * Extermination of Geometrical Organ 是的没错这玩意的全称就是这么长
   */
  public static final ResourceKey<DamageType> EGO = register("ego");

  public static void bootstrap(BootstrapContext<DamageType> context) {
    register(context, "physics", ModDamageTypes.PHYSICS, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1f, DamageEffects.HURT, DeathMessageType.DEFAULT);
    register(context, "spirit", ModDamageTypes.SPIRIT, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.2f, DamageEffects.HURT, DeathMessageType.DEFAULT);
    register(context, "erosion", ModDamageTypes.EROSION, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.3f, DamageEffects.HURT, DeathMessageType.DEFAULT);
    register(context, "theSoul", ModDamageTypes.THE_SOUL, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.4f, DamageEffects.HURT, DeathMessageType.DEFAULT);
    register(context, "abnormalities", ModDamageTypes.ABNORMALITIES, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.3f, DamageEffects.HURT, DeathMessageType.DEFAULT);
    register(context, "ego", ModDamageTypes.EGO, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.3f, DamageEffects.HURT, DeathMessageType.DEFAULT);
  }
}
