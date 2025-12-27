package ctn.imaginarycraft.init.util;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.datagen.i18n.ZhCn;
import ctn.imaginarycraft.init.ModCreativeModeTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class CreativeModeTabRegisterUtil {
  protected static DeferredHolder<CreativeModeTab, CreativeModeTab> register(
    String name,
    String zhCn,
    BiFunction<String, String, CreativeModeTab.Builder> builder
  ) {
    return ModCreativeModeTabs.REGISTRY.register(name, builder.apply(name, zhCn)::build);
  }

  protected static CreativeModeTab.Builder createCreativeModeTab(
    String name,
    String zhCn,
    CreativeModeTab.DisplayItemsGenerator displayItemsGenerator,
    Supplier<ItemStack> icon,
    ResourceKey<CreativeModeTab> withTabsBefore
  ) {
    return createCreativeModeTab(name, zhCn, displayItemsGenerator, icon)
      .withTabsBefore(withTabsBefore);
  }

  protected static CreativeModeTab.Builder createCreativeModeTab(
    String name,
    String zhCn,
    CreativeModeTab.DisplayItemsGenerator displayItemsGenerator,
    Supplier<ItemStack> icon
  ) {
    return createCreativeModeTab(name, zhCn, displayItemsGenerator)
      .icon(icon);
  }

  protected static CreativeModeTab.Builder createCreativeModeTab(
    String name,
    String zhCn,
    CreativeModeTab.DisplayItemsGenerator displayItemsGenerator
  ) {
    String key = "itemGroup." + ImaginaryCraft.ID + "." + name;
    ZhCn.clientAddI18nText(zhCn, key);
    return CreativeModeTab.builder()
      .title(Component.translatable(key))
      .displayItems(displayItemsGenerator);
  }


}
