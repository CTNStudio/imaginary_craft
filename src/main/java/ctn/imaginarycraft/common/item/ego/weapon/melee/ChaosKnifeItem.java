package ctn.imaginarycraft.common.item.ego.weapon.melee;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import ctn.imaginarycraft.common.item.ego.weapon.template.melee.SwordsEgoWeaponItem;
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
public class ChaosKnifeItem extends SwordsEgoWeaponItem {
  public static final String KEY = ImaginaryCraft.ID + ".item_tooltip.geo_describe.damage_type";

  public ChaosKnifeItem(Properties itemProperties, Builder egoWeaponBuilder) {
    super(itemProperties.component(ModDataComponents.LC_DAMAGE_TYPE.get(), LcDamageType.PHYSICS), egoWeaponBuilder);
  }

  @Override
  @NotNull
  public InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player playerEntity, @NotNull InteractionHand handUsed) {
    ItemStack itemStackInHand = playerEntity.getItemInHand(handUsed);
    itemStackInHand.update(ModDataComponents.LC_DAMAGE_TYPE, LcDamageType.PHYSICS,
      (damageType) -> {
        LcDamageType[] values = LcDamageType.values();
        int i = damageType.getIndex() + 1;
        return values[i >= values.length ? 0 : i];
      });
    return InteractionResultHolder.success(itemStackInHand);
  }

  @Override
  public Component getLcDamageTypeToTooltip(final ItemStack itemStack) {
    LcDamageType damageType = getLcDamageColorDamageType(itemStack);
    return Component.translatable(KEY).withColor(0xAAAAAA).append(Component.literal(" ")
      .append(damageType.getName()).withColor(damageType.getColourValue()));
  }

  @Override
  @NotNull
  public LcDamageType getLcDamageColorDamageType(ItemStack itemStack) {
    return itemStack.getOrDefault(ModDataComponents.LC_DAMAGE_TYPE, LcDamageType.PHYSICS);
  }

  @Override
  public Set<LcDamageType> getCanCauseLcDamageTypes(final ItemStack itemStack) {
    return Set.of(LcDamageType.values());
  }

  @Override
  public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext tooltipContext, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
    super.appendHoverText(itemStack, tooltipContext, tooltipComponents, tooltipFlag);
  }
}
