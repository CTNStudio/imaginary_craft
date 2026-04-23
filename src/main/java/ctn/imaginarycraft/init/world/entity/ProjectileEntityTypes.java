package ctn.imaginarycraft.init.world.entity;

import ctn.imaginarycraft.api.LcLevel;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.FruitOfUnderstanding;
import ctn.imaginarycraft.common.world.entity.projectile.MagicBulletEntity;
import ctn.imaginarycraft.common.world.entity.projectile.ParadiseLostSpikeweed;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ProjectileEntityTypes {
  public static final DeferredRegister<EntityType<?>> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.ENTITY_TYPE);

  public static final DeferredHolder<EntityType<?>, EntityType<ParadiseLostSpikeweed>> PARADISE_LOST_SPIKEWEED = register(
    "paradise_lost_spikeweed",
    "失乐园尖刺",
    LcLevel.ALEPH,
    EntityType.Builder.of(ParadiseLostSpikeweed::new, MobCategory.MISC)
      .sized(2F, 2.5F)
      .clientTrackingRange(6)
      .updateInterval(2));

  public static final DeferredHolder<EntityType<?>, EntityType<MagicBulletEntity>> MAGIC_BULLET_ENTITY = register(
    "magic_bullet",
    "魔弹",
    LcLevel.WAW,
    EntityType.Builder.<MagicBulletEntity>of(MagicBulletEntity::new, MobCategory.MISC)
      .sized(0.2F, 0.2F)
      .clientTrackingRange(6)
      .updateInterval(1));

	public static final DeferredHolder<EntityType<?>, EntityType<FruitOfUnderstanding.FruitBullet>> FRUIT_OF_UNDERSTANDING_BULLET = register(
		"fruit_bullet",
		"“理解”",
    LcLevel.TETH,
		EntityType.Builder.<FruitOfUnderstanding.FruitBullet>of(FruitOfUnderstanding.FruitBullet::new, MobCategory.MISC)
      .sized(0.3F, 0.3F)
      .clientTrackingRange(4)
      .updateInterval(10));
  static void init(IEventBus bus) {
    REGISTRY.register(bus);
  }

	private static <I extends Entity> DeferredHolder<EntityType<?>, EntityType<I>> register(
		String name, String zhName, LcLevel lcLevel, EntityType.Builder<I> sup
	) {
    return register(name, zhName, lcLevel, () -> sup.build(name));
  }

	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(
		String name, String zhName, LcLevel lcLevel, Supplier<EntityType<T>> sup
	) {
    DeferredHolder<EntityType<?>, EntityType<T>> holder = REGISTRY.register(name, sup);
    ModEntityTypes.lcLevel(lcLevel, holder);
    ZhCn.addI18nEntityTypeText(zhName, holder);
    return holder;
  }
}
