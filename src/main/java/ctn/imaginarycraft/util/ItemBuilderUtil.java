package ctn.imaginarycraft.util;

import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.component.*;

public final class ItemBuilderUtil {
  public static void addAttributeModifier(final ItemAttributeModifiers.Builder builder, final Holder<Attribute> attributeHolder, final ResourceLocation id, final double value, final AttributeModifier.Operation operation, EquipmentSlotGroup slot) {
    if (value == 0) {
      return;
    }
    builder.add(attributeHolder, new AttributeModifier(id, value, operation), slot);
  }
}
