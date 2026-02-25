package ctn.imaginarycraft.init;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ModItems;
import ctn.imaginarycraft.init.item.ToolItems;
import ctn.imaginarycraft.init.item.WeaponItems;
import ctn.imaginarycraft.init.item.ego.EgoArmorItems;
import ctn.imaginarycraft.init.item.ego.EgoCurioItems;
import ctn.imaginarycraft.init.item.ego.EgoItems;
import ctn.imaginarycraft.init.item.ego.EgoWeaponItems;
import ctn.imaginarycraft.init.util.CreativeModeTabRegisterUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 创造模式物品栏
 */
public final class ModCreativeModeTabs extends CreativeModeTabRegisterUtil {
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
}
