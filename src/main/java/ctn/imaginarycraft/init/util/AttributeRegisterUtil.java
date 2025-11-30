package ctn.imaginarycraft.init.util;

import ctn.imaginarycraft.common.world.attribute.BasicAttribute;
import ctn.imaginarycraft.common.world.attribute.MaxAttribute;
import ctn.imaginarycraft.common.world.attribute.MinAttribute;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.world.ModAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.BooleanAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class AttributeRegisterUtil {

  protected static @NotNull <T extends Attribute> DeferredHolder<Attribute, T> register(String name,
                                                                                        Function<T, Attribute> function,
                                                                                        T attribute) {
    return ModAttributes.REGISTRY.register(name, () -> (T) function.apply(attribute));
  }

  protected static @NotNull DeferredHolder<Attribute, RangedAttribute> register(String name,
                                                                                Function<RangedAttribute, Attribute> function,
                                                                                double defaultValue,
                                                                                double minValue,
                                                                                double maxValue) {
    return register(name, function, new RangedAttribute(descriptionId(name), defaultValue, minValue, maxValue));
  }

  protected static @NotNull DeferredHolder<Attribute, RangedAttribute> register(String name,
                                                                                Function<RangedAttribute, Attribute> function,
                                                                                double minValue,
                                                                                double maxValue) {
    return register(name, function, minValue, minValue, maxValue);
  }

  protected static @NotNull DeferredHolder<Attribute, BasicAttribute> register(String name,
                                                                               Function<BasicAttribute, Attribute> function,
                                                                               double value) {
    return register(name, function, new BasicAttribute(descriptionId(name), value));
  }

  protected static @NotNull DeferredHolder<Attribute, MinAttribute> registerMin(String name,
                                                                                Function<MinAttribute, Attribute> function,
                                                                                double defaultValue,
                                                                                double minValue) {
    return register(name, function, new MinAttribute(descriptionId(name), defaultValue, minValue));
  }

  protected static @NotNull DeferredHolder<Attribute, MaxAttribute> registerMax(String name,
                                                                                Function<MaxAttribute, Attribute> function,
                                                                                double defaultValue,
                                                                                double maxValue) {
    return register(name, function, new MaxAttribute(descriptionId(name), defaultValue, maxValue));
  }

  protected static @NotNull DeferredHolder<Attribute, BooleanAttribute> register(String name,
                                                                                 Function<BooleanAttribute, Attribute> function,
                                                                                 boolean defaultValue) {
    return register(name, function, new BooleanAttribute(descriptionId(name), defaultValue));
  }

  protected static @NotNull String descriptionId(String name) {
    return ImaginaryCraft.ID + ".attribute.name." + name;
  }
}
