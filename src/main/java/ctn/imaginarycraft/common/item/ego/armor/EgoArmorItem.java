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

  public EgoArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties,
                      Builder builder, GeoRenderProvider renderProvider) {
    super(material, type, properties
      .stacksTo(1)
      .attributes(builder.getItemAttributeModifiers(type, material))
      .component(ModDataComponents.IS_RESTRAIN, false)
      .component(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, builder.virtueUsageReqBuilder.build()));
    this.renderProvider = renderProvider;
  }

  @Override
  public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {

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

    public ItemAttributeModifiers getItemAttributeModifiers(ArmorItem.Type type, Holder<ArmorMaterial> material) {
      ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
      ArmorMaterial armorMaterial = material.value();
      EquipmentSlotGroup dropLocation = EquipmentSlotGroup.bySlot(type.getSlot());
      ResourceLocation id = getArmorModifierId(type);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.PHYSICS_VULNERABLE, id, this.physicsVulnerable, AttributeModifier.Operation.ADD_VALUE, dropLocation);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.SPIRIT_VULNERABLE, id, this.spiritVulnerable, AttributeModifier.Operation.ADD_VALUE, dropLocation);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.EROSION_VULNERABLE, id, this.erosionVulnerable, AttributeModifier.Operation.ADD_VALUE, dropLocation);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.THE_SOUL_VULNERABLE, id, this.theSoulVulnerable, AttributeModifier.Operation.ADD_VALUE, dropLocation);

      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ARMOR, id, armorMaterial.getDefense(type), AttributeModifier.Operation.ADD_VALUE, dropLocation);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ARMOR_TOUGHNESS, id, armorMaterial.toughness(), AttributeModifier.Operation.ADD_VALUE, dropLocation);
      ItemBuilderUtil.addAttributeModifier(builder, Attributes.KNOCKBACK_RESISTANCE, id, armorMaterial.knockbackResistance(), AttributeModifier.Operation.ADD_VALUE, dropLocation);
      return builder.build();
    }

    public Builder virtueUsageReqBuilder(ItemVirtueUsageReq.Builder virtueUsageReqBuilder) {
      this.virtueUsageReqBuilder = virtueUsageReqBuilder;
      return this;
    }

    private static @NotNull ResourceLocation getArmorModifierId(final Type type) {
      return ResourceLocation.withDefaultNamespace("armor." + type.getName());
    }

    /**
     * 易伤
     */
    public Builder vulnerable(double physics, double spirit, double erosion, double theSoul) {
      this.physicsVulnerable = physics;
      this.spiritVulnerable = spirit;
      this.erosionVulnerable = erosion;
      this.theSoulVulnerable = theSoul;
      return this;
    }
  }
}
