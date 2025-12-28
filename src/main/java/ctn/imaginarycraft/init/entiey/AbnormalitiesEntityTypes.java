package ctn.imaginarycraft.init.entiey;

import ctn.imaginarycraft.common.entity.abnormalities.TrainingRabbits;
import ctn.imaginarycraft.common.entity.abnormalities.ordeals.violet.GrantUsLove;
import ctn.imaginarycraft.common.entity.projectile.MagicBulletEntity;
import ctn.imaginarycraft.common.entity.projectile.ParadiseLostSpikeweed;
import ctn.imaginarycraft.core.ImaginaryCraft;
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

  public static final Supplier<EntityType<TrainingRabbits>> TRAINING_RABBITS = register(
    "training_rabbits",
    EntityType.Builder.of(TrainingRabbits::new, MobCategory.MISC)
      .sized(0.625F, 1.375F)
      .eyeHeight(1F)
      .clientTrackingRange(8)
      .updateInterval(2)
      .canSpawnFarFromPlayer());

  public static final Supplier<EntityType<GrantUsLove>> GRANT_US_LOVE = register(
    "grant_us_love",
    EntityType.Builder.of(GrantUsLove::new, MobCategory.MISC)
      .sized(1.5F, 3F)
      .eyeHeight(2.0F)
      .clientTrackingRange(8)
      .updateInterval(2));

  public static final Supplier<EntityType<ParadiseLostSpikeweed>> PARADISE_LOST_SPIKEWEED = register(
    "paradise_lost_spikeweed",
    EntityType.Builder.of(ParadiseLostSpikeweed::new, MobCategory.MISC)
      .sized(2F, 2.5F)
      .clientTrackingRange(6)
      .updateInterval(2));

  public static final Supplier<EntityType<MagicBulletEntity>> MAGIC_BULLET_ENTITY = register(
    "magic_bullet",
    EntityType.Builder.<MagicBulletEntity>of(MagicBulletEntity::new, MobCategory.MISC)
      .sized(0.2F, 0.2F)
      .clientTrackingRange(6)
      .updateInterval(1));

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

  public static <I extends Entity> Supplier<EntityType<I>> register(final String name, final EntityType.Builder<I> sup) {
    return register(name, () -> sup.build(name));
  }

  public static <I extends EntityType<?>> DeferredHolder<EntityType<?>, I> register(final String name, final Supplier<? extends I> sup) {
    return REGISTRY.register(name, sup);
  }
}
