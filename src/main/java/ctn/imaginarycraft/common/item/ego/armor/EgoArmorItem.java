package ctn.imaginarycraft.common.item.ego.armor;

import ctn.imaginarycraft.api.capability.item.IItemEgo;
import ctn.imaginarycraft.api.capability.item.IItemUsageReq;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.ModDataComponents;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class EgoArmorItem extends ArmorItem implements GeoItem, IItemUsageReq, IItemEgo {
  protected final GeoRenderProvider renderProvider;
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public EgoArmorItem(Holder<ArmorMaterial> armorMaterial, ArmorItem.Type armorType, Item.Properties itemProperties,
                      Builder egoArmorBuilder, GeoRenderProvider geoRendererProvider) {
    super(armorMaterial, armorType, itemProperties
      .stacksTo(1)
      .attributes(egoArmorBuilder.getItemAttributeModifiers(armorType, armorMaterial))
      .component(ModDataComponents.IS_RESTRAIN, false)
      .component(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, egoArmorBuilder.virtueUsageReqBuilder.build()));
    this.renderProvider = geoRendererProvider;
  }

  @Override
  public void registerControllers(final AnimatableManager.ControllerRegistrar controllerRegistrar) {

  }

  @Override
  public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
    consumer.accept(this.renderProvider);
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  public static class Builder {
    protected ItemVirtueUsageReq.Builder virtueUsageReqBuilder;
    protected double physicsVulnerable;
    protected double spiritVulnerable;
    protected double erosionVulnerable;
    protected double theSoulVulnerable;

    public Builder() {
    }

    public ItemAttributeModifiers getItemAttributeModifiers(ArmorItem.Type armorType, Holder<ArmorMaterial> material) {
      ItemAttributeModifiers.Builder attributeBuilder = ItemAttributeModifiers.builder();
      ArmorMaterial armorMaterial = material.value();
      EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(armorType.getSlot());
      ResourceLocation id = getArmorModifierId(armorType);
      ItemBuilderUtil.addAttributeModifier(attributeBuilder, ModAttributes.PHYSICS_VULNERABLE, id, this.physicsVulnerable, AttributeModifier.Operation.ADD_VALUE, equipmentSlotGroup);
      ItemBuilderUtil.addAttributeModifier(attributeBuilder, ModAttributes.SPIRIT_VULNERABLE, id, this.spiritVulnerable, AttributeModifier.Operation.ADD_VALUE, equipmentSlotGroup);
      ItemBuilderUtil.addAttributeModifier(attributeBuilder, ModAttributes.EROSION_VULNERABLE, id, this.erosionVulnerable, AttributeModifier.Operation.ADD_VALUE, equipmentSlotGroup);
      ItemBuilderUtil.addAttributeModifier(attributeBuilder, ModAttributes.THE_SOUL_VULNERABLE, id, this.theSoulVulnerable, AttributeModifier.Operation.ADD_VALUE, equipmentSlotGroup);

      ItemBuilderUtil.addAttributeModifier(attributeBuilder, Attributes.ARMOR, id, armorMaterial.getDefense(armorType), AttributeModifier.Operation.ADD_VALUE, equipmentSlotGroup);
      ItemBuilderUtil.addAttributeModifier(attributeBuilder, Attributes.ARMOR_TOUGHNESS, id, armorMaterial.toughness(), AttributeModifier.Operation.ADD_VALUE, equipmentSlotGroup);
      ItemBuilderUtil.addAttributeModifier(attributeBuilder, Attributes.KNOCKBACK_RESISTANCE, id, armorMaterial.knockbackResistance(), AttributeModifier.Operation.ADD_VALUE, equipmentSlotGroup);
      return attributeBuilder.build();
    }

    public Builder virtueUsageReqBuilder(ItemVirtueUsageReq.Builder virtueRequirementBuilder) {
      this.virtueUsageReqBuilder = virtueRequirementBuilder;
      return this;
    }

    private static @NotNull ResourceLocation getArmorModifierId(final Type armorType) {
      return ResourceLocation.withDefaultNamespace("armor." + armorType.getName());
    }

    /**
     * 易伤
     */
    public Builder vulnerable(double physicsVulnerability, double spiritVulnerability, double erosionVulnerability, double soulVulnerability) {
      this.physicsVulnerable = physicsVulnerability;
      this.spiritVulnerable = spiritVulnerability;
      this.erosionVulnerable = erosionVulnerability;
      this.theSoulVulnerable = soulVulnerability;
      return this;
    }
  }
}
