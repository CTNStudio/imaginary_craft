package ctn.imaginarycraft.common.item.ego;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import ctn.imaginarycraft.client.model.GeoCurioModel;
import ctn.imaginarycraft.init.ModAttributes;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import static ctn.imaginarycraft.common.item.ego.EgoCurioItem.Builder.AddAttribute.addAttribute;

// TODO 补充一些装备联动导致的属性加成

/**
 * ego饰品 普通饰品请额外建个类
 *
 * @author Dusttt
 */
public class EgoCurioItem extends EgoItem implements ICurioItem, GeoItem {
  private final @Nullable Builder.AddAttribute fortitude;
  private final @Nullable Builder.AddAttribute prudence;
  private final @Nullable Builder.AddAttribute temperance;
  private final @Nullable Builder.AddAttribute justice;
  protected final GeoCurioModel<EgoCurioItem> model;
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  public EgoCurioItem(Builder builder) {
    super(builder.properties.component(ModDataComponents.IS_RESTRAIN, false).stacksTo(1).durability(0));
    this.model = builder.model;
    this.fortitude = builder.fortitude;
    this.prudence = builder.prudence;
    this.temperance = builder.temperance;
    this.justice = builder.justice;
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    ICurioItem.super.curioTick(slotContext, stack);
  }

  @Override
  public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
    ICurioItem.super.onEquip(slotContext, prevStack, stack);
  }

  @Override
  public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
    ICurioItem.super.onUnequip(slotContext, newStack, stack);
  }

  @Override
  public boolean canEquip(SlotContext slotContext, ItemStack stack) {
    return ICurioItem.super.canEquip(slotContext, stack);
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }

  /**
   * 属性加成
   */
  @Override
  public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
    Multimap<Holder<Attribute>, AttributeModifier> modifier = ICurioItem.super.getAttributeModifiers(slotContext, id, stack);
    apply(slotContext, id, stack, modifier, this.fortitude);
    apply(slotContext, id, stack, modifier, this.prudence);
    apply(slotContext, id, stack, modifier, this.temperance);
    apply(slotContext, id, stack, modifier, this.justice);
    return modifier;
  }

  private static void apply(final SlotContext slotContext, final ResourceLocation id, final ItemStack stack, final Multimap<Holder<Attribute>, AttributeModifier> modifier, final @Nullable Builder.AddAttribute fortitude) {
    if (fortitude == null) {
      return;
    }
    modifier.putAll(fortitude.apply(slotContext.entity(), id, stack));
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return cache;
  }

  public GeoCurioModel<EgoCurioItem> getModel() {
    return model;
  }

  public static class Builder {
    private @Nullable AddAttribute fortitude;
    private @Nullable AddAttribute prudence;
    private @Nullable AddAttribute temperance;
    private @Nullable AddAttribute justice;
    private Properties properties = new Properties();
    private final GeoCurioModel<EgoCurioItem> model;

    public Builder(GeoCurioModel<EgoCurioItem> model) {
      this.model = model;
    }

    public Builder properties(Properties properties) {
      this.properties = properties;
      return this;
    }

    public Builder fortitude(int basePoints) {
      if (basePoints == 0) {
        return this;
      }
      this.fortitude = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        addAttribute(basePoints, modifier, id, Attributes.MAX_HEALTH, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    public Builder prudence(int basePoints) {
      if (basePoints == 0) {
        return this;
      }
      this.prudence = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        addAttribute(basePoints, modifier, id, ModAttributes.MAX_RATIONALITY, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    public Builder temperance(int blockBreakSpeed, int attackKnockback) {
      if (blockBreakSpeed == 0 && attackKnockback == 0) {
        return this;
      }
      this.temperance = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        addAttribute(blockBreakSpeed, modifier, id, Attributes.BLOCK_BREAK_SPEED, AttributeModifier.Operation.ADD_VALUE);
        addAttribute(attackKnockback, modifier, id, Attributes.ATTACK_KNOCKBACK, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    public Builder justice(int movementSpeed, int attackSpeed, int swimSpeed) {
      if (movementSpeed == 0 && attackSpeed == 0 && swimSpeed == 0) {
        return this;
      }
      this.justice = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        addAttribute(movementSpeed, modifier, id, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADD_VALUE);
        addAttribute(attackSpeed, modifier, id, Attributes.ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE);
        addAttribute(swimSpeed, modifier, id, NeoForgeMod.SWIM_SPEED, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    public interface AddAttribute {
      Multimap<Holder<Attribute>, AttributeModifier> apply(LivingEntity entity, ResourceLocation id, ItemStack stack);

      static void addAttribute(float value, Multimap<Holder<Attribute>, AttributeModifier> multimap, ResourceLocation id, Holder<Attribute> holder, AttributeModifier.Operation operation) {
        if (value == 0) {
          return;
        }
        multimap.put(holder, new AttributeModifier(id, value, operation));
      }
    }
  }
}
