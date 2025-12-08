package ctn.imaginarycraft.capability.item;

import ctn.imaginarycraft.api.lobotomycorporation.LcDamageType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.CheckForNull;
import java.util.List;

public interface ILcDamageTypeItem {
  /// 获取物品当前的伤害颜色<p>注：这个只对近战攻击有效果
  @CheckForNull
  LcDamageType getLcDamageColorDamageType(ItemStack stack);

  /// 获取可以造成的伤害类型 一般用于描述
  @CheckForNull
  List<LcDamageType> getCanCauseDamageTypes();

  /// 获取四色伤害类型描述
  Component getColorDamageTypeToTooltip();
}
