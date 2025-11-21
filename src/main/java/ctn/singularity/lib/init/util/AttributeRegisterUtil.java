package ctn.singularity.lib.init.util;

import ctn.singularity.lib.common.attribute.BasicAttribute;
import ctn.singularity.lib.common.attribute.MaxAttribute;
import ctn.singularity.lib.common.attribute.MinAttribute;
import ctn.singularity.lib.core.LibMain;
import ctn.singularity.lib.init.LibAttributes;
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
    return LibAttributes.REGISTRY.register(name, () -> (T) function.apply(attribute));
  }

  protected static@NotNull DeferredHolder<Attribute, RangedAttribute> register(String name,
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

  protected static@NotNull DeferredHolder<Attribute, BasicAttribute> register(String name,
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

  protected static@NotNull DeferredHolder<Attribute, MaxAttribute> registerMax(String name,
                                                                               Function<MaxAttribute, Attribute> function,
                                                                               double defaultValue,
                                                                               double maxValue) {
    return register(name, function, new MaxAttribute(descriptionId(name), defaultValue, maxValue));
  }

  protected static@NotNull DeferredHolder<Attribute, BooleanAttribute> register(String name,
                                                                                Function<BooleanAttribute, Attribute> function,
                                                                                boolean defaultValue) {
    return register(name, function, new BooleanAttribute(descriptionId(name), defaultValue));
  }

  protected static @NotNull String descriptionId(String name) {
    return LibMain.LIB_ID + ".attribute.name." + name;
  }
}
