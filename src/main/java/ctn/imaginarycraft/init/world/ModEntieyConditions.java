package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.common.world.entity.condition.MobEffectsCondition;
import ctn.imaginarycraft.core.ImaginaryCraft;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.registry.EpicFightRegistries;

import java.util.function.Supplier;

public final class ModEntieyConditions {
  public static final DeferredRegister<Supplier<Condition<?>>> REGISTRY = ImaginaryCraft.modRegister(EpicFightRegistries.CONDITION);

  public static final DeferredHolder<Supplier<Condition<?>>, Supplier<Condition<?>>> MOB_EFFECTS = register("mob_effects", () -> MobEffectsCondition::new);

  private static <T extends Supplier<Condition<?>>> DeferredHolder<Supplier<Condition<?>>, T> register(String name, Supplier<T> sup) {
    return REGISTRY.register(name, sup);
  }
}
