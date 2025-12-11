package ctn.imaginarycraft.init;

import ctn.imaginarycraft.core.ImaginaryCraft;
import ctn.imaginarycraft.init.item.ego.EgoCurioItems;
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
//  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_WEAPON =
//    register("ego_weapon", (name) -> createCreativeModeTab(
//      name, (parameters, output) -> {
//      }, () -> ));

//  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_ARMOR =
//    register("ego_armor", (name) -> createCreativeModeTab(
//      name, (parameters, output) -> {
//      }, () -> ));

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EGO_CURIOS = register(
    "ego_curios", "E.G.O 饰品", (name, zhCn) -> createCreativeModeTab(name, zhCn, (parameters, output) ->
      EgoCurioItems.REGISTRY.getEntries().forEach(entry -> output.accept(entry.get())), () ->
      EgoCurioItems.BENEDICTION.get().getDefaultInstance()));

//  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TOOL =
//    register("creative_tool", (name) -> createCreativeModeTab(
//      name, (parameters, output) -> {
//      }, () -> ));
}
