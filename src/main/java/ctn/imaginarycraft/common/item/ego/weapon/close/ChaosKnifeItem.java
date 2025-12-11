package ctn.imaginarycraft.common.item.ego.weapon.close;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.common.item.ego.weapon.EgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/// 混沌刀
public class ChaosKnifeItem extends EgoWeaponItem {
  public static final String KEY = ImaginaryCraft.ID + ".item_tooltip.geo_describe.damage_type";

  public ChaosKnifeItem(Properties properties, Builder builder) {
    super(properties.component(ModDataComponents.LC_DAMAGE_TYPE.get(), LcDamageType.PHYSICS), builder
      .damage(7)
      .attackSpeed(-1.4F));
  }

  @Override
  @NotNull
  public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
    ItemStack itemStack = player.getItemInHand(usedHand);
    itemStack.update(ModDataComponents.LC_DAMAGE_TYPE, LcDamageType.PHYSICS,
      (damageType) -> {
        LcDamageType[] values = LcDamageType.values();
        int i = damageType.getIndex() + 1;
        return values[i >= values.length ? 0 : i];
      });
    return InteractionResultHolder.success(itemStack);
  }

  @Override
  public Component getLcDamageTypeToTooltip(final ItemStack stack) {
    LcDamageType damageType = getLcDamageColorDamageType(stack);
    return Component.translatable(KEY).withColor(0xAAAAAA).append(Component.literal(" ")
      .append(damageType.getName()).withColor(damageType.getColourValue()));
  }

  @Override
  @NotNull
  public LcDamageType getLcDamageColorDamageType(ItemStack stack) {
    return stack.getOrDefault(ModDataComponents.LC_DAMAGE_TYPE, LcDamageType.PHYSICS);
  }

  @Override
  public Set<LcDamageType> getCanCauseLcDamageTypes(final ItemStack stack) {
    return Set.of(LcDamageType.values());
  }

  @Override
  public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
  }
}
