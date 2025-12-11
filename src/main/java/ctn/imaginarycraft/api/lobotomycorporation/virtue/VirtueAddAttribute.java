package ctn.imaginarycraft.api.lobotomycorporation.virtue;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import ctn.imaginarycraft.init.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.Nullable;

public record VirtueAddAttribute(@Nullable Builder.Add fortitude, @Nullable Builder.Add prudence,
                                 @Nullable Builder.Add temperance, @Nullable Builder.Add justice) {

  public Multimap<Holder<Attribute>, AttributeModifier> getAttribute(LivingEntity entity, ResourceLocation id, ItemStack stack) {
    Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
    addAttribute(entity, id, stack, this.fortitude);
    addAttribute(entity, id, stack, this.prudence);
    addAttribute(entity, id, stack, this.temperance);
    addAttribute(entity, id, stack, this.justice);
    return modifier;
  }


  public static void addAttribute(double value, Multimap<Holder<Attribute>, AttributeModifier> multimap, ResourceLocation id, Holder<Attribute> holder, AttributeModifier.Operation operation) {
    if (value == 0) {
      return;
    }
    multimap.put(holder, new AttributeModifier(id, value, operation));
  }

  public void addAttribute(LivingEntity entity, ResourceLocation id, ItemStack stack) {
    addAttribute(entity, id, stack, this.fortitude);
    addAttribute(entity, id, stack, this.prudence);
    addAttribute(entity, id, stack, this.temperance);
    addAttribute(entity, id, stack, this.justice);
  }

  public void removeAttribute(LivingEntity entity, ResourceLocation id, ItemStack stack) {
    removeAttribute(entity, id, stack, this.fortitude);
    removeAttribute(entity, id, stack, this.prudence);
    removeAttribute(entity, id, stack, this.temperance);
    removeAttribute(entity, id, stack, this.justice);
  }

  private static void addAttribute(final LivingEntity entity, final ResourceLocation id, final ItemStack stack, final @Nullable Builder.Add fortitude) {
    if (fortitude == null || entity == null) {
      return;
    }
    entity.getAttributes().addTransientAttributeModifiers(fortitude.apply(entity, id, stack));
  }

  private static void removeAttribute(final LivingEntity entity, final ResourceLocation id, final ItemStack stack, final @Nullable Builder.Add fortitude) {
    if (fortitude == null || entity == null) {
      return;
    }
    entity.getAttributes().removeAttributeModifiers(fortitude.apply(entity, id, stack));
  }

  public static class Builder {
    private @Nullable Add fortitude;
    private @Nullable Add prudence;
    private @Nullable Add temperance;
    private @Nullable Add justice;

    public VirtueAddAttribute build() {
      return new VirtueAddAttribute(this.fortitude, this.prudence, this.temperance, this.justice);
    }

    /**
     * 勇气
     *
     * @param maxHealth 最大生命值
     */
    public Builder fortitude(int maxHealth) {
      if (maxHealth == 0) {
        return this;
      }
      this.fortitude = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        VirtueAddAttribute.addAttribute(maxHealth, modifier, id, Attributes.MAX_HEALTH, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    /**
     * 谨慎
     *
     * @param maxRationality 最大理智
     */
    public Builder prudence(int maxRationality) {
      if (maxRationality == 0) {
        return this;
      }
      this.prudence = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        VirtueAddAttribute.addAttribute(maxRationality, modifier, id, ModAttributes.MAX_RATIONALITY, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }


    /**
     * 自律
     *
     * @param blockBreakSpeed 挖掘速度
     * @param attackKnockback 攻击击退
     * @param workSuccessRate 工作成功率
     * @param workSpeed       工作速度
     */
    public Builder temperance(int blockBreakSpeed, int attackKnockback, int workSuccessRate, int workSpeed) {
      if (blockBreakSpeed == 0 && attackKnockback == 0) {
        return this;
      }
      this.temperance = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        // TODO 补充 成功率，工作速度
        VirtueAddAttribute.addAttribute(blockBreakSpeed * VirtueTemperance.BLOCK_BREAK_SPEED, modifier, id, Attributes.BLOCK_BREAK_SPEED, AttributeModifier.Operation.ADD_VALUE);
        VirtueAddAttribute.addAttribute(attackKnockback * VirtueTemperance.ATTACK_KNOCKBACK_SPEED, modifier, id, Attributes.ATTACK_KNOCKBACK, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    /**
     * 自律
     *
     * @param blockBreakSpeed 挖掘速度
     * @param attackKnockback 攻击击退
     * @param workValue       工作成功率，工作速度
     */
    public Builder temperance(int blockBreakSpeed, int attackKnockback, int workValue) {
      return temperance(blockBreakSpeed, attackKnockback, workValue, workValue);
    }

    /**
     * 自律
     */
    public Builder temperance(int value) {
      return temperance(value, value, value, value);
    }

    /**
     * 正义
     *
     * @param movementSpeed 移动速度
     * @param swimSpeed     游泳速度
     * @param attackSpeed   攻击速度
     */
    public Builder justice(int movementSpeed, int swimSpeed, int attackSpeed) {
      if (movementSpeed == 0 && attackSpeed == 0 && swimSpeed == 0) {
        return this;
      }
      this.justice = (entity, id, stack) -> {
        Multimap<Holder<Attribute>, AttributeModifier> modifier = LinkedHashMultimap.create();
        VirtueAddAttribute.addAttribute(movementSpeed * VirtueJustice.MOVEMENT_SPEED, modifier, id, Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.ADD_VALUE);
        VirtueAddAttribute.addAttribute(attackSpeed * VirtueJustice.ATTACK_SPEED, modifier, id, Attributes.ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE);
        VirtueAddAttribute.addAttribute(swimSpeed * VirtueJustice.SWIM_SPEED, modifier, id, NeoForgeMod.SWIM_SPEED, AttributeModifier.Operation.ADD_VALUE);
        return modifier;
      };
      return this;
    }

    /**
     * 正义
     *
     * @param speed       移动速度，游泳速度
     * @param attackSpeed 攻击速度
     */
    public Builder justice(int speed, int attackSpeed) {
      return justice(speed, speed, attackSpeed);
    }

    /**
     * 正义
     */
    public Builder justice(int value) {
      return justice(value, value, value);
    }

    @FunctionalInterface
    public interface Add {
      Multimap<Holder<Attribute>, AttributeModifier> apply(LivingEntity entity, ResourceLocation id, ItemStack stack);

      default void addAttribute(LivingEntity entity, ResourceLocation id, ItemStack stack) {
        entity.getAttributes().addTransientAttributeModifiers(apply(entity, id, stack));
      }

      default void removeAttribute(LivingEntity entity, ResourceLocation id, ItemStack stack) {
        entity.getAttributes().removeAttributeModifiers(apply(entity, id, stack));
      }
    }
  }
}
