package ctn.imaginarycraft.common.item.ego.armor;

import ctn.imaginarycraft.api.capability.item.IItemEgo;
import ctn.imaginarycraft.api.capability.item.IItemUsageReq;
import ctn.imaginarycraft.client.model.ModGeoArmorModel;
import ctn.imaginarycraft.client.renderer.providers.ModGeoArmourRenderProvider;
import ctn.imaginarycraft.common.components.ItemVirtueUsageReq;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.ModDataComponents;
import ctn.imaginarycraft.util.ItemBuilderUtil;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EgoArmorItem extends ArmorItem implements GeoItem, IItemUsageReq, IItemEgo {
  protected final ModGeoArmourRenderProvider<EgoArmorItem> renderProvider;
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public EgoArmorItem(Builder builder) {
    super(builder.material, builder.type, builder.properties
      .stacksTo(1)
      .component(ModDataComponents.IS_RESTRAIN, false));
    this.renderProvider = builder.renderProvider;
  }

  public EgoArmorItem(Properties properties, Builder builder) {
    this(builder.properties(properties));
  }

  @Override
  public void useImpede(final ItemStack itemStack, final Level level, final LivingEntity entity) {

  }

  @Override
  public void attackImpede(final ItemStack itemStack, final Level level, final LivingEntity entity) {

  }

  @Override
  public void onTheHandImpede(final ItemStack itemStack, final Level level, final LivingEntity entity) {

  }

  @Override
  public void inTheBackpackImpede(final ItemStack itemStack, final Level level, final LivingEntity entity) {

  }

  @Override
  public void equipmentImpede(final ItemStack itemStack, final Level level, final LivingEntity entity) {

  }

  @Override
  public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {

  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return null;
  }

  public static class Builder {
    private Properties properties = new Properties();
    protected ItemVirtueUsageReq.Builder virtueUsageReqBuilder;
    protected Holder<ArmorMaterial> material;
    protected ArmorItem.Type type;
    protected double physicsVulnerable;
    protected double spiritVulnerable;
    protected double erosionVulnerable;
    protected double theSoulVulnerable;
    protected ModGeoArmourRenderProvider<EgoArmorItem> renderProvider;
    /**
     * 耐久
     */
    protected int durability;

    public Builder() {
    }

    public Builder init(Holder<ArmorMaterial> material, ArmorItem.Type type, ModGeoArmourRenderProvider<EgoArmorItem> renderProvider) {
      this.material = material;
      this.type = type;
      this.renderProvider = renderProvider;
      return this;
    }

    public Builder init(Holder<ArmorMaterial> material, ArmorItem.Type type, ModGeoArmorModel<EgoArmorItem> model) {
      this.material = material;
      this.type = type;
      this.renderProvider = new ModGeoArmourRenderProvider<>(model, null);
      return this;
    }

    public Builder properties(Properties properties) {
      this.properties = properties;
      return this;
    }

    public Builder material(Holder<ArmorMaterial> material) {
      this.material = material;
      return this;
    }

    public Builder type(ArmorItem.Type type) {
      this.type = type;
      return this;
    }

    public Builder renderProvider(ModGeoArmourRenderProvider<EgoArmorItem> renderProvider) {
      this.renderProvider = renderProvider;
      return this;
    }

    public Properties buildProperties() {
      Properties properties = this.properties;
      int durability = this.durability;
      properties.attributes(getItemAttributeModifiers());
      if (durability > 0) {
        properties.durability(durability);
      }
      ItemVirtueUsageReq.Builder.add(properties, this.virtueUsageReqBuilder);
      return properties;
    }

    public ItemAttributeModifiers getItemAttributeModifiers() {
      ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
      Type armorType = this.type;
      ArmorMaterial armorMaterial = material.value();
      EquipmentSlotGroup dropLocation = EquipmentSlotGroup.bySlot(armorType.getSlot());
      ResourceLocation id = getArmorModifierId(armorType);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.PHYSICS_VULNERABLE, id, this.physicsVulnerable, AttributeModifier.Operation.ADD_VALUE, dropLocation);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.SPIRIT_VULNERABLE, id, this.spiritVulnerable, AttributeModifier.Operation.ADD_VALUE, dropLocation);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.EROSION_VULNERABLE, id, this.erosionVulnerable, AttributeModifier.Operation.ADD_VALUE, dropLocation);
      ItemBuilderUtil.addAttributeModifier(builder, ModAttributes.THE_SOUL_VULNERABLE, id, this.theSoulVulnerable, AttributeModifier.Operation.ADD_VALUE, dropLocation);

      ItemBuilderUtil.addAttributeModifier(builder, Attributes.ARMOR, id, armorMaterial.getDefense(armorType), AttributeModifier.Operation.ADD_VALUE, dropLocation);
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

    public Builder durability(int durability) {
      this.durability = durability;
      return this;
    }
  }
}
