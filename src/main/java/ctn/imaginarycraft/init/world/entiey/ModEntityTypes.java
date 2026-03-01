package ctn.imaginarycraft.init.world.entiey;

import ctn.imaginarycraft.core.ImaginaryCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModEntityTypes {
  public static final DeferredRegister<EntityType<?>> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.ENTITY_TYPE);

  public static void init(IEventBus bus) {
    REGISTRY.register(bus);
    AbnormalitiesEntityTypes.init(bus);
  }

  private static <I extends Entity> Supplier<EntityType<I>> register(final String name, final EntityType.Builder<I> sup) {
    return register(name, () -> sup.build(name));
  }

  private static <I extends EntityType<?>> DeferredHolder<EntityType<?>, I> register(final String name, final Supplier<? extends I> sup) {
    return REGISTRY.register(name, sup);
  }
}
