package ctn.imaginarycraft.linkage.jade;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.jetbrains.annotations.NotNull;
import snownee.jade.JadeInternals;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

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
    boolean isPhysics = hasAttribute(entity, ModAttributes.PHYSICS_VULNERABLE);
    boolean isSpirit = hasAttribute(entity, ModAttributes.SPIRIT_VULNERABLE);
    boolean isErosion = hasAttribute(entity, ModAttributes.EROSION_VULNERABLE);
    boolean isTheSoul = hasAttribute(entity, ModAttributes.THE_SOUL_VULNERABLE);
    if (!isPhysics && !isSpirit && !isErosion && !isTheSoul) {
      return;
    }
    IElementHelper elements = JadeInternals.getElementHelper();
    iTooltip.add(Component.translatable(ATTRIBUTE_DESCRIPTION_KEY));
    if (isPhysics) {
      iTooltip.add(getSprite(elements, "physics8x"));
      iTooltip.append(getComponent(entity, PHYSICS_KEY, LcDamageType.PHYSICS.getColourValue(), ModAttributes.PHYSICS_VULNERABLE));
    }
    if (isSpirit) {
      iTooltip.add(getSprite(elements, "spirit8x"));
      iTooltip.append(getComponent(entity, SPIRIT_KEY, LcDamageType.SPIRIT.getColourValue(), ModAttributes.SPIRIT_VULNERABLE));
    }
    if (isErosion) {
      iTooltip.add(getSprite(elements, "erosion8x"));
      iTooltip.append(getComponent(entity, EROSION_KEY, LcDamageType.EROSION.getColourValue(), ModAttributes.EROSION_VULNERABLE));
    }
    if (isTheSoul) {
      iTooltip.add(getSprite(elements, "the_soul8x"));
      iTooltip.append(getComponent(entity, THE_SOUL_KEY, LcDamageType.THE_SOUL.getColourValue(), ModAttributes.THE_SOUL_VULNERABLE));
    }
  }

  private static IElement getSprite(IElementHelper elements, String physics8x) {
    return elements.sprite(ImaginaryCraft.modRl(physics8x), 8, 8);
  }

  private static @NotNull MutableComponent getComponent(LivingEntity entity, String key, int color, Holder<Attribute> attribute) {
    return Component.translatable(key).withColor(color).append(getAttributeValue(entity, attribute)).withColor(color);
  }

  private static @NotNull String getAttributeValue(LivingEntity entity, Holder<Attribute> attribute) {
    return String.format(" %.2f", entity.getAttributeValue(attribute));
  }

  private static boolean hasAttribute(final LivingEntity entity, Holder<Attribute> attribute) {
    return entity.getAttributes().hasAttribute(attribute);
  }

  @Override
  public ResourceLocation getUid() {
    return ModPlugin.ENTITY_LC_VULNERABLE;
  }
}
