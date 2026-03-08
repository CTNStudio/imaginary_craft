package ctn.imaginarycraft.mixin.world.item;

import com.llamalad7.mixinextras.expression.*;
import com.llamalad7.mixinextras.injector.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import ctn.imaginarycraft.client.event.*;
import ctn.imaginarycraft.init.*;
import net.minecraft.core.component.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.function.*;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder, net.neoforged.neoforge.common.MutableDataComponentHolder, net.neoforged.neoforge.common.extensions.IItemStackExtension {
  @Shadow
  public abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> component, Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag);

  @WrapOperation(method = "addToTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/TooltipProvider;addToTooltip(Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", ordinal = 0))
  private <T extends TooltipProvider> void imaginarycraft$addItemDataComponentTooltipEvent(
    TooltipProvider instance, Item.TooltipContext context,
    Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag,
    Operation<Void> original,
    @Local(argsOnly = true) DataComponentType<T> component
  ) {
    ItemStack imaginarycraft$itemStack = getImaginarycraft$itemStack();
    var event = new AddItemDataComponentTooltipEvent.Up<>(imaginarycraft$itemStack, component, context, tooltipAdder, tooltipFlag);
    if (event.isCanceled()) {
      return;
    }
    original.call(instance, context, tooltipAdder, tooltipFlag);
    new AddItemDataComponentTooltipEvent.Down<>(imaginarycraft$itemStack, component, context, tooltipAdder, tooltipFlag);
  }

  @Definition(id = "has", method = "Lnet/minecraft/world/item/ItemStack;has(Lnet/minecraft/core/component/DataComponentType;)Z")
  @Definition(id = "HIDE_ADDITIONAL_TOOLTIP", field = "HIDE_ADDITIONAL_TOOLTIP", type = DataComponents.class)
  @Expression("this.has(HIDE_ADDITIONAL_TOOLTIP)")
  @ModifyExpressionValue(method = "getTooltipLines", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean imaginarycraft$addTooltip(
    boolean original, @Local Consumer<Component> instance,
    @Local(argsOnly = true) Item.TooltipContext tooltipContext,
    @Local(argsOnly = true) TooltipFlag tooltipFlag
  ) {
    // 添加物品使用要求
    addToTooltip(ModDataComponents.ITEM_VIRTUE_USAGE_REQ, tooltipContext, instance, tooltipFlag);
    return original;
  }

  @Unique
  @NotNull
  private ItemStack getImaginarycraft$itemStack() {
    return (ItemStack) (Object) this;
  }
}
