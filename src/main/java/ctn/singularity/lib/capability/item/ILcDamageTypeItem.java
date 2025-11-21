package ctn.singularity.lib.capability.item;

import ctn.singularity.lib.api.lobotomycorporation.LcDamage;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.CheckForNull;
import java.util.List;

public interface ILcDamageTypeItem {
  /// 获取物品当前的伤害颜色<p>注：这个只对近战攻击有效果
  @CheckForNull
  LcDamage getColorDamageType(ItemStack stack);

  /// 获取可以造成的伤害类型 一般用于描述
  @CheckForNull
  List<LcDamage> getCanCauseDamageTypes();

  /// 获取四色伤害类型描述
  Component getColorDamageTypeToTooltip();
}
