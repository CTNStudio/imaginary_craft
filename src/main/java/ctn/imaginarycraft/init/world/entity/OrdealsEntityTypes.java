package ctn.imaginarycraft.init.world.entity;

import ctn.imaginarycraft.api.LcLevel;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.FruitOfUnderstanding;
import ctn.imaginarycraft.common.world.entity.ordeals.violet.GrantUsLove;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class OrdealsEntityTypes {
	public static final DeferredRegister<EntityType<?>> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.ENTITY_TYPE);

  //region 紫罗兰
  public static final DeferredHolder<EntityType<?>, EntityType<GrantUsLove>> GRANT_US_LOVE = register(
    "grant_us_love",
    "“请给我们爱”",
    LcLevel.HE,
    EntityType.Builder.of(GrantUsLove::new, MobCategory.MISC)
	    .fireImmune()
	    .immuneTo(Blocks.POWDER_SNOW)
	    .canSpawnFarFromPlayer()
      .sized(2.0F, 5F)
      .eyeHeight(2.5F)
      .clientTrackingRange(8)
      .updateInterval(2));
  public static final DeferredHolder<EntityType<?>, EntityType<FruitOfUnderstanding>> FRUIT_OF_UNDERSTANDING = register(
    "fruit_of_understanding",
    "“理解的果实”",
    LcLevel.TETH,
    EntityType.Builder.of(FruitOfUnderstanding::new, MobCategory.MISC)
      .sized(1.7F, 1.7F)
      .eyeHeight(1.0F)
      .clientTrackingRange(8)
	    .updateInterval(3));
  //endregion

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
