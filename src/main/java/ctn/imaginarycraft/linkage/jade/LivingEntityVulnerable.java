package ctn.imaginarycraft.linkage.jade;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.core.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import snownee.jade.*;
import snownee.jade.api.*;
import snownee.jade.api.config.*;
import snownee.jade.api.ui.*;

public enum LivingEntityVulnerable implements IEntityComponentProvider {
  INSTANCE;

  public static final String ATTRIBUTE_DESCRIPTION_KEY = ImaginaryCraft.modRlText("entity.attribute_description");
  public static final String PHYSICS_KEY = ImaginaryCraft.modRlText("entity.attribute_description.physics");
  public static final String SPIRIT_KEY = ImaginaryCraft.modRlText("entity.attribute_description.spirit");
  public static final String EROSION_KEY = ImaginaryCraft.modRlText("entity.attribute_description.erosion");
  public static final String THE_SOUL_KEY = ImaginaryCraft.modRlText("entity.attribute_description.the_soul");

  @Override
  public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
    if (!(entityAccessor.getEntity() instanceof LivingEntity entity)) {
      return;
    }
    IElementHelper elements = JadeInternals.getElementHelper();
    iTooltip.add(Component.translatable(ATTRIBUTE_DESCRIPTION_KEY));
    add(iTooltip, PHYSICS_KEY, "physics8x", LcDamageType.PHYSICS, entity, elements);
    add(iTooltip, SPIRIT_KEY, "spirit8x", LcDamageType.SPIRIT, entity, elements);
    add(iTooltip, EROSION_KEY, "erosion8x", LcDamageType.EROSION, entity, elements);
    add(iTooltip, THE_SOUL_KEY, "the_soul8x", LcDamageType.THE_SOUL, entity, elements);
  }

  private static void add(ITooltip iTooltip, String key, String spriteRl, LcDamageType damageType, LivingEntity entity, IElementHelper elements) {
    iTooltip.add(elements.sprite(ImaginaryCraft.modRl(spriteRl), 8, 8));
    Holder<Attribute> vulnerable = damageType.getVulnerable();
    double text = hasAttribute(entity, vulnerable) ? entity.getAttributeValue(vulnerable) : vulnerable.value().getDefaultValue();
    int colour = damageType.getColourValue();
    iTooltip.append(Component.translatable(key).append(String.format(" %.2f", text)).withColor(colour));
  }

  private static boolean hasAttribute(final LivingEntity entity, Holder<Attribute> attribute) {
    return entity.getAttributes().hasAttribute(attribute);
  }

  @Override
  public ResourceLocation getUid() {
    return ModJadePlugin.ENTITY_LC_VULNERABLE;
  }
}
