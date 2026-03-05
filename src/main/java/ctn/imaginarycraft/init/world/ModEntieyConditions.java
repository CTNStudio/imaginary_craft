package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.common.world.entity.condition.*;
import ctn.imaginarycraft.core.*;
import net.neoforged.neoforge.registries.*;
import yesman.epicfight.data.conditions.*;
import yesman.epicfight.registry.*;

import java.util.function.*;

public final class ModEntieyConditions {
  public static final DeferredRegister<Supplier<Condition<?>>> REGISTRY = ImaginaryCraft.modRegister(EpicFightRegistries.CONDITION);

  public static final DeferredHolder<Supplier<Condition<?>>, Supplier<Condition<?>>> MOB_EFFECTS = register("mob_effects", () -> MobEffectsCondition::new);

  private static <T extends Supplier<Condition<?>>> DeferredHolder<Supplier<Condition<?>>, T> register(String name, Supplier<T> sup) {
    return REGISTRY.register(name, sup);
  }
}
