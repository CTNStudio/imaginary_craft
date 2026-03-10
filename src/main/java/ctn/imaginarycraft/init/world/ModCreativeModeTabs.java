package ctn.imaginarycraft.init.world;

import ctn.imaginarycraft.core.*;
import ctn.imaginarycraft.datagen.i18n.*;
import ctn.imaginarycraft.init.world.item.*;
import ctn.imaginarycraft.init.world.item.ego.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

/**
 * 创造模式物品栏
 */
public final class ModCreativeModeTabs {
  public static final DeferredRegister<CreativeModeTab> REGISTRY = ImaginaryCraft.modRegister(BuiltInRegistries.CREATIVE_MODE_TAB);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_CURIOS = register(
    "ego_curios", "异想工艺|E.G.O饰品", (name, zhCn) -> createCreativeModeTab(name, zhCn, (parameters, output) ->
      addRegistryItem(EgoCurioItems.REGISTRY, output), () ->
      EgoCurioItems.BENEDICTION.get().getDefaultInstance()));
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_WEAPON = register(
    "ego_weapon", "异想工艺|E.G.O武器", (name, zhCn) -> createCreativeModeTab(name, zhCn, (parameters, output) ->
      addRegistryItem(EgoWeaponItems.REGISTRY, output), () ->
      EgoWeaponItems.IN_THE_NAME_OF_LOVE_AND_HATE.get().getDefaultInstance()));
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_ARMOR = register(
    "ego_armor", "异想工艺|E.G.O护甲", (name, zhCn) -> createCreativeModeTab(name, zhCn, (parameters, output) ->
      addRegistryItem(EgoArmorItems.REGISTRY, output), () ->
      EgoArmorItems.IN_THE_NAME_OF_LOVE_AND_HATE.chestplate().get().getDefaultInstance()));
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ITEMS = register(
    "items", "异想工艺|物品", (name, zhCn) -> createCreativeModeTab(name, zhCn, (parameters, output) -> {
      addRegistryItem(EgoItems.REGISTRY, output);
      addRegistryItem(ModItems.REGISTRY, output);
      addRegistryItem(ToolItems.REGISTRY, output);
      addRegistryItem(WeaponItems.REGISTRY, output);
    }, () -> EgoArmorItems.IN_THE_NAME_OF_LOVE_AND_HATE.chestplate().get().getDefaultInstance()));
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SPAWN_EGG =register(
    "spwan_egg","异想工艺|刷怪蛋",(name, zhCn) ->createCreativeModeTab(name,zhCn,(parameters,output)->
      addRegistryItem(AbnormalitiesSpawnEggs.REGISTRY,output),()->
      AbnormalitiesSpawnEggs.GRANT_US_LOVE_SPAWN_EGG.get().getDefaultInstance()));
  private static DeferredHolder<CreativeModeTab, CreativeModeTab> register(
    String name,
    String zhCn,
    BiFunction<String, String, CreativeModeTab.Builder> builder
  ) {
    return ModCreativeModeTabs.REGISTRY.register(name, builder.apply(name, zhCn)::build);
  }

  private static CreativeModeTab.Builder createCreativeModeTab(
    String name,
    String zhCn,
    CreativeModeTab.DisplayItemsGenerator displayItemsGenerator,
    Supplier<ItemStack> icon,
    ResourceKey<CreativeModeTab> withTabsBefore
  ) {
    return createCreativeModeTab(name, zhCn, displayItemsGenerator, icon)
      .withTabsBefore(withTabsBefore);
  }

  private static CreativeModeTab.Builder createCreativeModeTab(
    String name,
    String zhCn,
    CreativeModeTab.DisplayItemsGenerator displayItemsGenerator,
    Supplier<ItemStack> icon
  ) {
    return createCreativeModeTab(name, zhCn, displayItemsGenerator)
      .icon(icon);
  }

  private static CreativeModeTab.Builder createCreativeModeTab(
    String name,
    String zhCn,
    CreativeModeTab.DisplayItemsGenerator displayItemsGenerator
  ) {
    String key = "itemGroup." + ImaginaryCraft.ID + "." + name;
    ZhCn.addI18nText(zhCn, key);
    return CreativeModeTab.builder()
      .title(Component.translatable(key))
      .displayItems(displayItemsGenerator);
  }

  private static void addRegistryItem(DeferredRegister.Items registry, CreativeModeTab.Output output) {
    registry.getEntries().forEach(entry -> output.accept(entry.get()));
  }
}
