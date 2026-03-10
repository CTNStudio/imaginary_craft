package ctn.imaginarycraft.init.world.entity;

import ctn.imaginarycraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.entity.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

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
