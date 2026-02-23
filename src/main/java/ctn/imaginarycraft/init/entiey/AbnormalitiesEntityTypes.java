package ctn.imaginarycraft.init.entiey;

import ctn.imaginarycraft.api.LcLevelType;
import ctn.imaginarycraft.common.entity.abnormalities.TrainingRabbits;
import ctn.imaginarycraft.common.entity.abnormalities.ordeals.violet.GrantUsLove;
import ctn.imaginarycraft.common.entity.projectile.MagicBulletEntity;
import ctn.imaginarycraft.common.entity.projectile.ParadiseLostSpikeweed;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.core.registry.CapabilityRegistry;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class AbnormalitiesEntityTypes {
  public static final DeferredRegister<EntityType<?>> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.ENTITY_TYPE);

  public static final DeferredHolder<EntityType<?>, EntityType<TrainingRabbits>> TRAINING_RABBITS = register(
    "training_rabbits",
    "训练兔兔",
    LcLevelType.TETH,
    EntityType.Builder.of(TrainingRabbits::new, MobCategory.MISC)
      .sized(0.625F, 1.375F)
      .eyeHeight(1F)
      .clientTrackingRange(8)
      .updateInterval(2)
      .canSpawnFarFromPlayer());

  public static final DeferredHolder<EntityType<?>, EntityType<GrantUsLove>> GRANT_US_LOVE = register(
    "grant_us_love",
    "“请给我们爱”",
    LcLevelType.HE,
    EntityType.Builder.of(GrantUsLove::new, MobCategory.MISC)
      .sized(1.5F, 3F)
      .eyeHeight(2.0F)
      .clientTrackingRange(8)
      .updateInterval(2));

  public static final DeferredHolder<EntityType<?>, EntityType<ParadiseLostSpikeweed>> PARADISE_LOST_SPIKEWEED = register(
    "paradise_lost_spikeweed",
    "失乐园尖刺",
    LcLevelType.ALEPH,
    EntityType.Builder.of(ParadiseLostSpikeweed::new, MobCategory.MISC)
      .sized(2F, 2.5F)
      .clientTrackingRange(6)
      .updateInterval(2));

  public static final DeferredHolder<EntityType<?>, EntityType<MagicBulletEntity>> MAGIC_BULLET_ENTITY = register(
    "magic_bullet",
    "魔弹",
    LcLevelType.WAW,
    EntityType.Builder.<MagicBulletEntity>of(MagicBulletEntity::new, MobCategory.MISC)
      .sized(0.2F, 0.2F)
      .clientTrackingRange(6)
      .updateInterval(1));

  static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  private static <I extends Entity> DeferredHolder<EntityType<?>, EntityType<I>> register(String name, String zhName,
                                                                                          LcLevelType lcLevelType,
                                                                                          EntityType.Builder<I> sup) {
    return register(name, zhName, lcLevelType, () -> sup.build(name));
  }

  private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, String zhName,
                                                                                          LcLevelType lcLevelType,
                                                                                          Supplier<EntityType<T>> sup) {
    DeferredHolder<EntityType<?>, EntityType<T>> register = REGISTRY.register(name, sup);
    Supplier<EntityType<?>> supplier = (Supplier<EntityType<?>>) (Object) register;
    (switch (lcLevelType) {
      case ZAYIN -> CapabilityRegistry.ENTITY_ZAYIN;
      case TETH -> CapabilityRegistry.ENTITY_TETH;
      case HE -> CapabilityRegistry.ENTITY_HE;
      case WAW -> CapabilityRegistry.ENTITY_WAW;
      case ALEPH -> CapabilityRegistry.ENTITY_ALEPH;
    }).add(supplier);
    ZhCn.clientAddI18nEntityTypeText(zhName, supplier);
    return register;
  }
}
