package ctn.imaginarycraft.common.world.item.ego.weapon.melee.swords;

import ctn.imaginarycraft.api.*;
import ctn.imaginarycraft.api.world.item.*;
import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.core.capability.item.*;
import ctn.imaginarycraft.init.*;
import net.minecraft.network.chat.*;
import net.minecraft.tags.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.*;
import org.jetbrains.annotations.*;

import java.util.*;

/// 混沌刀
public class ChaosKnifeItem extends SwordsEgoWeaponItem implements IItemLcDamageType {
  public static final String KEY = ImaginaryCraft.ID + ".item_tooltip.geo_describe.damage_type";
  private static final LcDamageType.Component DEFAULT_COMPONENT = new LcDamageType.Component(LcDamageType.PHYSICS, LcDamageType.values());

  public ChaosKnifeItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder builder) {
    super(new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 0, builder.attackSpeed, builder.weaponDamage, 5, Ingredient::of), itemProperties.component(ModDataComponents.LC_DAMAGE_TYPE.get(), new LcDamageType.Component(LcDamageType.PHYSICS, LcDamageType.values())), builder);
  }

  @Override
  @NotNull
  public InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player playerEntity, @NotNull InteractionHand handUsed) {
    ItemStack itemStackInHand = playerEntity.getItemInHand(handUsed);
    itemStackInHand.update(ModDataComponents.LC_DAMAGE_TYPE, DEFAULT_COMPONENT,
      (damageType) -> {
        LcDamageType[] values = LcDamageType.values();
        int i = damageType.lcDamageType().getIndex() + 1;
        return new LcDamageType.Component(values[i >= values.length ? 0 : i], LcDamageType.values());
      });
    return InteractionResultHolder.success(itemStackInHand);
  }

  @Override
  public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext tooltipContext, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
    super.appendHoverText(itemStack, tooltipContext, tooltipComponents, tooltipFlag);
  }

  @Override
  public @Nullable LcDamageType getLcDamageType(ItemStack stack) {
    return getComponent(stack).lcDamageType();
  }

  @Override
  public @NotNull Set<LcDamageType> getCanCauseLcDamageTypes(ItemStack stack) {
    return getComponent(stack).canCauseLcDamageTypes();
  }

  private static LcDamageType.Component getComponent(ItemStack stack) {
    return stack.getOrDefault(ModDataComponents.LC_DAMAGE_TYPE, DEFAULT_COMPONENT);
  }
}
