package ctn.imaginarycraft.init.util;

import ctn.imaginarycraft.common.attribute.BasicAttribute;
import ctn.imaginarycraft.common.attribute.MaxAttribute;
import ctn.imaginarycraft.common.attribute.MinAttribute;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.common.BooleanAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class AttributeRegisterUtil {

  protected static @NotNull <T extends Attribute> DeferredHolder<Attribute, T> register(
    final String name,
    final Function<T, Attribute> function,
    final T attribute
  ) {
    return ModAttributes.REGISTRY.register(name, () -> (T) function.apply(attribute));
  }

  protected static @NotNull DeferredHolder<Attribute, RangedAttribute> register(
    final String name,
    final Function<RangedAttribute, Attribute> function,
    final double defaultValue,
    final double minValue,
    final double maxValue
  ) {
    return register(name, function, new RangedAttribute(descriptionId(name), defaultValue, minValue, maxValue));
  }

  protected static @NotNull DeferredHolder<Attribute, RangedAttribute> register(
    final String name,
    final Function<RangedAttribute, Attribute> function,
    final double minValue,
    final double maxValue
  ) {
    return register(name, function, minValue, minValue, maxValue);
  }

  protected static @NotNull DeferredHolder<Attribute, BasicAttribute> register(
    final String name,
    final Function<BasicAttribute, Attribute> function,
    final double value
  ) {
    return register(name, function, new BasicAttribute(descriptionId(name), value));
  }

  protected static @NotNull DeferredHolder<Attribute, MinAttribute> registerMin(
    final String name,
    final Function<MinAttribute, Attribute> function,
    final double defaultValue,
    final double minValue
  ) {
    return register(name, function, new MinAttribute(descriptionId(name), defaultValue, minValue));
  }

  protected static @NotNull DeferredHolder<Attribute, MaxAttribute> registerMax(
    final String name,
    final Function<MaxAttribute, Attribute> function,
    final double defaultValue,
    final double maxValue
  ) {
    return register(name, function, new MaxAttribute(descriptionId(name), defaultValue, maxValue));
  }

  protected static @NotNull DeferredHolder<Attribute, BooleanAttribute> register(
    final String name,
    final Function<BooleanAttribute, Attribute> function,
    final boolean defaultValue
  ) {
    return register(name, function, new BooleanAttribute(descriptionId(name), defaultValue));
  }

  protected static @NotNull String descriptionId(String name) {
    return ImaginaryCraft.ID + ".attribute.name." + name;
  }
}
