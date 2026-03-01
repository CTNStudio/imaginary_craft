package ctn.imaginarycraft.common.world.item.ego.weapon.melee.swords;

import ctn.imaginarycraft.api.LcDamageType;
import ctn.imaginarycraft.api.world.item.IMeleeEgoWeaponItem;
import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.core.capability.item.IItemLcDamageType;
import ctn.imaginarycraft.init.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/// 混沌刀
public class ChaosKnifeItem extends SwordsEgoWeaponItem implements IItemLcDamageType {
  public static final String KEY = ImaginaryCraft.ID + ".item_tooltip.geo_describe.damage_type";
  private static final LcDamageType.Component DEFAULT_COMPONENT = new LcDamageType.Component(LcDamageType.PHYSICS, LcDamageType.values());

  public ChaosKnifeItem(Properties itemProperties, IMeleeEgoWeaponItem.Builder builder) {
    super(itemProperties.component(ModDataComponents.LC_DAMAGE_TYPE.get(), new LcDamageType.Component(LcDamageType.PHYSICS, LcDamageType.values())), builder);
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
